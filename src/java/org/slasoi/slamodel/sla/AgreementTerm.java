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

package org.slasoi.slamodel.sla;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.ConstraintExpr;

public class AgreementTerm extends Annotated implements Entity.Instance{
	
	private static final long serialVersionUID = 1L;
	
	private ID _id = null;
	private VariableDeclr[] _vars = null;
	private Guaranteed[] _guarantees = null;
	private ConstraintExpr _pre = null;
	    
    public Entity entity(){ return meta.AGREEMENT_TERM; }
	
	/**
     * @deprecated
     */
    public AgreementTerm(){}
	
	public AgreementTerm(ID id, ConstraintExpr pre, VariableDeclr[] vars, Guaranteed[] guarantees){
	    setId(id);
        setVariableDeclrs(vars);
	    setPrecondition(pre);
	    setGuarantees(guarantees);
	}
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
	    _id = id;
	}
	
	public ID getId(){ return _id; }
    
    public void setPrecondition(ConstraintExpr pre){ _pre = pre; }
    
    public ConstraintExpr getPrecondition(){ return _pre; }
	
	public void setVariableDeclrs(VariableDeclr[] vars){ _vars = vars; }
	
	public VariableDeclr[] getVariableDeclrs(){ return _vars; }
    
    /**
     * Retrieves the VariableDeclr with the given variable name
     * @param variableName
     * @return
     */
	public VariableDeclr getVariableDeclr(String variableName){
        if (variableName != null && _vars != null){
            for (VariableDeclr v : _vars){
                if (v.getVar().getValue().equals(variableName)) return v;
            }
        }
        return null;
    }
	
	public void setGuarantees(Guaranteed[] guarantees){
        if (guarantees == null || guarantees.length == 0) throw new IllegalArgumentException("No guarantees specified");
	    _guarantees = guarantees;
	}
	
	public Guaranteed[] getGuarantees(){ return _guarantees; }
    
    /**
     * Retrieves the Guaranteed State or Action with the given id
     * @param id
     * @return
     */
	public Guaranteed getGuaranteed(String id){
        if (id != null && _guarantees != null){
            for (Guaranteed g : _guarantees){
                if (g.getId().getValue().equals(id)) return g;
            }
        }
        return null;
    }
	
	public String toString(){ return bnf.render(this); }

}
