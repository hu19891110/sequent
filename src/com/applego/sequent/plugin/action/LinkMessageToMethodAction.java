package com.applego.sequent.plugin.action;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by pin on 25.11.14.
 */
public class LinkMessageToMethodAction extends SequentAction implements IntentionAction {
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
        return "Assign to message a method from class linked to target participant";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Comments";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
     /*
        ChooseByNamePopupComponent chooseByNamePopupComponent = ChooseByNameFactory.getInstance(project).createChooseByNamePopupComponent(new GotoSymbolModel2(project));
        chooseByNamePopupComponent.invoke(new ChooseByNamePopupComponent.Callback() {
            @Override
            public void elementChosen(Object element) {
                // got it
                logger.debug("" + element.toString());
            }

        }, ModalityState.current(), false);
     */

    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
