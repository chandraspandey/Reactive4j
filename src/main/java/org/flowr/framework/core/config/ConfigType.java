package org.flowr.framework.core.config;


import java.lang.Enum;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ConfigType{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enum<?> get(Enum ENUM,String enumClass,String name){
		
		Class<Enum> klass = null;
		
		try {
			klass = (Class<Enum>) Class.forName(enumClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Enum.valueOf( klass, name);
	}
}
