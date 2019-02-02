package org.flowr.framework.core.node.schema;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface MessageSchema extends ProtocolSchema{

	public boolean isSchematicData();

	/**
	 * Defines high level marker interface for Messaging operations
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 * @author Chandra
	 *
	 */
	public interface Messaging {

		/**
		 * Defines high level message flow type for upstream & downstream execution classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageFlowType implements ByteEnumerableType{
			NONE(0),
			UPSTREAM(1),
			DOWNSTREAM(2)
			;
			
			private byte code = 0;
			
			MessageFlowType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageFlowType getType(int code) {
				
				MessageFlowType messageFlowType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageFlowType = NONE;
						break;
					}case 1:{
						messageFlowType = UPSTREAM;
						break;
					}case 2:{
						messageFlowType = DOWNSTREAM;
						break;
					}default :{
						messageFlowType = NONE;
						break;
					}			
				}
				
				return messageFlowType;
			}
		}
		
		/**
		 * Defines high level message type conversion required for processing
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageConversionType implements ByteEnumerableType{
			NONE(0),
			REQUIRED(1),
			NOT_REQUIRED(2)
			;
			
			private byte code = 0;
			
			MessageConversionType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageConversionType getType(int code) {
				
				MessageConversionType messageConversionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageConversionType = NONE;
						break;
					}case 1:{
						messageConversionType = REQUIRED;
						break;
					}case 2:{
						messageConversionType = NOT_REQUIRED;
						break;
					}default :{
						messageConversionType = NONE;
						break;
					}			
				}
				
				return messageConversionType;
			}
		}
		
		/**
		 * Defines high level messaging provider type for message provider handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessagingProvider implements ByteEnumerableType{
			NONE(0),
			SOCKET(1),
			RPC(2),			
			SERVICE(3),
			EXTERNAL(4);
			
			private byte code = 0;
			
			MessagingProvider(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessagingProvider getType(int code) {
				
				MessagingProvider messagingProvider = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messagingProvider = NONE;
						break;
					}case 1:{
						messagingProvider = SOCKET;
						break;
					}case 2:{
						messagingProvider = RPC;
						break;
					}case 3:{
						messagingProvider = SERVICE;
						break;
					}case 4:{
						messagingProvider = EXTERNAL;
						break;
					}default :{
						messagingProvider = NONE;
						break;
					}			
				}
				
				return messagingProvider;
			}
		}
		
		/**
		 * Defines high level message persistence type for message persistence handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessagePersistenceType implements ByteEnumerableType{
			NONE(0),
			DELETE_ON_CONSUMPTION(1),
			ARCHIVE_ON_CONSUMPTION(2),
			PERSIST(3);
			
			private byte code = 0;
			
			MessagePersistenceType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessagePersistenceType getType(int code) {
				
				MessagePersistenceType messagePersistenceType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messagePersistenceType = NONE;
						break;
					}case 1:{
						messagePersistenceType = DELETE_ON_CONSUMPTION;
						break;
					}case 2:{
						messagePersistenceType = ARCHIVE_ON_CONSUMPTION;
						break;
					}case 3:{
						messagePersistenceType = PERSIST;
						break;
					}default :{
						messagePersistenceType = NONE;
						break;
					}			
				}
				
				return messagePersistenceType;
			}
		}
		
		/**
		 * Defines high level message error handling type for error handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageErrorHandlingType implements ByteEnumerableType{
			NONE(0),
			LOOPBACK_FOR_REPLAY(1),
			WRITE_TO_ERROR_TOPIC(2),
			WRITE_TO_ERROR_DB(3),
			WRITE_TO_ERROR_LOG(4);
			
			private byte code = 0;
			
			MessageErrorHandlingType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageErrorHandlingType getType(int code) {
				
				MessageErrorHandlingType messageErrorHandlingType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageErrorHandlingType = NONE;
						break;
					}case 1:{
						messageErrorHandlingType = LOOPBACK_FOR_REPLAY;
						break;
					}case 2:{
						messageErrorHandlingType = WRITE_TO_ERROR_TOPIC;
						break;
					}case 3:{
						messageErrorHandlingType = WRITE_TO_ERROR_DB;
						break;
					}case 4:{
						messageErrorHandlingType = WRITE_TO_ERROR_LOG;
						break;
					}default :{
						messageErrorHandlingType = NONE;
						break;
					}			
				}
				
				return messageErrorHandlingType;
			}
		}
		
		
		/**
		 * Defines high level messaging role type classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessagingRoleType implements ByteEnumerableType{
			NONE(0),
			CONSUMER(1),
			PUBLISHER(2),
			RELAY(3);
			
			private byte code = 0;
			
			MessagingRoleType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessagingRoleType getType(int code) {
				
				MessagingRoleType messagingRoleType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messagingRoleType = NONE;
						break;
					}case 1:{
						messagingRoleType = CONSUMER;
						break;
					}case 2:{
						messagingRoleType = PUBLISHER;
						break;
					}case 3:{
						messagingRoleType = RELAY;
						break;
					}default :{
						messagingRoleType = NONE;
						break;
					}			
				}
				
				return messagingRoleType;
			}
		}
		
		/**
		 * Defines high level message transformation type for transformation classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageTransformationType implements ByteEnumerableType{
			NONE(0),
			TRANSFORM_BEFORE_CONSUMPTION(1),
			ENRICH_BEFORE_CONSUMPTION(2),
			PASS_THROUGH(3),
			TRANSFORM_BEFORE_PUBLISHING(4),
			ENRICH_BEFORE_PUBLISHING(5),
			;
			
			private byte code = 0;
			
			MessageTransformationType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageTransformationType getType(int code) {
				
				MessageTransformationType messageTransformationType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageTransformationType = NONE;
						break;
					}case 1:{
						messageTransformationType = TRANSFORM_BEFORE_CONSUMPTION;
						break;
					}case 2:{
						messageTransformationType = ENRICH_BEFORE_CONSUMPTION;
						break;
					}case 3:{
						messageTransformationType = PASS_THROUGH;
						break;
					}case 4:{
						messageTransformationType = TRANSFORM_BEFORE_PUBLISHING;
						break;
					}case 5:{
						messageTransformationType = ENRICH_BEFORE_PUBLISHING;
						break;
					}default :{
						messageTransformationType = NONE;
						break;
					}			
				}				
								
				return messageTransformationType;
			}
		}
		
		/**
		 * Defines high level message execution type for local & remote execution classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageExecutionType implements ByteEnumerableType{
			NONE(0),
			LOCAL(1),
			REMOTE(2)
			;
			
			private byte code = 0;
			
			MessageExecutionType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageExecutionType getType(int code) {
				
				MessageExecutionType messageExecutionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageExecutionType = NONE;
						break;
					}case 1:{
						messageExecutionType = LOCAL;
						break;
					}case 2:{
						messageExecutionType = REMOTE;
						break;
					}default :{
						messageExecutionType = NONE;
						break;
					}			
				}
				
				return messageExecutionType;
			}
		}
		
		/**
		 * Defines high level message archival mode for archival process classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageArchivalMode implements ByteEnumerableType{
			NONE(0),
			TIME_TO_LIVE(1),
			TRIGGER(2),
			MANUAL(3)
			;
			
			private byte code = 0;
			
			MessageArchivalMode(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageArchivalMode getType(int code) {
				
				MessageArchivalMode messageExecutionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageExecutionType = NONE;
						break;
					}case 1:{
						messageExecutionType = TIME_TO_LIVE;
						break;
					}case 2:{
						messageExecutionType = TRIGGER;
						break;
					}case 3:{
						messageExecutionType = MANUAL;
						break;
					}default :{
						messageExecutionType = NONE;
						break;
					}			
				}
				
				return messageExecutionType;
			}
		}
		
		/**
		 * 
		 * Defines high level message processing state for state management classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageProcessingState implements ByteEnumerableType{
			
			NONE(0),			
			RECEIVED(1),		
			SENT(2),			
			ERROR(3),
			TIMEOUT(4);
			
			private byte code =0;
			
			MessageProcessingState(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageProcessingState getType(int code) {
				
				MessageProcessingState messageProcessingState = NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageProcessingState = NONE;
						break;
					}case 1:{
						messageProcessingState = RECEIVED;
						break;
					}case 2:{
						messageProcessingState = SENT;
						break;
					}case 3:{
						messageProcessingState = ERROR;
						break;
					}case 4:{
						messageProcessingState = TIMEOUT;
						break;
					}default :{
						messageProcessingState = NONE;
						break;
					}							
				}
				
				return messageProcessingState;
			}
		}
		
		/**
		 * 
		 * Defines high level message processing status for process management classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum MessageProcessingStatus implements ByteEnumerableType{
			NONE(0),
			INITIATED(1),
			PROCESSING(2),
			COMPLETED(3);
			
			private byte code =0;
			
			MessageProcessingStatus(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static MessageProcessingStatus getType(int code) {
				
				MessageProcessingStatus messageProcessingStatus = MessageProcessingStatus.NONE;
				
				switch((byte) code) {
					
					case 0:{
						messageProcessingStatus = NONE;
						break;
					}case 1:{
						messageProcessingStatus = INITIATED;
						break;
					}case 2:{
						messageProcessingStatus = PROCESSING;
						break;
					}case 3:{
						messageProcessingStatus = COMPLETED;
						break;
					}default :{
						messageProcessingStatus = NONE;
						break;
					}			
				}
				
				return messageProcessingStatus;
			}
		}
		
	}
}
