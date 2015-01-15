package com.applego.sequent.action;

import com.applego.sequent.ui.SequentToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

/**
 * Created by pin on 16.11.14.
 */
public class SequentDiagramActionGroup extends DefaultActionGroup {

    private SequentToolWindow toolWindow;

    public void setToolWindow(SequentToolWindow toolWindow) {
        AnAction[] actions =getChildActionsOrStubs();
        for (AnAction action:actions) {
            if (action instanceof SequentAction) {
                ((SequentAction)action).setToolWindow(toolWindow);
            }
        }
        this.toolWindow = toolWindow;
    }

    public SequentToolWindow getToolWindow() {
        return toolWindow;
    }
}
