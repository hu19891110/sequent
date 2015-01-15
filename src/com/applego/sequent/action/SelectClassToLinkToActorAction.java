package com.applego.sequent.action;

import com.applego.sequent.util.UIUtils;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by pin on 25.11.14.
 */
public class SelectClassToLinkToActorAction extends SequentAction implements IntentionAction {
    public SelectClassToLinkToActorAction() {
        logger.debug("LinkClassInCurrentEditorToActorAction creation time");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        logger.debug("Serving ActionEvent: " + e);
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @NotNull
    @Override
    public String getText() {
        return "Link participant to Java class by annotation";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Comments";
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, Editor editor, PsiFile file) {
        Editor selectedEditor = UIUtils.getSelectedEditor(project);
        if (selectedEditor != null) {
            Document doc = selectedEditor.getDocument();
            if (doc != null) {
                PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(doc);
                if (psiFile != null) {
                    if (JavaLanguage.INSTANCE.equals(psiFile.getLanguage())) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        logger.debug("LinkActorToClassAction invoked ...");
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
