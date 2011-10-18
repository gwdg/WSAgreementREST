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

package org.slasoi.slamodel.core;

import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.vocab.bnf;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Expression;

public class CompoundConstraintExpr extends Annotated implements ConstraintExpr{
	
	private static final long serialVersionUID = 1L;
	
	private STND _op = null;
	private ConstraintExpr[] _sub = null;
    
    public Expression expression(){ return meta.CONSTRAINT; }
	
	/**
	 * @deprecated
	 */
	public CompoundConstraintExpr(){}
	
	public CompoundConstraintExpr(STND op, ConstraintExpr[] sub){
	    setLogicalOp(op);
	    setSubExpressions(sub);
	}
	
	public void setLogicalOp(STND op){
        if (op == null) throw new IllegalArgumentException("No logical operator specified");
	    _op = op;
	}
	
	public STND getLogicalOp(){ return _op; }
	
	public void setSubExpressions(ConstraintExpr[] sub){
        if (sub == null || sub.length==0) throw new IllegalArgumentException("No sub-expressions specified");
        _sub = sub;
	}
	
	public ConstraintExpr[] getSubExpressions(){ return _sub; }

    public String toString(){ return bnf.render(this); }

}
