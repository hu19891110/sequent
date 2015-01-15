// This is a generated file. Not intended for manual editing.
package com.applego.sequent.parser.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.applego.sequent.parser.psi.impl.*;

public interface SequentTypes {

  IElementType PROPERTY = new SequentElementType("PROPERTY");

  IElementType COLOR_PREF = new SequentTokenType("#");
  IElementType COMMENT = new SequentTokenType("COMMENT");
  IElementType CRLF = new SequentTokenType("CRLF");
  IElementType KEY = new SequentTokenType("KEY");
  IElementType KW_AS = new SequentTokenType("as");
  IElementType KW_DEFINE = new SequentTokenType("!define");
  IElementType KW_DEFINELONG = new SequentTokenType("!definelong");
  IElementType KW_ENDDEFINELONG = new SequentTokenType("!enddefinelong");
  IElementType KW_ENDUML = new SequentTokenType("@enduml");
  IElementType KW_STARTUML = new SequentTokenType("@startuml");
  IElementType OP_SEQ_BCK = new SequentTokenType("<-");
  IElementType OP_SEQ_DELAY = new SequentTokenType("...");
  IElementType OP_SEQ_FOR = new SequentTokenType("->");
  IElementType OP_SEQ_PREF2_OPT = new SequentTokenType("[");
  IElementType OP_SEQ_SEPARATOR = new SequentTokenType("==");
  IElementType OP_SEQ_SPACE = new SequentTokenType("|||");
  IElementType OP_SEQ_SPACE_SUFF_NUM = new SequentTokenType("||");
  IElementType OP_SEQ_SUFF2_OPT = new SequentTokenType("]");
  IElementType OP_SEQ_SUFF_CIRC = new SequentTokenType("o");
  IElementType OP_SEQ_SUFF_DOT = new SequentTokenType("-");
  IElementType OP_SEQ_SUFF_X = new SequentTokenType("x");
  IElementType SEPARATOR = new SequentTokenType("SEPARATOR");
  IElementType SEP_SEMI = new SequentTokenType(";");
  IElementType VALUE = new SequentTokenType("VALUE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == PROPERTY) {
        return new SequentPropertyImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
