package com.applego.sequent.ui;

import com.intellij.openapi.project.Project;

/**
 * Created by pin on 23.11.14.
 */
public interface SourceProvider {

    String getSelectedSource();

    String getSelectedSourceWithCaret(Project project);
}
