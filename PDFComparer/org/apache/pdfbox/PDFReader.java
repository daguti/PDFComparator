/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.pdfviewer.PageWrapper;
/*     */ import org.apache.pdfbox.pdfviewer.ReaderBottomPanel;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDPageable;
/*     */ import org.apache.pdfbox.util.ExtensionFileFilter;
/*     */ import org.apache.pdfbox.util.ImageIOUtil;
/*     */ 
/*     */ public class PDFReader extends JFrame
/*     */ {
/*  48 */   private File currentDir = new File(".");
/*     */   private JMenuItem saveAsImageMenuItem;
/*     */   private JMenuItem exitMenuItem;
/*     */   private JMenu fileMenu;
/*     */   private JMenuBar menuBar;
/*     */   private JMenuItem openMenuItem;
/*     */   private JMenuItem printMenuItem;
/*     */   private JMenu viewMenu;
/*     */   private JMenuItem nextPageItem;
/*     */   private JMenuItem previousPageItem;
/*  58 */   private JPanel documentPanel = new JPanel();
/*  59 */   private ReaderBottomPanel bottomStatusPanel = new ReaderBottomPanel();
/*     */ 
/*  61 */   private PDDocument document = null;
/*  62 */   private List<PDPage> pages = null;
/*     */ 
/*  64 */   private int currentPage = 0;
/*  65 */   private int numberOfPages = 0;
/*  66 */   private String currentFilename = null;
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String NONSEQ = "-nonSeq";
/*  70 */   private static boolean useNonSeqParser = false;
/*     */ 
/*  72 */   private static final String VERSION = Version.getVersion();
/*  73 */   private static final String BASETITLE = "PDFBox " + VERSION;
/*     */ 
/*     */   public PDFReader()
/*     */   {
/*  80 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  91 */     this.menuBar = new JMenuBar();
/*  92 */     this.fileMenu = new JMenu();
/*  93 */     this.openMenuItem = new JMenuItem();
/*  94 */     this.saveAsImageMenuItem = new JMenuItem();
/*  95 */     this.exitMenuItem = new JMenuItem();
/*  96 */     this.printMenuItem = new JMenuItem();
/*  97 */     this.viewMenu = new JMenu();
/*  98 */     this.nextPageItem = new JMenuItem();
/*  99 */     this.previousPageItem = new JMenuItem();
/*     */ 
/* 102 */     setTitle(BASETITLE);
/* 103 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent evt)
/*     */       {
/* 108 */         PDFReader.this.exitApplication();
/*     */       }
/*     */     });
/* 113 */     JScrollPane documentScroller = new JScrollPane();
/* 114 */     documentScroller.setViewportView(this.documentPanel);
/*     */ 
/* 117 */     getContentPane().add(documentScroller, "Center");
/* 118 */     getContentPane().add(this.bottomStatusPanel, "South");
/*     */ 
/* 120 */     this.fileMenu.setText("File");
/* 121 */     this.openMenuItem.setText("Open");
/* 122 */     this.openMenuItem.setToolTipText("Open PDF file");
/* 123 */     this.openMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 127 */         PDFReader.this.openMenuItemActionPerformed(evt);
/*     */       }
/*     */     });
/* 131 */     this.fileMenu.add(this.openMenuItem);
/*     */ 
/* 133 */     this.printMenuItem.setText("Print");
/* 134 */     this.printMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/*     */         try
/*     */         {
/* 140 */           if (PDFReader.this.document != null)
/*     */           {
/* 142 */             PDPageable pageable = new PDPageable(PDFReader.this.document);
/* 143 */             PrinterJob job = pageable.getPrinterJob();
/* 144 */             job.setPageable(pageable);
/* 145 */             if (job.printDialog())
/*     */             {
/* 147 */               job.print();
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (PrinterException e)
/*     */         {
/* 153 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/* 157 */     this.fileMenu.add(this.printMenuItem);
/*     */ 
/* 159 */     this.saveAsImageMenuItem.setText("Save as image");
/* 160 */     this.saveAsImageMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 164 */         if (PDFReader.this.document != null)
/*     */         {
/* 166 */           PDFReader.this.saveImage();
/*     */         }
/*     */       }
/*     */     });
/* 170 */     this.fileMenu.add(this.saveAsImageMenuItem);
/*     */ 
/* 172 */     this.exitMenuItem.setText("Exit");
/* 173 */     this.exitMenuItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 177 */         PDFReader.this.exitApplication();
/*     */       }
/*     */     });
/* 181 */     this.fileMenu.add(this.exitMenuItem);
/*     */ 
/* 183 */     this.menuBar.add(this.fileMenu);
/*     */ 
/* 185 */     this.viewMenu.setText("View");
/* 186 */     this.nextPageItem.setText("Next page");
/* 187 */     this.nextPageItem.setAccelerator(KeyStroke.getKeyStroke('+'));
/* 188 */     this.nextPageItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 192 */         PDFReader.this.nextPage();
/*     */       }
/*     */     });
/* 195 */     this.viewMenu.add(this.nextPageItem);
/*     */ 
/* 197 */     this.previousPageItem.setText("Previous page");
/* 198 */     this.previousPageItem.setAccelerator(KeyStroke.getKeyStroke('-'));
/* 199 */     this.previousPageItem.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt)
/*     */       {
/* 203 */         PDFReader.this.previousPage();
/*     */       }
/*     */     });
/* 206 */     this.viewMenu.add(this.previousPageItem);
/*     */ 
/* 208 */     this.menuBar.add(this.viewMenu);
/*     */ 
/* 210 */     setJMenuBar(this.menuBar);
/*     */ 
/* 213 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 214 */     setBounds((screenSize.width - 700) / 2, (screenSize.height - 600) / 2, 700, 600);
/*     */   }
/*     */ 
/*     */   private void updateTitle()
/*     */   {
/* 220 */     setTitle(BASETITLE + ": " + this.currentFilename + " (" + (this.currentPage + 1) + "/" + this.numberOfPages + ")");
/*     */   }
/*     */ 
/*     */   private void nextPage()
/*     */   {
/* 225 */     if (this.currentPage < this.numberOfPages - 1)
/*     */     {
/* 227 */       this.currentPage += 1;
/* 228 */       updateTitle();
/* 229 */       showPage(this.currentPage);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void previousPage()
/*     */   {
/* 235 */     if (this.currentPage > 0)
/*     */     {
/* 237 */       this.currentPage -= 1;
/* 238 */       updateTitle();
/* 239 */       showPage(this.currentPage);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void openMenuItemActionPerformed(ActionEvent evt)
/*     */   {
/* 245 */     JFileChooser chooser = new JFileChooser();
/* 246 */     chooser.setCurrentDirectory(this.currentDir);
/*     */ 
/* 248 */     ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[] { "PDF" }, "PDF Files");
/* 249 */     chooser.setFileFilter(pdfFilter);
/* 250 */     int result = chooser.showOpenDialog(this);
/* 251 */     if (result == 0)
/*     */     {
/* 253 */       String name = chooser.getSelectedFile().getPath();
/* 254 */       this.currentDir = new File(name).getParentFile();
/*     */       try
/*     */       {
/* 257 */         openPDFFile(name, "");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 261 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void exitApplication()
/*     */   {
/*     */     try
/*     */     {
/* 270 */       if (this.document != null)
/*     */       {
/* 272 */         this.document.close();
/*     */       }
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/*     */     }
/*     */ 
/* 279 */     setVisible(false);
/* 280 */     dispose();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 290 */     PDFReader viewer = new PDFReader();
/* 291 */     String password = "";
/* 292 */     String filename = null;
/* 293 */     for (int i = 0; i < args.length; i++)
/*     */     {
/* 295 */       if (args[i].equals("-password"))
/*     */       {
/* 297 */         i++;
/* 298 */         if (i >= args.length)
/*     */         {
/* 300 */           usage();
/*     */         }
/* 302 */         password = args[i];
/*     */       }
/* 304 */       if (args[i].equals("-nonSeq"))
/*     */       {
/* 306 */         useNonSeqParser = true;
/*     */       }
/*     */       else
/*     */       {
/* 310 */         filename = args[i];
/*     */       }
/*     */     }
/*     */ 
/* 314 */     if (filename != null)
/*     */     {
/* 316 */       viewer.openPDFFile(filename, password);
/*     */     }
/* 318 */     viewer.setVisible(true);
/*     */   }
/*     */ 
/*     */   private void openPDFFile(String filename, String password) throws Exception
/*     */   {
/* 323 */     if (this.document != null)
/*     */     {
/* 325 */       this.document.close();
/* 326 */       this.documentPanel.removeAll();
/*     */     }
/*     */ 
/* 329 */     File file = new File(filename);
/* 330 */     parseDocument(file, password);
/* 331 */     this.pages = this.document.getDocumentCatalog().getAllPages();
/* 332 */     this.numberOfPages = this.pages.size();
/* 333 */     this.currentFilename = file.getAbsolutePath();
/* 334 */     this.currentPage = 0;
/* 335 */     updateTitle();
/* 336 */     showPage(0);
/*     */   }
/*     */ 
/*     */   private void showPage(int pageNumber)
/*     */   {
/*     */     try
/*     */     {
/* 343 */       PageWrapper wrapper = new PageWrapper(this);
/* 344 */       wrapper.displayPage((PDPage)this.pages.get(pageNumber));
/* 345 */       if (this.documentPanel.getComponentCount() > 0)
/*     */       {
/* 347 */         this.documentPanel.remove(0);
/*     */       }
/* 349 */       this.documentPanel.add(wrapper.getPanel());
/* 350 */       pack();
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/* 354 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveImage()
/*     */   {
/*     */     try
/*     */     {
/* 362 */       PDPage pageToSave = (PDPage)this.pages.get(this.currentPage);
/* 363 */       BufferedImage pageAsImage = pageToSave.convertToImage();
/* 364 */       String imageFilename = this.currentFilename;
/* 365 */       if (imageFilename.toLowerCase().endsWith(".pdf"))
/*     */       {
/* 367 */         imageFilename = imageFilename.substring(0, imageFilename.length() - 4);
/*     */       }
/* 369 */       imageFilename = imageFilename + "_" + (this.currentPage + 1) + ".png";
/* 370 */       ImageIOUtil.writeImage(pageAsImage, imageFilename, 300);
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/* 374 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void parseDocument(File file, String password)
/*     */     throws IOException
/*     */   {
/* 386 */     this.document = null;
/* 387 */     if (useNonSeqParser)
/*     */     {
/* 389 */       this.document = PDDocument.loadNonSeq(file, null, password);
/*     */     }
/*     */     else
/*     */     {
/* 393 */       this.document = PDDocument.load(file);
/* 394 */       if (this.document.isEncrypted())
/*     */       {
/*     */         try
/*     */         {
/* 398 */           this.document.decrypt(password);
/*     */         }
/*     */         catch (CryptographyException e)
/*     */         {
/* 402 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ReaderBottomPanel getBottomStatusPanel()
/*     */   {
/* 415 */     return this.bottomStatusPanel;
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 423 */     System.err.println("usage: java -jar pdfbox-app-" + VERSION + ".jar PDFReader [OPTIONS] <input-file>\n" + "  -password <password>      Password to decrypt the document\n" + "  -nonSeq                   Enables the new non-sequential parser\n" + "  <input-file>              The PDF document to be loaded\n");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFReader
 * JD-Core Version:    0.6.2
 */