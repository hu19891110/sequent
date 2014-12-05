package org.plantuml.idea.action;

import com.applego.sequent.plugin.ui.PagingDiagramViewer;
import com.applego.sequent.plugin.ui.PlantUmlDiagramTool;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.plantuml.idea.util.UIUtils;

/**
 * Author: Eugene Steinberg
 * Date: 2/28/13
 */
public class PrevPageAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getProject();
        if (project != null)
            ((PagingDiagramViewer)UIUtils.getToolWindow(project)).prevPage(project);
    }
}
