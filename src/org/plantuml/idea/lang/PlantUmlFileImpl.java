package org.plantuml.idea.lang;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.source.PsiPlainTextFileImpl;
import org.jetbrains.annotations.NotNull;

/**
 * @author Eugene Steinberg
 */
public class PlantUmlFileImpl extends PsiPlainTextFileImpl {

    public PlantUmlFileImpl(FileViewProvider viewProvider) {
        super(viewProvider);//, PlantUmlLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return PlantUmlFileType.PLANTUML_FILE_TYPE;
    }
}
