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

import java.util.Calendar;

import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.TIME;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.tools.ExprValidator;
import org.slasoi.slamodel.sla.tools.Validator;
import org.slasoi.slamodel.sla.tools.Validator.Exception;
import org.slasoi.slamodel.sla.tools.Validator.Warning;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public class ProductOfferingPrice extends Guaranteed.Action.Defn{

	private static final long serialVersionUID = 1L;
	
	private ID _id = null;
	private String _name = null;
	private TIME _validFrom = null;
    private TIME _validUntil = null;
	private STND _billingFrequency = null;
	private Product _product = null;
	private ComponentProductOfferingPrice[] _cpops = null;
    
	/**
     * @deprecated
     */
    public ProductOfferingPrice(ID id, String name, CONST validFor, STND billingFrequency, ComponentProductOfferingPrice[] cpops){
        this(id,name,null,null,billingFrequency,null,cpops);
    }
    
    public ProductOfferingPrice(ID id, String name, TIME validFrom, TIME validUntil, STND billingFrequency, ComponentProductOfferingPrice[] cpops){
        this(id,name,validFrom,validUntil,billingFrequency,null,cpops);
    }
	
	public ProductOfferingPrice(ID id, String name, TIME validFrom, TIME validUntil, STND billingFrequency, Product product, ComponentProductOfferingPrice[] cpops){
		setId(id);
	    setName(name);
		setValidFrom(validFrom);
        setValidUntil(validUntil);
		setBillingFrequency(billingFrequency);
		setProduct(product);
		setComponentProductOfferingPrices(cpops);
	}
    
    public void setId(ID id){
        if (id == null) throw new IllegalArgumentException("No id specified");
        _id = id;
    }
    
    public ID getId(){ return _id; }
	
	public void setName(String name){
        if (name == null) throw new IllegalArgumentException("No name specified");
	    _name = name;
	}
	
	public String getName(){ return _name; }
	
	public void setValidFrom(TIME validFrom){
        if (validFrom == null) throw new IllegalArgumentException("No validFrom specified");
	    _validFrom = validFrom;
	}
	
	public TIME getValidFrom(){ return _validFrom; }
    
    public void setValidUntil(TIME validUntil){
        if (validUntil == null) throw new IllegalArgumentException("No validUntil specified");
        _validUntil = validUntil;
    }
    
    public TIME getValidUntil(){ return _validUntil; }
	
	public void setBillingFrequency(STND billingFrequency){
        if (billingFrequency == null) throw new IllegalArgumentException("No billingFrequency specified");
        _billingFrequency = billingFrequency;
	}
	
	public STND getBillingFrequency(){ return _billingFrequency; }
	
	public void setProduct(Product product){ _product = product; }
	
	public Product getProduct(){ return _product; }
	
	public void setComponentProductOfferingPrices(ComponentProductOfferingPrice[] cpops){
        if (cpops == null || cpops.length == 0) throw new IllegalArgumentException("No ComponentProductOfferingPrices specified");
        _cpops = cpops;
	}
	
	public ComponentProductOfferingPrice[] getComponentProductOfferingPrices(){ return _cpops; }

	@Override
	public Warning[] validate(ExprValidator ev) throws Exception {
        /*
         * TODO are there any constraints on the Name ?
         */
        Calendar v1 = getValidFrom().getValue();
        Calendar v2 = getValidUntil().getValue();
        if (v2.before(v1)){
            throw new Validator.Exception("PRODUCT OFFERING PRICE: Invalid 'validFrom'-'validUntil' range", this);
        }
        STND billing_freq = getBillingFrequency();
        if (ev.metaModel().getNominal(billing_freq, Role.BILLING_FREQUENCY)==null){
            throw new Validator.Exception("PRODUCT OFFERING PRICE: Invalid 'billing frequency': "+billing_freq.getValue(), billing_freq);
        }
        ComponentProductOfferingPrice[] cpops = getComponentProductOfferingPrices();
        for (ComponentProductOfferingPrice cpop : cpops){
            STND price_type = cpop.getPriceType();
            if (ev.metaModel().getNominal(price_type, Role.PRICE_TYPE)==null){
                throw new Validator.Exception("COMPONENT PRODUCT OFFERING PRICE: Invalid 'price type': "+price_type.getValue(), price_type);
            }
            CONST price = cpop.getPrice();
//            Category c = ev.validateExpression(price, null);
//            if (!c.isa(meta.COST)){
//                throw new Validator.Exception("COMPONENT PRODUCT OFFERING PRICE: Invalid 'price': "+price.toString(), price);
//            }            
            if (price.getDatatype() == null) {
            	Category c = ev.validateExpression(new ID(price.getValue()), null);
                if (!c.isa(meta.NUMBER)){
                    throw new Validator.Exception("COMPONENT PRODUCT OFFERING VARIABLE: Invalid 'price': "+price.toString(), price);
                }
            } else {
                Category c = ev.validateExpression(price, null);
                if (!c.isa(meta.COST)){
                    throw new Validator.Exception("COMPONENT PRODUCT OFFERING PRICE: Invalid 'price': "+price.toString(), price);
                }        	
            }

            CONST quantity = cpop.getQuantity();
            Category c = ev.validateExpression(quantity, null);
            if (!c.isa(meta.QUANTITY)){
                throw new Validator.Exception("COMPONENT PRODUCT OFFERING PRICE: Invalid 'quantity': "+quantity.toString(), quantity);
            }
            PriceModification[] pms = cpop.getPriceModifications();
            for (PriceModification pm : pms){
                STND type = pm.getType();
                if (ev.metaModel().getNominal(type, Role.PRICE_MODIFICATION_TYPE)==null){
                    throw new Validator.Exception("PRICE MODIFICATION: Invalid 'type': "+type.getValue(), type);
                }
                CONST value = pm.getValue();
                c = ev.validateExpression(value, null);
                if (c.isa(meta.COST) || c.isa(meta.RATIO)); // OK
                else throw new Validator.Exception("PRICE MODIFICATION: Invalid 'value': "+value.toString(), value);
            }
        }
        return new Validator.Warning[]{
            new Validator.Warning("Unable to validate ProductOfferingPrice 'name' (criteria unspecified)", this),
        };
	}

}
