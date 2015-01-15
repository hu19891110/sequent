package com.applego.sequent.ui;

import com.intellij.openapi.project.Project;

import java.io.File;

/**
 * Created by pin on 23.11.14.
 */
public interface PlantUmlDiagramTool {
    boolean renderRequired(String newSource);

    void    renderLater(Project project);
    void    renderWithBaseDir(Project myProject, String source, File baseDir, int pageNum);

}