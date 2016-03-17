/*    */ package org.apache.pdfbox.pdmodel.interactive.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionGoTo;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionJavaScript;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionLaunch;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionRemoteGoTo;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionURI;
/*    */ 
/*    */ public class PDActionFactory
/*    */ {
/*    */   public static PDAction createAction(COSDictionary action)
/*    */   {
/* 54 */     PDAction retval = null;
/* 55 */     if (action != null)
/*    */     {
/* 57 */       String type = action.getNameAsString("S");
/* 58 */       if ("JavaScript".equals(type))
/*    */       {
/* 60 */         retval = new PDActionJavaScript(action);
/*    */       }
/* 62 */       else if ("GoTo".equals(type))
/*    */       {
/* 64 */         retval = new PDActionGoTo(action);
/*    */       }
/* 66 */       else if ("Launch".equals(type))
/*    */       {
/* 68 */         retval = new PDActionLaunch(action);
/*    */       }
/* 70 */       else if ("GoToR".equals(type))
/*    */       {
/* 72 */         retval = new PDActionRemoteGoTo(action);
/*    */       }
/* 74 */       else if ("URI".equals(type))
/*    */       {
/* 76 */         retval = new PDActionURI(action);
/*    */       }
/*    */     }
/* 79 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory
 * JD-Core Version:    0.6.2
 */