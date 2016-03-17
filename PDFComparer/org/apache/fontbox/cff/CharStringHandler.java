/*    */ package org.apache.fontbox.cff;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class CharStringHandler
/*    */ {
/*    */   public List<Integer> handleSequence(List<Object> sequence)
/*    */   {
/* 41 */     List numbers = null;
/* 42 */     int offset = 0;
/* 43 */     int size = sequence.size();
/* 44 */     for (int i = 0; i < size; i++)
/*    */     {
/* 46 */       Object object = sequence.get(i);
/* 47 */       if ((object instanceof CharStringCommand))
/*    */       {
/* 49 */         if (numbers == null)
/* 50 */           numbers = sequence.subList(offset, i);
/*    */         else
/* 52 */           numbers.addAll(sequence.subList(offset, i));
/* 53 */         List stack = handleCommand(numbers, (CharStringCommand)object);
/* 54 */         if ((stack != null) && (!stack.isEmpty()))
/* 55 */           numbers = stack;
/*    */         else
/* 57 */           numbers = null;
/* 58 */         offset = i + 1;
/*    */       }
/*    */     }
/* 61 */     if ((numbers != null) && (!numbers.isEmpty())) {
/* 62 */       return numbers;
/*    */     }
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   public abstract List<Integer> handleCommand(List<Integer> paramList, CharStringCommand paramCharStringCommand);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CharStringHandler
 * JD-Core Version:    0.6.2
 */