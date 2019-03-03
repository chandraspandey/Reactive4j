package org.flowr.framework.core.node.schema;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface OperationSchema extends ProtocolSchema{

	/**
	 * Defines high level marker interface for RPC operations
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 * @author Chandra
	 *
	 */
	public interface Operation {

		/**
		 * Defines high level operation mode for operational mode classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationMode implements ByteEnumerableType{
			NONE(0),
			INIT(1),
			NEGOTATION(2),
			TRANSACTION(3),
			COMMIT(4),
			ROLLBACK(5);
			
			private byte code = 0;
			
			OperationMode(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationMode getType(int code) {
				
				OperationMode operationMode = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationMode = NONE;
						break;
					}case 1:{
						operationMode = INIT;
						break;
					}case 2:{
						operationMode = NEGOTATION;
						break;
					}case 3:{
						operationMode = TRANSACTION;
						break;
					}case 4:{
						operationMode = COMMIT;
						break;
					}case 5:{
						operationMode = ROLLBACK;
						break;
					}default :{
						operationMode = NONE;
						break;
					}			
				}
				
				return operationMode;
			}
		}
		
		/**
		 * Defines high level operation exchange mode for operational data exchange handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationExchangeMode implements ByteEnumerableType{
			NONE(0),
			COMMAND(1),
			ACK_REQUIRED(2),
			ACK_NOT_REQUIRED(3),
			RESPONSE_REQUIRED(4),
			RESPONSE_NOT_REQUIRED(5)			
			;
			
			private byte code = 0;
			
			OperationExchangeMode(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationExchangeMode getType(int code) {
				
				OperationExchangeMode operationExchangeMode = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationExchangeMode = NONE;
						break;
					}case 1:{
						operationExchangeMode = COMMAND;
						break;
					}case 2:{
						operationExchangeMode = ACK_REQUIRED;
						break;
					}case 3:{
						operationExchangeMode = ACK_NOT_REQUIRED;
						break;
					}case 4:{
						operationExchangeMode = RESPONSE_REQUIRED;
						break;
					}case 5:{
						operationExchangeMode = RESPONSE_NOT_REQUIRED;
						break;
					}default :{
						operationExchangeMode = NONE;
						break;
					}			
				}
				
				return operationExchangeMode;
			}
		}
		
		
		/**
		 * Defines high level operation type for operational classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationType implements ByteEnumerableType{
			NONE(0),
			COMMAND(1),
			READ(2),
			WRITE(3),
			FAILBACK(4),
			FAILFORWARD(5)			
			;
			
			private byte code = 0;
			
			OperationType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationType getType(int code) {
				
				OperationType operationType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationType = NONE;
						break;
					}case 1:{
						operationType = COMMAND;
						break;
					}case 2:{
						operationType = READ;
						break;
					}case 3:{
						operationType = WRITE;
						break;
					}case 4:{
						operationType = FAILBACK;
						break;
					}case 5:{
						operationType = FAILFORWARD;
						break;
					}default :{
						operationType = NONE;
						break;
					}			
				}
				
				return operationType;
			}
		}
		
		/**
		 * Defines high level operation type for operational dependency classification & handling for execution of unit
		 * operation
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationDependencyType implements ByteEnumerableType{
			NONE(0),
			DEPENDENT(1),
			INDEPENDENT(2);
			
			private byte code = 0;
			
			OperationDependencyType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationDependencyType getType(int code) {
				
				OperationDependencyType operationDependencyType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationDependencyType = NONE;
						break;
					}case 1:{
						operationDependencyType = DEPENDENT;
						break;
					}case 2:{
						operationDependencyType = INDEPENDENT;
						break;
					}default :{
						operationDependencyType = NONE;
						break;
					}			
				}
				
				return operationDependencyType;
			}
		}
		
		/**
		 * Defines high level operation Tree for determining the entire tree as executable based on 
		 * operational dependency classification based on role played by the unit node in a tree based resolution. 
		 * Provides a tiered view of operational dependency for modeling & loose coupling as executable operation 
		 * at aggregate level with tiered & flexible exception handling. This in reduces the need to re run surface area
		 * in case of failure, timeout & unresolved dependency resolution for complex environments.  
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationDependencyClassificationType implements ByteEnumerableType{
			NONE(0),
			ROOT(1),
			TREE(2),
			BRANCH(3),
			LEAF(4)
			;
			
			private byte code = 0;
			
			OperationDependencyClassificationType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationDependencyClassificationType getType(int code) {
				
				OperationDependencyClassificationType operationDependencyClassificationType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationDependencyClassificationType = NONE;
						break;
					}case 1:{
						operationDependencyClassificationType = ROOT;
						break;
					}case 2:{
						operationDependencyClassificationType = TREE;
						break;
					}case 3:{
						operationDependencyClassificationType = BRANCH;
						break;
					}case 4:{
						operationDependencyClassificationType = LEAF;
						break;
					}default :{
						operationDependencyClassificationType = NONE;
						break;
					}			
				}
				
				return operationDependencyClassificationType;
			}
		}
		
		/**
		 * Defines high level operation role type for sequencing operation based on dependency classification
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationDependencyRoleType implements ByteEnumerableType{
			NONE(0),
			ANTECEDENT(1),
			DEPENDENT(2);
			
			private byte code = 0;
			
			OperationDependencyRoleType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationDependencyRoleType getType(int code) {
				
				OperationDependencyRoleType operationDependencyRoleType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationDependencyRoleType = NONE;
						break;
					}case 1:{
						operationDependencyRoleType = ANTECEDENT;
						break;
					}case 2:{
						operationDependencyRoleType = DEPENDENT;
						break;
					}default :{
						operationDependencyRoleType = NONE;
						break;
					}			
				}
				
				return operationDependencyRoleType;
			}
		}
		
		/**
		 * Defines high level operation dependency status for determining execution based on dependency status.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationDependencyStatus implements ByteEnumerableType{
			NONE(0),
			RESOLVED(1),
			UNRESOLVED(2);
			
			private byte code = 0;
			
			OperationDependencyStatus(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationDependencyStatus getType(int code) {
				
				OperationDependencyStatus operationDependencyStatus = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationDependencyStatus = NONE;
						break;
					}case 1:{
						operationDependencyStatus = RESOLVED;
						break;
					}case 2:{
						operationDependencyStatus = UNRESOLVED;
						break;
					}default :{
						operationDependencyStatus = NONE;
						break;
					}			
				}
				
				return operationDependencyStatus;
			}
		}
		
		/**
		 * Defines high level operation type for operational classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationExecutionType implements ByteEnumerableType{
			NONE(0),
			REQUEST(1),
			RESPONSE(2),
			TRANSACTION(3)
			;
			
			private byte code = 0;
			
			OperationExecutionType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationExecutionType getType(int code) {
				
				OperationExecutionType operationExecutionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationExecutionType = NONE;
						break;
					}case 1:{
						operationExecutionType = REQUEST;
						break;
					}case 2:{
						operationExecutionType = RESPONSE;
						break;
					}case 3:{
						operationExecutionType = TRANSACTION;
						break;
					}default :{
						operationExecutionType = NONE;
						break;
					}			
				}
				
				return operationExecutionType;
			}
		}
		
		/**
		 * 
		 * Defines high level operation transaction type for data flow classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationTransactionType implements ByteEnumerableType{
			NONE(0),
			LOCAL(1),
			REMOTE(2),
			FEDERATED(3),
			TOKEN(4),
			SESSION(5),
			ATOMIC(6),
			BATCH(7);
			
			private byte code =0;
			
			OperationTransactionType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationTransactionType getType(int code) {
				
				OperationTransactionType operationTransactionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationTransactionType = NONE;
						break;
					}case 1:{
						operationTransactionType = LOCAL;
						break;
					}case 2:{
						operationTransactionType = REMOTE;
						break;
					}case 3:{
						operationTransactionType = FEDERATED;
						break;
					}case 4:{
						operationTransactionType = TOKEN;
						break;
					}case 5:{
						operationTransactionType = SESSION;
						break;
					}case 6:{
						operationTransactionType = ATOMIC;
						break;
					}case 7:{
						operationTransactionType = BATCH;
						break;
					}default :{
						operationTransactionType = NONE;
						break;
					}				
				}
				
				return operationTransactionType;
			}
		}
		
				
		/**
		 * 
		 * Defines high level operation state for state management classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationState implements ByteEnumerableType{
			
			NONE(0),
			COMMAND(1),
			
			REQUEST_INITIATED(10),		
			REQUEST_ACCEPTED(11),
			REQUEST_VERIFIED(12),
			REQUEST_ACKNOWLEDGEMENT_RECIEVED(13),
			REQUEST_DATA_SUBMITTED(14),
			REQUEST_PROCESSING(15),
			REQUEST_COMPLETED(16),
			REQUEST_ERROR(17),
			REQUEST_TIMEOUT(18),
			
			RESPONSE_INITIATED(64),		
			RESPONSE_ACCEPTED(65),
			RESPONSE_VERIFIED(66),
			RESPONSE_ACKNOWLEDGEMENT_RECIEVED(67),
			RESPONSE_DATA_SUBMITTED(68),
			RESPONSE_PROCESSING(69),
			RESPONSE_COMPLETED(70),
			RESPONSE_ERROR(71),
			RESPONSE_TIMEOUT(72);
			
			private byte code =0;
			
			OperationState(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationState getType(int code) {
				
				OperationState operationState = NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationState = NONE;
						break;
					}case 1:{
						operationState = COMMAND;
						break;
					}case 10:{
						operationState = REQUEST_INITIATED;
						break;
					}case 11:{
						operationState = REQUEST_ACCEPTED;
						break;
					}case 12:{
						operationState = REQUEST_VERIFIED;
						break;
					}case 13:{
						operationState = REQUEST_ACKNOWLEDGEMENT_RECIEVED;
						break;
					}case 14:{
						operationState = REQUEST_DATA_SUBMITTED;
						break;
					}case 15:{
						operationState = REQUEST_PROCESSING;
						break;
					}case 16:{
						operationState = REQUEST_COMPLETED;
						break;
					}case 17:{
						operationState = REQUEST_ERROR;
						break;
					}case 18:{
						operationState = REQUEST_TIMEOUT;
						break;
					}case 64:{
						operationState = RESPONSE_INITIATED;
						break;
					}case 65:{
						operationState = RESPONSE_ACCEPTED;
						break;
					}case 66:{
						operationState = RESPONSE_VERIFIED;
						break;
					}case 67:{
						operationState = RESPONSE_ACKNOWLEDGEMENT_RECIEVED;
						break;
					}case 68:{
						operationState = RESPONSE_DATA_SUBMITTED;
						break;
					}case 69:{
						operationState = RESPONSE_PROCESSING;
						break;
					}case 70:{
						operationState = RESPONSE_COMPLETED;
						break;
					}case 71:{
						operationState = RESPONSE_ERROR;
						break;
					}case 72:{
						operationState = RESPONSE_TIMEOUT;
						break;
					}default :{
						operationState = NONE;
						break;
					}							
				}
				
				return operationState;
			}
		}
		
		/**
		 * 
		 * Defines high level operation status for operation management classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum OperationStatus implements ByteEnumerableType{
			NONE(0),
			COMMAND(1),
			INITIATED(1),
			PROCESSING(2),
			COMPLETED(3),
			ERROR(4),
			TIMEOUT(5);
			
			private byte code =0;
			
			OperationStatus(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static OperationStatus getType(int code) {
				
				OperationStatus operationStatus = OperationStatus.NONE;
				
				switch((byte) code) {
					
					case 0:{
						operationStatus = NONE;
						break;
					}case 1:{
						operationStatus = COMMAND;
						break;
					}case 2:{
						operationStatus = INITIATED;
						break;
					}case 3:{
						operationStatus = PROCESSING;
						break;
					}case 4:{
						operationStatus = COMPLETED;
						break;
					}case 5:{
						operationStatus = ERROR;
						break;
					}case 6:{
						operationStatus = TIMEOUT;
						break;
					}default :{
						operationStatus = NONE;
						break;
					}			
				}
				
				return operationStatus;
			}
		}
		
	}
}
