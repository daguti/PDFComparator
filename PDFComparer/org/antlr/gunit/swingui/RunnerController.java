/*     */ package org.antlr.gunit.swingui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ import org.antlr.gunit.swingui.model.Rule;
/*     */ import org.antlr.gunit.swingui.model.TestCase;
/*     */ import org.antlr.gunit.swingui.model.TestSuite;
/*     */ 
/*     */ public class RunnerController
/*     */   implements IController
/*     */ {
/*  62 */   private RunnerView view = new RunnerView();
/*     */ 
/*     */   public Object getModel()
/*     */   {
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   public Component getView() {
/*  94 */     return this.view;
/*     */   }
/*     */ 
/*     */   public void update() {
/*  98 */     this.view.initComponents();
/*     */   }
/*     */ 
/*     */   public void OnShowSuiteResult(TestSuite suite) {
/* 102 */     update();
/* 103 */     this.view.tree.setModel(new RunnerTreeModel(suite));
/* 104 */     this.view.tree.setCellRenderer(new RunnerTreeRenderer(null));
/*     */   }
/*     */ 
/*     */   public void OnShowRuleResult(Rule rule) {
/* 108 */     update();
/*     */   }
/*     */ 
/*     */   private class RunnerTreeRenderer
/*     */     implements TreeCellRenderer
/*     */   {
/*     */     private RunnerTreeRenderer()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */     {
/* 206 */       JLabel label = new JLabel();
/*     */ 
/* 208 */       if ((value instanceof RunnerController.TestSuiteTreeNode))
/*     */       {
/* 210 */         label.setText(value.toString());
/* 211 */         label.setIcon(ImageFactory.getSingleton().TESTSUITE);
/*     */       }
/* 213 */       else if ((value instanceof RunnerController.TestGroupTreeNode))
/*     */       {
/* 215 */         RunnerController.TestGroupTreeNode node = (RunnerController.TestGroupTreeNode)value;
/* 216 */         label.setText(value.toString());
/* 217 */         label.setIcon(RunnerController.TestGroupTreeNode.access$400(node) ? ImageFactory.getSingleton().TESTGROUPX : ImageFactory.getSingleton().TESTGROUP);
/*     */       }
/* 221 */       else if ((value instanceof RunnerController.TestCaseTreeNode))
/*     */       {
/* 223 */         RunnerController.TestCaseTreeNode node = (RunnerController.TestCaseTreeNode)value;
/* 224 */         label.setIcon(RunnerController.TestCaseTreeNode.access$500(node).isPass() ? ImageFactory.getSingleton().RUN_PASS : ImageFactory.getSingleton().RUN_FAIL);
/*     */ 
/* 227 */         label.setText(value.toString());
/*     */       }
/*     */       else {
/* 230 */         throw new IllegalArgumentException("Invalide tree node type + " + value.getClass().getName());
/*     */       }
/*     */ 
/* 234 */       return label;
/*     */     }
/*     */ 
/*     */     RunnerTreeRenderer(RunnerController.1 x1)
/*     */     {
/* 200 */       this();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class RunnerTreeModel extends DefaultTreeModel
/*     */   {
/*     */     public RunnerTreeModel(TestSuite testSuite)
/*     */     {
/* 196 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class TestCaseTreeNode extends DefaultMutableTreeNode
/*     */   {
/*     */     private TestCase data;
/*     */ 
/*     */     private TestCaseTreeNode(TestCase tc)
/*     */     {
/* 188 */       super();
/* 189 */       this.data = tc;
/*     */     }
/*     */ 
/*     */     TestCaseTreeNode(TestCase x1, RunnerController.1 x2)
/*     */     {
/* 183 */       this(x1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class TestGroupTreeNode extends DefaultMutableTreeNode
/*     */   {
/*     */     private Rule data;
/* 154 */     private boolean hasFail = false;
/*     */ 
/*     */     private TestGroupTreeNode(Rule rule) {
/* 157 */       super();
/* 158 */       for (Iterator i$ = rule.getTestCases().iterator(); i$.hasNext(); ) { TestCase tc = (TestCase)i$.next();
/* 159 */         add(new RunnerController.TestCaseTreeNode(RunnerController.this, tc, null));
/*     */       }
/*     */ 
/* 162 */       this.data = rule;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 167 */       int iPass = 0;
/* 168 */       int iFail = 0;
/* 169 */       for (Iterator i$ = this.data.getTestCases().iterator(); i$.hasNext(); ) { TestCase tc = (TestCase)i$.next();
/* 170 */         if (tc.isPass())
/* 171 */           iPass++;
/*     */         else {
/* 173 */           iFail++;
/*     */         }
/*     */       }
/* 176 */       this.hasFail = (iFail > 0);
/*     */ 
/* 178 */       return String.format("%s (pass %d, fail %d)", new Object[] { this.data.getName(), new Integer(iPass), new Integer(iFail) });
/*     */     }
/*     */ 
/*     */     TestGroupTreeNode(Rule x1, RunnerController.1 x2)
/*     */     {
/* 151 */       this(x1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class TestSuiteTreeNode extends DefaultMutableTreeNode
/*     */   {
/*     */     private TestSuite data;
/*     */ 
/*     */     public TestSuiteTreeNode(TestSuite suite)
/*     */     {
/* 134 */       super();
/* 135 */       for (int i = 0; i < suite.getRuleCount(); i++) {
/* 136 */         Rule rule = suite.getRule(i);
/* 137 */         if (rule.getNotEmpty()) add(new RunnerController.TestGroupTreeNode(RunnerController.this, rule, null));
/*     */       }
/* 139 */       this.data = suite;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 144 */       return String.format("%s (%d test groups)", new Object[] { this.data.getGrammarName(), new Integer(getChildCount()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   public class RunnerView extends JPanel
/*     */   {
/*  65 */     private JTextArea textArea = new JTextArea();
/*     */ 
/*  67 */     private JTree tree = new JTree();
/*     */ 
/*  69 */     private JScrollPane scroll = new JScrollPane(this.tree, 22, 30);
/*     */ 
/*     */     public RunnerView() {
/*     */     }
/*     */ 
/*     */     public void initComponents() {
/*  75 */       this.tree.setOpaque(false);
/*  76 */       this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
/*  77 */       this.scroll.setOpaque(false);
/*  78 */       setLayout(new BoxLayout(this, 1));
/*  79 */       add(this.scroll);
/*  80 */       setBorder(BorderFactory.createEmptyBorder());
/*  81 */       setOpaque(false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.RunnerController
 * JD-Core Version:    0.6.2
 */