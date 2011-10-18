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

import java.io.Serializable;

import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;

public class ComponentProductOfferingPrice implements Serializable{
	
	private static final long serialVersionUID = -520783005319254527L;
	private ID _id = null;
	private STND _priceType = null;
	private CONST _price = null;
	private CONST _quantity = null;
    private PriceModification[] _price_mods = null;
    
    public ComponentProductOfferingPrice(ID id, STND priceType, CONST price, CONST quantity){
        this(id,priceType,price,quantity,null);
    }
    
    public ComponentProductOfferingPrice(ID id, STND priceType, ID priceVar, CONST quantity){
		/**
		 * TODO change the price type from CONST to ValueExpr and update
		 * SyntaxConverter and XML beans accordingly. The current implementation is just
		 * a workaround that assumes that a price without a unit of measure refers to a variable
		 */
        this(id,priceType,new CONST(priceVar.toString(), null),quantity,null);
    }

	public ComponentProductOfferingPrice(ID id, STND priceType, CONST price, CONST quantity, PriceModification[] pms){
		setId(id);
		setPriceType(priceType);
		setPrice(price);
		setQuantity(quantity);
		setPriceModifications(pms);
	}
	
	public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
	    _id = id;
	}
	
	public ID getId(){ return _id; }
	
	public void setPriceType(STND priceType){
        if (priceType == null) throw new IllegalArgumentException("No priceType specified");
	    _priceType = priceType;
	}
	
	public STND getPriceType(){ return _priceType; }
	
	public void setPrice(CONST price){
        if (price == null) throw new IllegalArgumentException("No price specified");
	    _price = price;
	}
	
	public CONST getPrice(){ return _price; }
	
	public void setQuantity(CONST quantity){
	    _quantity = quantity != null? quantity: new CONST("1", null);
	}
	
	public CONST getQuantity(){ return _quantity; }
    
    public void setPriceModifications(PriceModification[] pms){
        _price_mods = pms != null? pms: new PriceModification[]{};
    }
    
    public PriceModification[] getPriceModifications(){ return _price_mods; }

}
