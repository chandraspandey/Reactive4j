package org.flowr.framework.core.node.io.flow.data.binary.collection;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.node.io.flow.data.DataConstants;

/**
 * 
 * Defines high level collection model of Bit oriented instrumentation as 
 * related to BitMap & BitAttribute.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface BitCollection {

	/**
	 * 
	 * Defines high level behavior of BitMap for presence/absence of data content that follows as part of message.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public interface BitMap {
		
		public enum BitMapType{
			PRIMARY,
			SECONDARY
		}
		
		public void set(int fromIndex, int toIndex, boolean value);
	
		public BitSet get();
		
		public BitMapType getBitMapType();
		
		public int getBitMapSize();
		
	}
	
	/**
	 * Defines the mandatory BitMap that defines the presence of fields in the message. 8 bytes or 64 bit bits are used 
	 * to defines the presence of fields ranging from sequence number 0-63. 
	 * The FirstBit value 1 indicates that there is associated data fields that follows immediately after primary bitmap.
	 * The FirstBit value 0 indicates that there is no associated data fields.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public class PrimaryBitMap implements BitMap {

		/**
		 * The Primary bitmap is modeled as BitSet instance. By default its output size to byte array increments based 
		 * on 1 byte or 8 bits. 
		 */
		private BitSet primaryBitMap 				= new BitSet(DataConstants.PRIMARY_BITMAP_SIZE);
		private boolean isSecondaryBitMapPresent	= false;
		
		/**
		 * The expectedArraySize controls the behavior of byte array output from bitset. If provided with zero creates a
		 * BitSet which can produce an array of 64 bits.If isSecondaryBitMapPresent is marked as true it marks the value
		 * of first bit as 1.
		 */
		public PrimaryBitMap(int expectedArraySize,boolean isSecondaryBitMapPresent){
									
			if(expectedArraySize == 0){				
				
				primaryBitMap = new BitSet(DataConstants.PRIMARY_BITMAP_SIZE);
				
				if(isSecondaryBitMapPresent){
					
					// Mark the secondary bitmap presence to be true
					primaryBitMap.set(0, DataConstants.PRIMARY_BITMAP_SIZE, true);
				}else{
					// Mark the secondary bitmap presence to be false
					primaryBitMap.set(0, DataConstants.PRIMARY_BITMAP_SIZE, false);
				}
					
			}else{
				
				primaryBitMap = new BitSet(expectedArraySize);
				
				if(isSecondaryBitMapPresent){
					
					// Mark the secondary bitmap presence to be true
					primaryBitMap.set(0, DataConstants.PRIMARY_BITMAP_SIZE, true);
				}else{
					// Mark the secondary bitmap presence to be false
					primaryBitMap.set(0, DataConstants.PRIMARY_BITMAP_SIZE, false);
				}
			}			
		}
		
		/**
		 * Ingests byte array and converts into primary bitmap. The length of bytes and set bits determine the length of
		 * expectedArraySize. The index represented by expectedArraySize value is set to true so that byte array is
		 * generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param primaryBitSet
		 * @param expectedArraySize
		 */
		public PrimaryBitMap(byte[] primaryBitSet, int expectedArraySize){
			this.primaryBitMap 	= BitSet.valueOf(primaryBitSet);
			this.primaryBitMap.set(expectedArraySize, true);
		}
		
		/**
		 * Ingests ByteBuffer and converts into primary bitmap. The length of bytes and set bits determine the length of
		 * expectedArraySize. The index represented by expectedArraySize value is set to true so that byte array is
		 * generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param primaryBitSet
		 * @param expectedArraySize
		 */
		public PrimaryBitMap(ByteBuffer primaryByteBuffer, int expectedArraySize){
			this.primaryBitMap 	= BitSet.valueOf(primaryByteBuffer);
			this.primaryBitMap.set(expectedArraySize, true);
		}
		
		
		/**
		 * Ingests byte array and converts into primary bitmap. The length of bytes and set bits determine the length of
		 * primary bitmap.
		 * @param primaryBitSet
		 */
		public PrimaryBitMap(byte[] primaryBitSet){
			this.primaryBitMap 	= BitSet.valueOf(primaryBitSet);
		}
		
		/**
		 * Ingests BitAttributeSet as byte array and converts into primary bitmap. The length of byte array determine 
		 * the length of primary bitmap.
		 * @param primaryBitSet
		 */
		public PrimaryBitMap(BitAttributeSet bitAttributeSet){
			this.primaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
		}
		
		/**
		 * Ingests BitAttributeSet as byte array and converts into primary bitmap. The length of byte array determine  
		 * the length of primary bitmap.The index represented by expectedArraySize value is set to true so that byte 
		 * array is generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param expectedArraySize
		 * @param primaryBitSet
		 */
		public PrimaryBitMap(BitAttributeSet bitAttributeSet,int expectedArraySize){
			this.primaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
			this.primaryBitMap.set(expectedArraySize, true);
		}
		
		/**
		 * Ingests Boolean constants of TRUE,FALSE as easy to use representation for setting the bits.
		 * @param bits
		 */
		public PrimaryBitMap(Boolean... bits){
			
			if(bits != null && bits.length > 0) {
				
				BitAttributeSet bitAttributeSet = new BitFieldSet();
				
				for(int index = 0; index < bits.length;index++) {
					
					if(bits[index] == Boolean.TRUE) {
						bitAttributeSet.addAttribute(new BitField(index,Boolean.TRUE,0));
					}else {
						bitAttributeSet.addAttribute(new BitField(index,Boolean.FALSE,0));
					}
					
				}		
				
				this.primaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
			}
		}
		
		/**
		 * Initializes the BitMap range with value.
		 * @param fromIndex
		 * @param toIndex
		 * @param value
		 */
		public void set(int fromIndex, int toIndex, boolean value){		
			this.primaryBitMap.set(fromIndex, toIndex, value);
		}
		
		public BitSet get(){		
			return this.primaryBitMap;
		}
		
		public boolean isSecondaryBitMapPresent(){
			
			return this.isSecondaryBitMapPresent;
		}
		
		public void setSecondaryBitMapPresent(boolean isSecondaryBitMapPresent){
			
			this.isSecondaryBitMapPresent = isSecondaryBitMapPresent;
		}
		
		public BitMapType getBitMapType() {
			return BitMapType.PRIMARY;
		}
		
		public int getBitMapSize() {
									
			return primaryBitMap.toByteArray().length;
		}
		
		
		public String toString(){		
			return "\n PrimaryBitMap { "+BitMapType.PRIMARY+" | isSecondaryBitMapPresent : "+isSecondaryBitMapPresent+
					" | "+Arrays.toString(primaryBitMap.toByteArray())
					+" }";
		}	
	}
	
	/**
	 * Defines the optional BitMap that defines the presence of fields in the message. 8 bytes or 64 bit bits are used 
	 * to define the presence of fields ranging from sequence number 64-127. 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class SecondaryBitMap implements BitMap{

		/**
		 * The Secondary bitmap is modeled as BitSet instance. By default its output size to bytearray increments based 
		 * on 1 bytes or 8 bits. 
		 */
		private BitSet secondaryBitMap 				= new BitSet(DataConstants.SECONDARY_BITMAP_SIZE);	
		
		/**
		 * The expectedArraySize controls the behavior of byte array output from bitset. If provided with zero creates a
		 * BitSet which can produce an array of 64 bits.
		 */
		public SecondaryBitMap(int expectedArraySize){
									
			if(expectedArraySize == 0){							
				secondaryBitMap = new BitSet(DataConstants.SECONDARY_BITMAP_SIZE);								
			}else{				
				secondaryBitMap = new BitSet(expectedArraySize);
			}			
		}
		
		/**
		 * Ingests byte array and converts into secondary bitmap. The length of bytes and set bits determine the length 
		 * of expectedArraySize. The index represented by expectedArraySize value is set to true so that byte array is
		 * generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param secondaryaryBitSet
		 * @param expectedArraySize
		 */
		public SecondaryBitMap(byte[] secondaryaryBitSet, int expectedArraySize){
			this.secondaryBitMap 	= BitSet.valueOf(secondaryaryBitSet);
			this.secondaryBitMap.set(expectedArraySize, true);
		}
		
		/**
		 * Ingests byte array and converts into secondary bitmap. The length of bytes and set bits determine the length 
		 * of expectedArraySize. The index represented by expectedArraySize value is set to true so that byte array is
		 * generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param secondaryaryByteBuffer
		 * @param expectedArraySize
		 */
		public SecondaryBitMap(ByteBuffer secondaryaryByteBuffer, int expectedArraySize){
			this.secondaryBitMap 	= BitSet.valueOf(secondaryaryByteBuffer);
			this.secondaryBitMap.set(expectedArraySize, true);
		}
		
		
		/**
		 * Ingests byte array and converts into primary bitmap. The length of bytes and set bits determine the length of
		 * primary bitmap.
		 * @param secondaryaryBitSet
		 */
		public SecondaryBitMap(byte[] secondaryaryBitSet){
			this.secondaryBitMap 	= BitSet.valueOf(secondaryaryBitSet);
		}
		
		/**
		 * Ingests BitAttributeSet as byte array and converts into primary bitmap. The length of byte array determine  
		 * the length of primary bitmap.
		 * @param primaryBitSet
		 */
		public SecondaryBitMap(BitAttributeSet bitAttributeSet){
			this.secondaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
		}
		
		
		/**
		 * Ingests BitAttributeSet as byte array and converts into secondary bitmap. The length of byte array determine  
		 * the length of secondary bitmap.The index represented by expectedArraySize value is set to true so that byte 
		 * array is generated of fix size. By default its output size to bytearray increments based on 1 bytes or 8 bits.  
		 * The calling program should take care that this index is not used internally by downstream 
		 * usage. For 16 bytes BitMap it is suggested to use 127(Control Character) as index 
		 * @param primaryBitSet
		 * @param expectedArraySize
		 */
		public SecondaryBitMap(BitAttributeSet bitAttributeSet, int expectedArraySize){
			this.secondaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
			this.secondaryBitMap.set(expectedArraySize, true);
		}
		
		/**
		 * Ingests Boolean constants of TRUE,FALSE as easy to use representation for setting the bits.
		 * @param bits
		 */
		public SecondaryBitMap(Boolean... bits){
			
			if(bits != null && bits.length > 0) {
				
				BitAttributeSet bitAttributeSet = new BitFieldSet();
				
				for(int index = 0; index < bits.length;index++) {
					
					if(bits[index] == Boolean.TRUE) {
						bitAttributeSet.addAttribute(new BitField(index,Boolean.TRUE,0));
					}else {
						bitAttributeSet.addAttribute(new BitField(index,Boolean.FALSE,0));
					}
					
				}		
				
				this.secondaryBitMap 	= BitSet.valueOf(bitAttributeSet.asBitArray());
			}
		}
		
		/**
		 * Initializes the BitMap range with value.
		 * @param fromIndex
		 * @param toIndex
		 * @param value
		 */
		public void set(int fromIndex, int toIndex, boolean value){		
			this.secondaryBitMap.set(fromIndex, toIndex, value);
		}
		
		public BitSet get(){		
			return this.secondaryBitMap;
		}
		
		public BitMapType getBitMapType() {
			return BitMapType.SECONDARY;
		}
		
		public int getBitMapSize() {
			
			return this.secondaryBitMap.toByteArray().length;
		}
			
		
		
		public String toString(){		
			return "\n SecondaryBitMap { "+BitMapType.SECONDARY+" | "+Arrays.toString(secondaryBitMap.toByteArray())+" }";
		}	
	}
	
	/**
	 * Defines basic bit with toggle values of 0 or 1. 
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public class Bit implements Attribute{

		// Can take only the value as 0 0r 1. Defaults to clear i.e. 0
		private  int set = 0;	
		
		// Maps the index of bit in a sequence 
		private  int bitIndex = 0;	
		
		public  void set(boolean flag){
			
			if(flag) {
				set = 1;
			}else {
				set = 0;
			}			
		}
		
		public  void clear(){
			set	= 0;
		}
		
		public  int get(){
			return set;
		}
		
		public  int getBitIndex() {
			return bitIndex;
		}

		public  void setBitIndex(int bIndex) {
			bitIndex = bIndex;
		}
		
		public  String toString(){		
			return "| bitIndex : "+bitIndex+" | set : "+set+"|";
		}
	}

	/**
	 * Extends a Bit to provide linked downstream instrumentation of a field value association.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public final class BitField extends Bit{

		// Generic Type to store any numeric Java Type
		private Number number = 0;
		
		public BitField(int bitIndex, boolean flag,Number num){
			set(flag);
			setBitIndex(bitIndex);
			number = num;
		}
		
		public BitField(int bitIndex,Number num){
			set(true);
			setBitIndex(bitIndex);
			number = num;
		}
		
		public static BitField valueOf(Bit bit){
			
			BitField field = new BitField(bit.getBitIndex(),null);		
					
			return field;
		}
		
		public static BitField valueOf(Bit bit, Number num){
			
			BitField field = new BitField(bit.getBitIndex(),num);		
					
			return field;
		}
		
		public static BitField valueOf(int bitIndex, Number num){
			
			BitField field = new BitField(bitIndex,num);		
					
			return field;
		}
		
		public static BitField valueOf(int bitIndex){
			
			BitField field = new BitField(bitIndex,null);		
					
			return field;
		}

		
		public String literalValue(){
			
			BigInteger b = new BigInteger(String.valueOf(number));
			 System.out.println(b.toString(2) + " " + number);
			return b.toString(2);
		}
		
		
		public Number getNumericValue(){
			
			return number;
		}
		
		public void setNumericValue(Number num){
			
			number = num;
		}
		
		public String toString(){		
			return "\n BitField { number : "+number+" | "+super.toString()+" }";
		}	
	}
	
	/**
	 *  Provides additional instrumentation required for supporting type as BitField.
	 * @author Chandra Pandey
	 *
	 */

	public interface BitAttributeSet extends AttributeSet{

		public BitField getByBitIndex(int bitIndex);
		
		public BitField getByBitValue(int bitValue);
		
		public boolean isBitIndexPresent(int bitIndex);
		
		public boolean isBitValuePresent(int bitValue);
		
		public byte[] asBitArray();

		public BitSet asBitSet();
		
		public boolean isSecondaryMapPresent() ;

		public void setSecondaryMapPresent(boolean isSecondaryMapPresent);
	}

	public class BitFieldSet implements BitAttributeSet{
		
		private List<Attribute> attributeList 	= new ArrayList<Attribute>();
		private boolean isSecondaryMapPresent 	= false;
		
		public BitFieldSet() {
			
		}
		
		public BitFieldSet(Boolean... bits){
			
			if(bits != null && bits.length > 0) {
				
				for(int index = 0; index < bits.length;index++) {
					
					if(bits[index] == Boolean.TRUE) {
						this.attributeList.add(new BitField(index,Boolean.TRUE,0));
					}else {
						this.attributeList.add(new BitField(index,Boolean.FALSE,0));
					}
					
				}				
			}
		}
		
		@Override
		public void addAttribute(Attribute attribute) {
			attributeList.add(attribute);
		}
		
		@Override
		public byte[] asBitArray(){
			
			byte[] arr = null;
			
			BitField bitField = null;
			
			if(!attributeList.isEmpty()){
				
				arr = new byte[attributeList.size()];
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				for(int index =0;attributeIterator.hasNext();index++){
					
					bitField = (BitField)attributeIterator.next();
					
					arr[index] = (byte) bitField.getBitIndex();
				}
			}
			
			return arr;
		}
		
		@Override
		public BitSet asBitSet(){
			
			BitSet bitSet = new BitSet();
		
			BitField bitField = null;
			
			if(!attributeList.isEmpty()){
							
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					bitField = (BitField)attributeIterator.next();
					
					bitSet.set(bitField.getBitIndex());
				}
			}
			
			return bitSet;
		}
		
		@Override
		public BitField getByBitIndex(int bitIndex){
			
			BitField bitField = null;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					bitField = (BitField)attributeIterator.next();
					
					if(bitField.getBitIndex() == bitIndex){
						 break;
					}
				}
			}
			
			return bitField;
		}
		
		@Override
		public BitField getByBitValue(int bitValue){
			
			BitField bitField = null;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					bitField = (BitField)attributeIterator.next();
					
					if(bitField.getNumericValue().intValue() == bitValue){
						 break;
					}
				}
			}
			
			return bitField;
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
		public boolean isBitIndexPresent(int bitIndex){
					
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					BitField attrib = (BitField)attributeIterator.next();
					
					if(attrib.getBitIndex() == bitIndex ){
						
						 isPresent = true;
						 break;
					}
				}
			}		
			return isPresent;
		}

		@Override
		public boolean isBitValuePresent(int bitValue){
			
			boolean isPresent = false;
			
			if(!attributeList.isEmpty()){
				
				Iterator<Attribute> attributeIterator = attributeList.iterator();
				
				while(attributeIterator.hasNext()){
					
					BitField attrib = (BitField)attributeIterator.next();
					
					if(attrib.getNumericValue().intValue() == bitValue){
						
						 isPresent = true;
						 break;
					}
				}
			}
			
			return isPresent;
		}
		
		@Override
		public List<Attribute> getAttributeList() {
			return this.attributeList;
		}

		@Override
		public void setAttributeList(List<Attribute> attributeList) {
			this.attributeList = attributeList;
		}
		
		@Override
		public boolean isSecondaryMapPresent() {
			return isSecondaryMapPresent;
		}

		@Override
		public void setSecondaryMapPresent(boolean isSecondaryMapPresent) {
			this.isSecondaryMapPresent = isSecondaryMapPresent;
		}
		
		public String toString(){		
			return "BitFieldSet { isSecondaryMapPresent : "+isSecondaryMapPresent +" | attributeList : "+attributeList+
					 "}\n";
		}
	}	
	
	/**
	 * Provides octet representation of BitField for easy instrumentation.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */

	public class ByteField{

		private int OCTET = 8;
		private BitField[] bitFieldOctet = new BitField[OCTET];
		
		public ByteField valueOf(BitField[] bitFieldOctet) throws DataAccessException{
			
			if(bitFieldOctet != null && bitFieldOctet.length == OCTET){
				
				this.bitFieldOctet = bitFieldOctet;
			}else{
				
				DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
						ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
						"Invalid input length. ByteField accepts only 8 BitField. Input provided : "+
								Arrays.toString(bitFieldOctet)
				);
				throw exception;
			}
			
			return this;
		}
		
		public ByteField valueOf(Bit[] bitOctet) throws DataAccessException{
			
			ByteField byteField;
			
			if(bitOctet != null && bitOctet.length == OCTET){
				
				byteField = new ByteField();
				
				BitField[] bitFieldOctet = new BitField[OCTET];
				
				for(int bitIndex = 0; bitIndex < bitOctet.length; bitIndex++){
									
					bitFieldOctet[bitIndex] = BitField.valueOf(bitOctet[bitIndex]);
				}
				
				byteField =  byteField.valueOf(bitFieldOctet);
				
			}else{
				
				DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
						ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
						"Invalid input length. ByteField accepts only 8 BitField. Input provided : "+bitFieldOctet
				);
				throw exception;
			}
			
			return byteField;
		}
		
		public long value(){
			
			long val = 0;
			
			if(bitFieldOctet != null && bitFieldOctet.length > 0){
			
				for(int bitIndex = 0; bitIndex < bitFieldOctet.length; bitIndex++){
					
					val += bitFieldOctet[bitIndex].getNumericValue().longValue();
				}
			}
			
			return val;
		}
		
		public String[] toStringArray(){
			
			String[] literalList = null;
			
			if(bitFieldOctet != null && bitFieldOctet.length > 0){
				
				literalList = new String[OCTET];
				
				for(int bitIndex = 0; bitIndex < bitFieldOctet.length; bitIndex++){
					
					literalList[bitIndex] = bitFieldOctet[bitIndex].literalValue();
				}
			}
			return literalList;
		}
	}
}
