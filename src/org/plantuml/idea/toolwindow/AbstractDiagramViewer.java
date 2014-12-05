package org.plantuml.idea.toolwindow;

import com.applego.sequent.plugin.ui.PlantUmlDiagramTool;
import com.applego.sequent.plugin.ui.SourceProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import org.plantuml.idea.action.SelectPageAction;
import org.plantuml.idea.util.LazyApplicationPoolExecutor;
import org.plantuml.idea.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by pin on 2.12.14.
 */
public abstract class AbstractDiagramViewer extends JPanel implements PlantUmlDiagramTool {
    private SourceProvider sourceProvider;
    private int page = 0;
    private int numPages = 1;
    private LazyApplicationPoolExecutor lazyExecutor = new LazyApplicationPoolExecutor();
    private SelectPageAction selectPageAction;

    protected AbstractDiagramViewer(LayoutManager layout, SourceProvider sourceProvider) {
        super(layout);

        this.sourceProvider = sourceProvider;
    }

    protected boolean isProjectValid(Project project) {
        return project != null && !project.isDisposed();
    }

    public void renderLater(final Project project) {
        if (project == null) return;

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!isProjectValid(project))
                    return;
                final String source = sourceProvider.getSelectedSourceWithCaret(project);
                if (!renderRequired(source))
                    return;
                final File selectedDir = UIUtils.getSelectedDir(project);
                lazyExecutor.execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                renderWithBaseDir(project, source, selectedDir, page);
                            }
                        }
                );
            }
        });
    }

    public int getPage() {
        return page;
    }

    public void setPage(Project myProject, int page) {
        if (page >= 0 && page < numPages) {
            this.page = page;
            selectPageAction.setPage(page);
            renderLater(myProject);
        }
    }


    public void nextPage(Project myProject) {
        setPage(myProject, this.page + 1);
    }

    public void prevPage(Project myProject) {
        setPage(myProject, this.page - 1);
    }


    public void setNumPages(Project myProject, int numPages) {
        this.numPages = numPages;
        if (page >= numPages)
            setPage(myProject, numPages - 1);
        selectPageAction.setNumPages(numPages);
    }

    public void setSelectPageAction(SelectPageAction selectPageAction) {
        this.selectPageAction = selectPageAction;
    }
}
