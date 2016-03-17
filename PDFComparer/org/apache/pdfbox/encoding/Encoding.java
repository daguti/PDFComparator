/*     */ package org.apache.pdfbox.encoding;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.util.ResourceLoader;
/*     */ 
/*     */ public abstract class Encoding
/*     */   implements COSObjectable
/*     */ {
/*  47 */   private static final Log LOG = LogFactory.getLog(Encoding.class);
/*     */   public static final String NOTDEF = ".notdef";
/*  55 */   protected final Map<Integer, String> codeToName = new HashMap();
/*     */ 
/*  61 */   protected final Map<String, Integer> nameToCode = new HashMap();
/*     */ 
/*  64 */   private static final Map<String, String> NAME_TO_CHARACTER = new HashMap();
/*     */ 
/*  67 */   private static final Map<String, String> CHARACTER_TO_NAME = new HashMap();
/*     */ 
/*     */   private static void loadGlyphList(String location)
/*     */   {
/* 115 */     BufferedReader glyphStream = null;
/*     */     try
/*     */     {
/* 118 */       InputStream resource = ResourceLoader.loadResource(location);
/* 119 */       if (resource == null)
/*     */       {
/* 121 */         throw new MissingResourceException("Glyphlist not found: " + location, Encoding.class.getName(), location);
/*     */       }
/*     */ 
/* 124 */       glyphStream = new BufferedReader(new InputStreamReader(resource));
/* 125 */       String line = null;
/* 126 */       while ((line = glyphStream.readLine()) != null)
/*     */       {
/* 128 */         line = line.trim();
/*     */ 
/* 130 */         if (!line.startsWith("#"))
/*     */         {
/* 132 */           int semicolonIndex = line.indexOf(';');
/* 133 */           if (semicolonIndex >= 0)
/*     */           {
/* 135 */             String unicodeValue = null;
/*     */             try
/*     */             {
/* 138 */               String characterName = line.substring(0, semicolonIndex);
/* 139 */               unicodeValue = line.substring(semicolonIndex + 1, line.length());
/* 140 */               StringTokenizer tokenizer = new StringTokenizer(unicodeValue, " ", false);
/* 141 */               StringBuilder value = new StringBuilder();
/* 142 */               while (tokenizer.hasMoreTokens())
/*     */               {
/* 144 */                 int characterCode = Integer.parseInt(tokenizer.nextToken(), 16);
/* 145 */                 value.append((char)characterCode);
/*     */               }
/* 147 */               if (NAME_TO_CHARACTER.containsKey(characterName))
/*     */               {
/* 149 */                 LOG.warn("duplicate value for characterName=" + characterName + "," + value);
/*     */               }
/*     */               else
/*     */               {
/* 153 */                 NAME_TO_CHARACTER.put(characterName, value.toString());
/*     */               }
/*     */             }
/*     */             catch (NumberFormatException nfe)
/*     */             {
/* 158 */               LOG.error("malformed unicode value " + unicodeValue, nfe);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/* 166 */       LOG.error("error while reading the glyph list.", io);
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       if (glyphStream != null)
/*     */       {
/*     */         try
/*     */         {
/* 174 */           glyphStream.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 178 */           LOG.error("error when closing the glyph list.", e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getCodeToNameMap()
/*     */   {
/* 191 */     return Collections.unmodifiableMap(this.codeToName);
/*     */   }
/*     */ 
/*     */   public Map<String, Integer> getNameToCodeMap()
/*     */   {
/* 200 */     return Collections.unmodifiableMap(this.nameToCode);
/*     */   }
/*     */ 
/*     */   public void addCharacterEncoding(int code, String name)
/*     */   {
/* 211 */     this.codeToName.put(Integer.valueOf(code), name);
/* 212 */     this.nameToCode.put(name, Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   public boolean hasCodeForName(String name)
/*     */   {
/* 223 */     return this.nameToCode.containsKey(name);
/*     */   }
/*     */ 
/*     */   public boolean hasNameForCode(int code)
/*     */   {
/* 234 */     return this.codeToName.containsKey(Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   public int getCode(String name)
/*     */     throws IOException
/*     */   {
/* 248 */     Integer code = (Integer)this.nameToCode.get(name);
/* 249 */     if (code == null)
/*     */     {
/* 251 */       throw new IOException("No character code for character name '" + name + "'");
/*     */     }
/* 253 */     return code.intValue();
/*     */   }
/*     */ 
/*     */   public String getName(int code)
/*     */     throws IOException
/*     */   {
/* 267 */     return (String)this.codeToName.get(Integer.valueOf(code));
/*     */   }
/*     */ 
/*     */   public static String getCharacterForName(String name)
/*     */   {
/* 280 */     if (NAME_TO_CHARACTER.containsKey(name))
/*     */     {
/* 282 */       LOG.debug("No character for name " + name);
/* 283 */       return (String)NAME_TO_CHARACTER.get(name);
/*     */     }
/* 285 */     return null;
/*     */   }
/*     */ 
/*     */   public String getNameFromCharacter(char c)
/*     */     throws IOException
/*     */   {
/* 299 */     String name = (String)CHARACTER_TO_NAME.get(Character.toString(c));
/* 300 */     if (name == null)
/*     */     {
/* 302 */       throw new IOException("No name for character '" + c + "'");
/*     */     }
/* 304 */     return name;
/*     */   }
/*     */ 
/*     */   public String getCharacter(int code)
/*     */     throws IOException
/*     */   {
/* 318 */     String name = getName(code);
/* 319 */     if (name != null)
/*     */     {
/* 321 */       return getCharacter(name);
/*     */     }
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */   public String getCharacter(String name)
/*     */   {
/* 335 */     String character = (String)NAME_TO_CHARACTER.get(name);
/* 336 */     if (character == null)
/*     */     {
/* 339 */       if (name.indexOf('.') > 0)
/*     */       {
/* 341 */         character = getCharacter(name.substring(0, name.indexOf('.')));
/*     */       }
/* 346 */       else if (name.startsWith("uni"))
/*     */       {
/* 348 */         int nameLength = name.length();
/* 349 */         StringBuilder uniStr = new StringBuilder();
/*     */         try
/*     */         {
/* 352 */           for (int chPos = 3; chPos + 4 <= nameLength; chPos += 4)
/*     */           {
/* 354 */             int characterCode = Integer.parseInt(name.substring(chPos, chPos + 4), 16);
/*     */ 
/* 356 */             if ((characterCode > 55295) && (characterCode < 57344))
/*     */             {
/* 358 */               LOG.warn("Unicode character name with not allowed code area: " + name);
/*     */             }
/*     */             else
/*     */             {
/* 362 */               uniStr.append((char)characterCode);
/*     */             }
/*     */           }
/* 365 */           character = uniStr.toString();
/* 366 */           NAME_TO_CHARACTER.put(name, character);
/*     */         }
/*     */         catch (NumberFormatException nfe)
/*     */         {
/* 370 */           LOG.warn("Not a number in Unicode character name: " + name);
/* 371 */           character = name;
/*     */         }
/*     */ 
/*     */       }
/* 375 */       else if (name.startsWith("u"))
/*     */       {
/*     */         try
/*     */         {
/* 379 */           int characterCode = Integer.parseInt(name.substring(1), 16);
/* 380 */           if ((characterCode > 55295) && (characterCode < 57344))
/*     */           {
/* 382 */             LOG.warn("Unicode character name with not allowed code area: " + name);
/*     */           }
/*     */           else
/*     */           {
/* 386 */             character = String.valueOf((char)characterCode);
/* 387 */             NAME_TO_CHARACTER.put(name, character);
/*     */           }
/*     */         }
/*     */         catch (NumberFormatException nfe)
/*     */         {
/* 392 */           LOG.warn("Not a number in Unicode character name: " + name);
/* 393 */           character = name;
/*     */         }
/*     */       }
/* 396 */       else if (this.nameToCode.containsKey(name))
/*     */       {
/* 398 */         int code = ((Integer)this.nameToCode.get(name)).intValue();
/* 399 */         character = Character.toString((char)code);
/*     */       }
/*     */       else
/*     */       {
/* 403 */         character = name;
/*     */       }
/*     */     }
/* 406 */     return character;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  73 */     loadGlyphList("org/apache/pdfbox/resources/glyphlist.txt");
/*     */ 
/*  75 */     loadGlyphList("org/apache/pdfbox/resources/additional_glyphlist.txt");
/*     */     try
/*     */     {
/*  80 */       String location = System.getProperty("glyphlist_ext");
/*  81 */       if (location != null)
/*     */       {
/*  83 */         File external = new File(location);
/*  84 */         if (external.exists())
/*     */         {
/*  86 */           loadGlyphList(location);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/*     */     }
/*     */ 
/*  95 */     NAME_TO_CHARACTER.put(".notdef", "");
/*  96 */     NAME_TO_CHARACTER.put("fi", "fi");
/*  97 */     NAME_TO_CHARACTER.put("fl", "fl");
/*  98 */     NAME_TO_CHARACTER.put("ffi", "ffi");
/*  99 */     NAME_TO_CHARACTER.put("ff", "ff");
/* 100 */     NAME_TO_CHARACTER.put("pi", "pi");
/*     */ 
/* 102 */     for (Map.Entry entry : NAME_TO_CHARACTER.entrySet())
/*     */     {
/* 104 */       CHARACTER_TO_NAME.put(entry.getValue(), entry.getKey());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.Encoding
 * JD-Core Version:    0.6.2
 */