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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.Interface.Specification;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.Party.Operative;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.sla;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.vocab.ext.Nominal;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

final class _acc extends Validator.Report{
	
	private static final long serialVersionUID = 1L;

	Map<STND,List<Party>> roles 
    	= Collections.synchronizedMap(new HashMap<STND,List<Party>>());
	private MetaModel ext = null; 
	private Map<String,Entity> level_0 
		= Collections.synchronizedMap(new HashMap<String,Entity>());
	private Map<String,Map<String,Entity>> level_1 
		= Collections.synchronizedMap(new HashMap<String,Map<String,Entity>>());
	private Map<String,Map<String,Map<String,Entity>>> level_2 
		= Collections.synchronizedMap(new HashMap<String,Map<String,Map<String,Entity>>>());
	private Map<String,Category> vdec_params 
		= Collections.synchronizedMap(new HashMap<String,Category>()); 
	
	_acc(MetaModel ext, SLATemplate slat){
		this.ext = ext;
		this.slat = slat;
	}
	
	void p_REGISTER_ISPEC(String idec_id, Specification spec){
		Map<String,Specification> map = ispecs.get(idec_id);
		if (map == null){
			map = Collections.synchronizedMap(new HashMap<String, Specification>());
			ispecs.put(idec_id, map);
		}
		map.put(spec.getName(), spec);
	}
	
	Specification p_GET_REGISTERED_ISPEC(String name){
		for (String id : ispecs.keySet()){
			Map<String,Specification> map = ispecs.get(id);
			if (map != null){
				Specification spec = map.get(name);
				if (spec != null) return spec;
			}
		}
		return null;
	}
    
    void p_REGISTER_RES_TYPE(String idec_id, ResourceType rt){
        Map<String,ResourceType> map = res_types.get(idec_id);
        if (map == null){
            map = Collections.synchronizedMap(new HashMap<String, ResourceType>());
            res_types.put(idec_id, map);
        }
        map.put(rt.getName(), rt);
    }
    /*
    ResourceType p_GET_REGISTERED_RES_TYPE(String name){
        for (String id : res_types.keySet()){
            Map<String,ResourceType> map = res_types.get(id);
            if (map != null){
                ResourceType rt = map.get(name);
                if (rt != null) return rt;
            }
        }
        return null;
    }
    */

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Variable Declrs
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	VariableDeclr p_GET_VDEC(String $vdec_id, String context){
	    //System.out.println("looking for var '"+$vdec_id+"' in context '"+context+"'");
	    if (context != null){
            String[] xx = $vdec_id.split(ID.$path_separator);
            String $v = xx[xx.length-1];
	        String[] ss = context.split(ID.$path_separator);
	        if (ss.length>0){
	            //System.out.println("looking for agreement term '"+ss[0]+"'");
	            AgreementTerm at = slat.getAgreementTerm(ss[0]);
	            if (at != null){
	                //System.out.println("found agreement term '"+ss[0]+"' , looking for var '"+$v+"'");
	                VariableDeclr vd = at.getVariableDeclr($v);
	                if (vd != null){
	                    //System.out.println("found: "+$vdec_id+" in AgreementTerm");
	                    return vd;
	                }
	            }
	        }
	    }
        VariableDeclr vd = slat.getVariableDeclr($vdec_id);
        if (vd != null){
            //System.out.println("found: "+$vdec_id+" in SLAT");
            return vd;
        }
		return null;
	}

	void p_SET_VDEC_PARAM(String $vdec_id, Category param){ vdec_params.put($vdec_id, param); }
	Category p_GET_VDEC_PARAM(String $vdec_id){ return vdec_params.get($vdec_id); }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Entity Identifiers
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    void p_REGISTER_ID(String id, Entity ent) throws Validator.Exception{
		String[] ss = id.split(ID.$path_separator);
		switch (ss.length){
		case 1:
			if (level_0.get(ss[0]) != null){
				throw new Validator.Exception("ACC: Duplicate ID: "+ss[0], id);
			}
			level_0.put(ss[0], ent);
			level_1.put(ss[0], Collections.synchronizedMap(new HashMap<String,Entity>()));
			level_2.put(ss[0], Collections.synchronizedMap(new HashMap<String,Map<String,Entity>>()));
			break;
		case 2:
			if (level_0.get(ss[0]) == null) throw new Validator.Exception("ACC: Invalid ID: "+ss[0]+"(/"+ss[1]+")", id);
			Map<String,Entity> map = level_1.get(ss[0]);
			if (map == null){
				map = Collections.synchronizedMap(new HashMap<String,Entity>());
				level_1.put(ss[0], map);
			}
			if (map.get(ss[1]) != null) throw new Validator.Exception("ACC: Duplicate ID: ("+ss[0]+"/)"+ss[1], id);
			map.put(ss[1], ent);
			break;
		case 3:
			if (level_0.get(ss[0]) == null) throw new Validator.Exception("ACC: Invalid ID: "+ss[0]+"(/"+ss[1]+"/"+ss[2]+")", id);
			Map<String,Entity> map3 = level_1.get(ss[0]);
			if (map3.get(ss[1]) == null) throw new Validator.Exception("ACC: Invalid ID: ("+ss[0]+"/)"+ss[1]+"(/"+ss[2]+")", id);
			Map<String,Map<String,Entity>> map1 = level_2.get(ss[0]);
			if (map1 == null){
				map1 = Collections.synchronizedMap(new HashMap<String,Map<String,Entity>>());
				level_2.put(ss[0], map1);
			}
			map3 = map1.get(ss[1]);
			if (map3 == null){
				map3 = Collections.synchronizedMap(new HashMap<String,Entity>());
				map1.put(ss[1], map3);
			}
			if (map3.get(ss[2]) != null) throw new Validator.Exception("ACC: Duplicate ID: ("+ss[0]+"/"+ss[1]+"/)"+ss[2], id);
			map3.put(ss[2], ent);
			break;
		default: throw new Validator.Exception("ACC: Invalid PATH: "+id, id);
		}
		// System.out.println("registered ID '"+id+"' for entity "+ent.toString());
	}
	
	Entity p_ENT_FOR_ID(String id, String context){
		//System.out.println("p_ENT_FOR_ID  id="+id+"  context="+context);
		String[] ss = id.split(ID.$path_separator);
		Entity e = x_ENT_FOR_ID(ss);
		if (e == null && context != null){ // only if it can't find the variable given by the id
			String[] cs = context.split(ID.$path_separator);
			String[] xx = new String[cs.length+ss.length];
			int i = 0;
			for (int j=0; j<cs.length; j++) xx[i++] = cs[j];
			for (int j=0; j<ss.length; j++) xx[i++] = ss[j];
			if (xx.length>0){
			    AgreementTerm at = slat.getAgreementTerm(xx[0]);
			    if (at != null) xx = new String[]{ xx[0],id };
			}
			e = x_ENT_FOR_ID(xx);
		}
        //System.out.println("p_ENT_FOR_ID --> entity " + (e != null? e.term: "null"));
		return e;
	}
    
    private Entity x_ENT_FOR_ID(String[] ss){
        switch (ss.length){
        case 1:
            //System.out.println("p_ENT_FOR_ID  case1");
            Entity e = level_0.get(ss[0]);
            if (e != null){
                //System.out.println("ID matched for key " + ss[0] + " --> entity " + e.term);
                return e;
            }
            break;
        case 2:
            //System.out.println("p_ENT_FOR_ID  case2");
            Map<String,Entity> map = level_1.get(ss[0]);
            e = map != null? map.get(ss[1]): null;
            if (e != null) return e;
            break;
        case 3:
            //System.out.println("p_ENT_FOR_ID  case3");
            Map<String,Map<String,Entity>> map1 = level_2.get(ss[0]);
            if (map1 != null){
                Map<String,Entity> map2 = map1.get(ss[1]);
                if (map2 != null){
                    Entity ent = map2.get(ss[2]);
                    if (ent != null) return ent;
                }
            }
            // try the default properties ..
            String $pid = ss[ss.length-1];
            Nominal n = ext.getNominal(new ID($pid), Role.OPERATION_PROPERTY);
            if (n != null){
                String $parent = "";
                for (int i=0; i<ss.length-1; i++){
                    if (i>0) $parent += ID.$path_separator;
                    $parent += ss[i];
                }
                //System.out.println("--> parentXX = "+$parent);
                Entity parent = p_ENT_FOR_ID($parent, null);
                if (parent != null && parent.isa(meta.OPERATION)) return meta.OPERATION;
            }
        }
        // try the SLA attributes ..
        return x_ENT_FOR_SLAT_ATTR(ss);
    }
	
	private Entity x_ENT_FOR_SLAT_ATTR(String[] ss){
        String $pid = ss[ss.length-1];
        //System.out.println("x_ENT_FOR_SLAT_ATTR : "+$pid);
	    Nominal n = ext.getNominal(new ID($pid), Role.SLAT_ATTRIBUTE);
        if (n != null){
            // System.out.println("found: "+n.toString());
            switch (ss.length){
               case 1: // must be a top-level attribute (no context)
                   if ($pid.equals(sla.$sla_agreedAt)
                      || $pid.equals(sla.$sla_effectiveFrom)
                      || $pid.equals(sla.$sla_effectiveUntil)) return meta.ATRIBUTE;
                    break;
                default:
                    String $parent = "";
                    for (int i=0; i<ss.length-1; i++){
                        if (i>0) $parent += ID.$path_separator;
                        $parent += ss[i];
                    }
                    Entity parent = p_ENT_FOR_ID($parent, null);
                    //System.out.println("--> parent = "+$parent+" --> "+(parent != null? parent.toString(): "null"));
                    if (parent != null){
                        if (parent.isa(meta.ENDPOINT)){
                            if ($pid.equals(sla.$endpoint_location)) return meta.ATRIBUTE;
                        }
                    }
                }
        }
        return null;
	}
	
	// returns true if service provider
	
	STND p_GET_REGISTERED_ROLE_OF_PARTY_REF(ID ref, boolean idec) throws Validator.Exception{
		String $providerRef = ref.getValue();
		if (ext.getNominal(ref, Role.AGREEMENT_ROLE) != null){ // provider identified by role ...
		    String $A = idec? "ProviderRef": "ActorRef";
            String $B = idec? "InterfaceDeclr": "Guaranteed.Action";
            STND stnd_ref = new STND($providerRef);
    		List<Party> ps = roles.get(stnd_ref); // ref may not have been implemented as a STND !!!!
    		if (ps == null || ps.isEmpty()){
    			throw new Validator.Exception("ACC: Invalid "+$A+" in "+$B+" (no party defined for role: "+ref.toString()+")", ref);
    		}else if (ps.size() > 1){
    			throw new Validator.Exception("ACC: Ambiguous "+$A+" in "+$B+" (multiple parties defined for role: "+ref.toString()+")", ref);
    		}
    		if (p_ENT_FOR_ID($providerRef, "") != null) p_REGISTER_ID($providerRef, meta.PARTY);
    		return ps.get(0).getAgreementRole();
    	}else{ // provider identified by party id ..
    	    //System.out.println("looking for party/operative with id = "+$providerRef);
    	    Entity e = p_ENT_FOR_ID($providerRef, null);
    	    if (e != null){
                //System.out.println("found entity = "+e.term.getValue());
                Party[] ps = slat.getParties();
                if (e.term.equals(meta.PARTY.term)){
                    //System.out.println("checking parties ..");
                    for (Party p : ps){
                        if (p.getId().equals(ref)){
                            //System.out.println("found party ..");
                            return p.getAgreementRole();
                        }
                    }
                }else if (e.term.equals(meta.OPERATIVE.term)){
                    //System.out.println("checking operatives ..");
                    String[] ss = $providerRef.split(ID.$path_separator);
                    if (ss.length == 1){
                        for (Party p : ps){
                            Operative op = p.getOperative(ss[0]);
                            if (op != null){
                                //System.out.println("found operative (1) ..");
                                return p.getAgreementRole();
                            }
                        }
                    }else if (ss.length == 2){
                        for (Party p : ps){
                            if (p.getId().equals(ss[0])){
                                Operative op = p.getOperative(ss[1]);
                                if (op != null){
                                    //System.out.println("found operative (2) ..");
                                    return p.getAgreementRole();
                                }
                            }
                        }
                    }
                }
    	    }
    	}
        throw new Validator.Exception("ACC: invalid party reference: "+ref.getValue(), ref);
	}
	
	int p_CHILD_COUNT_FOR_TOP_LEVEL_ID(String id){
		Map<String,Entity> map = level_1.get(id);
		return map!=null? map.size(): 0;
	}

}
