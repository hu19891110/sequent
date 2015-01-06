package com.applego.sequent.plugin.action;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by pin on 25.11.14.
 */
public class AddParticipantToDiagram extends SequentAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        logger.debug("Serving ActionEvent: " + e);
    }

    @Override
    protected boolean isEnabled() {
        return false;
    }
}
