package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_CODE_DEFAULT;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.Iterator;
import java.util.Optional;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldLengthType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldOption;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldType;
import org.flowr.framework.core.node.io.flow.data.DataSchemaFormat;
import org.flowr.framework.core.node.io.flow.data.binary.ByteArrayFieldBuffer;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayField;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayFieldAttributeSet;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.FieldAttributeSet;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobBatchType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobCommitType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobConcurrencyType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobErrorHandlingType;
import org.flowr.framework.core.node.schema.JobSchema.BatchJob.JobExchangeMode;
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

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class JobSchemaFormat implements JobSchema{

	private String schemaFor							= null;
	private boolean isSchematicData						= false;	
	private IOFlowType  ioFlowType						= IOFlowType.NONE;
	private JobType jobType								= JobType.NONE;
	private JobSchedulingType jobSchedulingType			= JobSchedulingType.NONE;
	private JobProvider jobProvider 					= JobProvider.NONE;
	private JobExchangeMode 	jobExchangeMode			= JobExchangeMode.NONE;
	
	private JobExecutionType jobExecutionType 			= JobExecutionType.NONE;
	private JobConcurrencyType jobConcurrencyType		= JobConcurrencyType.NONE;
		
	private JobPersistenceType jobPersistenceType		= JobPersistenceType.NONE;
	private JobErrorHandlingType jobErrorHandlingType   = JobErrorHandlingType.NONE;
	private JobCommitType jobCommitType 				= JobCommitType.NONE;
	private JobRoleType jobRoleType 					= JobRoleType.NONE;	
	
	private JobState jobState							= JobState.NONE;
	private JobStatus jobStatus							= JobStatus.NONE;	
	
	
	private Optional<JobQueueType> jobQueueType			= Optional.empty(); 
	private Optional<JobBatchType> jobBatchType			= Optional.empty(); 
	private Optional<JobLevelizationType> jobLevelizationType 	= Optional.empty(); 
	
	private DataSchemaFormat dataSchemaFormat			= null;
	private FieldAttributeSet primaryFieldSet 			= new ByteArrayFieldAttributeSet();
	
	public JobSchemaFormat(JobSchemaFormat jsf,DataSchemaFormat dataSchemaFormat) 
			throws DataAccessException{
		
		if(
			dataSchemaFormat != null &&
			jsf.ioFlowType != null && jsf.ioFlowType != IOFlowType.NONE && 
			jsf.jobType != null && jsf.jobType != JobType.NONE &&
			jsf.jobProvider != null && jsf.jobProvider != JobProvider.NONE &&
			jsf.jobSchedulingType != null && jsf.jobSchedulingType != JobSchedulingType.NONE &&
			jsf.jobExchangeMode != null && jsf.jobExchangeMode != JobExchangeMode.NONE &&
			jsf.jobExecutionType != null && jobExecutionType != JobExecutionType.NONE &&			
			jsf.jobConcurrencyType != null && jsf.jobConcurrencyType != JobConcurrencyType.NONE &&
			jsf.jobPersistenceType != null && jsf.jobPersistenceType != JobPersistenceType.NONE &&
			jsf.jobErrorHandlingType != null && jsf.jobErrorHandlingType != JobErrorHandlingType.NONE &&
			jsf.jobCommitType != null && jsf.jobCommitType != JobCommitType.NONE &&
			jsf.jobRoleType != null && jsf.jobRoleType != JobRoleType.NONE &&					
			jsf.jobState != null && jsf.jobState != JobState.NONE &&
			jsf.jobStatus != null && jsf.jobStatus != JobStatus.NONE
		
		) {
		
			this.dataSchemaFormat 		= dataSchemaFormat;			
			this.ioFlowType 			= jsf.ioFlowType;
			this.jobType 				= jsf.jobType;
			this.jobProvider 			= jsf.jobProvider;
			this.jobSchedulingType 		= jsf.jobSchedulingType;
			this.jobExchangeMode 		= jsf.jobExchangeMode;
			this.jobExecutionType 		= jsf.jobExecutionType;
			this.jobConcurrencyType 	= jsf.jobConcurrencyType;
			this.jobPersistenceType 	= jsf.jobPersistenceType;
			this.jobErrorHandlingType 	= jsf.jobErrorHandlingType;
			this.jobCommitType 			= jsf.jobCommitType;
			this.jobRoleType 			= jsf.jobRoleType;
			
			this.jobLevelizationType 	= jsf.jobLevelizationType;
			this.jobQueueType			= jsf.jobQueueType;
			this.jobBatchType			= jsf.jobBatchType;
			
			this.jobState 				= jsf.jobState;
			this.jobStatus 				= jsf.jobStatus;
			
			isSchematicData				= true;
		}else {
			DataAccessException exception = new DataAccessException(
				ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
				ExceptionMessages.MSG_IO_INVALID_INPUT,
				"Invalid not present for SchematicData."
			);
			throw exception;
		}
	}
	
	
	
	public JobSchemaFormat(DataSchemaFormat dataSchemaFormat,IOFlowType  ioFlowType, JobType jobType,
			JobSchedulingType jobSchedulingType, JobProvider jobProvider,JobExchangeMode 	jobExchangeMode,
			JobExecutionType jobExecutionType,JobConcurrencyType jobConcurrencyType, JobPersistenceType jobPersistenceType,
			JobErrorHandlingType jobErrorHandlingType, JobCommitType jobCommitType,JobRoleType jobRoleType, 
			Optional<JobQueueType> jobQueueType,Optional<JobBatchType> jobBatchType,Optional<JobLevelizationType> jobLevelizationType,
			JobState jobState,JobStatus jobStatus
		) throws DataAccessException{
					
			if(
				dataSchemaFormat != null &&
				ioFlowType != null && ioFlowType != IOFlowType.NONE && 
				jobSchedulingType != null && jobSchedulingType != JobSchedulingType.NONE &&
				jobProvider != null && jobProvider != JobProvider.NONE &&
				jobExchangeMode != null && jobExchangeMode != JobExchangeMode.NONE &&
				jobExecutionType != null && jobExecutionType != JobExecutionType.NONE &&			
				jobConcurrencyType != null && jobConcurrencyType != JobConcurrencyType.NONE &&
				jobPersistenceType != null && jobPersistenceType != JobPersistenceType.NONE &&
				jobErrorHandlingType != null && jobErrorHandlingType != JobErrorHandlingType.NONE &&
				jobCommitType != null && jobCommitType != JobCommitType.NONE &&
				jobRoleType != null && jobRoleType != JobRoleType.NONE &&				
				jobState != null && jobState != JobState.NONE &&
				jobStatus != null && jobStatus != JobStatus.NONE
			) {
			
				this.dataSchemaFormat 		= dataSchemaFormat;
				
				this.primaryFieldSet.setPrimaryBitMap(this.dataSchemaFormat.primaryBitMap());
				this.primaryFieldSet.setSecondaryBitMap(this.dataSchemaFormat.secondaryBitMap());
												
				this.primaryFieldSet.addAttribute(asField(ioFlowType.name(), IOFlowType.valueOf(ioFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobSchedulingType.name(), JobSchedulingType.valueOf(jobSchedulingType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobProvider.name(), JobProvider.valueOf(jobProvider.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobExchangeMode.name(), JobExchangeMode.valueOf(jobExchangeMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobExecutionType.name(), JobExecutionType.valueOf(jobExecutionType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobConcurrencyType.name(), JobConcurrencyType.valueOf(jobConcurrencyType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobPersistenceType.name(),JobPersistenceType.valueOf(jobPersistenceType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobErrorHandlingType.name(), JobErrorHandlingType.valueOf(jobErrorHandlingType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobCommitType.name(), JobCommitType.valueOf(jobCommitType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobRoleType.name(), JobRoleType.valueOf(jobRoleType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(jobState.name(), JobState.valueOf(jobState.name()).getCode()));				
				this.primaryFieldSet.addAttribute(asField(jobStatus.name(), JobStatus.valueOf(jobStatus.name()).getCode()));	
				
				if(jobQueueType.isPresent()) {
					
					JobQueueType queueType =jobQueueType.get();
					
					this.primaryFieldSet.addAttribute(asField(queueType.name(), JobQueueType.valueOf(queueType.name()).getCode()));
				}
				
				if(jobBatchType.isPresent()) {
					
					JobBatchType batchType = jobBatchType.get();
					this.primaryFieldSet.addAttribute(asField(batchType.name(), JobBatchType.valueOf(batchType.name()).getCode()));
				}
				
				if(jobLevelizationType.isPresent()) {
					
					JobLevelizationType levelizationType = jobLevelizationType.get();
					
					this.primaryFieldSet.addAttribute(asField(levelizationType.name(), JobBatchType.valueOf(levelizationType.name()).getCode()));
				}
				
				isSchematicData				= true;
			}else {
				DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"Mandatory inputs not present for SchematicData."
				);
				throw exception;
			}
		}
	
	@Override
	public boolean isSchematicData() {
		return this.isSchematicData;
	}
		
	public int size() {
		
		int length = 0;
		
		if(this.dataSchemaFormat != null && this.primaryFieldSet != null) {
			try {
				length += this.primaryFieldSet.size();
				length += dataSchemaFormat.getData().length;
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
		
		return length;
	}
	
	private ByteArrayField asField(String name, byte opCode) {
		
		return new ByteArrayField(
				FieldLengthType.FIXED, 
				FieldOption.MANDATORY, 
				FieldType.NUMERIC, 
				name, 
				CHARSET, 
				new byte[] {opCode},				
				FIELD_CODE_DEFAULT, 
				FIELD_CODE_DEFAULT, 
				FIELD_DEFAULT,
				FIELD_CODE_DEFAULT);
	}
	
	public byte[] getData() throws DataAccessException{
		
		byte[] dataBuffer = null;
		
		if(isSchematicData) {
						
			ByteArrayFieldBuffer byteArrayFieldBuffer = new ByteArrayFieldBuffer(size());		
			
			Iterator<Attribute> primaryIter = primaryFieldSet.getAttributeList().iterator();
			
			while(primaryIter.hasNext()) {
				
				byteArrayFieldBuffer.put(((ByteArrayField)primaryIter.next()).getByteArrayValue());
			}		
				
			byteArrayFieldBuffer.put(dataSchemaFormat.getData());
			
			dataBuffer = byteArrayFieldBuffer.get();
			
		}else {
			
			DataAccessException exception = new DataAccessException(
				ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
				ExceptionMessages.MSG_IO_INVALID_INPUT,
				"PrimaryFieldSet not present for SchematicData."
			);
			throw exception;
		}
		return dataBuffer;
	}
		
	public void setSchemaFor(String schemaFor) {
		this.schemaFor = schemaFor;
	}
	
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("JobSchemaFormat{");
		builder.append("\n schemaFor : "+schemaFor);		
		builder.append("\n size : "+size());
		builder.append("\n"+this.primaryFieldSet.toString());

		builder.append("\n");

		builder.append(dataSchemaFormat.toString());		
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
