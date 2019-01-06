package org.flowr.framework.core.node.io.flow.data.binary.collection;

/**
 * Defines high level interface for Batch Format. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public interface BatchFormatCollection {

	/**
	 * 
	 * Defines marker interface for Batch sequence oriented protocols.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public interface BatchSequence {
			
		/**
		 * 
		 * Provides Batch sequence based on single character change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class SingleCharacterDataSequence implements BatchSequence{
			
			//  First byte of moving index
			private  byte firstMovingIndex = 64;
			
			// Rolling enforcement when secondMovingIndex reaches 90 
			private  long rollingIndex = 90;
			
			private static SingleCharacterDataSequence singleCharacterDataSequence;
			
			
			private SingleCharacterDataSequence(){
				
			}
			
			/**
			 * Accept the sequence if acting as recipient
			 * @param sequence
			 * @return
			 */
			public byte accept(byte sequence){
				
				firstMovingIndex 	= sequence;
				
				resetIfApplies();
				
				return next();
				
			}
			
			
			public static SingleCharacterDataSequence getInstance(){
				
				if(singleCharacterDataSequence == null){
					singleCharacterDataSequence = new SingleCharacterDataSequence();
				}
				
				return singleCharacterDataSequence;
			}
			
			public  byte next(){
				
				byte sequence = firstMovingIndex++;
				
				resetIfApplies();
				
				return sequence;
			}
			
			
			// reset at 90 byte value
			private  void resetIfApplies(){
				
				if(firstMovingIndex > rollingIndex){
					
					firstMovingIndex = 64;
				}
						
			}

		}
		
		/**
		 * 
		 * Provides Batch sequence based on dual character change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class DualCharacterDataSequence implements BatchSequence{
			
			//  First byte of moving index
			private  byte firstMovingIndex = 64;
			
			//  second byte of moving index, default incremented by 1 over first moving index
			private  byte secondMovingIndex = firstMovingIndex;
			
			// Rolling enforcement when secondMovingIndex reaches 90 
			private  long rollingIndex = 90;
			
			private static DualCharacterDataSequence dualCharacterDataSequence;
			
			
			private DualCharacterDataSequence(){
				
			}
			
			/**
			 * Accept the Header if acting as recipient
			 * @param initHeader
			 * @return
			 */
			public byte[] accept(byte[] sequence){
				
				firstMovingIndex 	= sequence[0];
				secondMovingIndex 	= sequence[1];
				resetIfApplies();
				
				return next();
				
			}
			
			
			public static DualCharacterDataSequence getInstance(){
				
				if(dualCharacterDataSequence == null){
					dualCharacterDataSequence = new DualCharacterDataSequence();
				}
				
				return dualCharacterDataSequence;
			}
			
			public  byte[] next(){
				
				byte[] sequence = null;
				
				if(firstMovingIndex <= rollingIndex && secondMovingIndex <= rollingIndex){
					
					sequence = new byte[]{firstMovingIndex, secondMovingIndex++};
				}
				
				resetIfApplies();
				
				return sequence;
			}
			
			
			// reset at 90 byte value
			private  void resetIfApplies(){
				
				if(secondMovingIndex > rollingIndex){
				
					secondMovingIndex = 64;
					firstMovingIndex++;
				}
				
				if(firstMovingIndex > rollingIndex){
					
					firstMovingIndex = 64;				
				}
						
			}

		}
		
		/**
		 * 
		 * Provides Batch sequence based on single number change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class SingleNumericDataSequence implements BatchSequence{
			
			//  First byte of moving index
			private  byte firstMovingIndex = 49;
			
			// Rolling enforcement when secondMovingIndex reaches 57 
			private  long rollingIndex = 57;
			
			private static SingleNumericDataSequence singleNumericDataSequence;
			
			
			private SingleNumericDataSequence(){
				
			}
			
			/**
			 * Accept the sequence if acting as recipient
			 * @param sequence
			 * @return
			 */
			public byte accept(byte sequence){
				
				firstMovingIndex 	= sequence;
				
				resetIfApplies();
				
				return next();
				
			}
			
			
			public static SingleNumericDataSequence getInstance(){
				
				if(singleNumericDataSequence == null){
					singleNumericDataSequence = new SingleNumericDataSequence();
				}
				
				return singleNumericDataSequence;
			}
			
			public  byte next(){
				
				byte sequence = firstMovingIndex++;
				
				resetIfApplies();
				
				return sequence;
			}
			
			
			// reset at 57 byte value
			private  void resetIfApplies(){
				
				if(firstMovingIndex > rollingIndex){
					
					firstMovingIndex = 49;
				}
						
			}

		}
		
		/**
		 * 
		 * Provides Batch sequence based on dual number change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class DualNumberDataSequence implements BatchSequence{
			
			//  First byte of moving index
			private  byte firstMovingIndex = 49;
			
			//  second byte of moving index, default incremented by 1 over first moving index
			private  byte secondMovingIndex = firstMovingIndex;
			
			// Rolling enforcement when secondMovingIndex reaches 57 
			private  long rollingIndex = 57;
			
			private static DualNumberDataSequence dualNumberDataSequence;
			
			
			private DualNumberDataSequence(){
				
			}
			
			/**
			 * Accept the Header if acting as recipient
			 * @param initHeader
			 * @return
			 */
			public byte[] accept(byte[] sequence){
				
				firstMovingIndex 	= sequence[0];
				secondMovingIndex 	= sequence[1];
				resetIfApplies();
				
				return next();
				
			}
			
			
			public static DualNumberDataSequence getInstance(){
				
				if(dualNumberDataSequence == null){
					dualNumberDataSequence = new DualNumberDataSequence();
				}
				
				return dualNumberDataSequence;
			}
			
			public  byte[] next(){
				
				byte[] sequence = null;
				
				if(firstMovingIndex <= rollingIndex && secondMovingIndex <= rollingIndex){
					
					sequence = new byte[]{firstMovingIndex, secondMovingIndex++};
				}
				
				resetIfApplies();
				
				return sequence;
			}
			
			
			// reset at 57 byte value
			private  void resetIfApplies(){
				
				if(secondMovingIndex > rollingIndex){
				
					secondMovingIndex = 49;
					firstMovingIndex++;
				}
				
				if(firstMovingIndex > rollingIndex){
					
					firstMovingIndex = 49;				
				}
						
			}

		}
		
		/**
		 * 
		 * Provides Batch sequence based on one character & one number change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class SingleAlphaNumericDataSequence implements BatchSequence{
			
			private SingleCharacterDataSequence singleCharacterDataSequence = SingleCharacterDataSequence.getInstance();
			private SingleNumericDataSequence singleNumericDataSequence 	= SingleNumericDataSequence.getInstance();
			
			private static SingleAlphaNumericDataSequence singleAlphaNumericDataSequence;		
			
			private SingleAlphaNumericDataSequence(){			
			}
			
			public static SingleAlphaNumericDataSequence getInstance(){
				
				if(singleAlphaNumericDataSequence == null){
					singleAlphaNumericDataSequence = new SingleAlphaNumericDataSequence();
				}
				
				return singleAlphaNumericDataSequence;
			}
			
			public  byte[] next(){
				
				return new byte[] { singleCharacterDataSequence.next(), singleNumericDataSequence.next()};			
				
			}
		}
		
		/**
		 * 
		 * Provides Batch sequence based on dual character & dual number change oriented protocols.
		 * @author Chandra Shekhar Pandey
		 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
		 *
		 */
		public class DualAlphaNumericDataSequence implements BatchSequence{
			
			private DualCharacterDataSequence dualCharacterDataSequence = DualCharacterDataSequence.getInstance();
			private DualNumberDataSequence dualNumberDataSequence 		= DualNumberDataSequence.getInstance();
			
			private static DualAlphaNumericDataSequence dualAlphaNumericDataSequence;
			
			private DualAlphaNumericDataSequence() {			
			}
			
			public static DualAlphaNumericDataSequence getInstance() {
				
				if(dualAlphaNumericDataSequence == null){
					dualAlphaNumericDataSequence = new DualAlphaNumericDataSequence();
				}
				
				return dualAlphaNumericDataSequence;
			}
			
			public  byte[] next(){
				
				byte[] charSequence = dualCharacterDataSequence.next();
				byte[] numberSequence = dualNumberDataSequence.next();
				
				byte[] sequence 			= new byte[4];
				
				System.arraycopy(charSequence, 0, sequence, 0, charSequence.length );
				
				System.arraycopy(numberSequence, 0, sequence, 2, numberSequence.length );
				
				return sequence;			
				
			}
		}
	}
}
