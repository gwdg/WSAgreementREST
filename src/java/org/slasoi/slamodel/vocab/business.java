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

package org.slasoi.slamodel.vocab;

import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Functional;
import org.slasoi.slamodel.vocab.ext.Nominal;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public class business implements Extensions{
    
    public static final String $base = "http://www.slaatsoi.org/business#";

    // STRING VERSIONS
    public static final String $one_time_charge = $base + "one_time_charge";
    public static final String $tariff_usage = $base + "tariff_usage";
    public static final String $plain_tariff = $base + "plain_tariff";
    public static final String $per_request = $base + "per_request";
    public static final String $per_day = $base + "per_day";
    public static final String $per_week = $base + "per_week";
    public static final String $per_month = $base + "per_month";
    public static final String $per_year = $base + "per_year";
    public static final String $discount = $base + "discount";
    public static final String $increment = $base + "increment";
    public static final String $penalty_count = $base + "penalty_count";
    public static final String $violation_count = $base + "violation_count";
    
    // STND VERSIONS
    public static final STND one_time_charge = new STND($one_time_charge);
    public static final STND tariff_usage = new STND($tariff_usage);
    public static final STND plain_tariff = new STND($plain_tariff);
    public static final STND per_request = new STND($per_request);
    public static final STND per_day = new STND($per_day);
    public static final STND per_week = new STND($per_week);
    public static final STND per_month = new STND($per_month);
    public static final STND per_year = new STND($per_year);
    public static final STND discount = new STND($discount);
    public static final STND increment = new STND($increment);
    public static final STND penalty_count = new STND($penalty_count);
    public static final STND violation_count = new STND($violation_count);

    /*--------- ANNOTATIONS ---------------------------------------------------------------*/
    
    // STRINGS
    public static final String $ContactPointPhone = $base + "ContactPointPhone";
    public static final String $ContactPointFax = $base + "ContactPointFax";
    public static final String $ContactPointAddress = $base + "ContactPointAddress";
    public static final String $ContactPointName = $base + "ContactPointName";
    public static final String $ContactPointEmail = $base + "ContactPointEmail";
    // STND VERSIONS
    public static final STND ContactPointPhone = new STND($ContactPointPhone);
    public static final STND ContactPointFax = new STND($ContactPointFax);
    public static final STND ContactPointAddress = new STND($ContactPointAddress);
    public static final STND ContactPointName = new STND($ContactPointName);
    public static final STND ContactPointEmail = new STND($ContactPointEmail);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{
        new Nominal(one_time_charge, meta.STND, Role.PRICE_TYPE), 
        new Nominal(tariff_usage, meta.STND, Role.PRICE_TYPE), 
        new Nominal(plain_tariff, meta.STND, Role.PRICE_TYPE), 
        new Nominal(per_request, meta.STND, new Role[]{ Role.PRICE_TYPE, Role.BILLING_FREQUENCY }), 
        new Nominal(per_day, meta.STND, Role.BILLING_FREQUENCY), 
        new Nominal(per_week, meta.STND, Role.PRICE_TYPE), 
        new Nominal(per_month, meta.STND, new Role[]{ Role.PRICE_TYPE, Role.BILLING_FREQUENCY }),   
        new Nominal(per_year, meta.STND, Role.BILLING_FREQUENCY), 
        new Nominal(discount, meta.STND, Role.PRICE_MODIFICATION_TYPE),  
        new Nominal(increment, meta.STND, Role.PRICE_MODIFICATION_TYPE)  
    };
    private static final Functional[] _predicates = new Functional[]{
        new Functional(penalty_count, new Category[]{ meta.SERVICE }, meta.COUNT, false),
        new Functional(violation_count, new Category[]{ meta.AGREEMENT_TERM }, meta.COUNT, false),
    };
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
    public String[] getDays(){ return new String[0]; }
    public String[] getMonths(){ return new String[0]; }

}
