package org.flowr.framework.core.node.schema;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface DataProtocolSchema extends ProtocolSchema{

	/**
	 * Defines high level Data Security Type for handling data based on applied security.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataSecurityType implements ByteEnumerableType{
		NONE(0),
		ENCRYPTED(1),
		UNENCRYPTED(2),
		MASKED(3);
		
		private byte code = 0;
		
		DataSecurityType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataSecurityType getType(int code) {
			
			DataSecurityType dataSecurityType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataSecurityType = NONE;
					break;
				}case 1:{
					dataSecurityType = ENCRYPTED;
					break;
				}case 2:{
					dataSecurityType = UNENCRYPTED;
					break;
				}case 3:{
					dataSecurityType = MASKED;
					break;
				}default :{
					dataSecurityType = NONE;
					break;
				}			
			}
			
			return dataSecurityType;
		}
	}
	
	/**
	 * Defines high level Data Classification Type for handling data classification.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataClassificationType implements ByteEnumerableType{
		NONE(0),
		PUBLIC(1),
		RESTRICTED(2),
		CONFIDENTIAL(3),
		PII(4),
		PCI(5),
		REGULATORY(5),
		LEGAL(7),
		COMPLIANCE(8)
		;
		
		private byte code = 0;
		
		DataClassificationType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataClassificationType getType(int code) {
			
			DataClassificationType dataClassificationType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataClassificationType = NONE;
					break;
				}case 1:{
					dataClassificationType = PUBLIC;
					break;
				}case 2:{
					dataClassificationType = RESTRICTED;
					break;
				}case 3:{
					dataClassificationType = CONFIDENTIAL;
					break;
				}case 4:{
					dataClassificationType = PII;
					break;
				}case 5:{
					dataClassificationType = PCI;
					break;
				}case 6:{
					dataClassificationType = REGULATORY;
					break;
				}case 7:{
					dataClassificationType = LEGAL;
					break;
				}case 8:{
					dataClassificationType = COMPLIANCE;
					break;
				}default :{
					dataClassificationType = NONE;
					break;
				}			
			}
			
			return dataClassificationType;
		}
	}
	
	/**
	 * Defines high level Data Retention Type for handling data retention.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataRetentionType implements ByteEnumerableType{
		NONE(0),
		BUSINESS(1),
		COMPLIANCE(2)
		;
		
		private byte code = 0;
		
		DataRetentionType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataRetentionType getType(int code) {
			
			DataRetentionType dataRetentionType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataRetentionType = NONE;
					break;
				}case 1:{
					dataRetentionType = BUSINESS;
					break;
				}case 2:{
					dataRetentionType = COMPLIANCE;
					break;
				}default :{
					dataRetentionType = NONE;
					break;
				}			
			}
			
			return dataRetentionType;
		}
	}
	
	/**
	 * Defines high level Data restriction Type for handling data restriction related to data storage as applicable
	 * under applicable law for storage.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataRestrictionType implements ByteEnumerableType{
		NONE(0),
		GLOBAL(1),
		GDPR(2),
		COUNTRY(3)
		;
		
		private byte code = 0;
		
		DataRestrictionType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataRestrictionType getType(int code) {
			
			DataRestrictionType dataRestrictionType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataRestrictionType = NONE;
					break;
				}case 1:{
					dataRestrictionType = GLOBAL;
					break;
				}case 2:{
					dataRestrictionType = GDPR;
					break;
				}case 3:{
					dataRestrictionType = COUNTRY;
					break;
				}default :{
					dataRestrictionType = NONE;
					break;
				}			
			}
			
			return dataRestrictionType;
		}
	}
	
	/**
	 * Defines high level Data compression Type for handling data compression attributes. 
	 * 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum DataCompressionType implements ByteEnumerableType{
		NONE(0),
		COMPRESSED(1),
		UNCOMPRESSED(2)
		;
		
		private byte code = 0;
		
		DataCompressionType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static DataCompressionType getType(int code) {
			
			DataCompressionType dataCompressionType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					dataCompressionType = NONE;
					break;
				}case 1:{
					dataCompressionType = COMPRESSED;
					break;
				}case 2:{
					dataCompressionType = UNCOMPRESSED;
					break;
				}default :{
					dataCompressionType = NONE;
					break;
				}			
			}
			
			return dataCompressionType;
		}
	}
}
