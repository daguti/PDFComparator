/*     */ package org.antlr.gunit.swingui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JToolBar;
/*     */ 
/*     */ public class WorkSpaceView extends JFrame
/*     */ {
/*     */   protected JSplitPane splitListClient;
/*     */   protected JTabbedPane tabEditors;
/*     */   protected JPanel paneToolBar;
/*     */   protected StatusBarController paneStatus;
/*     */   protected TestCaseEditController paneEditor;
/*     */   protected JToolBar toolbar;
/*     */   protected JTextArea txtEditor;
/*     */   protected RuleListController listRules;
/*     */   protected JMenuBar menuBar;
/*     */   protected JScrollPane scrollCode;
/*     */   protected JPanel resultPane;
/*     */   protected JButton btnOpenGrammar;
/*     */ 
/*     */   protected void initComponents()
/*     */   {
/*  65 */     this.paneEditor = new TestCaseEditController(this);
/*  66 */     this.paneStatus = new StatusBarController();
/*     */ 
/*  68 */     this.toolbar = new JToolBar();
/*  69 */     this.toolbar.setBorder(BorderFactory.createEmptyBorder());
/*  70 */     this.toolbar.setFloatable(false);
/*  71 */     this.toolbar.setBorder(BorderFactory.createEmptyBorder());
/*     */ 
/*  73 */     this.txtEditor = new JTextArea();
/*  74 */     this.txtEditor.setLineWrap(false);
/*  75 */     this.txtEditor.setFont(new Font("Courier New", 0, 13));
/*  76 */     this.scrollCode = new JScrollPane(this.txtEditor, 22, 30);
/*     */ 
/*  79 */     this.scrollCode.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
/*     */ 
/*  81 */     this.tabEditors = new JTabbedPane();
/*  82 */     this.tabEditors.addTab("Case Editor", ImageFactory.getSingleton().TEXTFILE16, this.paneEditor.getView());
/*  83 */     this.tabEditors.addTab("Script Source", ImageFactory.getSingleton().WINDOW16, this.scrollCode);
/*     */ 
/*  85 */     this.listRules = new RuleListController();
/*     */ 
/*  87 */     this.splitListClient = new JSplitPane(1, this.listRules.getView(), this.tabEditors);
/*     */ 
/*  89 */     this.splitListClient.setResizeWeight(0.4D);
/*  90 */     this.splitListClient.setBorder(BorderFactory.createEmptyBorder());
/*     */ 
/*  94 */     getContentPane().add(this.toolbar, "North");
/*  95 */     getContentPane().add(this.splitListClient, "Center");
/*  96 */     getContentPane().add(this.paneStatus.getView(), "South");
/*     */ 
/*  99 */     setPreferredSize(new Dimension(900, 500));
/* 100 */     setDefaultCloseOperation(3);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.WorkSpaceView
 * JD-Core Version:    0.6.2
 */