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
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageArchivalMode;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageConversionType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageErrorHandlingType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageExecutionType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageFlowType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessagePersistenceType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageProcessingState;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageProcessingStatus;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessageTransformationType;
import org.flowr.framework.core.node.schema.MessageSchema.Messaging.MessagingRoleType;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class MessageSchemaFormat implements MessageSchema{

	private String schemaFor									= null;
	private boolean isSchematicData								= false;	
	private MessageFlowType  messageFlowType					= MessageFlowType.NONE;	
	
	private MessageConversionType messageConversionType			= MessageConversionType.NONE;
	private MessagePersistenceType messagePersistenceType		= MessagePersistenceType.NONE;
	private MessageErrorHandlingType messageErrorHandlingType	= MessageErrorHandlingType.NONE;
	private MessagingRoleType messagingRoleType					= MessagingRoleType.NONE;
	private MessageTransformationType messageTransformationType = MessageTransformationType.NONE;
	private MessageExecutionType messageExecutionType			= MessageExecutionType.NONE;
	private MessageArchivalMode messageArchivalMode				= MessageArchivalMode.NONE;
			
	private MessageProcessingState messageProcessingState		= MessageProcessingState.NONE;
	private MessageProcessingStatus messageProcessingStatus		= MessageProcessingStatus.NONE;	
	

	private DataSchemaFormat dataSchemaFormat					= null;
	private FieldAttributeSet primaryFieldSet 					= new ByteArrayFieldAttributeSet();
	
	public MessageSchemaFormat(MessageSchemaFormat osf,DataSchemaFormat dataSchemaFormat) 
			throws DataAccessException{
		
		if(
			dataSchemaFormat != null &&
			osf.messageFlowType != null && osf.messageFlowType != MessageFlowType.NONE && 
			osf.messageConversionType != null && osf.messageConversionType != MessageConversionType.NONE &&
			osf.messagePersistenceType != null && osf.messagePersistenceType != MessagePersistenceType.NONE &&
			osf.messageErrorHandlingType != null && messageErrorHandlingType != MessageErrorHandlingType.NONE &&			
			osf.messagingRoleType != null && osf.messagingRoleType != MessagingRoleType.NONE &&			
			osf.messageTransformationType != null && osf.messageTransformationType != MessageTransformationType.NONE &&
			osf.messageExecutionType != null && osf.messageExecutionType != MessageExecutionType.NONE &&
			osf.messageArchivalMode != null && osf.messageArchivalMode != MessageArchivalMode.NONE  &&
			osf.messageProcessingState != null && osf.messageProcessingState != MessageProcessingState.NONE &&
			osf.messageProcessingStatus != null && osf.messageProcessingStatus != MessageProcessingStatus.NONE
		) {
		
			this.dataSchemaFormat 		= dataSchemaFormat;			
			this.messageFlowType 			= osf.messageFlowType;
			this.messageConversionType 			= osf.messageConversionType;
			this.messagePersistenceType 			= osf.messagePersistenceType;
			this.messagingRoleType 	= osf.messagingRoleType;
			this.messageErrorHandlingType = osf.messageErrorHandlingType;
			this.messageProcessingState 		= osf.messageProcessingState;
			this.messageProcessingStatus 		= osf.messageProcessingStatus;
			
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
	
	
	
	public MessageSchemaFormat(DataSchemaFormat dataSchemaFormat,MessageFlowType  messageFlowType,
			MessageConversionType messageConversionType,MessagePersistenceType messagePersistenceType,
			MessageErrorHandlingType messageErrorHandlingType,MessagingRoleType messagingRoleType,
			MessageTransformationType messageTransformationType,MessageExecutionType messageExecutionType,
			MessageArchivalMode messageArchivalMode,MessageProcessingState messageProcessingState,
			MessageProcessingStatus messageProcessingStatus
		) throws DataAccessException{
					
			if(
				dataSchemaFormat != null &&
				messageFlowType != null && messageFlowType != MessageFlowType.NONE && 
				messageConversionType != null && messageConversionType != MessageConversionType.NONE &&
				messagePersistenceType != null && messagePersistenceType != MessagePersistenceType.NONE &&
				messageErrorHandlingType != null && messageErrorHandlingType != MessageErrorHandlingType.NONE &&
				messagingRoleType != null && messagingRoleType != MessagingRoleType.NONE &&			
				messageTransformationType != null && messageTransformationType != MessageTransformationType.NONE &&
				messageExecutionType != null && messageExecutionType != MessageExecutionType.NONE &&
				messageArchivalMode != null && messageArchivalMode != MessageArchivalMode.NONE &&
				messageProcessingState != null && messageProcessingState != MessageProcessingState.NONE &&
				messageProcessingStatus != null && messageProcessingStatus != MessageProcessingStatus.NONE
			) {
			
				this.dataSchemaFormat 		= dataSchemaFormat;
				
				this.primaryFieldSet.setPrimaryBitMap(this.dataSchemaFormat.primaryBitMap());
				this.primaryFieldSet.setSecondaryBitMap(this.dataSchemaFormat.secondaryBitMap());
												
				this.primaryFieldSet.addAttribute(asField(messageFlowType.name(), MessageFlowType.valueOf(messageFlowType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageConversionType.name(), MessageConversionType.valueOf(messageConversionType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messagePersistenceType.name(), MessagePersistenceType.valueOf(messagePersistenceType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageErrorHandlingType.name(), MessageErrorHandlingType.valueOf(messageErrorHandlingType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messagingRoleType.name(), MessagingRoleType.valueOf(messagingRoleType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageTransformationType.name(), MessageTransformationType.valueOf(messageTransformationType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageExecutionType.name(),MessageExecutionType.valueOf(messageExecutionType.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageArchivalMode.name(), MessageArchivalMode.valueOf(messageArchivalMode.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageProcessingState.name(), MessageProcessingState.valueOf(messageProcessingState.name()).getCode()));
				this.primaryFieldSet.addAttribute(asField(messageProcessingStatus.name(), MessageProcessingStatus.valueOf(messageProcessingStatus.name()).getCode()));
				
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
		
		builder.append("MessageSchemaFormat{");
		builder.append("\n schemaFor : "+schemaFor);		
		builder.append("\n size : "+size());
		builder.append("\n"+this.primaryFieldSet.toString());

		builder.append("\n");

		builder.append(dataSchemaFormat.toString());		
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
