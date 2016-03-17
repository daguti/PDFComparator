/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.BadFieldValueException;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.JobType;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ 
/*     */ @StructuredType(preferedPrefix="xmpBJ", namespace="http://ns.adobe.com/xap/1.0/bj/")
/*     */ public class XMPBasicJobTicketSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Job, card=Cardinality.Bag)
/*     */   public static final String JOB_REF = "JobRef";
/*     */   private ArrayProperty bagJobs;
/*     */ 
/*     */   public XMPBasicJobTicketSchema(XMPMetadata metadata)
/*     */   {
/*  48 */     this(metadata, null);
/*     */   }
/*     */ 
/*     */   public XMPBasicJobTicketSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/*  53 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void addJob(String id, String name, String url)
/*     */   {
/*  58 */     addJob(id, name, url, null);
/*     */   }
/*     */ 
/*     */   public void addJob(String id, String name, String url, String fieldPrefix)
/*     */   {
/*  63 */     JobType job = new JobType(getMetadata(), fieldPrefix);
/*  64 */     job.setId(id);
/*  65 */     job.setName(name);
/*  66 */     job.setUrl(url);
/*  67 */     addJob(job);
/*     */   }
/*     */ 
/*     */   public void addJob(JobType job)
/*     */   {
/*  72 */     String prefix = getNamespacePrefix(job.getNamespace());
/*  73 */     if (prefix != null)
/*     */     {
/*  76 */       job.setPrefix(prefix);
/*     */     }
/*     */     else
/*     */     {
/*  81 */       addNamespace(job.getNamespace(), job.getPrefix());
/*     */     }
/*     */ 
/*  84 */     if (this.bagJobs == null)
/*     */     {
/*  86 */       this.bagJobs = createArrayProperty("JobRef", Cardinality.Bag);
/*  87 */       addProperty(this.bagJobs);
/*     */     }
/*     */ 
/*  90 */     this.bagJobs.getContainer().addProperty(job);
/*     */   }
/*     */ 
/*     */   public List<JobType> getJobs() throws BadFieldValueException
/*     */   {
/*  95 */     List tmp = getUnqualifiedArrayList("JobRef");
/*  96 */     if (tmp != null)
/*     */     {
/*  98 */       List layers = new ArrayList();
/*  99 */       for (AbstractField abstractField : tmp)
/*     */       {
/* 101 */         if ((abstractField instanceof JobType))
/*     */         {
/* 103 */           layers.add((JobType)abstractField);
/*     */         }
/*     */         else
/*     */         {
/* 107 */           throw new BadFieldValueException("Job expected and " + abstractField.getClass().getName() + " found.");
/*     */         }
/*     */       }
/*     */ 
/* 111 */       return layers;
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPBasicJobTicketSchema
 * JD-Core Version:    0.6.2
 */