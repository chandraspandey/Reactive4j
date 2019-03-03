package org.flowr.framework.core.node.io.flow.data;

import java.util.Optional;

import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * Marker interface for Schematic Data. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface DataSchema {

	public byte[] getData() throws DataAccessException;
	
	/**
	 * 
	 * Defines high level data flow type for data handling classification
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataFlowType implements ByteEnumerableType{
		NONE(0),
		CONTIGOUS(1),
		SEGMENTAL(2),
		BATCH(3),
		SPARSE(4),
		STREAM(5),
		INERMITTENT(6),
		STEP_TYPE(7),
		INTERACTIVE(8),
		COMMAND(9),
		MESSAGE(10),
		PROTOCOL(11),
		JOB(12);
		
		private byte code = 0;
		
		DataFlowType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataFlowType getType(int code) {
			
			DataFlowType dataFlowType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataFlowType = NONE;
					break;
				}case 1:{
					dataFlowType = CONTIGOUS;
					break;
				}case 2:{
					dataFlowType = SEGMENTAL;
					break;
				}case 3:{
					dataFlowType = BATCH;
					break;
				}case 4:{
					dataFlowType = SPARSE;
					break;
				}case 5:{
					dataFlowType = STREAM;
					break;
				}case 6:{
					dataFlowType = INERMITTENT;
					break;
				}case 7:{
					dataFlowType = STEP_TYPE;
					break;
				}case 8:{
					dataFlowType = INTERACTIVE;
					break;
				}case 9:{
					dataFlowType = COMMAND;
					break;
				}case 10:{
					dataFlowType = MESSAGE;
					break;
				}case 11:{
					dataFlowType = PROTOCOL;
					break;
				}case 12:{
					dataFlowType = JOB;
					break;
				}default :{
					dataFlowType = NONE;
					break;
				}				
			}
			
			return dataFlowType;
		}
	}
	
	public void setDataFlowType(DataFlowType dataFlowType);
	
	public DataFlowType getDataFlowType();
	
	boolean isSchematicData();
	
	/**
	 * Defines Padding as a characteristic that can be applied for Field(ByteArray) type.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public interface Padding {

		/**
		 * PaddingType that can be instrumented on byte array
		 */
		public enum PaddingType{
			NONE,
			LEFT,
			RIGHT
		}
		
		/**
		 * Returns byte array used in padding
		 * @return
		 */	
		public Optional<byte[]> getPadding();
		
		
		/**
		 * Provides a mechanism to enforce padding to the byte array value based on the ByteArrayField attributes. 
		 */
		public void enforcePadding();
		
		public void enforcePadding(byte[] paddingArray);
		
		/**
		 * Returns true is padding in use
		 * @return
		 */
		public boolean isPadded();
		
		/**
		 * Returns PaddingType in use 
		 * @return
		 */
		public PaddingType getPaddingType();
		
		/**
		 * Sets PaddingType in use 
		 * @return
		 */
		public void setPaddingType(PaddingType paddingType);

		public byte[] getPaddedByteArrayValue(); 
	}
	
	/**
	 * Defines Field as a Type for byte values.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public interface DataField extends Attribute,Padding{

		/*
		 * Supported FieldTypes that can be validated on individual bytes of an array
		 */
		public enum FieldType{
			CHARACTER,
			NUMERIC,
			ALPHANUMERIC,
			BINARY,
			BLOB,
			CLOB
		}
		
		/*
		 * Defines instrumentation capability in building a collection of input/output as per their availability in an
		 * operational context.
		 */
		public enum FieldOption{
			MANDATORY,
			OPTIONAL,
			CONDITIONAL
		}
		
		/*
		 * Defines the status of field in an I/O operation.
		 */
		public enum FieldStatus{
			EMPTY,
			PROVISION,
			FILLED
		}
		
		/*
		 * Defines length characteristics that a Field can assume 
		 */
		public enum FieldLengthType{
			FIXED,
			VARIABLE
		}
		
		
		/**
		 * Validates if the arrayValue confirms to byte array constraints
		 * @return
		 */
		public boolean isValid();
		
		/**
		 * Validates the array value based on Field Type set for the byte array.
		 * @return
		 */
		public boolean isValidFieldType();

		public FieldOption getFieldOption();
		
		public FieldStatus getFieldStatus();
		
		public boolean isPatternMatch(int toffset, String other, int offset, int len);
		
		public boolean isValuePresent(String fieldValue);
		
		public boolean isKeyPresent(String fieldName);
		
		public int getActualLength();
		
		public String getFieldName();

		public String getFieldValue();
		
		public byte[] getByteArrayValue();
		
		public void setByteArrayValue(byte[] byteArrayValue);

		public int getMinLength();
		
		public int getMaxLength();	
		
		public int getStartPosition();

		public int getEndPosition();

		public FieldType getFieldType();
		
		public FieldLengthType getFieldLengthType();
	}

}
