/*    */ package org.antlr.gunit.swingui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.ImageIcon;
/*    */ 
/*    */ public class ImageFactory
/*    */ {
/*    */   private static ImageFactory singleton;
/*    */   private static final String IMG_DIR = "org/antlr/gunit/swingui/images/";
/*    */   public ImageIcon ACCEPT;
/*    */   public ImageIcon ADD;
/*    */   public ImageIcon DELETE;
/*    */   public ImageIcon TEXTFILE;
/*    */   public ImageIcon ADDFILE;
/*    */   public ImageIcon TEXTFILE16;
/*    */   public ImageIcon WINDOW16;
/*    */   public ImageIcon FAV16;
/*    */   public ImageIcon SAVE;
/*    */   public ImageIcon OPEN;
/*    */   public ImageIcon EDIT16;
/*    */   public ImageIcon FILE16;
/*    */   public ImageIcon NEXT;
/*    */   public ImageIcon RUN_PASS;
/*    */   public ImageIcon RUN_FAIL;
/*    */   public ImageIcon TESTSUITE;
/*    */   public ImageIcon TESTGROUP;
/*    */   public ImageIcon TESTGROUPX;
/*    */ 
/*    */   public static ImageFactory getSingleton()
/*    */   {
/* 15 */     if (singleton == null) singleton = new ImageFactory();
/* 16 */     return singleton;
/*    */   }
/*    */ 
/*    */   private ImageFactory() {
/* 20 */     this.ACCEPT = getImage("accept.png");
/* 21 */     this.ADD = getImage("add.png");
/* 22 */     this.DELETE = getImage("delete24.png");
/* 23 */     this.TEXTFILE = getImage("textfile24.png");
/* 24 */     this.TEXTFILE16 = getImage("textfile16.png");
/* 25 */     this.ADDFILE = getImage("addfile24.png");
/* 26 */     this.WINDOW16 = getImage("windowb16.png");
/* 27 */     this.FAV16 = getImage("favb16.png");
/* 28 */     this.SAVE = getImage("floppy24.png");
/* 29 */     this.OPEN = getImage("folder24.png");
/* 30 */     this.EDIT16 = getImage("edit16.png");
/* 31 */     this.FILE16 = getImage("file16.png");
/* 32 */     this.RUN_PASS = getImage("runpass.png");
/* 33 */     this.RUN_FAIL = getImage("runfail.png");
/* 34 */     this.TESTSUITE = getImage("testsuite.png");
/* 35 */     this.TESTGROUP = getImage("testgroup.png");
/* 36 */     this.TESTGROUPX = getImage("testgroupx.png");
/* 37 */     this.NEXT = getImage("next24.png");
/*    */   }
/*    */ 
/*    */   private ImageIcon getImage(String name) {
/* 41 */     name = "org/antlr/gunit/swingui/images/" + name;
/*    */     try {
/* 43 */       ClassLoader loader = ImageFactory.class.getClassLoader();
/* 44 */       InputStream in = loader.getResourceAsStream(name);
/* 45 */       byte[] data = new byte[in.available()];
/* 46 */       in.read(data);
/* 47 */       in.close();
/* 48 */       return new ImageIcon(data);
/*    */     } catch (IOException ex) {
/* 50 */       System.err.println("Can't load image file: " + name);
/* 51 */       System.exit(1);
/*    */     } catch (RuntimeException e) {
/* 53 */       System.err.println("Can't load image file: " + name);
/* 54 */       System.exit(1);
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.ImageFactory
 * JD-Core Version:    0.6.2
 */