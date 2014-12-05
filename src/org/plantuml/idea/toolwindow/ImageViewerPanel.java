package org.plantuml.idea.toolwindow;

import com.applego.sequent.plugin.ui.PagingDiagramViewer;
import com.applego.sequent.plugin.ui.SourceProvider;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.plantuml.idea.action.SelectPageAction;
import org.plantuml.idea.plantuml.PlantUml;
import org.plantuml.idea.plantuml.PlantUmlResult;
import org.plantuml.idea.util.LazyApplicationPoolExecutor;
import org.plantuml.idea.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Eugene Steinberg
 */
public class ImageViewerPanel extends AbstractDiagramViewer implements PagingDiagramViewer {
    private static Logger logger = Logger.getInstance(ImageViewerPanel.class);

    private JLabel imageLabel;

    private int zoom = 100;

    private String cachedSource = "";
    private int cachedPage = 0;
    private int cachedZoom = zoom;

    private PlantUml.ImageFormat imageFormat;

    public ImageViewerPanel(SourceProvider umlProvider) {
        this(umlProvider, PlantUml.ImageFormat.PNG);
    }

    public ImageViewerPanel(SourceProvider sourceProvider, PlantUml.ImageFormat imageFormat) {
        super(new BorderLayout(), sourceProvider);

        this.imageFormat = imageFormat;

        setupUI();
    }

    private void setupUI() {
        ActionGroup group = (ActionGroup) ActionManager.getInstance().getAction("PlantUML.Toolbar");
        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, group, true);
        actionToolbar.setTargetComponent(this);
        add(actionToolbar.getComponent(), BorderLayout.PAGE_START);

        imageLabel = new JLabel();

        JScrollPane scrollPane = new JBScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);

        setSelectPageAction((SelectPageAction) ActionManager.getInstance().getAction("PlantUML.SelectPage"));
    }

    public boolean renderRequired(String newSource) {
        if (newSource.isEmpty())
            return false;
        if (!newSource.equals(cachedSource) || getPage() != cachedPage || zoom != cachedZoom) {
            cachedSource = newSource;
            cachedPage = getPage();
            cachedZoom = zoom;
            return true;
        }
        return false;
    }

    public void renderWithBaseDir(Project myProject, String source, File baseDir, int pageNum) {
        if (source.isEmpty())
            return;
        PlantUmlResult result = PlantUml.render(source, baseDir, imageFormat, pageNum);
        try {
            final BufferedImage image = UIUtils.getBufferedImage(result.getDiagramBytes());
            if (image != null) {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    public void run() {
                        UIUtils.setImage(image, imageLabel, zoom);
                    }
                });
            }
            setNumPages(myProject, result.getPages());
        } catch (Exception e) {
            logger.warn("Exception occurred rendering source = " + source + ": " + e);
        }
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(Project myProject, int zoom) {
        this.zoom = zoom;
        renderLater(myProject);
    }
}

