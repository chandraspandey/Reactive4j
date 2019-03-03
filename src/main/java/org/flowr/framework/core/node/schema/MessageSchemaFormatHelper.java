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
import org.flowr.framework.core.node.io.flow.protocol.DataProtocol.DataMode;
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

public class MessageSchemaFormatHelper {


	 public static MessageSchemaFormat MessageFlowType(IOFlowType ioFlowType,DataMode dataMode, 
		MessageFlowType messageFlowType, MessageConversionType messageConversionType,
		MessagePersistenceType messagePersistenceType, MessageErrorHandlingType messageErrorHandlingType,
		MessagingRoleType messagingRoleType, MessageTransformationType messageTransformationType,
		MessageExecutionType messageExecutionType, MessageArchivalMode messageArchivalMode,
		MessageProcessingState messageProcessingState, MessageProcessingStatus messageProcessingStatus,
		Attribute... messageAttributes) 
			 throws DataAccessException {
			
			PrimaryBitMap messagePrimaryBitMap 			= new PrimaryBitMap(true);
			messagePrimaryBitMap.setSecondaryBitMapPresent(true);
				
			Attribute messageAttribute					= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																messageFlowType.name(), 
																CHARSET, 
																ByteEnumerableType.type(messageFlowType),				
																ByteEnumerableType.type(messageFlowType).length, 
																ByteEnumerableType.type(messageFlowType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(messageFlowType).length);
			
					
			FieldAttributeSet messageAttributeSet 		= new ByteArrayFieldAttributeSet(messageAttribute);
			
			messageAttributeSet.setPrimaryBitMap(Optional.of(messagePrimaryBitMap));
			
			SimpleEntry<PrimaryBitMap,FieldAttributeSet> messageAttributeSetFields 	= 
					new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(messagePrimaryBitMap,messageAttributeSet);

			Attribute messageValueAttribute				= new ByteArrayField(
																	FieldLengthType.FIXED, 
																	FieldOption.MANDATORY, 
																	FieldType.CHARACTER, 
																	messageFlowType.name(), 
																	CHARSET, 
																	ByteEnumerableType.type(messageFlowType),				
																	ByteEnumerableType.type(messageFlowType).length, 
																	ByteEnumerableType.type(messageFlowType).length, 
																	FIELD_DEFAULT,
																	ByteEnumerableType.type(dataMode).length);


			FieldAttributeSet messageValueAttributeSet 	= new ByteArrayFieldAttributeSet(messageValueAttribute);
			
			
			Arrays.asList(messageAttributes).forEach(
					
					(attribute) -> {
						messageAttributeSet.addAttribute(attribute);
					}
			);	
			
			SecondaryBitMap messageSecondaryBitMap		= new SecondaryBitMap(true);
			
			messageSecondaryBitMap.set(1, messageAttributes.length+1, Boolean.TRUE);
			
			messageAttributeSet.setSecondaryBitMap(Optional.of(messageSecondaryBitMap));
						
			
			SimpleEntry<SecondaryBitMap,FieldAttributeSet> messageValueAttributeSetFields 	= 
					new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(messageSecondaryBitMap,messageValueAttributeSet);
			
			DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(messageAttributeSetFields, Optional.of(
					messageValueAttributeSetFields));
			
			dataSchemaFormat.setDataFlowType(DataFlowType.MESSAGE);
			
			MessageSchemaFormat messageSchemaFormat = new MessageSchemaFormat(
														dataSchemaFormat, 
														messageFlowType,	
														messageConversionType,
														messagePersistenceType,
														messageErrorHandlingType,
														messagingRoleType,	
														messageTransformationType,
														messageExecutionType,
														messageArchivalMode,
														messageProcessingState,
														messageProcessingStatus
													);
					
			
			messageSchemaFormat.setSchemaFor(messageFlowType.name());
							
			return messageSchemaFormat;
		}
	
}
