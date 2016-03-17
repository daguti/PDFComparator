/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import org.apache.xmpbox.DateConverter;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public class DateType extends AbstractSimpleProperty
/*     */ {
/*     */   private Calendar dateValue;
/*     */ 
/*     */   public DateType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*     */   {
/*  57 */     super(metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   private void setValueFromCalendar(Calendar value)
/*     */   {
/*  69 */     this.dateValue = value;
/*     */   }
/*     */ 
/*     */   public Calendar getValue()
/*     */   {
/*  79 */     return this.dateValue;
/*     */   }
/*     */ 
/*     */   private boolean isGoodType(Object value)
/*     */   {
/*  91 */     if ((value instanceof Calendar))
/*     */     {
/*  93 */       return true;
/*     */     }
/*  95 */     if ((value instanceof String))
/*     */     {
/*     */       try
/*     */       {
/*  99 */         DateConverter.toCalendar((String)value);
/* 100 */         return true;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 104 */         return false;
/*     */       }
/*     */     }
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/* 118 */     if (!isGoodType(value))
/*     */     {
/* 120 */       throw new IllegalArgumentException("Value given is not allowed for the Date type : " + value.getClass());
/*     */     }
/*     */ 
/* 125 */     if ((value instanceof String))
/*     */     {
/* 127 */       setValueFromString((String)value);
/*     */     }
/*     */     else
/*     */     {
/* 132 */       setValueFromCalendar((Calendar)value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStringValue()
/*     */   {
/* 141 */     return DateConverter.toISO8601(this.dateValue);
/*     */   }
/*     */ 
/*     */   private void setValueFromString(String value)
/*     */   {
/*     */     try
/*     */     {
/* 154 */       setValueFromCalendar(DateConverter.toCalendar(value));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 160 */       throw new IllegalArgumentException(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.DateType
 * JD-Core Version:    0.6.2
 */