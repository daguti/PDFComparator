/*     */ package org.antlr.gunit.swingui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.event.CaretEvent;
/*     */ import javax.swing.event.CaretListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import org.antlr.gunit.swingui.model.ITestCaseInput;
/*     */ import org.antlr.gunit.swingui.model.ITestCaseOutput;
/*     */ import org.antlr.gunit.swingui.model.Rule;
/*     */ import org.antlr.gunit.swingui.model.TestCase;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputFile;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputMultiString;
/*     */ import org.antlr.gunit.swingui.model.TestCaseInputString;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputAST;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputResult;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputReturn;
/*     */ import org.antlr.gunit.swingui.model.TestCaseOutputStdOut;
/*     */ 
/*     */ public class TestCaseEditController
/*     */   implements IController
/*     */ {
/*  45 */   private JPanel view = new JPanel();
/*     */   private JScrollPane scroll;
/*     */   private JPanel paneDetail;
/*     */   private AbstractEditorPane paneDetailInput;
/*     */   private AbstractEditorPane paneDetailOutput;
/*     */   private JToolBar toolbar;
/*     */   private JList listCases;
/*     */   private ListModel listModel;
/*     */   public ActionListener onTestCaseNumberChange;
/*     */   private InputFileEditor editInputFile;
/*     */   private InputStringEditor editInputString;
/*     */   private InputMultiEditor editInputMulti;
/*     */   private OutputResultEditor editOutputResult;
/*     */   private OutputAstEditor editOutputAST;
/*     */   private OutputStdEditor editOutputStd;
/*     */   private OutputReturnEditor editOutputReturn;
/*     */   private JComboBox comboInputType;
/*     */   private JComboBox comboOutputType;
/*     */   private static final String IN_TYPE_STRING = "Single-line Text";
/*     */   private static final String IN_TYPE_MULTI = "Multi-line Text";
/*     */   private static final String IN_TYPE_FILE = "Disk File";
/*     */   private static final String OUT_TYPE_BOOL = "OK or Fail";
/*     */   private static final String OUT_TYPE_AST = "AST";
/*     */   private static final String OUT_TYPE_STD = "Standard Output";
/*     */   private static final String OUT_TYPE_RET = "Return Value";
/*     */   private static final String DEFAULT_IN_SCRIPT = "";
/*     */   private static final String DEFAULT_OUT_SCRIPT = "";
/*  79 */   private static final Object[] INPUT_TYPE = { "Single-line Text", "Multi-line Text", "Disk File" };
/*     */ 
/*  83 */   private static final Object[] OUTPUT_TYPE = { "OK or Fail", "AST", "Standard Output", "Return Value" };
/*     */   private static final int TEST_CASE_DETAIL_WIDTH = 300;
/*     */   private static final int TEST_EDITOR_WIDTH = 280;
/*     */   private static final int TEST_CASE_DETAIL_HEIGHT = 250;
/*     */   private static final int TEST_EDITOR_HEIGHT = 120;
/*  94 */   private Rule currentRule = null;
/*  95 */   private TestCase currentTestCase = null;
/*     */ 
/* 101 */   private static final HashMap<Class, String> TypeNameTable = new HashMap();
/*     */ 
/* 576 */   private static TestCaseListRenderer listRenderer = new TestCaseListRenderer(null);
/*     */ 
/*     */   public TestCaseEditController(WorkSpaceView workspace)
/*     */   {
/* 116 */     initComponents();
/*     */   }
/*     */ 
/*     */   public TestCaseEditController() {
/* 120 */     initComponents();
/*     */   }
/*     */ 
/*     */   public void OnLoadRule(Rule rule) {
/* 124 */     if (rule == null) throw new IllegalArgumentException("Null");
/* 125 */     this.currentRule = rule;
/* 126 */     this.currentTestCase = null;
/* 127 */     this.listModel = rule;
/* 128 */     this.listCases.setModel(this.listModel);
/*     */   }
/*     */ 
/*     */   public void setCurrentTestCase(TestCase testCase) {
/* 132 */     if (testCase == null) throw new IllegalArgumentException("Null");
/* 133 */     this.listCases.setSelectedValue(testCase, true);
/* 134 */     this.currentTestCase = testCase;
/*     */   }
/*     */ 
/*     */   public Rule getCurrentRule() {
/* 138 */     return this.currentRule;
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 144 */     this.listCases = new JList();
/* 145 */     this.listCases.addListSelectionListener(new TestCaseListSelectionListener(null));
/* 146 */     this.listCases.setCellRenderer(listRenderer);
/* 147 */     this.listCases.setOpaque(false);
/*     */ 
/* 149 */     this.scroll = new JScrollPane(this.listCases);
/* 150 */     this.scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Test Cases"));
/*     */ 
/* 152 */     this.scroll.setOpaque(false);
/* 153 */     this.scroll.setViewportBorder(BorderFactory.createEtchedBorder());
/*     */ 
/* 157 */     this.editInputString = new InputStringEditor();
/* 158 */     this.editInputMulti = new InputMultiEditor();
/* 159 */     this.editInputFile = new InputFileEditor();
/*     */ 
/* 161 */     this.editOutputResult = new OutputResultEditor();
/* 162 */     this.editOutputAST = new OutputAstEditor();
/* 163 */     this.editOutputStd = new OutputStdEditor();
/* 164 */     this.editOutputReturn = new OutputReturnEditor();
/*     */ 
/* 166 */     this.paneDetail = new JPanel();
/* 167 */     this.paneDetail.setBorder(BorderFactory.createEmptyBorder());
/* 168 */     this.paneDetail.setOpaque(false);
/*     */ 
/* 170 */     this.comboInputType = new JComboBox(INPUT_TYPE);
/* 171 */     this.comboInputType.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent event) {
/* 173 */         TestCaseEditController.this.OnInputTestCaseTypeChanged(TestCaseEditController.this.comboInputType.getSelectedItem());
/*     */       }
/*     */     });
/* 176 */     this.comboOutputType = new JComboBox(OUTPUT_TYPE);
/* 177 */     this.comboOutputType.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent event) {
/* 179 */         TestCaseEditController.this.OnOutputTestCaseTypeChanged(TestCaseEditController.this.comboOutputType.getSelectedItem());
/*     */       }
/*     */     });
/* 182 */     this.paneDetailInput = new InputEditorPane(this.comboInputType);
/* 183 */     this.paneDetailOutput = new OutputEditorPane(this.comboOutputType);
/*     */ 
/* 185 */     BoxLayout layout = new BoxLayout(this.paneDetail, 3);
/* 186 */     this.paneDetail.setLayout(layout);
/*     */ 
/* 188 */     this.paneDetail.add(this.paneDetailInput);
/* 189 */     this.paneDetail.add(this.paneDetailOutput);
/*     */ 
/* 192 */     this.toolbar = new JToolBar("Edit TestCases", 1);
/* 193 */     this.toolbar.setFloatable(false);
/* 194 */     this.toolbar.add(new AddTestCaseAction());
/* 195 */     this.toolbar.add(new RemoveTestCaseAction());
/*     */ 
/* 198 */     this.view.setLayout(new BorderLayout());
/* 199 */     this.view.setBorder(BorderFactory.createEmptyBorder());
/* 200 */     this.view.setOpaque(false);
/* 201 */     this.view.add(this.toolbar, "West");
/* 202 */     this.view.add(this.scroll, "Center");
/* 203 */     this.view.add(this.paneDetail, "East");
/*     */   }
/*     */ 
/*     */   private void updateInputEditor() {
/* 207 */     JComponent editor = null;
/*     */ 
/* 209 */     if (this.currentTestCase != null) {
/* 210 */       ITestCaseInput input = this.currentTestCase.getInput();
/* 211 */       if ((input instanceof TestCaseInputString)) {
/* 212 */         this.editInputString.setText(input.getScript());
/* 213 */         editor = this.editInputString;
/* 214 */         this.comboInputType.setSelectedItem("Single-line Text");
/* 215 */       } else if ((input instanceof TestCaseInputMultiString)) {
/* 216 */         this.editInputMulti.setText(input.getScript());
/* 217 */         editor = this.editInputMulti.getView();
/* 218 */         this.comboInputType.setSelectedItem("Multi-line Text");
/* 219 */       } else if ((input instanceof TestCaseInputFile)) {
/* 220 */         this.editInputFile.setText(input.getScript());
/* 221 */         editor = this.editInputFile;
/* 222 */         this.comboInputType.setSelectedItem("Disk File");
/*     */       } else {
/* 224 */         throw new Error("Wrong type");
/*     */       }
/*     */     }
/*     */ 
/* 228 */     this.paneDetailInput.setEditor(editor);
/*     */   }
/*     */ 
/*     */   private void updateOutputEditor() {
/* 232 */     JComponent editor = null;
/*     */ 
/* 234 */     if (this.currentTestCase != null)
/*     */     {
/* 236 */       ITestCaseOutput output = this.currentTestCase.getOutput();
/*     */ 
/* 238 */       if ((output instanceof TestCaseOutputAST))
/*     */       {
/* 240 */         this.editOutputAST.setText(output.getScript());
/* 241 */         editor = this.editOutputAST.getView();
/* 242 */         this.comboOutputType.setSelectedItem("AST");
/*     */       }
/* 244 */       else if ((output instanceof TestCaseOutputResult))
/*     */       {
/* 246 */         this.editOutputResult.setValue(output.getScript());
/* 247 */         editor = this.editOutputResult;
/* 248 */         this.comboOutputType.setSelectedItem("OK or Fail");
/*     */       }
/* 250 */       else if ((output instanceof TestCaseOutputStdOut))
/*     */       {
/* 252 */         this.editOutputStd.setText(output.getScript());
/* 253 */         editor = this.editOutputStd.getView();
/* 254 */         this.comboOutputType.setSelectedItem("Standard Output");
/*     */       }
/* 256 */       else if ((output instanceof TestCaseOutputReturn))
/*     */       {
/* 258 */         this.editOutputReturn.setText(output.getScript());
/* 259 */         editor = this.editOutputReturn.getView();
/* 260 */         this.comboOutputType.setSelectedItem("Return Value");
/*     */       }
/*     */       else
/*     */       {
/* 264 */         throw new Error("Wrong type");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 269 */     this.paneDetailOutput.setEditor(editor);
/*     */   }
/*     */ 
/*     */   private void OnInputTestCaseTypeChanged(Object inputTypeStr) {
/* 273 */     if (this.currentTestCase != null)
/*     */     {
/*     */       ITestCaseInput input;
/* 275 */       if (inputTypeStr == "Single-line Text") {
/* 276 */         input = new TestCaseInputString("");
/*     */       }
/*     */       else
/*     */       {
/*     */         ITestCaseInput input;
/* 277 */         if (inputTypeStr == "Multi-line Text") {
/* 278 */           input = new TestCaseInputMultiString("");
/*     */         }
/*     */         else
/*     */         {
/*     */           ITestCaseInput input;
/* 279 */           if (inputTypeStr == "Disk File")
/* 280 */             input = new TestCaseInputFile("");
/*     */           else
/* 282 */             throw new Error("Wrong Type");
/*     */         }
/*     */       }
/*     */       ITestCaseInput input;
/* 285 */       if (input.getClass().equals(this.currentTestCase.getInput().getClass())) {
/* 286 */         return;
/*     */       }
/* 288 */       this.currentTestCase.setInput(input);
/*     */     }
/* 290 */     updateInputEditor();
/*     */   }
/*     */ 
/*     */   private void OnOutputTestCaseTypeChanged(Object outputTypeStr) {
/* 294 */     if (this.currentTestCase != null)
/*     */     {
/*     */       ITestCaseOutput output;
/* 297 */       if (outputTypeStr == "AST") {
/* 298 */         output = new TestCaseOutputAST("");
/*     */       }
/*     */       else
/*     */       {
/*     */         ITestCaseOutput output;
/* 299 */         if (outputTypeStr == "OK or Fail") {
/* 300 */           output = new TestCaseOutputResult(false);
/*     */         }
/*     */         else
/*     */         {
/*     */           ITestCaseOutput output;
/* 301 */           if (outputTypeStr == "Standard Output") {
/* 302 */             output = new TestCaseOutputStdOut("");
/*     */           }
/*     */           else
/*     */           {
/*     */             ITestCaseOutput output;
/* 303 */             if (outputTypeStr == "Return Value")
/* 304 */               output = new TestCaseOutputReturn("");
/*     */             else
/* 306 */               throw new Error("Wrong Type");
/*     */           }
/*     */         }
/*     */       }
/*     */       ITestCaseOutput output;
/* 309 */       if (output.getClass().equals(this.currentTestCase.getOutput().getClass())) {
/* 310 */         return;
/*     */       }
/* 312 */       this.currentTestCase.setOutput(output);
/*     */     }
/* 314 */     updateOutputEditor();
/*     */   }
/*     */ 
/*     */   private void OnTestCaseSelected(TestCase testCase)
/*     */   {
/* 320 */     this.currentTestCase = testCase;
/* 321 */     updateInputEditor();
/* 322 */     updateOutputEditor();
/*     */   }
/*     */ 
/*     */   private void OnAddTestCase()
/*     */   {
/* 327 */     if (this.currentRule == null) return;
/*     */ 
/* 329 */     TestCase newCase = new TestCase(new TestCaseInputString(""), new TestCaseOutputResult(true));
/*     */ 
/* 332 */     this.currentRule.addTestCase(newCase);
/* 333 */     setCurrentTestCase(newCase);
/*     */ 
/* 335 */     this.listCases.setSelectedValue(newCase, true);
/* 336 */     this.listCases.updateUI();
/* 337 */     OnTestCaseSelected(newCase);
/* 338 */     this.onTestCaseNumberChange.actionPerformed(null);
/*     */   }
/*     */ 
/*     */   private void OnRemoveTestCase() {
/* 342 */     if (this.currentTestCase == null) return;
/* 343 */     this.currentRule.removeElement(this.currentTestCase);
/* 344 */     this.listCases.updateUI();
/*     */ 
/* 346 */     TestCase nextActiveCase = this.listCases.isSelectionEmpty() ? null : (TestCase)this.listCases.getSelectedValue();
/*     */ 
/* 348 */     OnTestCaseSelected(nextActiveCase);
/* 349 */     this.onTestCaseNumberChange.actionPerformed(null);
/*     */   }
/*     */ 
/*     */   public Object getModel() {
/* 353 */     return this.currentRule;
/*     */   }
/*     */ 
/*     */   public Component getView() {
/* 357 */     return this.view;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 102 */     TypeNameTable.put(TestCaseInputString.class, "Single-line Text");
/* 103 */     TypeNameTable.put(TestCaseInputMultiString.class, "Multi-line Text");
/* 104 */     TypeNameTable.put(TestCaseInputFile.class, "Disk File");
/*     */ 
/* 106 */     TypeNameTable.put(TestCaseOutputResult.class, "OK or Fail");
/* 107 */     TypeNameTable.put(TestCaseOutputAST.class, "AST");
/* 108 */     TypeNameTable.put(TestCaseOutputStdOut.class, "Standard Output");
/* 109 */     TypeNameTable.put(TestCaseOutputReturn.class, "Return Value");
/*     */   }
/*     */ 
/*     */   private static class TestCaseListRenderer
/*     */     implements ListCellRenderer
/*     */   {
/* 581 */     private static Font IN_FONT = new Font("mono", 0, 12);
/* 582 */     private static Font OUT_FONT = new Font("default", 1, 12);
/*     */ 
/*     */     private TestCaseListRenderer() {  } 
/* 585 */     public static String clamp(String text, int len) { if (text.length() > len) {
/* 586 */         return text.substring(0, len - 3).concat("...");
/*     */       }
/* 588 */       return text;
/*     */     }
/*     */ 
/*     */     public static String clampAtNewLine(String text)
/*     */     {
/* 593 */       int pos = text.indexOf('\n');
/* 594 */       if (pos >= 0) {
/* 595 */         return text.substring(0, pos).concat("...");
/*     */       }
/* 597 */       return text;
/*     */     }
/*     */ 
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus)
/*     */     {
/* 605 */       JPanel pane = new JPanel();
/*     */ 
/* 607 */       if ((value instanceof TestCase)) {
/* 608 */         TestCase item = (TestCase)value;
/*     */ 
/* 611 */         JLabel labIn = new JLabel(clamp(clampAtNewLine(item.getInput().getScript()), 18));
/*     */ 
/* 613 */         JLabel labOut = new JLabel(clamp(clampAtNewLine(item.getOutput().getScript()), 18));
/*     */ 
/* 615 */         labOut.setFont(OUT_FONT);
/* 616 */         labIn.setFont(IN_FONT);
/*     */ 
/* 618 */         labIn.setIcon((item.getInput() instanceof TestCaseInputFile) ? ImageFactory.getSingleton().FILE16 : ImageFactory.getSingleton().EDIT16);
/*     */ 
/* 622 */         pane.setBorder(BorderFactory.createEtchedBorder());
/* 623 */         pane.setLayout(new BoxLayout(pane, 1));
/* 624 */         pane.add(labIn);
/* 625 */         pane.add(labOut);
/* 626 */         pane.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
/*     */       }
/*     */ 
/* 629 */       return pane;
/*     */     }
/*     */ 
/*     */     TestCaseListRenderer(TestCaseEditController.1 x0)
/*     */     {
/* 579 */       this();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class RemoveTestCaseAction extends AbstractAction
/*     */   {
/*     */     public RemoveTestCaseAction()
/*     */     {
/* 566 */       super(ImageFactory.getSingleton().DELETE);
/* 567 */       putValue("ShortDescription", "Remove a gUnit test case.");
/*     */     }
/*     */     public void actionPerformed(ActionEvent e) {
/* 570 */       TestCaseEditController.this.OnRemoveTestCase();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class AddTestCaseAction extends AbstractAction
/*     */   {
/*     */     public AddTestCaseAction()
/*     */     {
/* 556 */       super(ImageFactory.getSingleton().ADD);
/* 557 */       putValue("ShortDescription", "Add a gUnit test case.");
/*     */     }
/*     */     public void actionPerformed(ActionEvent e) {
/* 560 */       TestCaseEditController.this.OnAddTestCase();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class TestCaseListSelectionListener
/*     */     implements ListSelectionListener
/*     */   {
/*     */     private TestCaseListSelectionListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void valueChanged(ListSelectionEvent e)
/*     */     {
/* 543 */       if (e.getValueIsAdjusting()) return;
/* 544 */       JList list = (JList)e.getSource();
/* 545 */       TestCase value = (TestCase)list.getSelectedValue();
/* 546 */       if (value != null) TestCaseEditController.this.OnTestCaseSelected(value);
/*     */     }
/*     */ 
/*     */     TestCaseListSelectionListener(TestCaseEditController.1 x1)
/*     */     {
/* 539 */       this();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class OutputReturnEditor extends TestCaseEditController.OutputAstEditor
/*     */   {
/*     */     public OutputReturnEditor()
/*     */     {
/* 535 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class OutputStdEditor extends TestCaseEditController.OutputAstEditor
/*     */   {
/*     */     public OutputStdEditor()
/*     */     {
/* 534 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class OutputAstEditor
/*     */     implements CaretListener
/*     */   {
/* 504 */     private JTextArea textArea = new JTextArea(20, 30);
/* 505 */     private JScrollPane scroll = new JScrollPane(this.textArea, 22, 32);
/*     */ 
/*     */     public OutputAstEditor()
/*     */     {
/* 511 */       this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
/* 512 */       this.textArea.addCaretListener(this);
/*     */     }
/*     */ 
/*     */     public void caretUpdate(CaretEvent arg0) {
/* 516 */       TestCaseEditController.this.currentTestCase.getOutput().setScript(getText());
/* 517 */       TestCaseEditController.this.listCases.updateUI();
/*     */     }
/*     */ 
/*     */     public void setText(String text) {
/* 521 */       this.textArea.setText(text);
/*     */     }
/*     */ 
/*     */     public String getText() {
/* 525 */       return this.textArea.getText();
/*     */     }
/*     */ 
/*     */     public JScrollPane getView() {
/* 529 */       return this.scroll;
/*     */     }
/*     */   }
/*     */ 
/*     */   public class OutputResultEditor extends JPanel
/*     */     implements ActionListener
/*     */   {
/*     */     private JToggleButton tbFail;
/*     */     private JToggleButton tbOk;
/*     */ 
/*     */     public OutputResultEditor()
/*     */     {
/* 464 */       this.tbFail = new JToggleButton("Fail");
/* 465 */       this.tbOk = new JToggleButton("OK");
/* 466 */       ButtonGroup group = new ButtonGroup();
/* 467 */       group.add(this.tbFail);
/* 468 */       group.add(this.tbOk);
/*     */ 
/* 470 */       add(this.tbFail);
/* 471 */       add(this.tbOk);
/*     */ 
/* 473 */       this.tbFail.addActionListener(this);
/* 474 */       this.tbOk.addActionListener(this);
/*     */ 
/* 476 */       setPreferredSize(new Dimension(280, 100));
/*     */     }
/*     */ 
/*     */     public void actionPerformed(ActionEvent e)
/*     */     {
/* 481 */       TestCaseOutputResult output = (TestCaseOutputResult)TestCaseEditController.this.currentTestCase.getOutput();
/*     */ 
/* 484 */       if (e.getSource() == this.tbFail)
/* 485 */         output.setScript(false);
/*     */       else {
/* 487 */         output.setScript(true);
/*     */       }
/*     */ 
/* 490 */       TestCaseEditController.this.listCases.updateUI();
/*     */     }
/*     */ 
/*     */     public void setValue(String value) {
/* 494 */       if (TestCaseOutputResult.OK.equals(value))
/* 495 */         this.tbOk.setSelected(true);
/*     */       else
/* 497 */         this.tbFail.setSelected(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public class InputFileEditor extends TestCaseEditController.InputStringEditor
/*     */   {
/*     */     public InputFileEditor()
/*     */     {
/* 455 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class InputMultiEditor
/*     */     implements CaretListener
/*     */   {
/* 426 */     private JTextArea textArea = new JTextArea(20, 30);
/* 427 */     private JScrollPane scroll = new JScrollPane(this.textArea, 22, 32);
/*     */ 
/*     */     public InputMultiEditor()
/*     */     {
/* 433 */       this.scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
/* 434 */       this.textArea.addCaretListener(this);
/*     */     }
/*     */ 
/*     */     public void caretUpdate(CaretEvent arg0) {
/* 438 */       TestCaseEditController.this.currentTestCase.getInput().setScript(getText());
/* 439 */       TestCaseEditController.this.listCases.updateUI();
/*     */     }
/*     */ 
/*     */     public String getText() {
/* 443 */       return this.textArea.getText();
/*     */     }
/*     */ 
/*     */     public void setText(String text) {
/* 447 */       this.textArea.setText(text);
/*     */     }
/*     */ 
/*     */     public JComponent getView() {
/* 451 */       return this.scroll;
/*     */     }
/*     */   }
/*     */ 
/*     */   public class InputStringEditor extends JTextField
/*     */     implements CaretListener
/*     */   {
/*     */     public InputStringEditor()
/*     */     {
/* 415 */       setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
/* 416 */       addCaretListener(this);
/*     */     }
/*     */ 
/*     */     public void caretUpdate(CaretEvent arg0) {
/* 420 */       TestCaseEditController.this.currentTestCase.getInput().setScript(getText());
/* 421 */       TestCaseEditController.this.listCases.updateUI();
/*     */     }
/*     */   }
/*     */ 
/*     */   public class OutputEditorPane extends TestCaseEditController.AbstractEditorPane
/*     */   {
/*     */     public OutputEditorPane(JComboBox comboBox)
/*     */     {
/* 405 */       super(comboBox, "Output");
/*     */     }
/*     */   }
/*     */ 
/*     */   public class InputEditorPane extends TestCaseEditController.AbstractEditorPane
/*     */   {
/*     */     public InputEditorPane(JComboBox comboBox)
/*     */     {
/* 399 */       super(comboBox, "Input");
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract class AbstractEditorPane extends JPanel
/*     */   {
/*     */     private JComboBox combo;
/*     */     private JComponent editor;
/*     */     private String title;
/* 367 */     private JLabel placeHolder = new JLabel();
/*     */ 
/*     */     public AbstractEditorPane(JComboBox comboBox, String title) {
/* 370 */       this.combo = comboBox;
/* 371 */       this.editor = this.placeHolder;
/* 372 */       this.title = title;
/* 373 */       initComponents();
/*     */     }
/*     */ 
/*     */     private void initComponents() {
/* 377 */       this.placeHolder.setPreferredSize(new Dimension(300, 250));
/*     */ 
/* 379 */       setLayout(new BoxLayout(this, 1));
/* 380 */       add(this.combo, "North");
/* 381 */       add(this.editor, "Center");
/* 382 */       setOpaque(false);
/* 383 */       setBorder(BorderFactory.createTitledBorder(this.title));
/* 384 */       setPreferredSize(new Dimension(300, 250));
/*     */     }
/*     */ 
/*     */     public void setEditor(JComponent newEditor)
/*     */     {
/* 389 */       if (newEditor == null) newEditor = this.placeHolder;
/* 390 */       remove(this.editor);
/* 391 */       add(newEditor);
/* 392 */       this.editor = newEditor;
/* 393 */       updateUI();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.TestCaseEditController
 * JD-Core Version:    0.6.2
 */