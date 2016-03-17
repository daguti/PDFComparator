/*     */ package org.antlr.gunit.swingui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import org.antlr.gunit.swingui.model.Rule;
/*     */ import org.antlr.gunit.swingui.model.TestSuite;
/*     */ import org.antlr.gunit.swingui.model.TestSuiteFactory;
/*     */ import org.antlr.gunit.swingui.runner.gUnitAdapter;
/*     */ 
/*     */ public class WorkSpaceController
/*     */   implements IController
/*     */ {
/*     */   private TestSuite currentTestSuite;
/*  52 */   private String testSuiteFileName = null;
/*     */ 
/*  55 */   private final WorkSpaceView view = new WorkSpaceView();
/*     */ 
/*  58 */   private final RunnerController runner = new RunnerController();
/*     */ 
/*     */   public WorkSpaceController() {
/*  61 */     this.view.resultPane = ((JPanel)this.runner.getView());
/*  62 */     this.view.initComponents();
/*  63 */     initEventHandlers();
/*  64 */     initToolbar();
/*     */   }
/*     */ 
/*     */   public void show() {
/*  68 */     this.view.setTitle("gUnitEditor");
/*  69 */     this.view.setVisible(true);
/*  70 */     this.view.pack();
/*     */   }
/*     */ 
/*     */   public Component getEmbeddedView() {
/*  74 */     return this.view.paneEditor.getView();
/*     */   }
/*     */ 
/*     */   private void initEventHandlers() {
/*  78 */     this.view.tabEditors.addChangeListener(new TabChangeListener());
/*  79 */     this.view.listRules.setListSelectionListener(new RuleListSelectionListener(null));
/*  80 */     this.view.paneEditor.onTestCaseNumberChange = new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  82 */         WorkSpaceController.this.view.listRules.getView().updateUI();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private void OnCreateTest() {
/*  88 */     JFileChooser jfc = new JFileChooser();
/*  89 */     jfc.setDialogTitle("Create test suite from grammar");
/*  90 */     jfc.setDialogType(0);
/*  91 */     jfc.setFileFilter(new FileFilter()
/*     */     {
/*     */       public boolean accept(File f) {
/*  94 */         return (f.isDirectory()) || (f.getName().toLowerCase().endsWith(".g"));
/*     */       }
/*     */ 
/*     */       public String getDescription()
/*     */       {
/*  99 */         return "ANTLR grammar file (*.g)";
/*     */       }
/*     */     });
/* 102 */     if (jfc.showOpenDialog(this.view) != 0) return;
/*     */ 
/* 104 */     this.view.paneStatus.setProgressIndetermined(true);
/* 105 */     File grammarFile = jfc.getSelectedFile();
/*     */ 
/* 107 */     this.currentTestSuite = TestSuiteFactory.createTestSuite(grammarFile);
/*     */ 
/* 109 */     this.view.listRules.initialize(this.currentTestSuite);
/* 110 */     this.view.tabEditors.setSelectedIndex(0);
/* 111 */     this.view.paneStatus.setText("Grammar: " + this.currentTestSuite.getGrammarName());
/* 112 */     this.view.paneStatus.setProgressIndetermined(false);
/*     */ 
/* 114 */     this.testSuiteFileName = null;
/*     */   }
/*     */ 
/*     */   private void OnSaveTest() {
/* 118 */     TestSuiteFactory.saveTestSuite(this.currentTestSuite);
/* 119 */     JOptionPane.showMessageDialog(this.view, "Testsuite saved to:\n" + this.currentTestSuite.getTestSuiteFile().getAbsolutePath());
/*     */   }
/*     */ 
/*     */   private void OnOpenTest()
/*     */   {
/* 124 */     JFileChooser jfc = new JFileChooser();
/* 125 */     jfc.setDialogTitle("Open existing gUnit test suite");
/* 126 */     jfc.setDialogType(0);
/* 127 */     jfc.setFileFilter(new FileFilter()
/*     */     {
/*     */       public boolean accept(File f)
/*     */       {
/* 131 */         return (f.isDirectory()) || (f.getName().toLowerCase().endsWith(".gunit"));
/*     */       }
/*     */ 
/*     */       public String getDescription()
/*     */       {
/* 136 */         return "ANTLR unit test file (*.gunit)";
/*     */       }
/*     */     });
/* 139 */     if (jfc.showOpenDialog(this.view) != 0) return;
/*     */ 
/* 141 */     File testSuiteFile = jfc.getSelectedFile();
/*     */     try {
/* 143 */       this.testSuiteFileName = testSuiteFile.getCanonicalPath();
/*     */     } catch (IOException e) {
/* 145 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 148 */     this.view.paneStatus.setProgressIndetermined(true);
/*     */ 
/* 150 */     this.currentTestSuite = TestSuiteFactory.loadTestSuite(testSuiteFile);
/* 151 */     this.view.listRules.initialize(this.currentTestSuite);
/* 152 */     this.view.paneStatus.setText(this.currentTestSuite.getGrammarName());
/* 153 */     this.view.tabEditors.setSelectedIndex(0);
/*     */ 
/* 155 */     this.view.paneStatus.setProgressIndetermined(false);
/*     */   }
/*     */ 
/*     */   private void OnSelectRule(Rule rule) {
/* 159 */     if (rule == null) throw new IllegalArgumentException("Null");
/* 160 */     this.view.paneEditor.OnLoadRule(rule);
/* 161 */     this.view.paneStatus.setRule(rule.getName());
/*     */ 
/* 164 */     this.runner.OnShowRuleResult(rule);
/*     */   }
/*     */ 
/*     */   private void OnSelectTextPane() {
/* 168 */     Thread worker = new Thread()
/*     */     {
/*     */       public void run() {
/* 171 */         WorkSpaceController.this.view.paneStatus.setProgressIndetermined(true);
/* 172 */         WorkSpaceController.this.view.txtEditor.setText(TestSuiteFactory.getScript(WorkSpaceController.this.currentTestSuite));
/*     */ 
/* 174 */         WorkSpaceController.this.view.paneStatus.setProgressIndetermined(false);
/*     */       }
/*     */     };
/* 178 */     worker.start();
/*     */   }
/*     */ 
/*     */   private void OnRunTest()
/*     */   {
/* 183 */     TestSuiteFactory.saveTestSuite(this.currentTestSuite);
/*     */     try
/*     */     {
/* 187 */       gUnitAdapter adapter = new gUnitAdapter(this.currentTestSuite);
/* 188 */       if (this.currentTestSuite == null) return;
/* 189 */       adapter.run();
/*     */ 
/* 191 */       this.runner.OnShowSuiteResult(this.currentTestSuite);
/* 192 */       this.view.tabEditors.addTab("Test Result", ImageFactory.getSingleton().FILE16, this.runner.getView());
/* 193 */       this.view.tabEditors.setSelectedComponent(this.runner.getView());
/*     */     } catch (Exception ex) {
/* 195 */       JOptionPane.showMessageDialog(this.view, "Fail to run test:\n" + ex.getMessage(), "Error", 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initToolbar()
/*     */   {
/* 201 */     this.view.toolbar.add(new JButton(new CreateAction()));
/* 202 */     this.view.toolbar.add(new JButton(new OpenAction()));
/* 203 */     this.view.toolbar.add(new JButton(new SaveAction()));
/* 204 */     this.view.toolbar.add(new JButton(new RunAction()));
/*     */   }
/*     */ 
/*     */   public Object getModel()
/*     */   {
/* 209 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Component getView() {
/* 213 */     return this.view;
/*     */   }
/*     */ 
/*     */   private class RunAction extends AbstractAction
/*     */   {
/*     */     public RunAction()
/*     */     {
/* 279 */       super(ImageFactory.getSingleton().NEXT);
/* 280 */       putValue("ShortDescription", "Run the current test suite");
/* 281 */       putValue("AcceleratorKey", KeyStroke.getKeyStroke(82, 2));
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 285 */       WorkSpaceController.this.OnRunTest();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class OpenAction extends AbstractAction
/*     */   {
/*     */     public OpenAction()
/*     */     {
/* 266 */       super(ImageFactory.getSingleton().OPEN);
/* 267 */       putValue("ShortDescription", "Open an existing test suite");
/* 268 */       putValue("AcceleratorKey", KeyStroke.getKeyStroke(79, 2));
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e) {
/* 272 */       WorkSpaceController.this.OnOpenTest();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SaveAction extends AbstractAction
/*     */   {
/*     */     public SaveAction()
/*     */     {
/* 254 */       super(ImageFactory.getSingleton().SAVE);
/* 255 */       putValue("ShortDescription", "Save the test suite");
/*     */     }
/*     */     public void actionPerformed(ActionEvent e) {
/* 258 */       WorkSpaceController.this.OnSaveTest();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CreateAction extends AbstractAction
/*     */   {
/*     */     public CreateAction()
/*     */     {
/* 242 */       super(ImageFactory.getSingleton().ADDFILE);
/* 243 */       putValue("ShortDescription", "Create a test suite from an ANTLR grammar");
/*     */     }
/*     */     public void actionPerformed(ActionEvent e) {
/* 246 */       WorkSpaceController.this.OnCreateTest();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class TabChangeListener
/*     */     implements ChangeListener
/*     */   {
/*     */     public TabChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void stateChanged(ChangeEvent evt)
/*     */     {
/* 231 */       if (WorkSpaceController.this.view.tabEditors.getSelectedIndex() == 1)
/* 232 */         WorkSpaceController.this.OnSelectTextPane();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class RuleListSelectionListener
/*     */     implements ListSelectionListener
/*     */   {
/*     */     private RuleListSelectionListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void valueChanged(ListSelectionEvent event)
/*     */     {
/* 220 */       if (event.getValueIsAdjusting()) return;
/* 221 */       JList list = (JList)event.getSource();
/* 222 */       Rule rule = (Rule)list.getSelectedValue();
/* 223 */       if (rule != null) WorkSpaceController.this.OnSelectRule(rule);
/*     */     }
/*     */ 
/*     */     RuleListSelectionListener(WorkSpaceController.1 x1)
/*     */     {
/* 218 */       this();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.WorkSpaceController
 * JD-Core Version:    0.6.2
 */