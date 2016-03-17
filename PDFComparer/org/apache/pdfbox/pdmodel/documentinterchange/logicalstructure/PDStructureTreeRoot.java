/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSDictionaryMap;
/*     */ import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
/*     */ import org.apache.pdfbox.pdmodel.common.PDNumberTreeNode;
/*     */ 
/*     */ public class PDStructureTreeRoot extends PDStructureNode
/*     */ {
/*  46 */   private static final Log LOG = LogFactory.getLog(PDStructureTreeRoot.class);
/*     */   private static final String TYPE = "StructTreeRoot";
/*     */ 
/*     */   public PDStructureTreeRoot()
/*     */   {
/*  56 */     super("StructTreeRoot");
/*     */   }
/*     */ 
/*     */   public PDStructureTreeRoot(COSDictionary dic)
/*     */   {
/*  66 */     super(dic);
/*     */   }
/*     */ 
/*     */   public COSArray getKArray()
/*     */   {
/*  76 */     COSBase k = getCOSDictionary().getDictionaryObject(COSName.K);
/*  77 */     if (k != null)
/*     */     {
/*  79 */       if ((k instanceof COSDictionary))
/*     */       {
/*  81 */         COSDictionary kdict = (COSDictionary)k;
/*  82 */         k = kdict.getDictionaryObject(COSName.K);
/*  83 */         if ((k instanceof COSArray))
/*     */         {
/*  85 */           return (COSArray)k;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  90 */         return (COSArray)k;
/*     */       }
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public COSBase getK()
/*     */   {
/* 103 */     return getCOSDictionary().getDictionaryObject(COSName.K);
/*     */   }
/*     */ 
/*     */   public void setK(COSBase k)
/*     */   {
/* 113 */     getCOSDictionary().setItem(COSName.K, k);
/*     */   }
/*     */ 
/*     */   public PDNameTreeNode getIDTree()
/*     */   {
/* 123 */     COSDictionary idTreeDic = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.ID_TREE);
/* 124 */     if (idTreeDic != null)
/*     */     {
/* 126 */       return new PDNameTreeNode(idTreeDic, PDStructureElement.class);
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   public void setIDTree(PDNameTreeNode idTree)
/*     */   {
/* 138 */     getCOSDictionary().setItem(COSName.ID_TREE, idTree);
/*     */   }
/*     */ 
/*     */   public PDNumberTreeNode getParentTree()
/*     */   {
/* 148 */     COSDictionary parentTreeDic = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.PARENT_TREE);
/* 149 */     if (parentTreeDic != null)
/*     */     {
/* 151 */       return new PDNumberTreeNode(parentTreeDic, COSBase.class);
/*     */     }
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   public void setParentTree(PDNumberTreeNode parentTree)
/*     */   {
/* 163 */     getCOSDictionary().setItem(COSName.PARENT_TREE, parentTree);
/*     */   }
/*     */ 
/*     */   public int getParentTreeNextKey()
/*     */   {
/* 173 */     return getCOSDictionary().getInt(COSName.PARENT_TREE_NEXT_KEY);
/*     */   }
/*     */ 
/*     */   public void setParentTreeNextKey(int parentTreeNextkey)
/*     */   {
/* 183 */     getCOSDictionary().setInt(COSName.PARENT_TREE_NEXT_KEY, parentTreeNextkey);
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getRoleMap()
/*     */   {
/* 193 */     COSBase rm = getCOSDictionary().getDictionaryObject(COSName.ROLE_MAP);
/* 194 */     if ((rm instanceof COSDictionary))
/*     */     {
/*     */       try
/*     */       {
/* 198 */         return COSDictionaryMap.convertBasicTypesToMap((COSDictionary)rm);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 202 */         LOG.error(e, e);
/*     */       }
/*     */     }
/* 205 */     return new Hashtable();
/*     */   }
/*     */ 
/*     */   public void setRoleMap(Map<String, String> roleMap)
/*     */   {
/* 215 */     COSDictionary rmDic = new COSDictionary();
/* 216 */     for (String key : roleMap.keySet())
/*     */     {
/* 218 */       rmDic.setName(key, (String)roleMap.get(key));
/*     */     }
/* 220 */     getCOSDictionary().setItem(COSName.ROLE_MAP, rmDic);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot
 * JD-Core Version:    0.6.2
 */