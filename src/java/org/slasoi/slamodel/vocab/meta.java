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
import org.slasoi.slamodel.vocab.ext.Value;
import org.slasoi.slamodel.vocab.ext.Entity;
import org.slasoi.slamodel.vocab.ext.Expression;

public final class meta {
	
	public static final String $base = "http://www.slaatsoi.org/types#";

	// STRINGS
	public static final String $ANY = $base+"ANY";
	public static final String $UNTYPED = $base+"UNTYPED";

    public static final String $NUMBER = $base + "NUMBER";
    public static final String $DOMAIN = $base+"DOMAIN";
    public static final String $CONSTRAINT = $base+"CONSTRAINT";
    public static final String $FUNCTION = $base+"FUNCTION";
    public static final String $EVENT = $base+"EVENT";
    
    // SLA ENTITIES
    public static final String $ATTRIBUTE = $base+"ATTRIBUTE";
    public static final String $AGENT = $base+"AGENT";
    public static final String $PARTY = $base+"PARTY";
    public static final String $OPERATIVE = $base+"OPERATIVE";
    public static final String $SERVICE = $base+"SERVICE";
    public static final String $MESSAGE_TYPE_SERVICE = $base+"MESSAGE_TYPE_SERVICE";
    public static final String $RESOURCE_TYPE_SERVICE = $base+"RESOURCE_TYPE_SERVICE";
    public static final String $INTERFACE = $base+"INTERFACE";
    public static final String $OPERATION = $base+"OPERATION";
    public static final String $PROPERTY = $base+"PROPERTY";
    public static final String $ENDPOINT = $base+"ENDPOINT";
    public static final String $VARIABLE = $base+"VARIABLE";
    public static final String $AGREEMENT_TERM = $base+"AGREEMENT_TERM";
    public static final String $STATE = $base+"STATE";
    public static final String $ACTION = $base+"ACTION";

    public static final String $TEXT = $base+"TEXT";
    public static final String $ID = $base+"ID";
    public static final String $UUID = $base+"UUID";
    public static final String $STND = $base+"STND";
	
	public static final String $STANDARD = $base+"STANDARD";
	//public static final String $INTERVAL = $base+"INTERVAL";
	public static final String $BOOLEAN = $base+"BOOLEAN";
	public static final String $SLA_STATE_CHANGE = $base+"SLA_STATE_CHANGE";
	public static final String $FAULT = $base+"FAULT";
	public static final String $TIME_SIGNAL = $base + "TIME_SIGNAL";
	public static final String $DAY = $base + "DAY";
	public static final String $MONTH = $base + "MONTH";
	public static final String $YEAR = $base + "YEAR";
	public static final String $DATE = $base + "DATE";
    public static final String $TIME_OF_DAY = $base + "TIME_OF_DAY";
    public static final String $TIME_STAMP = $base + "TIME_STAMP";
	public static final String $QUANTITY = $base + "QUANTITY";
	public static final String $COUNT = $base+"COUNT";
	public static final String $AREA = $base + "AREA";
	public static final String $DATA_RATE = $base + "DATA_RATE";
	public static final String $DATA_SIZE = $base + "DATA_SIZE";
	public static final String $ENERGY = $base + "ENERGY";
	public static final String $LENGTH = $base + "LENGTH";
	public static final String $DURATION = $base+"DURATION";
	public static final String $FREQUENCY = $base + "FREQUENCY";
	public static final String $RATIO = $base + "RATIO";
	public static final String $POWER = $base + "POWER";
	public static final String $TX_RATE = $base + "TX_RATE";
	public static final String $WEIGHT = $base + "WEIGHT";
	public static final String $COST = $base + "COST";

	// ATOM VERSIONS
	public static final Value ANY = new Value(new STND($ANY), new Value[0]);
	public static final Value UNTYPED = new Value(new STND($UNTYPED));

    public static final Value NUMBER = new Value(new STND($NUMBER));
    public static final Value BOOLEAN = new Value(new STND($BOOLEAN));
    public static final Value TEXT = new Value(new STND($TEXT));
    public static final Value ID = new Value(new STND($ID));
    public static final Value UUID = new Value(new STND($UUID), ID);
    public static final Value STND = new Value(new STND($STND), UUID);
	public static final Value STANDARD = new Value(new STND($STANDARD), STND);
    
    public static final Expression DOMAIN = new Expression(new STND($DOMAIN));
    public static final Expression CONSTRAINT = new Expression(new STND($CONSTRAINT), BOOLEAN);
    public static final Expression FUNCTION = new Expression(new STND($FUNCTION), NUMBER);
    public static final Expression EVENT = new Expression(new STND($EVENT));

    // SLA ENTITIES
    public static final Entity ATRIBUTE = new Entity(new STND($ATTRIBUTE), ID);
    public static final Entity AGENT = new Entity(new STND($AGENT), ID);
    public static final Entity PARTY = new Entity(new STND($PARTY), AGENT);
    public static final Entity OPERATIVE = new Entity(new STND($OPERATIVE), AGENT);
    public static final Entity SERVICE = new Entity(new STND($SERVICE), ID);
    public static final Entity MESSAGE_TYPE_SERVICE = new Entity(new STND($MESSAGE_TYPE_SERVICE), SERVICE);
    public static final Entity RESOURCE_TYPE_SERVICE = new Entity(new STND($RESOURCE_TYPE_SERVICE), SERVICE);
    public static final Entity INTERFACE = new Entity(new STND($INTERFACE), new Value[]{MESSAGE_TYPE_SERVICE,RESOURCE_TYPE_SERVICE});
    public static final Entity OPERATION = new Entity(new STND($OPERATION), MESSAGE_TYPE_SERVICE);
    public static final Entity PROPERTY = new Entity(new STND($PROPERTY), ID);
    public static final Entity ENDPOINT = new Entity(new STND($ENDPOINT), ID);
    public static final Entity VARIABLE = new Entity(new STND($VARIABLE), ID);
    public static final Entity AGREEMENT_TERM = new Entity(new STND($AGREEMENT_TERM), ID);
    public static final Entity STATE = new Entity(new STND($STATE), new Value[]{CONSTRAINT,AGREEMENT_TERM});
    public static final Entity ACTION = new Entity(new STND($ACTION), AGREEMENT_TERM);
	
	//public static final Value INTERVAL = new Value(new STND($INTERVAL));
	public static final Value SLA_STATE_CHANGE = new Value(new STND($SLA_STATE_CHANGE), EVENT);
	public static final Value FAULT = new Value(new STND($FAULT), EVENT);
	public static final Value TIME_SIGNAL = new Value(new STND($TIME_SIGNAL), EVENT);
	public static final Value DAY = new Value(new STND($DAY), NUMBER);
	public static final Value MONTH = new Value(new STND($MONTH), NUMBER);
	public static final Value YEAR = new Value(new STND($YEAR), NUMBER);
    public static final Value DATE = new Value(new STND($DATE), NUMBER);
    public static final Value TIME_OF_DAY = new Value(new STND($TIME_OF_DAY), NUMBER);
    public static final Value TIME_STAMP = new Value(new STND($TIME_STAMP), NUMBER);
    public static final Value QUANTITY = new Value(new STND($QUANTITY), NUMBER);
	public static final Value COUNT = new Value(new STND($COUNT), QUANTITY);
	public static final Value AREA = new Value(new STND($AREA), NUMBER);
    public static final Value DATA_RATE = new Value(new STND($DATA_RATE), NUMBER);
    public static final Value DATA_SIZE = new Value(new STND($DATA_SIZE), NUMBER);
    public static final Value ENERGY = new Value(new STND($ENERGY), NUMBER);
    public static final Value LENGTH = new Value(new STND($LENGTH), NUMBER);
    public static final Value DURATION = new Value(new STND($DURATION), NUMBER);
	public static final Value FREQUENCY = new Value(new STND($FREQUENCY), NUMBER);
    public static final Value RATIO = new Value(new STND($RATIO), NUMBER);
    public static final Value POWER = new Value(new STND($POWER), NUMBER);
    public static final Value TX_RATE = new Value(new STND($TX_RATE), NUMBER);
    public static final Value WEIGHT = new Value(new STND($WEIGHT), NUMBER);
    public static final Value COST = new Value(new STND($COST), NUMBER);

}
