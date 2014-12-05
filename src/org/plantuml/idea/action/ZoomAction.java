package org.plantuml.idea.action;

import com.applego.sequent.plugin.ui.DiagramViewer;
import com.applego.sequent.plugin.ui.PlantUmlDiagramTool;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import org.plantuml.idea.util.UIUtils;

/**
 * @author Eugene Steinberg
 */
public abstract class ZoomAction extends AnAction {
    protected static int DEFAULT_ZOOM = 100;
    protected static int MAX_ZOOM = 500;
    protected static int MIN_ZOOM = 20;
    protected static int ZOOM_STEP = 20;

    protected int getZoom(Project project) {
        DiagramViewer plantUML = (DiagramViewer)UIUtils.getToolWindow(project);
        return plantUML.getZoom();
    }

    protected void setZoom(Project project, int zoom) {
        DiagramViewer plantUML = (DiagramViewer)UIUtils.getToolWindow(project);
        plantUML.setZoom(project, zoom);
    }

}
