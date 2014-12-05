package org.plantuml.idea.util;

import com.applego.sequent.plugin.ui.PlantumUmlToolsProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.plantuml.idea.plantuml.PlantUml;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eugene Steinberg
 */
public class UIUtils {
    private static Map<Project, PlantumUmlToolsProvider> windowMap = new ConcurrentHashMap<Project, PlantumUmlToolsProvider>();
    private static Map<Component, Project> projectsMap = new ConcurrentHashMap<Component, Project>();
    private static Map<Project, Editor> selectedEditors = new ConcurrentHashMap<Project, Editor>();

    public static BufferedImage getBufferedImage(byte[] imageBytes) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(input);
    }

    /**
     * Scales the image and sets it to label
     *
     * @param image source image
     * @param label destination label
     * @param zoom  zoom factor
     */
    public static void setImage(@NotNull BufferedImage image, JLabel label, int zoom) {
        int newWidth;
        int newHeight;
        Image scaledImage;

        if (zoom == 100) { // default zoom, no scaling
            newWidth = image.getWidth();
            newHeight = image.getHeight();
            scaledImage = image;
        } else {
            newWidth = Math.round(image.getWidth() * zoom / 100.0f);
            newHeight = Math.round(image.getHeight() * zoom / 100.0f);
            scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        }

        ImageIcon imageIcon = new ImageIcon(scaledImage);
        label.setIcon(imageIcon);
        label.setPreferredSize(new Dimension(newWidth, newHeight));
    }

    public static String getSelectedSourceWithCaret(Project myProject) {
        String source = ""; //getSelectedSource(myProject);

        Editor selectedTextEditor = getSelectedEditor(myProject);
        if (selectedTextEditor != null) {
            int offset = selectedTextEditor.getCaretModel().getOffset();
            source = getSource(selectedTextEditor, offset);
        }
        return source;
    }

    private static String getSource(Editor selectedTextEditor, int offset) {
        String source;
        final Document document = selectedTextEditor.getDocument();
        if (offset > 0) {
            source = PlantUml.extractSource(document.getText(), offset);
        } else {
            source = selectedTextEditor.getDocument().getText();
        }
        return source;
    }

    public static String getSelectedSource(Project myProject) {
        String source = "";
        Editor selectedTextEditor = getSelectedEditor(myProject);
        if (selectedTextEditor != null) {
            final Document document = selectedTextEditor.getDocument();
            source = document.getText();
        }
        return source;
    }

    public static VirtualFile getSelectedFile(Project myProject) {
        Editor selectedTextEditor = getSelectedEditor(myProject);
        VirtualFile file = null;
        if (selectedTextEditor != null) {
            final Document document = selectedTextEditor.getDocument();
            file = FileDocumentManager.getInstance().getFile(document);
        }
        return file;
    }

    public static File getSelectedDir(Project myProject) {
        Editor selectedTextEditor = getSelectedEditor(myProject);
        File baseDir = null;
        if (selectedTextEditor != null) {

            final Document document = selectedTextEditor.getDocument();
            final VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            if (file != null) {
                VirtualFile parent = file.getParent();
                if (parent != null && parent.isDirectory()) {
                    baseDir= new File(parent.getPath());
                }
            }
        }
        return baseDir;
    }

    public static PlantumUmlToolsProvider getToolWindow(Project project) {
        return windowMap.get(project);
    }

    public static void addToolWindow(Project project, PlantumUmlToolsProvider toolWindow) {
        windowMap.put(project, toolWindow);
    }

    public static void removeToolWindow(Project project) {
        windowMap.remove(project);
    }

    public static Project getProject(Component key) {
        return projectsMap.get(key);
    }

    public static void addProject(Component key, Project project) {
        projectsMap.put(key, project);
    }

    public static void removeProject(Component key) {
        projectsMap.remove(key);
    }

/*
    private static Editor getSelectedEditor(Project myProject) {
        return FileEditorManager.getInstance(myProject).getSelectedTextEditor();
    }
*/
    public static Editor getSelectedEditor(Project project) {
        return selectedEditors.get(project);
    }

    public static void setSelectedEditor(Project project, Editor editor) {
        selectedEditors.put(project, editor);
    }
}
