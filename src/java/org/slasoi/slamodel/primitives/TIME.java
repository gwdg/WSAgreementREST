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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slasoi.slamodel.vocab.units;

public class TIME implements ValueExpr{
	
	private static final long serialVersionUID = 1L;
    
    private transient Calendar _value = null; // see write/read serialisation overrides below
    private long _serialised_calendar = 0;
    
    /**
     * @deprecated
     */
    public TIME(){}
    
    public TIME(Calendar value){
        setValue(value);
    }
    
    /**
     * note: sets the value to a <b>copy</b> of the input Calendar
     * @param value
     */
    public void setValue(Calendar value){
        if (value == null) throw new IllegalArgumentException("No value specified");
        _value = (Calendar)value.clone();
    }
    
    /**
     * returns a <b>copy</b> of the Calendar value
     * @return
     */
    public Calendar getValue(){ return (Calendar)_value.clone(); }
    
    public boolean equals(Object o){
		if (this == o) return true;
        return o instanceof TIME && ((TIME)o).getValue().equals(_value);
    }
    
    public String toString(){
       return _value.getTime().toString();
    }
    
    // SERIALISATION OVERRIDES
    
    static final SimpleDateFormat sdf = new SimpleDateFormat(units.$date_format);
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        // OSGi seems to have problems serialising Claendar objects, so do this manually ...
        _serialised_calendar = _value.getTimeInMillis();
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        // we need to convert the "serialised calendar" that was saved with this object, back into a proper Calendar object
        _value = Calendar.getInstance();
        _value.setTimeInMillis(_serialised_calendar);
        _serialised_calendar = 0;
    }

}

