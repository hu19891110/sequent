package com.applego.sequent.plugin.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by pin on 16.11.14.
 */
public class OpenSequenceDiagramAction extends SequentAction {
    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        logger.debug("OpenSequenceDiagramAction performs ...");

        Project myProject = anActionEvent.getData(PlatformDataKeys.PROJECT);
        FileChooserDescriptor fcd = new FileChooserDescriptor(true, false, false, false, false, false);
        VirtualFile vf = FileChooser.chooseFile(fcd, myProject, null);
        if (vf != null) {
            logger.debug("VF returned: " + vf.getName());
        }

        getToolWindow().addPlant(vf, myProject);
    }
}
