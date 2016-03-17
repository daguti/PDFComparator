/*     */ package org.apache.pdfbox.preflight.action;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class ActionManagerFactory
/*     */ {
/*     */   public final List<AbstractActionManager> getActionManagers(PreflightContext ctx, COSDictionary dictionary)
/*     */     throws ValidationException
/*     */   {
/*  81 */     List result = new ArrayList(0);
/*  82 */     Map alreadyCreated = new HashMap();
/*     */ 
/*  84 */     COSBase aDict = dictionary.getDictionaryObject(COSName.A);
/*  85 */     if (aDict != null)
/*     */     {
/*  87 */       callCreateAction(aDict, ctx, result, alreadyCreated);
/*     */     }
/*     */ 
/*  90 */     COSDocument cosDocument = ctx.getDocument().getDocument();
/*  91 */     COSBase oaDict = dictionary.getDictionaryObject("OpenAction");
/*  92 */     if (oaDict != null)
/*     */     {
/*  94 */       if (!COSUtils.isArray(oaDict, cosDocument))
/*     */       {
/*  96 */         callCreateAction(oaDict, ctx, result, alreadyCreated);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     COSBase aa = dictionary.getDictionaryObject("AA");
/*     */     COSDictionary aaDict;
/* 103 */     if (aa != null)
/*     */     {
/* 105 */       aaDict = COSUtils.getAsDictionary(aa, cosDocument);
/* 106 */       if (aaDict != null)
/*     */       {
/* 108 */         for (Object key : aaDict.keySet())
/*     */         {
/* 110 */           COSName name = (COSName)key;
/* 111 */           callCreateAction(aaDict.getItem(name), ctx, result, name.getName(), alreadyCreated);
/*     */         }
/*     */       }
/*     */     }
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */   private void callCreateAction(COSBase aDict, PreflightContext ctx, List<AbstractActionManager> result, Map<COSObjectKey, Boolean> alreadyCreated)
/*     */     throws ValidationException
/*     */   {
/* 135 */     callCreateAction(aDict, ctx, result, null, alreadyCreated);
/*     */   }
/*     */ 
/*     */   private void callCreateAction(COSBase aDict, PreflightContext ctx, List<AbstractActionManager> result, String additionActionKey, Map<COSObjectKey, Boolean> alreadyCreated)
/*     */     throws ValidationException
/*     */   {
/* 161 */     COSDocument cosDocument = ctx.getDocument().getDocument();
/* 162 */     if (COSUtils.isDictionary(aDict, cosDocument))
/*     */     {
/* 164 */       if ((aDict instanceof COSObject))
/*     */       {
/* 166 */         COSObjectKey cok = new COSObjectKey((COSObject)aDict);
/* 167 */         if (!alreadyCreated.containsKey(cok))
/*     */         {
/* 169 */           result.add(createActionManager(ctx, COSUtils.getAsDictionary(aDict, cosDocument), additionActionKey));
/* 170 */           alreadyCreated.put(cok, Boolean.valueOf(true));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 175 */         result.add(createActionManager(ctx, COSUtils.getAsDictionary(aDict, cosDocument), additionActionKey));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 180 */       ctx.addValidationError(new ValidationResult.ValidationError("6.1.3", "Action entry isn't an instance of COSDictionary"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public final List<AbstractActionManager> getNextActions(PreflightContext ctx, COSDictionary actionDictionary)
/*     */     throws ValidationException
/*     */   {
/* 198 */     List result = new ArrayList(0);
/* 199 */     Map alreadyCreated = new HashMap();
/*     */ 
/* 201 */     COSBase nextDict = actionDictionary.getDictionaryObject("Next");
/* 202 */     if (nextDict != null)
/*     */     {
/* 204 */       COSDocument cosDocument = ctx.getDocument().getDocument();
/* 205 */       if (COSUtils.isArray(nextDict, cosDocument))
/*     */       {
/* 207 */         COSArray array = COSUtils.getAsArray(nextDict, cosDocument);
/*     */ 
/* 209 */         for (int i = 0; i < array.size(); i++)
/*     */         {
/* 211 */           callCreateAction(array.get(i), ctx, result, alreadyCreated);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 217 */         callCreateAction(nextDict, ctx, result, alreadyCreated);
/*     */       }
/*     */     }
/* 220 */     return result;
/*     */   }
/*     */ 
/*     */   protected AbstractActionManager createActionManager(PreflightContext ctx, COSDictionary action, String aaKey)
/*     */     throws ValidationException
/*     */   {
/* 241 */     String type = action.getNameAsString(COSName.TYPE);
/* 242 */     if ((type != null) && (!"Action".equals(type)))
/*     */     {
/* 244 */       throw new ValidationException("The given dictionary isn't the dictionary of an Action");
/*     */     }
/*     */ 
/* 249 */     String s = action.getNameAsString(COSName.S);
/*     */ 
/* 252 */     if ("GoTo".equals(s))
/*     */     {
/* 254 */       return new GoToAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 257 */     if ("GoToR".equals(s))
/*     */     {
/* 259 */       return new GoToRemoteAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 262 */     if ("Thread".equals(s))
/*     */     {
/* 264 */       return new ThreadAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 267 */     if ("URI".equals(s))
/*     */     {
/* 269 */       return new UriAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 272 */     if ("Hide".equals(s))
/*     */     {
/* 274 */       return new HideAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 277 */     if ("Named".equals(s))
/*     */     {
/* 279 */       return new NamedAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 282 */     if ("SubmitForm".equals(s))
/*     */     {
/* 284 */       return new SubmitAction(this, action, ctx, aaKey);
/*     */     }
/*     */ 
/* 288 */     if (("Launch".equals(s)) || ("Sound".equals(s)) || ("Movie".equals(s)) || ("ResetForm".equals(s)) || ("ImportData".equals(s)) || ("JavaScript".equals(s)) || ("SetState".equals(s)) || ("NOP".equals(s)))
/*     */     {
/* 293 */       return new InvalidAction(this, action, ctx, aaKey, s);
/*     */     }
/*     */ 
/* 304 */     return new UndefAction(this, action, ctx, aaKey, s);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.ActionManagerFactory
 * JD-Core Version:    0.6.2
 */