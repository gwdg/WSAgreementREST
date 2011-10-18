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

import java.util.HashMap;
import java.util.Map;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.ValueExpr;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Category;

public class Invocation extends Guaranteed.Action.Defn{
	
	private static final long serialVersionUID = 1L;
	
	private ID _endpoint = null;
	private ID _operation = null;
	private Map<ID,ValueExpr> _params = new HashMap<ID,ValueExpr>();
    
    /**
     * @deprecated
     */
    public Invocation(){}
	
	public Invocation(ID operation){
	    setOperationId(operation);
	}
	
	public void setEndpointId(ID endpoint){ _endpoint = endpoint; }
	
	public ID getEndpointId(){ return _endpoint; }
	
	public void setOperationId(ID operationId){
        if (operationId == null) throw new IllegalArgumentException("No operationId specified");
	    _operation = operationId;
	}
	
	public ID getOperationId(){ return _operation; }
	
	public ID[] getParameterKeys(){ return _params.keySet().toArray(new ID[0]); }
	
	public void setParameterValue(ID name, ValueExpr value){
        if (name == null) throw new IllegalArgumentException("No parameter name specified");
	    _params.put(name, value);
	}
	
	public ValueExpr getParameterValue(ID name){ return _params.get(name); }

    public Validator.Warning[] validate(ExprValidator ev) throws Validator.Exception{
        ID endpoint_id = getEndpointId();
        if (endpoint_id != null){
            // The Endpoint ID must be a complete PATH expression identifying 
            // an InterfaceDeclr Endpoint, so no context is required (i.e. the context is already in the PATH) ...
            Category c = ev.validateExpression(endpoint_id, null);
            if (!c.isa(meta.ENDPOINT)){
                throw new Validator.Exception("INVOC: invalid enpoint ref: "+endpoint_id.toString(), endpoint_id);
            }
        }
        ID operation_id = getOperationId();
        String $operation_id = operation_id.toString();
        // The Operation ID must be a complete PATH expression identifying an 
        // Interface.Operation, so no context is required (i.e. the context is already in the PATH) ...
        Category c = ev.validateExpression(operation_id, null);  
        if (!c.isa(meta.OPERATION)){
            throw new Validator.Exception("INVOC: invalid operation ref: "+$operation_id, operation_id);
        }
        ID[] param_keys = getParameterKeys();
        for (ID param_key : param_keys){
            /*
             * Each "param_key" is the name of an Interface.Operation.Property. To check whether this property 
             * is valid (i.e. is actually a property of the Interface.Operation), 
             * we need to supply the Interface.Operation identifier as context ...
             */
            c = ev.validateExpression(param_key, $operation_id);
            if (!(c.isa(meta.PROPERTY) || c.isa(meta.UNTYPED))) {
                throw new Validator.Exception("INVOC: invalid operation property ref: "+param_key.toString(), param_key);
            }
        }
        return new Validator.Warning[0];
    }

}
