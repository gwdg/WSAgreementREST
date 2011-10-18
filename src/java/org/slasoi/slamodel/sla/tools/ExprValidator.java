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

import java.util.ArrayList;
import java.util.List;

import org.slasoi.slamodel.core.*;
import org.slasoi.slamodel.primitives.*;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.sla.Customisable;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.vocab.core;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.*;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public final class ExprValidator {

    _acc acc = null;
	MetaModel ext = null;
	
	ExprValidator(MetaModel ext_definitions, _acc acc){
		this.ext = ext_definitions;
		this.acc = acc;
	}
	
	public SLATemplate getSLATemplate(){
	    return acc.slat;
	}
	
	public MetaModel metaModel(){
	    return ext;
	}
	
	public Category validateExpression(Expr e, String context) throws Validator.Exception{
		if (e instanceof ConstraintExpr){
			x_CONSTRAINT_EXPR((ConstraintExpr)e, context);
			return meta.CONSTRAINT;
		}else if (e instanceof ValueExpr) return x_VALUE_EXPR_CAT((ValueExpr)e, context);
		else throw new Validator.Exception("EXPR: unknown Expr class: "+e.getClass().getName(), e);
	}
	
	//--- CONSTRAINT -----------------------------------------------------------------
	
	private Category x_CONSTRAINT_EXPR(ConstraintExpr ce, String context) throws Validator.Exception{
		if (ce instanceof TypeConstraintExpr) return x_TYPE_CONSTRAINT_EXPR((TypeConstraintExpr)ce, context);
		else if (ce instanceof CompoundConstraintExpr) return x_COMPOUND_CONSTRAINT_EXPR((CompoundConstraintExpr)ce, context);
        else if (ce instanceof CID) return x_ID_CAT((CID)ce, context);
		else throw new Validator.Exception("EXPR: unknown ConstraintExpr class: "+ce.getClass().getName(), ce);
	}
	
	//--- TYPE CONSTRAINT -----------------------------------------------------------------
	
	private Category x_TYPE_CONSTRAINT_EXPR(TypeConstraintExpr tce, String context) throws Validator.Exception{
	    Category c1 = x_VALUE_EXPR_CAT(tce.getValue(), context);
        Category c2 = x_DOMAIN_EXPR_CAT(tce.getDomain(), context);
        if (!(c1.equals(c2) || c1.isa(c2) || c2.isa(c1))){
            throw new Validator.Exception("EXPR: conflicting types in expression: "+tce.toString(), tce);
        }
        return c1;
	}
	
	//--- COMPOUND CONSTRAINT -----------------------------------------------------------------
	
	private Category x_COMPOUND_CONSTRAINT_EXPR(CompoundConstraintExpr cce, String context) throws Validator.Exception{
		x_LOGICAL_OP(cce);
		ConstraintExpr[] sub = cce.getSubExpressions();
        Category type = null;
        for (ConstraintExpr ce : sub) x_CONSTRAINT_EXPR(ce, context);
        return type;
	}
	
	//--- DOMAIN -----------------------------------------------------------------
	
	private Category x_DOMAIN_EXPR_CAT(DomainExpr de, String context) throws Validator.Exception{
		if (de instanceof SimpleDomainExpr) return x_SIMPLE_DOMAIN_EXPR_CAT((SimpleDomainExpr)de, context);
		else if (de instanceof CompoundDomainExpr) return x_COMPOUND_DOMAIN_EXPR_CAT((CompoundDomainExpr)de, context);
		else throw new Validator.Exception("EXPR: unknown DomainExpr class: "+de.getClass().getName(), de);
	}
	
	//--- SIMPLE DOMAIN -----------------------------------------------------------------
	
	private Category x_SIMPLE_DOMAIN_EXPR_CAT(SimpleDomainExpr sde, /*_acc acc,*/ String context) throws Validator.Exception{
		STND op = sde.getComparisonOp();
		if (ext.getNominal(op, Role.COMPARISON_OP) == null){
			throw new Validator.Exception("EXPR: invalid comparison operator in domain expression '"+sde.toString()+"'", op);
		}
		return x_VALUE_EXPR_CAT(sde.getValue(), context);
	}
	
	//--- COMPOUND DOMAIN -----------------------------------------------------------------
	
	private Category x_COMPOUND_DOMAIN_EXPR_CAT(CompoundDomainExpr cde, String context) throws Validator.Exception{
		x_LOGICAL_OP(cde);
		DomainExpr[] sub = cde.getSubExpressions();
        Category type = null;
		for (DomainExpr de : sub){
			Category t = x_DOMAIN_EXPR_CAT(de, context);
			if (type == null) type = t;
			else if (!t.equals(type)){
			    throw new Validator.Exception("EXPR: conflicting Types in compound domain expr: "+cde.toString(), cde);
			}
		}
		return type;
	}
	
	//--- VALUE EXPRS -----------------------------------------------------------------
	
	private Category x_VALUE_EXPR_CAT(ValueExpr ve, String context) throws Validator.Exception{
	    if (ve instanceof DomainExpr) return x_DOMAIN_EXPR_CAT((DomainExpr)ve, context);
        else if (ve instanceof FunctionalExpr) return x_FUNC_EXPR_CAT((FunctionalExpr)ve, context);
		else if (ve instanceof EventExpr) return x_EVENT_EXPR_CAT((EventExpr)ve, context);
		else if (ve instanceof ServiceRef) return x_SERVICE_REF_CAT((ServiceRef)ve, context);
		else if (ve instanceof STND) return meta.STND;
        else if (ve instanceof UUID) return meta.UUID;
		else if (ve instanceof ID) return x_ID_CAT((ID)ve, context);
		else if (ve instanceof BOOL) return meta.BOOLEAN;
		else if (ve instanceof TIME) return meta.TIME_STAMP;
		else if (ve instanceof LIST){
			LIST list = (LIST)ve;
			if (list.isEmpty()){
				throw new Validator.Exception("EXPR: list is empty: "+ve.toString(), ve);
			}
			Category p = null;
			for (ValueExpr x : list){
				Category q = x_VALUE_EXPR_CAT(x, context);
				if (q == null) throw new IllegalStateException("EXPR: IllegalStateException (1)");
				if (p == null) p = q;
				else if (!q.equals(p)){
					throw new Validator.Exception("EXPR: list contains conflicting types: "+ve.toString(), ve);
				}
			}
			if (p != null) return new Set(p);
		}else if (ve instanceof CONST) return x_CONST_CAT((CONST)ve);
		throw new Validator.Exception("EXPR: unable to determine type for: "+ve.toString(), ve);
	}
    
    //--- IDS -----------------------------------------------------------------
	
	public Entity entityForId(String id, String context){
	    return acc.p_ENT_FOR_ID(id, context);
	}
    
    private Category x_ID_CAT(ID id, String context) throws Validator.Exception{
        String $id = id.getValue();
        Entity ent = entityForId($id, context);
        // System.out.println("--> ENT:"+(ent != null? ent.toString(): "null"));
        // System.out.println("EXPR: id cat ? "+$id);
        if (ent != null){
            if (ent.isa(meta.ATRIBUTE)){
                String[] ss = $id.split(ID.$path_separator);
                ID pid = new ID(ss[ss.length-1]);
                Nominal n = ext.getNominal(pid, Role.SLAT_ATTRIBUTE);
                if (n != null) return n.category();
                throw new Validator.Exception("EXPR: invalid SLAT Attribute: "+$id, id);
            }else if (ent.isa(meta.AGENT)) return meta.AGENT;
            else if (ent.isa(meta.INTERFACE)) return meta.INTERFACE;
            else if (ent.isa(meta.OPERATION)) return meta.OPERATION;
            else if (ent.isa(meta.ENDPOINT)) return meta.ENDPOINT;
            else if (ent.isa(meta.PROPERTY)){
                String[] ss = $id.split(ID.$path_separator);
                ID pid = new ID(ss[ss.length-1]);
                Nominal n = ext.getNominal(pid, Role.OPERATION_PROPERTY);
                if (n != null) return n.category();
                else return meta.PROPERTY; // will be an interface specific input/output/related property
            }else if (ent.isa(meta.VARIABLE)){
                // System.out.println("EXPR: variable ? "+$id);
                Category p = acc.p_GET_VDEC_PARAM($id);
                if (p == null){
                    VariableDeclr vdec = acc.p_GET_VDEC($id, context);
                    if (vdec == null){
                        throw new Validator.Exception("EXPR: unrecognised variable: "+$id, id);
                    }
                    if (vdec instanceof Customisable){
                        // THIS IS DE TO AN MISTAKE IN THE Y2 SLA-MODEL
                        // THE EXPR SHOULD BE THE CONSTANT - FIXED IN Y3
                        p = x_VALUE_EXPR_CAT(((Customisable)vdec).getValue(), context);
                    }else{
                        p = validateExpression(vdec.getExpr(), context);
                    }
                    acc.p_SET_VDEC_PARAM($id, p);
                }
                // System.out.println("Category of variable '"+$id+"' resolved to '"+p.toString()+"'");
                return p;
            }else if (ent.isa(meta.STATE)) return meta.STATE; 
        }
        throw new Validator.Exception("EXPR: invalid ID: "+$id, id);
    }

	//--- FUNCTIONAL EXPRS -----------------------------------------------------------------
	// returns the FE "result type"
	private Category x_FUNC_EXPR_CAT(FunctionalExpr fe, String context) throws Validator.Exception{
		STND op = fe.getOperator();
		ValueExpr[] params = fe.getParameters();
		List<Category> cs = new ArrayList<Category>();
		for (ValueExpr p : params){
			Category c = null;
			if (p instanceof DomainExpr) {
				c = meta.DOMAIN;
			} else {
				c = x_VALUE_EXPR_CAT(p, context);
			}
		    if (c == null){
                throw new Validator.Exception("EXPR: can't determine type of param: "+p.toString(), p); 
            }
		    cs.add(c);
		}
		Functional func = ext.getMetric(op, cs.toArray(new Category[0]));
		if (func != null){
            return func.category();
		}
		String s = op.getValue() + "(";
		for (int i=0; i<cs.size(); i++){
		    if (i>0) s += ",";
		    s += cs.get(i).toString();
		}
		s += ")";
		throw new Validator.Exception("EXPR: unrecognised function: "+ s + " " + fe, fe);
	}
	
	//--- EVENT EXPRS -----------------------------------------------------------------
	
	private Category x_EVENT_EXPR_CAT(EventExpr ee, String context) throws Validator.Exception{
        STND op = ee.getOperator();
        Expr[] params = ee.getParameters();
        List<Category> cs = new ArrayList<Category>();
        for (Expr e : params){
            Category c = validateExpression(e, context);
            if (c == null){
                throw new Validator.Exception("EXPR: can't determine type of param: "+e.toString(), e); 
            }
            cs.add(c);
        }
        Functional ev = ext.getEvent(op, cs.toArray(new Category[0]));
        if (ev != null){
            return ev.category();
        }
        String s = op.getValue() + "(";
        for (int i=0; i<cs.size(); i++){
            if (i>0) s += ",";
            s += cs.get(i).toString();
        }
        s += ")";
        throw new Validator.Exception("EXPR: unrecognised event: "+s, ee);
	}
	
	//--- SERVICE REFS -----------------------------------------------------------------
	
	private Category x_SERVICE_REF_CAT(ServiceRef sr, String context) throws Validator.Exception{
		ID[] ids = sr.getInterfaceDeclrIds();
		if (ids != null) for (ID id : ids){
			Category p = x_VALUE_EXPR_CAT(id, null); // context must be null (i.e. ID path must be full)
			if (!p.equals(meta.INTERFACE)){
				throw new Validator.Exception("EXPR: invalid interface ID '"+id.getValue()+"' in ServiceRef:"+sr.toString(), id);
			}
		}
		ids = sr.getOperationIds();
		if (ids != null) for (ID id : ids){
			Category p = x_VALUE_EXPR_CAT(id, null); // context must be null (i.e. ID path must be full)
			if (!p.equals(meta.OPERATION)){
				throw new Validator.Exception("EXPR: invalid operation ID '"+id.getValue()+"' in ServiceRef:"+sr.toString(), id);
			}
		}
		ids = sr.getEndpointIds();
		if (ids != null) for (ID id : ids){
			Category p = x_VALUE_EXPR_CAT(id, null); // context must be null (i.e. ID path must be full)
			if (!p.equals(meta.ENDPOINT)){
				throw new Validator.Exception("EXPR: invalid endpoint ID '"+id.getValue()+"' in ServiceRef:"+sr.toString(), id);
			}
		}
		return meta.SERVICE;
	}
	
	//--- CONST -----------------------------------------------------------------
	
	private Category x_CONST_CAT(CONST c) throws Validator.Exception{
		STND dt = c.getDatatype();
		String $val = c.getValue();
		if (dt != null){
			Nominal nom = ext.getNominal(dt, Role.DATATYPE);
			if (nom != null){
			    Category cat = nom.category();
			    if (cat.isa(meta.DAY)){
			        int i = x_IS_INTEGER($val)? Integer.parseInt($val): ext.dayStringToDayInt($val);
			        if (i < 0 || i > 6){
                        throw new Validator.Exception("EXPR: invalid 'day' CONST: "+c.toString(), c);
			        }
			    }else if (cat.isa(meta.MONTH)){
                    int i = x_IS_INTEGER($val)? Integer.parseInt($val): ext.monthStringToMonthInt($val);
                    if (i < 0 || i > 11){
                        throw new Validator.Exception("EXPR: invalid 'month' CONST: "+c.toString(), c);
                    }
                }else if (cat.isa(meta.COUNT) && !x_IS_INTEGER($val)){
		            throw new Validator.Exception("EXPR: can't parse integer CONST: "+c.toString(), c);
				}else if (nom.category().isa(meta.NUMBER) && !x_IS_NUMBER($val)){
			        throw new Validator.Exception("EXPR: can't parse numeric CONST: "+c.toString(), c);
			    }
			    return nom.category();
			}else if (x_IS_BOOLEAN($val)) return meta.BOOLEAN;	// **********************************
 			throw new Validator.Exception("EXPR: unrecognised CONST datatype: "+c.toString(), dt);
		}else if (x_IS_INTEGER($val)) return meta.COUNT;
		else if (x_IS_NUMBER($val)) return meta.QUANTITY;
        else if (ext.dayStringToDayInt($val) >= 0) return meta.DAY;
        else if (ext.monthStringToMonthInt($val) >= 0) return meta.MONTH;
		return meta.TEXT;
	}
	
	//--- HELPER -----------------------------------------------------------------
	
	private boolean x_IS_BOOLEAN(String s){				// **********************************
		s = s.toLowerCase();
		return s.equals("true") || s.equals("false")
		|| s.equals("on") || s.equals("off")
		|| s.equals("yes") || s.equals("no")
		|| s.equals("y") || s.equals("n");
	}
	
	private boolean x_IS_INTEGER(String s){
	    try{
            Integer.parseInt(s);
            return true;
        }catch(java.lang.Exception e){}
        return false;
	}
	
	private boolean x_IS_NUMBER(String s){
	    try{
            Double.parseDouble(s);
            return true;
        }catch(java.lang.Exception e){}
        return false;
	}
    
    private void x_LOGICAL_OP(CompoundConstraintExpr cce) throws Validator.Exception{
        x_LOGICAL_OP(cce.getLogicalOp(), cce.getSubExpressions(), cce);
    }
    
    private void x_LOGICAL_OP(CompoundDomainExpr cde) throws Validator.Exception{
        x_LOGICAL_OP(cde.getLogicalOp(), cde.getSubExpressions(), cde);
    }
	
	private void x_LOGICAL_OP(STND op, Object[] args, Object src) throws Validator.Exception{
        int _op = op.equals(core.not)? 1: op.equals(core.and)? 2: op.equals(core.or)? 3: 0;
		switch (_op){
			case 1: 
				if (args.length != 1){
					throw new Validator.Exception("EXPR: too many arguments to 'not' operator (max = 1)", src);
				}
				break;
			case 2:
			case 3:
				if (args.length == 1){
					throw new Validator.Exception("EXPR: to few arguments to 'and'/'or' operator (min = 2)", src);
				}
				break;
			default:
				throw new Validator.Exception("EXPR: invalid logical operator in '"+op.getValue()+"'", src);
		}
	}

}
