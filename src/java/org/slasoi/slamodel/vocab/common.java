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
import org.slasoi.slamodel.vocab.ext.Set;

public final class common implements Extensions{
	
	public static final String $base = "http://www.slaatsoi.org/commonTerms#";
	
	// STRING VERSIONS
	public static final String $availability = $base + "availability"; // OK
	public static final String $accessibility = $base + "accessibility"; // OK
	public static final String $arrival_rate = $base + "arrival_rate"; // OK
	public static final String $data_volume = $base + "data_volume"; // OK
	public static final String $throughput = $base + "throughput"; // OK
	public static final String $completion_time = $base + "completion_time"; // OK
	public static final String $mttr = $base + "mttr"; // OK
	public static final String $mttf = $base + "mttf"; // OK
	public static final String $mttv = $base + "mttv"; // OK
	public static final String $reliability = $base + "reliability"; // OK
	public static final String $isolation = $base + "isolation"; // OK
	public static final String $accuracy = $base + "accuracy"; // OK
	public static final String $non_repudiation = $base + "non_repudiation"; // OK
	public static final String $supported_standards = $base + "supported_standards"; // OK
	public static final String $regulatory = $base + "regulatory"; // OK
	public static final String $integrity = $base + "integrity"; // OK
	public static final String $authentication = $base + "authentication"; // OK
	public static final String $auditability = $base + "auditability"; // OK
	public static final String $authorisation = $base + "authorisation"; // OK
	public static final String $data_encryption = $base + "data_encryption"; // OK
	// public static final String $location = $base + "location"; // deprecated 
	//B5 extension
	public static final String $bandwidth  = $base + "bandwidth";
    public static final String $latency = $base + "latency";
    public static final String $duration = $base + "duration";
	
	// STND VERSIONS
    public static final STND availability = new STND($availability);
    public static final STND accessibility = new STND($accessibility);
    public static final STND arrival_rate = new STND($arrival_rate);
    public static final STND data_volume = new STND($data_volume);
    public static final STND throughput = new STND($throughput);
    public static final STND completion_time = new STND($completion_time);
    public static final STND mttr = new STND($mttr);
    public static final STND mttf = new STND($mttf);
    public static final STND mttv = new STND($mttv);
    public static final STND reliability = new STND($reliability);
    public static final STND isolation = new STND($isolation);
    public static final STND accuracy = new STND($accuracy);
    public static final STND non_repudiation = new STND($non_repudiation);
    public static final STND supported_standards = new STND($supported_standards);
    public static final STND regulatory = new STND($regulatory);
    public static final STND integrity = new STND($integrity);
    public static final STND authentication = new STND($authentication);
    public static final STND auditability = new STND($auditability);
    public static final STND authorisation = new STND($authorisation);
    public static final STND data_encryption = new STND($data_encryption);
    // public static final STND location = new STND($location);
	//B5 extension
	public static final STND bandwidth = new STND($bandwidth);
    public static final STND latency = new STND($latency);
    public static final STND duration = new STND($duration);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{};
    private static final Functional[] _predicates = new Functional[]{
        new Functional(availability, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.RATIO, false),
        new Functional(accessibility, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.RATIO, false),
        new Functional(arrival_rate, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.TX_RATE, false),
        new Functional(data_volume, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.DATA_SIZE, false),
        new Functional(throughput, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.TX_RATE, false),
        new Functional(completion_time, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.DURATION, false),
        new Functional(mttr, new Category[]{ meta.SERVICE }, meta.DURATION, false),
        new Functional(mttf, new Category[]{ meta.SERVICE }, meta.DURATION, false),
        new Functional(mttv, new Category[]{ meta.SERVICE }, meta.DURATION, false),
        new Functional(reliability, new Category[]{ meta.SERVICE }, meta.RATIO, false),
        new Functional(isolation, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
        new Functional(accuracy, new Category[]{ meta.SERVICE }, meta.COUNT, false),
        new Functional(non_repudiation, new Category[]{ meta.SERVICE }, new Set(meta.STANDARD), false),
        new Functional(supported_standards, new Category[]{ meta.SERVICE }, new Set(meta.STANDARD), false),
        new Functional(regulatory, new Category[]{ meta.SERVICE }, new Set(meta.STANDARD), false),
        new Functional(integrity, new Category[]{ meta.SERVICE }, new Set(meta.STANDARD), false),
        new Functional(authentication, new Category[]{ meta.SERVICE }, meta.UNTYPED, false),
        new Functional(auditability, new Category[]{ meta.SERVICE }, meta.UNTYPED, false),
        new Functional(authorisation, new Category[]{ meta.SERVICE }, meta.UNTYPED, false),
        new Functional(data_encryption, new Category[]{ meta.SERVICE }, meta.UNTYPED, false),
		//B5 extension
		new Functional(bandwidth, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.DATA_RATE, false),
        new Functional(latency, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.DURATION, false),
        new Functional(duration, new Category[]{ meta.MESSAGE_TYPE_SERVICE }, meta.DURATION, false)
        // new Functional(location, new Category[]{ meta.SERVICE }, meta.UNTYPED, false)
    };
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
	public String[] getDays(){ return new String[0]; }
	public String[] getMonths(){ return new String[0]; }

}
