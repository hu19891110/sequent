// This is a generated file. Not intended for manual editing.
package com.applego.sequent.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.applego.sequent.parser.psi.SequentTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.applego.sequent.parser.psi.*;

public class SequentPropertyImpl extends ASTWrapperPsiElement implements SequentProperty {

  public SequentPropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SequentVisitor) ((SequentVisitor)visitor).visitProperty(this);
    else super.accept(visitor);
  }

}
