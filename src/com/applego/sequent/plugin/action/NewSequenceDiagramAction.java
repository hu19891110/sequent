package com.applego.sequent.plugin.action;

import com.applego.sequent.plugin.util.template.PumlCreateFromTemplateHandler;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Properties;

/**
 * Created by pin on 16.11.14.
 */
public class NewSequenceDiagramAction extends SequentAction {
    final static String defaultTemplateName = "/templates/UMLSequence.puml.ft";
    final static String templateName = "UML Sequence";

    @Override
    protected boolean isEnabled() {
        return true;
    }

    public void actionPerformed(AnActionEvent e) {
        logger.debug("NewSequenceDiagramAction action is being performed...");

        Project  project = e.getData(PlatformDataKeys.PROJECT);
        FileType puml = FileTypeManager.getInstance().getFileTypeByExtension("puml");
        FileType realFileType = puml == UnknownFileType.INSTANCE ? FileTypes.PLAIN_TEXT : puml;
        PsiDirectory baseDir = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
        // From a VirtualFile: PsiManager.getInstance(project).findFile()
        // From a Document: PsiDocumentManager.getInstance(project).getPsiFile()

        //athManager.getSystemPath() + File.separatorChar + "extResources";
        //final File dir = new File(myResourcePath);
        //dir.mkdirs();

        // Prepare file name
        boolean fileNameValid = false;
        Boolean canOverwrite = Boolean.TRUE;
        String fileName = "sequence." + realFileType.getDefaultExtension();
        while (!fileNameValid) {
            fileName = Messages.showInputDialog(project,
                    "New diagram name",
                    "New diagram",
                    null,
                    fileName,
                    null
            );

            if (fileName == null) {
                logger.debug("User stopped new diagram creation by canceling file name input dialog.");
                return;
            }

            if (!"".equals(fileName)) {
                // TODO-PZA: Use Settings: Diagrams folder or Usecurrently edited class folder(ifany).
                PsiFile existingFile = baseDir.findFile(fileName);
                if (existingFile != null) {
                //}
                //DOESNT WORK:  if (FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.projectScope(project)) == null) {
                //              if (LocalFileSystem.getInstance().findFileByPath(baseDir.getText() + File.separator + fileName) != null) {
                    int reaction = Messages.showYesNoCancelDialog(project,
                            "File already exists. Overwrite?",
                            "New Sequence Diagram",
                            "Yes",
                            "No",
                            "Cancel",
                            Messages.getQuestionIcon());
                    if (reaction == 0) {
                        // YES - replace
                        canOverwrite = Boolean.TRUE;
                        existingFile.delete();
                        break;
                    } else if (reaction == 1) {
                        // NO - DoNot replace,ask for new name
                    } else if (reaction == 2) {
                        //CANCEL - Stop askin adndontcreate new diagram
                        // TODO-PZA: Continue asking for new file name
                        fileName = null;
                        fileNameValid = true;
                    }
                } else {
                    fileNameValid = true;
                }
            }
        }
        if (fileName == null) {
            return;
        }

        Properties properties = new Properties(FileTemplateManager.getInstance().getDefaultProperties());
        properties.put(PumlCreateFromTemplateHandler.CAN_OVERWRITE, canOverwrite);
        // TODO-PZA: Add properties as needed
        final FileTemplate template = FileTemplateManager.getInstance().getTemplate(templateName);
        if (template == null) {
            // TODO-PZA: create/insert template if missing
        }
        JavaTemplateUtil.setPackageNameAttribute(properties, baseDir);

        // Create Psi file from template
        PsiElement  psiElement = null;
        try {
            psiElement = FileTemplateUtil.createFromTemplate(template, fileName, properties, baseDir);
            logger.debug("PsiElement.text=" + psiElement.getText());
        } catch (Exception e1) {
            Messages.showErrorDialog(project, e1.getMessage(), "New Sequence");
            e1.printStackTrace();
        }

        // Add the plant to the toolWindow
        getToolWindow().addPlant(((PsiFile)psiElement).getVirtualFile(), project);
    }
}
