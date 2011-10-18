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
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.core.Annotated;

public class Party extends Annotated implements Entity.Instance{
	
	private static final long serialVersionUID = 1L;
	
	private ID _id = null;
	private STND _role = null;
	private Operative[] _operatives = null;
        
    public Entity entity(){ return meta.PARTY; }
	
	/**
     * @deprecated
     */
    public Party(){}
    
    public Party(ID id, STND role){
        setId(id);
        setAgreementRole(role);
    }
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
	    _id = id;
	}
	
	public ID getId(){ return _id; }
	
	public void setAgreementRole(STND role){
        if (role == null) throw new IllegalArgumentException("No role specified");
	    _role = role;
	}
	
	public STND getAgreementRole(){ return _role; }
	
	public void setOperatives(Operative[] operatives){ _operatives = operatives; }
	
	public Operative[] getOperatives(){ return _operatives; }
	
    /**
     * Retrieves the Operative with the given id
     * @param id
     * @return
     */
	public Operative getOperative(String id){
        if (id != null && _operatives != null){
            for (Operative o : _operatives){
                if (o.getId().getValue().equals(id)) return o;
            }
        }
        return null;
    }
	
	public String toString(){
	    return bnf.render(this);
	}
	
	public static class Operative extends Annotated implements Entity.Instance{
		
		private static final long serialVersionUID = 1L;
		
		private ID _id = null;
        
	    public Entity entity(){ return meta.OPERATIVE; }
		
		/**
	     * @deprecated
	     */
	    public Operative(){}
		
		public Operative(ID id){
		    setId(id);
		}
		
		public void setId(ID id){
	        if (id == null) throw new IllegalArgumentException("No id specified");
		    _id = id;
		}
		
		public ID getId(){ return _id; }
	    
	    public String toString(){
		    return bnf.render(this);
	    }
		
	}

}
