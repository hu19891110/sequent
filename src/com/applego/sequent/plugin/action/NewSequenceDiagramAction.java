package com.applego.sequent.plugin.action;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.impl.FileDocumentManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;

/**
 * Created by pin on 16.11.14.
 */
public class NewSequenceDiagramAction extends SequentAction {
    @Override
    protected boolean isEnabled() {
        return true;
    }

    public void actionPerformed(AnActionEvent e) {
        logger.debug("NewSequenceDiagramAction action is being performed...");

        Project project = e.getData(PlatformDataKeys.PROJECT);
        /*
            ToolWindow toolWindow = e.getData(PlatformDataKeys.TOOL_WINDOW);
            PsiElement psiElement = e.getData(PlatformDataKeys.PSI_ELEMENT);
            Navigatable navigatable = e.getData(PlatformDataKeys.NAVIGATABLE);
            PsiFile psiFile = e.getData(PlatformDataKeys.PSI_FILE);
        */

        // 1. Get Template for Type (Sequence for now)
        // 2. Create Document with template text
        // 3. Create VirtualFile from Document ???
        // Add the plant to the toolWindow


        FileChooserDescriptor fcd = new FileChooserDescriptor(false, true, false, false, false, false);
        VirtualFile vf = FileChooser.chooseFile(fcd, project, project.getBaseDir());

/*
        final Project project2 = CommonDataKeys.PROJECT.getData(dataContext);
        logger.debug("project2 = " + project2);
*/

        DataContext dataContext = e.getDataContext();
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (view == null) {
            return;
        }
        final PsiDirectory dir = view.getOrChooseDirectory();
        if (dir == null || project == null) return;

        String inputText = ""; // TODO-PZA-get form Sequence diagram template

        final EditorFactory factory = EditorFactory.getInstance();
        final Document doc = factory.createDocument(inputText);
/*
        FileDocumentManagerImpl.registerDocument(doc, vf); // ???
*/

        getToolWindow().addPlant(vf, project);

    }
}
