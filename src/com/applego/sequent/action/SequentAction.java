package com.applego.sequent.action;

import com.applego.sequent.ui.SequentToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;

/**
 * Created by pavlin on 30.4.2014.
 */
public abstract class SequentAction extends AnAction {
    protected Logger logger = Logger.getInstance("sequentLogger");
    private SequentToolWindow toolWindow;

    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(isEnabled());
    }

    protected abstract boolean isEnabled();

    public void setToolWindow(SequentToolWindow toolWindow) {
        this.toolWindow = toolWindow;
    }

    public SequentToolWindow getToolWindow() {
        return toolWindow;
    }
}
