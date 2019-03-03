package org.flowr.framework.core.node.schema;

import static org.flowr.framework.core.node.io.flow.data.DataConstants.CHARSET;
import static org.flowr.framework.core.node.io.flow.data.DataConstants.FIELD_DEFAULT;

import java.util.AbstractMap.SimpleEntry;
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
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataClassificationType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataSecurityType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataRetentionType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataRestrictionType;
import org.flowr.framework.core.node.schema.DataProtocolSchema.DataCompressionType;

/**
 * 
 * Converts the Operation Characteristics as Enumerable type in form of DataProtocolSchemaFormat.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public class DataProtocolSchemaFormatHelper {
    
  public static DataProtocolSchemaFormat DataMode(IOFlowType ioFlowType,DataMode dataMode) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataMode.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataMode),				
															ByteEnumerableType.type(dataMode).length, 
															ByteEnumerableType.type(dataMode).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataMode).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataMode.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataMode),				
																ByteEnumerableType.type(dataMode).length, 
																ByteEnumerableType.type(dataMode).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataMode).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap		= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
								  						dataSchemaFormat,
								  						ioFlowType,								  						
								  						DataFlowType.CONTIGOUS,
								  						dataMode, 
								  						DataOperationType.COMMAND,
								  						DataDistributionType.UNICAST
								  					);
		
		dataProtocolSchemaFormat.setSchemaFor(dataMode.name());
		
		return dataProtocolSchemaFormat;
	}
  
  public static DataProtocolSchemaFormat DataOperationType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataOperationType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataOperationType),				
															ByteEnumerableType.type(dataOperationType).length, 
															ByteEnumerableType.type(dataOperationType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataOperationType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataOperationType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataOperationType),				
																ByteEnumerableType.type(dataOperationType).length, 
																ByteEnumerableType.type(dataOperationType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataOperationType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
									  						dataSchemaFormat,
									  						ioFlowType,								  						
									  						DataFlowType.CONTIGOUS,
									  						dataMode, 
									  						dataOperationType,
									  						DataDistributionType.UNICAST
									  					);
		
		dataProtocolSchemaFormat.setSchemaFor(dataOperationType.name());
		
		return dataProtocolSchemaFormat;
	} 
  
  public static DataProtocolSchemaFormat DataDistributionType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataDistributionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataDistributionType),				
															ByteEnumerableType.type(dataDistributionType).length, 
															ByteEnumerableType.type(dataDistributionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataDistributionType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataOperationType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataDistributionType),				
																ByteEnumerableType.type(dataDistributionType).length, 
																ByteEnumerableType.type(dataDistributionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataDistributionType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataOperationType.name());

		return dataProtocolSchemaFormat;
	}  
  
  public static DataProtocolSchemaFormat DataSecurityType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType,
		  DataSecurityType dataSecurityType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataSecurityType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataSecurityType),				
															ByteEnumerableType.type(dataSecurityType).length, 
															ByteEnumerableType.type(dataSecurityType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataSecurityType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataSecurityType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataSecurityType),				
																ByteEnumerableType.type(dataSecurityType).length, 
																ByteEnumerableType.type(dataSecurityType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataSecurityType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataSecurityType.name());

		return dataProtocolSchemaFormat;
	}  
  
  public static DataProtocolSchemaFormat DataClassificationType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType,
		  DataClassificationType dataClassificationType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataClassificationType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataClassificationType),				
															ByteEnumerableType.type(dataClassificationType).length, 
															ByteEnumerableType.type(dataClassificationType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataClassificationType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataClassificationType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataClassificationType),				
																ByteEnumerableType.type(dataClassificationType).length, 
																ByteEnumerableType.type(dataClassificationType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataClassificationType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataClassificationType.name());

		return dataProtocolSchemaFormat;
	}  

  public static DataProtocolSchemaFormat DataRetentionType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType,
		  DataRetentionType dataRetentionType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataRetentionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataRetentionType),				
															ByteEnumerableType.type(dataRetentionType).length, 
															ByteEnumerableType.type(dataRetentionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataRetentionType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataRetentionType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataRetentionType),				
																ByteEnumerableType.type(dataRetentionType).length, 
																ByteEnumerableType.type(dataRetentionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataRetentionType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataRetentionType.name());

		return dataProtocolSchemaFormat;
	}  
  
  public static DataProtocolSchemaFormat DataRestrictionType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType,
		  DataRestrictionType dataRestrictionType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataRestrictionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataRestrictionType),				
															ByteEnumerableType.type(dataRestrictionType).length, 
															ByteEnumerableType.type(dataRestrictionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataRestrictionType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataRestrictionType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataRestrictionType),				
																ByteEnumerableType.type(dataRestrictionType).length, 
																ByteEnumerableType.type(dataRestrictionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataRestrictionType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataRestrictionType.name());

		return dataProtocolSchemaFormat;
	}  
  
  public static DataProtocolSchemaFormat DataCompressionType(IOFlowType ioFlowType,DataMode dataMode,
		  DataOperationType dataOperationType, DataDistributionType dataDistributionType,
		  DataCompressionType dataCompressionType) throws DataAccessException {
		
		PrimaryBitMap dataProtocolPrimaryBitMap 			= new PrimaryBitMap(true);
		dataProtocolPrimaryBitMap.setSecondaryBitMapPresent(true);
			
		Attribute dataProtocolAttribute					= new ByteArrayField(
															FieldLengthType.FIXED, 
															FieldOption.MANDATORY, 
															FieldType.CHARACTER, 
															dataCompressionType.name(), 
															CHARSET, 
															ByteEnumerableType.type(dataCompressionType),				
															ByteEnumerableType.type(dataCompressionType).length, 
															ByteEnumerableType.type(dataCompressionType).length, 
															FIELD_DEFAULT,
															ByteEnumerableType.type(dataCompressionType).length);
		
				
		FieldAttributeSet dataProtocolAttributeSet 		= new ByteArrayFieldAttributeSet(dataProtocolAttribute);
		
		dataProtocolAttributeSet.setPrimaryBitMap(Optional.of(dataProtocolPrimaryBitMap));
		
		SimpleEntry<PrimaryBitMap,FieldAttributeSet> dataProtocolAttributeSetFields 	= 
				new SimpleEntry<PrimaryBitMap,FieldAttributeSet>(dataProtocolPrimaryBitMap,dataProtocolAttributeSet);

		Attribute dataProtocolValueAttribute				= new ByteArrayField(
																FieldLengthType.FIXED, 
																FieldOption.MANDATORY, 
																FieldType.CHARACTER, 
																dataCompressionType.name(), 
																CHARSET, 
																ByteEnumerableType.type(dataCompressionType),				
																ByteEnumerableType.type(dataCompressionType).length, 
																ByteEnumerableType.type(dataCompressionType).length, 
																FIELD_DEFAULT,
																ByteEnumerableType.type(dataCompressionType).length);


		FieldAttributeSet dataProtocolValueAttributeSet 	= new ByteArrayFieldAttributeSet(dataProtocolValueAttribute);
		
		SecondaryBitMap dataProtocolSecondaryBitMap			= new SecondaryBitMap(true);
		
		dataProtocolAttributeSet.setSecondaryBitMap(Optional.of(dataProtocolSecondaryBitMap));
		
		SimpleEntry<SecondaryBitMap,FieldAttributeSet> dataProtocolValueAttributeSetFields 	= 
				new SimpleEntry<SecondaryBitMap,FieldAttributeSet>(dataProtocolSecondaryBitMap,dataProtocolValueAttributeSet);
		
		DataSchemaFormat dataSchemaFormat = new DataSchemaFormat(dataProtocolAttributeSetFields, Optional.of(
				dataProtocolValueAttributeSetFields));
		
		dataSchemaFormat.setDataFlowType(DataFlowType.COMMAND);
		
		DataProtocolSchemaFormat dataProtocolSchemaFormat = new DataProtocolSchemaFormat(
																dataSchemaFormat,
																ioFlowType,								  						
																DataFlowType.CONTIGOUS,
																dataMode, 
																dataOperationType,
																dataDistributionType
															);

		dataProtocolSchemaFormat.setSchemaFor(dataCompressionType.name());

		return dataProtocolSchemaFormat;
	}  
  
  
}
