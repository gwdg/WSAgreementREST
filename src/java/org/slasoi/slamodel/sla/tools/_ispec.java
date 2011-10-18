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
import java.util.List;

import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.service.Interface;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.service.Interface.Specification;
import org.slasoi.slamodel.service.Interface.Specification.Resolver;
import org.slasoi.slamodel.service.Interface.Operation.Property;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.InterfaceRef;
import org.slasoi.slamodel.vocab.meta;

class _ispec {
	
	static class _empty_resolver implements Resolver{

		public Specification getInterfaceSpecification(String name){ return null; }
		
	}
	
	Resolver resolver = null;
	
	_ispec(Resolver r){
		resolver = r != null? r: new _empty_resolver();
	}

	//--- DOWNLOAD INTERFACE SPECS -----------------------------------------------------------------
	
	void p_RESOLVE_INTERFACES(InterfaceDeclr[] idecs, _acc acc) throws Validator.Exception{
		if (idecs == null || idecs.length == 0) return; // deal with this later :)
		for (InterfaceDeclr idec : idecs){
			Interface iface = idec.getInterface();
			ID idec_id = idec.getId();
			String $idec_id = idec_id.getValue();
			acc.p_REGISTER_ID($idec_id, meta.INTERFACE);
			if (iface instanceof Specification){
				x_IFACE_SPEC($idec_id, (Specification)iface, acc, new ArrayList<String>(), 0);
			}else if (iface instanceof InterfaceRef){
				InterfaceRef ref = (InterfaceRef)iface;
				String $spec_name = ref.getInterfaceLocation().getValue();
				x_ISPEC_RESOLVE($idec_id, $spec_name, acc, new ArrayList<String>(), 0);
			}else if (iface instanceof ResourceType){
			    x_RES_TYPE($idec_id, (ResourceType)iface, acc);
			}else throw new Validator.Exception("IFS (0): Unrecognised Interface class: "+iface.getClass().getName(), iface);
		}
	}
    
    //--- RES_TYPES -----------------------------------------------------------------
    
    private String x_RES_TYPE(String $idec_id, ResourceType rt, _acc acc) throws Validator.Exception{
        String $name = rt.getName();
        if ($name == null){
            throw new Validator.Exception("IFS: resource-type has no name", rt);
        }
        acc.p_REGISTER_RES_TYPE($idec_id, rt);
        return $name;
    }
	
	//--- RESOLVE ISPECS-----------------------------------------------------------------
	
	private Specification x_ISPEC_RESOLVE(String $idec_id, String $spec_name, _acc acc, List<String> visited, int ply) throws Validator.Exception{
		Specification spec = acc.p_GET_REGISTERED_ISPEC($spec_name);
		if (spec != null){
			if (acc.p_ENT_FOR_ID($idec_id, null) == null){
				acc.p_REGISTER_ID($idec_id, meta.INTERFACE);
			}
			Operation[] ops = spec.getOperations();
			if (ops != null){
				for (Operation op : ops){
					String $op = op.getName().getValue();
					if (acc.p_ENT_FOR_ID($op, $idec_id) == null){
						acc.p_REGISTER_ID($idec_id+ID.$path_separator+$op, meta.OPERATION);
					}
				}
			}
			ID[] extd = spec.getExtended();
			if (extd != null){
				for (ID id : extd){
					x_ISPEC_RESOLVE($idec_id, id.getValue(), acc, visited, ply+1);
				}
			}
		}else{
		    spec = resolver.getInterfaceSpecification($spec_name);
			if (spec == null){
				throw new Validator.Exception("IFS ("+ply+"): unable to locate interface: '"+$spec_name+"'", $spec_name);
			}
			x_IFACE_SPEC($idec_id, spec, acc, visited, ply+1);
		}
		return spec;
	}
	
	//--- IFACE_SPECS -----------------------------------------------------------------
	
	private String x_IFACE_SPEC(String $idec_id, Specification spec, _acc acc, List<String> visited, int ply) throws Validator.Exception{
		String $spec_name = spec.getName();
		if ($spec_name == null){
			throw new Validator.Exception("IFS ("+ply+"): interface spec has no name", spec);
		}
		acc.p_REGISTER_ISPEC($idec_id, spec);
		visited.add($spec_name);
		ID[] exts = spec.getExtended();
		if (exts != null){
			for (ID ext : exts){
				String $ext_spec_name = ext.getValue();
				if (visited.contains($ext_spec_name)){
					throw new Validator.Exception("IFS ("+ply+"): circular extension in interface '"+$spec_name+"'", spec);
				}
				x_ISPEC_RESOLVE($idec_id, $ext_spec_name, acc, visited, ply+1);
			}
		}
		Operation[] ops = spec.getOperations();
		if (ops != null && ops.length>0){
			for (Operation op : ops){
				String $op = op.getName().getValue();
				acc.p_REGISTER_ID($idec_id+ID.$path_separator+$op, meta.OPERATION);
				x_IFACE_OP_PROPERTY($idec_id, $op, op.getInputs(), acc, ply);
				x_IFACE_OP_PROPERTY($idec_id, $op, op.getOutputs(), acc, ply);
				x_IFACE_OP_PROPERTY($idec_id, $op, op.getRelated(), acc, ply);
			}
		}
		return $spec_name;
	}
	
	private void x_IFACE_OP_PROPERTY(String $idec_id, String $op, Property[] ps, _acc acc, int ply) throws Validator.Exception{
		if (ps != null){
			for (Property p : ps){
				String $p = p.getName().getValue();
				acc.p_REGISTER_ID($idec_id+ID.$path_separator+$op+ID.$path_separator+$p, meta.PROPERTY);
			}
		}
		// default properties don't need to be registered
	}

}
