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

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Functional;
import org.slasoi.slamodel.vocab.ext.Nominal;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public final class sla implements Extensions{
    
	public static final String $base = "http://www.slaatsoi.org/slamodel#";

    /*--------- SLA(T) ATTRIBUTES ---------------------------------------------------------------*/
    // the attributes of all IDENTIFIABLE entities in SLA(T)s can be referred to .. 

    // STRINGS
    public static final String $sla_agreedAt = "agreedAt"; 
    public static final String $sla_effectiveFrom = "effectiveFrom"; 
    public static final String $sla_effectiveUntil = "effectiveUntil";
    public static final String $endpoint_location = "location";
    // ID VERSIONS
    public static final ID sla_agreedAt = new ID($sla_agreedAt); 
    public static final ID sla_effectiveFrom = new ID($sla_effectiveFrom); 
    public static final ID sla_effectiveUntil = new ID($sla_effectiveUntil);
    public static final ID endpoint_location = new ID($endpoint_location);
	
	/*--------- VARIOUS IDS ---------------------------------------------------------------*/

    // STRINGS
	public static final String $custom_location = "custom_location";
    public static final String $autogen = "autogen";
    public static final String $endpoint = "endpoint";
    public static final String $consumer = "consumer";
    public static final String $request_time = "request_time";
    public static final String $reply_time = "reply_time";
    // ID VERSIONS
    public static final ID custom_location = new ID($custom_location);
    public static final ID autogen = new ID($autogen);
    public static final ID endpoint = new ID($endpoint);
    public static final ID consumer = new ID($consumer);
    public static final ID request_time = new ID($request_time);
    public static final ID reply_time = new ID($reply_time);

	
	/*--------- ROLES ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $provider = $base + "provider";
	public static final String $customer = $base + "customer";
	public static final String $third_party_provider = $base + "third_party_provider";
    public static final String $auxiliary_party = $base + "auxiliary_party";
	// STND VERSIONS
    public static final STND provider = new STND($provider);
    public static final STND customer = new STND($customer);
    public static final STND third_party_provider = new STND($third_party_provider);
    public static final STND auxiliary_party = new STND($auxiliary_party);
	
	/*--------- PROTOCOLS ---------------------------------------------------------------*/

    // STRINGS
	public static final String $telephone = $base + "telephone";
	public static final String $SOAP = $base + "SOAP";
	public static final String $email = $base + "email";
	public static final String $SMS = $base + "SMS";
	public static final String $REST = $base + "REST";
	public static final String $XMPP = $base + "XMPP";
    public static final String $HTTP = $base + "HTTP";
    public static final String $fax = $base + "fax";
    public static final String $post_mail = $base + "post_mail";
    public static final String $SSH = $base + "SSH";
	// STND VERSIONS
    public static final STND telephone = new STND($telephone);
    public static final STND SOAP = new STND($SOAP);
    public static final STND email = new STND($email);
    public static final STND SMS = new STND($SMS);
    public static final STND REST = new STND($REST);
    public static final STND XMPP = new STND($XMPP);
    public static final STND HTTP = new STND($HTTP);
    public static final STND fax = new STND($fax);
    public static final STND post_mail = new STND($post_mail);
    public static final STND SSH = new STND($SSH);
	
	/*--------- POLICIES ---------------------------------------------------------------*/
    
	// STRINGS
	public static final String $mandatory = $base + "mandatory";
	public static final String $optional = $base + "optional";
	public static final String $forbidden = $base + "forbidden";
	// STND VERSIONS
    public static final STND mandatory = new STND($mandatory);
    public static final STND optional = new STND($optional);
    public static final STND forbidden = new STND($forbidden);

    /*--------- ANNOTATIONS ---------------------------------------------------------------*/
    
    // STRINGS
    public static final String $gslam_epr = $base + "gslam_epr";
    public static final String $service_type = $base + "service_type";
    public static final String $service_id = $base + "service_id";
    public static final String $dsp_neg_id = $base + "dsp_neg_id";
    // STND VERSIONS
    public static final STND gslam_epr = new STND($gslam_epr);
    public static final STND service_type = new STND($service_type);
    public static final STND service_id = new STND($service_id);
    public static final STND dsp_neg_id = new STND($dsp_neg_id);

	
	/*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
	
    private UUID uuid = new UUID($base);
	private static final Nominal[] _nominals = new Nominal[]{
	    // sla(t) attributes
	    new Nominal(sla_agreedAt, meta.TIME_STAMP, Role.SLAT_ATTRIBUTE), 
	    new Nominal(sla_effectiveFrom, meta.TIME_STAMP, Role.SLAT_ATTRIBUTE),
	    new Nominal(sla_effectiveUntil, meta.TIME_STAMP, Role.SLAT_ATTRIBUTE),
	    new Nominal(endpoint_location, meta.UUID, Role.SLAT_ATTRIBUTE),
	    // related properties
		new Nominal(endpoint, meta.ENDPOINT, Role.OPERATION_PROPERTY),
		new Nominal(consumer, meta.AGENT, Role.OPERATION_PROPERTY),
		new Nominal(request_time, meta.TIME_STAMP, Role.OPERATION_PROPERTY),
		new Nominal(reply_time, meta.TIME_STAMP, Role.OPERATION_PROPERTY),
		// agreement roles
		new Nominal(provider, meta.STND, Role.AGREEMENT_ROLE),
		new Nominal(customer, meta.STND, Role.AGREEMENT_ROLE),
		new Nominal(third_party_provider, meta.STND, Role.AGREEMENT_ROLE),
		new Nominal(auxiliary_party, meta.STND, Role.AGREEMENT_ROLE),
		// endpoint protocols
		new Nominal(telephone, meta.STND, Role.ENDPOINT_PROTOCOL),
		new Nominal(SOAP, meta.STND, Role.ENDPOINT_PROTOCOL),
		new Nominal(email, meta.STND, Role.ENDPOINT_PROTOCOL),
		new Nominal(SMS, meta.STND, Role.ENDPOINT_PROTOCOL),
		new Nominal(REST, meta.STND, Role.ENDPOINT_PROTOCOL),
		new Nominal(XMPP, meta.STND, Role.ENDPOINT_PROTOCOL),
        new Nominal(HTTP, meta.STND, Role.ENDPOINT_PROTOCOL),
        new Nominal(fax, meta.STND, Role.ENDPOINT_PROTOCOL),
        new Nominal(post_mail, meta.STND, Role.ENDPOINT_PROTOCOL),
        new Nominal(SSH, meta.STND, Role.ENDPOINT_PROTOCOL),
		// action policies
		new Nominal(mandatory, meta.STND, Role.ACTION_POLICY),
		new Nominal(optional, meta.STND, Role.ACTION_POLICY),
		new Nominal(forbidden, meta.STND, Role.ACTION_POLICY)
	};
	private static final Functional[] _predicates = new Functional[]{};
	
    public UUID getUuid(){ return uuid; }
	public Nominal[] getNominals(){ return _nominals; }
	public Functional[] getFunctionals(){ return _predicates; }
	public String[] getDays(){ return new String[0]; }
	public String[] getMonths(){ return new String[0]; }

}
