package org.flowr.framework.core.node.io.flow.data.binary;

import java.util.Iterator;
import java.util.List;

import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayField;


/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public class ByteArrayFieldSetBuffer implements ByteBuffer{

	private byte[] dataSchemaFormatArray  	= null;
	
	// Cursor for holding the addition at position
	private int cursor 						= 0;
	private int dataSchemaFormatArraySize	= 0;
	
	
	public ByteArrayFieldSetBuffer(byte[] dataSchemaFormatArray) {
		
		this.dataSchemaFormatArray 	= dataSchemaFormatArray;
		this.dataSchemaFormatArraySize 		= dataSchemaFormatArray.length;
	}
	
	public ByteArrayFieldSetBuffer(int bufferSize){
		this.dataSchemaFormatArraySize = bufferSize;
		dataSchemaFormatArray = new byte[bufferSize];
	}

	/**
	 * Adds the byte array to the relative position of cursor. cursor is maintained at the position of previous put.
	 * @param byteArray
	 */
	public void put(byte[] byteArray){
		
		if(byteArray != null && byteArray.length > 0){
			
			synchronized(dataSchemaFormatArray){
				
				System.arraycopy(byteArray, cursor, dataSchemaFormatArray, cursor, byteArray.length);		
				cursor += byteArray.length;
			}
					
		}
	}
	

	
	public int getBufferSize() {
		return dataSchemaFormatArraySize;
	}
	
	public byte[] get(){
		
		return this.dataSchemaFormatArray;
	}
	
	
	
	public List<ByteArrayField> bufferArrayToFixedlengthFieldList(List<ByteArrayField> fieldList){
				
		if(dataSchemaFormatArray.length > 0 && fieldList != null && !fieldList.isEmpty()){
			
			Iterator<ByteArrayField> byteArrayFieldIterator = fieldList.iterator();
			
			while(byteArrayFieldIterator.hasNext()){
				
				ByteArrayField byteArrayField = byteArrayFieldIterator.next();
				
				System.arraycopy(dataSchemaFormatArray, byteArrayField.getStartPosition(), byteArrayField.getByteArrayValue(), 
						byteArrayField.getEndPosition(), (byteArrayField.getEndPosition()-byteArrayField.getStartPosition()) );
			}			
		}		
		
		return fieldList;
	}
	
	/**
	 * Returns the current position of cursor during put operation
	 * @return
	 */
	public int getCursor() {
		return cursor;
	}
	
}
