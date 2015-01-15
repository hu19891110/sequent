/* The following code was generated by JFlex 1.4.3 on 1/9/15 7:33 PM */

package com.applego.sequent;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.applego.sequent.parser.psi.SequentTypes.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 1/9/15 7:33 PM from the specification file
 * <tt>/mnt/datahome/pin/Development/IdeaPlugins/sequent/src/com/applego/sequent/_SequentLexer.flex</tt>
 */
public class _SequentLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\1\1\0\1\1\1\1\22\0\1\1\1\30\1\0"+
    "\1\34\11\0\1\2\1\12\14\0\1\14\1\4\1\11\1\3\1\0"+
    "\1\15\1\52\1\0\1\35\1\0\1\40\1\45\4\0\1\46\1\44"+
    "\1\37\1\41\1\36\1\51\1\0\1\43\1\50\1\42\1\54\1\53"+
    "\2\0\1\47\1\0\1\7\1\0\1\10\3\0\1\20\2\0\1\27"+
    "\1\25\1\31\1\33\1\0\1\32\2\0\1\24\1\23\1\26\1\5"+
    "\2\0\1\21\1\16\1\17\1\22\2\0\1\6\3\0\1\13\uff83\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\1\1\4\1\5\1\6"+
    "\1\7\3\1\1\10\3\1\1\11\4\1\1\12\1\13"+
    "\1\14\1\0\1\15\2\0\1\16\7\0\1\17\1\20"+
    "\6\0\1\21\7\0\1\22\10\0\1\23\7\0\1\24"+
    "\1\0\1\25\1\26\5\0\1\27\2\0\1\30\3\0"+
    "\1\31\2\0\1\32";

  private static int [] zzUnpackAction() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\55\0\132\0\207\0\264\0\55\0\55\0\55"+
    "\0\55\0\341\0\u010e\0\u013b\0\55\0\u0168\0\u0195\0\u01c2"+
    "\0\55\0\u01ef\0\u021c\0\u0249\0\u0276\0\55\0\55\0\55"+
    "\0\u02a3\0\u02d0\0\u02fd\0\u032a\0\55\0\u0357\0\u0384\0\u03b1"+
    "\0\u03de\0\u040b\0\u0438\0\u0465\0\55\0\55\0\u0492\0\u04bf"+
    "\0\u04ec\0\u0519\0\u0546\0\u0573\0\55\0\u05a0\0\u05cd\0\u05fa"+
    "\0\u0627\0\u0654\0\u0681\0\u06ae\0\55\0\u06db\0\u0708\0\u0735"+
    "\0\u0762\0\u078f\0\u07bc\0\u07e9\0\u0816\0\55\0\u0843\0\u0870"+
    "\0\u089d\0\u08ca\0\u08f7\0\u0924\0\u0951\0\55\0\u097e\0\u09ab"+
    "\0\55\0\u09d8\0\u0a05\0\u0a32\0\u0a5f\0\u0a8c\0\55\0\u0ab9"+
    "\0\u0ae6\0\55\0\u0b13\0\u0b40\0\u0b6d\0\55\0\u0b9a\0\u0bc7"+
    "\0\55";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\2\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\2\2\1\17"+
    "\7\2\1\20\3\2\1\21\1\22\10\2\1\23\1\2"+
    "\1\24\2\2\1\25\1\2\56\0\1\3\56\0\1\26"+
    "\53\0\1\27\63\0\1\30\55\0\1\31\55\0\1\32"+
    "\57\0\1\33\6\0\1\34\45\0\1\35\63\0\1\36"+
    "\1\0\1\37\63\0\1\40\4\0\1\41\51\0\1\42"+
    "\54\0\1\43\66\0\1\44\14\0\1\45\55\0\1\46"+
    "\60\0\1\47\63\0\1\50\54\0\1\51\53\0\1\52"+
    "\66\0\1\53\61\0\1\54\57\0\1\55\56\0\1\56"+
    "\47\0\1\57\30\0\1\60\63\0\1\61\54\0\1\62"+
    "\56\0\1\63\62\0\1\64\62\0\1\65\61\0\1\66"+
    "\56\0\1\67\21\0\1\70\55\0\1\71\61\0\1\72"+
    "\57\0\1\73\62\0\1\74\57\0\1\75\51\0\1\76"+
    "\33\0\1\77\60\0\1\100\56\0\1\101\55\0\1\102"+
    "\67\0\1\103\65\0\1\104\24\0\1\105\56\0\1\106"+
    "\61\0\1\107\50\0\1\110\71\0\1\111\54\0\1\112"+
    "\35\0\1\113\63\0\1\114\46\0\1\115\66\0\1\116"+
    "\42\0\1\117\56\0\1\120\33\0\1\121\112\0\1\122"+
    "\36\0\1\123\55\0\1\124\52\0\1\125\63\0\1\126"+
    "\26\0\1\127\75\0\1\130\61\0\1\131\21\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3060];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\3\1\4\11\3\1\1\11\3\1\1\11"+
    "\4\1\3\11\1\0\1\1\2\0\1\11\7\0\2\11"+
    "\6\0\1\11\7\0\1\11\10\0\1\11\7\0\1\11"+
    "\1\0\1\1\1\11\5\0\1\11\2\0\1\11\3\0"+
    "\1\11\2\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */
  public _SequentLexer() {
    this((java.io.Reader)null);
  }


  public _SequentLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public _SequentLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 140) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 8: 
          { return SEP_SEMI;
          }
        case 27: break;
        case 15: 
          { return OP_SEQ_DELAY;
          }
        case 28: break;
        case 22: 
          { return COMMENT;
          }
        case 29: break;
        case 17: 
          { return KEY;
          }
        case 30: break;
        case 14: 
          { return KW_AS;
          }
        case 31: break;
        case 10: 
          { return OP_SEQ_FOR;
          }
        case 32: break;
        case 4: 
          { return OP_SEQ_SUFF_CIRC;
          }
        case 33: break;
        case 1: 
          { return com.intellij.psi.TokenType.BAD_CHARACTER;
          }
        case 34: break;
        case 9: 
          { return COLOR_PREF;
          }
        case 35: break;
        case 21: 
          { return KW_DEFINE;
          }
        case 36: break;
        case 19: 
          { return VALUE;
          }
        case 37: break;
        case 23: 
          { return KW_STARTUML;
          }
        case 38: break;
        case 12: 
          { return OP_SEQ_SEPARATOR;
          }
        case 39: break;
        case 13: 
          { return OP_SEQ_SPACE_SUFF_NUM;
          }
        case 40: break;
        case 11: 
          { return OP_SEQ_BCK;
          }
        case 41: break;
        case 26: 
          { return KW_ENDDEFINELONG;
          }
        case 42: break;
        case 6: 
          { return OP_SEQ_PREF2_OPT;
          }
        case 43: break;
        case 3: 
          { return OP_SEQ_SUFF_DOT;
          }
        case 44: break;
        case 18: 
          { return CRLF;
          }
        case 45: break;
        case 25: 
          { return KW_DEFINELONG;
          }
        case 46: break;
        case 7: 
          { return OP_SEQ_SUFF2_OPT;
          }
        case 47: break;
        case 16: 
          { return OP_SEQ_SPACE;
          }
        case 48: break;
        case 20: 
          { return KW_ENDUML;
          }
        case 49: break;
        case 24: 
          { return SEPARATOR;
          }
        case 50: break;
        case 5: 
          { return OP_SEQ_SUFF_X;
          }
        case 51: break;
        case 2: 
          { return com.intellij.psi.TokenType.WHITE_SPACE;
          }
        case 52: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
