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
import org.slasoi.slamodel.service.Interface;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.core.Annotated;

public class InterfaceDeclr extends Annotated implements Entity.Instance{
	
	private static final long serialVersionUID = 1L;
	
	private ID _id = null;
	private ID _provider = null;
	private Endpoint[] _endpoints = null;
	private Interface _interface = null;
    
    public Entity entity(){ return meta.INTERFACE; }
	
	/**
     * @deprecated
     */
    public InterfaceDeclr(){}
    
    public InterfaceDeclr(ID id, ID provider, Interface iface){
        this(id, provider, new Endpoint[0], iface);
    }
    
    public InterfaceDeclr(ID id, ID provider, Endpoint[] endpoints, Interface iface){
        setId(id);
        setProviderRef(provider);
        setEndpoints(endpoints);
        setInterface(iface);
    }
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
	    _id = id;
	}
	
	public ID getId(){ return _id; }
	
	public void setProviderRef(ID provider){
        if (provider == null) throw new IllegalArgumentException("No provider specified");
	    _provider = provider;
	}
	
	public ID getProviderRef(){ return _provider; }
	
	public void setEndpoints(Endpoint[] endpoints){ _endpoints = endpoints; }
	
	public Endpoint[] getEndpoints(){ return _endpoints; }
	
	public void setInterface(Interface iface){
        if (iface == null) throw new IllegalArgumentException("No interface specified");
	    _interface = iface;
	}
	
	public Interface getInterface(){ return _interface; }
    
    public String toString(){
        return bnf.render(this);
    }

}
