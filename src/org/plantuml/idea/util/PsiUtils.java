package org.plantuml.idea.util;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * Created by pin on 27.11.14.
 */
public class PsiUtils {

    public static PsiClass findMainPsiClass(final PsiFile psiFile) {
        PsiClass psiClass = null;

        PsiJavaFile psiJavaFile= null;
        if (JavaLanguage.INSTANCE.equals(psiFile.getLanguage())) {
            psiJavaFile = (PsiJavaFile) psiFile;
        }

        final String fileName = psiJavaFile.getName();
        final int prefixIndex = fileName.indexOf((int)'.');
        final String filenameWithoutPrefix = fileName.substring(0, prefixIndex);

        final PsiClass[] classes = psiJavaFile.getClasses();
        for (PsiClass aPsiClass : classes) {
            final String className = aPsiClass.getName();
            if (filenameWithoutPrefix.equals(className)) {
                return psiClass = aPsiClass;
            }
        }
        return psiClass;
    }
}
