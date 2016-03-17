/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Anchor extends Phrase
/*     */ {
/*     */   private static final long serialVersionUID = -852278536049236911L;
/*  78 */   protected String name = null;
/*     */ 
/*  81 */   protected String reference = null;
/*     */ 
/*     */   public Anchor()
/*     */   {
/*  89 */     super(16.0F);
/*     */   }
/*     */ 
/*     */   public Anchor(float leading)
/*     */   {
/*  99 */     super(leading);
/*     */   }
/*     */ 
/*     */   public Anchor(Chunk chunk)
/*     */   {
/* 108 */     super(chunk);
/*     */   }
/*     */ 
/*     */   public Anchor(String string)
/*     */   {
/* 117 */     super(string);
/*     */   }
/*     */ 
/*     */   public Anchor(String string, Font font)
/*     */   {
/* 128 */     super(string, font);
/*     */   }
/*     */ 
/*     */   public Anchor(float leading, Chunk chunk)
/*     */   {
/* 139 */     super(leading, chunk);
/*     */   }
/*     */ 
/*     */   public Anchor(float leading, String string)
/*     */   {
/* 150 */     super(leading, string);
/*     */   }
/*     */ 
/*     */   public Anchor(float leading, String string, Font font)
/*     */   {
/* 162 */     super(leading, string, font);
/*     */   }
/*     */ 
/*     */   public Anchor(Phrase phrase)
/*     */   {
/* 171 */     super(phrase);
/* 172 */     if ((phrase instanceof Anchor)) {
/* 173 */       Anchor a = (Anchor)phrase;
/* 174 */       setName(a.name);
/* 175 */       setReference(a.reference);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 192 */       Iterator i = getChunks().iterator();
/* 193 */       boolean localDestination = (this.reference != null) && (this.reference.startsWith("#"));
/* 194 */       boolean notGotoOK = true;
/* 195 */       while (i.hasNext()) {
/* 196 */         Chunk chunk = (Chunk)i.next();
/* 197 */         if ((this.name != null) && (notGotoOK) && (!chunk.isEmpty())) {
/* 198 */           chunk.setLocalDestination(this.name);
/* 199 */           notGotoOK = false;
/*     */         }
/* 201 */         if (localDestination) {
/* 202 */           chunk.setLocalGoto(this.reference.substring(1));
/*     */         }
/* 204 */         listener.add(chunk);
/*     */       }
/* 206 */       return true;
/*     */     } catch (DocumentException de) {
/*     */     }
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 220 */     boolean localDestination = (this.reference != null) && (this.reference.startsWith("#"));
/* 221 */     boolean notGotoOK = true;
/* 222 */     List tmp = new ArrayList();
/* 223 */     Iterator i = iterator();
/*     */ 
/* 225 */     while (i.hasNext()) {
/* 226 */       Element element = (Element)i.next();
/* 227 */       if ((element instanceof Chunk)) {
/* 228 */         Chunk chunk = (Chunk)element;
/* 229 */         notGotoOK = applyAnchor(chunk, notGotoOK, localDestination);
/* 230 */         tmp.add(chunk);
/*     */       }
/*     */       else {
/* 233 */         for (Chunk c : element.getChunks()) {
/* 234 */           notGotoOK = applyAnchor(c, notGotoOK, localDestination);
/* 235 */           tmp.add(c);
/*     */         }
/*     */       }
/*     */     }
/* 239 */     return tmp;
/*     */   }
/*     */ 
/*     */   protected boolean applyAnchor(Chunk chunk, boolean notGotoOK, boolean localDestination)
/*     */   {
/* 250 */     if ((this.name != null) && (notGotoOK) && (!chunk.isEmpty())) {
/* 251 */       chunk.setLocalDestination(this.name);
/* 252 */       notGotoOK = false;
/*     */     }
/* 254 */     if (localDestination) {
/* 255 */       chunk.setLocalGoto(this.reference.substring(1));
/*     */     }
/* 257 */     else if (this.reference != null)
/* 258 */       chunk.setAnchor(this.reference);
/* 259 */     return notGotoOK;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 269 */     return 17;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 280 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public void setReference(String reference)
/*     */   {
/* 289 */     this.reference = reference;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 300 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getReference()
/*     */   {
/* 309 */     return this.reference;
/*     */   }
/*     */ 
/*     */   public URL getUrl()
/*     */   {
/*     */     try
/*     */     {
/* 319 */       return new URL(this.reference);
/*     */     } catch (MalformedURLException mue) {
/*     */     }
/* 322 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Anchor
 * JD-Core Version:    0.6.2
 */