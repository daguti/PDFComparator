/*    */ package org.apache.pdfbox.pdmodel.graphics.color;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdmodel.PDDocument;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*    */ 
/*    */ public class PDOutputIntent
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary dictionary;
/*    */ 
/*    */   public PDOutputIntent(PDDocument doc, InputStream colorProfile)
/*    */     throws Exception
/*    */   {
/* 35 */     this.dictionary = new COSDictionary();
/* 36 */     this.dictionary.setItem(COSName.TYPE, COSName.OUTPUT_INTENT);
/* 37 */     this.dictionary.setItem(COSName.S, COSName.GTS_PDFA1);
/* 38 */     PDStream destOutputIntent = configureOutputProfile(doc, colorProfile);
/* 39 */     this.dictionary.setItem(COSName.DEST_OUTPUT_PROFILE, destOutputIntent);
/*    */   }
/*    */ 
/*    */   public PDOutputIntent(COSDictionary dictionary) {
/* 43 */     this.dictionary = dictionary;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject() {
/* 47 */     return this.dictionary;
/*    */   }
/*    */ 
/*    */   public COSStream getDestOutputIntent() {
/* 51 */     return (COSStream)this.dictionary.getItem(COSName.DEST_OUTPUT_PROFILE);
/*    */   }
/*    */ 
/*    */   public String getInfo() {
/* 55 */     return this.dictionary.getString(COSName.INFO);
/*    */   }
/*    */ 
/*    */   public void setInfo(String value)
/*    */   {
/* 60 */     this.dictionary.setString(COSName.INFO, value);
/*    */   }
/*    */ 
/*    */   public String getOutputCondition() {
/* 64 */     return this.dictionary.getString(COSName.OUTPUT_CONDITION);
/*    */   }
/*    */ 
/*    */   public void setOutputCondition(String value)
/*    */   {
/* 69 */     this.dictionary.setString(COSName.OUTPUT_CONDITION, value);
/*    */   }
/*    */ 
/*    */   public String getOutputConditionIdentifier() {
/* 73 */     return this.dictionary.getString(COSName.OUTPUT_CONDITION_IDENTIFIER);
/*    */   }
/*    */ 
/*    */   public void setOutputConditionIdentifier(String value)
/*    */   {
/* 78 */     this.dictionary.setString(COSName.OUTPUT_CONDITION_IDENTIFIER, value);
/*    */   }
/*    */ 
/*    */   public String getRegistryName() {
/* 82 */     return this.dictionary.getString(COSName.REGISTRY_NAME);
/*    */   }
/*    */ 
/*    */   public void setRegistryName(String value)
/*    */   {
/* 87 */     this.dictionary.setString(COSName.REGISTRY_NAME, value);
/*    */   }
/*    */ 
/*    */   private PDStream configureOutputProfile(PDDocument doc, InputStream colorProfile) throws IOException {
/* 91 */     PDStream stream = new PDStream(doc, colorProfile, false);
/* 92 */     stream.getStream().setFilters(COSName.FLATE_DECODE);
/* 93 */     stream.getStream().setInt(COSName.LENGTH, stream.getByteArray().length);
/* 94 */     stream.getStream().setInt(COSName.N, 3);
/* 95 */     stream.addCompression();
/* 96 */     return stream;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent
 * JD-Core Version:    0.6.2
 */