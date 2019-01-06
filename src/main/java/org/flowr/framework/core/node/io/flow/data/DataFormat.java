package org.flowr.framework.core.node.io.flow.data;

import java.nio.charset.Charset;
import java.util.TreeMap;

import org.flowr.framework.core.constants.ExceptionConstants;
import org.flowr.framework.core.constants.ExceptionMessages;
import org.flowr.framework.core.exception.DataAccessException;
import org.flowr.framework.core.exception.FrameworkException;
import org.flowr.framework.core.node.io.flow.data.DataConstants.BinaryLiteralConstant;
import org.flowr.framework.core.node.io.flow.data.DataConstants.FormatTokenizer;

/**
 * Marker interface for defining Pre defined format that can be used for a use case. Mapped to ISO_8859_1 
 * Definition : https://en.wikipedia.org/wiki/ISO/IEC_8859-1
 * Defines Binary literal Encoding for values in range of 0-127
 * https://en.wikipedia.org/wiki/ASCII
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface DataFormat extends DataSchema{

		
	/** Defines Sequence type in a flow for downstream handling
	 * PRESEQUENCE : Occurs before data,
	 * POSTSEQUENCE : Occurs after data
	 * CONTROL_CHARACTER : Represents Control Character for TCP IP handling
	 * PATTERN : Represents the sequence as predefined Pattern
	 */
	public enum FormatSequenceType{
		PRESEQUENCE,
		POSTSEQUENCE,
		CONTROL_CHARACTER,
		DATA,
		PATTERN
	}

	public class BinaryFormat {		
		
		// Maintains the mapping between FormatTokenizer & corresponding binary value
		private static TreeMap<FormatTokenizer,Byte> binaryMap = new TreeMap<FormatTokenizer,Byte>();
		
		static{
					
			// Control bytes
			binaryMap.put(FormatTokenizer.NUL, 				DataConstants.NUL);	
			binaryMap.put(FormatTokenizer.SOH, 				DataConstants.SOH);	
			binaryMap.put(FormatTokenizer.STX, 				DataConstants.STX);
			binaryMap.put(FormatTokenizer.ETX, 				DataConstants.ETX);	
			binaryMap.put(FormatTokenizer.EOT, 				DataConstants.EOT);	
			binaryMap.put(FormatTokenizer.ENQ, 				DataConstants.ENQ);	
			binaryMap.put(FormatTokenizer.ACK, 				DataConstants.ACK);	
			binaryMap.put(FormatTokenizer.BEL, 				DataConstants.BEL);	
			binaryMap.put(FormatTokenizer.BS,  				DataConstants.BS);
			binaryMap.put(FormatTokenizer.HT,  				DataConstants.HT);
			binaryMap.put(FormatTokenizer.LF,  				DataConstants.LF);	
			binaryMap.put(FormatTokenizer.VT,  				DataConstants.VT);
			binaryMap.put(FormatTokenizer.FF,  				DataConstants.FF);		
			binaryMap.put(FormatTokenizer.CR,  				DataConstants.CR);	
			binaryMap.put(FormatTokenizer.SO,  				DataConstants.SO);	
			binaryMap.put(FormatTokenizer.SI,  				DataConstants.SI);	
			binaryMap.put(FormatTokenizer.DLE, 				DataConstants.DLE);
			binaryMap.put(FormatTokenizer.DC1, 				DataConstants.DC1);		
			binaryMap.put(FormatTokenizer.DC2, 				DataConstants.DC2);		
			binaryMap.put(FormatTokenizer.DC3, 				DataConstants.DC3);		
			binaryMap.put(FormatTokenizer.DC4, 				DataConstants.DC4);		
			binaryMap.put(FormatTokenizer.NAK, 				DataConstants.NAK);	
			binaryMap.put(FormatTokenizer.SYN, 				DataConstants.SYN);	
			binaryMap.put(FormatTokenizer.ETB, 				DataConstants.ETB);	
			binaryMap.put(FormatTokenizer.CAN, 				DataConstants.CAN);	
			binaryMap.put(FormatTokenizer.EM,  				DataConstants.EM);	
			binaryMap.put(FormatTokenizer.SUB, 				DataConstants.SUB);
			binaryMap.put(FormatTokenizer.ESC, 				DataConstants.ESC);	
			binaryMap.put(FormatTokenizer.FS,  				DataConstants.FS);	
			binaryMap.put(FormatTokenizer.GS,  				DataConstants.GS);	
			binaryMap.put(FormatTokenizer.RS,  				DataConstants.RS);	
			binaryMap.put(FormatTokenizer.US,  				DataConstants.US);	
			binaryMap.put(FormatTokenizer.SP,  				DataConstants.SP);	
			binaryMap.put(FormatTokenizer.DEL, 				DataConstants.DEL);
			
			// ASCII/Printable Character bytes
			binaryMap.put(FormatTokenizer.EXCLAMATION,		DataConstants.EXCLAMATION);		
			binaryMap.put(FormatTokenizer.QUOTATION,		DataConstants.QUOTATION);		
			binaryMap.put(FormatTokenizer.HASH,				DataConstants.HASH);		
			binaryMap.put(FormatTokenizer.DOLLAR,			DataConstants.DOLLAR);		
			binaryMap.put(FormatTokenizer.PERCENT,			DataConstants.PERCENT);
			binaryMap.put(FormatTokenizer.AMPERSAND,		DataConstants.AMPERSAND);		
			binaryMap.put(FormatTokenizer.APOSTROPHE,		DataConstants.APOSTROPHE);		
			binaryMap.put(FormatTokenizer.PARANTHESIS_LEFT,	DataConstants.PARANTHESIS_LEFT);			
			binaryMap.put(FormatTokenizer.PARANTHESIS_RIGHT,DataConstants.PARANTHESIS_RIGHT);			
			binaryMap.put(FormatTokenizer.ASTERIX,			DataConstants.ASTERIX);			
			binaryMap.put(FormatTokenizer.PLUS,				DataConstants.PLUS);			
			binaryMap.put(FormatTokenizer.COMMA,			DataConstants.COMMA);			
			binaryMap.put(FormatTokenizer.MINUS,			DataConstants.MINUS);			
			binaryMap.put(FormatTokenizer.FULL_STOP,		DataConstants.FULL_STOP);			
			binaryMap.put(FormatTokenizer.SLASH,			DataConstants.SLASH);			
			binaryMap.put(FormatTokenizer.ZERO,				DataConstants.ZERO);			
			binaryMap.put(FormatTokenizer.ONE,				DataConstants.ONE);			
			binaryMap.put(FormatTokenizer.TWO,				DataConstants.TWO);			
			binaryMap.put(FormatTokenizer.THREE,			DataConstants.THREE);			
			binaryMap.put(FormatTokenizer.FOUR,				DataConstants.FOUR);			
			binaryMap.put(FormatTokenizer.FIVE, 			DataConstants.FIVE);			
			binaryMap.put(FormatTokenizer.SIX,				DataConstants.SIX);			
			binaryMap.put(FormatTokenizer.SEVEN,			DataConstants.SEVEN);			
			binaryMap.put(FormatTokenizer.EIGHT,			DataConstants.EIGHT);			
			binaryMap.put(FormatTokenizer.NINE,				DataConstants.NINE);			
			binaryMap.put(FormatTokenizer.COLON,			DataConstants.COLON);			
			binaryMap.put(FormatTokenizer.SEMI_COLON,		DataConstants.SEMI_COLON);			
			binaryMap.put(FormatTokenizer.LESS_THAN,		DataConstants.LESS_THAN);			
			binaryMap.put(FormatTokenizer.EQUAL_TO,			DataConstants.EQUAL_TO);			
			binaryMap.put(FormatTokenizer.GREATER_THAN,		DataConstants.GREATER_THAN);			
			binaryMap.put(FormatTokenizer.QUESTION_MARK,	DataConstants.QUESTION_MARK);			
			binaryMap.put(FormatTokenizer.AT_THE_RATE,		DataConstants.AT_THE_RATE);			
			binaryMap.put(FormatTokenizer.A,				DataConstants.A);			
			binaryMap.put(FormatTokenizer.B,				DataConstants.B);			
			binaryMap.put(FormatTokenizer.C,				DataConstants.C);			
			binaryMap.put(FormatTokenizer.D,				DataConstants.D);			
			binaryMap.put(FormatTokenizer.E,				DataConstants.E);			
			binaryMap.put(FormatTokenizer.F,				DataConstants.F);			
			binaryMap.put(FormatTokenizer.G,				DataConstants.G);			
			binaryMap.put(FormatTokenizer.H,				DataConstants.H);			
			binaryMap.put(FormatTokenizer.I,				DataConstants.I);			
			binaryMap.put(FormatTokenizer.J,				DataConstants.J);			
			binaryMap.put(FormatTokenizer.K,				DataConstants.K);			
			binaryMap.put(FormatTokenizer.L,				DataConstants.L);			
			binaryMap.put(FormatTokenizer.M,				DataConstants.M);			
			binaryMap.put(FormatTokenizer.N,				DataConstants.N);			
			binaryMap.put(FormatTokenizer.O,				DataConstants.O);			
			binaryMap.put(FormatTokenizer.P,				DataConstants.P);			
			binaryMap.put(FormatTokenizer.Q,				DataConstants.Q);			
			binaryMap.put(FormatTokenizer.R,				DataConstants.R);			
			binaryMap.put(FormatTokenizer.S,				DataConstants.S);			
			binaryMap.put(FormatTokenizer.T,				DataConstants.T);			
			binaryMap.put(FormatTokenizer.U,				DataConstants.U);			
			binaryMap.put(FormatTokenizer.V,				DataConstants.V);			
			binaryMap.put(FormatTokenizer.W,				DataConstants.W);			
			binaryMap.put(FormatTokenizer.X,				DataConstants.X);			
			binaryMap.put(FormatTokenizer.Y,				DataConstants.Y);			
			binaryMap.put(FormatTokenizer.Z,				DataConstants.Z);			
			binaryMap.put(FormatTokenizer.LEFT_SQUARE_BRACKET,DataConstants.LEFT_SQUARE_BRACKET);			
			binaryMap.put(FormatTokenizer.BACKSLASH,		DataConstants.BACKSLASH);			
			binaryMap.put(FormatTokenizer.RIGHT_SQUARE_BRACKET,	DataConstants.RIGHT_SQUARE_BRACKET);			
			binaryMap.put(FormatTokenizer.CARET,			DataConstants.CARET);			
			binaryMap.put(FormatTokenizer.UNDERSCORE,		DataConstants.UNDERSCORE);			
			binaryMap.put(FormatTokenizer.ACCENT,			DataConstants.ACCENT);			
			binaryMap.put(FormatTokenizer.a,				DataConstants.a);			
			binaryMap.put(FormatTokenizer.b,				DataConstants.b);			
			binaryMap.put(FormatTokenizer.c,				DataConstants.c);			
			binaryMap.put(FormatTokenizer.d,				DataConstants.d);			
			binaryMap.put(FormatTokenizer.e,				DataConstants.e);			
			binaryMap.put(FormatTokenizer.f,				DataConstants.f);			
			binaryMap.put(FormatTokenizer.g,				DataConstants.g);			
			binaryMap.put(FormatTokenizer.h,				DataConstants.h);			
			binaryMap.put(FormatTokenizer.i,				DataConstants.i);			
			binaryMap.put(FormatTokenizer.j,				DataConstants.j);			
			binaryMap.put(FormatTokenizer.k,				DataConstants.k);			
			binaryMap.put(FormatTokenizer.l,				DataConstants.l);			
			binaryMap.put(FormatTokenizer.m,				DataConstants.m);			
			binaryMap.put(FormatTokenizer.n,				DataConstants.n);			
			binaryMap.put(FormatTokenizer.o,				DataConstants.o);			
			binaryMap.put(FormatTokenizer.p,				DataConstants.p);			
			binaryMap.put(FormatTokenizer.q,				DataConstants.q);			
			binaryMap.put(FormatTokenizer.r,				DataConstants.r);			
			binaryMap.put(FormatTokenizer.s,				DataConstants.s);			
			binaryMap.put(FormatTokenizer.t,				DataConstants.t);			
			binaryMap.put(FormatTokenizer.u,				DataConstants.u);			
			binaryMap.put(FormatTokenizer.v,				DataConstants.v);			
			binaryMap.put(FormatTokenizer.w,				DataConstants.w);			
			binaryMap.put(FormatTokenizer.x,				DataConstants.x);			
			binaryMap.put(FormatTokenizer.y,				DataConstants.y);			
			binaryMap.put(FormatTokenizer.z,				DataConstants.z);			
			binaryMap.put(FormatTokenizer.LEFT_CURLY_BRACKET,DataConstants.LEFT_CURLY_BRACKET);			
			binaryMap.put(FormatTokenizer.VERTICAL_BAR,		DataConstants.VERTICAL_BAR);			
			binaryMap.put(FormatTokenizer.RIGHTT_CURLY_BRACKET,DataConstants.RIGHTT_CURLY_BRACKET);			
			binaryMap.put(FormatTokenizer.TILDE,			DataConstants.TILDE);
		}
		
		/**
		 * Returns the map of FormatTokenizers and Binary values
		 * @param formatTokenizer
		 * @return
		 */
		public static TreeMap<FormatTokenizer,Byte> binaryMap(FormatTokenizer formatTokenizer){				
			return binaryMap;
		}
		
		/**
		 * Returns the binary value corresponding to FormatTokenizer
		 * @param formatTokenizer
		 * @return
		 */	
		public static byte lookup(FormatTokenizer formatTokenizer){		
			return binaryMap.get(formatTokenizer);
		}
		
		/**
		 * Returns binary array based on value. Wrapper implementation for instrumentation of byte array based on 
		 * sequence of of FormatTokenizer as Input 
		 * @param val
		 * @return
		 * @throws FrameworkException if input not in range of 0-127
		 */
		public static byte[] getBinary(FormatTokenizer[] formatTokenizer) throws DataAccessException{
			
			byte[] value = new byte[formatTokenizer.length];
			
			for(int bitIndex=0;bitIndex < formatTokenizer.length; bitIndex++){
				value[bitIndex] = getBinary(formatTokenizer[bitIndex]);
			}
			
			return value;
		}
		
		/**
		 * Returns binary value for based on FormatTokenizer
		 * Reference implementation for Logical instrumentation that can be extended for building Output based on Input
		 * type
		 * @param formatTokenizer
		 * @return
		 * @throws DataAccessException if input not in range of 0-127
		 */
		public static byte getBinaryLiteral(FormatTokenizer formatTokenizer) throws DataAccessException{
			
			byte token = 0;
			
			switch(formatTokenizer){
				
				case NUL:{						
					token = BinaryLiteralConstant.NUL;
					break;
				}case SOH:{					
					token = BinaryLiteralConstant.SOH;
					break;
				}case STX:{					
					token = BinaryLiteralConstant.STX;
					break;
				}case ETX:{					
					token = BinaryLiteralConstant.ETX;
					break;
				}case EOT:{					
					token = BinaryLiteralConstant.EOT;
					break;
				}case ENQ:{					
					token = BinaryLiteralConstant.ENQ;
					break;
				}case ACK:{					
					token = BinaryLiteralConstant.ACK;
					break;
				}case BEL:{					
					token = BinaryLiteralConstant.BEL;
					break;
				}case HT:{					
					token = BinaryLiteralConstant.HT;
					break;
				}case LF:{					
					token = BinaryLiteralConstant.LF;
					break;
				}case VT:{					
					token = BinaryLiteralConstant.VT;
					break;
				}case FF:{					
					token = BinaryLiteralConstant.FF;
					break;
				}case CR:{					
					token = BinaryLiteralConstant.CR;
					break;
				}case SO:{					
					token = BinaryLiteralConstant.SO;
					break;
				}case SI:{					
					token = BinaryLiteralConstant.SI;
					break;
				}case DLE:{					
					token = BinaryLiteralConstant.DLE;
					break;
				}case DC1:{					
					token = BinaryLiteralConstant.DC1;
					break;
				}case DC2:{					
					token = BinaryLiteralConstant.DC2;
					break;
				}case DC3:{					
					token = BinaryLiteralConstant.DC3;
					break;
				}case DC4:{					
					token = BinaryLiteralConstant.DC4;
					break;
				}case NAK:{					
					token = BinaryLiteralConstant.NAK;
					break;
				}case SYN:{					
					token = BinaryLiteralConstant.SYN;
					break;
				}case ETB:{					
					token = BinaryLiteralConstant.ETB;
					break;
				}case CAN:{					
					token = BinaryLiteralConstant.CAN;
					break;
				}case SUB:{					
					token = BinaryLiteralConstant.SUB;
					break;
				}case EM:{					
					token = BinaryLiteralConstant.EM;
					break;
				}case ESC:{					
					token = BinaryLiteralConstant.ESC;
					break;
				}case FS:{					
					token = BinaryLiteralConstant.FS;
					break;
				}case GS:{					
					token = BinaryLiteralConstant.GS;
					break;
				}case RS:{					
					token = BinaryLiteralConstant.RS;
					break;
				}case US:{					
					token = BinaryLiteralConstant.US;
					break;
				}case SP:{					
					token = BinaryLiteralConstant.SP;
					break;
				}case DEL:{					
					token = BinaryLiteralConstant.DEL;
					break;
				}case EXCLAMATION:{
					token = BinaryLiteralConstant.EXCLAMATION;
					break;
				}case QUOTATION:{
					token = BinaryLiteralConstant.QUOTATION;
					break;
				}case HASH:{
					token = BinaryLiteralConstant.HASH;
					break;
				}case DOLLAR:{
					token = BinaryLiteralConstant.DOLLAR;
					break;
				}case PERCENT:{
					token = BinaryLiteralConstant.PERCENT;
					break;
				}case AMPERSAND:{
					token = BinaryLiteralConstant.AMPERSAND;
					break;
				}case APOSTROPHE:{
					token = BinaryLiteralConstant.APOSTROPHE;
					break;
				}case PARANTHESIS_LEFT:{
					token = BinaryLiteralConstant.PARANTHESIS_LEFT;
					break;
				}case PARANTHESIS_RIGHT:{
					token = BinaryLiteralConstant.PARANTHESIS_RIGHT;
					break;
				}case ASTERIX:{
					token = BinaryLiteralConstant.ASTERIX;
					break;
				}case PLUS:{
					token = BinaryLiteralConstant.PLUS;
					break;
				}case COMMA:{
					token = BinaryLiteralConstant.COMMA;
					break;
				}case MINUS:{
					token = BinaryLiteralConstant.MINUS;
					break;
				}case FULL_STOP:{
					token = BinaryLiteralConstant.FULL_STOP;
					break;
				}case SLASH:{
					token = BinaryLiteralConstant.SLASH;
					break;
				}case ZERO:{
					token = BinaryLiteralConstant.ZERO;
					break;
				}case ONE:{
					token = BinaryLiteralConstant.ONE;
					break;
				}case TWO:{
					token = BinaryLiteralConstant.TWO;
					break;
				}case THREE:{
					token = BinaryLiteralConstant.THREE;
					break;
				}case FOUR:{
					token = BinaryLiteralConstant.FOUR;
					break;
				}case FIVE:{
					token = BinaryLiteralConstant.FIVE;
					break;
				}case SIX:{
					token = BinaryLiteralConstant.SIX;
					break;
				}case SEVEN:{
					token = BinaryLiteralConstant.SEVEN;
					break;
				}case EIGHT:{
					token = BinaryLiteralConstant.EIGHT;
					break;
				}case NINE:{
					token = BinaryLiteralConstant.NINE;
					break;
				}case COLON:{
					token = BinaryLiteralConstant.COLON;
					break;
				}case SEMI_COLON:{
					token = BinaryLiteralConstant.SEMI_COLON;
					break;
				}case LESS_THAN:{
					token = BinaryLiteralConstant.LESS_THAN;
					break;
				}case EQUAL_TO:{
					token = BinaryLiteralConstant.EQUAL_TO;
					break;
				}case GREATER_THAN:{
					token = BinaryLiteralConstant.GREATER_THAN;
					break;
				}case QUESTION_MARK:{
					token = BinaryLiteralConstant.QUESTION_MARK;
					break;
				}case AT_THE_RATE:{
					token = BinaryLiteralConstant.AT_THE_RATE;
					break;
				}case A:{
					token = BinaryLiteralConstant.A;
					break;
				}case B:{
					token = BinaryLiteralConstant.B;
					break;
				}case C:{
					token = BinaryLiteralConstant.C;
					break;
				}case D:{
					token = BinaryLiteralConstant.D;
					break;
				}case E:{
					token = BinaryLiteralConstant.E;
					break;
				}case F:{
					token = BinaryLiteralConstant.F;
					break;
				}case G:{
					token = BinaryLiteralConstant.G;
					break;
				}case H:{
					token = BinaryLiteralConstant.H;
					break;
				}case I:{
					token = BinaryLiteralConstant.I;
					break;
				}case J:{
					token = BinaryLiteralConstant.J;
					break;
				}case K:{
					token = BinaryLiteralConstant.K;
					break;
				}case L:{
					token = BinaryLiteralConstant.L;
					break;
				}case M:{
					token = BinaryLiteralConstant.M;
					break;
				}case N:{
					token = BinaryLiteralConstant.N;
					break;
				}case O:{
					token = BinaryLiteralConstant.O;
					break;
				}case P:{
					token = BinaryLiteralConstant.P;
					break;
				}case Q:{
					token = BinaryLiteralConstant.Q;
					break;
				}case R:{
					token = BinaryLiteralConstant.R;
					break;
				}case S:{
					token = BinaryLiteralConstant.S;
					break;
				}case T:{
					token = BinaryLiteralConstant.T;
					break;
				}case U:{
					token = BinaryLiteralConstant.U;
					break;
				}case V:{
					token = BinaryLiteralConstant.V;
					break;
				}case W:{
					token = BinaryLiteralConstant.W;
					break;
				}case X:{
					token = BinaryLiteralConstant.X;
					break;
				}case Y:{
					token = BinaryLiteralConstant.Y;
					break;
				}case Z:{
					token = BinaryLiteralConstant.Z;
					break;
				}case LEFT_SQUARE_BRACKET:{
					token = BinaryLiteralConstant.LEFT_SQUARE_BRACKET;
					break;
				}case BACKSLASH:{
					token = BinaryLiteralConstant.BACKSLASH;
					break;
				}case RIGHT_SQUARE_BRACKET:{
					token = BinaryLiteralConstant.RIGHT_SQUARE_BRACKET;
					break;
				}case CARET:{
					token = BinaryLiteralConstant.CARET;
					break;
				}case UNDERSCORE:{
					token = BinaryLiteralConstant.UNDERSCORE;
					break;
				}case ACCENT:{
					token = BinaryLiteralConstant.ACCENT;
					break;
				}case a:{
					token = BinaryLiteralConstant.a;
					break;
				}case b:{
					token = BinaryLiteralConstant.b;
					break;
				}case c:{
					token = BinaryLiteralConstant.c;
					break;
				}case d:{
					token = BinaryLiteralConstant.d;
					break;
				}case e:{
					token = BinaryLiteralConstant.e;
					break;
				}case f:{
					token = BinaryLiteralConstant.f;
					break;
				}case g:{
					token = BinaryLiteralConstant.g;
					break;
				}case h:{
					token = BinaryLiteralConstant.h;
					break;
				}case i:{
					token = BinaryLiteralConstant.i;
					break;
				}case j:{
					token = BinaryLiteralConstant.j;
					break;
				}case k:{
					token = BinaryLiteralConstant.k;
					break;
				}case l:{
					token = BinaryLiteralConstant.l;
					break;
				}case m:{
					token = BinaryLiteralConstant.m;
					break;
				}case n:{
					token = BinaryLiteralConstant.n;
					break;
				}case o:{
					token = BinaryLiteralConstant.o;
					break;
				}case p:{
					token = BinaryLiteralConstant.p;
					break;
				}case q:{
					token = BinaryLiteralConstant.q;
					break;
				}case r:{
					token = BinaryLiteralConstant.r;
					break;
				}case s:{
					token = BinaryLiteralConstant.s;
					break;
				}case t:{
					token = BinaryLiteralConstant.t;
					break;
				}case u:{
					token = BinaryLiteralConstant.u;
					break;
				}case v:{
					token = BinaryLiteralConstant.v;
					break;
				}case w:{
					token = BinaryLiteralConstant.w;
					break;
				}case x:{
					token = BinaryLiteralConstant.x;
					break;
				}case y:{
					token = BinaryLiteralConstant.y;
					break;
				}case z:{
					token = BinaryLiteralConstant.z;
					break;
				}case LEFT_CURLY_BRACKET:{
					token = BinaryLiteralConstant.LEFT_CURLY_BRACKET;
					break;
				}case VERTICAL_BAR:{
					token = BinaryLiteralConstant.VERTICAL_BAR;
					break;
				}case RIGHTT_CURLY_BRACKET:{
					token = BinaryLiteralConstant.RIGHTT_CURLY_BRACKET;
					break;
				}case TILDE:{
					token = BinaryLiteralConstant.TILDE;
					break;
				}default:{
					DataAccessException e = new DataAccessException(
							ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
							ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
							"Input not in range of 0-127");
					throw e;
				}
			}		
			return token;
		}
		
		/**
		 * Returns binary value for based on FormatTokenizer
		 * Reference implementation for Logical instrumentation that can be extended for building Output based on Input
		 * type
		 * @param formatTokenizer
		 * @return
		 * @throws DataAccessException if input not in range of 0-127
		 */
		public static byte getBinaryLiteral(int val) throws DataAccessException{
			
			byte token = 0;
			
			if(val >= 0 && val < 128){
				
				switch(val){
				
					case 0:{						
						token = BinaryLiteralConstant.NUL;
						break;
					}case 1:{					
						token = BinaryLiteralConstant.SOH;
						break;
					}case 2:{					
						token = BinaryLiteralConstant.STX;
						break;
					}case 3:{					
						token = BinaryLiteralConstant.ETX;
						break;
					}case 4:{					
						token = BinaryLiteralConstant.EOT;
						break;
					}case 5:{					
						token = BinaryLiteralConstant.ENQ;
						break;
					}case 6:{					
						token = BinaryLiteralConstant.ACK;
						break;
					}case 7:{					
						token = BinaryLiteralConstant.BEL;
						break;
					}case 8:{					
						token = BinaryLiteralConstant.BS;
						break;
					}case 9:{					
						token = BinaryLiteralConstant.HT;
						break;
					}case 10:{					
						token = BinaryLiteralConstant.LF;
						break;
					}case 11:{					
						token = BinaryLiteralConstant.VT;
						break;
					}case 12:{					
						token = BinaryLiteralConstant.FF;
						break;
					}case 13:{					
						token = BinaryLiteralConstant.CR;
						break;
					}case 14:{					
						token = BinaryLiteralConstant.SO;
						break;
					}case 15:{					
						token = BinaryLiteralConstant.SI;
						break;
					}case 16:{					
						token = BinaryLiteralConstant.DLE;
						break;
					}case 17:{					
						token = BinaryLiteralConstant.DC1;
						break;
					}case 18:{					
						token = BinaryLiteralConstant.DC2;
						break;
					}case 19:{					
						token = BinaryLiteralConstant.DC3;
						break;
					}case 20:{					
						token = BinaryLiteralConstant.DC4;
						break;
					}case 21:{					
						token = BinaryLiteralConstant.NAK;
						break;
					}case 22:{					
						token = BinaryLiteralConstant.SYN;
						break;
					}case 23:{					
						token = BinaryLiteralConstant.ETB;
						break;
					}case 24:{					
						token = BinaryLiteralConstant.CAN;
						break;
					}case 25:{					
						token = BinaryLiteralConstant.EM;
						break;
					}case 26:{
						token = BinaryLiteralConstant.SUB;
						break;
					}case 27:{					
						token = BinaryLiteralConstant.ESC;
						break;
					}case 28:{					
						token = BinaryLiteralConstant.FS;
						break;
					}case 29:{					
						token = BinaryLiteralConstant.GS;
						break;
					}case 30:{					
						token = BinaryLiteralConstant.RS;
						break;
					}case 31:{					
						token = BinaryLiteralConstant.US;
						break;
					}case 32:{					
						token = BinaryLiteralConstant.SP;
						break;
					}case 33:{
						token = BinaryLiteralConstant.EXCLAMATION;
						break;
					}case 34:{
						token = BinaryLiteralConstant.QUOTATION;
						break;
					}case 35:{
						token = BinaryLiteralConstant.HASH;
						break;
					}case 36:{
						token = BinaryLiteralConstant.DOLLAR;
						break;
					}case 37:{
						token = BinaryLiteralConstant.PERCENT;
						break;
					}case 38:{
						token = BinaryLiteralConstant.AMPERSAND;
						break;
					}case 39:{
						token = BinaryLiteralConstant.APOSTROPHE;
						break;
					}case 40:{
						token = BinaryLiteralConstant.PARANTHESIS_LEFT;
						break;
					}case 41:{
						token = BinaryLiteralConstant.PARANTHESIS_RIGHT;
						break;
					}case 42:{
						token = BinaryLiteralConstant.ASTERIX;
						break;
					}case 43:{
						token = BinaryLiteralConstant.PLUS;
						break;
					}case 44:{
						token = BinaryLiteralConstant.COMMA;
						break;
					}case 45:{
						token = BinaryLiteralConstant.MINUS;
						break;
					}case 46:{
						token = BinaryLiteralConstant.FULL_STOP;
						break;
					}case 47:{
						token = BinaryLiteralConstant.SLASH;
						break;
					}case 48:{
						token = BinaryLiteralConstant.ZERO;
						break;
					}case 49:{
						token = BinaryLiteralConstant.ONE;
						break;
					}case 50:{
						token = BinaryLiteralConstant.TWO;
						break;
					}case 51:{
						token = BinaryLiteralConstant.THREE;
						break;
					}case 52:{
						token = BinaryLiteralConstant.FOUR;
						break;
					}case 53:{
						token = BinaryLiteralConstant.FIVE;
						break;
					}case 54:{
						token = BinaryLiteralConstant.SIX;
						break;
					}case 55:{
						token = BinaryLiteralConstant.SEVEN;
						break;
					}case 56:{
						token = BinaryLiteralConstant.EIGHT;
						break;
					}case 57:{
						token = BinaryLiteralConstant.NINE;
						break;
					}case 58:{
						token = BinaryLiteralConstant.COLON;
						break;
					}case 59:{
						token = BinaryLiteralConstant.SEMI_COLON;
						break;
					}case 60:{
						token = BinaryLiteralConstant.LESS_THAN;
						break;
					}case 61:{
						token = BinaryLiteralConstant.EQUAL_TO;
						break;
					}case 62:{
						token = BinaryLiteralConstant.GREATER_THAN;
						break;
					}case 63:{
						token = BinaryLiteralConstant.QUESTION_MARK;
						break;
					}case 64:{
						token = BinaryLiteralConstant.AT_THE_RATE;
						break;
					}case 65:{
						token = BinaryLiteralConstant.A;
						break;
					}case 66:{
						token = BinaryLiteralConstant.B;
						break;
					}case 67:{
						token = BinaryLiteralConstant.C;
						break;
					}case 68:{
						token = BinaryLiteralConstant.D;
						break;
					}case 69:{
						token = BinaryLiteralConstant.E;
						break;
					}case 70:{
						token = BinaryLiteralConstant.F;
						break;
					}case 71:{
						token = BinaryLiteralConstant.G;
						break;
					}case 72:{
						token = BinaryLiteralConstant.H;
						break;
					}case 73:{
						token = BinaryLiteralConstant.I;
						break;
					}case 74:{
						token = BinaryLiteralConstant.J;
						break;
					}case 75:{
						token = BinaryLiteralConstant.K;
						break;
					}case 76:{
						token = BinaryLiteralConstant.L;
						break;
					}case 77:{
						token = BinaryLiteralConstant.M;
						break;
					}case 78:{
						token = BinaryLiteralConstant.N;
						break;
					}case 79:{
						token = BinaryLiteralConstant.O;
						break;
					}case 80:{
						token = BinaryLiteralConstant.P;
						break;
					}case 81:{
						token = BinaryLiteralConstant.Q;
						break;
					}case 82:{
						token = BinaryLiteralConstant.R;
						break;
					}case 83:{
						token = BinaryLiteralConstant.S;
						break;
					}case 84:{
						token = BinaryLiteralConstant.T;
						break;
					}case 85:{
						token = BinaryLiteralConstant.U;
						break;
					}case 86:{
						token = BinaryLiteralConstant.V;
						break;
					}case 87:{
						token = BinaryLiteralConstant.W;
						break;
					}case 88:{
						token = BinaryLiteralConstant.X;
						break;
					}case 89:{
						token = BinaryLiteralConstant.Y;
						break;
					}case 90:{
						token = BinaryLiteralConstant.Z;
						break;
					}case 91:{
						token = BinaryLiteralConstant.LEFT_SQUARE_BRACKET;
						break;
					}case 92:{
						token = BinaryLiteralConstant.BACKSLASH;
						break;
					}case 93:{
						token = BinaryLiteralConstant.RIGHT_SQUARE_BRACKET;
						break;
					}case 94:{
						token = BinaryLiteralConstant.CARET;
						break;
					}case 95:{
						token = BinaryLiteralConstant.UNDERSCORE;
						break;
					}case 96:{
						token = BinaryLiteralConstant.ACCENT;
						break;
					}case 97:{
						token = BinaryLiteralConstant.a;
						break;
					}case 98:{
						token = BinaryLiteralConstant.b;
						break;
					}case 99:{
						token = BinaryLiteralConstant.c;
						break;
					}case 100:{
						token = BinaryLiteralConstant.d;
						break;
					}case 101:{
						token = BinaryLiteralConstant.e;
						break;
					}case 102:{
						token = BinaryLiteralConstant.f;
						break;
					}case 103:{
						token = BinaryLiteralConstant.g;
						break;
					}case 104:{
						token = BinaryLiteralConstant.h;
						break;
					}case 105:{
						token = BinaryLiteralConstant.i;
						break;
					}case 106:{
						token = BinaryLiteralConstant.j;
						break;
					}case 107:{
						token = BinaryLiteralConstant.k;
						break;
					}case 108:{
						token = BinaryLiteralConstant.l;
						break;
					}case 109:{
						token = BinaryLiteralConstant.m;
						break;
					}case 110:{
						token = BinaryLiteralConstant.n;
						break;
					}case 111:{
						token = BinaryLiteralConstant.o;
						break;
					}case 112:{
						token = BinaryLiteralConstant.p;
						break;
					}case 113:{
						token = BinaryLiteralConstant.q;
						break;
					}case 114:{
						token = BinaryLiteralConstant.r;
						break;
					}case 115:{
						token = BinaryLiteralConstant.s;
						break;
					}case 116:{
						token = BinaryLiteralConstant.t;
						break;
					}case 117:{
						token = BinaryLiteralConstant.u;
						break;
					}case 118:{
						token = BinaryLiteralConstant.v;
						break;
					}case 119:{
						token = BinaryLiteralConstant.w;
						break;
					}case 120:{
						token = BinaryLiteralConstant.x;
						break;
					}case 121:{
						token = BinaryLiteralConstant.y;
						break;
					}case 122:{
						token = BinaryLiteralConstant.z;
						break;
					}case 123:{
						token = BinaryLiteralConstant.LEFT_CURLY_BRACKET;
						break;
					}case 124:{
						token = BinaryLiteralConstant.VERTICAL_BAR;
						break;
					}case 125:{
						token = BinaryLiteralConstant.RIGHTT_CURLY_BRACKET;
						break;
					}case 126:{
						token = BinaryLiteralConstant.TILDE;
						break;
					}case 127:{
						token = BinaryLiteralConstant.DEL;
						break;
					}default:{
						DataAccessException e = new DataAccessException(
								ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
								ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
								"Input not in range of 0-127");
						throw e;
					}
				}
			}		
			return token;
		}	
		
		/**
		 * Returns binary value for based on FormatTokenizer
		 * Reference implementation for Logical instrumentation that can be extended for building Output based on Input
		 * type
		 * @param formatTokenizer
		 * @return
		 * @throws DataAccessException if input not in range of 0-127
		 */
		public static byte getBinary(FormatTokenizer formatTokenizer) throws DataAccessException{
			
			byte token = 0;
			
			switch(formatTokenizer){
				
				case NUL:{						
					token = DataConstants.NUL;
					break;
				}case SOH:{					
					token = DataConstants.SOH;
					break;
				}case STX:{					
					token = DataConstants.STX;
					break;
				}case ETX:{					
					token = DataConstants.ETX;
					break;
				}case EOT:{					
					token = DataConstants.EOT;
					break;
				}case ENQ:{					
					token = DataConstants.ENQ;
					break;
				}case ACK:{					
					token = DataConstants.ACK;
					break;
				}case BEL:{					
					token = DataConstants.BEL;
					break;
				}case HT:{					
					token = DataConstants.HT;
					break;
				}case LF:{					
					token = DataConstants.LF;
					break;
				}case VT:{					
					token = DataConstants.VT;
					break;
				}case FF:{					
					token = DataConstants.FF;
					break;
				}case CR:{					
					token = DataConstants.CR;
					break;
				}case SO:{					
					token = DataConstants.SO;
					break;
				}case SI:{					
					token = DataConstants.SI;
					break;
				}case DLE:{					
					token = DataConstants.DLE;
					break;
				}case DC1:{					
					token = DataConstants.DC1;
					break;
				}case DC2:{					
					token = DataConstants.DC2;
					break;
				}case DC3:{					
					token = DataConstants.DC3;
					break;
				}case DC4:{					
					token = DataConstants.DC4;
					break;
				}case NAK:{					
					token = DataConstants.NAK;
					break;
				}case SYN:{					
					token = DataConstants.SYN;
					break;
				}case ETB:{					
					token = DataConstants.ETB;
					break;
				}case CAN:{					
					token = DataConstants.CAN;
					break;
				}case SUB:{					
					token = DataConstants.SUB;
					break;
				}case EM:{					
					token = DataConstants.EM;
					break;
				}case ESC:{					
					token = DataConstants.ESC;
					break;
				}case FS:{					
					token = DataConstants.FS;
					break;
				}case GS:{					
					token = DataConstants.GS;
					break;
				}case RS:{					
					token = DataConstants.RS;
					break;
				}case US:{					
					token = DataConstants.US;
					break;
				}case SP:{					
					token = DataConstants.SP;
					break;
				}case DEL:{					
					token = DataConstants.DEL;
					break;
				}case EXCLAMATION:{
					token = DataConstants.EXCLAMATION;
					break;
				}case QUOTATION:{
					token = DataConstants.QUOTATION;
					break;
				}case HASH:{
					token = DataConstants.HASH;
					break;
				}case DOLLAR:{
					token = DataConstants.DOLLAR;
					break;
				}case PERCENT:{
					token = DataConstants.PERCENT;
					break;
				}case AMPERSAND:{
					token = DataConstants.AMPERSAND;
					break;
				}case APOSTROPHE:{
					token = DataConstants.APOSTROPHE;
					break;
				}case PARANTHESIS_LEFT:{
					token = DataConstants.PARANTHESIS_LEFT;
					break;
				}case PARANTHESIS_RIGHT:{
					token = DataConstants.PARANTHESIS_RIGHT;
					break;
				}case ASTERIX:{
					token = DataConstants.ASTERIX;
					break;
				}case PLUS:{
					token = DataConstants.PLUS;
					break;
				}case COMMA:{
					token = DataConstants.COMMA;
					break;
				}case MINUS:{
					token = DataConstants.MINUS;
					break;
				}case FULL_STOP:{
					token = DataConstants.FULL_STOP;
					break;
				}case SLASH:{
					token = DataConstants.SLASH;
					break;
				}case ZERO:{
					token = DataConstants.ZERO;
					break;
				}case ONE:{
					token = DataConstants.ONE;
					break;
				}case TWO:{
					token = DataConstants.TWO;
					break;
				}case THREE:{
					token = DataConstants.THREE;
					break;
				}case FOUR:{
					token = DataConstants.FOUR;
					break;
				}case FIVE:{
					token = DataConstants.FIVE;
					break;
				}case SIX:{
					token = DataConstants.SIX;
					break;
				}case SEVEN:{
					token = DataConstants.SEVEN;
					break;
				}case EIGHT:{
					token = DataConstants.EIGHT;
					break;
				}case NINE:{
					token = DataConstants.NINE;
					break;
				}case COLON:{
					token = DataConstants.COLON;
					break;
				}case SEMI_COLON:{
					token = DataConstants.SEMI_COLON;
					break;
				}case LESS_THAN:{
					token = DataConstants.LESS_THAN;
					break;
				}case EQUAL_TO:{
					token = DataConstants.EQUAL_TO;
					break;
				}case GREATER_THAN:{
					token = DataConstants.GREATER_THAN;
					break;
				}case QUESTION_MARK:{
					token = DataConstants.QUESTION_MARK;
					break;
				}case AT_THE_RATE:{
					token = DataConstants.AT_THE_RATE;
					break;
				}case A:{
					token = DataConstants.A;
					break;
				}case B:{
					token = DataConstants.B;
					break;
				}case C:{
					token = DataConstants.C;
					break;
				}case D:{
					token = DataConstants.D;
					break;
				}case E:{
					token = DataConstants.E;
					break;
				}case F:{
					token = DataConstants.F;
					break;
				}case G:{
					token = DataConstants.G;
					break;
				}case H:{
					token = DataConstants.H;
					break;
				}case I:{
					token = DataConstants.I;
					break;
				}case J:{
					token = DataConstants.J;
					break;
				}case K:{
					token = DataConstants.K;
					break;
				}case L:{
					token = DataConstants.L;
					break;
				}case M:{
					token = DataConstants.M;
					break;
				}case N:{
					token = DataConstants.N;
					break;
				}case O:{
					token = DataConstants.O;
					break;
				}case P:{
					token = DataConstants.P;
					break;
				}case Q:{
					token = DataConstants.Q;
					break;
				}case R:{
					token = DataConstants.R;
					break;
				}case S:{
					token = DataConstants.S;
					break;
				}case T:{
					token = DataConstants.T;
					break;
				}case U:{
					token = DataConstants.U;
					break;
				}case V:{
					token = DataConstants.V;
					break;
				}case W:{
					token = DataConstants.W;
					break;
				}case X:{
					token = DataConstants.X;
					break;
				}case Y:{
					token = DataConstants.Y;
					break;
				}case Z:{
					token = DataConstants.Z;
					break;
				}case LEFT_SQUARE_BRACKET:{
					token = DataConstants.LEFT_SQUARE_BRACKET;
					break;
				}case BACKSLASH:{
					token = DataConstants.BACKSLASH;
					break;
				}case RIGHT_SQUARE_BRACKET:{
					token = DataConstants.RIGHT_SQUARE_BRACKET;
					break;
				}case CARET:{
					token = DataConstants.CARET;
					break;
				}case UNDERSCORE:{
					token = DataConstants.UNDERSCORE;
					break;
				}case ACCENT:{
					token = DataConstants.ACCENT;
					break;
				}case a:{
					token = DataConstants.a;
					break;
				}case b:{
					token = DataConstants.b;
					break;
				}case c:{
					token = DataConstants.c;
					break;
				}case d:{
					token = DataConstants.d;
					break;
				}case e:{
					token = DataConstants.e;
					break;
				}case f:{
					token = DataConstants.f;
					break;
				}case g:{
					token = DataConstants.g;
					break;
				}case h:{
					token = DataConstants.h;
					break;
				}case i:{
					token = DataConstants.i;
					break;
				}case j:{
					token = DataConstants.j;
					break;
				}case k:{
					token = DataConstants.k;
					break;
				}case l:{
					token = DataConstants.l;
					break;
				}case m:{
					token = DataConstants.m;
					break;
				}case n:{
					token = DataConstants.n;
					break;
				}case o:{
					token = DataConstants.o;
					break;
				}case p:{
					token = DataConstants.p;
					break;
				}case q:{
					token = DataConstants.q;
					break;
				}case r:{
					token = DataConstants.r;
					break;
				}case s:{
					token = DataConstants.s;
					break;
				}case t:{
					token = DataConstants.t;
					break;
				}case u:{
					token = DataConstants.u;
					break;
				}case v:{
					token = DataConstants.v;
					break;
				}case w:{
					token = DataConstants.w;
					break;
				}case x:{
					token = DataConstants.x;
					break;
				}case y:{
					token = DataConstants.y;
					break;
				}case z:{
					token = DataConstants.z;
					break;
				}case LEFT_CURLY_BRACKET:{
					token = DataConstants.LEFT_CURLY_BRACKET;
					break;
				}case VERTICAL_BAR:{
					token = DataConstants.VERTICAL_BAR;
					break;
				}case RIGHTT_CURLY_BRACKET:{
					token = DataConstants.RIGHTT_CURLY_BRACKET;
					break;
				}case TILDE:{
					token = DataConstants.TILDE;
					break;
				}default:{
					DataAccessException e = new DataAccessException(
							ExceptionConstants.ERR_CONFIG_INVALID_FORMAT,						 
							ExceptionMessages.MSG_CLIENT_INVALID_CONFIG,
							"Input not in range of 0-127");
					throw e;
				}
			}		
			return token;
		}
		
		
		/**
		 * Returns binary array with literal based on value. Wrapper implementation for building array output based on the 
		 * sequence of input types 
		 * @param val
		 * @return
		 * @throws DataAccessException if input not in range of 0-127
		 */
		public static byte[] getBinaryLiteral(int[] val) throws DataAccessException{
			
			byte[] value = new byte[val.length];
			
			for(int bitIndex=0;bitIndex < val.length; bitIndex++){
				value[bitIndex] = getBinaryLiteral(bitIndex);
			}
			
			return value;
		}
		

		/**
		 * Returns String representation of binary literal as maintained in BinaryFormat
		 * @param literal
		 * @return
		 */
		public static String binaryLiteralToString(int literal){
			
			return String.format("%8s", Integer.toBinaryString(literal & 0xFF)).replace(' ', '0');
		}
		
		/**
		 * Returns String array representation of binary literal array as maintained in BinaryFormat
		 * @param literal
		 * @return
		 */
		public static String[] binaryLiteralToStringArray(int[] arr) throws DataAccessException{
			
			String[] val = new String[arr.length];
			
			for(int byteIndex =0; byteIndex <arr.length; byteIndex++){
				
				val[byteIndex] = binaryLiteralToString(getBinaryLiteral(arr[byteIndex]));			
			}		
			return val;
		}
		
		/**
		 * Returns String representation of byte array as applicable to a Charset
		 * @param literal
		 * @return
		 */
		public static String binaryToCharset(byte[] arr, Charset charset){
			
			return new String(arr, charset);
		}
		
		/**
		 * Returns String representation of binary literal as applicable to a Charset
		 * @param literal
		 * @return
		 */
		public static String binaryLiteralToCharset(int literal, Charset charset){
			
			String s = String.format("%8s", Integer.toBinaryString(literal & 0xFF)).replace(' ', '0');
			
			return new String(s.getBytes(charset));
		}
		
	}

}
