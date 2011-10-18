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

package org.slasoi.slamodel.service;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.xsd;
import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.DomainExpr;

public abstract class Interface extends Annotated{
	
	private static final long serialVersionUID = 1L;
	
	public static class Specification extends Interface{
		
		private static final long serialVersionUID = 1L;

	    private String _name = null;
		private ID[] _extended = null;
		private Operation[] _operations = null;
		
		/**
	     * @deprecated
	     */
	    public Specification(){}
		
		public Specification(String name){
		    this.setName(name);
		}
		
		public void setName(String name){
	        if (name == null) throw new IllegalArgumentException("No name specified");
		    _name = name;
		}
		
		public String getName(){ return _name; }
		
		public void setExtended(ID[] extended){ _extended = extended; }
		
		public ID[] getExtended(){ return _extended; }
		
		public void setOperations(Operation[] operations){ _operations = operations; }
		
		public Operation[] getOperations(){ return _operations; }
	    
	    /**
	     * Retrieves the Operation with the given name
	     * @param name
	     * @return
	     */
		public Operation getOperation(String name){
	        if (name != null && _operations != null){
	            for (Operation o : _operations){
	                if (o.getName().getValue().equals(name)) return o;
	            }
	        }
	        return null;
	    }
		
	    public String toString(){
	        return bnf.render(this);
	    }
	    
	    public interface Resolver{
	    	
	    	public Specification getInterfaceSpecification(String name);
	    	
	    }
		
	}
	
	public static class Operation extends Annotated{
		
		private static final long serialVersionUID = 1L;
	
		private ID _name = null;
		private Property[] _inputs = null;
		private Property[] _outputs = null;
		private Property[] _related = null;
		private STND[] _faults = null;
	    
	    /**
	     * @deprecated
	     */
	    public Operation(){}
		
		public Operation(ID name){
		    setName(name);
		}
	
		public void setName(ID name){
            if (name == null) throw new IllegalArgumentException("No name specified");
		    _name = name;
		}
	
		public ID getName(){ return _name; }
		
		public void setInputs(Property[] inputs){ _inputs = inputs; }
		
		public Property[] getInputs(){ return _inputs; }
        
		/**
         * Retrieves the Input property with the given name 
         * @param name
         * @return
         */
        public Property getInput(String name){
            if (name != null && _inputs != null){
                for (Property p : _inputs){
                    if (p.getName().getValue().equals(name)) return p;
                }
            }
            return null;
        }
		
		public void setOutputs(Property[] outputs){ _outputs = outputs; }
		
		public Property[] getOutputs(){ return _outputs; }
        
        /**
         * Retrieves the Output property with the given name 
         * @param name
         * @return
         */
		public Property getOutput(String name){
            if (name != null && _outputs != null){
                for (Property p : _outputs){
                    if (p.getName().getValue().equals(name)) return p;
                }
            }
            return null;
        }
		
		public void setRelated(Property[] related){ _related = related; }
		
		public Property[] getRelated(){ return _related; }
        
		/**
         * Retrieves the Related property with the given name 
         * @param name
         * @return
         */
        public Property getRelated(String name){
            if (name != null && _related != null){
                for (Property p : _related){
                    if (p.getName().getValue().equals(name)) return p;
                }
            }
            return null;
        }
		
		public void setFaults(STND[] faults){ _faults = faults; }
		
		public STND[] getFaults(){ return _faults; }
		
	    public String toString(){
	        return bnf.render(this);
	    }
		
		public static class Property extends Annotated{
			
			private static final long serialVersionUID = 1L;
		
			private ID _name = null;
			private boolean _aux = false;
			private STND _datatype = null;
			private DomainExpr _domain = null;
		    
		    /**
		     * @deprecated
		     */
		    public Property(){}
            
            /**
             * Default datatype & domain are null, default auxiliary value is false
             * @param name
             */
			public Property(ID name){
                setName(name);
                setDatatype(null);
                setAuxiliary(false);
                setDomain(null);
            }
			
			public void setName(ID name){
		        if (name == null) throw new IllegalArgumentException("No name specified");
			    _name = name;
			}
		
			public ID getName(){ return _name; }
			
			public void setAuxiliary(boolean aux){ _aux = aux; }
			
			public boolean isAuxiliary(){ return _aux; }
			
			public void setDatatype(STND datatype){
				if (datatype == null) datatype = new STND(xsd.$string);
				_datatype = datatype;
			}
			
			public STND getDatatype(){ return _datatype; }
			
			public void setDomain(DomainExpr domain){ _domain = domain; }
			
			public DomainExpr getDomain(){ return _domain; }
			
		    public String toString(){ return bnf.render(this); }
			
		}
		
	}

}
