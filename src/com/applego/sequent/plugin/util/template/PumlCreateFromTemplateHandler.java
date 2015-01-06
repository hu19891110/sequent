package com.applego.sequent.plugin.util.template;

import com.intellij.ide.fileTemplates.DefaultCreateFromTemplateHandler;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsBundle;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.util.IncorrectOperationException;

import java.util.Map;

/**
 * Created by pin on 5.1.15.
 */
public class PumlCreateFromTemplateHandler extends DefaultCreateFromTemplateHandler {

    public static final String CAN_OVERWRITE = "CAN_OVERWRITE";
    /*public static enum FileTemplateProperty {
        Overwrite();

        private String name;
        private String value;
    }*/

    @Override
    public PsiElement createFromTemplate(final Project project, final PsiDirectory directory, String fileName, final FileTemplate template,
                                         final String templateText,
                                         final Map<String, Object> props) throws IncorrectOperationException {
        fileName = checkAppendExtension(fileName, template);

        if (FileTypeManager.getInstance().isFileIgnored(fileName)) {
            throw new IncorrectOperationException("This filename is ignored (Settings | File Types | Ignore files and folders)");
        }

        directory.checkCreateFile(fileName);
        VirtualFile existingFile = directory.getVirtualFile().findChild(fileName);
        if (existingFile != null) {
            boolean showldOverwrite = Boolean.TRUE.equals(props.get(CAN_OVERWRITE));
            if (!showldOverwrite) {
                throw new IncorrectOperationException(VfsBundle.message("file.already.exists.error", existingFile.getPresentableUrl()));
            }
        }

        FileType type = FileTypeRegistry.getInstance().getFileTypeByFileName(fileName);
        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(fileName, type, templateText);

        if (template.isReformatCode()) {
            CodeStyleManager.getInstance(project).reformat(file);
        }

        file = (PsiFile)directory.add(file);
        return file;
    }
}
