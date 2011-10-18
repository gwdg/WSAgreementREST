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

import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.Validator.Exception;
import org.slasoi.slamodel.sla.tools.Validator.Warning;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.sla;
import org.slasoi.slamodel.vocab.units;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public class TerminationClause extends Guaranteed.Action.Defn{

	private static final long serialVersionUID = 1L;
	
	private ID _id = null;
	private STND _initiator = null;
	private String _type = null;
    private String _clause = null;
	private STND _notificationMethod = null; // TODO default STND
	private CONST _notificationPeriod = null; // TODO default CONST
	private CONST _fees = null; // TODO default CONST
	
	public TerminationClause(ID id, STND initiator, String type, String clause, CONST period){
	    this(id,initiator,type,clause,null,period,null);
	}
    
    public TerminationClause(ID id, STND initiator, String type, String clause, STND method, CONST period, CONST fees){
        setId(id);
        setInitiator(initiator);
        setType(type);
        setClause(clause);
        setNotificationMethod(method);
        setNotificationPeriod(period);
        setFees(fees);
    }
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
	    _id = id;
	}
	
	public ID getId(){ return _id; }
	
	public void setInitiator(STND initiator){
        if (initiator == null) throw new IllegalArgumentException("No initiator specified");
	    _initiator = initiator;
	}
	
	public STND getInitiator(){ return _initiator; }
    
    public void setType(String type){
        if (type == null) throw new IllegalArgumentException("No type specified");
        _type = type;
    }
    
    public String getType(){ return _type; }
	
	public void setClause(String clause){
        if (clause == null) throw new IllegalArgumentException("No clause specified");
	    _clause = clause;
	}
	
	public String getClause(){ return _clause; }
	
	public void setNotificationMethod(STND notificationMethod){
        _notificationMethod = notificationMethod != null? notificationMethod: sla.fax;
	}
	
	public STND getNotificationMethod(){ return _notificationMethod; }
	
	public void setNotificationPeriod(CONST notificationPeriod){
        if (notificationPeriod == null) throw new IllegalArgumentException("No notification period specified");
	    _notificationPeriod = notificationPeriod;
	}
	
	public CONST getNotificationPeriod(){ return _notificationPeriod; }
	
	public void setFees(CONST fees){
        _fees = fees != null? fees: new CONST("0", units.EUR);
	}
	
	public CONST getFees(){ return _fees; }

	@Override
	public Warning[] validate(ExprValidator ev) throws Exception {
        /*
         * TODO are there any constraints on the ID ?
         */
        STND initiator = getInitiator();
        if (ev.metaModel().getNominal(initiator, Role.AGREEMENT_ROLE)==null){
            throw new Validator.Exception("TERMINATION_CLAUSE: Invalid Initiator: "+initiator.getValue(), initiator);
        }
        STND method = getNotificationMethod();
        if (ev.metaModel().getNominal(method, Role.ENDPOINT_PROTOCOL)==null){
            throw new Validator.Exception("TERMINATION_CLAUSE: Invalid Notification Method: "+method.getValue(), method);
        }
        CONST period = getNotificationPeriod();
        Category c = ev.validateExpression(period, null);
        if (!c.isa(meta.DURATION)){
            throw new Validator.Exception("TERMINATION_CLAUSE: Invalid Notification Period: "+period.toString(), period);
        }
        CONST fees = getFees();
        c = ev.validateExpression(fees, null);
        if (!c.isa(meta.COST)){
            throw new Validator.Exception("TERMINATION_CLAUSE: Invalid Fees: "+fees.toString(), fees);
        }
        return new Validator.Warning[]{
            new Validator.Warning("Unable to validate TerminationClause 'id' (criteria unspecified)", this),
            new Validator.Warning("Unable to validate TerminationClause 'type' (criteria unspecified)", this),
            new Validator.Warning("Unable to validate TerminationClause 'clause' (criteria unspecified)", this)
        };
	}

}
