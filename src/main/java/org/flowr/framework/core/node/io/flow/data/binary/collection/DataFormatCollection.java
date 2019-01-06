package org.flowr.framework.core.node.io.flow.data.binary.collection;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.node.io.flow.data.DataConstants;
import org.flowr.framework.core.node.io.flow.data.DataConstants.FormatTokenizer;
import org.flowr.framework.core.node.io.flow.data.DataConstants.FormatType;
import org.flowr.framework.core.node.io.flow.data.DataFormat.BinaryFormat;
import org.flowr.framework.core.node.io.flow.data.DataFormat.FormatSequenceType;

/**
 * Provides Data Format related classes & Utilities for various use cases.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface DataFormatCollection {
	
	 /**
	   * Converts byte array to short. Effective for byte aray of less than 4 byte length	
	   * @param arr
	   * @return
	   */
	  public static short convertByteArrayToByte(byte[] arr) throws DataAccessException{
	        
		  
	        return arr[0];
	  }	
		
	  /**
	   * Converts byte array to short. Effective for byte aray of less than 4 byte length	
	   * @param arr
	   * @return
	   */
	  public static short convertByteArrayToShort(byte[] arr) throws DataAccessException{
	        
		  
	        return ByteBuffer.wrap(arr).getShort();
	  }
	 
	  /**
	   * Effective for encoding length into byte array which can be decoded into short of less than 4 byte array i.e 2 byte
	   * @param len
	   * @return
	   */
	  public static byte[] convertShortToByteArray(short len) throws DataAccessException{
	                   
	        return ByteBuffer.allocate(2).putShort(len).array();
	  }
	  
	  /**
	   * Converts byte array to short. Effective for byte aray of greater than 4 byte length	
	   * @param arr
	   * @return
	   */
	  public static int convertByteArrayToInt(byte[] arr) throws DataAccessException{
	        
	        return ByteBuffer.wrap(arr).getInt();
	  }
	 
	  /**
	   * Effective for encoding length into byte array which can be decoded into greater than 4 byte array
	   * @param len
	   * @return
	   */
	  public static byte[] convertIntToByteArray(short len){
	                   
	        return ByteBuffer.allocate(4).putShort(len).array();
	  }

	/**
	 * Creates a Binary data sequence with NUL(byte 0) as pre sequence token
	 * @param data
	 * @return
	 */
	public static DataSequence DefaultPreDataSequence(byte[] data) {
		
		DataSequence dataSequence = new DataSequence(FormatType.BINARY,  FormatSequenceType.PRESEQUENCE, 
				FormatTokenizer.NUL,  data);
		
		return dataSequence;		
	}
	
	/**
	 * Creates a Binary data sequence with NUL(byte 0) as sequence token
	 * @param data
	 * @return
	 */
	public static DataSequence DefaultDataSequence(byte[] data) {
		
		DataSequence dataSequence = new DataSequence(FormatType.BINARY,  FormatSequenceType.DATA, 
				FormatTokenizer.NUL,  data);
		
		return dataSequence;		
	}
	
	/**
	 * Creates a Binary data sequence with NUL(byte 0) as post sequence token
	 * @param data
	 * @return
	 */
	public static DataSequence DefaultPostDataSequence(byte[] data) {
		
		DataSequence dataSequence = new DataSequence(FormatType.BINARY,  FormatSequenceType.POSTSEQUENCE, 
				FormatTokenizer.NUL,  data);
		
		return dataSequence;		
	}
	
	/**
	 * Returns byte array as prefix sequence for provided input parameters.
	 * @param formatType
	 * @param prefixSequenceType
	 * @param data
	 * @return
	 * @throws DataAccessException
	 */
	public static byte[] buildPrefixSequence(FormatType formatType, FormatTokenizer prefixSequenceType, byte[] data) 
		throws DataAccessException{
		
		byte[] sequence = null;
		
		if(data != null) {
		
			sequence = new byte[1+data.length];
			
			int sequenceCounter = 0;
			
			byte[] preSequenceData = new byte[BinaryFormat.getBinary(prefixSequenceType)];
			
			System.arraycopy(preSequenceData, 0, sequence, sequenceCounter, 1 );
			sequenceCounter= sequenceCounter+1;
			System.arraycopy(data, 0, sequence,sequenceCounter, data.length );
			sequenceCounter= sequenceCounter+ data.length;
		}else {
			
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
		return sequence;
	}
	
	/**
	 * Returns byte array as prefix sequence for provided input parameters.
	 * @param formatType
	 * @param prefixSequenceType
	 * @param data
	 * @return
	 * @throws DataAccessException
	 */
	public static byte[] buildPrefixSequence(FormatType formatType,FormatTokenizer[] prefixSequenceType, byte[] data) 
		throws DataAccessException{
		
		byte[] sequence = null;
		
		if(data != null) {
		
			sequence = new byte[prefixSequenceType.length+data.length];
			int sequenceCounter = 0;
			
			byte[] preSequenceData = BinaryFormat.getBinary(prefixSequenceType);
			
			System.arraycopy(preSequenceData, 0, sequence, sequenceCounter, preSequenceData.length );
			sequenceCounter= sequenceCounter+preSequenceData.length;
			
			System.arraycopy(data, 0, sequence,sequenceCounter, data.length );
			sequenceCounter= sequenceCounter+data.length;
		}else {
			
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
		
		return sequence;
	}
	
	/**
	 * Returns byte array as suffix sequence for provided input parameters.
	 * @param formatType
	 * @param prefixSequenceType
	 * @param data
	 * @return
	 * @throws DataAccessException
	 */
	public static byte[] buildSuffixSequence(FormatType formatType, FormatTokenizer suffixSequenceType,byte[] data) 
		throws DataAccessException{
			
		byte[] sequence = null;
		
		if(data != null) {
		
			sequence = new byte[data.length+1];
			
			int sequenceCounter = 0;
			
			byte[] sufSequenceData = new byte[BinaryFormat.getBinary(suffixSequenceType)];
			
			System.arraycopy(data, 0, sequence,sequenceCounter, data.length );
			sequenceCounter= sequenceCounter+data.length;
			System.arraycopy(sufSequenceData, 0, sequence, sequenceCounter,1 );
			sequenceCounter= sequenceCounter+1;
		}else {
			
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
			
		return sequence;
	}
	
	/**
	 * Returns byte array as suffix sequence for provided input parameters.
	 * @param formatType
	 * @param prefixSequenceType
	 * @param data
	 * @return
	 * @throws DataAccessException
	 */
	public static byte[] buildSuffixSequence(FormatType formatType, FormatTokenizer[] suffixSequenceType,byte[] data) 
		throws DataAccessException{
		
		byte[] sequence = null;
		
		if(data != null) {
			
			sequence = new byte[data.length+suffixSequenceType.length];
		
			int sequenceCounter = 0;
			
			byte[] sufSequenceData = BinaryFormat.getBinary(suffixSequenceType);
			
			System.arraycopy(data, 0, sequence, sequenceCounter,data.length );
			
			sequenceCounter= sequenceCounter+data.length;
			System.arraycopy(sufSequenceData, 0, sequence, sequenceCounter, sufSequenceData.length );
			sequenceCounter= sequenceCounter+sufSequenceData.length;
		}else {
			
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
		
		return sequence;
	}
	
	/**
	 * Builds the Format of type : FORMAT< CONTROL +  DATA + CONTROL >
	 * Total Bytes used = 2 + data.length 
	 * @param data
	 * @return
	 * @throws DataAccessException 
	 */
	public static byte[] DefaultNetworkFormatSequence(byte[] data) throws DataAccessException{
		
		byte[] sequence = null; 
		
		if(	data != null && data.length > 0 ){
			
			// 1 byte each for Start control character, space & end of ,  & additional data.length for spacing
			int bytecount = 2+ data.length;
			
			sequence = new byte[bytecount];
			
			int sequenceIndex =0;
			
			// Mark the start of Control Character
			sequence[sequenceIndex++] = 127;

			
			System.out.println("data.length : "+data.length+" | sequence.length :"+sequence.length);
			
			for(int index = 0; index < data.length; index++){
				
				sequence[sequenceIndex++] = data[index];
			}
			
			// Mark the end of Control Character
			sequence[sequenceIndex++] = 127;			
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
				
		return sequence;
	}	
	
	/**
	 * Builds the Format of type : FORMAT< FormatSequenceType + DATA + FormatSequenceType >
	 * Total Bytes used = 2 + data.length 
	 * @param prefixSequenceType
	 * @param prefixFormatTokenizer
	 * @param data
	 * @param suffixSequenceType
	 * @param suffixFormatTokenizer
	 * @return
	 * @throws DataAccessException 
	 */
	public static byte[] buildNetworkFormatSequence(
		FormatSequenceType prefixSequenceType, FormatTokenizer prefixFormatTokenizer, 
		byte[] data, 
		FormatSequenceType suffixSequenceType, FormatTokenizer suffixFormatTokenizer) throws DataAccessException{
		
		byte[] sequence = null; 
		
		if(
				prefixSequenceType != null && 
				suffixSequenceType != null && 
				data != null && data.length > 0
		){
			
			// 1 byte each for Start control character, space & end of ,  & additional data.length for spacing
			int bytecount = 2+ data.length;
			
			sequence = new byte[bytecount];
			
			int sequenceIndex =0;
			
			// Mark the start of Control Character
			sequence[sequenceIndex++] = BinaryFormat.getBinary(prefixFormatTokenizer);

			
			System.out.println("data.length : "+data.length+" | sequence.length :"+sequence.length);
			
			for(int index = 0; index < data.length; index++){
				
				sequence[sequenceIndex++] = data[index];
			}
			
			// Mark the end of Control Character
			sequence[sequenceIndex++] = BinaryFormat.getBinary(suffixFormatTokenizer);			
		}else {
			DataAccessException exception = new DataAccessException(
					ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
					ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
					"Invalid input length. data : "+data
			);
			throw exception;
		}
				
		return sequence;
	}
	
	/**
	 * 
	 * Marker interface for sequencing in Data Format.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 */
	public interface FormatSequence {
		
		public default FormatTokenizer defaultTokenizer() {
			return FormatTokenizer.NUL;
		}		
	}
	
	/**
	 * 
	 * Default implementation of FormatSequence.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public class DataSequence implements FormatSequence{
		
		private FormatType formatType 					= FormatType.BINARY;
		private FormatSequenceType formatSequenceType	= FormatSequenceType.DATA;
		private FormatTokenizer formatTokenizer			= FormatTokenizer.NUL;
		private byte[] data;
		
		public DataSequence(FormatType formatType,  FormatSequenceType formatSequenceType, FormatTokenizer 
			formatTokenizer, byte[] data) {
			
			this.formatType 		= formatType;
			this.formatSequenceType = formatSequenceType;
			this.formatTokenizer	= formatTokenizer;
			this.data				= data;
		}
		
		public FormatType getFormatType() {
			return formatType;
		}

		public FormatSequenceType getFormatSequenceType() {
			return formatSequenceType;
		}

		public FormatTokenizer getFormatTokenizer() {
			return formatTokenizer;
		}

		public byte[] getData() throws DataAccessException {
			
			byte[] sequence = null;
			
			int sequenceCounter = 0;
			
			byte[] sequenceData = new byte[] {BinaryFormat.getBinary(formatTokenizer)};
			
			if( data != null) {
			
				if(formatSequenceType == FormatSequenceType.PRESEQUENCE ) {
					
					sequence = new byte[sequenceData.length+data.length];
					System.arraycopy(sequenceData, 0, sequence, sequenceCounter, sequenceData.length );
					sequenceCounter=sequenceCounter+sequenceData.length;
					System.arraycopy(data, 0, sequence,sequenceCounter, data.length );
					sequenceCounter=sequenceCounter+data.length;
					
				}else if(formatSequenceType == FormatSequenceType.POSTSEQUENCE ) {
					
					sequence = new byte[sequenceData.length+data.length];
					System.arraycopy(data, 0, sequence, sequenceCounter, data.length );
					sequenceCounter=sequenceCounter+data.length;
					System.arraycopy(sequenceData, 0, sequence, sequenceCounter, sequenceData.length );
					sequenceCounter=sequenceCounter+sequenceData.length;
				}else if(formatSequenceType == FormatSequenceType.DATA ) {
					
					sequence = data;
				}
			
			}else {
				DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
						ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
						"Invalid input length. data : "+data
				);
				throw exception;
			}			
			
			return sequence;
		}
		
		public String toString(){
			
			return "DataSequence { "+
					formatType+	
					" | "+formatSequenceType+	
					" | "+formatTokenizer+
					" | "+Arrays.toString(data)+	
					" }\n";
		}

	}

	/**
	 * Provides default implementation of FormatSequence for a DataField with optional Prefix & Suffix sequence option.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public class DataFieldFormatSequence implements FormatSequence{

		private Optional<DataSequence> prefixFormatSequence;
		private DataSequence dataSequence; 
		private Optional<DataSequence> suffixFormatSequence;
			
		public DataFieldFormatSequence(DataSequence dataSequence) {
			
			this.dataSequence = dataSequence;
		}
		
		public DataFieldFormatSequence(Optional<DataSequence> prefixFormatSequence,DataSequence dataSequence, 
			Optional<DataSequence> suffixFormatSequence ) {
			
			this.prefixFormatSequence 	= prefixFormatSequence;
			this.dataSequence			= dataSequence;
			this.suffixFormatSequence	= suffixFormatSequence;
		}
		

		public byte[] getFormatSequenceData() throws DataAccessException {
					
			byte[] prefixSequence 	= new byte[] {};
			byte[] data			 	= dataSequence.getData();
			byte[] suffixSequence 	= new byte[] {};
			
			if(prefixFormatSequence.isPresent()) {
				
				prefixSequence = prefixFormatSequence.get().getData();			
			}		
				
			if(suffixFormatSequence.isPresent()) {
				
				suffixSequence = suffixFormatSequence.get().getData();
			}
			
			byte[] sequence = new byte[prefixSequence.length+data.length+suffixSequence.length];
			
			int sequenceCounter = 0;
			
			if(prefixFormatSequence.isPresent()) {
				
				System.arraycopy(prefixSequence, 0, sequence, sequenceCounter, prefixSequence.length );
				sequenceCounter=sequenceCounter+prefixSequence.length;
			}
			
			if(dataSequence != null) {
				
				System.arraycopy(data, 0, sequence,sequenceCounter, data.length );			
				sequenceCounter=sequenceCounter+data.length;
			}else {
				DataAccessException exception = new DataAccessException(
						ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
						ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
						"Invalid input length. data : "+data
				);
				throw exception;
			}		
			
			if(suffixFormatSequence.isPresent()) {
				
				System.arraycopy(suffixSequence, 0, sequence, sequenceCounter, suffixSequence.length );			
				sequenceCounter=sequenceCounter+suffixSequence.length;
			}
			
			return sequence;
		}


		public String toString(){
			
			StringBuffer rtn = new StringBuffer();
			
			rtn.append("ByteFormatSequence {");
			
			prefixFormatSequence.ifPresent(
				(s) ->{
					rtn.append("\n"+prefixFormatSequence.get().toString());
				}
			);		
			
			rtn.append(dataSequence.toString());
			
			suffixFormatSequence.ifPresent(
				(s) ->{
					rtn.append(suffixFormatSequence.get().toString());
				}
			);
			
			rtn.append("}\n");
			
			return rtn.toString();
		}	
	}

	/**
	 * Defines Cursor as a navigable index for list of records.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public interface Cursor {

		public enum CursorState{
			OPEN,
			PROCESSING,
			CLOSE
		}
		
		
		/**
		 * SINGELTON locks/synchronizes the read while cursor is open &
		 * operating
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public enum CursorType{
			SINGELTON,
			MULTIACCESS
		}
		
		/**
		 * Defines ByteCursor with byte array as input provides utility  for read, add, remove, format, mask,
		 * and replace functionalities.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */

		public final class ByteCursor implements Cursor{

			private CursorState cursorState = CursorState.CLOSE;
			private byte[] inBuffer;
			private int cursorPosition;
			
			
			/**
			 * Provides bye array loading the input for traversal and marks the CursorState as OPEN.
			 * @param inBuffer
			 * @return
			 * @throws DataAccessException
			 */
			public CursorState open(byte[] inBuffer) throws DataAccessException{
				
				if(inBuffer != null && inBuffer.length > 0){			
					this.inBuffer 	= inBuffer;				
				}else{
					reset();
					throw new DataAccessException(
						101,"Invalid cursor position.",
						"Invalid input or input length. Input : "+ Arrays.toString(inBuffer));
				}
				
				cursorState = CursorState.OPEN;
				
				return cursorState;
			}
			
			/**
			 * Reads from current cursor position and returns the byte array as defined by the length as input.
			 * @param length
			 * @return
			 */
			public byte[] read(int length){
				
				byte[] out = new byte[length];
				
				cursorState = CursorState.PROCESSING;
				
				System.arraycopy(inBuffer, cursorPosition, out, 0, length);
				cursorPosition += length;
				
				cursorState = CursorState.OPEN;
				
				return out;
			}
			
			/**
			 * Returns the current buffer used as input.
			 * @return
			 */
			public byte[] getInBuffer(){
				
				return this.inBuffer;
			}
			
			/**
			 * Resets the cursor position to start
			 */
			public void reset(){
				cursorPosition = 0;
				cursorState = CursorState.CLOSE;
			}
			
			/**
			 * Formats the int array to byte literal array
			 * @param in
			 * @return
			 * @throws DataAccessException
			 */
			public String[] formatToLiteral(int[] in) throws DataAccessException{
				
				String[] out = null;
				
				if(in != null && in.length > 0){
					
					cursorState = CursorState.PROCESSING;
					
					out = BinaryFormat.binaryLiteralToStringArray(in);
				}else{
					throw new DataAccessException(
							102,"Invalid index or length of data input.",
							"Cursor can not proceed."
					);
				}
					
				cursorState = CursorState.OPEN;
				
				return out;		
			}
			
			/**
			 * Formats the byte array to number
			 * @param in
			 * @return
			 * @throws DataAccessException
			 */
			public int formatToNumber(byte[] in) throws DataAccessException{
				
				int val = 0;
				
				if(in != null && in.length > 0){
					
					cursorState = CursorState.PROCESSING;
					
					if(in.length < 2){
					
						val = convertByteArrayToByte(in);				
					}else if(in.length >= 2 && in.length < 4){

						val = convertByteArrayToShort(in);				
					}else{
						val = convertByteArrayToInt(in);
					}			
					cursorState = CursorState.OPEN;
				}else{
					throw new DataAccessException(
							102,"Invalid index or length of dat input.",
							"Cursor can not proceed."
					);
				}		
				return val;		
			}
			
			/**
			 * Formats the input buffer with header & trailer
			 * @param header
			 * @param dataLength
			 * @param trailer
			 * @return
			 * @throws DataAccessException
			 */
			public byte[] format(byte[] header,byte[] dataLength, byte[] trailer) throws
				DataAccessException{
				
				byte[] outBuffer = null;
				
				if(
						header != null && header.length > 0 
						&& dataLength != null && dataLength.length > 0 
						&& inBuffer != null && inBuffer.length > 0 
						&& trailer != null && trailer.length > 0
				){
				
					outBuffer = new byte[header.length+dataLength.length+inBuffer.length+trailer.length];
					
					int cursor = 0;
					
					cursorState = CursorState.PROCESSING;
					
					System.arraycopy(header, 0, outBuffer, cursor, header.length);
					cursor+=header.length;
					System.arraycopy(dataLength, 0, outBuffer, cursor, dataLength.length);
					cursor+=dataLength.length;
					System.arraycopy(inBuffer, 0, outBuffer, cursor, inBuffer.length);
					cursor+=inBuffer.length;
					System.arraycopy(trailer, 0, outBuffer, cursor, trailer.length);
					cursor+=trailer.length;
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							101,"Invalid cursor position.",
							"Invalid inputs for formatting."
							+ "\n header : "+ Arrays.toString(header)
							+ "\n dataLength : "+ Arrays.toString(dataLength)
							+ "\n inBuffer : "+ Arrays.toString(inBuffer)
							+ "\n trailer : "+ Arrays.toString(trailer)
					);
				}
				return outBuffer;
				
			}
				
			/**
			 * Verifies if the pattern matches with input buffer.
			 * @param pattern
			 * @return
			 * @throws DataAccessException
			 */
			public boolean formatMatches(byte[] pattern) throws DataAccessException{
				
				boolean isPresent = false;
				
				if(pattern != null && pattern.length > 0 ){
					
					cursorState = CursorState.PROCESSING;
					
					String original = new String(inBuffer,DataConstants.CHARSET);
					
					String regex = new String(pattern,DataConstants.CHARSET);
					
					isPresent = original.matches(regex);
					
					System.out.println("isPresent : "+isPresent);
					System.out.println("original : "+original+" | "+ Arrays.toString(inBuffer));
					System.out.println("regex : "+regex+" | "+ Arrays.toString(pattern));
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							102,"Invalid pattern or length of data input.",
							"Cursor can not proceed."
					);
				}
				
				cursorState = CursorState.OPEN;
				
				return isPresent;
				
			}
			
			/**
			 * Returns the first index of occurrence of a pattern.
			 * @param pattern
			 * @return
			 * @throws DataAccessException
			 */
			public int indexOf(byte[] pattern) throws DataAccessException{
				
				int index = -1;
				
				if(pattern != null && pattern.length > 0 ){
					
					cursorState = CursorState.PROCESSING;
					
					String original = new String(inBuffer,DataConstants.CHARSET);
					
					String regex = new String(pattern,DataConstants.CHARSET);
					
					index = original.indexOf(regex);
					
					System.out.println("index : "+index);
					System.out.println("original : "+original+" | "+ Arrays.toString(inBuffer));
					System.out.println("regex : "+regex+" | "+ Arrays.toString(pattern));
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							102,"Invalid pattern or length of data input.",
							"Cursor can not proceed."
					);
				}
				
				cursorState = CursorState.OPEN;
				
				return index;		
			}
			
			/**
			 * Verifies if the pattern matches with input buffer.
			 * @param pattern
			 * @param ignoreStart
			 * @param ignoreEnd
			 * @return
			 * @throws DataAccessException
			 */
			public boolean regionMatches(byte[] pattern, int ignoreStart, int ignoreEnd) throws DataAccessException{
				
				boolean isPresent = false;
				
				if(pattern != null && pattern.length > 0 ){
					
					cursorState = CursorState.PROCESSING;
					
					byte[] changeBuffer = new byte[inBuffer.length-(ignoreStart+ignoreEnd)];
					
					int startIndex 	= 0;
					int endIndex 	= 0;
					
					if(	ignoreStart >= 0 && ignoreStart < inBuffer.length ){				
						startIndex 	= ignoreStart;
					}else{
						startIndex 	= 0;
					}
					
					if(	ignoreEnd > 0 && ignoreEnd <= inBuffer.length ){				
						endIndex 	= inBuffer.length -(ignoreStart+ignoreEnd);				
					}else{
						endIndex 	= changeBuffer.length;
					}
					
					System.arraycopy(inBuffer, startIndex, changeBuffer, 0, endIndex );
					
					String original = new String(inBuffer,DataConstants.CHARSET);
					
					String change = new String(changeBuffer,DataConstants.CHARSET);
					
					String regex = new String(pattern,DataConstants.CHARSET);
					
					isPresent = change.regionMatches(true, 0, regex, 0, pattern.length);
						
					System.out.println("isPresent : "+isPresent);
					System.out.println("original : "+original+" | "+ Arrays.toString(inBuffer));
					System.out.println("change : "+change+" | "+ Arrays.toString(changeBuffer));
					System.out.println("regex : "+regex+" | "+ Arrays.toString(pattern));
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							102,"Invalid pattern or length of data input.",
							"Cursor can not proceed."
					);
				}
				
				cursorState = CursorState.OPEN;
				
				return isPresent;
				
			}
			
			/**
			 * Adds the byte array at specified index for valid input else throws DataAccessException for invali input. The 
			 * cursor position moves to the end of byte array addition. Returns the expanded byte array after addition.
			 * @param index
			 * @param fieldVal
			 * @return
			 * @throws DataAccessException
			 */
			public byte[] add(int index, byte[] fieldVal) throws DataAccessException{
				
				byte[] changeBuffer = null;
						
				if(index > 0 && fieldVal.length > 0){
					
					changeBuffer = new byte[inBuffer.length+fieldVal.length];
					
					int cursorPosition = 0;			
					System.arraycopy(inBuffer, cursorPosition, changeBuffer, cursorPosition, index );
					cursorPosition += index;
					System.arraycopy(fieldVal,0, changeBuffer, cursorPosition, fieldVal.length );
					cursorPosition += fieldVal.length;
					System.arraycopy(inBuffer, index, changeBuffer, cursorPosition, (inBuffer.length-index));		
					
					this.inBuffer = changeBuffer;
				}else{
					
					reset();
					throw new DataAccessException(
							102,"Invalid index or length of data input.",
							"Cursor can not proceed."
					);
				}
						
				return changeBuffer;
			}
			
			/**
			 * Removes the bytes from the input from th index for the length provided
			 * @param index
			 * @param length
			 * @return
			 * @throws DataAccessException
			 */
			public byte[] remove(int index, int length) throws DataAccessException{
				
				byte[] changeBuffer = null;
				
				if(index > 0 && length > 0 && index < inBuffer.length){
					
					changeBuffer = new byte[inBuffer.length-length];
					
					int cursorPosition = 0;			
					System.arraycopy(inBuffer, cursorPosition, changeBuffer, cursorPosition, index );
					cursorPosition += index;			
					System.arraycopy(inBuffer, index+length, changeBuffer,cursorPosition , (changeBuffer.length-cursorPosition));		
					this.inBuffer = changeBuffer;
				}else{
					
					reset();
					throw new DataAccessException(
							102,"Invalid index or length of data input.",
							"Cursor can not proceed."
					);
				}
				
				return changeBuffer;
			}
			
			/**
			 * Mask pattern occurrence in input with masked bytes 
			 * @param pattern
			 * @param maskWith
			 * @return
			 * @throws DataAccessException
			 */
			public byte[] mask(byte[] pattern, byte maskWith) throws DataAccessException{
				
				if(pattern != null && pattern.length > 0 && maskWith  > 0){
					
					cursorState = CursorState.PROCESSING;
					
					byte[] mask = new byte[]{maskWith};
					
					inBuffer = findAndreplace(pattern,mask);
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							102,"Invalid index or length of dat input.",
							"Cursor can not proceed."
					);
				}
				
				cursorState = CursorState.OPEN;
				
				return inBuffer;
			}
			
			/**
			 * Finds all the occurence of pattern & replaces them with the new byte sequence
			 * @param pattern
			 * @param replaceWith
			 * @return
			 * @throws DataAccessException
			 */
			public byte[] findAndreplace(byte[] pattern, byte[] replaceWith) throws DataAccessException{
					
				if(pattern != null && pattern.length > 0 && replaceWith != null && replaceWith.length > 0){
					
					cursorState = CursorState.PROCESSING;
					
					String original = new String(inBuffer,DataConstants.CHARSET);
					
					String regex = new String(pattern,DataConstants.CHARSET);
					
					String replacement = new String(replaceWith,DataConstants.CHARSET);
					
					System.out.println("original : "+original+" | "+ Arrays.toString(inBuffer));
					System.out.println("regex : "+regex+" | "+ Arrays.toString(pattern));			
					System.out.println("replacement : "+replacement+" | "+ Arrays.toString(replaceWith));
					
					String changed = original.replaceAll(regex, replacement);		
					
								
					System.out.println("changed : "+changed+" | "+ Arrays.toString(changed.getBytes()));			
					
					inBuffer = changed.getBytes();
					
					cursorState = CursorState.OPEN;
				}else{
					reset();
					throw new DataAccessException(
							102,"Invalid index or length of data input.",
							"Cursor can not proceed."
					);
				}
				
				cursorState = CursorState.OPEN;
				
				return inBuffer;
			}
			
			/**
			 * Closes the cursor & changes the cursor state
			 * @return
			 * @throws DataAccessException
			 */
			public CursorState close() throws DataAccessException{
				
				// mark the inBuffer as processed
				this.inBuffer 	= null; 
				
				// Can happen in asynchronous handling 
				if(cursorState == CursorState.PROCESSING){
					reset();
					throw new DataAccessException(
							102,"Invalid Cursor state",
							"Processing Buffer I/O, cursor can not be closed. "
					);
				}
				
				cursorState = CursorState.CLOSE;
				return cursorState;
			}
		}
	}
}
