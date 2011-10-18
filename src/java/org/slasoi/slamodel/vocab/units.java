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

public final class units implements Extensions{

    public static final String $date_format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public static final String $base = "http://www.slaatsoi.org/coremodel/units#";
	// area
	public static final String $mm2 = $base + "mm2";
	public static final String $um2 = $base + "um2";
	// data rate
	public static final String $b_per_s = $base + "b_per_s";
	public static final String $Kb_per_s = $base + "Kb_per_s";
	public static final String $Mb_per_s = $base + "Mb_per_s";
	// data size
	public static final String $bit = $base + "bit";
	public static final String $Byte = $base + "Byte";
	public static final String $KB = $base + "KB";
	public static final String $MB = $base + "MB";
	public static final String $GB = $base + "GB";
	// energy
	public static final String $J = $base + "J";
	public static final String $KJ = $base + "KJ";
	public static final String $Wh = $base + "Wh";
	public static final String $KWh = $base + "KWh";
	public static final String $mWh = $base + "mWh";
	// length
	public static final String $m = $base + "m";
	public static final String $cm = $base + "cm";
	public static final String $mm = $base + "mm";
	// duration
	public static final String $s = $base + "s";
	public static final String $tick = $base + "tick";
	public static final String $ms = $base + "ms";
	public static final String $us = $base + "us";
	public static final String $min = $base + "min";
	public static final String $hrs = $base + "hrs";
	public static final String $day = $base + "day";
	//B5 extension
	public static final String $weekend = $base + "weekend";
	//
    public static final String $week = $base + "week";
    public static final String $month = $base + "month";
    public static final String $year = $base + "year";
    // frequency
	public static final String $Hz = $base + "Hz";
	public static final String $KHz = $base + "KHz";
	public static final String $MHz = $base + "MHz";
	public static final String $GHz = $base + "GHz";
	public static final String $rpm = $base + "rpm";
	// power
	public static final String $W = $base + "W";
	public static final String $mW = $base + "mW";
	public static final String $kW = $base + "kW";
	// TX RATE
	public static final String $tx_per_s = $base + "tx_per_s";
	public static final String $tx_per_m = $base + "tx_per_m";
	public static final String $tx_per_h = $base + "tx_per_h";
	// weight
	public static final String $g = $base + "g";
	public static final String $mg = $base + "mg";
	public static final String $kg = $base + "kg";
	// currency
	public static final String $EUR = $base + "EUR";
	public static final String $USD = $base + "USD";
	// ratio
	public static final String $percentage = $base + "percentage";
	    
    // ... STND VERSIONS
	// area
    public static final STND mm2 = new STND($mm2);
    public static final STND um2 = new STND($um2);
    // data rate
    public static final STND b_per_s = new STND($b_per_s);
    public static final STND Kb_per_s = new STND($Kb_per_s);
    public static final STND Mb_per_s = new STND($Mb_per_s);
    // data size
    public static final STND bit = new STND($bit);
    public static final STND Byte = new STND($Byte);
    public static final STND KB = new STND($KB);
    public static final STND MB = new STND($MB);
    public static final STND GB = new STND($GB);
    // energy
    public static final STND J = new STND($J);
    public static final STND KJ = new STND($KJ);
    public static final STND Wh = new STND($Wh);
    public static final STND KWh = new STND($KWh);
    public static final STND mWh = new STND($mWh);
    // length
    public static final STND m = new STND($m);
    public static final STND cm = new STND($cm);
    public static final STND mm = new STND($mm);
    // time
    public static final STND s = new STND($s);
    public static final STND tick = new STND($tick);
    public static final STND ms = new STND($ms);
    public static final STND us = new STND($us);
    public static final STND min = new STND($min);
    public static final STND hrs = new STND($hrs);
	//B5 extension
	public static final STND weekend = new STND($weekend);
	//
    public static final STND day = new STND($day);
    public static final STND week = new STND($week);
    public static final STND month = new STND($month);
    public static final STND year = new STND($year);
    // freq
    public static final STND Hz = new STND($Hz);
    public static final STND KHz = new STND($KHz);
    public static final STND MHz = new STND($MHz);
    public static final STND GHz = new STND($GHz);
    public static final STND rpm = new STND($rpm);
    // power
    public static final STND W = new STND($W);
    public static final STND mW = new STND($mW);
    public static final STND kW = new STND($kW);
    // tx rate
    public static final STND tx_per_s = new STND($tx_per_s);
    public static final STND tx_per_m = new STND($tx_per_m);
    public static final STND tx_per_h = new STND($tx_per_h);
    // weight
    public static final STND g = new STND($g);
    public static final STND mg = new STND($mg);
    public static final STND kg = new STND($kg);
    // currency
    public static final STND EUR = new STND($EUR);
    public static final STND USD = new STND($USD);
    // ratio
    public static final STND percentage = new STND($percentage);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{
        new Nominal(mm2, meta.AREA, Role.DATATYPE),
        new Nominal(um2, meta.AREA, Role.DATATYPE),
        new Nominal(b_per_s, meta.DATA_RATE, Role.DATATYPE),
        new Nominal(Kb_per_s, meta.DATA_RATE, Role.DATATYPE),
        new Nominal(Mb_per_s, meta.DATA_RATE, Role.DATATYPE),
        new Nominal(bit, meta.DATA_SIZE, Role.DATATYPE),
        new Nominal(Byte, meta.DATA_SIZE, Role.DATATYPE),
        new Nominal(KB, meta.DATA_SIZE, Role.DATATYPE),
        new Nominal(MB, meta.DATA_SIZE, Role.DATATYPE),
        new Nominal(GB, meta.DATA_SIZE, Role.DATATYPE),
        new Nominal(J, meta.ENERGY, Role.DATATYPE),
        new Nominal(KJ, meta.ENERGY, Role.DATATYPE),
        new Nominal(Wh, meta.ENERGY, Role.DATATYPE),
        new Nominal(KWh, meta.ENERGY, Role.DATATYPE),
        new Nominal(mWh, meta.ENERGY, Role.DATATYPE),
        new Nominal(m, meta.LENGTH, Role.DATATYPE),
        new Nominal(cm, meta.LENGTH, Role.DATATYPE),
        new Nominal(mm, meta.LENGTH, Role.DATATYPE),
        new Nominal(s, meta.DURATION, Role.DATATYPE),
        new Nominal(tick, meta.DURATION, Role.DATATYPE),
        new Nominal(ms, meta.DURATION, Role.DATATYPE),
        new Nominal(us, meta.DURATION, Role.DATATYPE),
        new Nominal(min, meta.DURATION, Role.DATATYPE),
        new Nominal(hrs, meta.DURATION, Role.DATATYPE),
        new Nominal(day, meta.DURATION, Role.DATATYPE),
		//b5 extension
		new Nominal(weekend, meta.DURATION, Role.DATATYPE),
		//
        new Nominal(week, meta.DURATION, Role.DATATYPE),
        new Nominal(month, meta.DURATION, Role.DATATYPE),
        new Nominal(year, meta.DURATION, Role.DATATYPE),
        new Nominal(Hz, meta.FREQUENCY, Role.DATATYPE),
        new Nominal(KHz, meta.FREQUENCY, Role.DATATYPE),
        new Nominal(MHz, meta.FREQUENCY, Role.DATATYPE),
        new Nominal(GHz, meta.FREQUENCY, Role.DATATYPE),
        new Nominal(rpm, meta.FREQUENCY, Role.DATATYPE),
        new Nominal(percentage, meta.RATIO, Role.DATATYPE),
        new Nominal(W, meta.POWER, Role.DATATYPE),
        new Nominal(mW, meta.POWER, Role.DATATYPE),
        new Nominal(kW, meta.POWER, Role.DATATYPE),
        new Nominal(tx_per_s, meta.TX_RATE, Role.DATATYPE),
        new Nominal(tx_per_m, meta.TX_RATE, Role.DATATYPE),
        new Nominal(tx_per_h, meta.TX_RATE, Role.DATATYPE),
        new Nominal(g, meta.WEIGHT, Role.DATATYPE),
        new Nominal(mg, meta.WEIGHT, Role.DATATYPE),
        new Nominal(kg, meta.WEIGHT, Role.DATATYPE),
        new Nominal(EUR, meta.COST, Role.DATATYPE),
        new Nominal(USD, meta.COST, Role.DATATYPE),
        
    };
    private static final Functional[] _predicates = new Functional[]{};
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
	public String[] getDays(){ return new String[0]; }
	public String[] getMonths(){ return new String[0]; }

}
