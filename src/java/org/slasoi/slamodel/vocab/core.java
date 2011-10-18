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
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public final class core implements Extensions{
	
	public static final String $base = "http://www.slaatsoi.org/coremodel#";
	
	/*--------- LOGICAL_OPS ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $and = $base + "and";
	public static final String $or = $base + "or";
	public static final String $not = $base + "not";
    // STND VERSIONS
    public static final STND and = new STND($and);
    public static final STND or = new STND($or);
    public static final STND not = new STND($not);
	
	/*--------- COMPARISON_OPS ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $identical_to = $base + "identical_to";
	public static final String $equals = $base + "equals";
	public static final String $not_equals = $base + "not_equals";
	public static final String $less_than = $base + "less_than";
	public static final String $less_than_or_equals = $base + "less_than_or_equals";
	public static final String $greater_than = $base + "greater_than";
	public static final String $greater_than_or_equals = $base + "greater_than_or_equals";
	public static final String $member_of = $base + "member_of";
	public static final String $subset_of = $base + "subset_of";
	public static final String $proper_subset_of = $base + "proper_subset_of";
	public static final String $superset_of = $base + "superset_of";
	public static final String $proper_superset_of = $base + "proper_superset_of";
	public static final String $matches = $base + "matches";
    public static final String $isa = $base + "isa";
	// STND VERSIONS
    public static final STND identical_to = new STND($identical_to);
    public static final STND equals = new STND($equals);
    public static final STND not_equals = new STND($not_equals);
    public static final STND less_than = new STND($less_than);
    public static final STND less_than_or_equals = new STND($less_than_or_equals);
    public static final STND greater_than = new STND($greater_than);
    public static final STND greater_than_or_equals = new STND($greater_than_or_equals);
    public static final STND member_of = new STND($member_of);
    public static final STND subset_of = new STND($subset_of);
    public static final STND proper_subset_of = new STND($proper_subset_of);
    public static final STND superset_of = new STND($superset_of);
    public static final STND proper_superset_of = new STND($proper_superset_of);
    public static final STND matches = new STND($matches);
    public static final STND isa = new STND($isa);
	
	/*--------- EVENT OPS ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $specialisation = $base + "specialisation";
	public static final String $intersection = $base + "intersection";
	public static final String $union = $base + "union";
	public static final String $invocation = $base + "invocation";
	public static final String $periodic = $base + "periodic";
	// public static final String $bounded_periodic = $base + "bounded_periodic";
	public static final String $schedule = $base + "schedule";
	public static final String $time = $base + "time";
	public static final String $fault = $base + "fault";
	public static final String $violated = $base + "violated";
	public static final String $warned = $base + "warned";
	public static final String $recovered = $base + "recovered";
    // STND VERSIONS
    public static final STND specialisation = new STND($specialisation);
    public static final STND intersection = new STND($intersection);
    public static final STND union = new STND($union);
    public static final STND invocation = new STND($invocation);
    public static final STND periodic = new STND($periodic);
    // public static final STND bounded_periodic = new STND($bounded_periodic);
    public static final STND schedule = new STND($schedule);
    public static final STND time = new STND($time);
    public static final STND fault = new STND($fault);
    public static final STND violated = new STND($violated);
    public static final STND warned = new STND($warned);
    public static final STND recovered = new STND($recovered);
	
	/*--------- ARITHMETIC OPS ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $add = $base + "add";
	public static final String $subtract = $base + "subtract";
	public static final String $multiply = $base + "multiply";
	public static final String $divide = $base + "divide";
	public static final String $modulo = $base + "modulo";
	public static final String $round = $base + "round";
    // STND VERSIONS
    public static final STND add = new STND($add);
    public static final STND subtract = new STND($subtract);
    public static final STND multiply = new STND($multiply);
    public static final STND divide = new STND($divide);
    public static final STND modulo = new STND($modulo);
    public static final STND round = new STND($round);
	
	/*--------- SET OPS ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $sum = $base + "sum";
	public static final String $std = $base + "std";
	public static final String $mean = $base + "mean";
	public static final String $median = $base + "median";
	public static final String $mode = $base + "mode";
	public static final String $max = $base + "max";
	public static final String $min = $base + "min";
	public static final String $count = $base + "count";
	public static final String $percent = $base + "percent";
    // STND VERSIONS
    public static final STND sum = new STND($sum);
    public static final STND std = new STND($std);
    public static final STND mean = new STND($mean);
    public static final STND median = new STND($median);
    public static final STND mode = new STND($mode);
    public static final STND max = new STND($max);
    public static final STND min = new STND($min);
    public static final STND count = new STND($count);
    public static final STND percent = new STND($percent);
	
	/*--------- TIME SERIES ---------------------------------------------------------------*/
	
	// STRINGS
	public static final String $series = $base + "series";
	public static final String $value = $base + "value";
    // STND VERSIONS
    public static final STND series = new STND($series);
    public static final STND value = new STND($value);
	
	/*--------- CONTEXT OPS ---------------------------------------------------------------*/
    
    // STRINGS
    public static final String $year_is = $base + "year_is";
    public static final String $month_is = $base + "month_is";
    public static final String $day_is = $base + "day_is";
    public static final String $time_is = $base + "time_is";
    // STND VERSIONS
    public static final STND year_is = new STND($year_is);
    public static final STND month_is = new STND($month_is);
    public static final STND day_is = new STND($day_is);
    public static final STND time_is = new STND($time_is);
	
	/*--------- MISC OPS ---------------------------------------------------------------*/

    // STRINGS
    public static final String $interval = $base + "interval";
    // STND VERSIONS
    public static final STND interval = new STND($interval);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{
        /*
         TODO : all these can probably be modelled as functions !!
         .. then: any function which evaluates to a boolean can be used as a constraint.
        */
        new Nominal(and, meta.BOOLEAN, Role.LOGICAL_OP),
        new Nominal(or, meta.BOOLEAN, Role.LOGICAL_OP),
        new Nominal(not, meta.BOOLEAN, Role.LOGICAL_OP),
        new Nominal(identical_to, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(equals, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(not_equals, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(less_than, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(less_than_or_equals, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(greater_than, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(greater_than_or_equals, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(member_of, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(subset_of, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(proper_subset_of, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(superset_of, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(proper_superset_of, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(matches, meta.BOOLEAN, Role.COMPARISON_OP),
        new Nominal(isa, meta.BOOLEAN, Role.COMPARISON_OP)
        
    };
    private static final Functional[] _predicates = new Functional[]{
        new Functional(add, new Category[]{ meta.NUMBER, meta.NUMBER }, meta.NUMBER, true),
        new Functional(subtract, new Category[]{ meta.NUMBER, meta.NUMBER }, meta.NUMBER, true),
        new Functional(multiply, new Category[]{ meta.NUMBER, meta.NUMBER }, meta.NUMBER, true),
        new Functional(divide, new Category[]{ meta.NUMBER, meta.NUMBER }, meta.NUMBER, true),
        new Functional(modulo, new Category[]{ meta.NUMBER, meta.NUMBER }, meta.NUMBER, true),
        new Functional(round, new Category[]{ meta.NUMBER, meta.COUNT }, meta.NUMBER, true),
        new Functional(sum, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(std, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(mean, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(median, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(mode, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(max, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(min, new Category[]{ new Set(meta.NUMBER) }, meta.NUMBER, true),
        new Functional(count, new Category[]{ new Set(meta.ANY) }, meta.QUANTITY, true),
        new Functional(count, new Category[]{ new Set(meta.ANY), meta.DOMAIN }, meta.QUANTITY, true),
        new Functional(percent, new Category[]{ new Set(meta.ANY), meta.DOMAIN }, meta.RATIO, true),
        new Functional(series, new Category[]{ meta.NUMBER, meta.EVENT }, new Set(meta.NUMBER), true),
        new Functional(series, new Category[]{ meta.NUMBER, meta.EVENT, meta.COUNT }, new Set(meta.NUMBER), true),
        new Functional(value, new Category[]{ meta.NUMBER, meta.EVENT }, meta.NUMBER, true),        
        new Functional(year_is, new Category[]{}, meta.YEAR, false),
        new Functional(month_is, new Category[]{}, meta.MONTH, false),
        new Functional(day_is, new Category[]{}, meta.DAY, false),
        new Functional(time_is, new Category[]{}, meta.TIME_OF_DAY, false),
        new Functional(interval, new Category[]{ meta.TIME_STAMP, meta.TIME_STAMP }, meta.DURATION, false),
        new Functional(specialisation, new Category[]{ meta.EVENT, meta.CONSTRAINT }, meta.EVENT, false),
        new Functional(intersection, new Category[]{ meta.EVENT, meta.EVENT }, meta.EVENT, false),
        new Functional(union, new Category[]{ meta.EVENT, meta.EVENT }, meta.EVENT, false),
        new Functional(invocation, new Category[]{ meta.SERVICE }, meta.EVENT, false), 
        new Functional(periodic, new Category[]{ meta.DURATION }, meta.TIME_SIGNAL, false),
        //new Functional(bounded_periodic, new Category[]{ meta.DURATION, meta.TIME_STAMP }, meta.TIME_SIGNAL, false),
        new Functional(schedule, new Category[]{ new Set(meta.TIME_STAMP) }, meta.TIME_SIGNAL, false),
        new Functional(time, new Category[]{ meta.TIME_STAMP }, meta.TIME_SIGNAL, false),
        new Functional(fault, new Category[]{ meta.MESSAGE_TYPE_SERVICE, new Set(meta.STND) }, meta.FAULT, false), 
        new Functional(violated, new Category[]{ meta.CONSTRAINT }, meta.SLA_STATE_CHANGE, false),
        new Functional(warned, new Category[]{ meta.CONSTRAINT, meta.RATIO }, meta.SLA_STATE_CHANGE, false),
        new Functional(recovered, new Category[]{ meta.CONSTRAINT }, meta.SLA_STATE_CHANGE, false),
        
        // TO SUPPORT a$ SLAT ...
        new Functional(count, new Category[]{ meta.SERVICE }, meta.QUANTITY, true),
        new Functional(subset_of, new Category[]{ meta.SERVICE }, meta.SERVICE, true),
        new Functional(proper_subset_of, new Category[]{ meta.SERVICE }, meta.SERVICE, true),
        
    };
    private String[] days = new String[]{
    	"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"	
    };
    private String[] months = new String[]{
        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"	
    };
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
	public String[] getDays(){ return days; }
	public String[] getMonths(){ return months; }

}
