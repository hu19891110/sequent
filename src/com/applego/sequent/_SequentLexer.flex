package com.applego.sequent;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.applego.sequent.parser.psi.SequentTypes.*;

%%

%{
  public _SequentLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _SequentLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+


%%
<YYINITIAL> {
  {WHITE_SPACE}         { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "->"                  { return OP_SEQ_FOR; }
  "<-"                  { return OP_SEQ_BCK; }
  "-"                   { return OP_SEQ_SUFF_DOT; }
  "o"                   { return OP_SEQ_SUFF_CIRC; }
  "x"                   { return OP_SEQ_SUFF_X; }
  "["                   { return OP_SEQ_PREF2_OPT; }
  "]"                   { return OP_SEQ_SUFF2_OPT; }
  "=="                  { return OP_SEQ_SEPARATOR; }
  "..."                 { return OP_SEQ_DELAY; }
  "|||"                 { return OP_SEQ_SPACE; }
  "||"                  { return OP_SEQ_SPACE_SUFF_NUM; }
  ";"                   { return SEP_SEMI; }
  "@startuml"           { return KW_STARTUML; }
  "@enduml"             { return KW_ENDUML; }
  "!define"             { return KW_DEFINE; }
  "!definelong"         { return KW_DEFINELONG; }
  "!enddefinelong"      { return KW_ENDDEFINELONG; }
  "#"                   { return COLOR_PREF; }
  "as"                  { return KW_AS; }
  "COMMENT"             { return COMMENT; }
  "CRLF"                { return CRLF; }
  "KEY"                 { return KEY; }
  "SEPARATOR"           { return SEPARATOR; }
  "VALUE"               { return VALUE; }


  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
