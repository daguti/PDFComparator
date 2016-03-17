/*     */ package org.stringtemplate.v4.gui;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextPane;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.JTree;
/*     */ 
/*     */ public class STViewFrame extends JFrame
/*     */ {
/*     */   private JToolBar toolBar1;
/*     */   public JSplitPane treeContentSplitPane;
/*     */   public JSplitPane treeAttributesSplitPane;
/*     */   public JScrollPane treeScrollPane;
/*     */   protected JTree tree;
/*     */   protected JScrollPane attributeScrollPane;
/*     */   protected JTree attributes;
/*     */   public JSplitPane outputTemplateSplitPane;
/*     */   protected JScrollPane scrollPane7;
/*     */   public JTextPane output;
/*     */   public JTabbedPane templateBytecodeTraceTabPanel;
/*     */   private JPanel panel1;
/*     */   private JScrollPane scrollPane3;
/*     */   public JTextPane template;
/*     */   private JScrollPane scrollPane2;
/*     */   public JTree ast;
/*     */   protected JScrollPane scrollPane15;
/*     */   protected JTextPane bytecode;
/*     */   private JScrollPane scrollPane1;
/*     */   public JTextPane trace;
/*     */   public JScrollPane errorScrollPane;
/*     */   protected JList errorList;
/*     */ 
/*     */   public STViewFrame()
/*     */   {
/*  42 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  48 */     this.toolBar1 = new JToolBar();
/*  49 */     this.treeContentSplitPane = new JSplitPane();
/*  50 */     this.treeAttributesSplitPane = new JSplitPane();
/*  51 */     this.treeScrollPane = new JScrollPane();
/*  52 */     this.tree = new JTree();
/*  53 */     this.attributeScrollPane = new JScrollPane();
/*  54 */     this.attributes = new JTree();
/*  55 */     this.outputTemplateSplitPane = new JSplitPane();
/*  56 */     this.scrollPane7 = new JScrollPane();
/*  57 */     this.output = new JTextPane();
/*  58 */     this.templateBytecodeTraceTabPanel = new JTabbedPane();
/*  59 */     this.panel1 = new JPanel();
/*  60 */     this.scrollPane3 = new JScrollPane();
/*  61 */     this.template = new JTextPane();
/*  62 */     this.scrollPane2 = new JScrollPane();
/*  63 */     this.ast = new JTree();
/*  64 */     this.scrollPane15 = new JScrollPane();
/*  65 */     this.bytecode = new JTextPane();
/*  66 */     this.scrollPane1 = new JScrollPane();
/*  67 */     this.trace = new JTextPane();
/*  68 */     this.errorScrollPane = new JScrollPane();
/*  69 */     this.errorList = new JList();
/*     */ 
/*  72 */     Container contentPane = getContentPane();
/*  73 */     contentPane.setLayout(new GridBagLayout());
/*  74 */     ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] { 0, 0 };
/*  75 */     ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] { 0, 0, 0, 0 };
/*  76 */     ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] { 1.0D, 0.0001D };
/*  77 */     ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] { 0.0D, 1.0D, 0.0D, 0.0001D };
/*  78 */     contentPane.add(this.toolBar1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*  84 */     this.treeContentSplitPane.setResizeWeight(0.25D);
/*     */ 
/*  88 */     this.treeAttributesSplitPane.setOrientation(0);
/*  89 */     this.treeAttributesSplitPane.setResizeWeight(0.7D);
/*     */ 
/*  93 */     this.treeScrollPane.setViewportView(this.tree);
/*     */ 
/*  95 */     this.treeAttributesSplitPane.setTopComponent(this.treeScrollPane);
/*     */ 
/*  99 */     this.attributeScrollPane.setViewportView(this.attributes);
/*     */ 
/* 101 */     this.treeAttributesSplitPane.setBottomComponent(this.attributeScrollPane);
/*     */ 
/* 103 */     this.treeContentSplitPane.setLeftComponent(this.treeAttributesSplitPane);
/*     */ 
/* 107 */     this.outputTemplateSplitPane.setOrientation(0);
/* 108 */     this.outputTemplateSplitPane.setResizeWeight(0.7D);
/*     */ 
/* 112 */     this.scrollPane7.setViewportView(this.output);
/*     */ 
/* 114 */     this.outputTemplateSplitPane.setTopComponent(this.scrollPane7);
/*     */ 
/* 121 */     this.panel1.setLayout(new BoxLayout(this.panel1, 0));
/*     */ 
/* 125 */     this.scrollPane3.setViewportView(this.template);
/*     */ 
/* 127 */     this.panel1.add(this.scrollPane3);
/*     */ 
/* 131 */     this.scrollPane2.setViewportView(this.ast);
/*     */ 
/* 133 */     this.panel1.add(this.scrollPane2);
/*     */ 
/* 135 */     this.templateBytecodeTraceTabPanel.addTab("template", this.panel1);
/*     */ 
/* 140 */     this.scrollPane15.setViewportView(this.bytecode);
/*     */ 
/* 142 */     this.templateBytecodeTraceTabPanel.addTab("bytecode", this.scrollPane15);
/*     */ 
/* 147 */     this.scrollPane1.setViewportView(this.trace);
/*     */ 
/* 149 */     this.templateBytecodeTraceTabPanel.addTab("trace", this.scrollPane1);
/*     */ 
/* 152 */     this.outputTemplateSplitPane.setBottomComponent(this.templateBytecodeTraceTabPanel);
/*     */ 
/* 154 */     this.treeContentSplitPane.setRightComponent(this.outputTemplateSplitPane);
/*     */ 
/* 156 */     contentPane.add(this.treeContentSplitPane, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/* 162 */     this.errorScrollPane.setViewportView(this.errorList);
/*     */ 
/* 164 */     contentPane.add(this.errorScrollPane, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/* 167 */     pack();
/* 168 */     setLocationRelativeTo(getOwner());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.gui.STViewFrame
 * JD-Core Version:    0.6.2
 */