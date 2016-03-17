/*     */ package org.apache.pdfbox.pdfviewer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class PDFTreeModel
/*     */   implements TreeModel
/*     */ {
/*     */   private PDDocument document;
/*     */ 
/*     */   public PDFTreeModel()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDFTreeModel(PDDocument doc)
/*     */   {
/*  71 */     setDocument(doc);
/*     */   }
/*     */ 
/*     */   public void setDocument(PDDocument doc)
/*     */   {
/*  81 */     this.document = doc;
/*     */   }
/*     */ 
/*     */   public void addTreeModelListener(TreeModelListener l)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Object getChild(Object parent, int index)
/*     */   {
/* 112 */     Object retval = null;
/* 113 */     if ((parent instanceof COSArray))
/*     */     {
/* 115 */       ArrayEntry entry = new ArrayEntry();
/* 116 */       entry.setIndex(index);
/* 117 */       entry.setValue(((COSArray)parent).getObject(index));
/* 118 */       retval = entry;
/*     */     }
/* 120 */     else if ((parent instanceof COSDictionary))
/*     */     {
/* 122 */       COSDictionary dict = (COSDictionary)parent;
/* 123 */       List keys = new ArrayList(dict.keySet());
/* 124 */       Collections.sort(keys);
/* 125 */       Object key = keys.get(index);
/* 126 */       Object value = dict.getDictionaryObject((COSName)key);
/* 127 */       MapEntry entry = new MapEntry();
/* 128 */       entry.setKey(key);
/* 129 */       entry.setValue(value);
/* 130 */       retval = entry;
/*     */     }
/* 132 */     else if ((parent instanceof MapEntry))
/*     */     {
/* 134 */       retval = getChild(((MapEntry)parent).getValue(), index);
/*     */     }
/* 136 */     else if ((parent instanceof ArrayEntry))
/*     */     {
/* 138 */       retval = getChild(((ArrayEntry)parent).getValue(), index);
/*     */     }
/* 140 */     else if ((parent instanceof COSDocument))
/*     */     {
/* 142 */       retval = ((COSDocument)parent).getObjects().get(index);
/*     */     }
/* 144 */     else if ((parent instanceof COSObject))
/*     */     {
/* 146 */       retval = ((COSObject)parent).getObject();
/*     */     }
/*     */     else
/*     */     {
/* 150 */       throw new RuntimeException("Unknown COS type " + parent.getClass().getName());
/*     */     }
/* 152 */     return retval;
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object parent)
/*     */   {
/* 166 */     int retval = 0;
/* 167 */     if ((parent instanceof COSArray))
/*     */     {
/* 169 */       retval = ((COSArray)parent).size();
/*     */     }
/* 171 */     else if ((parent instanceof COSDictionary))
/*     */     {
/* 173 */       retval = ((COSDictionary)parent).size();
/*     */     }
/* 175 */     else if ((parent instanceof MapEntry))
/*     */     {
/* 177 */       retval = getChildCount(((MapEntry)parent).getValue());
/*     */     }
/* 179 */     else if ((parent instanceof ArrayEntry))
/*     */     {
/* 181 */       retval = getChildCount(((ArrayEntry)parent).getValue());
/*     */     }
/* 183 */     else if ((parent instanceof COSDocument))
/*     */     {
/* 185 */       retval = ((COSDocument)parent).getObjects().size();
/*     */     }
/* 187 */     else if ((parent instanceof COSObject))
/*     */     {
/* 189 */       retval = 1;
/*     */     }
/* 191 */     return retval;
/*     */   }
/*     */ 
/*     */   public int getIndexOfChild(Object parent, Object child)
/*     */   {
/* 206 */     int retval = -1;
/* 207 */     if ((parent != null) && (child != null))
/*     */     {
/* 209 */       if ((parent instanceof COSArray))
/*     */       {
/* 211 */         COSArray array = (COSArray)parent;
/* 212 */         if ((child instanceof ArrayEntry))
/*     */         {
/* 214 */           ArrayEntry arrayEntry = (ArrayEntry)child;
/* 215 */           retval = arrayEntry.getIndex();
/*     */         }
/*     */         else
/*     */         {
/* 219 */           retval = array.indexOf((COSBase)child);
/*     */         }
/*     */       }
/* 222 */       else if ((parent instanceof COSDictionary))
/*     */       {
/* 224 */         MapEntry entry = (MapEntry)child;
/* 225 */         COSDictionary dict = (COSDictionary)parent;
/* 226 */         List keys = new ArrayList(dict.keySet());
/* 227 */         Collections.sort(keys);
/* 228 */         for (int i = 0; (retval == -1) && (i < keys.size()); i++)
/*     */         {
/* 230 */           if (((COSName)keys.get(i)).equals(entry.getKey()))
/*     */           {
/* 232 */             retval = i;
/*     */           }
/*     */         }
/*     */       }
/* 236 */       else if ((parent instanceof MapEntry))
/*     */       {
/* 238 */         retval = getIndexOfChild(((MapEntry)parent).getValue(), child);
/*     */       }
/* 240 */       else if ((parent instanceof ArrayEntry))
/*     */       {
/* 242 */         retval = getIndexOfChild(((ArrayEntry)parent).getValue(), child);
/*     */       }
/* 244 */       else if ((parent instanceof COSDocument))
/*     */       {
/* 246 */         retval = ((COSDocument)parent).getObjects().indexOf(child);
/*     */       }
/* 248 */       else if ((parent instanceof COSObject))
/*     */       {
/* 250 */         retval = 0;
/*     */       }
/*     */       else
/*     */       {
/* 254 */         throw new RuntimeException("Unknown COS type " + parent.getClass().getName());
/*     */       }
/*     */     }
/* 257 */     return retval;
/*     */   }
/*     */ 
/*     */   public Object getRoot()
/*     */   {
/* 268 */     return this.document.getDocument().getTrailer();
/*     */   }
/*     */ 
/*     */   public boolean isLeaf(Object node)
/*     */   {
/* 284 */     boolean isLeaf = (!(node instanceof COSDictionary)) && (!(node instanceof COSArray)) && (!(node instanceof COSDocument)) && (!(node instanceof COSObject)) && ((!(node instanceof MapEntry)) || (isLeaf(((MapEntry)node).getValue()))) && ((!(node instanceof ArrayEntry)) || (isLeaf(((ArrayEntry)node).getValue())));
/*     */ 
/* 290 */     return isLeaf;
/*     */   }
/*     */ 
/*     */   public void removeTreeModelListener(TreeModelListener l)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void valueForPathChanged(TreePath path, Object newValue)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.PDFTreeModel
 * JD-Core Version:    0.6.2
 */