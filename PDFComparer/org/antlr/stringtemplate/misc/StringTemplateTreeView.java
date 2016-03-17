/*    */ package org.antlr.stringtemplate.misc;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Frame;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import javax.swing.JFrame;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.stringtemplate.StringTemplateGroup;
/*    */ 
/*    */ public class StringTemplateTreeView extends JFrame
/*    */ {
/*    */   static final int WIDTH = 200;
/*    */   static final int HEIGHT = 300;
/*    */ 
/*    */   public StringTemplateTreeView(String label, StringTemplate st)
/*    */   {
/* 56 */     super(label);
/*    */ 
/* 58 */     JTreeStringTemplatePanel tp = new JTreeStringTemplatePanel(new JTreeStringTemplateModel(st), null);
/*    */ 
/* 60 */     Container content = getContentPane();
/* 61 */     content.add(tp, "Center");
/* 62 */     addWindowListener(new WindowAdapter() {
/*    */       public void windowClosing(WindowEvent e) {
/* 64 */         Frame f = (Frame)e.getSource();
/* 65 */         f.setVisible(false);
/* 66 */         f.dispose();
/*    */       }
/*    */     });
/* 70 */     setSize(200, 300);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 74 */     StringTemplateGroup group = new StringTemplateGroup("dummy");
/* 75 */     StringTemplate bold = group.defineTemplate("bold", "<b>$attr$</b>");
/* 76 */     StringTemplate banner = group.defineTemplate("banner", "the banner");
/* 77 */     StringTemplate st = new StringTemplate(group, "<html>\n$banner(a=b)$<p><b>$name$:$email$</b>$if(member)$<i>$fontTag$member</font></i>$endif$");
/*    */ 
/* 84 */     st.setAttribute("name", "Terence");
/* 85 */     st.setAttribute("name", "Tom");
/* 86 */     st.setAttribute("email", "parrt@cs.usfca.edu");
/* 87 */     st.setAttribute("templateAttr", bold);
/* 88 */     StringTemplateTreeView frame = new StringTemplateTreeView("StringTemplate JTree Example", st);
/*    */ 
/* 90 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.misc.StringTemplateTreeView
 * JD-Core Version:    0.6.2
 */