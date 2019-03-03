package org.flowr.framework.core.node.schema;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataDistributionType;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataMode;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataOperationType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataClassificationType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataCompressionType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataRestrictionType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataRetentionType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataSecurityType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobBatchType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobCommitType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobConcurrencyType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobErrorHandlingType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobExecutionType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobLevelizationType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobPersistenceType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobProvider;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobQueueType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobRoleType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobSchedulingType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobState;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobStatus;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageArchivalMode;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageConversionType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageErrorHandlingType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageExecutionType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageFlowType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessagePersistenceType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageProcessingState;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageProcessingStatus;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageTransformationType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessagingProvider;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessagingRoleType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationDependencyClassificationType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationDependencyRoleType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationDependencyStatus;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationDependencyType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationExchangeMode;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationExecutionType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationMode;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationState;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationStatus;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationTransactionType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationType;


/**
 * Protocol Schema defined by the framework as supported types as part of distributed computing which may
 * involve RPC. 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface ProtocolSchema {

	public boolean isSchematicData();
	
	/**
	 * ProtocolResult defined as an  Enum type that can be exchanged as part of RPC communication & can be 
	 * interpreted by client & server programs for discovering & exchanging supported protocols. This allows choosing
	 * of appropriate protocol supported by different hardware & software stacks.  
	 * 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 */
	public enum ProtocolResult implements ByteEnumerableType{		
		UNDEFINED(0),
		SUPPORTED(1),
		UNSUPPORTED(2),
		RESTRICTED(3),
		NOT_ALLOWED(4)
		;
		
		private byte code = 0;
		
		ProtocolResult(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}		
		
		public static ProtocolResult getType(int code) {
			
			ProtocolResult protocolResult = UNDEFINED;
			
			switch((byte) code) {
				
				case 0:{
					protocolResult = UNDEFINED;
					break;
				}case 1:{
					protocolResult = SUPPORTED;
					break;
				}case 2:{
					protocolResult = UNSUPPORTED;
					break;
				}case 3:{
					protocolResult = RESTRICTED;
					break;
				}case 4:{
					protocolResult = NOT_ALLOWED;
					break;
				}default :{
					protocolResult = UNDEFINED;
					break;
				}			
			}
			
			return protocolResult;
		}
	}
	/**
	 * ProtocolExchangeType defined as an exchange of Enum type that can be exchanged as part of RPC communication 
	 * & can be interpreted by client & server programs. 
	 *  
	 * 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 */
	public enum ProtocolExchangeType implements ByteEnumerableType{		
		CONFIGURATION(0),
		HEALTHCHECK(1),
		INQUIRY_REQUEST(2),
		INQUIRY_RESPONSE(3),		
		TRANSFER(4),
		UPLOAD(5),
		DOWNLOAD(6)
		;
		
		private byte code = 0;
		
		ProtocolExchangeType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}		
		
		public static ProtocolExchangeType getType(int code) {
			
			ProtocolExchangeType protocolExchangeType = INQUIRY_REQUEST;
			
			switch((byte) code) {
				
				case 0:{
					protocolExchangeType = CONFIGURATION;
					break;
				}case 1:{
					protocolExchangeType = HEALTHCHECK;
					break;
				}case 2:{
					protocolExchangeType = INQUIRY_REQUEST;
					break;
				}case 3:{
					protocolExchangeType = INQUIRY_RESPONSE;
					break;
				}case 4:{
					protocolExchangeType = TRANSFER;
					break;
				}case 5:{
					protocolExchangeType = UPLOAD;
					break;
				}case 6:{
					protocolExchangeType = DOWNLOAD;
					break;
				}default :{
					protocolExchangeType = INQUIRY_REQUEST;
					break;
				}			
			}
			
			return protocolExchangeType;
		}
	}
	
	
	/**
	 * Defined Protocols as part supported Protocol Schema. Each Protocol in turn is enclosed full fledged enum type.  
	 * Types :
	 * 0-49 		: Protocol Schema support
	 * 50-99 		: Network Schema support
	 * 100-149 		: Operation Schema support
	 * 150-199 		: Broker Schema support
	 * 200-249 		: Workflow Schema support
	 * 250-299 		: Data(SQL, NoSQL etc.) Schema support
	 * 300-399 		: Command Schema support
	 * 400-499 		: Cloud/DataCenter  Schema support
	 * 500-549 		: Message Schema support
	 * 550-599 		: Job  Schema support
	 * 600-649 		: Stream  Schema support
	 * 650-699 		: Cache  Schema support
	 * 700-749 		: Cluster  Schema support  
	 * 750-799 		: Agent  Schema support
	 * 800-849 		: Collector  Schema support
	 * 850-899 		: Container  Schema support
	 * 900-949 		: DNS  Schema support
	 * 1000-1049 	: LDAP  Schema support
	 * 1050-1099 	: Proxy  Schema support
	 * 1100-1149 	: Routing  Schema support
	 * 1150-1199 	: Sidecar  Schema support
	 * 1200-1299 	: HTTP  Schema support
	 * 1300-1399 	: SOAP  Schema support
	 * 1400-1499 	: XMPP  Schema support
	 * 1500-1549 	: Hardware(Specification, Version etc.)  Schema support
	 * 1550-1599 	: Software(OS Specification, Version etc.)  Schema support
	 * 1600-1649 	: File Schema support
	 * 1640-1699 	: Media(Audio, Video, Codec, Image etc.)  Schema support
	 * 2000-2099 	: Miscellaneous/Custom support
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 */
	public enum ProtocolSupport implements ByteEnumerableType{
		ProtocolResult(ProtocolResult.class,0),
		ProtocolExchangeType(ProtocolExchangeType.class,1),
		
		OperationMode(OperationMode.class,100),
		OperationExchangeMode(OperationExchangeMode.class,101),
		OperationType(OperationType.class,102),
		OperationDependencyType(OperationDependencyType.class,103),
		OperationDependencyClassificationType(OperationDependencyClassificationType.class,104),
		OperationDependencyRoleType(OperationDependencyRoleType.class,105),
		OperationDependencyStatus(OperationDependencyStatus.class,106),
		OperationExecutionType(OperationExecutionType.class,107),
		OperationTransactionType(OperationTransactionType.class,108),
		OperationState(OperationState.class,109),
		OperationStatus(OperationStatus.class,110),
		
		DataMode(DataMode.class,250),
		DataOperationType(DataOperationType.class,251),
		DataDistributionType(DataDistributionType.class,252),
		DataCompressionType(DataCompressionType.class,253),
		DataSecurityType(DataSecurityType.class,254),
		DataClassificationType(DataClassificationType.class,255),
		DataRetentionType(DataRetentionType.class,256),
		DataRestrictionType(DataRestrictionType.class,257),
		
		MessageFlowType(MessageFlowType.class,500),
		MessageConversionType(MessageConversionType.class,501),
		MessagingProvider(MessagingProvider.class,502),
		MessagePersistenceType(MessagePersistenceType.class,503),
		MessageErrorHandlingType(MessageErrorHandlingType.class,504),
		MessagingRoleType(MessagingRoleType.class,505),
		MessageTransformationType(MessageTransformationType.class,506),
		MessageExecutionType(MessageExecutionType.class,507),
		MessageArchivalMode(MessageArchivalMode.class,508),
		MessageProcessingState(MessageProcessingState.class,509),
		MessageProcessingStatus(MessageProcessingStatus.class,510),
		
		JobType(JobType.class,550),
		JobSchedulingType(JobSchedulingType.class,551),
		JobLevelizationType(JobLevelizationType.class,552),
		JobQueueType(JobQueueType.class,553),
		JobBatchType(JobBatchType.class,554),
		JobExecutionType(JobExecutionType.class,555),
		JobConcurrencyType(JobConcurrencyType.class,556),
		JobProvider(JobProvider.class,557),
		JobPersistenceType(JobPersistenceType.class,558),
		JobErrorHandlingType(JobErrorHandlingType.class,559),
		JobCommitType(JobCommitType.class,560),
		JobRoleType(JobRoleType.class,561),
		JobState(JobState.class,562),
		JobStatus(JobStatus.class,563),
		

		;
		
		private int code = 0;
		
		ProtocolSupport(Class<? extends ByteEnumerableType> byteEnumerableTypeKlass,int code){
			
			this.code = code;
		}

		/**
		 * Default implementation, not to be used for ProtocolSupport having values of Integer range to accomodate 
		 * large use cases which exceeds the defined Byte range by design. 
		 */
		@Override
		public byte getCode() {
			
			return Byte.MIN_VALUE;
		}	

		/**
		 * Returns the Code used as part of supported protocol lookup	
		 * @param code
		 * @return
		 */
		public static ProtocolSupport getType(int code) {
			
			ProtocolSupport protocol = ProtocolResult;
			
			switch(code) {
				
				case 0:{
					protocol = ProtocolResult;
					break;
				}		
			}
			
			return protocol;
		}
		
		/**
		 * Returns the Enum type as protocol lookup by name 
		 * @param name
		 * @return
		 */
		public static Enum<?> getType(String name) {
			
			Enum<?> protocol = ProtocolResult;
			
			
			Iterator<ProtocolSupport> protocolIter = Arrays.asList(values()).iterator();
			
			while(protocolIter.hasNext()) {
				
				ProtocolSupport protocolType =protocolIter.next();
				
				if(protocolType.name().equals(name)) {
					
					protocol = protocolType;
					break;
				}
			}			
			
			return protocol;
		}
		
		/**
		 * Returns the Enum type as protocol lookup with generic type 
		 * @param name
		 * @return
		 */
		
		public static Enum<?> getType(Enum<?> enumType) {
			
			Enum<?> protocol = ProtocolResult;			
			
			Iterator<ProtocolSupport> protocolIter = Arrays.asList(values()).iterator();
			
			while(protocolIter.hasNext()) {
				
				ProtocolSupport protocolType =protocolIter.next();
				
				if(protocolType.getClass().getName().equals(enumType.getClass().getName())) {
					
					protocol = protocolType;
					break;
				}
			}			
			
			return protocol;
		}
		
		/**
		 * Returns protocol code as defined in the schema
		 * @return
		 */
		public int getProtocolCode() {
			
			return code;
		}	
					
		/**
		 * 
		 * Defines high level protocol exchange state for state negotiation management classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum ProtocolExchangeState implements ByteEnumerableType{
			
			NONE(0),
			EXCHANGE_INITIATED(1),		
			EXCHANGE_ACCEPTED(2),
			EXCHANGE_VERIFIED(3),
			EXCHANGE_ACKNOWLEDGEMENT_RECIEVED(4),
			EXCHANGE_PROTOCOL_SUBMITTED(5),
			EXCHANGE_PROCESSING(6),
			EXCHANGE_COMPLETED(7),
			EXCHANGE_ERROR(8),
			EXCHANGE_TIMEOUT(9);
			
			private byte code =0;
			
			ProtocolExchangeState(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static ProtocolExchangeState getType(int code) {
				
				ProtocolExchangeState protocolExchangeState = NONE;
				
				switch((byte) code) {
					
					case 0:{
						protocolExchangeState = NONE;
						break;
					}case 1:{
						protocolExchangeState = EXCHANGE_INITIATED;
						break;
					}case 2:{
						protocolExchangeState = EXCHANGE_ACCEPTED;
						break;
					}case 3:{
						protocolExchangeState = EXCHANGE_VERIFIED;
						break;
					}case 4:{
						protocolExchangeState = EXCHANGE_ACKNOWLEDGEMENT_RECIEVED;
						break;
					}case 5:{
						protocolExchangeState = EXCHANGE_PROTOCOL_SUBMITTED;
						break;
					}case 6:{
						protocolExchangeState = EXCHANGE_PROCESSING;
						break;
					}case 7:{
						protocolExchangeState = EXCHANGE_COMPLETED;
						break;
					}case 8:{
						protocolExchangeState = EXCHANGE_ERROR;
						break;
					}case 9:{
						protocolExchangeState = EXCHANGE_TIMEOUT;
						break;					
					}default :{
						protocolExchangeState = NONE;
						break;
					}							
				}
				
				return protocolExchangeState;
			}
		}
		
		/**
		 * 
		 * Defines high level protocol exchange status for protocol management classification & handling.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum ProtocolExchangeStatus implements ByteEnumerableType{
			NONE(0),
			INQUIRY(1),
			INITIATED(2),
			PROCESSING(3),
			COMPLETED(4),
			ERROR(5),
			TIMEOUT(6);
			
			private byte code =0;
			
			ProtocolExchangeStatus(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static ProtocolExchangeStatus getType(int code) {
				
				ProtocolExchangeStatus protocolExchangeStatus = ProtocolExchangeStatus.NONE;
				
				switch((byte) code) {
					
					case 0:{
						protocolExchangeStatus = NONE;
						break;
					}case 1:{
						protocolExchangeStatus = INQUIRY;
						break;
					}case 2:{
						protocolExchangeStatus = INITIATED;
						break;
					}case 3:{
						protocolExchangeStatus = PROCESSING;
						break;
					}case 4:{
						protocolExchangeStatus = COMPLETED;
						break;
					}case 5:{
						protocolExchangeStatus = ERROR;
						break;
					}case 6:{
						protocolExchangeStatus = TIMEOUT;
						break;
					}default :{
						protocolExchangeStatus = NONE;
						break;
					}			
				}
				
				return protocolExchangeStatus;
			}
		}
		
	}
	
	/**
	 * Provides Protocols supported as part of Protocol Schema as an EnumSet. 
	 * @return
	 */
	public static EnumSet<?> SupportedProtocols(){
		
		return EnumSet.copyOf(Arrays.asList(ProtocolSupport.values()));
	}
	
	/**
	 * Verifies if the Enum<?> is part of protocol definition of ProtocolSchema.
	 * @param enumType
	 * @return
	 */
	public static boolean contains(Enum<?> enumType) {
		
		boolean isPresent = false;			
		
		Iterator<ProtocolSupport> protocolIter = Arrays.asList(ProtocolSupport.values()).iterator();
		
		while(protocolIter.hasNext()) {
			
			ProtocolSupport protocolType =protocolIter.next();
			
			if(protocolType.getClass().getName().equals(enumType.getClass().getName())) {
				
				isPresent =  true;
				break;
			}
		}			
		
		return isPresent;
	}
}
