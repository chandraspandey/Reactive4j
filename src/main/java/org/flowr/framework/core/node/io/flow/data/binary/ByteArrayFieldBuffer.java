package org.flowr.framework.core.node.io.flow.data.binary;

import java.util.Arrays;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;

/**
 * Encapsulates byte array to provide capabilities to return discrete list of ByteArrayField. This class is not 
 * thread safe and operations should be externally restricted by synchronization.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public final class ByteArrayFieldBuffer implements ByteBuffer{

	private byte[] byteArrayBuffer  = null;
	
	// Cursor for holding the addition at position
	private int cursor 				= 0;
	private int bufferSize 			= 0;
	
	public ByteArrayFieldBuffer(int bufferSize){
		this.bufferSize = bufferSize;
		byteArrayBuffer = new byte[bufferSize];
	}

	/**
	 * Adds the byte array to the relative position of cursor. cursor is maintained at the position of previous put.
	 * @param byteFieldArray
	 */
	public void put(byte[] byteFieldArray) throws DataAccessException{
		
		if(byteFieldArray != null && byteFieldArray.length > 0 ){
			
			if((byteArrayBuffer.length - cursor) >= byteFieldArray.length) {
			
				synchronized(byteArrayBuffer){
					
					System.arraycopy(byteFieldArray, 0, byteArrayBuffer, cursor, byteFieldArray.length);		
					cursor += byteFieldArray.length;
				}
			}else {
				
				DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
						ExceptionMessages.MSG_IO_INVALID_INPUT,
						"Invalid array size of "+byteFieldArray.length+" for buffer capacity left as "+
						(byteArrayBuffer.length - cursor)+" for put operation."
				);
				throw exception;
			}
					
		}else {
			
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Byte Array as input not present for operation."
			);
			throw exception;
		}
	}
	
	/**
	 * Returns the byte array
	 * @return
	 */
	public byte[] get(){
		
		return this.byteArrayBuffer;
	}
	
	
	/**
	 * Prepends a new byte array to the start position of an array. Ignores the operation if the byte array is invalid.
	 * The cursor is reset to zero after the operation. The size is incremented with the length of new array.
	 * @param prependByteArray
	 * @return the byte array with prepended values
	 * @throws DataAccessException 
	 */
	public byte[] prepend(byte[] prependByteArray) throws DataAccessException{
		
		if(prependByteArray != null && prependByteArray.length > 0 ){			
			
			byte[] newBuffer = new byte[bufferSize+prependByteArray.length];
			
			// Prepend the addition
			System.arraycopy(prependByteArray, 0, newBuffer, 0, prependByteArray.length);	 
			
			// Copy the existing buffer
			System.arraycopy(byteArrayBuffer, 0, newBuffer, 
					prependByteArray.length, byteArrayBuffer.length);		
			
			
			// swap the buffer
			synchronized(byteArrayBuffer){
				
				byteArrayBuffer = newBuffer;
				bufferSize = byteArrayBuffer.length;
				cursor = 0;
			}
			
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Byte Array as input not present for operation."
			);
			throw exception;
		}
		
		return byteArrayBuffer;
	}
	
	/**
	 * Appends a new byte array to the end position of an array. Ignores the operation if the byte array is invalid.
	 * The cursor is reset to zero after the operation. 
	 * @param appendByteArray
	 * @return the byte array with appended values
	 * @throws DataAccessException 
	 */
	public byte[] append(byte[] appendByteArray) throws DataAccessException{
		
		if(appendByteArray != null && appendByteArray.length > 0 ){			
			
			byte[] newBuffer = new byte[bufferSize+appendByteArray.length];
						
			// Copy the existing array
			System.arraycopy(byteArrayBuffer, 0, newBuffer, 0, byteArrayBuffer.length);		
			
			// Append the new array
			System.arraycopy(appendByteArray, 0, newBuffer, byteArrayBuffer.length, appendByteArray.length);	 			
			
			// swap the buffer
			synchronized(byteArrayBuffer){
				
				byteArrayBuffer = newBuffer;
				bufferSize = byteArrayBuffer.length;
				cursor = 0;
			}
			
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Byte Array as input not present for operation."
			);
			throw exception;
		}
		
		return byteArrayBuffer;
	}
	
	/**
	 * Replaces the existing byte array at the begin position of an array. Ignores the operation if the byte array 
	 * or beginPosition is invalid.The cursor is reset to zero after the operation. 
	 * @param byteArray
	 * @return the byte array with replaced values
	 * @throws DataAccessException 
	 */
	public byte[] replace(byte[] byteArray,int beginPosition) throws DataAccessException{
		
		if(byteArray != null && byteArray.length > 0 && byteArray.length < byteArrayBuffer.length && beginPosition
			< byteArrayBuffer.length){			
			
		
			// replace in the existing array
			synchronized(byteArrayBuffer){
				
				System.arraycopy(byteArray, 0, byteArrayBuffer, beginPosition, byteArray.length);
				cursor = 0;
			}			
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Byte Array as input not present or beginPosition "+beginPosition+" beyond array length "
							+byteArrayBuffer.length+ " for operation."
			);
			throw exception;
		}
		
		return byteArrayBuffer;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	

	
	/**
	 * Returns slice of values from the byte array.
	 * @param startPosition
	 * @param length
	 * @return
	 * @throws DataAccessException 
	 */
	public byte[] slice(int startPosition, int length) throws DataAccessException{
		
		byte[] slice = null;
		
		if(startPosition < this.byteArrayBuffer.length && this.byteArrayBuffer.length < (startPosition+length)){
		
			slice = Arrays.copyOfRange(byteArrayBuffer, startPosition, startPosition+length);
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Invalid start position "+startPosition+" with length "+length+" for operation."
			);
			throw exception;
		}
		
		return slice;
	}
	
}
