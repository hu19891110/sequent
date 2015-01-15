package com.applego.sequent.ui;

import com.intellij.openapi.project.Project;

/**
 * Created by pin on 23.11.14.
 */
public interface PagingDiagramViewer extends DiagramViewer {

    int  getPage();
    void setPage(Project project, int page);

    void prevPage(Project project);
    void nextPage(Project project);
}