/*    */ package org.apache.xmpbox.type;
/*    */ 
/*    */ public enum Types
/*    */ {
/* 27 */   Structured(false, null, null), DefinedType(false, null, null), 
/*    */ 
/* 30 */   Text(true, null, TextType.class), Date(true, null, DateType.class), Boolean(true, null, BooleanType.class), Integer(true, null, IntegerType.class), 
/* 31 */   Real(true, null, RealType.class), 
/*    */ 
/* 33 */   ProperName(true, Text, ProperNameType.class), Locale(true, Text, LocaleType.class), AgentName(true, Text, AgentNameType.class), 
/* 34 */   GUID(true, Text, GUIDType.class), XPath(true, Text, XPathType.class), Part(true, Text, PartType.class), 
/* 35 */   URL(true, Text, URLType.class), URI(true, Text, URIType.class), Choice(true, Text, ChoiceType.class), 
/* 36 */   MIMEType(true, Text, MIMEType.class), LangAlt(true, Text, TextType.class), RenditionClass(true, Text, RenditionClassType.class), 
/*    */ 
/* 39 */   Layer(false, Structured, LayerType.class), Thumbnail(false, Structured, ThumbnailType.class), ResourceEvent(false, Structured, ResourceEventType.class), 
/* 40 */   ResourceRef(false, Structured, ResourceRefType.class), Version(false, Structured, VersionType.class), 
/* 41 */   PDFASchema(false, Structured, PDFASchemaType.class), PDFAField(false, Structured, PDFAFieldType.class), 
/* 42 */   PDFAProperty(false, Structured, PDFAPropertyType.class), PDFAType(false, Structured, PDFATypeType.class), 
/* 43 */   Job(false, Structured, JobType.class);
/*    */ 
/*    */   private boolean simple;
/*    */   private Types basic;
/*    */   private Class<? extends AbstractField> clz;
/*    */ 
/*    */   private Types(boolean s, Types b, Class<? extends AbstractField> c)
/*    */   {
/* 55 */     this.simple = s;
/* 56 */     this.basic = b;
/* 57 */     this.clz = c;
/*    */   }
/*    */ 
/*    */   public boolean isSimple()
/*    */   {
/* 62 */     return this.simple;
/*    */   }
/*    */ 
/*    */   public boolean isBasic()
/*    */   {
/* 67 */     return this.basic == null;
/*    */   }
/*    */ 
/*    */   public boolean isStructured()
/*    */   {
/* 72 */     return this.basic == Structured;
/*    */   }
/*    */ 
/*    */   public boolean isDefined()
/*    */   {
/* 77 */     return this == DefinedType;
/*    */   }
/*    */ 
/*    */   public Types getBasic()
/*    */   {
/* 82 */     return this.basic;
/*    */   }
/*    */ 
/*    */   public Class<? extends AbstractField> getImplementingClass()
/*    */   {
/* 87 */     return this.clz;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.Types
 * JD-Core Version:    0.6.2
 */