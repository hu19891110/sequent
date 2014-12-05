package com.applego.sequent.plugin.action;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by pavlin on 6.5.2014.
 */
public class AddMethodToSequenceDiagram extends SequentAction {
    @Override
    protected boolean isEnabled() {
        return true;
    }

    public void actionPerformed(AnActionEvent e) {
        logger.debug("addMethodToSequenceDiagram action is being performed...");
    }
}
