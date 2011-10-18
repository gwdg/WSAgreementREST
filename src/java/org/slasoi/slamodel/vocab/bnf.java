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

package org.slasoi.slamodel.vocab;

import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.CompoundConstraintExpr;
import org.slasoi.slamodel.core.CompoundDomainExpr;
import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.core.DomainExpr;
import org.slasoi.slamodel.core.EventExpr;
import org.slasoi.slamodel.core.FunctionalExpr;
import org.slasoi.slamodel.core.SimpleDomainExpr;
import org.slasoi.slamodel.core.TypeConstraintExpr;
import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.Expr;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.TIME;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.primitives.ValueExpr;
import org.slasoi.slamodel.service.Interface;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.service.Interface.Operation.Property;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Customisable;
import org.slasoi.slamodel.sla.Endpoint;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.InterfaceRef;
import org.slasoi.slamodel.sla.Invocation;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLA;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.Party.Operative;
import org.slasoi.slamodel.sla.business.Penalty;
import org.slasoi.slamodel.sla.business.PriceModification;
import org.slasoi.slamodel.sla.business.Product;
import org.slasoi.slamodel.sla.business.ProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Termination;
import org.slasoi.slamodel.sla.business.TerminationClause;
import org.slasoi.slamodel.sla.business.ComponentProductOfferingPrice;

public class bnf{
    
    static final String $indent = "    ";
    static final String $annot_color = "'#009900'";
    static final String $sep_color = "'#00FF00'";
    static final String $attr_color = "'#0000FF'";
    static final String $postfix_color = "'#808080'";
    static final String $var_color = "'#FF00FF'";
    static final String $constraint_color = "'#FF0000'";
    static final String $domain_color = $constraint_color;
    static final String $function_color = $constraint_color;
    static final String $event_color = $constraint_color;
    
    private static String $new_line = "<br>";
	
  //----SLA TEMPLATE-------------------------------------------------------------------------------------------

    /**
     * Returns a rendering of the given SLA(T) in BNF syntax.<br/>
     * @param slat
     * @param html : if <code>true</code>, the rendering is packaged as an HTML page with 'pretty' formatting.  
     * @return
     */
    public static String render(SLATemplate slat, boolean html){
        $new_line = html? "<br>": "\n";
		if (slat == null) throw new IllegalArgumentException("No SLATemplate specified");
        StringBuffer buf = new StringBuffer();
        if (html){
        	buf.append("<html>");
        	buf.append($new_line);
        	buf.append("<head></head>");
        	buf.append($new_line);
        	buf.append("<body>");
        	buf.append($new_line);
        	buf.append("<pre>");
        	buf.append($new_line);
        }
		if (slat instanceof SLA){
		    buf.append(x_SLA((SLA)slat, html));
		}else{
		    buf.append($new_line);
		    buf.append(html? "<b>sla_template</b>{": "sla_template{");
	        buf.append(x_SLAT_CONTENT(slat, html));
		    buf.append($new_line);
	        buf.append("}");
		}
        if (html){
        	buf.append("</pre>");
		    buf.append($new_line);
        	buf.append("</body>");
		    buf.append($new_line);
        	buf.append("</html>");
        }
        return buf.toString();
	}
	
  //----PUBLIC-------------------------------------------------------------------------------------------
    
    /**
     * Returns a rendering of the given Party in BNF syntax.
     * @param p
     * @return
     */
    public static String render(Party p){ return x_RENDER(p, "", false); }
	/**
	 * Returns a rendering of the given Party.Operative in BNF syntax.
	 * @param op
	 * @return
	 */
    public static String render(Party.Operative op){ return x_RENDER(op, "", false); }
    /**
     * Returns a rendering of the given InterfaceDeclr in BNF syntax.
     * @param idec
     * @return
     */
    public static String render(InterfaceDeclr idec){ return x_RENDER(idec, "", false); }
    /**
     * Returns a rendering of the given InterfaceRef in BNF syntax.
     * @param iref
     * @return
     */
    public static String render(InterfaceRef iref){ return x_RENDER(iref, "", false); }
    /**
     * Returns a rendering of the given Interface.Specification in BNF syntax.
     * @param ispec
     * @return
     */
    public static String render(Interface.Specification ispec){ return x_RENDER(ispec, "", false); }
    /**
     * Returns a rendering of the given ResourceType in BNF syntax.
     * @param ispec
     * @return
     */
    public static String render(ResourceType ires){ return x_RENDER(ires, "", false); }
    /**
     * Returns a rendering of the given Interface.Specification.Operation in BNF syntax.
     * @param op
     * @return
     */
    public static String render(Interface.Specification.Operation op){ return x_RENDER(op, "", false); }
    /**
     * Returns a rendering of the given Interface.Specification.Operation.Property in BNF syntax.
     * @param p
     * @return
     */
    public static String render(Interface.Specification.Operation.Property p){ return x_RENDER(p, "", "?property?", false); }
    /**
     * Returns a rendering of the given ServiceRef in BNF syntax.
     * @param sr
     * @return
     */
    public static String render(ServiceRef sr){return x_RENDER(sr, "", false); }
    /**
     * Returns a rendering of the given Endpoint in BNF syntax.
     * @param ep
     * @return
     */
    public static String render(Endpoint ep){return x_RENDER(ep, "", false); }
    /**
     * Returns a rendering of the given VariableDeclr in BNF syntax.
     * @param vdec
     * @return
     */
    public static String render(VariableDeclr vdec){return x_RENDER(vdec, "", false); }
    /**
     * Returns a rendering of the given AgreementTerm in BNF syntax.
     * @param term
     * @return
     */
    public static String render(AgreementTerm term){return x_RENDER(term, "", false); }
    /**
     * Returns a rendering of the given Guaranteed.State in BNF syntax.
     * @param gs
     * @return
     */
    public static String render(Guaranteed.State gs){return x_RENDER(gs, "", false); }
    /**
     * Returns a rendering of the given Guaranteed.Action in BNF syntax.
     * @param ga
     * @return
     */
    public static String render(Guaranteed.Action ga){return x_RENDER(ga, "", false); }
    /**
     * Returns a rendering of the given Guaranteed.Action.Defn in BNF syntax.
     * @param gad
     * @return
     */
    public static String render(Guaranteed.Action.Defn gad){return x_RENDER(gad, "", false); }
    
  //----ANNOTATION-------------------------------------------------------------------------------------------
    
    private static String x_ANNOT(Annotated a, String indent, boolean html){
		if (a == null) throw new IllegalArgumentException("No Annotation specified");
        StringBuffer buf = new StringBuffer();
        String descr = a.getDescr();
        STND[] keys = a.getPropertyKeys();
        if (descr != null || keys.length > 0){
            if (html) buf.append("<font color="+$annot_color+">");
            if (descr != null){
                String s = descr;
                if (html) s = s.replaceAll("<","&lt;").replaceAll(">","&gt;");
                while (s.length() > 75){
                    buf.append(indent);
                    buf.append("// ");
                    buf.append(s.substring(0,50));
                    buf.append($new_line);
                    s = s.substring(50);
                };
                buf.append(indent);
                buf.append("// ");
                buf.append(s);
            }
            for (STND key : keys){
		   	 	buf.append($new_line);
                buf.append(indent);
                buf.append("// ");
                buf.append(key.toString());
                buf.append(" = ");
                String v = a.getPropertyValue(key);
                if (html) v = v.replaceAll("<","&lt;").replaceAll(">","&gt;");
                buf.append(v);
            }
            if (html) buf.append("</font>");
		    buf.append($new_line);
        }
        return buf.toString();
    }
	
  //----SLA-------------------------------------------------------------------------------------------
    
    private static String x_SLA(SLA sla, boolean html){
		if (sla == null) throw new IllegalArgumentException("No SLA specified");
	    StringBuffer buf = new StringBuffer();
	    buf.append(x_ANNOT(sla,"", html));
	    buf.append(html? "<b>sla</b>{": "sla{");
		buf.append($new_line);
	    TIME t = sla.getAgreedAt();
	    buf.append($indent);
	    buf.append("agreedAt = ");
	    if (html) buf.append("<font color="+$attr_color+">");
	    buf.append(t != null? t.toString(): "<undefined>");
	    if (html) buf.append("</font>");
	    t = sla.getEffectiveFrom();
		buf.append($new_line);
        buf.append($indent);
        buf.append("effectiveFrom = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(t != null? t.toString(): "<undefined>");
        if (html) buf.append("</font>");
	    t = sla.getEffectiveUntil();
        buf.append($new_line);
        buf.append($indent);
        buf.append("effectiveUntil = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(t != null? t.toString(): "<undefined>");
        if (html) buf.append("</font>");
        UUID tid = sla.getTemplateId();
		buf.append($new_line);
        buf.append($indent);
        buf.append("templateId = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tid != null? tid.toString(): "<undefined>");
        if (html) buf.append("</font>");
        buf.append(x_SLAT_CONTENT(sla, html));
		buf.append($new_line);
	    buf.append("}");
		buf.append($new_line);
	    return buf.toString();
	}

	//----SLAT CONTENT-------------------------------------------------------------------------------------------
    
    private static String x_SLAT_CONTENT(SLATemplate slat, boolean html){
		if (slat == null) throw new IllegalArgumentException("No SLATemplate specified");
        StringBuffer buf = new StringBuffer();
		buf.append($new_line);
        buf.append($indent);
        buf.append("uuid = ");
        if (html) buf.append("<font color="+$attr_color+">");
        UUID uuid = slat.getUuid();
        buf.append(uuid != null? uuid.toString(): "<undefined>");
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append($indent);
        buf.append("sla_model_version = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(SLATemplate.$model_version);
        if (html) buf.append("</font>");
        buf.append($new_line);
        if (html) buf.append("<font color="+$sep_color+">");
		buf.append($new_line);
    	buf.append("/* ---- PARTY DESCRIPTIONS ------------------------------------------------- */");
		buf.append($new_line);
        if (html) buf.append("</font>");
        Party[] ps = slat.getParties();
        if (ps != null) for (Party p : ps){
			buf.append($new_line);
        	buf.append(x_RENDER(p, $indent, html));
        }
        if (html) buf.append("<font color="+$sep_color+">");
		buf.append($new_line);
        buf.append("/* ---- INTERFACE DECLARATIONS------------------------------------------------- */");
		buf.append($new_line);
        if (html) buf.append("</font>");
        InterfaceDeclr[] idecs = slat.getInterfaceDeclrs();
        if (idecs != null) for (InterfaceDeclr i : idecs){ 
			buf.append($new_line);
        	buf.append(x_RENDER(i, $indent, html));
			buf.append($new_line);
        }
        if (html) buf.append("<font color="+$sep_color+">");
		buf.append($new_line);
        buf.append("/* ---- VARIABLE DECLARATIONS------------------------------------------------- */");
		buf.append($new_line);
        if (html) buf.append("</font>");
        VariableDeclr[] vdecs = slat.getVariableDeclrs();
        if (vdecs != null) for (VariableDeclr v : vdecs){
			buf.append($new_line);
        	buf.append(x_RENDER(v, $indent, html));
			buf.append($new_line);
        }
        if (html) buf.append("<font color="+$sep_color+">");
		buf.append($new_line);
        buf.append("/* ---- AGREEMENT TERMS------------------------------------------------- */");
		buf.append($new_line);
        if (html) buf.append("</font>");
        AgreementTerm[] terms = slat.getAgreementTerms();
        if (terms != null) for (AgreementTerm t : terms){
			buf.append($new_line);
        	buf.append(x_RENDER(t, $indent, html));
			buf.append($new_line);
        }
        return buf.toString();
    }
	
	//----PARTY-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Party p, String indent, boolean html){
		if (p == null) throw new IllegalArgumentException("No Party specified");
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(p,indent, html));
        buf.append(indent);
        buf.append(html? "<b>party</b>{": "party{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("role = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.getAgreementRole().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        Operative[] os = p.getOperatives();
        if (os != null) for (Operative o : os){
            buf.append(x_RENDER(o, z, html));
        }
        buf.append(indent);
        buf.append("}");
        return buf.toString();
	}
    
	//----PARTY OPERATIVE-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Party.Operative op, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(op,indent, html));
        buf.append(indent);
        buf.append(html? "<b>operative</b>{": "operative{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append("id = ");
        if (html) buf.append("</font>");
        buf.append(op.getId().toString());
		buf.append($new_line);
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }
        
	//----INTERFACE DECLR-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(InterfaceDeclr idec, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(idec,indent, html));
        buf.append(indent);
        buf.append(html? "<b>interface_declr</b>{": "interface_declr{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(idec.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("provider_ref = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(idec.getProviderRef().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        Endpoint[] eps = idec.getEndpoints();
        if (eps != null) for (Endpoint e : eps){
            buf.append(x_RENDER(e, indent+$indent, html));
        }
        Interface iface = idec.getInterface();
        if (iface instanceof InterfaceRef){
            buf.append(x_RENDER((InterfaceRef)iface, z, html));
        }else if (iface instanceof Interface.Specification){
            buf.append(x_RENDER((Interface.Specification)iface, z, html));
        }else if (iface instanceof ResourceType){
            buf.append(x_RENDER((ResourceType)iface, z, html));
        }
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }
    
  //----ENDPOINT-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Endpoint ep, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>endpoint</b>{": "endpoint{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(x_ANNOT(ep,z, html));
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(ep.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        UUID loc = ep.getLocation();
        buf.append(z);
        buf.append("location = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(loc != null? loc.toString(): "<undefined>");
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("protocol = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(ep.getProtocol().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }
    
  //----INTERFACE REF-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(InterfaceRef iref, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>interface_ref</b>( ": "interface_ref( ");
        String z = indent+$indent;
        buf.append(x_ANNOT(iref, z, html));
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(iref.getInterfaceLocation().toString());
        if (html) buf.append("</font>");
        buf.append(" )");
		buf.append($new_line);
        return buf.toString();
    }
        
  //----INTERFACE SPEC-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Interface.Specification spec, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>interface_spec</b>{": "interface_spec{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(x_ANNOT(spec, z, html));
        String name = spec.getName();
        if (name != null){
            buf.append(z);
            buf.append("name = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(name);
            if (html) buf.append("</font>");
			buf.append($new_line);
        }
        ID[] exts = spec.getExtended();
        if (exts != null && exts.length > 0 ){
            buf.append(z);
            buf.append(html? "<b>extension_list</b>{": "extension_list{");
			buf.append($new_line);
            if (html) buf.append("<font color="+$attr_color+">");
            for (ID ext : exts){
                buf.append(z);
                buf.append($indent);
                buf.append(ext.toString());
				buf.append($new_line);
            }
            if (html) buf.append("</font>");
            buf.append("}");
			buf.append($new_line);
        }
        Operation[] ops = spec.getOperations();
        if (ops != null){
        	for (Operation op : ops) buf.append(x_RENDER(op, z, html));
        }
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }
    
  //----INTERFACE RESOURCETYPE-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(ResourceType res, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>interface_resource_type</b>{": "interface_resource_type{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(x_ANNOT(res, z, html));
        String name = res.getName();
        if (name != null){
            buf.append(z);
            buf.append("name = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(name);
            if (html) buf.append("</font>");
            buf.append($new_line);
        }
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }

  //----OPERATION-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Interface.Operation op, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(op, indent, html));
        buf.append(indent);
        buf.append(html? "<b>operation</b>{": "operation{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(op.getName().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        Property[] ps = op.getInputs();
        if (ps != null){
        	for (Property p : ps) buf.append(x_RENDER(p, z, "input", html));
        }
        ps = op.getOutputs();
        if (ps != null){
        	for (Property p : ps) buf.append(x_RENDER(p, z, "output", html));
        }
        ps = op.getRelated();
        if (ps != null){
        	for (Property p : ps) buf.append(x_RENDER(p, z, "related", html));
        }
        STND[] fs = op.getFaults();
        if (fs != null && fs.length > 0){
            buf.append(z);
            buf.append(html? "<b>fault_list</b>{": "fault_list{");
			buf.append($new_line);
            if (html) buf.append("<font color="+$attr_color+">");
            for (STND f : fs){
                buf.append(z);
                buf.append($indent);
                buf.append(f.toString());
				buf.append($new_line);
            }
            if (html) buf.append("</font>");
            buf.append(z);
            buf.append("}");
			buf.append($new_line);
        }
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }

  //----OPERATION PROPERTY-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Interface.Operation.Property p, String indent, String type, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>"+type+"</b>": type);
        buf.append("{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(x_ANNOT(p, z, html));
        buf.append(z);
        buf.append("name = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.getName().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("datatype = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.getDatatype().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        DomainExpr de = p.getDomain();
        if (de != null){
            buf.append(z);
            buf.append("domain = ");
            if (html) buf.append("<font color="+$attr_color+">");
            boolean simple = de instanceof SimpleDomainExpr;
            if (simple) buf.append("(");
            buf.append(x_RENDER(de, html));
            if (simple) buf.append(")");
            if (html) buf.append("</font>");
			buf.append($new_line);
        }
        buf.append(z);
        buf.append("auxiliary = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.isAuxiliary()? "true": "false");
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }

  //----SERVICE REF------------------------------------------------------------------------------------------
    
     private static String x_RENDER(ServiceRef sr, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        buf.append(html? "<b>service_ref</b>{ ": "service_ref{ ");
        if (html) buf.append("<font color="+$attr_color+">");
        ID[] ids = sr.getInterfaceDeclrIds();
        if (ids != null) for (ID id : ids){
        	buf.append(id.toString());
        	buf.append(" ");
        }
        ids = sr.getOperationIds();
        if (ids != null) for (ID id : ids){
        	buf.append(id.toString());
        	buf.append(" ");
        }
        ids = sr.getEndpointIds();
        if (ids != null) for (ID id : ids){
        	buf.append(id.toString());
        	buf.append(" ");
        }
        if (html) buf.append("</font>");
        buf.append("}");
        return buf.toString();
    }

  //----VAR-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(VariableDeclr vdec, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(vdec, indent, html));
        buf.append(indent);
        if (html) buf.append("<font color="+$var_color+">");
        buf.append(vdec.getVar().toString());
        if (html) buf.append("</font>");
        buf.append(html? " <b>is</b> ": " is ");
        if (vdec instanceof Customisable){
        	Customisable c = (Customisable)vdec;
        	DomainExpr de = (DomainExpr)c.getExpr();
        	buf.append(x_RENDER(de, html));
            if (html) buf.append("<font color="+$postfix_color+">");
        	buf.append(html? " &lt;&lt;": " <<");
        	buf.append(c.getValue().toString());
        	buf.append(html? "&gt;&gt;": ">>");
            if (html) buf.append("</font>");
        }else{
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(vdec.getExpr().toString());
            if (html) buf.append("</font>");
        }
        return buf.toString();
    }

  //----AGREEMENT TERM-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(AgreementTerm term, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(term, indent, html));
        buf.append(indent);
        buf.append(html? "<b>agreement_term</b>{": "agreement_term{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(term.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        ConstraintExpr prec = term.getPrecondition();
        if (prec != null){
            buf.append(z);
            buf.append(html? "<b>precondition</b>{ ": "precondition{ ");
            buf.append(x_RENDER(prec, html));
            buf.append(" }");
			buf.append($new_line);
        }
        VariableDeclr[] vdecs = term.getVariableDeclrs();
        if (vdecs != null) for (VariableDeclr v : vdecs){
        	buf.append(x_RENDER(v, z, html));
        	buf.append($new_line);
        }
        Guaranteed[] gs = term.getGuarantees();
        for (Guaranteed g : gs){
        	if (g instanceof Guaranteed.State)  buf.append(x_RENDER((Guaranteed.State)g, z, html));
        	else if (g instanceof Guaranteed.Action)  buf.append(x_RENDER((Guaranteed.Action)g, z, html));
        }
        buf.append(indent);
        buf.append("}");
        return buf.toString();
    }

  //----STATE-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Guaranteed.State gs, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(gs, indent, html));
        buf.append(indent);
        buf.append(html? "<b>guaranteed_state</b>{": "guaranteed_state{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(gs.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        CONST pri = gs.getPriority();
        if (pri != null){
            buf.append(z);
            buf.append("priority = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(pri.toString());
            if (html) buf.append("</font>");
			buf.append($new_line);
        }
        ConstraintExpr prec = gs.getPrecondition();
        if (prec != null){
            buf.append(z);
            buf.append(html? "<b>precondition</b>{ ": "precondition{ ");
            buf.append(x_RENDER(prec, html));
            buf.append(" }");
			buf.append($new_line);
        }
        ConstraintExpr state = gs.getState();
        buf.append(z);
        buf.append(x_RENDER(state, html));
		buf.append($new_line);
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }

  //----ACTION-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Guaranteed.Action ga, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(x_ANNOT(ga, indent, html));
        buf.append(indent);
        buf.append(html? "<b>guaranteed_action</b>{": "guaranteed_action{");
		buf.append($new_line);
        String z = indent+$indent;
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(ga.getId().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("actor = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(ga.getActorRef().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("policy = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(ga.getPolicy().toString());
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(z);
        buf.append("trigger = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(x_RENDER(ga.getPrecondition(), html));
        if (html) buf.append("</font>");
		buf.append($new_line);
        buf.append(x_RENDER(ga.getPostcondition(), z, html));
        buf.append(indent);
        buf.append("}");
		buf.append($new_line);
        return buf.toString();
    }

  //----ACTION DEFN-------------------------------------------------------------------------------------------
    
    private static String x_RENDER(Guaranteed.Action.Defn gad, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        buf.append(indent);
        String z = indent+$indent;
        if (gad instanceof Invocation){
            buf.append(x_RENDER_INVOCATION((Invocation)gad, indent, html));
        }else if (gad instanceof TerminationClause){
            buf.append(x_RENDER_TERMINATION_CLAUSE((TerminationClause)gad, indent, html));
        }else if (gad instanceof Termination){
            buf.append(x_RENDER_TERMINATION((Termination)gad, indent, html));
        }else if (gad instanceof Penalty){
            buf.append(x_RENDER_PENALTY((Penalty)gad, indent, html));
        }else if (gad instanceof ProductOfferingPrice){
            buf.append(x_RENDER_PRODUCT_OFFERING_PRICE((ProductOfferingPrice)gad, indent, html));
        }else{
            buf.append(html? "<b>custom</b>{": "custom{");
			buf.append($new_line);
            buf.append(x_ANNOT(gad, z, html));
            buf.append(indent);
            buf.append("}");
            buf.append($new_line);
        }
        return buf.toString();
    }

    //----INVOCATIONS-------------------------------------------------------------------------------------------

    private static String x_RENDER_INVOCATION(Invocation invoc, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        String z = indent+$indent;
        String z2 = z+$indent;
        buf.append(indent);
        buf.append(html? "<b>invocation</b>{": "invocation{");
        buf.append($new_line);
        buf.append(x_ANNOT(invoc, z, html));
        ID ep = invoc.getEndpointId();
        if (ep != null){
            buf.append(z);
            buf.append("endpoint = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(ep.toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
        }
        buf.append(z);
        buf.append("operation = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(invoc.getOperationId().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        ID[] pks = invoc.getParameterKeys();
        if (pks.length > 0){
            for (ID pk : pks){
                buf.append(z);
                buf.append("param{");
                buf.append($new_line);
                buf.append(z2);
                buf.append("name = ");
                if (html) buf.append("<font color="+$attr_color+">");
                buf.append(pk.toString());
                if (html) buf.append("</font>");
                buf.append($new_line);
                buf.append(z2);
                buf.append("value = \"");
                if (html) buf.append("<font color="+$attr_color+">");
                buf.append(invoc.getParameterValue(pk).toString());
                if (html) buf.append("</font>");
                buf.append("\"");
                buf.append($new_line);
                buf.append(z2);
                buf.append("}");
                buf.append($new_line);
            }
        }
        buf.append(indent);
        buf.append("}");
        buf.append($new_line);
        return buf.toString();
    }

    //----TERMINATION CLAUSE-------------------------------------------------------------------------------------------

    private static String x_RENDER_TERMINATION_CLAUSE(TerminationClause tc, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        String z = indent+$indent;
        buf.append(indent);
        buf.append(html? "<b>termination_clause</b>{": "termination_clause{");
        buf.append($new_line);
        buf.append(x_ANNOT(tc, z, html));
        
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getId().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("initiator = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getInitiator().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("type = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getType());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("clause = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getClause());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("notificationMethod = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getNotificationMethod().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("notificationPeriod = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getNotificationPeriod().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("fees = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(tc.getFees().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(indent);
        buf.append("}");
        buf.append($new_line);
        return buf.toString(); 
    }

    //----TERMINATION-------------------------------------------------------------------------------------------

    private static String x_RENDER_TERMINATION(Termination t, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        String z = indent+$indent;
        buf.append(indent);
        buf.append(html? "<b>termination</b>{": "termination{");
        buf.append($new_line);
        buf.append(x_ANNOT(t, z, html));

        buf.append(z);
        buf.append("name = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(t.getName());
        if (html) buf.append("</font>");
        buf.append($new_line);

        buf.append(z);
        buf.append("terminationClauseId = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(t.getTerminationClauseId());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(indent);
        buf.append("}");
        buf.append($new_line);
        return buf.toString(); 
    }

    //----PENALTY-------------------------------------------------------------------------------------------

    private static String x_RENDER_PENALTY(Penalty p, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        String z = indent+$indent;
        buf.append(indent);
        buf.append(html? "<b>penalty</b>{": "penalty{");
        buf.append($new_line);
        buf.append(x_ANNOT(p, z, html));

        buf.append(z);
        buf.append("price = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(p.getPrice());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(indent);
        buf.append("}");
        buf.append($new_line);
        return buf.toString(); 
    }

    //----PRODUCT OFFERING PRICE-------------------------------------------------------------------------------------------

    private static String x_RENDER_PRODUCT_OFFERING_PRICE(ProductOfferingPrice pop, String indent, boolean html){
        StringBuffer buf = new StringBuffer();
        String z = indent+$indent;
        String z2 = z+$indent;
        String z3 = z2+$indent;
        
        buf.append(indent);
        buf.append(html? "<b>productOfferingPrice</b>{": "productOfferingPrice{");
        buf.append($new_line);
        buf.append(x_ANNOT(pop, z, html));
        
        buf.append(z);
        buf.append("id = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(pop.getId().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(z);
        buf.append("name = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(pop.getName());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(z);
        buf.append("validFrom = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(pop.getValidFrom().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(z);
        buf.append("validUntil = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(pop.getValidUntil().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        buf.append(z);
        buf.append("billingFrequency = ");
        if (html) buf.append("<font color="+$attr_color+">");
        buf.append(pop.getBillingFrequency().toString());
        if (html) buf.append("</font>");
        buf.append($new_line);
        
        Product prod = pop.getProduct();
        if (prod != null){
            buf.append(z);
            buf.append(html? "<b>product</b>{": "product{");
            buf.append($new_line);
            buf.append(x_ANNOT(prod, z2, html));
            
            buf.append(z2);
            buf.append("id = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(prod.getId().toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            buf.append(z2);
            buf.append("name = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(prod.getName());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            buf.append(z);
            buf.append("}");
            buf.append($new_line);
        }
        
        ComponentProductOfferingPrice[] cpops = pop.getComponentProductOfferingPrices();
        for (ComponentProductOfferingPrice cpop : cpops){
            buf.append(z);
            buf.append(html? "<b>component_product_offering_price</b>{": "component_product_offering_price{");
            //buf.append($new_line);
            //buf.append(x_ANNOT(cpop, z2, html));
            
            buf.append(z2);
            buf.append("id = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(cpop.getId().toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            buf.append(z2);
            buf.append("priceType = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(cpop.getPriceType().toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            buf.append(z2);
            buf.append("price = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(cpop.getPrice().toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            buf.append(z2);
            buf.append("quantity = ");
            if (html) buf.append("<font color="+$attr_color+">");
            buf.append(cpop.getQuantity().toString());
            if (html) buf.append("</font>");
            buf.append($new_line);
            
            PriceModification[] pms = cpop.getPriceModifications();
            for (PriceModification pm : pms){
                buf.append(z2);
                buf.append(html? "<b>price_modification</b>{": "price_modification{");
                //buf.append($new_line);
                //buf.append(x_ANNOT(pm, z3, html));
                
                buf.append(z3);
                buf.append("type = ");
                if (html) buf.append("<font color="+$attr_color+">");
                buf.append(pm.getType().toString());
                if (html) buf.append("</font>");
                buf.append($new_line);
                
                buf.append(z3);
                buf.append("value = ");
                if (html) buf.append("<font color="+$attr_color+">");
                buf.append(pm.getValue().toString());
                if (html) buf.append("</font>");
                buf.append($new_line);
                
                buf.append(z2);
                buf.append("}");
                buf.append($new_line);
            }
            
            buf.append(z);
            buf.append("}");
            buf.append($new_line);
        }
        
        buf.append(indent);
        buf.append("}");
        buf.append($new_line);
        return buf.toString(); 
    }
    
  //----CONSTRAINT EXPR-------------------------------------------------------------------------------------------

    public static String render(ConstraintExpr ce){
        return x_RENDER(ce, false);
    }
    
    private static String x_RENDER(ConstraintExpr ce, boolean html){
        StringBuffer buf = new StringBuffer();
        if (html) buf.append("<font color="+$constraint_color+">");
        if (ce instanceof TypeConstraintExpr){
        	TypeConstraintExpr tce = (TypeConstraintExpr)ce;
        	buf.append(tce.getValue().toString());
        	buf.append(" ");
        	buf.append(x_RENDER(tce.getDomain(), html));
        	/*
        	CONST error = tce.getError();
        	if (error != null){
                if (html) buf.append("<font color="+$postfix_color+">");
        	    buf.append(html? " &lt;&lt;+/- ": " <<+/- ");
                buf.append(error.toString());
                buf.append(html? "&gt;&gt;": ">>");
                if (html) buf.append("</font>");
        	}
        	*/
        }else if (ce instanceof CompoundConstraintExpr){
        	CompoundConstraintExpr cce = (CompoundConstraintExpr)ce;
        	STND op = cce.getLogicalOp();
            int OP = op.equals(core.not)? 1: op.equals(core.and)? 2: op.equals(core.or)? 3: 0;
        	ConstraintExpr[] sub = cce.getSubExpressions();
        	switch (OP){
	        	case 1:
	        		if (sub.length == 1){
		            	buf.append("not(");
		            	buf.append(x_RENDER(sub[0], html));
	        		}else{
	        			buf.append("INVALID-COMPOUND-EXPR");
	        		}
	        		break;
	        	case 2:
	        	case 3:
	        		if (sub.length >= 2){
		        		buf.append(sub.length == 2? "(": (OP == 2? "and(": "or("));
		        		for (int i=0; i<sub.length; i++){
		        			if (i>0) buf.append(sub.length == 2? (OP == 2? " and ": " or "): ", ");
		        			buf.append(x_RENDER(sub[i], html));
		        		}
	        		}else{
	        			buf.append("INVALID-COMPOUND-EXPR");
	        		}
	        		break;
        	}
        	buf.append(")");
        }
        if (html) buf.append("</font>");
        return buf.toString();
    }

  //----DOMAIN EXPR-------------------------------------------------------------------------------------------
    
    public static String render(DomainExpr de){
        return x_RENDER(de, false);
    }

    private static String x_RENDER(DomainExpr de, boolean html){
        StringBuffer buf = new StringBuffer();
        if (html) buf.append("<font color="+$domain_color+">");
        if (de instanceof SimpleDomainExpr){
        	SimpleDomainExpr sde = (SimpleDomainExpr)de;
        	String $cop = sde.getComparisonOp().toString();
        	if (html) $cop = $cop.replaceAll("<","&lt;").replaceAll(">","&gt;");
        	buf.append($cop);
    		buf.append(" ");
            buf.append(sde.getValue().toString());
        }else if (de instanceof CompoundDomainExpr){
        	CompoundDomainExpr cde = (CompoundDomainExpr)de;
        	STND op = cde.getLogicalOp();
        	int OP = op.equals(core.not)? 1: op.equals(core.and)? 2: op.equals(core.or)? 3: 0;
        	DomainExpr[] sub = cde.getSubExpressions();
            switch (OP){
	        	case 1:
	        		if (sub.length == 1){
		            	buf.append("not(");
		            	buf.append(x_RENDER(sub[0], html));
	        		}else{
	        			buf.append("(INVALID-COMPOUND-EXPR");
	        		}
	        		break;
	        	case 2:
	        	case 3:
	        		if (sub.length >= 2){
		        		buf.append(sub.length == 2? "(": (OP == 2? "and(": "or("));
		        		for (int i=0; i<sub.length; i++){
		        			if (i>0) buf.append(sub.length == 2? (OP == 2? " and ": " or "): ", ");
		        			buf.append(x_RENDER(sub[i], html));
		        		}
	        		}else{
	        			buf.append("(INVALID-COMPOUND-EXPR");
	        		}
	        		break;
	        	default:
	        	       buf.append("(INVALID-COMPOUND-EXPR");
	        	    break;
        	}
        	buf.append(")");
        }
        if (html) buf.append("</font>");
        return buf.toString();
    }
    
  //----FUNCTIONAL EXPR-------------------------------------------------------------------------------------------

    public static String render(FunctionalExpr fe){
        return x_RENDER(fe, false);
    }
    
    private static String x_RENDER(FunctionalExpr fe, boolean html){
        StringBuffer buf = new StringBuffer();
        if (html) buf.append("<font color="+$function_color+">");
        buf.append(fe.getOperator().toString());
        buf.append("(");
        ValueExpr[] params = fe.getParameters();
        for (int i=0; i<params.length; i++){
        	if (i>0) buf.append(", ");
        	buf.append(params[i].toString());
        }
        buf.append(")");
        if (html) buf.append("</font>");
        return buf.toString();
    }
    
  //----EVENT EXPR-------------------------------------------------------------------------------------------

    public static String render(EventExpr ee){
        return x_RENDER(ee, false);
    }
    
    private static String x_RENDER(EventExpr ee, boolean html){
        StringBuffer buf = new StringBuffer();
        if (html) buf.append("<font color="+$event_color+">");
        buf.append(ee.getOperator().toString());
        buf.append("[");
        Expr[] params = ee.getParameters();
        for (int i=0; i<params.length; i++){
        	if (i>0) buf.append(", ");
        	buf.append(params[i].toString());
        }
        buf.append("]");
        if (html) buf.append("</font>");
        return buf.toString();
    }

}
