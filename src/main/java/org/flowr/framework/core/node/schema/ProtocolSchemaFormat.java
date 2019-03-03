package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_CODE_DEFAULT;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.Iterator;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldLengthType;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldOption;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldType;
import org.flowr.framework.core.node.io.flow.data.DataSchemaFormat;
import org.flowr.framework.core.node.io.flow.data.binary.ByteArrayFieldBuffer;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayField;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayFieldAttributeSet;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.FieldAttributeSet;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolSupport.ProtocolExchangeState;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolSupport.ProtocolExchangeStatus;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class ProtocolSchemaFormat implements ProtocolSchema{

	private String schemaFor									= null;
	private boolean isSchematicData								= false;	
	private ProtocolSupport  protocol							= ProtocolSupport.ProtocolResult;	
	private ProtocolResult  protocolResult						= ProtocolResult.UNSUPPORTED;
	private ProtocolExchangeType protocolExchangeType			= ProtocolExchangeType.HEALTHCHECK;
	
	private ProtocolExchangeState protocolExchangeState			= ProtocolExchangeState.EXCHANGE_ERROR;
	private ProtocolExchangeStatus protocolExchangeStatus		= ProtocolExchangeStatus.ERROR;

	private DataSchemaFormat dataSchemaFormat					= null;
	private FieldAttributeSet primaryFieldSet 					= new ByteArrayFieldAttributeSet();
	
	public ProtocolSchemaFormat(ProtocolSchemaFormat psf,DataSchemaFormat dataSchemaFormat) 
			throws DataAccessException{
		
		if(
			dataSchemaFormat != null &&
			psf.protocol != null &&	psf.protocolResult != null && protocolExchangeType != null &&
			psf.protocolExchangeState != null && psf.protocolExchangeState != ProtocolExchangeState.NONE &&
			psf.protocolExchangeStatus != null && protocolExchangeStatus != ProtocolExchangeStatus.NONE 
		) {
		
			this.dataSchemaFormat 		= dataSchemaFormat;			
			this.protocol 				= psf.protocol;
			this.protocolResult 		= psf.protocolResult;
			this.protocolExchangeType	= psf.protocolExchangeType;
			this.protocolExchangeState 	= psf.protocolExchangeState;
			this.protocolExchangeStatus = psf.protocolExchangeStatus;
			
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
	
	
	
	public ProtocolSchemaFormat(DataSchemaFormat dataSchemaFormat,ProtocolSupport  protocol,
			ProtocolExchangeType protocolExchangeType,ProtocolResult  protocolResult,
			ProtocolExchangeState protocolExchangeState, ProtocolExchangeStatus protocolExchangeStatus
		) throws DataAccessException{
					
			if(
				dataSchemaFormat != null && protocol != null && protocolResult != null && protocolExchangeType != null &&
				protocolExchangeState != null && protocolExchangeState != ProtocolExchangeState.NONE &&
				protocolExchangeStatus != null && protocolExchangeStatus != ProtocolExchangeStatus.NONE
			) {
			
				this.dataSchemaFormat 		= dataSchemaFormat;
				
				this.primaryFieldSet.setPrimaryBitMap(this.dataSchemaFormat.primaryBitMap());
				this.primaryFieldSet.setSecondaryBitMap(this.dataSchemaFormat.secondaryBitMap());
												
				this.primaryFieldSet.addAttribute(asField(protocol.name(), ProtocolSupport.valueOf(protocol.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(protocolExchangeType.name(), ProtocolExchangeType.valueOf(protocolExchangeType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(protocolResult.name(), ProtocolResult.valueOf(protocolResult.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(protocolExchangeState.name(), ProtocolExchangeState.valueOf(protocolExchangeState.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(protocolExchangeStatus.name(), ProtocolExchangeStatus.valueOf(protocolExchangeStatus.name()).getCode()));
				
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
		
		builder.append("ProtocolSchemaFormat{");
		builder.append("\n schemaFor : "+schemaFor);		
		builder.append("\n size : "+size());
		builder.append("\n"+this.primaryFieldSet.toString());

		builder.append("\n");

		builder.append(dataSchemaFormat.toString());		
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
