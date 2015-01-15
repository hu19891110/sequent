package com.applego.sequent.ui;

import com.intellij.openapi.project.Project;

/**
 * Created by pin on 23.11.14.
 */
public interface DiagramViewer {

    int  getZoom();
    void setZoom(Project project, int zoom);
}