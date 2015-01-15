package com.applego.sequent.parser.psi;

import com.applego.sequent.lang.PlantUmlFileType;
import com.applego.sequent.lang.PlantUmlLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by pin on 12.1.15.
 */
public class SequentFile extends PsiFileBase {

    public SequentFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, PlantUmlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PlantUmlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Simple File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }

    @Nullable
    @Override
    public PsiElement findElementAt(int offset) {
        return null;
    }


}
