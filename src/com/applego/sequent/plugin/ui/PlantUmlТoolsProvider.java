package com.applego.sequent.plugin.ui;

import com.intellij.openapi.fileEditor.FileEditor;

/**
 * Created by pin on 16.11.14.
 */
public interface PlantUmlТoolsProvider {
    FileEditor getSelectedTextEditor();
    PlantUmlDiagramTool getDiagramTool();
}
