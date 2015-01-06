package com.applego.sequent.plugin.util;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pin on 31.12.14.
 */
public class UIUtils {
    private static Map<Component, Project> projectsMap = new ConcurrentHashMap<Component, Project>();
    private static Map<Project, Editor> selectedEditors = new ConcurrentHashMap<Project, Editor>();

    public static Editor getSelectedEditor(Project project) {
        return selectedEditors.get(project);
    }

    public static void setSelectedEditor(Project project, Editor editor) {
        selectedEditors.put(project, editor);
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


}
