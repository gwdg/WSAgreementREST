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

package org.slasoi.slamodel.primitives;

import org.slasoi.slamodel.vocab.core;
import org.slasoi.slamodel.vocab.xsd;

class _STND_bnf {

    static String p_STRING(STND stnd){
        if (stnd.equals(core.identical_to)) return "==";
        if (stnd.equals(core.equals)) return "=";
        if (stnd.equals(core.not_equals)) return "!=";
        if (stnd.equals(core.less_than)) return "<";
        if (stnd.equals(core.less_than_or_equals)) return "<=";
        if (stnd.equals(core.greater_than)) return ">";
        if (stnd.equals(core.greater_than_or_equals)) return ">=";
        if (stnd.equals(core.add)) return "+";
        if (stnd.equals(core.subtract)) return "-";
        if (stnd.equals(core.multiply)) return "*";
        if (stnd.equals(core.divide)) return "/";
        if (stnd.equals(core.modulo)) return "%";
        String $s = stnd.getValue();
        String $base = "";
        if ($s.startsWith(xsd.$base)) $base = "xsd:"; 
        String[] ss = stnd.getValue().split("#");
        return $base+ss[ss.length-1];
    }

}
