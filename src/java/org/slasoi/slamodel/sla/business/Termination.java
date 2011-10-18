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

package org.slasoi.slamodel.sla.business;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.Validator.Exception;
import org.slasoi.slamodel.sla.tools.Validator.Warning;

public class Termination extends Guaranteed.Action.Defn{

	private static final long serialVersionUID = 1L;
	
	private String _name = null;
	private ID _clause = null;
	
	public Termination(String name, ID clause){
		setName(name);
		setTerminationClauseId(clause);
	}
	
	public void setName(String name){
        if (name == null) throw new IllegalArgumentException("No name specified");
	    _name = name;
	}
	
	public String getName(){ return _name; }
	
	public void setTerminationClauseId(ID clause){
        if (clause == null) throw new IllegalArgumentException("No Termination Clause specified");
	    _clause = clause;
	}
	
	public ID getTerminationClauseId(){ return _clause; }

	@Override
	public Warning[] validate(ExprValidator ev) throws Exception {
	    ID tcid = getTerminationClauseId();
	    if (!x_IS_VALID_TERMINATION_CLAUSE_ID(tcid, ev)){
            throw new Validator.Exception("TERMINATION_ACTION: Invalid TerminationClause ID: "+tcid.toString(), tcid);
	    }
	    
        return new Validator.Warning[]{
            new Validator.Warning("Unable to validate Termination Action 'id' (criteria unspecified)", this),
            new Validator.Warning("Unable to validate Termination Action 'name' (criteria unspecified)", this),
        };
	}
	
	private boolean x_IS_VALID_TERMINATION_CLAUSE_ID(ID tcid, ExprValidator ev){
        SLATemplate slat = ev.getSLATemplate();
        AgreementTerm[] ats = slat.getAgreementTerms();
        if (ats != null){
            for (AgreementTerm at : ats){
                Guaranteed[] gts = at.getGuarantees();
                for (Guaranteed gt : gts){
                    if (gt instanceof Guaranteed.Action){
                        Guaranteed.Action.Defn gad = ((Guaranteed.Action)gt).getPostcondition();
                        if (gad instanceof TerminationClause){
                            ID id = ((TerminationClause)gad).getId();
                            if (id.equals(tcid)) return true;
                        }
                    }
                }
            }
        }
	    return false;
	}

}
