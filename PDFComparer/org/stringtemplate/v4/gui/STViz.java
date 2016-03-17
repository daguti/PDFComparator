/*     */ package org.stringtemplate.v4.gui;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextPane;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.CaretEvent;
/*     */ import javax.swing.event.CaretListener;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.DefaultHighlighter;
/*     */ import javax.swing.text.Highlighter;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.CommonTreeAdaptor;
/*     */ import org.stringtemplate.v4.InstanceScope;
/*     */ import org.stringtemplate.v4.Interpreter;
/*     */ import org.stringtemplate.v4.ST;
/*     */ import org.stringtemplate.v4.STGroup;
/*     */ import org.stringtemplate.v4.STGroupFile;
/*     */ import org.stringtemplate.v4.STGroupString;
/*     */ import org.stringtemplate.v4.compiler.CompiledST;
/*     */ import org.stringtemplate.v4.debug.EvalTemplateEvent;
/*     */ import org.stringtemplate.v4.debug.InterpEvent;
/*     */ import org.stringtemplate.v4.misc.ErrorManager;
/*     */ import org.stringtemplate.v4.misc.Interval;
/*     */ import org.stringtemplate.v4.misc.Misc;
/*     */ import org.stringtemplate.v4.misc.STMessage;
/*     */ import org.stringtemplate.v4.misc.STRuntimeMessage;
/*     */ 
/*     */ public class STViz
/*     */ {
/*     */   public EvalTemplateEvent root;
/*     */   public InstanceScope currentScope;
/*     */   public List<InterpEvent> allEvents;
/*     */   public JTreeSTModel tmodel;
/*     */   public ErrorManager errMgr;
/*     */   public Interpreter interp;
/*     */   public String output;
/*     */   public List<String> trace;
/*     */   public List<STMessage> errors;
/*     */   public STViewFrame viewFrame;
/*     */ 
/*     */   public STViz(ErrorManager errMgr, EvalTemplateEvent root, String output, Interpreter interp, List<String> trace, List<STMessage> errors)
/*     */   {
/*  73 */     this.errMgr = errMgr;
/*  74 */     this.currentScope = root.scope;
/*  75 */     this.output = output;
/*  76 */     this.interp = interp;
/*  77 */     this.allEvents = interp.getEvents();
/*  78 */     this.trace = trace;
/*  79 */     this.errors = errors;
/*     */   }
/*     */ 
/*     */   public void open() {
/*  83 */     this.viewFrame = new STViewFrame();
/*  84 */     updateStack(this.currentScope, this.viewFrame);
/*  85 */     updateAttributes(this.currentScope, this.viewFrame);
/*     */ 
/*  87 */     List events = this.currentScope.events;
/*  88 */     this.tmodel = new JTreeSTModel(this.interp, (EvalTemplateEvent)events.get(events.size() - 1));
/*  89 */     this.viewFrame.tree.setModel(this.tmodel);
/*  90 */     this.viewFrame.tree.addTreeSelectionListener(new TreeSelectionListener()
/*     */     {
/*     */       public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
/*  93 */         STViz.this.currentScope = ((JTreeSTModel.Wrapper)STViz.this.viewFrame.tree.getLastSelectedPathComponent()).event.scope;
/*  94 */         STViz.this.updateCurrentST(STViz.this.viewFrame);
/*     */       }
/*     */     });
/*  99 */     JTreeASTModel astModel = new JTreeASTModel(new CommonTreeAdaptor(), this.currentScope.st.impl.ast);
/* 100 */     this.viewFrame.ast.setModel(astModel);
/* 101 */     this.viewFrame.ast.addTreeSelectionListener(new TreeSelectionListener()
/*     */     {
/*     */       public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
/* 104 */         TreePath path = treeSelectionEvent.getNewLeadSelectionPath();
/* 105 */         if (path == null) return;
/* 106 */         CommonTree node = (CommonTree)treeSelectionEvent.getNewLeadSelectionPath().getLastPathComponent();
/*     */ 
/* 108 */         CommonToken a = (CommonToken)STViz.this.currentScope.st.impl.tokens.get(node.getTokenStartIndex());
/* 109 */         CommonToken b = (CommonToken)STViz.this.currentScope.st.impl.tokens.get(node.getTokenStopIndex());
/* 110 */         STViz.this.highlight(STViz.this.viewFrame.template, a.getStartIndex(), b.getStopIndex());
/*     */       }
/*     */     });
/* 130 */     this.viewFrame.output.setText(this.output);
/*     */ 
/* 132 */     this.viewFrame.template.setText(this.currentScope.st.impl.template);
/* 133 */     this.viewFrame.bytecode.setText(this.currentScope.st.impl.disasm());
/* 134 */     this.viewFrame.trace.setText(Misc.join(this.trace.iterator(), "\n"));
/*     */ 
/* 136 */     CaretListener caretListenerLabel = new CaretListener() {
/*     */       public void caretUpdate(CaretEvent e) {
/* 138 */         int dot = e.getDot();
/* 139 */         InterpEvent de = STViz.this.findEventAtOutputLocation(STViz.this.allEvents, dot);
/* 140 */         if (de == null) STViz.this.currentScope = STViz.this.tmodel.root.event.scope; else {
/* 141 */           STViz.this.currentScope = de.scope;
/*     */         }
/*     */ 
/* 145 */         List stack = Interpreter.getEvalTemplateEventStack(STViz.this.currentScope, true);
/*     */ 
/* 147 */         Object[] path = new Object[stack.size()];
/* 148 */         int j = 0;
/*     */         EvalTemplateEvent s;
/* 149 */         for (Iterator i$ = stack.iterator(); i$.hasNext(); path[(j++)] = new JTreeSTModel.Wrapper(s)) s = (EvalTemplateEvent)i$.next();
/* 150 */         TreePath p = new TreePath(path);
/* 151 */         STViz.this.viewFrame.tree.setSelectionPath(p);
/* 152 */         STViz.this.viewFrame.tree.scrollPathToVisible(p);
/* 153 */         STViz.this.updateCurrentST(STViz.this.viewFrame);
/*     */       }
/*     */     };
/* 157 */     this.viewFrame.output.addCaretListener(caretListenerLabel);
/*     */ 
/* 160 */     if ((this.errors == null) || (this.errors.size() == 0)) {
/* 161 */       this.viewFrame.errorScrollPane.setVisible(false);
/*     */     }
/*     */     else {
/* 164 */       DefaultListModel errorListModel = new DefaultListModel();
/* 165 */       for (STMessage msg : this.errors) {
/* 166 */         errorListModel.addElement(msg);
/*     */       }
/* 168 */       this.viewFrame.errorList.setModel(errorListModel);
/*     */     }
/*     */ 
/* 171 */     this.viewFrame.errorList.addListSelectionListener(new ListSelectionListener()
/*     */     {
/*     */       public void valueChanged(ListSelectionEvent e) {
/* 174 */         int minIndex = STViz.this.viewFrame.errorList.getMinSelectionIndex();
/* 175 */         int maxIndex = STViz.this.viewFrame.errorList.getMaxSelectionIndex();
/* 176 */         int i = minIndex;
/* 177 */         while ((i <= maxIndex) && 
/* 178 */           (!STViz.this.viewFrame.errorList.isSelectedIndex(i))) {
/* 179 */           i++;
/*     */         }
/* 181 */         ListModel model = STViz.this.viewFrame.errorList.getModel();
/* 182 */         STMessage msg = (STMessage)model.getElementAt(i);
/* 183 */         if ((msg instanceof STRuntimeMessage)) {
/* 184 */           STRuntimeMessage rmsg = (STRuntimeMessage)msg;
/* 185 */           Interval I = rmsg.self.impl.sourceMap[rmsg.ip];
/* 186 */           STViz.this.currentScope = ((STRuntimeMessage)msg).scope;
/* 187 */           STViz.this.updateCurrentST(STViz.this.viewFrame);
/* 188 */           if (I != null)
/* 189 */             STViz.this.highlight(STViz.this.viewFrame.template, I.a, I.b);
/*     */         }
/*     */       }
/*     */     });
/* 196 */     Border empty = BorderFactory.createEmptyBorder();
/* 197 */     this.viewFrame.treeContentSplitPane.setBorder(empty);
/* 198 */     this.viewFrame.outputTemplateSplitPane.setBorder(empty);
/* 199 */     this.viewFrame.templateBytecodeTraceTabPanel.setBorder(empty);
/* 200 */     this.viewFrame.treeAttributesSplitPane.setBorder(empty);
/*     */ 
/* 203 */     this.viewFrame.treeContentSplitPane.setOneTouchExpandable(true);
/* 204 */     this.viewFrame.outputTemplateSplitPane.setOneTouchExpandable(true);
/* 205 */     this.viewFrame.treeContentSplitPane.setDividerSize(10);
/* 206 */     this.viewFrame.outputTemplateSplitPane.setDividerSize(8);
/* 207 */     this.viewFrame.treeContentSplitPane.setContinuousLayout(true);
/* 208 */     this.viewFrame.treeAttributesSplitPane.setContinuousLayout(true);
/* 209 */     this.viewFrame.outputTemplateSplitPane.setContinuousLayout(true);
/*     */ 
/* 211 */     this.viewFrame.setDefaultCloseOperation(2);
/* 212 */     this.viewFrame.pack();
/* 213 */     this.viewFrame.setSize(900, 700);
/*     */ 
/* 215 */     this.viewFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */   private void updateCurrentST(STViewFrame m)
/*     */   {
/* 221 */     updateStack(this.currentScope, m);
/* 222 */     updateAttributes(this.currentScope, m);
/* 223 */     m.bytecode.moveCaretPosition(0);
/* 224 */     m.bytecode.setText(this.currentScope.st.impl.disasm());
/* 225 */     m.template.moveCaretPosition(0);
/* 226 */     m.template.setText(this.currentScope.st.impl.template);
/* 227 */     JTreeASTModel astModel = new JTreeASTModel(new CommonTreeAdaptor(), this.currentScope.st.impl.ast);
/* 228 */     this.viewFrame.ast.setModel(astModel);
/*     */ 
/* 232 */     List events = this.currentScope.events;
/* 233 */     EvalTemplateEvent e = (EvalTemplateEvent)events.get(events.size() - 1);
/*     */ 
/* 235 */     highlight(m.output, e.outputStartChar, e.outputStopChar);
/*     */     try {
/* 237 */       m.output.scrollRectToVisible(m.output.modelToView(e.outputStartChar));
/*     */     }
/*     */     catch (BadLocationException ble) {
/* 240 */       this.currentScope.st.groupThatCreatedThisInstance.errMgr.internalError(this.currentScope.st, "bad location: char index " + e.outputStartChar, ble);
/*     */     }
/*     */ 
/* 245 */     if (this.currentScope.st.isAnonSubtemplate()) {
/* 246 */       Interval r = this.currentScope.st.impl.getTemplateRange();
/*     */ 
/* 249 */       highlight(m.template, r.a, r.b);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void highlight(JTextComponent comp, int i, int j) {
/* 254 */     Highlighter highlighter = comp.getHighlighter();
/* 255 */     highlighter.removeAllHighlights();
/*     */     try
/*     */     {
/* 258 */       highlighter.addHighlight(i, j + 1, DefaultHighlighter.DefaultPainter);
/*     */     }
/*     */     catch (BadLocationException ble) {
/* 261 */       this.errMgr.internalError(this.tmodel.root.event.scope.st, "bad highlight location", ble);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateAttributes(InstanceScope scope, STViewFrame m)
/*     */   {
/* 267 */     m.attributes.setModel(new JTreeScopeStackModel(scope));
/* 268 */     m.attributes.setRootVisible(false);
/* 269 */     m.attributes.setShowsRootHandles(true);
/*     */   }
/*     */ 
/*     */   protected void updateStack(InstanceScope scope, STViewFrame m)
/*     */   {
/* 303 */     List stack = Interpreter.getEnclosingInstanceStack(scope, true);
/* 304 */     m.setTitle("STViz - [" + Misc.join(stack.iterator(), " ") + "]");
/*     */   }
/*     */ 
/*     */   public InterpEvent findEventAtOutputLocation(List<InterpEvent> events, int charIndex)
/*     */   {
/* 316 */     InterpEvent e;
/* 316 */     for (Iterator i$ = events.iterator(); i$.hasNext(); 
/* 317 */       return e) { e = (InterpEvent)i$.next();
/* 317 */       if ((charIndex < e.outputStartChar) || (charIndex > e.outputStopChar)); } return null;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws IOException {
/* 323 */     if ((args.length > 0) && (args[0].equals("1"))) test1();
/* 324 */     else if ((args.length > 0) && (args[0].equals("2"))) test2();
/* 325 */     else if ((args.length > 0) && (args[0].equals("3"))) test3();
/* 326 */     else if ((args.length > 0) && (args[0].equals("4"))) test4(); 
/*     */   }
/*     */ 
/*     */   public static void test1() throws IOException {
/* 330 */     String templates = "method(type,name,locals,args,stats) ::= <<\npublic <type> <name>(<args:{a| int <a>}; separator=\", \">) {\n    <if(locals)>int locals[<locals>];<endif>\n    <stats;separator=\"\\n\">\n}\n>>\nassign(a,b) ::= \"<a> = <b>;\"\nreturn(x) ::= <<return <x>;>>\nparen(x) ::= \"(<x>)\"\n";
/*     */ 
/* 341 */     String tmpdir = System.getProperty("java.io.tmpdir");
/* 342 */     writeFile(tmpdir, "t.stg", templates);
/* 343 */     STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
/* 344 */     ST st = group.getInstanceOf("method");
/* 345 */     st.impl.dump();
/* 346 */     st.add("type", "float");
/* 347 */     st.add("name", "foo");
/* 348 */     st.add("locals", Integer.valueOf(3));
/* 349 */     st.add("args", new String[] { "x", "y", "z" });
/* 350 */     ST s1 = group.getInstanceOf("assign");
/* 351 */     ST paren = group.getInstanceOf("paren");
/* 352 */     paren.add("x", "x");
/* 353 */     s1.add("a", paren);
/* 354 */     s1.add("b", "y");
/* 355 */     ST s2 = group.getInstanceOf("assign");
/* 356 */     s2.add("a", "y");
/* 357 */     s2.add("b", "z");
/* 358 */     ST s3 = group.getInstanceOf("return");
/* 359 */     s3.add("x", "3.14159");
/* 360 */     st.add("stats", s1);
/* 361 */     st.add("stats", s2);
/* 362 */     st.add("stats", s3);
/*     */ 
/* 364 */     STViz viz = st.inspect();
/* 365 */     System.out.println(st.render());
/*     */   }
/*     */ 
/*     */   public static void test2() throws IOException {
/* 369 */     String templates = "t1(q1=\"Some\\nText\") ::= <<\n<q1>\n>>\n\nt2(p1) ::= <<\n<p1>\n>>\n\nmain() ::= <<\nSTART-<t1()>-END\n\nSTART-<t2(p1=\"Some\\nText\")>-END\n>>\n";
/*     */ 
/* 384 */     String tmpdir = System.getProperty("java.io.tmpdir");
/* 385 */     writeFile(tmpdir, "t.stg", templates);
/* 386 */     STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
/* 387 */     ST st = group.getInstanceOf("main");
/* 388 */     STViz viz = st.inspect();
/*     */   }
/*     */ 
/*     */   public static void test3() throws IOException {
/* 392 */     String templates = "main() ::= <<\nFoo: <{bar};format=\"lower\">\n>>\n";
/*     */ 
/* 397 */     String tmpdir = System.getProperty("java.io.tmpdir");
/* 398 */     writeFile(tmpdir, "t.stg", templates);
/* 399 */     STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
/* 400 */     ST st = group.getInstanceOf("main");
/* 401 */     st.inspect();
/*     */   }
/*     */ 
/*     */   public static void test4() throws IOException {
/* 405 */     String templates = "main(t) ::= <<\nhi: <t>\n>>\nfoo(x,y={hi}) ::= \"<bar(x,y)>\"\nbar(x,y) ::= << <y> >>\nignore(m) ::= \"<m>\"\n";
/*     */ 
/* 413 */     STGroup group = new STGroupString(templates);
/* 414 */     ST st = group.getInstanceOf("main");
/* 415 */     ST foo = group.getInstanceOf("foo");
/* 416 */     st.add("t", foo);
/* 417 */     ST ignore = group.getInstanceOf("ignore");
/* 418 */     ignore.add("m", foo);
/* 419 */     st.inspect();
/* 420 */     st.render();
/*     */   }
/*     */ 
/*     */   public static void writeFile(String dir, String fileName, String content) {
/*     */     try {
/* 425 */       File f = new File(dir, fileName);
/* 426 */       if (!f.getParentFile().exists()) f.getParentFile().mkdirs();
/* 427 */       FileWriter w = new FileWriter(f);
/* 428 */       BufferedWriter bw = new BufferedWriter(w);
/* 429 */       bw.write(content);
/* 430 */       bw.close();
/* 431 */       w.close();
/*     */     }
/*     */     catch (IOException ioe) {
/* 434 */       System.err.println("can't write file");
/* 435 */       ioe.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.gui.STViz
 * JD-Core Version:    0.6.2
 */