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

public class resources implements Extensions{
    
    public static final String $base = "http://www.slaatsoi.org/resources#";

    // STRING VERSIONS
    public static final String $vm_cores = $base + "vm_cores";
    public static final String $cpu_speed = $base + "cpu_speed";
    public static final String $memory = $base + "memory";
    public static final String $persistence = $base + "persistence";
    public static final String $vm_image = $base + "vm_image";
    
    // STND VERSIONS
    public static final STND vm_cores = new STND($vm_cores);
    public static final STND cpu_speed = new STND($cpu_speed);
    public static final STND memory = new STND($memory);
    public static final STND persistence = new STND($persistence);
    public static final STND vm_image = new STND($vm_image);
    
    /*--------- EXTENSIONS IMPLEMENTATION ---------------------------------------------------------------*/
    
    private UUID uuid = new UUID($base);
    private static final Nominal[] _nominals = new Nominal[]{
        // NOMINAL DEFINITIONS GO HERE
    };
    private static final Functional[] _predicates = new Functional[]{
        new Functional(vm_cores, new Category[]{ meta.SERVICE }, meta.COUNT, false),
        new Functional(cpu_speed, new Category[]{ meta.SERVICE }, meta.FREQUENCY, false),
        new Functional(memory, new Category[]{ meta.SERVICE }, meta.DATA_SIZE, false),
        new Functional(persistence, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
        new Functional(vm_image, new Category[]{ meta.SERVICE }, meta.UUID, false)
    };
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return _nominals; }
    public Functional[] getFunctionals(){ return _predicates; }
    public String[] getDays(){ return new String[0]; }
    public String[] getMonths(){ return new String[0]; }

}
