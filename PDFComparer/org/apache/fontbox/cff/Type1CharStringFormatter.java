/*    */ package org.apache.fontbox.cff;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Type1CharStringFormatter
/*    */ {
/* 30 */   private ByteArrayOutputStream output = null;
/*    */ 
/*    */   public byte[] format(List<Object> sequence)
/*    */   {
/* 39 */     this.output = new ByteArrayOutputStream();
/*    */ 
/* 41 */     for (Iterator i$ = sequence.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*    */ 
/* 43 */       if ((object instanceof CharStringCommand))
/*    */       {
/* 45 */         writeCommand((CharStringCommand)object);
/*    */       }
/* 47 */       else if ((object instanceof Integer))
/*    */       {
/* 49 */         writeNumber((Integer)object);
/*    */       }
/*    */       else
/*    */       {
/* 53 */         throw new IllegalArgumentException();
/*    */       }
/*    */     }
/* 56 */     return this.output.toByteArray();
/*    */   }
/*    */ 
/*    */   private void writeCommand(CharStringCommand command)
/*    */   {
/* 61 */     int[] value = command.getKey().getValue();
/* 62 */     for (int i = 0; i < value.length; i++)
/*    */     {
/* 64 */       this.output.write(value[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void writeNumber(Integer number)
/*    */   {
/* 70 */     int value = number.intValue();
/* 71 */     if ((value >= -107) && (value <= 107))
/*    */     {
/* 73 */       this.output.write(value + 139);
/*    */     }
/* 75 */     else if ((value >= 108) && (value <= 1131))
/*    */     {
/* 77 */       int b1 = (value - 108) % 256;
/* 78 */       int b0 = (value - 108 - b1) / 256 + 247;
/* 79 */       this.output.write(b0);
/* 80 */       this.output.write(b1);
/*    */     }
/* 82 */     else if ((value >= -1131) && (value <= -108))
/*    */     {
/* 84 */       int b1 = -((value + 108) % 256);
/* 85 */       int b0 = -((value + 108 + b1) / 256 - 251);
/* 86 */       this.output.write(b0);
/* 87 */       this.output.write(b1);
/*    */     }
/*    */     else
/*    */     {
/* 91 */       int b1 = value >>> 24 & 0xFF;
/* 92 */       int b2 = value >>> 16 & 0xFF;
/* 93 */       int b3 = value >>> 8 & 0xFF;
/* 94 */       int b4 = value >>> 0 & 0xFF;
/* 95 */       this.output.write(255);
/* 96 */       this.output.write(b1);
/* 97 */       this.output.write(b2);
/* 98 */       this.output.write(b3);
/* 99 */       this.output.write(b4);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.Type1CharStringFormatter
 * JD-Core Version:    0.6.2
 */