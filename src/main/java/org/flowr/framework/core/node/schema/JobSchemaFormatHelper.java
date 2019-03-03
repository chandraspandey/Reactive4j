package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Optional;

import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldLengthType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldOption;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataFlowType;
import org.flowr.framework.core.node.io.flow.data.DataSchemaFormat;
import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.PrimaryBitMap;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.SecondaryBitMap;
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
 * Converts the Job Characteristics as Enumerable type in form of JobSchemaFormat.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class JobSchemaFormatHelper {
 
	public static JobSchemaFormat Job(IOFlowType  ioFlowType, JobType jobType,
			JobSchedulingType jobSchedulingType, JobProvider jobProvider,JobExchangeMode jobExchangeMode,
			JobExecutionType jobExecutionType,JobConcurrencyType jobConcurrencyType, JobPersistenceType jobPersistenceType,
			JobErrorHandlingType jobErrorHandlingType, JobCommitType jobCommitType,JobRoleType jobRoleType, 
			Optional<JobQueueType> jobQueueType,Optional<JobBatchType> jobBatchType,
			Optional<JobLevelizationType> jobLevelizationType, JobState jobState,JobStatus jobStatus,
			Attribute... jobAttributes) throws DataAccessException {
				
				PrimaryBitMap jobPrimaryBitMap 			= new PrimaryBitMap(true);
				jobPrimaryBitMap.setSecondaryBitMapPresent(true);
					
				Attribute jobAttribute					= new ByteArrayField(
																	FieldLengthType.FIXED, 
																	FieldOption.MANDATORY, 
																	FieldType.CHARACTER, 
																	jobType.name(), 
																	CHARSET, 
																	ByteEnumerableType.type(jobType),				
																	ByteEnumerableType.type(jobType).length, 
																	ByteEnumerableType.type(jobType).length, 
																	FIELD_DEFAULT,
																	ByteEnumerableType.type(jobType).length);
				
						
				FieldAttributeSet jobAttributeSet 		= new ByteArrayFieldAttributeSet(jobAttribute);
				
				jobAttributeSet.setPrimaryBitMap(Optional.of(jobPrimaryBitMap));
				
				SimpleEntry<PrimaryBitMap,FieldAttributeSet> jobAttributeSetFields 	= 
						new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(jobPrimaryBitMap,jobAttributeSet);

				Attribute jobValueAttribute				= new ByteArrayField(
																		FieldLengthType.FIXED, 
																		FieldOption.MANDATORY, 
																		FieldType.CHARACTER, 
																		jobType.name(), 
																		CHARSET, 
																		ByteEnumerableType.type(jobType),				
																		ByteEnumerableType.type(jobType).length, 
																		ByteEnumerableType.type(jobType).length, 
																		FIELD_DEFAULT,
																		ByteEnumerableType.type(jobType).length);


				FieldAttributeSet jobValueAttributeSet 	= new ByteArrayFieldAttributeSet(jobValueAttribute);
				
				
				Arrays.asList(jobAttributes).forEach(
						
						(attribute) -> {
							jobAttributeSet.addAttribute(attribute);
						}
				);	
				
				SecondaryBitMap jobSecondaryBitMap		= new SecondaryBitMap(true);
				
				jobSecondaryBitMap.set(1, jobAttributes.length+1, Boolean.TRUE);
				
				jobAttributeSet.setSecondaryBitMap(Optional.of(jobSecondaryBitMap));
							
				
				SimpleEntry<SecondaryBitMap,FieldAttributeSet> jobValueAttributeSetFields 	= 
						new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(jobSecondaryBitMap,jobValueAttributeSet);
				
				DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(jobAttributeSetFields, Optional.of(
						jobValueAttributeSetFields));
				
				dataSchemaFormat.setDataFlowType(DataFlowType.JOB);
				
				JobSchemaFormat jobSchemaFormat = new JobSchemaFormat(
															dataSchemaFormat, 
															ioFlowType,
															jobType,
															jobSchedulingType,
															jobProvider,
															jobExchangeMode,
															jobExecutionType,
															jobConcurrencyType,
															jobPersistenceType,
															jobErrorHandlingType,
															jobCommitType,
															jobRoleType, 
															jobQueueType,
															jobBatchType,
															jobLevelizationType,
															jobState,
															jobStatus
														);						
				
				jobSchemaFormat.setSchemaFor(jobType.name());
								
				return jobSchemaFormat;
			}

}
