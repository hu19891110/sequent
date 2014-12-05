package com.applego.sequent.plugin.action;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.generate.tostring.element.ElementFactory;
import org.plantuml.idea.util.PsiUtils;
import org.plantuml.idea.util.UIUtils;

/**
 * Created by pin on 25.11.14.
 */
public class LinkClassInCurrentEditorToActorAction extends SequentAction implements IntentionAction {
    public LinkClassInCurrentEditorToActorAction() {
        logger.debug("LinkClassInCurrentEditorToActorAction creation time");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        logger.debug("Serving ActionEvent: " + e);
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @NotNull
    @Override
    public String getText() {
        return "Link participant to Java class by annotation";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Comments";
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, Editor editor, PsiFile file) {
        // File is a PUML file
        if (file == null) return false;
        if (!file.isWritable()) return false;

        Editor selectedEditor = UIUtils.getSelectedEditor(project);
        if (selectedEditor != null) {
            Document doc = selectedEditor.getDocument();
            if (doc != null) {
                PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(doc);
                if (psiFile != null) {
                    if (JavaLanguage.INSTANCE.equals(psiFile.getLanguage())) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor pumlEditor, PsiFile pumlFile) throws IncorrectOperationException {
        logger.debug("LinkActorToClassAction invoked ...");

        // See also:
        // JavaPsiFacade.getInstance(project).getElementFactory().createAnnotationFromText("com.example.AnnoName")
        //   On the class ModifierList call com.intellij.psi.PsiModifierList#addAnnotation
        // CommandProcessor.getInstance().executeCommand. (Or use WriteCommandAction
        // ReferenceImporter
        // PsiPolyVariantReference

        PsiFile psiFile = null;
        Editor selectedEditor = UIUtils.getSelectedEditor(project);
        if (selectedEditor != null) {
            Document selectedDoc = selectedEditor.getDocument();
            if (selectedDoc != null) {
                psiFile = PsiDocumentManager.getInstance(project).getPsiFile(selectedDoc);
            }
        }
        if (psiFile instanceof PsiJavaFile) {
            PsiClass psiClass = PsiUtils.findMainPsiClass(psiFile);
            PsiReference psiReference = psiClass.getReference(); //ContainingFile()

            PsiElement psiElement = psiReference.getElement();
            logger.debug("psiElement from class refer3ence = " + psiElement);
            VirtualFile vf = null;
                    //EditorFactory.getInstance()
            FileEditor editor = PsiAwareTextEditorProvider.getInstance().createEditor(project, vf);
            //FileEditorManager.getInstance(project).isFileOpen(); //getSelectedTextEditor();
            //FileEditorManager.getInstance(myProject).getSelectedTextEditor()
            //VirtualFile file = FileDocumentManager.getInstance().getFile(document);

            // FileDocumentManager.getDocument().
            // PsiDocumentManager.getInstance().getDocument() or PsiDocumentManager.getInstance().getCachedDocument()
            // JavaPsiFacade.findClass()

            // PsiJavaFile javaFile = (PsiJavaFile) psiClass.getContaningFile();
            // PsiPackage pkg = JavaPsiFacade.getInstance(project).findPackage(javaFile.getPackageName());
        }
        annotateClassAsActor(project, pumlEditor, pumlFile, psiFile);
    }
/*
    public static VirtualFile getSelectedFile(Project myProject) {
        Editor selectedTextEditor = FileEditorManager.getInstance(myProject).getSelectedTextEditor();
        VirtualFile file = null;
        if (selectedTextEditor != null) {
            final Document document = selectedTextEditor.getDocument();
            file = FileDocumentManager.getInstance().getFile(document);
        }
        return file;
    }*/
    private void annotateClassAsActor(Project project, Editor pumlEditor, PsiFile pumlFile, PsiFile psiFile) {
        if (!psiFile.isWritable()) {
            // TODO-PZA: Stop action execution and display error message
        } else {
            if (psiFile instanceof PsiJavaFile) {
                pumlEditor.getSelectionModel().selectWordAtCaret(false);
                String actorName = pumlEditor.getSelectionModel().getSelectedText();
                String javadoc = "/**\n @Participant(diagram=" + pumlFile.getName() + ",participant=" + actorName + "\n*/";

                PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
                PsiDocComment comment1 = elementFactory.createDocCommentFromText("/**\nThisisasimplecomment\n*/");
                PsiDocComment comment2 = elementFactory.createDocCommentFromText(javadoc);

                PsiClass psiClass = PsiUtils.findMainPsiClass(psiFile);
                if (psiClass != null) {
                    PsiDocComment docComment = psiClass.getDocComment();
                    if (docComment != null) {
                        PsiDocTag[] tags = docComment.getTags();
                        if ((tags != null) && (tags.length > 0)) {
                            docComment.addAfter(comment2, tags[tags.length - 1]);
                        }

                        docComment.addBefore(comment1, psiClass.getDocComment().getLastChild());
                        docComment.addBefore(comment2.getTags()[0].getNameElement(), psiClass.getDocComment().getLastChild());
                    } else {
                        PsiElement parent = psiClass.getModifierList();
                        PsiModifierListOwner element = (PsiModifierListOwner)parent.getParent();
                        //PsiDocComment docCommentModifiers = ((PsiDocCommentOwner)element).getDocComment();
                        docComment = (PsiDocComment)element.addBefore(comment1, parent);
                        //logger.debug("" + docCommentModifiers);
                    }
                    logger.debug("docComment = " + docComment);
                }
            } else {
                // TODO-PZA: Find out what to do if this is not a Java File. Support other languages? Filter action availability for Java only (then never reach this point)?
            }
        }
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
