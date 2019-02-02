package org.flowr.framework.core.node.schema;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface JobSchema extends ProtocolSchema{

	public boolean isSchematicData();

	/**
	 * Defines high level marker interface for Job operations
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 * @author Chandra
	 *
	 */
	public interface BatchJob extends Operation{
		
		/**
		 * Defines high level job type for data operation for Queue or batch handler for operations
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobType implements ByteEnumerableType{
			NONE(0),
			QUEUE(1),
			BATCH(2)
			;
			
			private byte code = 0;
			
			JobType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobType getType(int code) {
				
				JobType jobType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobType = NONE;
						break;
					}case 1:{
						jobType = QUEUE;
						break;
					}case 2:{
						jobType = BATCH;
						break;
					}default :{
						jobType = NONE;
						break;
					}			
				}
				
				return jobType;
			}
		}
		
		/**
		 * Defines high level job scheduling type as configurable operation for Queue or batch operations. 
		 * JobSchedulingType of FIXED type are sticky configuration both at client & server end points & is persistent
		 * to database. JobSchedulingType of ON_DEMAND type requires Remote end point availability via URL, Socket or
		 * RPC connection for On demand execution.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobSchedulingType implements ByteEnumerableType{
			NONE(0),
			FIXED(1),
			ON_DEMAND(2)
			;
			
			private byte code = 0;
			
			JobSchedulingType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobSchedulingType getType(int code) {
				
				JobSchedulingType jobSchedulingType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobSchedulingType = NONE;
						break;
					}case 1:{
						jobSchedulingType = FIXED;
						break;
					}case 2:{
						jobSchedulingType = ON_DEMAND;
						break;
					}default :{
						jobSchedulingType = NONE;
						break;
					}			
				}
				
				return jobSchedulingType;
			}
		}
		
		/**
		 * Defines high level job levelization type for balancing network IO & performance side effects of long running
		 * Queue or batch operations both at client & server end points. LEVLIZED Job would maintain constant throughput
		 * as determined by boundary conditions deemed suitable for data operations, can be configuration based or 
		 * algorithmic in implementation. NON_LEVELIZED may lead to peak & trough demands on system resources in data
		 * handling based on time of run.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobLevelizationType implements ByteEnumerableType{
			NONE(0),
			LEVELIZED(1),
			NON_LEVELIZED(2)
			;
			
			private byte code = 0;
			
			JobLevelizationType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobLevelizationType getType(int code) {
				
				JobLevelizationType jobLevelizationType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobLevelizationType = NONE;
						break;
					}case 1:{
						jobLevelizationType = LEVELIZED;
						break;
					}case 2:{
						jobLevelizationType = NON_LEVELIZED;
						break;
					}default :{
						jobLevelizationType = NONE;
						break;
					}			
				}
				
				return jobLevelizationType;
			}
		}
		
		/**
		 * Defines high level job queue type for data segregation & accumulator for Queue handler for operations at 
		 * client & server end points.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobQueueType implements ByteEnumerableType{
			NONE(0),
			PRIORITY(1),
			WEIGHTED_PRIORITY(2),
			NON_PRIORITY(3)
			;
			
			private byte code = 0;
			
			JobQueueType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobQueueType getType(int code) {
				
				JobQueueType jobQueueType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobQueueType = NONE;
						break;
					}case 1:{
						jobQueueType = PRIORITY;
						break;
					}case 2:{
						jobQueueType = WEIGHTED_PRIORITY;
						break;
					}case 3:{
						jobQueueType = NON_PRIORITY;
						break;
					}default :{
						jobQueueType = NONE;
						break;
					}			
				}
				
				return jobQueueType;
			}
		}
		
		/**
		 * Defines high level job batch type for determining data volume(Query boundaries) based on end use case. 
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobBatchType implements ByteEnumerableType{
			NONE(0),
			BATCH(1),
			MICRO_BATCH(2),
			SEGMENT_BATCH(3)
			;
			
			private byte code = 0;
			
			JobBatchType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobBatchType getType(int code) {
				
				JobBatchType jobBatchType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobBatchType = NONE;
						break;
					}case 1:{
						jobBatchType = BATCH;
						break;
					}case 2:{
						jobBatchType = MICRO_BATCH;
						break;
					}case 3:{
						jobBatchType = SEGMENT_BATCH;
						break;
					}default :{
						jobBatchType = NONE;
						break;
					}			
				}
				
				return jobBatchType;
			}
		}

		/**
		 * Defines high level job execution type for data operation(local or remote) for operation handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobExecutionType implements ByteEnumerableType{
			NONE(0),
			LOCAL(1),
			REMOTE(2)
			;
			
			private byte code = 0;
			
			JobExecutionType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobExecutionType getType(int code) {
				
				JobExecutionType jobExecutionType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobExecutionType = NONE;
						break;
					}case 1:{
						jobExecutionType = LOCAL;
						break;
					}case 2:{
						jobExecutionType = REMOTE;
						break;
					}default :{
						jobExecutionType = NONE;
						break;
					}			
				}
				
				return jobExecutionType;
			}
		}
		
		/**
		 * Defines high level job concurrency type for resource pool handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobConcurrencyType implements ByteEnumerableType{
			NONE(0),
			CONCURRENT(1),
			SEQUENTIAL(2)
			;
			
			private byte code = 0;
			
			JobConcurrencyType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobConcurrencyType getType(int code) {
				
				JobConcurrencyType jobConcurrencyType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobConcurrencyType = NONE;
						break;
					}case 1:{
						jobConcurrencyType = CONCURRENT;
						break;
					}case 2:{
						jobConcurrencyType = SEQUENTIAL;
						break;
					}default :{
						jobConcurrencyType = NONE;
						break;
					}			
				}
				
				return jobConcurrencyType;
			}
		}
		
		/**
		 * Defines high level job provider type for job provider handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobProvider implements ByteEnumerableType{
			NONE(0),
			SOCKET(1),
			RPC(2),			
			SERVICE(3),
			EXTERNAL(4);
			
			private byte code = 0;
			
			JobProvider(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobProvider getType(int code) {
				
				JobProvider jobProvider = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobProvider = NONE;
						break;
					}case 1:{
						jobProvider = SOCKET;
						break;
					}case 2:{
						jobProvider = RPC;
						break;
					}case 3:{
						jobProvider = SERVICE;
						break;
					}case 4:{
						jobProvider = EXTERNAL;
						break;
					}default :{
						jobProvider = NONE;
						break;
					}			
				}
				
				return jobProvider;
			}
		}
		
		/**
		 * Defines high level job persistence type for job persistence handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobPersistenceType implements ByteEnumerableType{
			NONE(0),
			DELETE_AFTER_RUN(1),
			ARCHIVE_AFTER_RUN(2),
			PERSIST(3);
			
			private byte code = 0;
			
			JobPersistenceType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobPersistenceType getType(int code) {
				
				JobPersistenceType jobPersistenceType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobPersistenceType = NONE;
						break;
					}case 1:{
						jobPersistenceType = DELETE_AFTER_RUN;
						break;
					}case 2:{
						jobPersistenceType = ARCHIVE_AFTER_RUN;
						break;
					}case 3:{
						jobPersistenceType = PERSIST;
						break;
					}default :{
						jobPersistenceType = NONE;
						break;
					}			
				}
				
				return jobPersistenceType;
			}
		}
		
		/**
		 * Defines high level job error handling type for error handling. Implementation should use as an array type 
		 * where more than one error handling mechanism(including manual intervention) may be required for end to 
		 * end data sync.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobErrorHandlingType implements ByteEnumerableType{
			NONE(0),
			INDEX_FOR_REPLAY(1),
			RETRY_FAILED_RECORDS(2),
			WRITE_TO_ERROR_DB(3),
			WRITE_TO_ERROR_LOG(4);
			
			private byte code = 0;
			
			JobErrorHandlingType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobErrorHandlingType getType(int code) {
				
				JobErrorHandlingType jobErrorHandlingType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobErrorHandlingType = NONE;
						break;
					}case 1:{
						jobErrorHandlingType = INDEX_FOR_REPLAY;
						break;
					}case 2:{
						jobErrorHandlingType = RETRY_FAILED_RECORDS;
						break;
					}case 3:{
						jobErrorHandlingType = WRITE_TO_ERROR_DB;
						break;
					}case 4:{
						jobErrorHandlingType = WRITE_TO_ERROR_LOG;
						break;
					}default :{
						jobErrorHandlingType = NONE;
						break;
					}			
				}
				
				return jobErrorHandlingType;
			}
		}
		
		/**
		 * Defines high level job commit type for data operation for modular exception handling in a dependency tree.
		 * Reduces interleaved data update if required at the level of LEAF, BRANCH or TREE
		 * level in case of failure. Job rerun would be limited to minimum surface area in complex tree structure 
		 * update. Roll back can be performed at granular levels.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobCommitType implements ByteEnumerableType{
			NONE(0),
			LEAF_UNIT(1),
			BRANCH_UNIT(2),
			TREE_UNIT(3),
			LEAF_AGGREGATE(4),
			BRANCH_AGGREGATE(5),
			TREE_AGGREGATE(6),			
			FOREST(7),
			SCHEMA(8)
			;
			
			private byte code = 0;
			
			JobCommitType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobCommitType getType(int code) {
				
				JobCommitType jobCommitType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobCommitType = NONE;
						break;
					}case 1:{
						jobCommitType = LEAF_UNIT;
						break;
					}case 2:{
						jobCommitType = BRANCH_UNIT;
						break;
					}case 3:{
						jobCommitType = TREE_UNIT;
						break;
					}case 4:{
						jobCommitType = LEAF_AGGREGATE;
						break;
					}case 5:{
						jobCommitType = BRANCH_AGGREGATE;
						break;
					}case 6:{
						jobCommitType = TREE_AGGREGATE;
						break;
					}case 7:{
						jobCommitType = FOREST;
						break;
					}case 8:{
						jobCommitType = SCHEMA;
						break;
					}default :{
						jobCommitType = NONE;
						break;
					}			
				}
				
				return jobCommitType;
			}
		}
		
		/**
		 * Defines high level job role type classification & handling
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobRoleType implements ByteEnumerableType{
			NONE(0),
			SOURCE(1),
			TARGET(2),
			REMEDIATION(3);
			
			private byte code = 0;
			
			JobRoleType(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobRoleType getType(int code) {
				
				JobRoleType jobRoleType = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobRoleType = NONE;
						break;
					}case 1:{
						jobRoleType = SOURCE;
						break;
					}case 2:{
						jobRoleType = TARGET;
						break;
					}case 3:{
						jobRoleType = REMEDIATION;
						break;
					}default :{
						jobRoleType = NONE;
						break;
					}			
				}
				
				return jobRoleType;
			}
		}
		
		/**
		 * Defines high level job state type for handling completion to address dependency resolution as deterministic
		 * programmable type as self healing characteristics that can be provided in complex execution paradigm. This 
		 * can enable tiered & re enforceable execution model in long running programs as fallback mechanisms in 
		 * autonomous/manual remediation.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobState implements ByteEnumerableType{
			NONE(0),
			RUNNING(1),
			PAUSING(2),
			RESUMING(3);
			
			private byte code = 0;
			
			JobState(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobState getType(int code) {
				
				JobState jobState = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobState = NONE;
						break;
					}case 1:{
						jobState = RUNNING;
						break;
					}case 2:{
						jobState = PAUSING;
						break;
					}case 3:{
						jobState = RESUMING;
						break;
					}default :{
						jobState = NONE;
						break;
					}			
				}
				
				return jobState;
			}
		}
		
		/**
		 * Defines high level job status as a type for governance for unit nodes for job execution in dependency tree.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum JobStatus implements ByteEnumerableType{
			NONE(0),
			EXECUTED(1),
			SKIPPED(2),
			UNEXECUTED(3);
			
			private byte code = 0;
			
			JobStatus(int code){
				
				this.code = (byte)code;
			}

			@Override
			public byte getCode() {
				
				return code;
			}	
			
			public static JobStatus getType(int code) {
				
				JobStatus jobStatus = NONE;
				
				switch((byte) code) {
					
					case 0:{
						jobStatus = NONE;
						break;
					}case 1:{
						jobStatus = EXECUTED;
						break;
					}case 2:{
						jobStatus = SKIPPED;
						break;
					}case 3:{
						jobStatus = UNEXECUTED;
						break;
					}default :{
						jobStatus = NONE;
						break;
					}			
				}
				
				return jobStatus;
			}
		}
	}
}
