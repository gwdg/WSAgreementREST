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
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.Validator.Exception;
import org.slasoi.slamodel.sla.tools.Validator.Warning;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Category;

public class Penalty extends Guaranteed.Action.Defn{

	private static final long serialVersionUID = 1L;
	
	private CONST _price = null;
	
	public Penalty(CONST price){
		setPrice(price);
	}
	
	public Penalty(ID priceVar){
		/**
		 * TODO change the price type from CONST to ValueExpr and update
		 * SyntaxConverter and XML beans accordingly. The current implementation is just
		 * a workaround that assumes that a price without a unit of measure refers to a variable
		 */
		_price = new CONST(priceVar.toString(), null);
	}
	
	public void setPrice(CONST price){
        if (price == null) throw new IllegalArgumentException("No price specified");
	    _price = price;
	}
	
	public CONST getPrice(){ return _price; }
	
	@Override
	public Warning[] validate(ExprValidator ev) throws Exception {
	    CONST price = getPrice();
        if (price.getDatatype() == null) {
        	Category c = ev.validateExpression(new ID(price.getValue()), null);
            if (!c.isa(meta.NUMBER)){
                throw new Validator.Exception("PENALTY: invalid price VARIABLE: "+_price.toString(), _price);
            }
        } else {
            Category c = ev.validateExpression(price, null);
            if (!c.isa(meta.COST)){
                throw new Validator.Exception("PENALTY: invalid price CONST: "+price.toString(), price);
            }        	
        }
        return new Validator.Warning[]{
            new Validator.Warning("Unable to validate Penalty 'id' (criteria unspecified)", this),
        };
	}

}
