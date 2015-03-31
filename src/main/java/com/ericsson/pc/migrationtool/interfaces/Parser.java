package com.ericsson.pc.migrationtool.interfaces;

import java.util.List;

import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.PhoneAsset;

public interface Parser {
	
	public List<Phone> execute();
	
	public Parser getNewInstance();

}
