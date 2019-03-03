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
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataDistributionType;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataMode;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataOperationType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationDependencyClassificationType;
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
 * 
 * Converts the Operation Characteristics as Enumerable type in form of OperationSchemaFormat.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class OperationSchemaFormatHelper {
 
   public static OperationSchemaFormat initMode() throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 		= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute				= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationMode.INIT.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationMode.INIT),				
															ByteEnumerableType.type(OperationMode.INIT).length, 
															ByteEnumerableType.type(OperationMode.INIT).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationMode.INIT).length);
		
				
		FieldAttributeSet operationAttributeSet 	= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute			= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationMode.INIT.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationMode.INIT),				
																ByteEnumerableType.type(OperationMode.INIT).length, 
																ByteEnumerableType.type(OperationMode.INIT).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationMode.INIT).length);


		FieldAttributeSet operationValueAttributeSet = new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap	 = new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat =  new OperationSchemaFormat(
														dataSchemaFormat,
														IOFlowType.OUTBOUND,
														OperationExecutionType.REQUEST,
														OperationType.READ,
														OperationMode.INIT, 
														OperationExchangeMode.COMMAND, 
														OperationState.COMMAND,
														OperationStatus.COMMAND, 
														DataFlowType.COMMAND,
														DataMode.SYNC, 
														DataOperationType.READ,
														DataDistributionType.UNICAST
													);
		
		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationMode.INIT.name());
  		
  		return operationSchemaFormat;
	}
	
   public static OperationSchemaFormat negitiationMode() throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationMode.NEGOTATION.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationMode.NEGOTATION),				
															ByteEnumerableType.type(OperationMode.NEGOTATION).length, 
															ByteEnumerableType.type(OperationMode.NEGOTATION).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationMode.NEGOTATION).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationMode.NEGOTATION.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationMode.NEGOTATION),				
																ByteEnumerableType.type(OperationMode.NEGOTATION).length, 
																ByteEnumerableType.type(OperationMode.NEGOTATION).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationMode.NEGOTATION).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat =  new OperationSchemaFormat(
														dataSchemaFormat,
														IOFlowType.OUTBOUND,
														OperationExecutionType.REQUEST,
														OperationType.READ,
														OperationMode.NEGOTATION, 
														OperationExchangeMode.COMMAND, 
														OperationState.COMMAND,
														OperationStatus.COMMAND, 
														DataFlowType.COMMAND,
														DataMode.ASYNC, 
														DataOperationType.READ,
														DataDistributionType.UNICAST
													);
		
		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationMode.NEGOTATION.name());
  		
  		return operationSchemaFormat;
	}
    
   public static OperationSchemaFormat transactionMode() throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationMode.TRANSACTION.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationMode.TRANSACTION),				
															ByteEnumerableType.type(OperationMode.TRANSACTION).length, 
															ByteEnumerableType.type(OperationMode.TRANSACTION).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationMode.TRANSACTION).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationMode.TRANSACTION.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationMode.TRANSACTION),				
																ByteEnumerableType.type(OperationMode.TRANSACTION).length, 
																ByteEnumerableType.type(OperationMode.TRANSACTION).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationMode.TRANSACTION).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat =  new OperationSchemaFormat(
														dataSchemaFormat,
														IOFlowType.OUTBOUND,
														OperationExecutionType.TRANSACTION,
														OperationType.READ,
														OperationMode.TRANSACTION, 
														OperationExchangeMode.COMMAND, 
														OperationState.COMMAND,
														OperationStatus.COMMAND, 
														DataFlowType.COMMAND,
														DataMode.ASYNC, 
														DataOperationType.READ,
														DataDistributionType.UNICAST
													);
		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationMode.TRANSACTION.name());
  		
  		return operationSchemaFormat;
	}
    
   public static OperationSchemaFormat commitMode() throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationMode.COMMIT.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationMode.COMMIT),				
															ByteEnumerableType.type(OperationMode.COMMIT).length, 
															ByteEnumerableType.type(OperationMode.COMMIT).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationMode.COMMIT).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationMode.COMMIT.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationMode.COMMIT),				
																ByteEnumerableType.type(OperationMode.COMMIT).length, 
																ByteEnumerableType.type(OperationMode.COMMIT).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationMode.COMMIT).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat =  new OperationSchemaFormat(
														dataSchemaFormat,
														IOFlowType.OUTBOUND,
														OperationExecutionType.TRANSACTION,
														OperationType.WRITE,
														OperationMode.COMMIT, 
														OperationExchangeMode.COMMAND, 
														OperationState.COMMAND,
														OperationStatus.COMMAND, 
														DataFlowType.COMMAND,
														DataMode.ASYNC, 
														DataOperationType.CREATEORUPDATE,
														DataDistributionType.UNICAST
													);
		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationMode.COMMIT.name());
  		
  		return operationSchemaFormat;
	}
    
   public static OperationSchemaFormat rollbackMode() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationMode.ROLLBACK.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationMode.ROLLBACK),				
 															ByteEnumerableType.type(OperationMode.ROLLBACK).length, 
 															ByteEnumerableType.type(OperationMode.ROLLBACK).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationMode.ROLLBACK).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationMode.ROLLBACK.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationMode.ROLLBACK),				
 																ByteEnumerableType.type(OperationMode.ROLLBACK).length, 
 																ByteEnumerableType.type(OperationMode.ROLLBACK).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationMode.ROLLBACK).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								 						dataSchemaFormat,
								 						IOFlowType.OUTBOUND,
								 						OperationExecutionType.TRANSACTION,
								 						OperationType.FAILBACK,
								 						OperationMode.ROLLBACK, 
								 						OperationExchangeMode.COMMAND, 
								 						OperationState.COMMAND,
								 						OperationStatus.COMMAND, 
								 						DataFlowType.COMMAND,
								 						DataMode.ASYNC, 
								 						DataOperationType.ROLLBACK,
								 						DataDistributionType.UNICAST
								 					);
 		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationMode.ROLLBACK.name());
  		
  		return operationSchemaFormat;
 	}
    
   public static OperationSchemaFormat exchangeModeReadCommand() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationExchangeMode.COMMAND.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND),				
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationExchangeMode.COMMAND.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND),				
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								 						dataSchemaFormat,
								 						IOFlowType.INBOUND,
								 						OperationExecutionType.TRANSACTION,
								 						OperationType.READ,
								 						OperationMode.TRANSACTION, 
								 						OperationExchangeMode.COMMAND, 
								 						OperationState.COMMAND,
								 						OperationStatus.COMMAND, 
								 						DataFlowType.COMMAND,
								 						DataMode.ASYNC, 
								 						DataOperationType.READ,
								 						DataDistributionType.UNICAST
								 					);
 		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationType.READ.name());
  		
  		return operationSchemaFormat;
 	}
    
   public static OperationSchemaFormat exchangeModeWriteCommand() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationExchangeMode.COMMAND.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND),				
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationExchangeMode.COMMAND).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationExchangeMode.COMMAND.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND),				
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationExchangeMode.COMMAND).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								 						dataSchemaFormat,
								 						IOFlowType.OUTBOUND,
								 						OperationExecutionType.TRANSACTION,
								 						OperationType.WRITE,
								 						OperationMode.TRANSACTION, 
								 						OperationExchangeMode.COMMAND, 
								 						OperationState.COMMAND,
								 						OperationStatus.COMMAND, 
								 						DataFlowType.COMMAND,
								 						DataMode.ASYNC, 
								 						DataOperationType.CREATEORUPDATE,
								 						DataDistributionType.UNICAST
								 					);
 		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.COMMAND.name()+"_"+ OperationType.WRITE.name());
  		
  		return operationSchemaFormat;
 	}
    
   public static OperationSchemaFormat exchangeModeReadACK() throws DataAccessException {
		
  		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
  		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
  			
  		Attribute operationAttribute					= new ByteArrayField(
  															FieldLengthType.FIXED, 
  															FieldOption.MANDATORY, 
  															FieldType.CHARACTER, 
  															OperationExchangeMode.ACK_REQUIRED.name(), 
  															CHARSET, 
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED),				
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  															FIELD_DEFAULT,
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length);
  		
  				
  		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
  		
  		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
  		
  		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
  				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

  		Attribute operationValueAttribute				= new ByteArrayField(
  																FieldLengthType.FIXED, 
  																FieldOption.MANDATORY, 
  																FieldType.CHARACTER, 
  																OperationExchangeMode.ACK_REQUIRED.name(), 
  																CHARSET, 
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED),				
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  																FIELD_DEFAULT,
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length);


  		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
  		
  		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
  		
  		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
  		
  		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
  				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
  		
  		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
  				operationValueAttributeSetFields));
  		
  		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
  		
  		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.INBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.READ,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.ACK_REQUIRED, 
								  						OperationState.REQUEST_INITIATED,
								  						OperationStatus.INITIATED, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.COMMAND,
								  						DataDistributionType.UNICAST
								  					);
  		operationSchemaFormat.setSchemaFor(OperationExchangeMode.ACK_REQUIRED.name());
  		
  		return operationSchemaFormat;
  	}
     
   public static OperationSchemaFormat exchangeModeWriteACK() throws DataAccessException {
 		
  		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
  		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
  			
  		Attribute operationAttribute					= new ByteArrayField(
  															FieldLengthType.FIXED, 
  															FieldOption.MANDATORY, 
  															FieldType.CHARACTER, 
  															OperationExchangeMode.ACK_REQUIRED.name(), 
  															CHARSET, 
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED),				
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  															FIELD_DEFAULT,
  															ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length);
  		
  				
  		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
  		
  		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
  		
  		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
  				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

  		Attribute operationValueAttribute				= new ByteArrayField(
  																FieldLengthType.FIXED, 
  																FieldOption.MANDATORY, 
  																FieldType.CHARACTER, 
  																OperationExchangeMode.ACK_REQUIRED.name(), 
  																CHARSET, 
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED),				
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length, 
  																FIELD_DEFAULT,
  																ByteEnumerableType.type(OperationExchangeMode.ACK_REQUIRED).length);


  		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
  		
  		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
  		
  		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
  		
  		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
  				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
  		
  		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
  				operationValueAttributeSetFields));
  		
  		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
  		
  		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.OUTBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.WRITE,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.ACK_REQUIRED, 
								  						OperationState.REQUEST_ACCEPTED,
								  						OperationStatus.COMPLETED, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.COMMAND,
								  						DataDistributionType.UNICAST
								  					);
  		
   		operationSchemaFormat.setSchemaFor(OperationExchangeMode.ACK_REQUIRED.name());
  		
  		return operationSchemaFormat;
  	}
     
   public static OperationSchemaFormat exchangeModeReadNoACK() throws DataAccessException {
 		
   		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
   		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
   			
   		Attribute operationAttribute					= new ByteArrayField(
   															FieldLengthType.FIXED, 
   															FieldOption.MANDATORY, 
   															FieldType.CHARACTER, 
   															OperationExchangeMode.ACK_NOT_REQUIRED.name(), 
   															CHARSET, 
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED),				
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   															FIELD_DEFAULT,
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length);
   		
   				
   		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
   		
   		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
   		
   		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
   				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

   		Attribute operationValueAttribute				= new ByteArrayField(
   																FieldLengthType.FIXED, 
   																FieldOption.MANDATORY, 
   																FieldType.CHARACTER, 
   																OperationExchangeMode.ACK_NOT_REQUIRED.name(), 
   																CHARSET, 
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED),				
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   																FIELD_DEFAULT,
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length);


   		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
   		
   		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
   		
   		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
   		
   		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
   				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
   		
   		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
   				operationValueAttributeSetFields));
   		
   		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
   		
   		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								   						dataSchemaFormat,
								   						IOFlowType.INBOUND,
								   						OperationExecutionType.TRANSACTION,
								   						OperationType.READ,
								   						OperationMode.TRANSACTION, 
								   						OperationExchangeMode.ACK_NOT_REQUIRED, 
								   						OperationState.REQUEST_INITIATED,
								   						OperationStatus.INITIATED, 
								   						DataFlowType.CONTIGOUS,
								   						DataMode.ASYNC, 
								   						DataOperationType.COMMAND,
								   						DataDistributionType.UNICAST
								   					);
   		
   		operationSchemaFormat.setSchemaFor(OperationExchangeMode.ACK_NOT_REQUIRED.name());
  		
  		return operationSchemaFormat;
   	}
      
   public static OperationSchemaFormat exchangeModeWriteNoACK() throws DataAccessException {
  		
   		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
   		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
   			
   		Attribute operationAttribute					= new ByteArrayField(
   															FieldLengthType.FIXED, 
   															FieldOption.MANDATORY, 
   															FieldType.CHARACTER, 
   															OperationExchangeMode.ACK_NOT_REQUIRED.name(), 
   															CHARSET, 
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED),				
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   															FIELD_DEFAULT,
   															ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length);
   		
   				
   		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
   		
   		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
   		
   		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
   				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

   		Attribute operationValueAttribute				= new ByteArrayField(
   																FieldLengthType.FIXED, 
   																FieldOption.MANDATORY, 
   																FieldType.CHARACTER, 
   																OperationExchangeMode.ACK_NOT_REQUIRED.name(), 
   																CHARSET, 
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED),				
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length, 
   																FIELD_DEFAULT,
   																ByteEnumerableType.type(OperationExchangeMode.ACK_NOT_REQUIRED).length);


   		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
   		
   		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
   		
   		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
   		
   		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
   				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
   		
   		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
   				operationValueAttributeSetFields));
   		
   		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
   		
   		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								   						dataSchemaFormat,
								   						IOFlowType.OUTBOUND,
								   						OperationExecutionType.TRANSACTION,
								   						OperationType.WRITE,
								   						OperationMode.TRANSACTION, 
								   						OperationExchangeMode.ACK_NOT_REQUIRED, 
								   						OperationState.REQUEST_ACCEPTED,
								   						OperationStatus.COMPLETED, 
								   						DataFlowType.CONTIGOUS,
								   						DataMode.ASYNC, 
								   						DataOperationType.COMMAND,
								   						DataDistributionType.UNICAST
								   					);
   		
   		operationSchemaFormat.setSchemaFor(OperationExchangeMode.ACK_NOT_REQUIRED.name());
  		
  		return operationSchemaFormat;
   	}
      
   public static OperationSchemaFormat exchangeModeReadForResponse() throws DataAccessException {
   		
     		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
     		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
     			
     		Attribute operationAttribute					= new ByteArrayField(
     															FieldLengthType.FIXED, 
     															FieldOption.MANDATORY, 
     															FieldType.CHARACTER, 
     															OperationExchangeMode.RESPONSE_REQUIRED.name(), 
     															CHARSET, 
     															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED),				
     															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
     															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
     															FIELD_DEFAULT,
     															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length);
     		
     				
     		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
     		
     		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
     		
     		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
     				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

     		Attribute operationValueAttribute				= new ByteArrayField(
     																FieldLengthType.FIXED, 
     																FieldOption.MANDATORY, 
     																FieldType.CHARACTER, 
     																OperationExchangeMode.RESPONSE_REQUIRED.name(), 
     																CHARSET, 
     																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED),				
     																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
     																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
     																FIELD_DEFAULT,
     																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length);


     		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
     		
     		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
     		
     		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
     		
     		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
     				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
     		
     		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
     				operationValueAttributeSetFields));
     		
     		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
     		
     		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								     						dataSchemaFormat,
								     						IOFlowType.INBOUND,
								     						OperationExecutionType.TRANSACTION,
								     						OperationType.READ,
								     						OperationMode.TRANSACTION, 
								     						OperationExchangeMode.RESPONSE_REQUIRED, 
								     						OperationState.REQUEST_INITIATED,
								     						OperationStatus.INITIATED, 
								     						DataFlowType.CONTIGOUS,
								     						DataMode.ASYNC, 
								     						DataOperationType.COMMAND,
								     						DataDistributionType.UNICAST
								     					);
     		
       		operationSchemaFormat.setSchemaFor(OperationType.READ+"_"+OperationExchangeMode.RESPONSE_REQUIRED.name());
      		
      		return operationSchemaFormat;
     	}
        
   public static OperationSchemaFormat exchangeModeWriteForResponse() throws DataAccessException {
     		
   		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
   		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
   			
   		Attribute operationAttribute					= new ByteArrayField(
   															FieldLengthType.FIXED, 
   															FieldOption.MANDATORY, 
   															FieldType.CHARACTER, 
   															OperationExchangeMode.RESPONSE_REQUIRED.name(), 
   															CHARSET, 
   															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED),				
   															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
   															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
   															FIELD_DEFAULT,
   															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length);
   		
   				
   		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
   		
   		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
   		
   		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
   				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

   		Attribute operationValueAttribute				= new ByteArrayField(
   																FieldLengthType.FIXED, 
   																FieldOption.MANDATORY, 
   																FieldType.CHARACTER, 
   																OperationExchangeMode.RESPONSE_REQUIRED.name(), 
   																CHARSET, 
   																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED),				
   																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
   																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length, 
   																FIELD_DEFAULT,
   																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_REQUIRED).length);


   		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
   		
   		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
   		
   		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
   		
   		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
   				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
   		
   		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
   				operationValueAttributeSetFields));
   		
   		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
   		
   		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								   						dataSchemaFormat,
								   						IOFlowType.OUTBOUND,
								   						OperationExecutionType.TRANSACTION,
								   						OperationType.WRITE,
								   						OperationMode.TRANSACTION, 
								   						OperationExchangeMode.RESPONSE_REQUIRED, 
								   						OperationState.REQUEST_COMPLETED,
								   						OperationStatus.COMPLETED, 
								   						DataFlowType.CONTIGOUS,
								   						DataMode.ASYNC, 
								   						DataOperationType.COMMAND,
								   						DataDistributionType.UNICAST
								   					);
   		
   		operationSchemaFormat.setSchemaFor(OperationType.WRITE+"_"+OperationExchangeMode.RESPONSE_REQUIRED.name());
  		
  		return operationSchemaFormat;
   	}
      
   public static OperationSchemaFormat exchangeModeForNoResponse() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationExchangeMode.RESPONSE_NOT_REQUIRED.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED),				
 															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length, 
 															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationExchangeMode.ACK_NOT_REQUIRED.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED),				
 																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length, 
 																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationExchangeMode.RESPONSE_NOT_REQUIRED).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								 						dataSchemaFormat,
								 						IOFlowType.OUTBOUND,
								 						OperationExecutionType.TRANSACTION,
								 						OperationType.WRITE,
								 						OperationMode.TRANSACTION, 
								 						OperationExchangeMode.RESPONSE_NOT_REQUIRED, 
								 						OperationState.REQUEST_INITIATED,
								 						OperationStatus.INITIATED, 
								 						DataFlowType.CONTIGOUS,
								 						DataMode.ASYNC, 
								 						DataOperationType.COMMAND,
								 						DataDistributionType.UNICAST
								 					);
 		
 		operationSchemaFormat.setSchemaFor(OperationExchangeMode.RESPONSE_NOT_REQUIRED.name());
  		
  		return operationSchemaFormat;
 	}
          
   public static OperationSchemaFormat OperationDependencyType(OperationDependencyType operationDependencyType) 
		throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationDependencyType.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationDependencyType),				
															ByteEnumerableType.type(operationDependencyType).length, 
															ByteEnumerableType.type(operationDependencyType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationDependencyType).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationDependencyType.DEPENDENT.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationDependencyType),				
																ByteEnumerableType.type(operationDependencyType).length, 
																ByteEnumerableType.type(operationDependencyType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationDependencyType).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationDependencyType.name());
		
		return operationSchemaFormat;
	}
    
   public static OperationSchemaFormat OperationDependencyClassificationType(OperationDependencyClassificationType 
		operationDependencyClassificationType) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationDependencyClassificationType.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationDependencyClassificationType),				
															ByteEnumerableType.type(operationDependencyClassificationType).length, 
															ByteEnumerableType.type(operationDependencyClassificationType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationDependencyClassificationType).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																operationDependencyClassificationType.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationDependencyClassificationType),				
																ByteEnumerableType.type(operationDependencyClassificationType).length, 
																ByteEnumerableType.type(operationDependencyClassificationType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationDependencyClassificationType).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationDependencyClassificationType.name());
		
		return operationSchemaFormat;
	}
       
   public static OperationSchemaFormat OperationDependencyStatus(OperationDependencyStatus operationDependencyStatus)
		   throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationDependencyStatus.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationDependencyStatus),				
															ByteEnumerableType.type(operationDependencyStatus).length, 
															ByteEnumerableType.type(operationDependencyStatus).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationDependencyStatus).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																operationDependencyStatus.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationDependencyStatus),				
																ByteEnumerableType.type(operationDependencyStatus).length, 
																ByteEnumerableType.type(operationDependencyStatus).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationDependencyStatus).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationDependencyStatus.name());
		
		return operationSchemaFormat;
	}
    
   public static OperationSchemaFormat OperationExecutionType(OperationExecutionType operationExecutionType) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationExecutionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationExecutionType),				
															ByteEnumerableType.type(operationExecutionType).length, 
															ByteEnumerableType.type(operationExecutionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationExecutionType).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																operationExecutionType.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationExecutionType),				
																ByteEnumerableType.type(operationExecutionType).length, 
																ByteEnumerableType.type(operationExecutionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationExecutionType).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						operationExecutionType,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationExecutionType.name());
		
		return operationSchemaFormat;
	}
     
   public static OperationSchemaFormat OperationTransactionType(OperationTransactionType operationTransactionType) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationTransactionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationTransactionType),				
															ByteEnumerableType.type(operationTransactionType).length, 
															ByteEnumerableType.type(operationTransactionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationTransactionType).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																operationTransactionType.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationTransactionType),				
																ByteEnumerableType.type(operationTransactionType).length, 
																ByteEnumerableType.type(operationTransactionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationTransactionType).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationTransactionType.name());
		
		return operationSchemaFormat;
	}
   
   public static OperationSchemaFormat OperationState(OperationState operationState) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															operationState.name(), 
															CHARSET, 
															ByteEnumerableType.type(operationState),				
															ByteEnumerableType.type(operationState).length, 
															ByteEnumerableType.type(operationState).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(operationState).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																operationState.name(), 
																CHARSET, 
																ByteEnumerableType.type(operationState),				
																ByteEnumerableType.type(operationState).length, 
																ByteEnumerableType.type(operationState).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(operationState).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.OUTBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.COMMAND,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.COMMAND,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(operationState.name());
		
		return operationSchemaFormat;
	}
   
   public static OperationSchemaFormat OperationStatus(OperationStatus operationStatus) throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															operationStatus.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(operationStatus),				
 															ByteEnumerableType.type(operationStatus).length, 
 															ByteEnumerableType.type(operationStatus).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(operationStatus).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																operationStatus.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(operationStatus),				
 																ByteEnumerableType.type(operationStatus).length, 
 																ByteEnumerableType.type(operationStatus).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(operationStatus).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
 									  						dataSchemaFormat,
 									  						IOFlowType.OUTBOUND,
 									  						OperationExecutionType.TRANSACTION,
 									  						OperationType.COMMAND,
 									  						OperationMode.TRANSACTION, 
 									  						OperationExchangeMode.COMMAND, 
 									  						OperationState.COMMAND,
 									  						OperationStatus.COMMAND, 
 									  						DataFlowType.CONTIGOUS,
 									  						DataMode.ASYNC, 
 									  						DataOperationType.COMMAND,
 									  						DataDistributionType.UNICAST
 									  					);
 		
 		operationSchemaFormat.setSchemaFor(operationStatus.name());
 		
 		return operationSchemaFormat;
 	}

   public static OperationSchemaFormat operationTypeCommand() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationType.COMMAND.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationType.COMMAND),				
 															ByteEnumerableType.type(OperationType.COMMAND).length, 
 															ByteEnumerableType.type(OperationType.COMMAND).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationType.COMMAND).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationType.COMMAND.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationType.COMMAND),				
 																ByteEnumerableType.type(OperationType.COMMAND).length, 
 																ByteEnumerableType.type(OperationType.COMMAND).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationType.COMMAND).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.OUTBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.COMMAND,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.COMMAND, 
								  						OperationState.COMMAND,
								  						OperationStatus.COMMAND, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.COMMAND,
								  						DataDistributionType.UNICAST
								  					);
 		
 		operationSchemaFormat.setSchemaFor(OperationType.COMMAND.name());
 		
 		return operationSchemaFormat;
 	}
   
   public static OperationSchemaFormat operationTypeFailback() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationType.FAILBACK.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationType.FAILBACK),				
 															ByteEnumerableType.type(OperationType.FAILBACK).length, 
 															ByteEnumerableType.type(OperationType.FAILBACK).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationType.FAILBACK).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationType.FAILBACK.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationType.FAILBACK),				
 																ByteEnumerableType.type(OperationType.FAILBACK).length, 
 																ByteEnumerableType.type(OperationType.FAILBACK).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationType.FAILBACK).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.OUTBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.FAILBACK,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.COMMAND, 
								  						OperationState.COMMAND,
								  						OperationStatus.COMMAND, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.ROLLBACK,
								  						DataDistributionType.UNICAST
								  					);
		operationSchemaFormat.setSchemaFor(OperationType.FAILBACK.name());
 		
 		return operationSchemaFormat;
 		
 	}
   
   public static OperationSchemaFormat operationTypeFailForward() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationType.FAILFORWARD.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationType.FAILFORWARD),				
 															ByteEnumerableType.type(OperationType.FAILFORWARD).length, 
 															ByteEnumerableType.type(OperationType.FAILFORWARD).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationType.FAILFORWARD).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationType.FAILBACK.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationType.FAILFORWARD),				
 																ByteEnumerableType.type(OperationType.FAILFORWARD).length, 
 																ByteEnumerableType.type(OperationType.FAILFORWARD).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationType.FAILFORWARD).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.OUTBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.FAILFORWARD,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.COMMAND, 
								  						OperationState.COMMAND,
								  						OperationStatus.COMMAND, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.CREATEORUPDATE,
								  						DataDistributionType.UNICAST
								  					);
 		
 		operationSchemaFormat.setSchemaFor(OperationType.FAILFORWARD.name());
 		
 		return operationSchemaFormat;
 	}
   
   public static OperationSchemaFormat operationTypeRead() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationType.READ.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationType.READ),				
 															ByteEnumerableType.type(OperationType.READ).length, 
 															ByteEnumerableType.type(OperationType.READ).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationType.READ).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationType.READ.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationType.READ),				
 																ByteEnumerableType.type(OperationType.READ).length, 
 																ByteEnumerableType.type(OperationType.READ).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationType.READ).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.INBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.READ,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.COMMAND, 
								  						OperationState.COMMAND,
								  						OperationStatus.COMMAND, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.READ,
								  						DataDistributionType.UNICAST
								  					);
 		
 		operationSchemaFormat.setSchemaFor(OperationType.READ.name());
 		
 		return operationSchemaFormat;
 	}
   
   public static OperationSchemaFormat operationTypeRead(Attribute... operationReadAttributes) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationType.READ.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationType.READ),				
															ByteEnumerableType.type(OperationType.READ).length, 
															ByteEnumerableType.type(OperationType.READ).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationType.READ).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationType.READ.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationType.READ),				
																ByteEnumerableType.type(OperationType.READ).length, 
																ByteEnumerableType.type(OperationType.READ).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationType.READ).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		Arrays.asList(operationReadAttributes).forEach(
				
				(attribute) -> {
					operationAttributeSet.addAttribute(attribute);
				}
		);		
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(operationReadAttributes.length+1);
		
		operationSecondaryBitMap.set(1, operationReadAttributes.length+1, Boolean.TRUE);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
								  						dataSchemaFormat,
								  						IOFlowType.INBOUND,
								  						OperationExecutionType.TRANSACTION,
								  						OperationType.READ,
								  						OperationMode.TRANSACTION, 
								  						OperationExchangeMode.COMMAND, 
								  						OperationState.COMMAND,
								  						OperationStatus.COMMAND, 
								  						DataFlowType.CONTIGOUS,
								  						DataMode.ASYNC, 
								  						DataOperationType.READ,
								  						DataDistributionType.UNICAST
								  					);
		
		operationSchemaFormat.setSchemaFor(OperationType.READ.name());
		
		return operationSchemaFormat;
	}
   
  public static OperationSchemaFormat operationTypeWrite() throws DataAccessException {
		
 		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
 		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
 			
 		Attribute operationAttribute					= new ByteArrayField(
 															FieldLengthType.FIXED, 
 															FieldOption.MANDATORY, 
 															FieldType.CHARACTER, 
 															OperationType.WRITE.name(), 
 															CHARSET, 
 															ByteEnumerableType.type(OperationType.WRITE),				
 															ByteEnumerableType.type(OperationType.WRITE).length, 
 															ByteEnumerableType.type(OperationType.WRITE).length, 
 															FIELD_DEFAULT,
 															ByteEnumerableType.type(OperationType.WRITE).length);
 		
 				
 		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
 		
 		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
 		
 		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
 				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

 		Attribute operationValueAttribute				= new ByteArrayField(
 																FieldLengthType.FIXED, 
 																FieldOption.MANDATORY, 
 																FieldType.CHARACTER, 
 																OperationType.WRITE.name(), 
 																CHARSET, 
 																ByteEnumerableType.type(OperationType.WRITE),				
 																ByteEnumerableType.type(OperationType.WRITE).length, 
 																ByteEnumerableType.type(OperationType.WRITE).length, 
 																FIELD_DEFAULT,
 																ByteEnumerableType.type(OperationType.WRITE).length);


 		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
 		
 		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(true);
 		
 		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
 		
 		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
 				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
 		
 		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
 				operationValueAttributeSetFields));
 		
 		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
 		
 		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.INBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.WRITE,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.CREATEORUPDATE,
									  						DataDistributionType.UNICAST
									  					);
 		
 		operationSchemaFormat.setSchemaFor(OperationType.WRITE.name());
 		
 		return operationSchemaFormat;
 	}
  
  public static OperationSchemaFormat operationTypeWrite(Attribute... operationWriteAttributes) throws DataAccessException {
		
		PrimaryBitMap operationPrimaryBitMap 			= new PrimaryBitMap(true);
		operationPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute operationAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															OperationType.WRITE.name(), 
															CHARSET, 
															ByteEnumerableType.type(OperationType.WRITE),				
															ByteEnumerableType.type(OperationType.WRITE).length, 
															ByteEnumerableType.type(OperationType.WRITE).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(OperationType.WRITE).length);
		
				
		FieldAttributeSet operationAttributeSet 		= new ByteArrayFieldAttributeSet(operationAttribute);
		
		operationAttributeSet.setPrimaryBitMap(Optional.of(operationPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> operationAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(operationPrimaryBitMap,operationAttributeSet);

		Attribute operationValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																OperationType.WRITE.name(), 
																CHARSET, 
																ByteEnumerableType.type(OperationType.WRITE),				
																ByteEnumerableType.type(OperationType.WRITE).length, 
																ByteEnumerableType.type(OperationType.WRITE).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(OperationType.WRITE).length);


		FieldAttributeSet operationValueAttributeSet 	= new ByteArrayFieldAttributeSet(operationValueAttribute);
		
		Arrays.asList(operationWriteAttributes).forEach(
				
				(attribute) -> {
					operationAttributeSet.addAttribute(attribute);
				}
		);	
		
		SecondaryBitMap operationSecondaryBitMap		= new SecondaryBitMap(operationWriteAttributes.length+1);
		operationSecondaryBitMap.set(1, operationWriteAttributes.length+1, Boolean.TRUE);
		
		operationAttributeSet.setSecondaryBitMap(Optional.of(operationSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> operationValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(operationSecondaryBitMap,operationValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(operationAttributeSetFields, Optional.of(
				operationValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		OperationSchemaFormat operationSchemaFormat = new OperationSchemaFormat(
									  						dataSchemaFormat,
									  						IOFlowType.INBOUND,
									  						OperationExecutionType.TRANSACTION,
									  						OperationType.WRITE,
									  						OperationMode.TRANSACTION, 
									  						OperationExchangeMode.COMMAND, 
									  						OperationState.COMMAND,
									  						OperationStatus.COMMAND, 
									  						DataFlowType.CONTIGOUS,
									  						DataMode.ASYNC, 
									  						DataOperationType.CREATEORUPDATE,
									  						DataDistributionType.UNICAST
									  					);
		
		operationSchemaFormat.setSchemaFor(OperationType.WRITE.name());
		
		return operationSchemaFormat;
	}

}
