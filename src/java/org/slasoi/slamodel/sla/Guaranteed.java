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

import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.CID;
import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.core.EventExpr;

public abstract class Guaranteed extends Annotated implements Entity.Instance{
	
	private static final long serialVersionUID = 1L;
	
    protected ID _id = null;
	
	protected Guaranteed(ID id){
	    setId(id);
	}
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
        _id = id;
	}
	
	public ID getId(){ return _id; }
	
	public static class State extends Guaranteed{
		
		private static final long serialVersionUID = 1L;
		
		private CONST _priority = null;
	    private ConstraintExpr _pre = null;
		private ConstraintExpr _state = null;
        
	    public Entity entity(){ return meta.STATE; }
        
        /**
         * @deprecated
         */
        public State(){ super(null); }
        
        public State(ID id, ConstraintExpr state){
            super(id);
            setState(state);
        }
        
        public void setId(ID id){
            if (id == null) throw new IllegalArgumentException("No id specified");
            if (!(id instanceof CID)) id = new CID(id.getValue());
            super.setId(id);
        }
		
		public void setPriority(CONST priority){ _priority = priority; }
		
		public CONST getPriority(){ return _priority; }
	    
	    public void setPrecondition(ConstraintExpr pre){ _pre = pre; }
	    
	    public ConstraintExpr getPrecondition(){ return _pre; }
		
		public void setState(ConstraintExpr state){
            if (state == null) throw new IllegalArgumentException("No state specified");
		    _state = state;
		}
		
		public ConstraintExpr getState(){ return _state; }
		
		public String toString(){ return bnf.render(this); }
		
	}
	
	public static class Action extends Guaranteed{
		
		private static final long serialVersionUID = 1L;
		
		private ID _actor = null;
		private STND _policy = null;
		private EventExpr _pre = null;
		private Defn _defn = null;
        
        public Entity entity(){ return meta.ACTION; }
        
        /**
         * @deprecated
         */
        public Action(){ super(null); }
		
		public Action(ID id, ID actor, STND policy, EventExpr pre, Defn defn){
		    super(id);
		    setActorRef(actor);
		    setPolicy(policy);
		    setPrecondition(pre);
		    setPostcondition(defn);
		}
		
		public void setActorRef(ID actor){
	        if (actor == null) throw new IllegalArgumentException("No actor specified");
		    _actor = actor;
		}
		
		public ID getActorRef(){ return _actor; }
		
		public void setPolicy(STND policy){
            if (policy == null) throw new IllegalArgumentException("No policy specified");
		    _policy = policy;
		}
		
		public STND getPolicy(){ return _policy; }
		
		public void setPrecondition(EventExpr pre){
            if (pre == null) throw new IllegalArgumentException("No precondition specified");
		    _pre = pre;
		}
		
		public EventExpr getPrecondition(){ return _pre; }
		
		public void setPostcondition(Defn defn){
            if (defn == null) throw new IllegalArgumentException("No postcondition specified");
		    _defn = defn;
		}
		
		public Defn getPostcondition(){ return _defn; }
		
		public String toString(){ return bnf.render(this); }
		
		public static abstract class Defn extends Annotated{
			
			private static final long serialVersionUID = 1L;
			
			public abstract Validator.Warning[] validate(ExprValidator ev) throws Validator.Exception;
			
			public String toString(){ return bnf.render(this); } 
			
		}
		
	}

}
