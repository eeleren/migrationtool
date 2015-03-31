package com.ericsson.pc.migrationtool.interfaces;

import java.util.List;

import com.ericsson.pc.migrationtool.bean.Phone;

public interface Parser {
	
	public List<Phone> execute(String file);
	public List<Phone> execute();	
	public Parser getNewInstance();

}
