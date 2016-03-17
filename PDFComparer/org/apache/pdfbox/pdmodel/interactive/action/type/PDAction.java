/*     */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ 
/*     */ public abstract class PDAction
/*     */   implements PDDestinationOrAction
/*     */ {
/*     */   public static final String TYPE = "Action";
/*     */   protected COSDictionary action;
/*     */ 
/*     */   public PDAction()
/*     */   {
/*  55 */     this.action = new COSDictionary();
/*  56 */     setType("Action");
/*     */   }
/*     */ 
/*     */   public PDAction(COSDictionary a)
/*     */   {
/*  66 */     this.action = a;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  76 */     return this.action;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  86 */     return this.action;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  97 */     return this.action.getNameAsString("Type");
/*     */   }
/*     */ 
/*     */   public void setType(String type)
/*     */   {
/* 108 */     this.action.setName("Type", type);
/*     */   }
/*     */ 
/*     */   public String getSubType()
/*     */   {
/* 119 */     return this.action.getNameAsString("S");
/*     */   }
/*     */ 
/*     */   public void setSubType(String s)
/*     */   {
/* 130 */     this.action.setName("S", s);
/*     */   }
/*     */ 
/*     */   public List getNext()
/*     */   {
/* 142 */     List retval = null;
/* 143 */     COSBase next = this.action.getDictionaryObject("Next");
/* 144 */     if ((next instanceof COSDictionary))
/*     */     {
/* 146 */       PDAction pdAction = PDActionFactory.createAction((COSDictionary)next);
/* 147 */       retval = new COSArrayList(pdAction, next, this.action, COSName.getPDFName("Next"));
/*     */     }
/* 149 */     else if ((next instanceof COSArray))
/*     */     {
/* 151 */       COSArray array = (COSArray)next;
/* 152 */       List actions = new ArrayList();
/* 153 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 155 */         actions.add(PDActionFactory.createAction((COSDictionary)array.getObject(i)));
/*     */       }
/* 157 */       retval = new COSArrayList(actions, array);
/*     */     }
/*     */ 
/* 160 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setNext(List next)
/*     */   {
/* 172 */     this.action.setItem("Next", COSArrayList.converterToCOSArray(next));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDAction
 * JD-Core Version:    0.6.2
 */