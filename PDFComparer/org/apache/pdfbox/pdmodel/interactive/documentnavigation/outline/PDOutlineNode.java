/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDOutlineNode
/*     */   implements COSObjectable
/*     */ {
/*     */   protected COSDictionary node;
/*     */ 
/*     */   public PDOutlineNode()
/*     */   {
/*  42 */     this.node = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDOutlineNode(COSDictionary dict)
/*     */   {
/*  52 */     this.node = dict;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  62 */     return this.node;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  72 */     return this.node;
/*     */   }
/*     */ 
/*     */   protected PDOutlineNode getParent()
/*     */   {
/*  83 */     PDOutlineNode retval = null;
/*  84 */     COSDictionary parent = (COSDictionary)this.node.getDictionaryObject("Parent", "P");
/*  85 */     if (parent != null)
/*     */     {
/*  87 */       if (parent.getDictionaryObject("Parent", "P") == null)
/*     */       {
/*  89 */         retval = new PDDocumentOutline(parent);
/*     */       }
/*     */       else
/*     */       {
/*  93 */         retval = new PDOutlineItem(parent);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     return retval;
/*     */   }
/*     */ 
/*     */   protected void setParent(PDOutlineNode parent)
/*     */   {
/* 108 */     this.node.setItem("Parent", parent);
/*     */   }
/*     */ 
/*     */   public void appendChild(PDOutlineItem outlineNode)
/*     */   {
/* 118 */     outlineNode.setParent(this);
/* 119 */     if (getFirstChild() == null)
/*     */     {
/* 121 */       int currentOpenCount = getOpenCount();
/* 122 */       setFirstChild(outlineNode);
/*     */ 
/* 124 */       int numberOfOpenNodesWeAreAdding = 1;
/* 125 */       if (outlineNode.isNodeOpen())
/*     */       {
/* 127 */         numberOfOpenNodesWeAreAdding += outlineNode.getOpenCount();
/*     */       }
/* 129 */       if (isNodeOpen())
/*     */       {
/* 131 */         setOpenCount(currentOpenCount + numberOfOpenNodesWeAreAdding);
/*     */       }
/*     */       else
/*     */       {
/* 135 */         setOpenCount(currentOpenCount - numberOfOpenNodesWeAreAdding);
/*     */       }
/* 137 */       updateParentOpenCount(numberOfOpenNodesWeAreAdding);
/*     */     }
/*     */     else
/*     */     {
/* 141 */       PDOutlineItem previousLastChild = getLastChild();
/* 142 */       previousLastChild.insertSiblingAfter(outlineNode);
/*     */     }
/*     */ 
/* 145 */     PDOutlineItem lastNode = outlineNode;
/* 146 */     while (lastNode.getNextSibling() != null)
/*     */     {
/* 148 */       lastNode = lastNode.getNextSibling();
/*     */     }
/* 150 */     setLastChild(lastNode);
/*     */   }
/*     */ 
/*     */   public PDOutlineItem getFirstChild()
/*     */   {
/* 160 */     PDOutlineItem last = null;
/* 161 */     COSDictionary lastDic = (COSDictionary)this.node.getDictionaryObject("First");
/* 162 */     if (lastDic != null)
/*     */     {
/* 164 */       last = new PDOutlineItem(lastDic);
/*     */     }
/* 166 */     return last;
/*     */   }
/*     */ 
/*     */   protected void setFirstChild(PDOutlineNode outlineNode)
/*     */   {
/* 176 */     this.node.setItem("First", outlineNode);
/*     */   }
/*     */ 
/*     */   public PDOutlineItem getLastChild()
/*     */   {
/* 186 */     PDOutlineItem last = null;
/* 187 */     COSDictionary lastDic = (COSDictionary)this.node.getDictionaryObject("Last");
/* 188 */     if (lastDic != null)
/*     */     {
/* 190 */       last = new PDOutlineItem(lastDic);
/*     */     }
/* 192 */     return last;
/*     */   }
/*     */ 
/*     */   protected void setLastChild(PDOutlineNode outlineNode)
/*     */   {
/* 202 */     this.node.setItem("Last", outlineNode);
/*     */   }
/*     */ 
/*     */   public int getOpenCount()
/*     */   {
/* 214 */     return this.node.getInt("Count", 0);
/*     */   }
/*     */ 
/*     */   protected void setOpenCount(int openCount)
/*     */   {
/* 225 */     this.node.setInt("Count", openCount);
/*     */   }
/*     */ 
/*     */   public void openNode()
/*     */   {
/* 236 */     if (!isNodeOpen())
/*     */     {
/* 238 */       int openChildrenCount = 0;
/* 239 */       PDOutlineItem currentChild = getFirstChild();
/* 240 */       while (currentChild != null)
/*     */       {
/* 243 */         openChildrenCount++;
/*     */ 
/* 245 */         if (currentChild.isNodeOpen())
/*     */         {
/* 247 */           openChildrenCount += currentChild.getOpenCount();
/*     */         }
/* 249 */         currentChild = currentChild.getNextSibling();
/*     */       }
/* 251 */       setOpenCount(openChildrenCount);
/* 252 */       updateParentOpenCount(openChildrenCount);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void closeNode()
/*     */   {
/* 263 */     if (isNodeOpen())
/*     */     {
/* 265 */       int openCount = getOpenCount();
/* 266 */       updateParentOpenCount(-openCount);
/* 267 */       setOpenCount(-openCount);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isNodeOpen()
/*     */   {
/* 277 */     return getOpenCount() > 0;
/*     */   }
/*     */ 
/*     */   protected void updateParentOpenCount(int amount)
/*     */   {
/* 289 */     PDOutlineNode parent = getParent();
/* 290 */     if (parent != null)
/*     */     {
/* 292 */       int currentCount = parent.getOpenCount();
/*     */ 
/* 295 */       boolean negative = (currentCount < 0) || (parent.getCOSDictionary().getDictionaryObject("Count") == null);
/*     */ 
/* 297 */       currentCount = Math.abs(currentCount);
/* 298 */       currentCount += amount;
/* 299 */       if (negative)
/*     */       {
/* 301 */         currentCount = -currentCount;
/*     */       }
/* 303 */       parent.setOpenCount(currentCount);
/*     */ 
/* 306 */       if (!negative)
/*     */       {
/* 308 */         parent.updateParentOpenCount(amount);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode
 * JD-Core Version:    0.6.2
 */