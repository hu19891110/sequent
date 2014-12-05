package org.ideaplugins.svgviewer.controller.project;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBusConnection;
import org.ideaplugins.svgviewer.Helpers;
import org.ideaplugins.svgviewer.controller.application.SvgConfiguration;
import org.ideaplugins.svgviewer.view.SvgViewerPanel;
import org.jetbrains.annotations.NotNull;

public class SvgViewerProjectComponentEmbed implements ProjectComponent {


    public static final String ID_TOOL_WINDOW = "SvgViewerEmbed";
    private Project _project;
    private FileListener _fileListener;
    private SvgViewerPanel _viewerPanel;
    private static final String PROJECT_COMPONENT_NAME = "SvgViewerProjectComponentEmbed";
    private MessageBusConnection _connection;


    public SvgViewerProjectComponentEmbed(Project project) {
        this._project = project;
    }



    @NotNull
    public String getComponentName() {
        return SvgViewerProjectComponentEmbed.ID_TOOL_WINDOW + "." + SvgViewerProjectComponentEmbed.PROJECT_COMPONENT_NAME;
    }



    public void initComponent() {
    }



    public void projectOpened() {
        if (SvgConfiguration.getInstance().isPluginEnabled()) {
            initToolWindow();
        }
    }



    public void projectClosed() {
        unregisterToolWindow();
    }



    public void disposeComponent() {
    }



    public void initToolWindow() {
        this._viewerPanel = new SvgViewerPanel(null/*PZA-Will throw NPE*/);

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(this._project);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow(SvgViewerProjectComponentEmbed.ID_TOOL_WINDOW, false, ToolWindowAnchor.RIGHT);
        final Content content = ContentFactory.SERVICE.getInstance().createContent(getViewerPanel(), "", false);
        content.setCloseable(false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.setIcon(Helpers.ICON_TOOL_WINDOW);
        getViewerPanel().getCanvas().setToolWindow(toolWindow);

        this._connection = this._project.getMessageBus().connect();
        this._connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new EditorListener(getViewerPanel()));

        this._fileListener = new FileListener(getViewerPanel());
        VirtualFileManager.getInstance().addVirtualFileListener(this._fileListener);
    }



    public void unregisterToolWindow() {
        if (this._connection != null) {
            this._connection.disconnect();
        }
        if (this._fileListener != null) {
            VirtualFileManager.getInstance().removeVirtualFileListener(this._fileListener);
        }
        if (isToolWindowRegistered()) {
            ToolWindowManager.getInstance(this._project).unregisterToolWindow(SvgViewerProjectComponentEmbed.ID_TOOL_WINDOW);
        }
    }



    private boolean isToolWindowRegistered() {
        return ToolWindowManager.getInstance(this._project).getToolWindow(SvgViewerProjectComponentEmbed.ID_TOOL_WINDOW) != null;
    }



    public static SvgViewerProjectComponentEmbed getInstance(Project project) {
        return project.getComponent(SvgViewerProjectComponentEmbed.class);
    }



    public SvgViewerPanel getViewerPanel() {
        return this._viewerPanel;
    }
}