package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Optional;

import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
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
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolSupport;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolSupport.ProtocolExchangeState;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolSupport.ProtocolExchangeStatus;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolExchangeType;
import org.flowr.framework.core.node.schema.ProtocolSchema.ProtocolResult;

public class ProtocolSchemaFormatHelper {


	 public static ProtocolSchemaFormat Protocol(
		ProtocolSupport  protocol,	ProtocolExchangeType protocolExchangeType, ProtocolResult  protocolResult,
		ProtocolExchangeState protocolExchangeState,ProtocolExchangeStatus protocolExchangeStatus,
		Attribute... protocolAttributes) 
		throws DataAccessException {
			
			PrimaryBitMap protocolPrimaryBitMap 			= new PrimaryBitMap(true);
			protocolPrimaryBitMap.setSecondaryBitMapPresent(true);
				
			Attribute protocolAttribute					= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																protocol.name(), 
																CHARSET, 
																ByteEnumerableType.type(protocol),				
																ByteEnumerableType.type(protocol).length, 
																ByteEnumerableType.type(protocol).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(protocol).length);
			
					
			FieldAttributeSet protocolAttributeSet 		= new ByteArrayFieldAttributeSet(protocolAttribute);
			
			protocolAttributeSet.setPrimaryBitMap(Optional.of(protocolPrimaryBitMap));
			
			SimpleEntry<PrimaryBitMap,FieldAttributeSet> protocolAttributeSetFields 	= 
					new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(protocolPrimaryBitMap,protocolAttributeSet);

			Attribute protocolValueAttribute			= new ByteArrayField(
																	FieldLengthType.FIXED, 
																	FieldOption.MANDATORY, 
																	FieldType.CHARACTER, 
																	protocol.name(), 
																	CHARSET, 
																	ByteEnumerableType.type(protocol),				
																	ByteEnumerableType.type(protocol).length, 
																	ByteEnumerableType.type(protocol).length, 
																	FIELD_DEFAULT,
																	ByteEnumerableType.type(protocol).length);


			FieldAttributeSet protocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(protocolValueAttribute);
			
			
			Arrays.asList(protocolAttributes).forEach(
					
					(attribute) -> {
						protocolAttributeSet.addAttribute(attribute);
					}
			);	
			
			SecondaryBitMap protocolSecondaryBitMap		= new SecondaryBitMap(true);
			
			protocolSecondaryBitMap.set(1, protocolAttributes.length+1, Boolean.TRUE);
			
			protocolAttributeSet.setSecondaryBitMap(Optional.of(protocolSecondaryBitMap));
						
			
			SimpleEntry<SecondaryBitMap,FieldAttributeSet> protocolValueAttributeSetFields 	= 
					new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(protocolSecondaryBitMap,protocolValueAttributeSet);
			
			DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(protocolAttributeSetFields, Optional.of(
					protocolValueAttributeSetFields));
			
			dataSchemaFormat.setDataFlowType(DataFlowType.PROTOCOL);
			
			ProtocolSchemaFormat protocolSchemaFormat = new ProtocolSchemaFormat(
														dataSchemaFormat, 
														protocol,	
														protocolExchangeType,
														protocolResult,
														protocolExchangeState,
														protocolExchangeStatus
													);
					
			
			protocolSchemaFormat.setSchemaFor(protocol.name());
							
			return protocolSchemaFormat;
		}
	
}
