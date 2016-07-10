/****************************************************************************
 *
 *   Copyright (c) 2016 Eike Mansfeld ecm@gmx.de. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 ****************************************************************************/

package com.comino.flight.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import com.comino.msp.model.DataModel;

public class KeyFigureMetaData {

	public String desc1;
	public String desc2;
	public String uom;
	public String mask;
	public int    hash;

	private String mspclass;
	private String mspfield;
	private String px4field;


	public KeyFigureMetaData() {
		this.desc1  = "None";
		this.hash   = 0;
	}


	public KeyFigureMetaData(String key, String desc, String uom, String mask) {
		this.desc1  = desc;
		this.uom    = uom;
		this.mask   = mask;
		this.hash   = key.toLowerCase().hashCode();
	}

	public void setMSPSource(String mspclass, String mspfield) {
		this.mspclass = mspclass;
		this.mspfield = mspfield;
	}

	public void setPX4Source(String px4field) {
		this.px4field = px4field;
		this.desc2    = px4field;
	}

	public float getValueFromMSPModel(DataModel m) throws Exception {
		Field mclass_field = m.getClass().getField(mspclass);
		Object mclass = mclass_field.get(m);
		Field mfield_field = mclass.getClass().getField(mspfield);
		return mfield_field.getFloat(mclass);
	}


	public float getValueFromPX4Model(Map<String,Object> data) {
		Object o = data.get(px4field);
		if(o instanceof Integer)
			return (float)(Integer)data.get(px4field);
		else
		    return (float)(Float)data.get(px4field);
	}

	public String toString() {
		return desc1;
	}

	public String toStringAll() {
		return desc1+": "+mspfield+"/"+px4field;
	}



}