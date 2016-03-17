/*     */ package org.apache.pdfbox.preflight;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.preflight.action.ActionManagerFactory;
/*     */ import org.apache.pdfbox.preflight.annotation.AnnotationValidatorFactory;
/*     */ import org.apache.pdfbox.preflight.annotation.pdfa.PDFAbAnnotationFactory;
/*     */ import org.apache.pdfbox.preflight.exception.MissingValidationProcessException;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory;
/*     */ import org.apache.pdfbox.preflight.process.AcroFormValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.BookmarkValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.CatalogValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.EmptyValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.FileSpecificationValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.MetadataValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.PageTreeValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.StreamValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.TrailerValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.ValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.XRefValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.ActionsValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.AnnotationValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.ExtGStateValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.FontValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.GraphicObjectPageValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.ResourcesValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.ShaddingPatternValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.SinglePageValidationProcess;
/*     */ import org.apache.pdfbox.preflight.process.reflect.TilingPatternValidationProcess;
/*     */ 
/*     */ public class PreflightConfiguration
/*     */ {
/*     */   public static final String CATALOG_PROCESS = "catalog-process";
/*     */   public static final String STREAM_PROCESS = "stream-process";
/*     */   public static final String TRAILER_PROCESS = "trailer-process";
/*     */   public static final String XREF_PROCESS = "xref-process";
/*     */   public static final String BOOKMARK_PROCESS = "bookmark-process";
/*     */   public static final String ACRO_FORM_PROCESS = "acro-form-process";
/*     */   public static final String FILE_SPECIF_PROCESS = "file-specification-process";
/*     */   public static final String PAGES_TREE_PROCESS = "pages-tree-process";
/*     */   public static final String META_DATA_PROCESS = "metadata-process";
/*     */   public static final String PAGE_PROCESS = "page-process";
/*     */   public static final String RESOURCES_PROCESS = "resources-process";
/*     */   public static final String ACTIONS_PROCESS = "actions-process";
/*     */   public static final String ANNOTATIONS_PROCESS = "annotations-process";
/*     */   public static final String GRAPHIC_PROCESS = "graphic-process";
/*     */   public static final String FONT_PROCESS = "font-process";
/*     */   public static final String EXTGSTATE_PROCESS = "extgstate-process";
/*     */   public static final String SHADDING_PATTERN_PROCESS = "shadding-pattern-process";
/*     */   public static final String TILING_PATTERN_PROCESS = "tiling-pattern-process";
/*  87 */   private boolean errorOnMissingProcess = true;
/*     */ 
/*  93 */   private boolean lazyValidation = false;
/*     */ 
/*  95 */   private Map<String, Class<? extends ValidationProcess>> processes = new LinkedHashMap();
/*     */ 
/*  97 */   private Map<String, Class<? extends ValidationProcess>> innerProcesses = new LinkedHashMap();
/*     */   private AnnotationValidatorFactory annotFact;
/*     */   private ActionManagerFactory actionFact;
/*     */   private ColorSpaceHelperFactory colorSpaceHelperFact;
/*     */ 
/*     */   public static PreflightConfiguration createPdfA1BConfiguration()
/*     */   {
/* 116 */     PreflightConfiguration configuration = new PreflightConfiguration();
/*     */ 
/* 118 */     configuration.replaceProcess("catalog-process", CatalogValidationProcess.class);
/* 119 */     configuration.replaceProcess("file-specification-process", FileSpecificationValidationProcess.class);
/* 120 */     configuration.replaceProcess("trailer-process", TrailerValidationProcess.class);
/* 121 */     configuration.replaceProcess("xref-process", XRefValidationProcess.class);
/* 122 */     configuration.replaceProcess("acro-form-process", AcroFormValidationProcess.class);
/* 123 */     configuration.replaceProcess("bookmark-process", BookmarkValidationProcess.class);
/* 124 */     configuration.replaceProcess("pages-tree-process", PageTreeValidationProcess.class);
/* 125 */     configuration.replaceProcess("metadata-process", MetadataValidationProcess.class);
/*     */ 
/* 127 */     configuration.replaceProcess("stream-process", StreamValidationProcess.class);
/*     */ 
/* 129 */     configuration.replacePageProcess("page-process", SinglePageValidationProcess.class);
/* 130 */     configuration.replacePageProcess("extgstate-process", ExtGStateValidationProcess.class);
/* 131 */     configuration.replacePageProcess("shadding-pattern-process", ShaddingPatternValidationProcess.class);
/* 132 */     configuration.replacePageProcess("graphic-process", GraphicObjectPageValidationProcess.class);
/* 133 */     configuration.replacePageProcess("tiling-pattern-process", TilingPatternValidationProcess.class);
/* 134 */     configuration.replacePageProcess("resources-process", ResourcesValidationProcess.class);
/* 135 */     configuration.replacePageProcess("font-process", FontValidationProcess.class);
/* 136 */     configuration.replacePageProcess("actions-process", ActionsValidationProcess.class);
/* 137 */     configuration.replacePageProcess("annotations-process", AnnotationValidationProcess.class);
/*     */ 
/* 139 */     configuration.actionFact = new ActionManagerFactory();
/* 140 */     configuration.annotFact = new PDFAbAnnotationFactory();
/* 141 */     configuration.colorSpaceHelperFact = new ColorSpaceHelperFactory();
/* 142 */     return configuration;
/*     */   }
/*     */ 
/*     */   public Collection<String> getProcessNames()
/*     */   {
/* 147 */     return this.processes.keySet();
/*     */   }
/*     */ 
/*     */   public ValidationProcess getInstanceOfProcess(String processName)
/*     */     throws MissingValidationProcessException, ValidationException
/*     */   {
/* 161 */     Class clazz = null;
/* 162 */     if (this.processes.containsKey(processName))
/*     */     {
/* 164 */       clazz = (Class)this.processes.get(processName);
/*     */     }
/* 166 */     else if (this.innerProcesses.containsKey(processName))
/*     */     {
/* 168 */       clazz = (Class)this.innerProcesses.get(processName);
/*     */     } else {
/* 170 */       if (this.errorOnMissingProcess)
/*     */       {
/* 172 */         throw new MissingValidationProcessException(processName);
/*     */       }
/*     */ 
/* 176 */       return new EmptyValidationProcess();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 181 */       return (ValidationProcess)clazz.newInstance();
/*     */     }
/*     */     catch (InstantiationException e)
/*     */     {
/* 185 */       throw new ValidationException(processName + " can't be created", e);
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/* 189 */       throw new ValidationException(processName + " can't be created", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void replaceProcess(String processName, Class<? extends ValidationProcess> process)
/*     */   {
/* 196 */     if (process == null)
/*     */     {
/* 198 */       removeProcess(processName);
/*     */     }
/*     */     else
/*     */     {
/* 202 */       this.processes.put(processName, process);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeProcess(String processName)
/*     */   {
/* 208 */     this.processes.remove(processName);
/*     */   }
/*     */ 
/*     */   public Collection<String> getPageValidationProcessNames()
/*     */   {
/* 213 */     return this.innerProcesses.keySet();
/*     */   }
/*     */ 
/*     */   public void replacePageProcess(String processName, Class<? extends ValidationProcess> process)
/*     */   {
/* 218 */     if (process == null) {
/* 219 */       removePageProcess(processName);
/*     */     }
/*     */     else
/* 222 */       this.innerProcesses.put(processName, process);
/*     */   }
/*     */ 
/*     */   public void removePageProcess(String processName)
/*     */   {
/* 228 */     this.innerProcesses.remove(processName);
/*     */   }
/*     */ 
/*     */   public boolean isErrorOnMissingProcess()
/*     */   {
/* 233 */     return this.errorOnMissingProcess;
/*     */   }
/*     */ 
/*     */   public void setErrorOnMissingProcess(boolean errorOnMissingProcess)
/*     */   {
/* 238 */     this.errorOnMissingProcess = errorOnMissingProcess;
/*     */   }
/*     */ 
/*     */   public boolean isLazyValidation()
/*     */   {
/* 243 */     return this.lazyValidation;
/*     */   }
/*     */ 
/*     */   public void setLazyValidation(boolean lazyValidation)
/*     */   {
/* 248 */     this.lazyValidation = lazyValidation;
/*     */   }
/*     */ 
/*     */   public AnnotationValidatorFactory getAnnotFact()
/*     */   {
/* 253 */     return this.annotFact;
/*     */   }
/*     */ 
/*     */   public void setAnnotFact(AnnotationValidatorFactory annotFact)
/*     */   {
/* 258 */     this.annotFact = annotFact;
/*     */   }
/*     */ 
/*     */   public ActionManagerFactory getActionFact()
/*     */   {
/* 263 */     return this.actionFact;
/*     */   }
/*     */ 
/*     */   public void setActionFact(ActionManagerFactory actionFact)
/*     */   {
/* 268 */     this.actionFact = actionFact;
/*     */   }
/*     */ 
/*     */   public ColorSpaceHelperFactory getColorSpaceHelperFact()
/*     */   {
/* 273 */     return this.colorSpaceHelperFact;
/*     */   }
/*     */ 
/*     */   public void setColorSpaceHelperFact(ColorSpaceHelperFactory colorSpaceHelperFact)
/*     */   {
/* 278 */     this.colorSpaceHelperFact = colorSpaceHelperFact;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.PreflightConfiguration
 * JD-Core Version:    0.6.2
 */