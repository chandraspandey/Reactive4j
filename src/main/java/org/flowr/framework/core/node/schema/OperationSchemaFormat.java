package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_CODE_DEFAULT;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.Iterator;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldLengthType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldOption;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataFlowType;
import org.flowr.framework.core.node.io.flow.data.DataSchemaFormat;
import org.flowr.framework.core.node.io.flow.data.binary.ByteArrayFieldBuffer;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayField;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayFieldAttributeSet;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.FieldAttributeSet;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataDistributionType;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataMode;
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataOperationType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationExchangeMode;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationExecutionType;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationMode;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationState;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationStatus;
import org.flowr.framework.core.node.schema.OperationSchema.Operation.OperationType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class OperationSchemaFormat implements OperationSchema{

	private boolean isSchematicData						= false;	
	private IOFlowType  ioFlowType						= IOFlowType.NONE;
	private OperationType operationType					= OperationType.NONE;
	private OperationMode operationMode					= OperationMode.NONE;
	private OperationExecutionType operationExecutionType = OperationExecutionType.NONE;
	private OperationExchangeMode operationExchangeMode	= OperationExchangeMode.NONE;
	private OperationState operationState				= OperationState.NONE;
	private OperationStatus operationStatus				= OperationStatus.NONE;	
	
	private DataFlowType dataFlowType					= DataFlowType.NONE;
	private DataMode dataMode							= DataMode.NONE;
	private DataOperationType dataOperationType			= DataOperationType.NONE;
	private DataDistributionType dataDistributionType	= DataDistributionType.NONE;
	private DataSchemaFormat dataSchemaFormat			= null;
	private FieldAttributeSet primaryFieldSet 			= new ByteArrayFieldAttributeSet();
	private FieldAttributeSet secondaryFieldSet			= new ByteArrayFieldAttributeSet();
	
	public OperationSchemaFormat(OperationSchemaFormat osf,DataSchemaFormat dataSchemaFormat) 
			throws DataAccessException{
		
		if(
			dataSchemaFormat != null &&
			osf.ioFlowType != null && osf.ioFlowType != IOFlowType.NONE && 
			osf.operationType != null && osf.operationType != OperationType.NONE &&
			osf.operationExecutionType != null && operationExecutionType != OperationExecutionType.NONE &&
			osf.operationMode != null && osf.operationMode != OperationMode.NONE &&
			osf.operationExchangeMode != null && osf.operationExchangeMode != OperationExchangeMode.NONE &&
			osf.operationState != null && osf.operationState != OperationState.NONE &&
			osf.operationStatus != null && osf.operationStatus != OperationStatus.NONE &&
			osf.dataFlowType != null && osf.dataFlowType != DataFlowType.NONE &&
			osf.dataMode != null && osf.dataMode != DataMode.NONE &&
			osf.dataOperationType != null && osf.dataOperationType != DataOperationType.NONE &&
			osf.dataDistributionType != null && osf.dataDistributionType != DataDistributionType.NONE
		) {
		
			this.dataSchemaFormat 		= dataSchemaFormat;			
			this.ioFlowType 			= osf.ioFlowType;
			this.operationType 			= osf.operationType;
			this.operationMode 			= osf.operationMode;
			this.operationExchangeMode 	= osf.operationExchangeMode;
			this.operationExecutionType = osf.operationExecutionType;
			this.operationState 		= osf.operationState;
			this.operationStatus 		= osf.operationStatus;
			
			this.dataFlowType			= osf.dataFlowType;
			this.dataMode				= osf.dataMode;
			this.dataOperationType		= osf.dataOperationType;
			this.dataDistributionType	= osf.dataDistributionType;
			
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
	
	
	
	public OperationSchemaFormat(DataSchemaFormat dataSchemaFormat,IOFlowType  ioFlowType, 
			OperationExecutionType operationExecutionType, OperationType operationType,
			OperationMode operationMode, OperationExchangeMode operationExchangeMode, OperationState operationState,
			OperationStatus operationStatus, DataFlowType dataFlowType,DataMode dataMode, DataOperationType dataOperationType,
			DataDistributionType dataDistributionType
		) throws DataAccessException{
					
			if(
				dataSchemaFormat != null &&
				ioFlowType != null && ioFlowType != IOFlowType.NONE && 
				operationExecutionType != null && operationExecutionType != OperationExecutionType.NONE &&
				operationType != null && operationType != OperationType.NONE &&
				operationMode != null && operationMode != OperationMode.NONE &&
				operationExchangeMode != null && operationExchangeMode != OperationExchangeMode.NONE &&			
				operationState != null && operationState != OperationState.NONE &&
				operationStatus != null && operationStatus != OperationStatus.NONE &&
				dataFlowType != null && dataFlowType != DataFlowType.NONE &&
				dataMode != null && dataMode != DataMode.NONE &&
				dataOperationType != null && dataOperationType != DataOperationType.NONE &&
				dataDistributionType != null && dataDistributionType != DataDistributionType.NONE
			) {
			
				this.dataSchemaFormat 		= dataSchemaFormat;
				
				System.out.println(ioFlowType+" | "+IOFlowType.getType(2)+" | "+IOFlowType.valueOf(ioFlowType.name()).getCode());
				
				this.primaryFieldSet.addAttribute(asField(ioFlowType.name(), IOFlowType.valueOf(ioFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationExecutionType.name(), OperationExecutionType.valueOf(operationExecutionType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationType.name(), OperationType.valueOf(operationType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationMode.name(), OperationMode.valueOf(operationMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationExchangeMode.name(), OperationExchangeMode.valueOf(operationExchangeMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationState.name(), OperationState.valueOf(operationState.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(operationStatus.name(),OperationStatus.valueOf(operationStatus.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataFlowType.name(), DataFlowType.valueOf(dataFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataMode.name(), DataMode.valueOf(dataMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataOperationType.name(), DataOperationType.valueOf(dataOperationType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataDistributionType.name(), DataDistributionType.valueOf(dataDistributionType.name()).getCode()));				
				
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
			
			System.out.println(" 2. length : "+this.size());
			
			ByteArrayFieldBuffer byteArrayFieldBuffer = new ByteArrayFieldBuffer(size());		
			
			Iterator<Attribute> primaryIter = primaryFieldSet.getAttributeList().iterator();
			
			while(primaryIter.hasNext()) {
				
				byteArrayFieldBuffer.put(((ByteArrayField)primaryIter.next()).getByteArrayValue());
			}		
			
			System.out.println(" 3. length : "+dataSchemaFormat.getData().length);
			
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
	
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("OperationSchemaFormat{");
		builder.append("\n : size : "+size());
		builder.append(this.primaryFieldSet.toString());

		builder.append("\n");

		builder.append("\t"+super.toString());		
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
