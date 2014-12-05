package com.applego.sequent.plugin.ui;

import com.applego.sequent.plugin.action.SequentDiagramActionGroup;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.ui.JBSplitter;
import com.intellij.util.messages.MessageBus;
import org.ideaplugins.svgviewer.view.SvgViewerPanel;
import org.jetbrains.annotations.NotNull;
import org.plantuml.idea.lang.PlantUmlFileType;
import org.plantuml.idea.plantuml.PlantUml;
import org.plantuml.idea.toolwindow.ImageViewerPanel;
import org.plantuml.idea.util.UIUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.intellij.codeInsight.completion.CompletionInitializationContext.DUMMY_IDENTIFIER;

/**
 * @author Pavlin Zahariev
 */
public class SequentToolWindow extends JPanel implements PlantumUmlToolsProvider, SourceProvider, ChangeListener {
    private static Logger logger = Logger.getInstance(SequentToolWindow.class);

    private /*static*/Map<Component, Editor> openEditors = new ConcurrentHashMap<Component, Editor>();

    private ToolWindow          toolWindow;
    private ImageViewerPanel    imageViewer;
    private SvgViewerPanel      svgViewer;
    private JTabbedPane         editorTabsPane;
    private JTabbedPane         graphicsTabsPane;

    private FileEditorManagerListener   plantUmlVirtualFileListener = new PlantUmlFileManagerListener();
    private DocumentListener            plantumDocumentListener = new PlantUmlDocumentListener();
    private CaretListener               plantumCaretListener = new PlantUmlCaretListener();
    private AncestorListener            plantumAncestorListener = new PlantUmlAncestorListener();
    private ProjectManagerListener      plantumProjectManagerListener = new PlantUmlProjectManagerListener();

    public SequentToolWindow(Project myProject, ToolWindow toolWindow) {
        super(new BorderLayout());

        setToolWindow(toolWindow);

        UIUtils.addToolWindow(myProject, this);
        UIUtils.addProject(this, myProject);

        setupUI();

        registerListeners(myProject);
    }

    private void setupUI() {
        SequentDiagramActionGroup group = (SequentDiagramActionGroup) ActionManager.getInstance().getAction("Sequent.DiagramActionGroup");
        group.setToolWindow(this);

        final ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, group, true);
        actionToolbar.setTargetComponent(this);
        add(actionToolbar.getComponent(), BorderLayout.PAGE_START);

        editorTabsPane = new JTabbedPane();
        editorTabsPane.addChangeListener(this);

        graphicsTabsPane = new JTabbedPane();
        graphicsTabsPane.addChangeListener(this);

        imageViewer = new ImageViewerPanel(this, PlantUml.ImageFormat.PNG);
        graphicsTabsPane.addTab("Png", imageViewer);
        svgViewer = new SvgViewerPanel(this); // TODO-PZA- Add SVG viewer);
        graphicsTabsPane.addTab("Svg", svgViewer);

        JBSplitter splitter = new JBSplitter(true, 0.5F);
        splitter.setFirstComponent(editorTabsPane);
        splitter.setSecondComponent(graphicsTabsPane);

        add(splitter, BorderLayout.CENTER);
    }

    private void registerListeners(Project myProject) {
        logger.debug("Registering listeners");
        MessageBus messageBus = myProject.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, plantUmlVirtualFileListener);

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(plantumDocumentListener);
        EditorFactory.getInstance().getEventMulticaster().addCaretListener(plantumCaretListener);

        toolWindow.getComponent().addAncestorListener(plantumAncestorListener);

        ProjectManager.getInstance().addProjectManagerListener(plantumProjectManagerListener);

        EditorFactory factory = EditorFactory.getInstance();
        factory.addEditorFactoryListener(new PlantUmlEditorFactoryListener(),
                new Disposable() {
                    @Override
                    public void dispose() {
                        SequentToolWindow.this.disposeMouseListener();
                    }
                });
        // Not needed because at creation time we do not have open PUMLs.
        // Well, once we implement "Open last diagrams at start-up functionality, then may be ...

        editorTabsPane.addChangeListener(this);

        imageViewer.renderLater(myProject);
    }

    private void disposeMouseListener() {
        logger.debug("Lets dispose editor mouse listener");
    }

    private void unregisterListeners() {
        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(plantumDocumentListener);
        EditorFactory.getInstance().getEventMulticaster().removeCaretListener(plantumCaretListener);

        toolWindow.getComponent().removeAncestorListener(plantumAncestorListener);

        ProjectManager.getInstance().removeProjectManagerListener(plantumProjectManagerListener);
    }

    public void addPlant(VirtualFile vf, Project project /*, JScrollPane textScrollPane*/) {
        /*
            FileDocumentManagerImpl.registerDocument(doc, vf);
        */

        FileEditor editor = PsiAwareTextEditorProvider.getInstance().createEditor(project, vf);
        Component newTab = getEditorTabsPane().add(vf.getName(),     editor.getComponent());
        getEditorTabsPane().setSelectedComponent(newTab);

        getEditorTabsPane().setTabComponentAt(getEditorTabsPane().indexOfComponent(newTab), getTitlePanel(getEditorTabsPane(), newTab, vf.getName()));

        // Register editor under component key -> used in "Component (tab) changed" event handling
        openEditors.put(editor.getComponent(), ((TextEditor)editor).getEditor());

        // Refresh diagram graphics
        ((PlantUmlDiagramTool)graphicsTabsPane.getSelectedComponent()).renderLater(project);

        // TODO-PZA: Create in memory Diagram Metadata, Validate References to JavaCode (Use JavaDoc or own Metadata file if exists).
    }

    private JPanel getTitlePanel(final JTabbedPane tabbedPane, final Component panel, String title) {
        final JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLbl = new JLabel(title);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        titlePanel.add(titleLbl);
        TabButton closeButton = new TabButton();//"x");

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.remove(panel);
                //tabbedPane.
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = getEditorTabsPane().indexOfTabComponent(titlePanel);
                if (i != -1) {
                    getEditorTabsPane().remove(i);
                }
            }
        });
        titlePanel.add(closeButton);

        return titlePanel;
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };

    private class TabButton extends JButton { //implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.GRAY);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    @Override
    public String getSelectedSource() {
        String source = "";
        Component tabComponent = editorTabsPane.getSelectedComponent();
        if (tabComponent != null) {
            Editor editor = null;
            try {
                editor = openEditors.get(tabComponent);
                source = editor.getDocument().getText();
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return source;
    }

    @Override
    public String getSelectedSourceWithCaret(Project project) {
        String source = getSelectedSource();

        Editor selectedTextEditor = getSelectedEditor(project);
        if (selectedTextEditor != null) {
            int offset = selectedTextEditor.getCaretModel().getOffset();
            source = getSource(selectedTextEditor, offset);
        }
        return source;
    }

    private Editor getSelectedEditor(Project project) {
        Editor editor = null;
        Component tabComponent = editorTabsPane.getSelectedComponent();
        if (tabComponent != null) {
            editor = openEditors.get(tabComponent);
        }

        return editor; //FileEditorManager.getInstance(project).getSelectedTextEditor();
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

    private class PlantUmlEditorFactoryListener implements EditorFactoryListener {
        @Override
        public void editorCreated(@NotNull EditorFactoryEvent event) {
            Editor editor = event.getEditor();
            Document doc = editor.getDocument();
            final VirtualFile vfile = FileDocumentManager.getInstance().getFile(doc);
            if ( vfile != null ) {
                String fileExtension = vfile.getExtension();
                logger.debug("Opened editor for file with extension: " + fileExtension);

                if(PlantUmlFileType.PLANTUML_EXT.equalsIgnoreCase(fileExtension)) {
                    editor.addEditorMouseListener(
                            new EditorMouseAdapter() {
                                @Override
                                public void mousePressed(EditorMouseEvent e) {
                                    super.mousePressed(e);
                                }

                                @Override
                                public void mouseEntered(EditorMouseEvent e) {
                                    logger.debug("Mouse " + ((e.getMouseEvent().getButton() == 1) ? "left":"right") + " button pressed in PUML editor");
                                }

                                public void mouseClicked(EditorMouseEvent e){
                                    logger.debug("Mouse " + ((e.getMouseEvent().getButton() == 1) ? "left":"right") + " button clicked in PUML editor");
                                    // TODO-PZA-Make selection of message (arrows) work at PUML language level.
                                    MouseEvent mouseEvent = e.getMouseEvent();
                                    Point point = new Point(mouseEvent.getPoint());
                                    Editor editor = e.getEditor();
                                    LogicalPosition pos = editor.xyToLogicalPosition(point);
                                    int offset = editor.logicalPositionToOffset(pos);
                                    int selStart = editor.getSelectionModel().getSelectionStart();
                                    int selEnd = editor.getSelectionModel().getSelectionEnd();
                                    logger.debug("Editor offset: " + offset + ", selStart=" + selStart + ", selEnd=" + selEnd);
                                    editor.getSelectionModel().selectWordAtCaret(true);
                                    // TODO-PZA-If selected word is an actor
                                    //editor.getCaretModel().getCaretOffset();
                                    //file.findReferenceAt(offset)

                                    HintManager.getInstance().showInformationHint(editor, "To to assign class to Actor, press Alt+Enter to display class selector dialog");
                                    //vfile.findReferenceAt();
                                    //editor.getSelectionModel().selectLineAtCaret();
                                    RangeHighlighter[] highliters = editor.getMarkupModel().getAllHighlighters();
                                    logger.debug("highliters = " + highliters);
                                    //int line, int layer, @Nullable TextAttributes textAttributes
                                    int currentLineNumber = editor.getDocument().getLineNumber(offset);
                                    //TextAttributes  textAttributes = editor.getSelectionModel().getTextAttributes();
                                    //editor.getMarkupModel().addLineHighlighter(currentLineNumber, 10, textAttributes);//ContentComponent().getBaseline()CaretModel().getVisualLineEnd())
                                }
                            }
                    );
                }
            }
        }

        @Override
        public void editorReleased(@NotNull EditorFactoryEvent event) {

        }
    }

    private class PlantUmlFileManagerListener implements FileEditorManagerListener {
        public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
            logger.debug("file opened " + file);
        }

        public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
            logger.debug("file closed = " + file);
        }

        public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            logger.debug("selection changed" + event);
            UIUtils.setSelectedEditor(event.getManager().getProject(), event.getManager().getSelectedTextEditor());
            imageViewer.renderLater(event.getManager().getProject());
        }
    }

    private class PlantUmlDocumentListener implements DocumentListener {

        public void beforeDocumentChange(DocumentEvent event) {
        }

        public void documentChanged(DocumentEvent event) {
            logger.debug("document changed " + event);
            //#18 Strange "IntellijIdeaRulezzz" - filter code completion event.
            if (!DUMMY_IDENTIFIER.equals(event.getNewFragment().toString())) {
                Editor[] editors = EditorFactory.getInstance().getEditors(event.getDocument());
                for (Editor editor : editors) {
                     UIUtils.setSelectedEditor(editor.getProject(), editor);
                    // TODO-PZA-Check this is needed: imageViewer.renderLater(editor.getProject());
                }
            }
        }
    }

    private class PlantUmlCaretListener implements CaretListener {
        @Override
        public void caretAdded(CaretEvent caretEvent) {

        }

        @Override
        public void caretRemoved(CaretEvent caretEvent) {

        }

        @Override
        public void caretPositionChanged(final CaretEvent e) {
            Editor editor = e.getEditor();
            Project project = editor.getProject();
            // Must be one of the PlantUml editors to
            if (isEditorOpenInToolWindow(editor)) {
                editor.getSelectionModel().selectWordAtCaret(false);
/*

                PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, editor.getProject());
                PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
                //if (LanguageDescriptor.INSTANCE.keywords.contains(element.getText())) {
                int numDiagrams = 0;
                SourceStringReader reader = new SourceStringReader(element.getText());
                java.util.List<BlockUml> blocks = reader.getBlocks();
                if (blocks.size() > 0) {
                    Diagram system = blocks.get(0).getDiagram();
                    if (system != null) {
                        UmlSource umlSource = system.getSource();
                        numDiagrams = system.getNbImages();
                        //umlSource.getDiagramType()
                        //umlSource.getLine()
                        //umlSource.
                    }
                }
*/
            } else {
                // Editor must be a regular editor, perhaps with Java file opened in it
            }

            //}
            // e.getEditor().getDocument().getSource().get. psiFile.findElementAt(offset)
            //getData(LangDataKeys.PSI_ELEMENT)
            // PsiMethodCallExpression mce = PsiTreeUtil.getParentOfType(psiFile.findElementAt(offset), PsiMethodCallExpression.class);
            //imageViewer.renderLater(project);
        }

    }

    private boolean isEditorOpenInToolWindow(Editor editor) {
        for (Editor anEditor:openEditors.values()) {
            if (editor.equals(anEditor)) {
                return true;
            }
        }
        return false;
    }

    private class PlantUmlAncestorListener implements AncestorListener {
        @Override
        public void ancestorAdded(AncestorEvent ancestorEvent) {
            Project[] projects = ProjectManager.getInstance().getOpenProjects();
            for (Project project : projects) {
                imageViewer.renderLater(project);
            }
        }

        @Override
        public void ancestorRemoved(AncestorEvent ancestorEvent) {
            // do nothing
        }

        @Override
        public void ancestorMoved(AncestorEvent ancestorEvent) {
            // do nothing

        }
    }

    private class PlantUmlProjectManagerListener implements ProjectManagerListener {
        @Override
        public void projectOpened(Project project) {
            logger.debug("opened project " + project);
            registerListeners(project);
        }

        @Override
        public boolean canCloseProject(Project project) {
            return true;
        }

        @Override
        public void projectClosed(Project project) {
            logger.debug("closed project " + project);
        }

        @Override
        public void projectClosing(Project project) {
            UIUtils.removeToolWindow(project);
            unregisterListeners();
        }
    }

    @Override
    public FileEditor getSelectedTextEditor() {
        return (FileEditor)(editorTabsPane.getSelectedComponent());
    }

    @Override
    public PagingDiagramViewer getDiagramViewer() {
        return imageViewer;
    }

    public ToolWindow getToolWindow() {
        return toolWindow;
    }

    public void setToolWindow(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
    }

    public JTabbedPane getEditorTabsPane() {
        return editorTabsPane;
    }

    public void stateChanged(ChangeEvent e) {
        logger.debug("State of" + e.getSource() + " changed ");
        if (e.getSource() instanceof JTabbedPane) {
            Project project = UIUtils.getProject(this);
            JTabbedPane tabsPane = (JTabbedPane)e.getSource();
            Component tabComponent = tabsPane.getSelectedComponent();
            if (tabComponent instanceof PlantUmlDiagramTool) {
                ((PlantUmlDiagramTool) tabComponent).renderLater(project);
            } else if (tabComponent instanceof DataProvider) { // not perfect but ...
                ((PlantUmlDiagramTool) graphicsTabsPane.getSelectedComponent()).renderLater(project);
            }
        }
    }
}
