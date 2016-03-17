/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTextPane;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.pdfviewer.ArrayEntry;
/*     */ import org.apache.pdfbox.pdfviewer.MapEntry;
/*     */ import org.apache.pdfbox.pdfviewer.PDFTreeCellRenderer;
/*     */ import org.apache.pdfbox.pdfviewer.PDFTreeModel;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.util.ExtensionFileFilter;
/*     */ 
/*     */ public class PDFDebugger extends JFrame
/*     */ {
/*  58 */   private File currentDir = new File(".");
/*  59 */   private PDDocument document = null;
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */   private static final String PASSWORD = "-password";
/*  64 */   private static boolean useNonSeqParser = false;
/*     */   private JMenuItem aboutMenuItem;
/*     */   private JMenuItem contentsMenuItem;
/*     */   private JMenuItem copyMenuItem;
/*     */   private JMenuItem cutMenuItem;
/*     */   private JMenuItem deleteMenuItem;
/*     */   private JMenu editMenu;
/*     */   private JMenuItem exitMenuItem;
/*     */   private JMenu fileMenu;
/*     */   private JMenu helpMenu;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JScrollPane jScrollPane2;
/*     */   private JSplitPane jSplitPane1;
/*     */   private JTextPane jTextPane1;
/*     */   private JTree jTree1;
/*     */   private JMenuBar menuBar;
/*     */   private JMenuItem openMenuItem;
/*     */   private JMenuItem pasteMenuItem;
/*     */   private JMenuItem saveAsMenuItem;
/*     */   private JMenuItem saveMenuItem;
/* 455 */   private JPanel documentPanel = new JPanel();
/*     */ 
/*     */   public PDFDebugger()
/*     */   {
/*  71 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  82 */     this.jSplitPane1 = new JSplitPane();
/*  83 */     this.jScrollPane1 = new JScrollPane();
/*  84 */     this.jTree1 = new JTree();
/*  85 */     this.jScrollPane2 = new JScrollPane();
/*  86 */     this.jTextPane1 = new JTextPane();
/*  87 */     this.menuBar = new JMenuBar();
/*  88 */     this.fileMenu = new JMenu();
/*  89 */     this.openMenuItem = new JMenuItem();
/*  90 */     this.saveMenuItem = new JMenuItem();
/*  91 */     this.saveAsMenuItem = new JMenuItem();
/*  92 */     this.exitMenuItem = new JMenuItem();
/*  93 */     this.editMenu = new JMenu();
/*  94 */     this.cutMenuItem = new JMenuItem();
/*  95 */     this.copyMenuItem = new JMenuItem();
/*  96 */     this.pasteMenuItem = new JMenuItem();
/*  97 */     this.deleteMenuItem = new JMenuItem();
/*  98 */     this.helpMenu = new JMenu();
/*  99 */     this.contentsMenuItem = new JMenuItem();
/* 100 */     this.aboutMenuItem = new JMenuItem();
/*     */ 
/* 102 */     this.jTree1.setCellRenderer(new PDFTreeCellRenderer());
/* 103 */     this.jTree1.setModel(null);
/*     */ 
/* 105 */     setTitle("PDFBox - PDF Viewer");
/* 106 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent evt)
/*     */       {
/* 110 */         PDFDebugger.this.exitForm(evt);
/*     */       }
/*     */     });
/* 115 */     this.jScrollPane1.setBorder(new BevelBorder(0));
/* 116 */     this.jScrollPane1.setPreferredSize(new Dimension(300, 500));
/* 117 */     this.jTree1.addTreeSelectionListener(new TreeSelectionListener()
/*     */     {
/*     */       public void valueChanged(TreeSelectionEvent evt)
/*     */       {
/* 121 */         PDFDebugger.this.jTree1ValueChanged(evt);
/*     */       }
/*     */     });
/* 125 */     this.jScrollPane1.setViewportView(this.jTree1);
/*     */ 
/* 127 */     this.jSplitPane1.setRightComponent(this.jScrollPane2);
/*     */ 
/* 129 */     this.jScrollPane2.setPreferredSize(new Dimension(300, 500));
/* 130 */     this.jScrollPane2.setViewportView(this.jTextPane1);
/*     */ 
/* 132 */     this.jSplitPane1.setLeftComponent(this.jScrollPane1);
/*     */ 
/* 134 */     JScrollPane documentScroller = new JScrollPane();
/*     */ 
/* 136 */     documentScroller.setViewportView(this.documentPanel);
/*     */ 
/* 138 */     getContentPane().add(this.jSplitPane1, "Center");
/*     */ 
/* 140 */     this.fileMenu.setText("File");
/* 141 */     this.openMenuItem.setText("Open");
/* 142 */     this.openMenuItem.setToolTipText("Open PDF file");
/* 143 */     this.openMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 147 */         PDFDebugger.this.openMenuItemActionPerformed(evt);
/*     */       }
/*     */     });
/* 151 */     this.fileMenu.add(this.openMenuItem);
/*     */ 
/* 153 */     this.saveMenuItem.setText("Save");
/*     */ 
/* 155 */     this.saveAsMenuItem.setText("Save As ...");
/*     */ 
/* 157 */     this.exitMenuItem.setText("Exit");
/* 158 */     this.exitMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 162 */         PDFDebugger.this.exitMenuItemActionPerformed(evt);
/*     */       }
/*     */     });
/* 166 */     this.fileMenu.add(this.exitMenuItem);
/*     */ 
/* 168 */     this.menuBar.add(this.fileMenu);
/*     */ 
/* 170 */     this.editMenu.setText("Edit");
/* 171 */     this.cutMenuItem.setText("Cut");
/* 172 */     this.editMenu.add(this.cutMenuItem);
/*     */ 
/* 174 */     this.copyMenuItem.setText("Copy");
/* 175 */     this.editMenu.add(this.copyMenuItem);
/*     */ 
/* 177 */     this.pasteMenuItem.setText("Paste");
/* 178 */     this.editMenu.add(this.pasteMenuItem);
/*     */ 
/* 180 */     this.deleteMenuItem.setText("Delete");
/* 181 */     this.editMenu.add(this.deleteMenuItem);
/*     */ 
/* 183 */     this.helpMenu.setText("Help");
/* 184 */     this.contentsMenuItem.setText("Contents");
/* 185 */     this.helpMenu.add(this.contentsMenuItem);
/*     */ 
/* 187 */     this.aboutMenuItem.setText("About");
/* 188 */     this.helpMenu.add(this.aboutMenuItem);
/*     */ 
/* 190 */     setJMenuBar(this.menuBar);
/*     */ 
/* 193 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 194 */     setBounds((screenSize.width - 700) / 2, (screenSize.height - 600) / 2, 700, 600);
/*     */   }
/*     */ 
/*     */   private void openMenuItemActionPerformed(ActionEvent evt)
/*     */   {
/* 199 */     JFileChooser chooser = new JFileChooser();
/* 200 */     chooser.setCurrentDirectory(this.currentDir);
/*     */ 
/* 202 */     ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[] { "pdf", "PDF" }, "PDF Files");
/* 203 */     chooser.setFileFilter(pdfFilter);
/* 204 */     int result = chooser.showOpenDialog(this);
/* 205 */     if (result == 0)
/*     */     {
/* 207 */       String name = chooser.getSelectedFile().getPath();
/* 208 */       this.currentDir = new File(name).getParentFile();
/*     */       try
/*     */       {
/* 211 */         readPDFFile(name, "");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 215 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void jTree1ValueChanged(TreeSelectionEvent evt)
/*     */   {
/* 222 */     TreePath path = this.jTree1.getSelectionPath();
/* 223 */     if (path != null)
/*     */     {
/*     */       try
/*     */       {
/* 227 */         Object selectedNode = path.getLastPathComponent();
/* 228 */         String data = convertToString(selectedNode);
/*     */ 
/* 232 */         if (data != null)
/*     */         {
/* 234 */           this.jTextPane1.setText(data);
/*     */         }
/*     */         else
/*     */         {
/* 238 */           this.jTextPane1.setText("");
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 243 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String convertToString(Object selectedNode)
/*     */   {
/* 250 */     String data = null;
/* 251 */     if ((selectedNode instanceof COSBoolean))
/*     */     {
/* 253 */       data = "" + ((COSBoolean)selectedNode).getValue();
/*     */     }
/* 255 */     else if ((selectedNode instanceof COSFloat))
/*     */     {
/* 257 */       data = "" + ((COSFloat)selectedNode).floatValue();
/*     */     }
/* 259 */     else if ((selectedNode instanceof COSNull))
/*     */     {
/* 261 */       data = "null";
/*     */     }
/* 263 */     else if ((selectedNode instanceof COSInteger))
/*     */     {
/* 265 */       data = "" + ((COSInteger)selectedNode).intValue();
/*     */     }
/* 267 */     else if ((selectedNode instanceof COSName))
/*     */     {
/* 269 */       data = "" + ((COSName)selectedNode).getName();
/*     */     }
/* 271 */     else if ((selectedNode instanceof COSString))
/*     */     {
/* 273 */       data = "" + ((COSString)selectedNode).getString();
/*     */     }
/* 275 */     else if ((selectedNode instanceof COSStream))
/*     */     {
/*     */       try
/*     */       {
/* 279 */         COSStream stream = (COSStream)selectedNode;
/* 280 */         InputStream ioStream = stream.getUnfilteredStream();
/* 281 */         ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
/* 282 */         byte[] buffer = new byte[1024];
/* 283 */         int amountRead = 0;
/* 284 */         while ((amountRead = ioStream.read(buffer, 0, buffer.length)) != -1)
/*     */         {
/* 286 */           byteArray.write(buffer, 0, amountRead);
/*     */         }
/* 288 */         data = byteArray.toString();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 292 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 295 */     else if ((selectedNode instanceof MapEntry))
/*     */     {
/* 297 */       data = convertToString(((MapEntry)selectedNode).getValue());
/*     */     }
/* 299 */     else if ((selectedNode instanceof ArrayEntry))
/*     */     {
/* 301 */       data = convertToString(((ArrayEntry)selectedNode).getValue());
/*     */     }
/* 303 */     return data;
/*     */   }
/*     */ 
/*     */   private void exitMenuItemActionPerformed(ActionEvent evt)
/*     */   {
/* 308 */     if (this.document != null)
/*     */     {
/*     */       try
/*     */       {
/* 312 */         this.document.close();
/*     */       }
/*     */       catch (IOException io)
/*     */       {
/* 316 */         io.printStackTrace();
/*     */       }
/*     */     }
/* 319 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   private void exitForm(WindowEvent evt)
/*     */   {
/* 327 */     if (this.document != null)
/*     */     {
/*     */       try
/*     */       {
/* 331 */         this.document.close();
/*     */       }
/*     */       catch (IOException io)
/*     */       {
/* 335 */         io.printStackTrace();
/*     */       }
/*     */     }
/* 338 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 348 */     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/* 349 */     PDFDebugger viewer = new PDFDebugger();
/* 350 */     String filename = null;
/* 351 */     String password = "";
/* 352 */     for (int i = 0; i < args.length; i++)
/*     */     {
/* 354 */       if (args[i].equals("-password"))
/*     */       {
/* 356 */         i++;
/* 357 */         if (i >= args.length)
/*     */         {
/* 359 */           usage();
/*     */         }
/* 361 */         password = args[i];
/*     */       }
/* 363 */       if (args[i].equals("-nonSeq"))
/*     */       {
/* 365 */         useNonSeqParser = true;
/*     */       }
/*     */       else
/*     */       {
/* 369 */         filename = args[i];
/*     */       }
/*     */     }
/*     */ 
/* 373 */     if (filename != null)
/*     */     {
/* 375 */       viewer.readPDFFile(filename, password);
/*     */     }
/* 377 */     viewer.setVisible(true);
/*     */   }
/*     */ 
/*     */   private void readPDFFile(String file, String password) throws Exception
/*     */   {
/* 382 */     if (this.document != null)
/*     */     {
/* 384 */       this.document.close();
/*     */     }
/* 386 */     File f = new File(file);
/* 387 */     parseDocument(f, password);
/* 388 */     TreeModel model = new PDFTreeModel(this.document);
/* 389 */     this.jTree1.setModel(model);
/* 390 */     setTitle("PDFBox - " + f.getAbsolutePath());
/*     */   }
/*     */ 
/*     */   private void parseDocument(File file, String password)
/*     */     throws IOException
/*     */   {
/* 401 */     if (useNonSeqParser)
/*     */     {
/* 403 */       this.document = PDDocument.loadNonSeq(file, null, password);
/*     */     }
/*     */     else
/*     */     {
/* 407 */       this.document = PDDocument.load(file);
/* 408 */       if (this.document.isEncrypted())
/*     */       {
/*     */         try
/*     */         {
/* 412 */           this.document.decrypt(password);
/*     */         }
/*     */         catch (CryptographyException e)
/*     */         {
/* 416 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 427 */     System.err.println("usage: java -jar pdfbox-app-x.y.z.jar PDFDebugger [OPTIONS] <input-file>\n  -password <password>      Password to decrypt the document\n  -nonSeq                   Enables the new non-sequential parser\n  <input-file>              The PDF document to be loaded\n");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFDebugger
 * JD-Core Version:    0.6.2
 */