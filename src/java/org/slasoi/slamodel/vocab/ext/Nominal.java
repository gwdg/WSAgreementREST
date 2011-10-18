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

package org.slasoi.slamodel.vocab.ext;

import org.slasoi.slamodel.primitives.ID;

public class Nominal {
    
    public static enum Role{
        SLAT_ATTRIBUTE,
        AGREEMENT_ROLE,
        ENDPOINT_PROTOCOL,
        ACTION_POLICY,
        DATATYPE,
        OPERATION_PROPERTY,
        LOGICAL_OP,
        COMPARISON_OP,
        PRICE_TYPE,
        BILLING_FREQUENCY,
        PRICE_MODIFICATION_TYPE
    }

    private ID term;
    private Value atom;
    private Role[] roles;
    
    public Nominal(ID term, Value atom, Role role){
        this(term, atom, new Role[]{ role });
    }
    
    public Nominal(ID term, Value atom, Role[] roles){
        if (term==null || atom==null || roles==null || roles.length == 0){
            throw new IllegalArgumentException();
        }
        for (Role r : roles) if (r == null) throw new IllegalArgumentException();
        this.term = term;
        this.atom = atom;
        this.roles = roles;
    }
    
    public ID term(){ return term; }
    public Value category(){ return atom; }
    public Role[] roles(){ return roles; }
    
    public String toString(){
        String $roles = null;
        for (Role r : roles){
            if ($roles == null) $roles = "[";
            else $roles += "|";
            $roles += r.toString();
        }
        $roles += "]";
        return "NOMINAL::"+term.toString()+"::"+atom.toString()+"::"+$roles;
    }

}
