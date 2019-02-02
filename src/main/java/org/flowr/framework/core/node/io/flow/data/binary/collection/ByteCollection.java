package org.flowr.framework.core.node.io.flow.data.binary.collection;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField;
import org.flowr.framework.core.node.io.flow.data.DataSchema.DataField.FieldOption;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.PrimaryBitMap;
import org.flowr.framework.core.node.io.flow.data.binary.collection.BitCollection.SecondaryBitMap;

/**
 * 
 * Provides Byte oriented collections for instrumentation
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ByteCollection {

	/**
	 * Provides additional instrumentation required for supporting type as ByteArrayField.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 */
	public interface FieldAttributeSet extends AttributeSet{
		
		
		public Optional<PrimaryBitMap> getPrimaryBitMap();
		
		public void setPrimaryBitMap(Optional<PrimaryBitMap> primaryBitMap);

		public Optional<SecondaryBitMap> getSecondaryBitMap();

		public void setSecondaryBitMap(Optional<SecondaryBitMap> secondaryBitMap);
		
		public void enforcePadding(boolean mandatoryFlag, boolean conditionalFlag);
		
		public ByteArrayField get(String fieldName);
		
		public boolean isValid();
		
		public boolean isValidFieldType();
		
		public boolean isKeyPresent(String fieldName);
		
		public boolean isValuePresent(String fieldValue);
		
		public int size();
		
	}
	
	
	/**
	 * Provides concrete implementation of the Field for validation. Defines inbuilt constraints for the Byte Array for
	 * providing larger composition. Provides String output based on charset setting along with other attributes to 
	 * support padding & discrete composition.
	 * The attribute startPosition & endPosition are used for copying data from input array/buffer/stream
	 * The attributes isMandatory, minLength & maxLength are provided for validation & padding support.
	 * The attribute PaddingType & padWith is provided for optional padding support
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class ByteArrayField implements DataField {

		private Charset charset 				= StandardCharsets.ISO_8859_1;

		private FieldOption fieldOption 		= FieldOption.OPTIONAL;
		private FieldLengthType fieldLengthType = FieldLengthType.FIXED;
		private FieldType fieldType 			= FieldType.ALPHANUMERIC;
		private FieldStatus fieldStatus 		= FieldStatus.EMPTY;

		// Defines name of the field
		private String fieldName;
		
		// Readable field value representation of byteArrayValue based as per ISO Charset
		private String fieldValue;
		
		// Holds the byte array value
		private byte[] byteArrayValue;
		
		// Defines Padding instrumentation
		private PaddingType paddingType		  = PaddingType.NONE;
		private Optional<byte[]> paddingArray = Optional.empty();
		private boolean isPadded;		

		// Defines start & end position for sequential reading/writing in composite byte array
		private int startPosition;
		private int endPosition;
		
		// Defines min & max length of the byte array
		private int minLength;
		private int maxLength;

		public ByteArrayField(FieldLengthType fieldLengthType, FieldOption fieldOption,FieldType fieldType,String fieldName,
			Charset charset,  byte[] byteArrayValue,int minLength,int maxLength,int startPosition, int endPosition){
			
			this.fieldLengthType				= fieldLengthType;
			this.fieldOption					= fieldOption;
			this.fieldType						= fieldType;
			this.fieldName						= fieldName;
			this.startPosition					= startPosition;
			this.endPosition					= endPosition;		
			this.minLength						= minLength;
			this.maxLength						= maxLength;		
			
			// Default to creation of max length if not specified & mark FieldStatus for validation after padding
			if(byteArrayValue != null && byteArrayValue.length > 0){
				this.byteArrayValue = byteArrayValue;
				this.fieldStatus 	= FieldStatus.FILLED;
			}else{
				this.byteArrayValue = new byte[maxLength];
				this.fieldStatus 	= FieldStatus.PROVISION;
			}
		}

		@Override
		public Optional<byte[]> getPadding() {
			return paddingArray;
		}


		@Override
		public void enforcePadding(byte[] paddingArray) {
			this.paddingArray = Optional.of(paddingArray);
		}
		
		@Override
		public String getFieldName() {
			return fieldName;
		}

		@Override
		public String getFieldValue() {
			
			String val = "";
			
			if(charset != null){		
				
				
				if(fieldType == FieldType.NUMERIC ) {
					
					val = Arrays.toString(this.byteArrayValue);
				}else {
					val = new String(this.byteArrayValue,charset);
				}
				
			}else{
				
				if(fieldType == FieldType.NUMERIC ) {
					
					val = Arrays.toString(this.byteArrayValue);
				}else {
					val = new String(this.byteArrayValue,Charset.defaultCharset());
				}
			}
			
			return val;
		}
		
		@Override
		public byte[] getByteArrayValue() {
			return byteArrayValue;
		}
		
		@Override
		public void setByteArrayValue(byte[] byteArrayValue) {
			
			this.byteArrayValue = byteArrayValue;
			this.fieldStatus 	= FieldStatus.FILLED;
		}

		@Override
		public int getMinLength() {
			return minLength;
		}
		@Override
		public int getMaxLength() {
			return maxLength;
		}
		@Override
		public int getStartPosition() {
			return startPosition;
		}

		@Override
		public int getEndPosition() {
			return endPosition;
		}

		@Override
		public FieldType getFieldType() {
			return fieldType;
		}
		
		@Override
		public FieldLengthType getFieldLengthType() {
			return fieldLengthType;
		}
		
		/**
		 * Returns the actual length including of primary & secondary bitmap if present.
		 * @return
		 */
		@Override
		public int getActualLength() {
			
			return byteArrayValue.length;
		}
	
		@Override
		public boolean isValid(){		
			
			boolean isValid = false;
			
			if(fieldOption == FieldOption.MANDATORY && fieldStatus != FieldStatus.EMPTY && isPadded){
			
				if( fieldStatus == FieldStatus.FILLED && byteArrayValue.length > 0 && 
						minLength <= byteArrayValue.length  && byteArrayValue.length <= maxLength){
					
					isValid = true;			
				}else{
					isValid = false;
				}
			}
			
			return isValid;
		}
		
		@Override
		public byte[] getPaddedByteArrayValue() {
			
			byte[] byteArray = null;
			
			if(this.minLength > 0 && this.maxLength > 0  && this.byteArrayValue != null){
			
				if(paddingArray.isPresent() ){
					
					byteArray = new byte[maxLength];
								
					int padLength = paddingArray.get().length;	
					
					if(paddingType == PaddingType.LEFT){
						
						System.arraycopy(paddingArray.get(), 0, byteArray, 0, padLength );
						System.arraycopy(byteArrayValue, padLength, byteArray, padLength, (maxLength-padLength) );
						
					}else if(paddingType == PaddingType.RIGHT){
						
						System.arraycopy(byteArrayValue, 0, byteArray, 0, (maxLength-padLength) );
						System.arraycopy(paddingArray.get(), 0, byteArray, (maxLength-padLength), padLength );
						
						
					}else{
						byteArray = Arrays.copyOf(byteArrayValue, maxLength);
					}
					
				}else{
					byteArray = Arrays.copyOf(byteArrayValue, maxLength);
				}
			}else{
				byteArray = byteArrayValue;
			}
			return byteArrayValue;
		}
		
		@Override
		public void enforcePadding() {
			
			byte[] byteArray = null;
			
			if( 	
					byteArrayValue != null && byteArrayValue.length > 0 && 
					minLength <= byteArrayValue.length  && byteArrayValue.length <= maxLength
			){
			
				if(paddingArray.isPresent() ){
					
					byteArray = new byte[maxLength];
								
					int padLength = paddingArray.get().length;	
					
					if(paddingType == PaddingType.LEFT){
						
						System.arraycopy(paddingArray.get(), 0, byteArray, 0, padLength );
						System.arraycopy(byteArrayValue, padLength, byteArray, padLength, (maxLength-padLength) );
						this.isPadded = true;
					}else if(paddingType == PaddingType.RIGHT){
						
						System.arraycopy(byteArrayValue, 0, byteArray, 0, (maxLength-padLength) );
						System.arraycopy(paddingArray.get(), 0, byteArray, (maxLength-padLength), padLength );					
						this.isPadded = true;					
					}else{
						byteArray = Arrays.copyOf(byteArrayValue, maxLength);
					}
					
					
				}else{
					byteArray = Arrays.copyOf(byteArrayValue, maxLength);
				}
				
				this.byteArrayValue = byteArray;
			}
			
		}
		
		@Override
		public boolean isValidFieldType(){
			
			boolean isValid = false;
			
			if(byteArrayValue.length > 0){
				
				String fieldVal = getFieldValue();			
			
				if(fieldType == FieldType.CHARACTER){
					
					for(int index = 0; index < fieldVal.length() ; index++){
											
						if(!Character.isLetter(fieldVal.charAt(index))){
							isValid = false;
							break;
						}else{
							isValid = true;
						}
					}
				}else if(fieldType == FieldType.NUMERIC){
					
					for(int index = 0; index < fieldVal.length() ; index++){
						
						if(!Character.isDigit(fieldVal.charAt(index))){
							isValid = false;
							break;
						}else{
							isValid = true;
						}					
					}
				}else if(fieldType == FieldType.ALPHANUMERIC){
					
					for(int index = 0; index < fieldVal.length() ; index++){
						
						if(!Character.isDigit(fieldVal.charAt(index)) || Character.isLetter(fieldVal.charAt(index))){
							isValid = false;
							break;
						}else{
							isValid = true;
						}					
					}
				}else {
					isValid = false;
				}
			}
			
			return isValid;
		}

		@Override
		public boolean isKeyPresent(String fieldName){
			
			boolean isPresent = false;
			
			if(this.fieldName.equals(fieldName)){
				
				isPresent =  true;
			}
			
			return isPresent;
		}
		
		@Override
		public boolean isValuePresent(String fieldValue){
			
			boolean isPresent = false;
				
			if(  this.fieldValue.equals(fieldValue)){
				
				isPresent =  true;
			}
			
			return isPresent;
		}
		
		
		/*
		 * Provides a wrapper functionality around pattern matching functionality
		 * of String for region matching.
		 */
		@Override
		public boolean isPatternMatch(int toffset, String other, int offset, int len){
			
			boolean isPresent = fieldValue.regionMatches(
					toffset, other, offset, len);
			
			return isPresent;
		}

		@Override
		public PaddingType getPaddingType() {
			return paddingType;
		}

		@Override
		public void setPaddingType(PaddingType paddingType) {
			this.paddingType = paddingType;
		}
		@Override
		public boolean isPadded() {
			return isPadded;
		}
		@Override
		public FieldOption getFieldOption() {
			return fieldOption;
		}

		@Override
		public FieldStatus getFieldStatus() {
			return fieldStatus;
		}

		public String toString(){
			
			return "\n ByteArrayField {"+
					"  fieldOption : "+fieldOption+
					" | fieldStatus : "+fieldStatus+
					" | fieldType : "+fieldType+					
					" | isPadded : "+isPadded+
					" | paddingType : "+paddingType+
					" | paddingArray : "+paddingArray+
					" | charset : "+charset+					
					" | byteArrayValue : "+byteArrayValue+
					" | minLength : "+minLength+
					" | maxLength : "+maxLength+				
					" | startPosition : "+startPosition+
					" | endPosition : "+endPosition+	
					"\n | fieldName : "+fieldName+
					" | fieldValue : "+getFieldValue()+
					" } ";
		}
	}

	/**
	 * A collection wrapper for ByteArrayField that can form a set of information. Custom instrumentation is provided for
	 * linking PrimaryBitMap which can be used in association with List by the calling program for I/O operation.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class ByteArrayFieldAttributeSet implements FieldAttributeSet{

		private List<Attribute> attributeList = new ArrayList<Attribute>();

		// Holds the primary bitmap position defined for this field, used as an anchor for setting standard based identification
		private Optional<PrimaryBitMap> primaryBitMap;
		
		// Holds the secondary bitmap position defined for this field, used as an anchor for setting standard based identification
		private Optional<SecondaryBitMap> secondaryBitMap;
		
		public ByteArrayFieldAttributeSet() {
			
		}
		
		public ByteArrayFieldAttributeSet(Attribute...attributes) {
			
			if(attributes != null && attributes.length > 0) {
				
				attributeList.addAll(Arrays.asList(attributes));		
			}
		}
		
		@Override
		public Optional<PrimaryBitMap> getPrimaryBitMap() {
			return primaryBitMap;
		}

		@Override
		public void setPrimaryBitMap(Optional<PrimaryBitMap> primaryBitMap) {
			this.primaryBitMap = primaryBitMap;
		}
		
		@Override
		public Optional<SecondaryBitMap> getSecondaryBitMap() {
			return secondaryBitMap;
		}

		@Override
		public void setSecondaryBitMap(Optional<SecondaryBitMap> secondaryBitMap) {
			this.secondaryBitMap = secondaryBitMap;
		}
		
		@Override
		public List<Attribute> getAttributeList() {
			return this.attributeList;
		}

		@Override
		public void setAttributeList(List<Attribute> attributeList) {
			this.attributeList=attributeList;		
		}


		@Override
		public void addAttribute(Attribute dataAttribute) {
			attributeList.add(dataAttribute);
		}
		
		@Override
		public void enforcePadding(boolean mandatoryFlag, boolean conditionalFlag){
	        
	        if(!attributeList.isEmpty()){
	            
	             Iterator<Attribute> fieldIterator = attributeList.iterator();
	            
	             ByteArrayField field = null;
	            
	             while(fieldIterator.hasNext()){
	                                       
	                  field = (ByteArrayField) fieldIterator.next();
	                 
	                  if(field.getFieldOption() == FieldOption.MANDATORY ){
	                       
	                	  if(mandatoryFlag) {
	                        field.enforcePadding();
	                	  }
	                  }else if(field.getFieldOption() == FieldOption.CONDITIONAL ){
	                       
	                	  if(conditionalFlag) {
	                        field.enforcePadding();
	                	  }
	                  }else{
	                       
	                        // Ignore
	                  }
	             }
	        }
		}
		
		@Override
		public ByteArrayField get(String fieldName){
			
			ByteArrayField byteArrayField = null;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					byteArrayField = (ByteArrayField)attributeIterator.next();
					
					if(byteArrayField.isKeyPresent(fieldName) ){
						 break;
					}
				}
			}
			
			return byteArrayField;
		}
		
		@Override
		public boolean isValid(){
			
			boolean isValid = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayField byteArrayField = (ByteArrayField)attributeIterator.next();
					
					if(byteArrayField.isValid() ){
						
						isValid = true;
					}else{
						isValid = false;
						 break;
					}
				}
			}
			
			return isValid;
		}
		
		@Override
		public boolean isValidFieldType(){
			
			boolean isValid = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayField byteArrayField = (ByteArrayField)attributeIterator.next();
					
					if(byteArrayField.isValidFieldType() ){
						
						isValid = true;
					}else{
						isValid = false;
						 break;
					}
				}
			}
			
			return isValid;
		}
		
		@Override
		public boolean isKeyPresent(String fieldName){
					
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayField attrib = (ByteArrayField)attributeIterator.next();
					
					if(attrib.isKeyPresent(fieldName) ){
						
						 isPresent = true;
						 break;
					}
				}
			}
			
			return isPresent;
		}

		@Override
		public boolean isValuePresent(String fieldValue){
			
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayField attrib = (ByteArrayField)attributeIterator.next();
					
					if(attrib.isValuePresent(fieldValue) ){
						
						 isPresent = true;
						 break;
					}
				}
			}
			
			return isPresent;
		}
		
		
		/*
		 * Provides a wrapper functionality around pattern matching functionality
		 * of String for region matching in the entire attribute set.
		 */
		public boolean isPatternMatch(int toffset, String other,
	            int ooffset, int len){
			
			boolean isPatternMatch = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayField attrib = (ByteArrayField)attributeIterator.next();
					
					if(attrib.isPatternMatch(toffset, other, ooffset, len)){
						
						isPatternMatch = true;
						 break;
					}
				}
			}
			
			return isPatternMatch;
			
		}
		
		@Override
		public boolean isEmpty() {
			
			if(attributeList != null){
				return attributeList.isEmpty();
			}else{
				return true;
			}
			
			
		}
		
		
		
		@Override
		public int size() {
			
			int length = 0;
			
			if(attributeList != null && !attributeList.isEmpty()){
				
				Iterator<Attribute> iter = this.attributeList.iterator();
				
				while(iter.hasNext()){
					
					length+=((ByteArrayField)iter.next()).getActualLength();
				}
			}
			
			return length;
		}
		
		public String toString(){
			
			return "ByteArrayFieldAttributeSet{"+
					"\n primaryBitMap : "+primaryBitMap+
					"\n secondaryBitMap : "+secondaryBitMap+
					"\n attributeList : "+attributeList+	
					"\n}\n";
		}
		
	}
	
	/**
	 * ByteAttribute represents a model of user defined type where there is 1:1 relationship exists between String key & 
	 * byte value as a byte expression.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class ByteArrayAttribute implements Attribute{

		private String key;
		private byte[] value;
		
		// Attribute defined for marking the length to be read
		private int byteOffset;
		private int byteLength;	
		
		public void set(String key, byte[] value){
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public byte[] getValue() {
			return value;
		}
		public void setValue(byte[] value) {
			this.value = value;
		}
		
		public int getByteOffset() {
			return byteOffset;
		}

		public void setByteOffset(int byteOffset) {
			this.byteOffset = byteOffset;
		}

		public int getByteLength() {
			return byteLength;
		}

		public void setByteLength(int byteLength) {
			this.byteLength = byteLength;
		}
		
		public boolean isKeyPresent(ByteArrayAttribute textAttribute){
			
			boolean isPresent = false;
			
			if(key.equals(textAttribute.getKey())){
				
				isPresent =  true;
			}
			
			return isPresent;
		}
		
		public boolean isValuePresent(ByteArrayAttribute byteAttribute){
			
			boolean isPresent = false;
			
			if(  value.toString().equals(
					byteAttribute.getValue().toString())){
				
				isPresent =  true;
			}
			
			return isPresent;
		}
		
		
		/*
		 * Provides a wrapper functionality around pattern matching functionality
		 * of String for region matching.
		 */
		public boolean isPatternMatch(int toffset, String other,
	            int ooffset, int len){
			
			boolean isPresent = (value.toString()).regionMatches(
					toffset, other, ooffset, len);
			
			return isPresent;
		}
		
		
		public String toString(){
			
			return "ByteAttribute{"+
					" key : "+key+
					" | value : "+value+
					" | byteOffset : "+byteOffset+
					" | byteLength : "+byteLength+
					"\n}\n";
		}
	}
	
	/**
	 * A collection wrapper for ByteAttribute that can form a set of information.
	 * 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class ByteArrayAttributeSet implements AttributeSet{

		private List<Attribute> attributeList = new ArrayList<Attribute>();
		
		@Override
		public List<Attribute> getAttributeList() {
			return this.attributeList;
		}

		@Override
		public void setAttributeList(List<Attribute> attributeList) {
			this.attributeList=attributeList;		
		}
		


		@Override
		public void addAttribute(Attribute dataAttribute) {
			attributeList.add(dataAttribute);
		}
		
		
		public boolean isKeyPresent(ByteArrayAttribute byteAttribute){
					
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayAttribute attrib = (ByteArrayAttribute)attributeIterator.next();
					
					if(attrib.isKeyPresent(byteAttribute) ){
						
						 isPresent = true;
						 break;
					}
				}
			}
			
			return isPresent;
		}

		public boolean isValuePresent(ByteArrayAttribute byteAttribute){
			
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayAttribute attrib = (ByteArrayAttribute)attributeIterator.next();
					
					if(attrib.isValuePresent(byteAttribute) ){
						
						 isPresent = true;
						 break;
					}
				}
			}
			
			return isPresent;
		}
		
		
		/*
		 * Provides a wrapper functionality around pattern matching functionality
		 * of String for region matching in the entire attribute set.
		 */
		public boolean isPatternMatch(int toffset, String other,
	            int ooffset, int len){
			
			boolean isPatternMatch = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					ByteArrayAttribute attrib = (ByteArrayAttribute)attributeIterator.next();
					
					if(attrib.isPatternMatch(toffset, other, ooffset, len)){
						
						isPatternMatch = true;
						 break;
					}
				}
			}
			
			return isPatternMatch;
			
		}
		
		@Override
		public boolean isEmpty() {
			
			if(attributeList != null){
				return attributeList.isEmpty();
			}else{
				return true;
			}
			
			
		}
		
		public String toString(){
			
			return "ByteAttributeSet{"+
					"\n attributeList : "+attributeList+	
					"\n}\n";
		}
		
	}
}
