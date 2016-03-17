/*     */ package antlr.collections.impl;
/*     */ 
/*     */ import antlr.collections.List;
/*     */ import antlr.collections.Stack;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class LList
/*     */   implements List, Stack
/*     */ {
/*  22 */   protected LLCell head = null; protected LLCell tail = null;
/*  23 */   protected int length = 0;
/*     */ 
/*     */   public void add(Object paramObject)
/*     */   {
/*  30 */     append(paramObject);
/*     */   }
/*     */ 
/*     */   public void append(Object paramObject)
/*     */   {
/*  37 */     LLCell localLLCell = new LLCell(paramObject);
/*  38 */     if (this.length == 0) {
/*  39 */       this.head = (this.tail = localLLCell);
/*  40 */       this.length = 1;
/*     */     }
/*     */     else {
/*  43 */       this.tail.next = localLLCell;
/*  44 */       this.tail = localLLCell;
/*  45 */       this.length += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object deleteHead()
/*     */     throws NoSuchElementException
/*     */   {
/*  54 */     if (this.head == null) throw new NoSuchElementException();
/*  55 */     Object localObject = this.head.data;
/*  56 */     this.head = this.head.next;
/*  57 */     this.length -= 1;
/*  58 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object elementAt(int paramInt)
/*     */     throws NoSuchElementException
/*     */   {
/*  67 */     int i = 0;
/*  68 */     for (LLCell localLLCell = this.head; localLLCell != null; localLLCell = localLLCell.next) {
/*  69 */       if (paramInt == i) return localLLCell.data;
/*  70 */       i++;
/*     */     }
/*  72 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   public Enumeration elements()
/*     */   {
/*  77 */     return new LLEnumeration(this);
/*     */   }
/*     */ 
/*     */   public int height()
/*     */   {
/*  82 */     return this.length;
/*     */   }
/*     */ 
/*     */   public boolean includes(Object paramObject)
/*     */   {
/*  90 */     for (LLCell localLLCell = this.head; localLLCell != null; localLLCell = localLLCell.next) {
/*  91 */       if (localLLCell.data.equals(paramObject)) return true;
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   protected void insertHead(Object paramObject)
/*     */   {
/* 101 */     LLCell localLLCell = this.head;
/* 102 */     this.head = new LLCell(paramObject);
/* 103 */     this.head.next = localLLCell;
/* 104 */     this.length += 1;
/* 105 */     if (this.tail == null) this.tail = this.head;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/* 110 */     return this.length;
/*     */   }
/*     */ 
/*     */   public Object pop()
/*     */     throws NoSuchElementException
/*     */   {
/* 118 */     Object localObject = deleteHead();
/* 119 */     return localObject;
/*     */   }
/*     */ 
/*     */   public void push(Object paramObject)
/*     */   {
/* 127 */     insertHead(paramObject);
/*     */   }
/*     */ 
/*     */   public Object top() throws NoSuchElementException {
/* 131 */     if (this.head == null) throw new NoSuchElementException();
/* 132 */     return this.head.data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.LList
 * JD-Core Version:    0.6.2
 */