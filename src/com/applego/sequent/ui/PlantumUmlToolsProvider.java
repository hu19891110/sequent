package com.applego.sequent.ui;

import com.intellij.openapi.fileEditor.FileEditor;

/**
 * Created by pin on 16.11.14.
 */
public interface PlantumUmlToolsProvider {
    FileEditor getSelectedTextEditor();
    PagingDiagramViewer getDiagramViewer();
}
