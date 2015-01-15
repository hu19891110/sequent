package com.applego.sequent.action;

import com.applego.sequent.ui.SequentToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

/**
 * Created by pin on 25.11.14.
 */
public class SequentMarkupActionGroup extends DefaultActionGroup {

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

    @Override
    public void update(AnActionEvent e) {
        // TODO-PZA
        // 1. Get current tool window editor document source
        // 2. Find selection
        // If selection is Actor -> enable/show Actor related actions
        // If selection is message or whole line -> enable/show Message related actions
        super.update(e);
    }

    public SequentToolWindow getToolWindow() {
        return toolWindow;
    }
}
