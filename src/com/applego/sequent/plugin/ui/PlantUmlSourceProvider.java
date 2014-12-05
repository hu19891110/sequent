package com.applego.sequent.plugin.ui;

import com.intellij.openapi.project.Project;

/**
 * Created by pin on 23.11.14.
 */
public interface PlantUmlSourceProvider {
    String getSelectedSource();

    String getSelectedSourceWithCaret(Project project);
}
