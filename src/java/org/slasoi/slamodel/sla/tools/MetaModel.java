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

package org.slasoi.slamodel.sla.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.vocab.business;
import org.slasoi.slamodel.vocab.common;
import org.slasoi.slamodel.vocab.core;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.resources;
import org.slasoi.slamodel.vocab.sla;
import org.slasoi.slamodel.vocab.units;
import org.slasoi.slamodel.vocab.xsd;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Functional;
import org.slasoi.slamodel.vocab.ext.Nominal;
import org.slasoi.slamodel.vocab.ext.Nominal.Role;

public class MetaModel {

    private Map<UUID,Map<ID,Map<Role,Nominal>>> nominal_map 
    = Collections.synchronizedMap(new HashMap<UUID,Map<ID,Map<Role,Nominal>>>());
    private Map<UUID,Map<STND,Map<Integer,List<Functional>>>> function_map 
    = Collections.synchronizedMap(new HashMap<UUID,Map<STND,Map<Integer,List<Functional>>>>());
    private Map<UUID,Map<STND,Map<Integer,List<Functional>>>> event_map 
    = Collections.synchronizedMap(new HashMap<UUID,Map<STND,Map<Integer,List<Functional>>>>());
    private Map<UUID,List<String>> days_map
    = Collections.synchronizedMap(new HashMap<UUID,List<String>>());
    private Map<UUID,List<String>> months_map
    = Collections.synchronizedMap(new HashMap<UUID,List<String>>());
    
    MetaModel(){
        try{
            //System.out.println("-------------------------\nLoading common\n-------------------------");
            p_ADD_EXT(new common(), false);
            //System.out.println("-------------------------\nLoading business\n-------------------------");
            p_ADD_EXT(new business(), false);
            //System.out.println("-------------------------\nLoading core\n-------------------------");
            p_ADD_EXT(new core(), false);
            //System.out.println("-------------------------\nLoading sla\n-------------------------");
            p_ADD_EXT(new sla(), false);
            //System.out.println("-------------------------\nLoading resources\n-------------------------");
            p_ADD_EXT(new resources(), false);
            //System.out.println("-------------------------\nLoading units\n-------------------------");
            p_ADD_EXT(new units(), false);
            //System.out.println("-------------------------\nLoading xsd\n-------------------------");
            p_ADD_EXT(new xsd(), false);
        }catch(Exception e){
            throw new IllegalStateException(e);
        }
    }
    
    public Nominal getNominal(ID id, Role role){
        if (id != null && role != null){
            for (Map<ID,Map<Role,Nominal>> map1 : nominal_map.values()){
                Map<Role,Nominal> map2 = map1.get(id);
                if (map2 != null) return map2.get(role);
            }
        }
        return null;
    }
    
    public Functional getMetric(ID op, Category[] params){
        return x_GET_FUNCTIONAL(false, op, params, function_map);
    }
    
    public Functional getEvent(ID op, Category[] params){
        return x_GET_FUNCTIONAL(false, op, params, event_map);
    }
    
    private Functional x_GET_FUNCTIONAL(boolean test_both_ways, ID op, Category[] params, Map<UUID,Map<STND,Map<Integer,List<Functional>>>> map){
        if (op != null){
            for (Map<STND,Map<Integer,List<Functional>>> map1 : map.values()){
                Map<Integer,List<Functional>> map2 = map1.get(op);
                if (map2 != null){
                    List<Functional> fs = map2.get(params.length); 
                    if (fs != null) for (Functional f : fs){
                        Category[] cs = f.params(); 
                        if (cs.length == params.length){
                            int count = 0;
                            for (int i=0; i<cs.length; i++){
                                if (cs.equals(meta.UNTYPED) || params[i].isa(cs[i]) 
                                    || (test_both_ways && (params[i].equals(meta.UNTYPED) || cs[i].isa(params[i])))){
                                    count++;
                                }
                            }
                            if (count == cs.length) return f;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public int dayStringToDayInt(String s){
        if (s != null) for (List<String> list : days_map.values()){
            for (int i=0; i<list.size(); i++){
                String z = list.get(i);
                if (s.equals(z)) return i;
            }
        }
        return -1;
    }
    
    public int monthStringToMonthInt(String s){
        if (s != null) for (List<String> list : months_map.values()){
            for (int i=0; i<list.size(); i++){
                String z = list.get(i);
                if (s.equals(z)) return i;
            }
        }
        return -1;
    }
    
    private void p_VALIDATE_DOMAIN_SPECIFIC_TERM_ID(String id) throws Validator.Exception{
        if (id.startsWith(common.$base) || id.startsWith(core.$base) || id.startsWith(units.$base) || id.startsWith(sla.$base)){
            throw new Validator.Exception("Invalid extensions ID : "+id, null);
        }
    }
    
    void p_ADD_EXT(Extensions ext, boolean domain_specific) throws Validator.Exception{
    	x_ADD_EXT(ext, domain_specific, false); // checks for exceptions
    	x_ADD_EXT(ext, domain_specific, true); // commits
    }
    
    private boolean x_EXISTS_NOMINAL(ID id){
        for (Map<ID,Map<Role,Nominal>> map1 : nominal_map.values()){
            Map<Role,Nominal> map2 = map1.get(id);
            if (map2 != null) for (Nominal n : map2.values()){
                if (n.term().equals(id)) return true;
            }
        }
        return false;
    }
    
    private void x_ADD_EXT(Extensions ext, boolean domain_specific, boolean commit) throws Validator.Exception{
    	if (ext == null) throw new IllegalArgumentException();
        UUID ext_uuid = ext.getUuid();
        String $ext = ext_uuid.getValue(); 
        if (domain_specific){
            if (x_IS_BUILTIN($ext)){
                throw new Validator.Exception("Built-in definitions can't be overwritten : "+$ext, ext_uuid);
            }
        }
        Nominal[] ns = ext.getNominals();
        for (Nominal n : ns){
            Role[] roles = n.roles();
            for (Role role : roles){
                if ((role == Role.LOGICAL_OP || role == Role.COMPARISON_OP) && domain_specific){
                    throw new Validator.Exception("Domain-specific logical/comparison operators are not supported: "+n.toString(), n);
                }
            }
            ID term = n.term();
            if (x_EXISTS_NOMINAL(term)){
                throw new Validator.Exception(n.toString()+" conflicts with existing nominal", n);
            }
            if (commit){
                Map<ID,Map<Role,Nominal>> map1 = nominal_map.get(ext_uuid);
                if (map1 == null){
                    map1 = Collections.synchronizedMap(new HashMap<ID,Map<Role,Nominal>>());
                    nominal_map.put(ext_uuid, map1);
                }
                Map<Role,Nominal> map2 = map1.get(term);
                if (map2 == null){
                    map2 = Collections.synchronizedMap(new HashMap<Role,Nominal>());
                    map1.put(term, map2);
                }
                for (Role role : roles) map2.put(role, n);
            }
        }
        Functional[] fs = ext.getFunctionals();
        for (Functional f : fs){
            //System.out.println("MetaModel: x_ADD_EXT functional "+f.toString());
            if (f.eval() && domain_specific){
                throw new Validator.Exception("Domain-specific evaluation functions are not supported: "+f.toString(), f);
            }
            boolean events = f.category().isa(meta.EVENT);
            STND op = f.op();
            Category[] params = f.params();
            Functional existing = x_GET_FUNCTIONAL(true, op, params, events? event_map: function_map);
            if (existing != null){
                throw new Validator.Exception(f.toString()+" conflicts with existing functional", f);
            }
            if (commit){
                Map<UUID,Map<STND,Map<Integer,List<Functional>>>> all = events? event_map: function_map; 
                Map<STND,Map<Integer,List<Functional>>> map1 = all.get(ext_uuid);
                if (map1 == null){
                    map1 = Collections.synchronizedMap(new HashMap<STND,Map<Integer,List<Functional>>>());
                    all.put(ext_uuid, map1);
                }
                Map<Integer,List<Functional>> map2 = map1.get(op);
                if (map2 == null){
                    map2 = Collections.synchronizedMap(new HashMap<Integer,List<Functional>>());
                    map1.put(op, map2);
                }
                List<Functional> flist = map2.get(params.length);
                if (flist == null){
                    flist = Collections.synchronizedList(new ArrayList<Functional>());
                    map2.put(params.length, flist);
                }
                if (!flist.contains(f)) flist.add(f);
            }
        }
        if (commit){
            String[] days = ext.getDays();
            if (days != null && days.length > 0){
                List<String> list = days_map.get(ext_uuid);
                if (list == null){
                    list = new ArrayList<String>();
                    days_map.put(ext_uuid, list);
                }
                for (String day : days){
                    if (domain_specific) p_VALIDATE_DOMAIN_SPECIFIC_TERM_ID(day);
                    if (!list.contains(day)) list.add(day);
                }
            }
            String[] months = ext.getMonths();
            if (months != null && months.length > 0){
                List<String> list = months_map.get(ext_uuid);
                if (list == null){
                    list = new ArrayList<String>();
                    months_map.put(ext_uuid, list);
                }
                for (String month : months){
                    if (domain_specific) p_VALIDATE_DOMAIN_SPECIFIC_TERM_ID(month);
                    if (!list.contains(month)) list.add(month);
                }
            }
        }
    }
    
    void p_REMOVE_EXT(Extensions ext) throws Validator.Exception{
    	if (ext == null) throw new IllegalArgumentException();
        UUID ext_uuid = ext.getUuid();
        String $ext = ext_uuid.getValue(); 
        if (x_IS_BUILTIN($ext)){
            throw new Validator.Exception("Built-in definitions can't be removed : "+$ext, ext_uuid);
        }
        nominal_map.remove(ext_uuid);
        function_map.remove(ext_uuid);
        event_map.remove(ext_uuid);
        days_map.remove(ext_uuid);
        months_map.remove(ext_uuid);
    }
    
    private boolean x_IS_BUILTIN(String uuid){
        return uuid.equals(common.$base) 
        || uuid.equals(core.$base) 
        || uuid.equals(units.$base) 
        || uuid.equals(sla.$base) 
        || uuid.equals(resources.$base);
    }

}
