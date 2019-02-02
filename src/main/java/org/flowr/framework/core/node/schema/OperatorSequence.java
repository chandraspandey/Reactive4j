package org.flowr.framework.core.node.schema;

import org.flowr.framework.core.node.io.flow.data.binary.ByteEnumerableType;

/**
 * Defines OperatorSequence as high level type functions which the implementation program should support.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface OperatorSequence {
	
	/**
	 * Defines high level sequencing type that operation execution should support for tree of dependency nodes. 
	 * Separate instances of TREE, BRANCH, LEAF handler should be maintained for recursive operation & exception 
	 * handling.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum OperatorSequenceHandlerType implements ByteEnumerableType{
		NONE(0),
		TREE(1),
		BRANCH(2),
		LEAF(3);
		
		private byte code = 0;
		
		OperatorSequenceHandlerType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static OperatorSequenceHandlerType getType(int code) {
			
			OperatorSequenceHandlerType operatorSequenceHandlerType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					operatorSequenceHandlerType = NONE;
					break;
				}case 1:{
					operatorSequenceHandlerType = TREE;
					break;
				}case 2:{
					operatorSequenceHandlerType = BRANCH;
					break;
				}case 3:{
					operatorSequenceHandlerType = LEAF;
					break;
				}default :{
					operatorSequenceHandlerType = NONE;
					break;
				}			
			}
			
			return operatorSequenceHandlerType;
		}
		
	}	
	
	/**
	 * Defines high level operator sequence type that can be used for cascading operation of commit or roll back in a 
	 * dependency tree with control points(handler) at TREE, BRANCH, LEAF  should maintain during execution for 
	 * exception handling. During Rerun or replay the OperatorSequenceType can acquire characteristics of RESET for
	 * handlers to react to external or enforced change by resetting all the instance variables for rerun or replay.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum OperatorSequenceType implements ByteEnumerableType{
		NONE(0),
		INCREMENT(1),
		DECREMENT(2),
		RESET(3);
		
		private byte code = 0;
		
		OperatorSequenceType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static OperatorSequenceType getType(int code) {
			
			OperatorSequenceType operatorSequenceType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					operatorSequenceType = NONE;
					break;
				}case 1:{
					operatorSequenceType = INCREMENT;
					break;
				}case 2:{
					operatorSequenceType = DECREMENT;
					break;
				}case 3:{
					operatorSequenceType = RESET;
					break;
				}default :{
					operatorSequenceType = NONE;
					break;
				}			
			}
			
			return operatorSequenceType;
		}
		
	}
	
	/**
	 * Defines high level operator sequence identifier type that is permissible during commit operation as inbuilt 
	 * audit trail in a chain of command. AUTO signifies independence of using schema specific algorithm in maintaining
	 * trace ability in sequence of commands, suitable when correlation of data commit to external schema is not a 
	 * requirement. UNIT signifies that sequencing is involved & commit is linear in nature which can be useful for 
	 * replay or rerun in queued operations.BLOCK signifies that sequencing is involved & commit is linear but block 
	 * in nature(Increment & decrement operation should be multiple of x units) which can be useful for replay or 
	 * rerun in queued operations.
	 * @author Chandra Shekhar Pandey
	 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
	 *
	 */
	public enum OperatorSequenceIdentifierType implements ByteEnumerableType{
		NONE(0),
		AUTO(1),
		UNIT(2),
		BLOCK(3);
		
		private byte code = 0;
		
		OperatorSequenceIdentifierType(int code){
			
			this.code = (byte)code;
		}

		@Override
		public byte getCode() {
			
			return code;
		}	
		
		public static OperatorSequenceIdentifierType getType(int code) {
			
			OperatorSequenceIdentifierType operatorSequenceIdentifierType = NONE;
			
			switch((byte) code) {
				
				case 0:{
					operatorSequenceIdentifierType = NONE;
					break;
				}case 1:{
					operatorSequenceIdentifierType = AUTO;
					break;
				}case 2:{
					operatorSequenceIdentifierType = UNIT;
					break;
				}case 3:{
					operatorSequenceIdentifierType = BLOCK;
					break;
				}default :{
					operatorSequenceIdentifierType = NONE;
					break;
				}			
			}
			
			return operatorSequenceIdentifierType;
		}
		
	}
	
	public void setOperatorSequenceHandlerType(OperatorSequenceHandlerType operatorSequenceHandlerType);
	
	public void setOperatorSequenceType(OperatorSequenceType operatorSequenceType);
	
	public void setOperatorSequenceIdentifierType(OperatorSequenceIdentifierType operatorSequenceIdentifierType);
	
	public OperatorSequenceHandlerType getOperatorSequenceHandlerType();	

	public OperatorSequenceType getOperatorSequenceType();
}
