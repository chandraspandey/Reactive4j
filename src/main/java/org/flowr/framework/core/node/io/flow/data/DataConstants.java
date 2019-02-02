package org.flowr.framework.core.node.io.flow.data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Defines DataConstants for Binary data for RPC operations.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
public class DataConstants {
	
	public static final Charset CHARSET							= StandardCharsets.ISO_8859_1;
	
	public static final int BUFFER_SIZE							= 1024;
	
	public static final int HEADER_SIZE							= 4;
	public static final int TRAILER_SIZE						= 4;
	
	public static final int PRIMARY_BITMAP_SIZE					= 64;
	
	public static final int SECONDARY_BITMAP_SIZE				= 64;
	
	// Defines the Field Name
	public static final String FIELD_DEFAULT_NAME				= "DEFAULT";
	
	// Defines the field position in primary bit map as per spec/standard
	public static final int FIELD_DEFAULT_POSITION				= 0;
	
	// Defines the field length as per spec/standard
	public static final int FIELD_DEFAULT_LENGTH				= 0;
	
	// Default length that is not set for easy declarative recognition
	public static final int FIELD_DEFAULT						= 0;
	public static final int FIELD_CODE_DEFAULT					= 1;
	
	
	// Control/ Non printable bytes
	public static final byte NUL							 	= (byte)0;
	public static final byte SOH								= (byte)1;
	public static final byte STX								= (byte)2;
	public static final byte ETX								= (byte)3;
	public static final byte EOT								= (byte)4;
	public static final byte ENQ								= (byte)5;
	public static final byte ACK								= (byte)6;
	public static final byte BEL								= (byte)7;
	public static final byte BS									= (byte)8;
	public static final byte HT									= (byte)9;
	public static final byte LF									= (byte)10;
	public static final byte VT									= (byte)11;
	public static final byte FF									= (byte)12;
	public static final byte CR									= (byte)13;
	public static final byte SO									= (byte)14;
	public static final byte SI									= (byte)15;
	public static final byte DLE								= (byte)16;
	public static final byte DC1								= (byte)17;
	public static final byte DC2								= (byte)18;
	public static final byte DC3								= (byte)19;
	public static final byte DC4								= (byte)20;
	public static final byte NAK								= (byte)21;
	public static final byte SYN								= (byte)22;
	public static final byte ETB								= (byte)23;
	public static final byte CAN								= (byte)24;
	public static final byte EM									= (byte)25;
	public static final byte SUB								= (byte)26;
	public static final byte ESC	 							= (byte)27;
	public static final byte FS									= (byte)28;
	public static final byte GS	 								= (byte)29;
	public static final byte RS									= (byte)30;
	public static final byte US									= (byte)31;
	public static final byte SP									= (byte)32;
	public static final byte DEL								= (byte)127;
	
	
	// ASCII / Printable bytes	
	public static final byte EXCLAMATION						= (byte)33;
	public static final byte QUOTATION							= (byte)34;
	public static final byte HASH								= (byte)35;
	public static final byte DOLLAR								= (byte)36;
	public static final byte PERCENT							= (byte)37;
	public static final byte AMPERSAND							= (byte)38;
	public static final byte APOSTROPHE							= (byte)39;
	public static final byte PARANTHESIS_LEFT					= (byte)40;
	public static final byte PARANTHESIS_RIGHT					= (byte)41;
	public static final byte ASTERIX							= (byte)42;	
	public static final byte PLUS								= (byte)43;
	public static final byte COMMA								= (byte)44;	
	public static final byte MINUS								= (byte)45;
	public static final byte FULL_STOP							= (byte)46;
	public static final byte SLASH								= (byte)47;
	public static final byte ZERO								= (byte)48;
	public static final byte ONE								= (byte)49;
	public static final byte TWO								= (byte)50;
	public static final byte THREE								= (byte)51;
	public static final byte FOUR								= (byte)52;
	public static final byte FIVE								= (byte)53;
	public static final byte SIX								= (byte)54;
	public static final byte SEVEN								= (byte)55;
	public static final byte EIGHT								= (byte)56;
	public static final byte NINE								= (byte)57;
	public static final byte COLON								= (byte)58;
	public static final byte SEMI_COLON							= (byte)59;
	public static final byte LESS_THAN							= (byte)60;
	public static final byte EQUAL_TO							= (byte)61;
	public static final byte GREATER_THAN						= (byte)62;
	public static final byte QUESTION_MARK						= (byte)63;		
	public static final byte AT_THE_RATE						= (byte)64;
	public static final byte A									= (byte)65;
	public static final byte B									= (byte)66;
	public static final byte C									= (byte)67;
	public static final byte D									= (byte)68;
	public static final byte E									= (byte)69;
	public static final byte F									= (byte)70;
	public static final byte G									= (byte)71;
	public static final byte H									= (byte)72;
	public static final byte I									= (byte)73;
	public static final byte J									= (byte)74;
	public static final byte K									= (byte)75;
	public static final byte L									= (byte)76;
	public static final byte M									= (byte)77;
	public static final byte N									= (byte)78;
	public static final byte O									= (byte)79;
	public static final byte P									= (byte)80;
	public static final byte Q									= (byte)81;
	public static final byte R									= (byte)82;
	public static final byte S									= (byte)83;
	public static final byte T									= (byte)84;
	public static final byte U									= (byte)85;
	public static final byte V									= (byte)86;
	public static final byte W									= (byte)87;
	public static final byte X									= (byte)88;
	public static final byte Y									= (byte)89;
	public static final byte Z									= (byte)90;
	public static final byte LEFT_SQUARE_BRACKET				= (byte)91;
	public static final byte BACKSLASH							= (byte)92;
	public static final byte RIGHT_SQUARE_BRACKET				= (byte)93;
	public static final byte CARET								= (byte)94; 
	public static final byte UNDERSCORE							= (byte)95; 
	public static final byte ACCENT								= (byte)96;
	public static final byte a									= (byte)97;
	public static final byte b									= (byte)98;
	public static final byte c									= (byte)99;
	public static final byte d									= (byte)100;
	public static final byte e									= (byte)101;
	public static final byte f									= (byte)102;
	public static final byte g									= (byte)103;
	public static final byte h									= (byte)104;
	public static final byte i									= (byte)105;
	public static final byte j									= (byte)106;
	public static final byte k									= (byte)107;
	public static final byte l									= (byte)108;
	public static final byte m									= (byte)109;
	public static final byte n									= (byte)110;
	public static final byte o									= (byte)111;
	public static final byte p									= (byte)112;
	public static final byte q									= (byte)113;
	public static final byte r									= (byte)114;
	public static final byte s									= (byte)115;
	public static final byte t									= (byte)116;
	public static final byte u									= (byte)117;
	public static final byte v									= (byte)118;
	public static final byte w									= (byte)119;
	public static final byte x									= (byte)120;
	public static final byte y									= (byte)121;
	public static final byte z									= (byte)122;
	public static final byte LEFT_CURLY_BRACKET					= (byte)123;
	public static final byte VERTICAL_BAR						= (byte)124;
	public static final byte RIGHTT_CURLY_BRACKET				= (byte)125;
	public static final byte TILDE								= (byte)126;

	public enum FormatTokenizer{
		// Start of Control Bytes
		NUL,
		SOH,
		STX,
		ETX,
		EOT,
		ENQ,
		ACK,
		BEL,
		BS,
		HT,
		LF,
		VT,
		FF,
		CR,
		SO,
		SI,
		DLE,
		DC1,
		DC2,
		DC3,
		DC4,
		NAK,
		SYN,
		ETB,
		CAN,
		EM,
		SUB,
		ESC,
		FS,
		GS,
		RS,
		US,
		SP,
		// End of Control Bytes
		
		// Start of Character/printable Bytes
		EXCLAMATION,
		QUOTATION,
		HASH,
		DOLLAR,
		PERCENT,
		AMPERSAND,
		APOSTROPHE,
		PARANTHESIS_LEFT,
		PARANTHESIS_RIGHT,
		ASTERIX,	
		PLUS,
		COMMA,	
		MINUS,
		FULL_STOP,
		SLASH,
		ZERO,
		ONE,
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		COLON,
		SEMI_COLON,
		LESS_THAN,
		EQUAL_TO,
		GREATER_THAN,
		QUESTION_MARK,		
		AT_THE_RATE,
		A,
		B,
		C,
		D,
		E,
		F,
		G,
		H,
		I,
		J,
		K,
		L,
		M,
		N,
		O,
		P,
		Q,
		R,
		S,
		T,
		U,
		V,
		W,
		X,
		Y,
		Z,
		LEFT_SQUARE_BRACKET,
		BACKSLASH,
		RIGHT_SQUARE_BRACKET,
		CARET, 
		UNDERSCORE, 
		ACCENT,
		a,
		b,
		c,
		d,
		e,
		f,
		g,
		h,
		i,
		j,
		k,
		l,
		m,
		n,
		o,
		p,
		q,
		r,
		s,
		t,
		u,
		v,
		w,
		x,
		y,
		z,
		LEFT_CURLY_BRACKET,
		VERTICAL_BAR,
		RIGHTT_CURLY_BRACKET,
		TILDE,
		// End of Character/printable Bytes
		DEL // Control Byte
	}
	
	
	public enum FormatType{
		BINARY,
		/*DECIMAL,
		HEXADECIMAL,
		OCTAL,
		ASCII*/
	}
	
	/**
	 * Defines Bit Tokenizer which can represent a Bit 
	 * @author Chandra Pandey
	 *
	 */
	public enum BitTokenizer{
		Bit0,
		Bit1,
		Bit2,
		Bit3,
		Bit4,
		Bit5,
		Bit6,
		Bit7,
		Bit8,
		Bit9,
		Bit10,
		Bit11,
		Bit12,
		Bit13,
		Bit14,
		Bit15,
		Bit16,
		Bit17,
		Bit18,
		Bit19,
		Bit20,
		Bit21,
		Bit22,
		Bit23,
		Bit24,
		Bit25,
		Bit26,
		Bit27,
		Bit28,
		Bit29,
		Bit30,
		Bit31,
		Bit32,
		Bit33,
		Bit34,
		Bit35,
		Bit36,
		Bit37,
		Bit38,
		Bit39,
		Bit40,
		Bit41,
		Bit42,
		Bit43,
		Bit44,
		Bit45,
		Bit46,
		Bit47,
		Bit48,
		Bit49,
		Bit50,
		Bit51,
		Bit52,
		Bit53,
		Bit54,
		Bit55,
		Bit56,
		Bit57,
		Bit58,
		Bit59,
		Bit60,		
		Bit61,
		Bit62,
		Bit63,
		Bit64,
		Bit65,
		Bit66,
		Bit67,
		Bit68,
		Bit69,
		Bit70,		
		Bit71,
		Bit72,
		Bit73,
		Bit74,
		Bit75,
		Bit76,
		Bit77,
		Bit78,
		Bit79,
		Bit80,	
		Bit81,
		Bit82,
		Bit83,
		Bit84,
		Bit85,
		Bit86,
		Bit87,
		Bit88,
		Bit89,
		Bit90,	
		Bit91,
		Bit92,
		Bit93,
		Bit94,
		Bit95,
		Bit96,
		Bit97,
		Bit98,
		Bit99,
		Bit100,	
		Bit101,
		Bit102,
		Bit103,
		Bit104,
		Bit105,
		Bit106,
		Bit107,
		Bit108,
		Bit109,
		Bit110,
		Bit111,
		Bit112,
		Bit113,
		Bit114,
		Bit115,
		Bit116,
		Bit117,
		Bit118,
		Bit119,
		Bit120,
		Bit121,
		Bit122,
		Bit123,
		Bit124,
		Bit125,
		Bit126,
		Bit127
	}


	public static class BinaryLiteralConstant {
		
		// Control/ Non printable bytes
		public static final byte NUL							 	= 0b0000000;
		public static final byte SOH								= 0b0000001;
		public static final byte STX								= 0b0000010;
		public static final byte ETX								= 0b0000011;
		public static final byte EOT								= 0b0000100;
		public static final byte ENQ								= 0b0000101;
		public static final byte ACK								= 0b0000110;
		public static final byte BEL								= 0b0000111;
		public static final byte BS									= 0b0001000;
		public static final byte HT									= 0b0001001;
		public static final byte LF									= 0b0001010;
		public static final byte VT									= 0b0001011;
		public static final byte FF									= 0b0001100;
		public static final byte CR									= 0b0001101;
		public static final byte SO									= 0b0001110;
		public static final byte SI									= 0b0001111;
		public static final byte DLE								= 0b0010000;
		public static final byte DC1								= 0b0010001;
		public static final byte DC2								= 0b0010010;
		public static final byte DC3								= 0b0010011;
		public static final byte DC4								= 0b0010100;
		public static final byte NAK								= 0b0010101;
		public static final byte SYN								= 0b0010110;
		public static final byte ETB								= 0b0010111;
		public static final byte CAN								= 0b0011000;
		public static final byte EM									= 0b0011001;
		public static final byte SUB								= 0b0011010;
		public static final byte ESC	 							= 0b0011011;
		public static final byte FS									= 0b0011100;
		public static final byte GS	 								= 0b0011101;
		public static final byte RS									= 0b0011110;
		public static final byte US									= 0b0011111;
		public static final byte SP									= 0b0100000;
		public static final byte DEL								= 0b1111111;
		
		
		// ASCII / Printable bytes	
		public static final byte EXCLAMATION						= 0b0100001;
		public static final byte QUOTATION							= 0b0100010;
		public static final byte HASH								= 0b0100011;
		public static final byte DOLLAR								= 0b0100100;
		public static final byte PERCENT							= 0b0100101;
		public static final byte AMPERSAND							= 0b0100110;
		public static final byte APOSTROPHE							= 0b0100111;
		public static final byte PARANTHESIS_LEFT					= 0b0101000;
		public static final byte PARANTHESIS_RIGHT					= 0b0101001;
		public static final byte ASTERIX							= 0b0101010;	
		public static final byte PLUS								= 0b0101011;
		public static final byte COMMA								= 0b0101100;	
		public static final byte MINUS								= 0b0101101;
		public static final byte FULL_STOP							= 0b0101110;
		public static final byte SLASH								= 0b0101111;
		public static final byte ZERO								= 0b0110000;
		public static final byte ONE								= 0b0110001;
		public static final byte TWO								= 0b0110010;
		public static final byte THREE								= 0b0110011;
		public static final byte FOUR								= 0b0110100;
		public static final byte FIVE								= 0b0110101;
		public static final byte SIX								= 0b0110110;
		public static final byte SEVEN								= 0b0110111;
		public static final byte EIGHT								= 0b0111000;
		public static final byte NINE								= 0b0111001;
		public static final byte COLON								= 0b0111010;
		public static final byte SEMI_COLON							= 0b0111011;
		public static final byte LESS_THAN							= 0b0111100;
		public static final byte EQUAL_TO							= 0b0111101;
		public static final byte GREATER_THAN						= 0b0111110;
		public static final byte QUESTION_MARK						= 0b0111111;		
		public static final byte AT_THE_RATE						= 0b1000000;
		public static final byte A									= 0b1000001;
		public static final byte B									= 0b1000010;
		public static final byte C									= 0b1000011;
		public static final byte D									= 0b1000100;
		public static final byte E									= 0b1000101;
		public static final byte F									= 0b1000110;
		public static final byte G									= 0b1000111;
		public static final byte H									= 0b1001000;
		public static final byte I									= 0b1001001;
		public static final byte J									= 0b1001010;
		public static final byte K									= 0b1001011;
		public static final byte L									= 0b1001100;
		public static final byte M									= 0b1001101;
		public static final byte N									= 0b1001110;
		public static final byte O									= 0b1001111;
		public static final byte P									= 0b1010000;
		public static final byte Q									= 0b1010001;
		public static final byte R									= 0b1010010;
		public static final byte S									= 0b1010011;
		public static final byte T									= 0b1010100;
		public static final byte U									= 0b1010101;
		public static final byte V									= 0b1010110;
		public static final byte W									= 0b1010111;
		public static final byte X									= 0b1011000;
		public static final byte Y									= 0b1011001;
		public static final byte Z									= 0b1011010;
		public static final byte LEFT_SQUARE_BRACKET				= 0b1011011;
		public static final byte BACKSLASH							= 0b1011100;
		public static final byte RIGHT_SQUARE_BRACKET				= 0b1011101;
		public static final byte CARET								= 0b1011110; 
		public static final byte UNDERSCORE							= 0b1011111; 
		public static final byte ACCENT								= 0b1100000;
		public static final byte a									= 0b1100001;
		public static final byte b									= 0b1100010;
		public static final byte c									= 0b1100011;
		public static final byte d									= 0b1100100;
		public static final byte e									= 0b1100101;
		public static final byte f									= 0b1100110;
		public static final byte g									= 0b1100111;
		public static final byte h									= 0b1101000;
		public static final byte i									= 0b1101001;
		public static final byte j									= 0b1101010;
		public static final byte k									= 0b1101011;
		public static final byte l									= 0b1101100;
		public static final byte m									= 0b1101101;
		public static final byte n									= 0b1101110;
		public static final byte o									= 0b1101111;
		public static final byte p									= 0b1110000;
		public static final byte q									= 0b1110001;
		public static final byte r									= 0b1110010;
		public static final byte s									= 0b1110011;
		public static final byte t									= 0b1110100;
		public static final byte u									= 0b1110101;
		public static final byte v									= 0b1110110;
		public static final byte w									= 0b1110111;
		public static final byte x									= 0b1111000;
		public static final byte y									= 0b1111001;
		public static final byte z									= 0b1111010;
		public static final byte LEFT_CURLY_BRACKET					= 0b1111011;
		public static final byte VERTICAL_BAR						= 0b1111100;
		public static final byte RIGHTT_CURLY_BRACKET				= 0b1111101;
		public static final byte TILDE								= 0b1111110;
	}
}

