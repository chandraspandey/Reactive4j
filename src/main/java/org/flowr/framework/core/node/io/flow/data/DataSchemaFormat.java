package org.flowr.framework.core.node.io.flow.data;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.node.io.flow.data.binary.ByteArrayFieldBuffer;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.BitAttributeSet;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.PrimaryBitMap;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.SecondaryBitMap;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.ByteArrayField;
import org.flowr.framework.core.node.io.flow.data.binary.collection.ByteCollection.FieldAttributeSet;

/**
 * Provides default implementation of FormatSequence for a DataField with optional Prefix & Suffix sequence option.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public class DataSchemaFormat implements DataFormat{

	private DataFlowType dataFlowType 					= DataFlowType.CONTIGOUS;
	private boolean isSchematicData						= false;	
	private PrimaryBitMap primaryBitMap 				= null;
	private FieldAttributeSet primaryfieldAttributeSet 	= null;
	private SecondaryBitMap secondaryBitMap				= null;
	private FieldAttributeSet secondaryfieldAttributeSet= null;
	
	public DataSchemaFormat(DataSchemaFormat dataSchemaFormat) throws DataAccessException{
		
		if(dataSchemaFormat != null && dataSchemaFormat.primaryBitMap != null && 
				dataSchemaFormat.primaryfieldAttributeSet != null) {
			
			this.isSchematicData = true;
		
			this.dataFlowType 				= dataSchemaFormat.dataFlowType;
			this.isSchematicData			= dataSchemaFormat.isSchematicData;
			this.primaryBitMap				= dataSchemaFormat.primaryBitMap;
			this.primaryfieldAttributeSet	= dataSchemaFormat.primaryfieldAttributeSet;
			this.secondaryBitMap			= dataSchemaFormat.secondaryBitMap;
			this.secondaryfieldAttributeSet = dataSchemaFormat.secondaryfieldAttributeSet;
		}else {
			
			DataAccessException exception = new DataAccessException(
				ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
				ExceptionMessages.MSG_IO_INVALID_INPUT,
				"Invalid DataSchemaFormat. PrimaryAttributeSet & PrimaryFieldAttributeSet not present for SchematicData."
			);
			throw exception;
		}
	}
	
	public DataSchemaFormat(			
			SimpleEntry<PrimaryBitMap,FieldAttributeSet> primaryFields,	
			Optional<SimpleEntry<SecondaryBitMap,FieldAttributeSet>> secondaryFields	
		) throws DataAccessException{	
				
			if(primaryFields != null) {
				
				this.isSchematicData = true;
				
				primaryBitMap = primaryFields.getKey();			
					
				primaryfieldAttributeSet 	= primaryFields.getValue();
				
				if( secondaryFields.isPresent() ) {
				
					if(primaryBitMap.isSecondaryBitMapPresent()) {
						
						secondaryBitMap	= secondaryFields.get().getKey();
						secondaryfieldAttributeSet = secondaryFields.get().getValue();
					}else {
						
						DataAccessException exception = new DataAccessException(
							ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
							ExceptionMessages.MSG_IO_INVALID_INPUT,
							"SecondaryAttributeSet not present for SchematicData, "
							+ "while PrimaryAttributeSet marked for presence."
						);
						throw exception;						
					}
				}else {
					
					DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
						ExceptionMessages.MSG_IO_INVALID_INPUT,
						"SecondaryAttributeSet or SecondaryFieldAttributeSet not present for SchematicData."
					);
					throw exception;	
				}

			}else {
				
				DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"PrimaryAttributeSet & PrimaryFieldAttributeSet not present for SchematicData."
				);
				throw exception;
			}
		
		}
	
	public DataSchemaFormat(			
		Tuple<BitAttributeSet,FieldAttributeSet> primaryFields,	
		Optional<Tuple<BitAttributeSet,FieldAttributeSet>> secondaryFields	
	) throws DataAccessException{	
			
		if(primaryFields != null) {
			
			this.isSchematicData = true;
			
			primaryBitMap = new PrimaryBitMap(primaryFields.getKey());			
				
			primaryBitMap.setSecondaryBitMapPresent(primaryFields.getKey().isSecondaryMapPresent());
			
			primaryfieldAttributeSet 	= primaryFields.getValues();
			
			if( secondaryFields.isPresent() ) {
			
				if(primaryFields.getKey().isSecondaryMapPresent()) {
					
					secondaryBitMap	= new SecondaryBitMap(secondaryFields.get().getKey());
					secondaryfieldAttributeSet = secondaryFields.get().getValues();
				}else {
					
					DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
						ExceptionMessages.MSG_IO_INVALID_INPUT,
						"SecondaryAttributeSet not present for SchematicData, "
						+ "while PrimaryAttributeSet marked for presence."
					);
					throw exception;						
				}
			}else {
				
				DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
					ExceptionMessages.MSG_IO_INVALID_INPUT,
					"SecondaryAttributeSet or SecondaryFieldAttributeSet not present for SchematicData."
				);
				throw exception;	
			}

		}else {
			
			DataAccessException exception = new DataAccessException(
				ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
				ExceptionMessages.MSG_IO_INVALID_INPUT,
				"PrimaryAttributeSet & PrimaryFieldAttributeSet not present for SchematicData."
			);
			throw exception;
		}
	
	}
	
	@Override
	public void setDataFlowType(DataFlowType dataFlowType) {
		this.dataFlowType = dataFlowType;
	}

	@Override
	public DataFlowType getDataFlowType() {
		return this.dataFlowType;
	}

	@Override
	public boolean isSchematicData() {
		return this.isSchematicData;
	}
	
	public int size() {
		
		int length = 0;
		
		if(primaryBitMap != null) {
			
			length += primaryBitMap.getBitMapSize();
		}
		
		if(primaryfieldAttributeSet != null) {
			length += primaryfieldAttributeSet.size();
		}
		
		if(secondaryBitMap != null) {
			length += secondaryBitMap.getBitMapSize();
		}
		
		if(secondaryfieldAttributeSet != null) {
			length += secondaryfieldAttributeSet.size();
		}
			
		System.out.println("DataSchemaFormat : length : "+length);
		
		return length;
	}
	
	public byte[] getData() throws DataAccessException{
				
		byte[] dataSchemaBuffer = null;
		
		if(isSchematicData) {
				
			ByteArrayFieldBuffer byteArrayFieldBuffer = new ByteArrayFieldBuffer(size());			
				
			byteArrayFieldBuffer.put(primaryBitMap.get().toByteArray());			
			
			Iterator<Attribute> primaryIter = primaryfieldAttributeSet.getAttributeList().iterator();
			
			while(primaryIter.hasNext()) {
				
				byteArrayFieldBuffer.put(((ByteArrayField)primaryIter.next()).getByteArrayValue());
			}
									
			
			if(secondaryBitMap != null) {
				
				byteArrayFieldBuffer.put(primaryBitMap.get().toByteArray());
			}
			
			Iterator<Attribute> secondaryIter = secondaryfieldAttributeSet.getAttributeList().iterator();
			
			while(secondaryIter.hasNext()) {
				
				byteArrayFieldBuffer.put(((ByteArrayField)secondaryIter.next()).getByteArrayValue());
			}			
			
			dataSchemaBuffer = byteArrayFieldBuffer.get();
		}else {
			
			DataAccessException exception = new DataAccessException(
				ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,
				ExceptionMessages.MSG_IO_INVALID_INPUT,
				"PrimaryAttributeSet & PrimaryFieldAttributeSet not present for SchematicData."
			);
			throw exception;
		}
		
		return dataSchemaBuffer;
	}
	
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("DataSchemaFormat{");
		
		builder.append("\n : size : "+this.size());
		
		builder.append(primaryBitMap);
		
		if(primaryfieldAttributeSet != null && !primaryfieldAttributeSet.isEmpty()) {
			
			Iterator<Attribute> primaryIter = primaryfieldAttributeSet.getAttributeList().iterator();
			
			builder.append("\n PRIMARY FIELDS : ");
			
			while(primaryIter.hasNext()) {
				
				ByteArrayField byteArrayField = (ByteArrayField)primaryIter.next();
				
				builder.append("\n - " +byteArrayField.getFieldName()+" | "+byteArrayField.getFieldValue()
					+" | "+Arrays.toString(byteArrayField.getByteArrayValue())
				);
			}
		}
		
		if(secondaryBitMap != null) {
			builder.append(secondaryBitMap);
		}
		
		if(secondaryfieldAttributeSet != null && !secondaryfieldAttributeSet.isEmpty()) {
		
			Iterator<Attribute> secondaryIter = secondaryfieldAttributeSet.getAttributeList().iterator();
			
			builder.append("\n SECONDARY FIELDS : ");
			
			while(secondaryIter.hasNext()) {
				
				ByteArrayField byteArrayField = (ByteArrayField)secondaryIter.next();
				
				builder.append("\n - " +byteArrayField.getFieldName()+" | "+byteArrayField.getFieldValue()
					+" | "+Arrays.toString(byteArrayField.getByteArrayValue())
				);
			}	
		}
		
		builder.append("\n}\n");
		
		return builder.toString();
		
		
	}
}
