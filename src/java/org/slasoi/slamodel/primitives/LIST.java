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

package org.slasoi.slamodel.primitives;

import java.util.ArrayList;

public class LIST extends ArrayList<ValueExpr> implements ValueExpr{
	
	private static final long serialVersionUID = 1L;
	
	public LIST(){}
    
    public LIST(ValueExpr[] elements){
        if (elements != null) for (ValueExpr v : elements) add(v);
    }
	
	public String toString(){
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        int size = size();
        for (int i=0; i<size; i++){
        	if (i>0) buf.append(", ");
        	buf.append(get(i).toString());
        }
        buf.append("]");
        return buf.toString();
	}
	
	public boolean equals(Object o){
		if (this == o) return true;
		if (o instanceof LIST){
			LIST o_list = (LIST)o;
			if (this.size() == o_list.size()){
				LIST list = new LIST();
				list.addAll(o_list);
				for (ValueExpr v : this){
					if (!list.remove(v)) return false;
				}
				return list.isEmpty();
			}
		}
		return false;
	}

}
