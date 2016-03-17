/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ComplexPropertyContainer
/*     */ {
/*     */   private List<AbstractField> properties;
/*     */ 
/*     */   public ComplexPropertyContainer()
/*     */   {
/*  47 */     this.properties = new ArrayList();
/*     */   }
/*     */ 
/*     */   protected AbstractField getFirstEquivalentProperty(String localName, Class<? extends AbstractField> type)
/*     */   {
/* 124 */     List list = getPropertiesByLocalName(localName);
/* 125 */     if (list != null)
/*     */     {
/* 127 */       for (AbstractField abstractField : list)
/*     */       {
/* 129 */         if (abstractField.getClass().equals(type))
/*     */         {
/* 131 */           return abstractField;
/*     */         }
/*     */       }
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   public void addProperty(AbstractField obj)
/*     */   {
/* 146 */     if (containsProperty(obj))
/*     */     {
/* 148 */       removeProperty(obj);
/*     */     }
/* 150 */     this.properties.add(obj);
/*     */   }
/*     */ 
/*     */   public List<AbstractField> getAllProperties()
/*     */   {
/* 160 */     return this.properties;
/*     */   }
/*     */ 
/*     */   public List<AbstractField> getPropertiesByLocalName(String localName)
/*     */   {
/* 172 */     List absFields = getAllProperties();
/* 173 */     if (absFields != null)
/*     */     {
/* 175 */       List list = new ArrayList();
/* 176 */       for (AbstractField abstractField : absFields)
/*     */       {
/* 178 */         if (abstractField.getPropertyName().equals(localName))
/*     */         {
/* 180 */           list.add(abstractField);
/*     */         }
/*     */       }
/* 183 */       if (list.size() == 0)
/*     */       {
/* 185 */         return null;
/*     */       }
/*     */ 
/* 189 */       return list;
/*     */     }
/*     */ 
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isSameProperty(AbstractField prop1, AbstractField prop2)
/*     */   {
/* 208 */     if (prop1.getClass().equals(prop2.getClass()))
/*     */     {
/* 210 */       String pn1 = prop1.getPropertyName();
/* 211 */       String pn2 = prop2.getPropertyName();
/* 212 */       if (pn1 == null)
/*     */       {
/* 214 */         return pn2 == null;
/*     */       }
/*     */ 
/* 218 */       if (pn1.equals(pn2))
/*     */       {
/* 220 */         return prop1.equals(prop2);
/*     */       }
/*     */     }
/*     */ 
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsProperty(AbstractField property)
/*     */   {
/* 236 */     Iterator it = getAllProperties().iterator();
/*     */ 
/* 238 */     while (it.hasNext())
/*     */     {
/* 240 */       AbstractField tmp = (AbstractField)it.next();
/* 241 */       if (isSameProperty(tmp, property))
/*     */       {
/* 243 */         return true;
/*     */       }
/*     */     }
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */   public void removeProperty(AbstractField property)
/*     */   {
/* 257 */     if (containsProperty(property))
/*     */     {
/* 259 */       this.properties.remove(property);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.ComplexPropertyContainer
 * JD-Core Version:    0.6.2
 */