/* 
SVN FILE: $Id: $ 
 
Copyright (c) 2008-2010, Engineering Ingegneria Informatica S.p.A.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Engineering Ingegneria Informatica S.p.A. nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Engineering Ingegneria Informatica S.p.A. BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

@author         $Author: $
@version        $Rev: $
@lastrevision   $Date: $
@filesource     $URL: $

*/

package org.slasoi.slamodel.sla.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.primitives.Expr;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.Interface.Specification;
import org.slasoi.slamodel.service.Interface.Specification.Resolver;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Endpoint;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.Guaranteed.Action.Defn;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.sla;
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public class Validator {
	
	private static final long serialVersionUID = 1L;
	
	public static class Report implements Serializable{
	    
		private static final long serialVersionUID = 1L;
		
		public SLATemplate slat = null;
		
		public List<Warning> warnings = Collections.synchronizedList(new ArrayList<Warning>());
		
		// Map<IterfaceDeclr.getId(), <Specification.getName(), Specification>>
		public Map<String,Map<String, Specification>> ispecs = Collections.synchronizedMap(new HashMap<String,Map<String, Specification>>());
		
		// Map<Specification.getName(), AgreementRole>
		public Map<String, STND> ispec_providers = Collections.synchronizedMap(new HashMap<String, STND>());
		
		// Map<IterfaceDeclr.getId(), <ResourceType.getName(), ResourceType>>
        public Map<String,Map<String, ResourceType>> res_types = Collections.synchronizedMap(new HashMap<String,Map<String, ResourceType>>());
        
        // Map<ResourceType.getName(), AgreementRole>
        public Map<String, STND> res_type_providers = Collections.synchronizedMap(new HashMap<String, STND>());
	}

	Resolver ispec_resolver = null;
    MetaModel metamodel = null;
    _ispec ispec = null;
    //_expr expr = null;
    
    public Validator(Resolver r){
        ispec_resolver = r;
        metamodel = new MetaModel();
        ispec = new _ispec(r);
        //expr = new _expr(metamodel);
    }
    
    public MetaModel getMetaModel(){ return metamodel; }
	
	public Report validate(SLATemplate slat) throws Exception{
        _acc acc = new _acc(metamodel, slat);
		ispec.p_RESOLVE_INTERFACES(slat.getInterfaceDeclrs(), acc);
		ExprValidator expr = new ExprValidator(metamodel, acc);
		x_FIRST_PASS(expr);
		return acc;
	}
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Extensions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public void addExtensions(Extensions exts) throws Exception{
    	metamodel.p_ADD_EXT(exts, true);
    }
    
    public void removeExtensions(Extensions exts) throws Exception{
    	metamodel.p_REMOVE_EXT(exts);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Warnings
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static class Warning{
        
        private String msg = null;
        private Object source_entity = null;
        
        public Warning(String msg, Object source_entity){
            this.msg = msg;
            this.source_entity = source_entity; 
        }
        
        public String message(){
            return msg;
        }
        
        public Object sourceEntity(){
            return source_entity;
        }
        
    }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Exceptions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static class Exception extends java.lang.Exception{
    	
    	private static final long serialVersionUID = 1L;

    	private Object source_entity = null;
        
        public Exception(java.lang.Exception e){
            super(e);
        }
        
        public Exception(String msg, Object source_entity){
            super(msg);
            this.source_entity = source_entity;
        }
        
        public Object sourceEntity(){
            return source_entity;
        }
        
	}
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // VALIDATION
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	private void x_FIRST_PASS(ExprValidator expr) throws Validator.Exception{
		// assume top-level atributes have been tested .. 
		List<Expr> expressions = new ArrayList<Expr>();
		Map<String,List<Expr>> contextualised_expressions = new HashMap<String,List<Expr>>(); 
        
        // VARIABLE DECLRS
        
        VariableDeclr[] vdecs = expr.acc.slat.getVariableDeclrs();
        if (vdecs != null){
            for (VariableDeclr vdec : vdecs){
                ID vdec_id = vdec.getVar();
                String $vdec_id = vdec_id.getValue();
                expr.acc.p_REGISTER_ID($vdec_id, meta.VARIABLE);
                expressions.add(vdec.getExpr());
            }
        }
		
		// PARTIES
		
		Party[] parties = expr.acc.slat.getParties(); 
		if (parties != null && parties.length>0){
			for (Party party : parties){
				ID party_id = party.getId();
				String $party_id = party_id.getValue();
				expr.acc.p_REGISTER_ID($party_id, meta.PARTY);
				STND role = party.getAgreementRole(); // guaranteed
				if (metamodel.getNominal(role, Role.AGREEMENT_ROLE)==null){
					throw new Validator.Exception("FPV: Invalid Agreement Role: "+role.getValue(), role);
				}
				List<Party> ps = expr.acc.roles.get(role);
				if (ps == null){
					ps = new ArrayList<Party>();
					expr.acc.roles.put(role,ps);
				}
				ps.add(party);
				
				Party.Operative[] party_operatives = party.getOperatives();
				if (party_operatives != null){
					for (Party.Operative operative : party_operatives){
						ID operative_id = operative.getId();
						String $operative_id = operative_id.getValue();
						expr.acc.p_REGISTER_ID($party_id+ID.$path_separator+$operative_id, meta.OPERATIVE);
					}
				}
			}
			List<Party> providers = expr.acc.roles.get(sla.provider);
			if (providers == null || providers.isEmpty()){
				throw new Validator.Exception("FPV: No provider party specifed", expr.acc.slat);
			}
		}else{
			throw new Validator.Exception("FPV: No agreement parties specifed", expr.acc.slat);
		}
		
		// INTERFACE DECLRS
        
		InterfaceDeclr[] idecs = expr.acc.slat.getInterfaceDeclrs();
		boolean provider_iface_declared = false;
		if (idecs != null && idecs.length>0){
			for (InterfaceDeclr idec : idecs){
				ID idec_id = idec.getId();
				String $idec_id = idec_id.getValue();
				// acc.p_REGISTER_ID($iface_declr_id, iface_declr_id); registered during _v_ISPEC validation
				ID providerRef = idec.getProviderRef();
				STND role = expr.acc.p_GET_REGISTERED_ROLE_OF_PARTY_REF(providerRef, true);  // throws ACC: exceptions if invalid reference
				if (role.equals(sla.provider)){ // throws ACC: exceptions if invalid reference
					provider_iface_declared = true;
				}
				// ispecs is part of Validator.Report ..
				// ispecs == Map<IterfaceDeclr.getId(), <Specification.getName(), Specification>>
				Map<String, Specification> map1 = expr.acc.ispecs.get(idec_id); 
				if (map1 != null) for (String s : map1.keySet()){
				    // record info about which party(role) offers the interface 
				    expr.acc.ispec_providers.put(s,role); // ispec_providers is part of Validator.Report
				}
				// repeat for ResourceTypes ...
				Map<String, ResourceType> map2 = expr.acc.res_types.get(idec_id); 
                if (map2 != null) for (String s : map2.keySet()){
                    expr.acc.res_type_providers.put(s,role); // res_type_providers is part of Validator.Report
                }
                Endpoint[] endpoints = idec.getEndpoints();
				if (endpoints != null){
					for (Endpoint endpoint : endpoints){
						STND protocol = endpoint.getProtocol();
						if (metamodel.getNominal(protocol, Role.ENDPOINT_PROTOCOL)==null){
							throw new Validator.Exception("FPV: Invalid Endpoint Protocol: "+protocol.getValue(), protocol);
						}
						ID endpoint_id = endpoint.getId();
						String $endpoint_id = endpoint_id.getValue();
						expr.acc.p_REGISTER_ID($idec_id+ID.$path_separator+$endpoint_id, meta.ENDPOINT);
					}
				}
				if (expr.acc.p_CHILD_COUNT_FOR_TOP_LEVEL_ID($idec_id)==0){
				    throw new Validator.Exception("FPV: InterfaceDeclr '"+$idec_id+"' has no operations & no endpoints", idec);
				}
			}
		}
		if (!provider_iface_declared){
			throw new Validator.Exception("FPV: no provider interface declared", expr.acc.slat);
		}
		
		// AGREEMENT TERMS
        
		AgreementTerm[] agreement_terms = expr.acc.slat.getAgreementTerms();
		if (agreement_terms != null){
			for (AgreementTerm term : agreement_terms){
				ID term_id = term.getId();
				String $term_id = term_id.getValue();
				expr.acc.p_REGISTER_ID($term_id, meta.AGREEMENT_TERM);
                ConstraintExpr precon = term.getPrecondition();
                if (precon != null){
                    x_ADD_CONTXT_EXPR(contextualised_expressions, $term_id, precon);
                }
				vdecs = term.getVariableDeclrs();
				if (vdecs != null){
					for (VariableDeclr vdec : vdecs){
						ID vdec_id = vdec.getVar();
						String $vdec_id = vdec_id.getValue();
						expr.acc.p_REGISTER_ID($term_id+ID.$path_separator+$vdec_id, meta.VARIABLE);
						x_ADD_CONTXT_EXPR(contextualised_expressions, $term_id, vdec.getExpr());
					}
				}
				Guaranteed[] g_terms = term.getGuarantees();
				if (g_terms != null){
					for (Guaranteed g_term : g_terms){
						ID g_term_id = g_term.getId();
						String $g_term_id = g_term_id.getValue();
						String $context = $term_id+ID.$path_separator+$g_term_id;
						expr.acc.p_REGISTER_ID($context, meta.STATE);
						if (g_term instanceof Guaranteed.State){
						    Guaranteed.State g_state = (Guaranteed.State)g_term;
	                        x_ADD_CONTXT_EXPR(contextualised_expressions, $context, g_state.getState());
	                        precon = g_state.getPrecondition();
	                        if (precon != null){
	                            x_ADD_CONTXT_EXPR(contextualised_expressions, $context, precon);
	                        }
						}else if (g_term instanceof Guaranteed.Action){
						    Guaranteed.Action g_action = (Guaranteed.Action)g_term;
						    ID actorRef = g_action.getActorRef();
						    expr.acc.p_GET_REGISTERED_ROLE_OF_PARTY_REF(actorRef, false); // throws ACC: exceptions if invalid reference
			                STND policy = g_action.getPolicy(); // guaranteed
			                if (metamodel.getNominal(policy, Role.ACTION_POLICY)==null){
			                    throw new Validator.Exception("FPV: Invalid Acion Policy: "+policy.getValue(), policy);
			                }
                            x_ADD_CONTXT_EXPR(contextualised_expressions, $context, g_action.getPrecondition());
                            Defn defn = g_action.getPostcondition();
                            Warning[] warnings = defn.validate(expr);
                            if (warnings != null){
                                for (Warning w : warnings){
                                    expr.acc.warnings.add(w);
                                }
                            }
						}else{
						    throw new Validator.Exception("FPV: unknown Guaranteed class: "+g_term.getClass().getName(), g_term);
						}
					}
				}
			}
		}
		for (Expr e : expressions){
			expr.validateExpression(e, null);
		}
		for (String context : contextualised_expressions.keySet()){
			List<Expr> list = contextualised_expressions.get(context);
			for (Expr e : list){
				expr.validateExpression(e, context);
			}
		}

	}
	
	private void x_ADD_CONTXT_EXPR(Map<String,List<Expr>> expr_map, String context, Expr expr){
        List<Expr> list = expr_map.get(context);
        if (list == null){
            list = new ArrayList<Expr>();
            expr_map.put(context, list);
        }
        list.add(expr);
	}
    

}
