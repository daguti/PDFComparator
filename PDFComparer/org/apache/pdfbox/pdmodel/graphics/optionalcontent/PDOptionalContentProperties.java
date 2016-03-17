/*     */ package org.apache.pdfbox.pdmodel.graphics.optionalcontent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDOptionalContentProperties
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dict;
/*     */ 
/*     */   public PDOptionalContentProperties()
/*     */   {
/*  89 */     this.dict = new COSDictionary();
/*  90 */     this.dict.setItem(COSName.OCGS, new COSArray());
/*  91 */     this.dict.setItem(COSName.D, new COSDictionary());
/*     */   }
/*     */ 
/*     */   public PDOptionalContentProperties(COSDictionary props)
/*     */   {
/* 100 */     this.dict = props;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 106 */     return this.dict;
/*     */   }
/*     */ 
/*     */   private COSArray getOCGs()
/*     */   {
/* 111 */     COSArray ocgs = (COSArray)this.dict.getItem(COSName.OCGS);
/* 112 */     if (ocgs == null)
/*     */     {
/* 114 */       ocgs = new COSArray();
/* 115 */       this.dict.setItem(COSName.OCGS, ocgs);
/*     */     }
/* 117 */     return ocgs;
/*     */   }
/*     */ 
/*     */   private COSDictionary getD()
/*     */   {
/* 122 */     COSDictionary d = (COSDictionary)this.dict.getDictionaryObject(COSName.D);
/* 123 */     if (d == null)
/*     */     {
/* 125 */       d = new COSDictionary();
/* 126 */       this.dict.setItem(COSName.D, d);
/*     */     }
/* 128 */     return d;
/*     */   }
/*     */ 
/*     */   public PDOptionalContentGroup getGroup(String name)
/*     */   {
/* 138 */     COSArray ocgs = getOCGs();
/* 139 */     for (COSBase o : ocgs)
/*     */     {
/* 141 */       COSDictionary ocg = toDictionary(o);
/* 142 */       String groupName = ocg.getString(COSName.NAME);
/* 143 */       if (groupName.equals(name))
/*     */       {
/* 145 */         return new PDOptionalContentGroup(ocg);
/*     */       }
/*     */     }
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   public void addGroup(PDOptionalContentGroup ocg)
/*     */   {
/* 157 */     COSArray ocgs = getOCGs();
/* 158 */     ocgs.add(ocg.getCOSObject());
/*     */ 
/* 161 */     COSArray order = (COSArray)getD().getDictionaryObject(COSName.ORDER);
/* 162 */     if (order == null)
/*     */     {
/* 164 */       order = new COSArray();
/* 165 */       getD().setItem(COSName.ORDER, order);
/*     */     }
/* 167 */     order.add(ocg);
/*     */   }
/*     */ 
/*     */   public Collection<PDOptionalContentGroup> getOptionalContentGroups()
/*     */   {
/* 176 */     Collection coll = new ArrayList();
/* 177 */     COSArray ocgs = getOCGs();
/* 178 */     for (COSBase base : ocgs)
/*     */     {
/* 180 */       COSObject obj = (COSObject)base;
/* 181 */       coll.add(new PDOptionalContentGroup((COSDictionary)obj.getObject()));
/*     */     }
/* 183 */     return coll;
/*     */   }
/*     */ 
/*     */   public BaseState getBaseState()
/*     */   {
/* 192 */     COSDictionary d = getD();
/* 193 */     COSName name = (COSName)d.getItem(COSName.BASE_STATE);
/* 194 */     return BaseState.valueOf(name);
/*     */   }
/*     */ 
/*     */   public void setBaseState(BaseState state)
/*     */   {
/* 203 */     COSDictionary d = getD();
/* 204 */     d.setItem(COSName.BASE_STATE, state.getName());
/*     */   }
/*     */ 
/*     */   public String[] getGroupNames()
/*     */   {
/* 213 */     COSArray ocgs = (COSArray)this.dict.getDictionaryObject(COSName.OCGS);
/* 214 */     int size = ocgs.size();
/* 215 */     String[] groups = new String[size];
/* 216 */     for (int i = 0; i < size; i++)
/*     */     {
/* 218 */       COSBase obj = ocgs.get(i);
/* 219 */       COSDictionary ocg = toDictionary(obj);
/* 220 */       groups[i] = ocg.getString(COSName.NAME);
/*     */     }
/* 222 */     return groups;
/*     */   }
/*     */ 
/*     */   public boolean hasGroup(String groupName)
/*     */   {
/* 232 */     String[] layers = getGroupNames();
/* 233 */     for (String layer : layers)
/*     */     {
/* 235 */       if (layer.equals(groupName))
/*     */       {
/* 237 */         return true;
/*     */       }
/*     */     }
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isGroupEnabled(String groupName)
/*     */   {
/* 253 */     COSDictionary d = getD();
/* 254 */     COSArray on = (COSArray)d.getDictionaryObject(COSName.ON);
/* 255 */     if (on != null)
/*     */     {
/* 257 */       for (COSBase o : on)
/*     */       {
/* 259 */         COSDictionary group = toDictionary(o);
/* 260 */         String name = group.getString(COSName.NAME);
/* 261 */         if (name.equals(groupName))
/*     */         {
/* 263 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 268 */     COSArray off = (COSArray)d.getDictionaryObject(COSName.OFF);
/* 269 */     if (off != null)
/*     */     {
/* 271 */       for (COSBase o : off)
/*     */       {
/* 273 */         COSDictionary group = toDictionary(o);
/* 274 */         String name = group.getString(COSName.NAME);
/* 275 */         if (name.equals(groupName))
/*     */         {
/* 277 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 282 */     BaseState baseState = getBaseState();
/* 283 */     boolean enabled = !baseState.equals(BaseState.OFF);
/*     */ 
/* 285 */     return enabled;
/*     */   }
/*     */ 
/*     */   private COSDictionary toDictionary(COSBase o)
/*     */   {
/* 290 */     if ((o instanceof COSObject))
/*     */     {
/* 292 */       return (COSDictionary)((COSObject)o).getObject();
/*     */     }
/*     */ 
/* 296 */     return (COSDictionary)o;
/*     */   }
/*     */ 
/*     */   public boolean setGroupEnabled(String groupName, boolean enable)
/*     */   {
/* 308 */     COSDictionary d = getD();
/* 309 */     COSArray on = (COSArray)d.getDictionaryObject(COSName.ON);
/* 310 */     if (on == null)
/*     */     {
/* 312 */       on = new COSArray();
/* 313 */       d.setItem(COSName.ON, on);
/*     */     }
/* 315 */     COSArray off = (COSArray)d.getDictionaryObject(COSName.OFF);
/* 316 */     if (off == null)
/*     */     {
/* 318 */       off = new COSArray();
/* 319 */       d.setItem(COSName.OFF, off);
/*     */     }
/*     */ 
/* 322 */     boolean found = false;
/* 323 */     if (enable)
/*     */     {
/* 325 */       for (COSBase o : off)
/*     */       {
/* 327 */         COSDictionary group = toDictionary(o);
/* 328 */         String name = group.getString(COSName.NAME);
/* 329 */         if (name.equals(groupName))
/*     */         {
/* 332 */           off.remove(group);
/* 333 */           on.add(group);
/* 334 */           found = true;
/* 335 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 341 */       for (COSBase o : on)
/*     */       {
/* 343 */         COSDictionary group = toDictionary(o);
/* 344 */         String name = group.getString(COSName.NAME);
/* 345 */         if (name.equals(groupName))
/*     */         {
/* 348 */           on.remove(group);
/* 349 */           off.add(group);
/* 350 */           found = true;
/* 351 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 355 */     if (!found)
/*     */     {
/* 357 */       PDOptionalContentGroup ocg = getGroup(groupName);
/* 358 */       if (enable)
/*     */       {
/* 360 */         on.add(ocg.getCOSObject());
/*     */       }
/*     */       else
/*     */       {
/* 364 */         off.add(ocg.getCOSObject());
/*     */       }
/*     */     }
/* 367 */     return found;
/*     */   }
/*     */ 
/*     */   public static enum BaseState
/*     */   {
/*  44 */     ON(COSName.ON), 
/*     */ 
/*  46 */     OFF(COSName.OFF), 
/*     */ 
/*  48 */     UNCHANGED(COSName.UNCHANGED);
/*     */ 
/*     */     private COSName name;
/*     */ 
/*     */     private BaseState(COSName value)
/*     */     {
/*  54 */       this.name = value;
/*     */     }
/*     */ 
/*     */     public COSName getName()
/*     */     {
/*  63 */       return this.name;
/*     */     }
/*     */ 
/*     */     public static BaseState valueOf(COSName state)
/*     */     {
/*  73 */       if (state == null)
/*     */       {
/*  75 */         return ON;
/*     */       }
/*  77 */       return valueOf(state.getName().toUpperCase());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties
 * JD-Core Version:    0.6.2
 */