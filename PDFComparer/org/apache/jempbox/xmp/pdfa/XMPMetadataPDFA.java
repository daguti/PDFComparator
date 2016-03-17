/*     */ package org.apache.jempbox.xmp.pdfa;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.apache.jempbox.xmp.XMPMetadata;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class XMPMetadataPDFA extends XMPMetadata
/*     */ {
/*     */   public XMPMetadataPDFA()
/*     */     throws IOException
/*     */   {
/*  43 */     init();
/*     */   }
/*     */ 
/*     */   public XMPMetadataPDFA(Document doc)
/*     */   {
/*  53 */     super(doc);
/*  54 */     init();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  60 */     this.nsMappings.put("http://www.aiim.org/pdfa/ns/field", XMPSchemaPDFAField.class);
/*  61 */     this.nsMappings.put("http://www.aiim.org/pdfa/ns/id/", XMPSchemaPDFAId.class);
/*  62 */     this.nsMappings.put("http://www.aiim.org/pdfa/ns/property", XMPSchemaPDFAProperty.class);
/*  63 */     this.nsMappings.put("http://www.aiim.org/pdfa/ns/schema", XMPSchemaPDFASchema.class);
/*  64 */     this.nsMappings.put("http://www.aiim.org/pdfa/ns/type", XMPSchemaPDFAType.class);
/*     */   }
/*     */ 
/*     */   public static XMPMetadata load(InputSource is)
/*     */     throws IOException
/*     */   {
/*  76 */     return new XMPMetadataPDFA(XMLUtil.parse(is));
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAField getPDFAFieldSchema()
/*     */     throws IOException
/*     */   {
/*  88 */     return (XMPSchemaPDFAField)getSchemaByClass(XMPSchemaPDFAField.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAField addPDFAFieldSchema()
/*     */   {
/*  98 */     XMPSchemaPDFAField schema = new XMPSchemaPDFAField(this);
/*  99 */     return (XMPSchemaPDFAField)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAId getPDFAIdSchema()
/*     */     throws IOException
/*     */   {
/* 109 */     return (XMPSchemaPDFAId)getSchemaByClass(XMPSchemaPDFAId.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAId addPDFAIdSchema()
/*     */   {
/* 119 */     XMPSchemaPDFAId schema = new XMPSchemaPDFAId(this);
/* 120 */     return (XMPSchemaPDFAId)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAProperty getPDFAPropertySchema()
/*     */     throws IOException
/*     */   {
/* 132 */     return (XMPSchemaPDFAProperty)getSchemaByClass(XMPSchemaPDFAProperty.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAProperty addPDFAPropertySchema()
/*     */   {
/* 142 */     XMPSchemaPDFAProperty schema = new XMPSchemaPDFAProperty(this);
/* 143 */     return (XMPSchemaPDFAProperty)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFASchema getPDFASchema()
/*     */     throws IOException
/*     */   {
/* 155 */     return (XMPSchemaPDFASchema)getSchemaByClass(XMPSchemaPDFASchema.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFASchema addPDFASchema()
/*     */   {
/* 165 */     XMPSchemaPDFASchema schema = new XMPSchemaPDFASchema(this);
/* 166 */     return (XMPSchemaPDFASchema)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAType getPDFATypeSchema()
/*     */     throws IOException
/*     */   {
/* 178 */     return (XMPSchemaPDFAType)getSchemaByClass(XMPSchemaPDFAType.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAType addPDFATypeSchema()
/*     */   {
/* 188 */     XMPSchemaPDFAType schema = new XMPSchemaPDFAType(this);
/* 189 */     return (XMPSchemaPDFAType)basicAddSchema(schema);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPMetadataPDFA
 * JD-Core Version:    0.6.2
 */