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
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Functional;
import org.slasoi.slamodel.vocab.ext.Nominal;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public final class xsd implements Extensions{
	
	public static final String $base = "http://www.w3.org/2001/XMLSchema#";

	public static final String $anyType = $base + "anyType";
	public static final String $anySimpleType = $base + "anySimpleType";
	public static final String $duration = $base + "duration";
	public static final String $dateTime = $base + "dateTime";
	public static final String $time = $base + "time";
	public static final String $date = $base + "date";
	public static final String $gYearMonth = $base + "gYearMonth";
	public static final String $gYear = $base + "gYear";
	public static final String $gMonthDay = $base + "gMonthDay";
	public static final String $gDay = $base + "gDay";
	public static final String $gMonth = $base + "gMonth";
	public static final String $boolean = $base + "boolean";
	public static final String $base64Binary = $base + "base64Binary";
	public static final String $hexBinary = $base + "hexBinary";
	public static final String $float = $base + "float";
	public static final String $decimal = $base + "decimal";
	public static final String $double = $base + "double";
	public static final String $anyURI = $base + "anyURI";
	public static final String $QName = $base + "QName";
	public static final String $NOTATION = $base + "NOTATION";
	public static final String $string = $base + "string";
	public static final String $normalizedString = $base + "normalizedString";
	public static final String $token = $base + "token";
	public static final String $language = $base + "language";
	public static final String $Name = $base + "Name";
	public static final String $NCName = $base + "NCName";
	public static final String $ID = $base + "ID";
	public static final String $IDREF = $base + "IDREF";
	public static final String $IDREFS = $base + "IDREFS";
	public static final String $ENTITY = $base + "ENTITY";
	public static final String $ENTITIES = $base + "ENTITIES";
	public static final String $NMTOKEN = $base + "NMTOKEN";
	public static final String $NMTOKENS = $base + "NMTOKENS";
	public static final String $integer = $base + "integer";
	public static final String $nonPositiveInteger = $base + "nonPositiveInteger";
	public static final String $long = $base + "long";
	public static final String $nonNegativeInteger = $base + "nonNegativeInteger";
	public static final String $negativeInteger = $base + "negativeInteger";
	public static final String $int = $base + "int";
	public static final String $unsignedLong = $base + "unsignedLong";
	public static final String $positiveInteger = $base + "positiveInteger";
	public static final String $short = $base + "short";
	public static final String $unsignedInt = $base + "unsignedInt";
	public static final String $byte = $base + "byte";
	public static final String $unsignedShort = $base + "unsignedShort";
	public static final String $unsignedByte = $base + "unsignedByte";

	public static final STND anyType = new STND($anyType);
	public static final STND anySimpleType = new STND($anySimpleType);
	public static final STND duration = new STND($duration);
	public static final STND dateTime = new STND($dateTime);
	public static final STND time = new STND($time);
	public static final STND date = new STND($date);
	public static final STND gYearMonth = new STND($gYearMonth);
	public static final STND gYear = new STND($gYear);
	public static final STND gMonthDay = new STND($gMonthDay);
	public static final STND gDay = new STND($gDay);
	public static final STND gMonth = new STND($gMonth);
	public static final STND _boolean = new STND($boolean);
	public static final STND base64Binary = new STND($base64Binary);
	public static final STND hexBinary = new STND($hexBinary);
	public static final STND _float = new STND($float);
	public static final STND decimal = new STND($decimal);
	public static final STND _double = new STND($double);
	public static final STND anyURI = new STND($anyURI);
	public static final STND QName = new STND($QName);
	public static final STND NOTATION = new STND($NOTATION);
	public static final STND string = new STND($string);
	public static final STND normalizedString = new STND($normalizedString);
	public static final STND token = new STND($token);
	public static final STND language = new STND($language);
	public static final STND Name = new STND($Name);
	public static final STND NCName = new STND($NCName);
	public static final STND ID = new STND($ID);
	public static final STND IDREF = new STND($IDREF);
	public static final STND IDREFS = new STND($IDREFS);
	public static final STND ENTITY = new STND($ENTITY);
	public static final STND ENTITIES = new STND($ENTITIES);
	public static final STND NMTOKEN = new STND($NMTOKEN);
	public static final STND NMTOKENS = new STND($NMTOKENS);
	public static final STND integer = new STND($integer);
	public static final STND nonPositiveInteger = new STND($nonPositiveInteger);
	public static final STND _long = new STND($long);
	public static final STND nonNegativeInteger = new STND($nonNegativeInteger);
	public static final STND negativeInteger = new STND($negativeInteger);
	public static final STND _int = new STND($int);
	public static final STND unsignedLong = new STND($unsignedLong);
	public static final STND positiveInteger = new STND($positiveInteger);
	public static final STND _short = new STND($short);
	public static final STND unsignedInt = new STND($unsignedInt);
	public static final STND _byte = new STND($byte);
	public static final STND unsignedShort = new STND($unsignedShort);
	public static final STND unsignedByte = new STND($unsignedByte);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{
        //anyType
        //anySimpleType
        new Nominal(duration, meta.DURATION, Role.DATATYPE),
        new Nominal(dateTime, meta.TIME_STAMP, Role.DATATYPE),
        new Nominal(time, meta.TIME_OF_DAY, Role.DATATYPE),
        new Nominal(date, meta.DATE, Role.DATATYPE),
        //gYearMonth
        new Nominal(gYear, meta.YEAR, Role.DATATYPE),
        //gMonthDay
        new Nominal(gDay, meta.DAY, Role.DATATYPE),
        new Nominal(gMonth, meta.MONTH, Role.DATATYPE),
        new Nominal(_boolean, meta.BOOLEAN, Role.DATATYPE),
        //base64Binary
        //hexBinary
        new Nominal(_float, meta.NUMBER, Role.DATATYPE),
        new Nominal(decimal, meta.NUMBER, Role.DATATYPE),
        new Nominal(_double, meta.NUMBER, Role.DATATYPE),
        new Nominal(anyURI, meta.UUID, Role.DATATYPE),
        //anyURI
        //QName
        //NOTATION
        new Nominal(string, meta.TEXT, Role.DATATYPE),
        new Nominal(normalizedString, meta.TEXT, Role.DATATYPE),
        //token
        //language
        //Name
        //NCName
        //ID
        //IDREF
        //IDREFS
        //ENTITY
        //ENTITIES
        //NMTOKEN
        //NMTOKENS
        new Nominal(integer, meta.COUNT, Role.DATATYPE),
        new Nominal(nonPositiveInteger, meta.COUNT, Role.DATATYPE),
        new Nominal(_long, meta.COUNT, Role.DATATYPE),
        new Nominal(nonNegativeInteger, meta.COUNT, Role.DATATYPE),
        new Nominal(negativeInteger, meta.COUNT, Role.DATATYPE),
        new Nominal(_int, meta.COUNT, Role.DATATYPE),
        new Nominal(unsignedLong, meta.COUNT, Role.DATATYPE),
        new Nominal(positiveInteger, meta.COUNT, Role.DATATYPE),
        new Nominal(_short, meta.COUNT, Role.DATATYPE),
        new Nominal(unsignedInt, meta.COUNT, Role.DATATYPE),
        //_byte
        new Nominal(unsignedShort, meta.COUNT, Role.DATATYPE)
        //unsignedByte
    };
    private static final Functional[] _predicates = new Functional[]{};
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
	public String[] getDays(){ return new String[0]; }
	public String[] getMonths(){ return new String[0]; }

}
