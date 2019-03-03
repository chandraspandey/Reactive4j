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

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class DataProtocolSchemaFormat implements DataProtocolSchema{

	private String schemaFor							= null;
	private boolean isSchematicData						= false;	
	private IOFlowType  ioFlowType						= IOFlowType.NONE;
	
	private DataFlowType dataFlowType					= DataFlowType.NONE;
	private DataMode dataMode							= DataMode.NONE;
	private DataOperationType dataOperationType			= DataOperationType.NONE;
	private DataDistributionType dataDistributionType	= DataDistributionType.NONE;
	private DataSchemaFormat dataSchemaFormat			= null;
	private FieldAttributeSet primaryFieldSet 			= new ByteArrayFieldAttributeSet();
	
	public DataProtocolSchemaFormat(DataProtocolSchemaFormat dsf,DataSchemaFormat dataSchemaFormat) 
			throws DataAccessException{
		
		if(
			dataSchemaFormat != null &&
			dsf.ioFlowType != null && dsf.ioFlowType != IOFlowType.NONE && 
			dsf.dataFlowType != null && dsf.dataFlowType != DataFlowType.NONE &&
			dsf.dataMode != null && dsf.dataMode != DataMode.NONE &&
			dsf.dataOperationType != null && dsf.dataOperationType != DataOperationType.NONE &&
			dsf.dataDistributionType != null && dsf.dataDistributionType != DataDistributionType.NONE
		) {
		
			this.dataSchemaFormat 		= dataSchemaFormat;			
			this.ioFlowType 			= dsf.ioFlowType;
			
			this.dataFlowType			= dsf.dataFlowType;
			this.dataMode				= dsf.dataMode;
			this.dataOperationType		= dsf.dataOperationType;
			this.dataDistributionType	= dsf.dataDistributionType;
			
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
	
	
	
	public DataProtocolSchemaFormat(DataSchemaFormat dataSchemaFormat,IOFlowType  ioFlowType,
			DataFlowType dataFlowType,DataMode dataMode, DataOperationType dataOperationType,
			DataDistributionType dataDistributionType
		) throws DataAccessException{
					
			if(
				dataSchemaFormat != null &&
				ioFlowType != null && ioFlowType != IOFlowType.NONE && 
				dataFlowType != null && dataFlowType != DataFlowType.NONE &&
				dataMode != null && dataMode != DataMode.NONE &&
				dataOperationType != null && dataOperationType != DataOperationType.NONE &&
				dataDistributionType != null && dataDistributionType != DataDistributionType.NONE
			) {
			
				this.dataSchemaFormat 		= dataSchemaFormat;
				
				this.primaryFieldSet.setPrimaryBitMap(this.dataSchemaFormat.primaryBitMap());
				this.primaryFieldSet.setSecondaryBitMap(this.dataSchemaFormat.secondaryBitMap());
												
				this.primaryFieldSet.addAttribute(asField(ioFlowType.name(), IOFlowType.valueOf(ioFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataFlowType.name(), DataFlowType.valueOf(dataFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataMode.name(), DataMode.valueOf(dataMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataOperationType.name(), DataOperationType.valueOf(dataOperationType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(dataDistributionType.name(), DataSecurityType.valueOf(dataDistributionType.name()).getCode()));				
				
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
		
		builder.append("DataProtocolSchemaFormat{");
		builder.append("\n schemaFor : "+schemaFor);		
		builder.append("\n size : "+size());
		builder.append("\n"+this.primaryFieldSet.toString());

		builder.append("\n");

		builder.append(dataSchemaFormat.toString());		
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
