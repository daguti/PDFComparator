/*    */ package org.apache.fontbox.cff;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class CFFDataInput extends DataInput
/*    */ {
/*    */   public CFFDataInput(byte[] buffer)
/*    */   {
/* 36 */     super(buffer);
/*    */   }
/*    */ 
/*    */   public int readCard8()
/*    */     throws IOException
/*    */   {
/* 46 */     return readUnsignedByte();
/*    */   }
/*    */ 
/*    */   public int readCard16()
/*    */     throws IOException
/*    */   {
/* 56 */     return readUnsignedShort();
/*    */   }
/*    */ 
/*    */   public int readOffset(int offSize)
/*    */     throws IOException
/*    */   {
/* 67 */     int value = 0;
/* 68 */     for (int i = 0; i < offSize; i++)
/*    */     {
/* 70 */       value = value << 8 | readUnsignedByte();
/*    */     }
/* 72 */     return value;
/*    */   }
/*    */ 
/*    */   public int readOffSize()
/*    */     throws IOException
/*    */   {
/* 82 */     return readUnsignedByte();
/*    */   }
/*    */ 
/*    */   public int readSID()
/*    */     throws IOException
/*    */   {
/* 92 */     return readUnsignedShort();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CFFDataInput
 * JD-Core Version:    0.6.2
 */