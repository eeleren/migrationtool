package com.ericsson.pc.migrationtool.interfaces;

import java.io.FileNotFoundException;
import java.util.List;

import com.ericsson.pc.migrationtool.bean.Model;


public interface Parser {
	
	public List<Model> execute(String file) throws FileNotFoundException;
	public List<Model> execute();	
	public Parser getNewInstance();

}
