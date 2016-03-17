/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ public enum ErrorType
/*    */ {
/* 33 */   NO_SUCH_TEMPLATE("no such template: %s"), 
/* 34 */   NO_IMPORTED_TEMPLATE("no such template: super.%s"), 
/* 35 */   NO_SUCH_ATTRIBUTE("attribute %s isn't defined"), 
/* 36 */   REF_TO_IMPLICIT_ATTRIBUTE_OUT_OF_SCOPE("implicitly-defined attribute %s not visible"), 
/* 37 */   MISSING_FORMAL_ARGUMENTS("missing argument definitions"), 
/* 38 */   NO_SUCH_PROPERTY("no such property or can't access: %s"), 
/* 39 */   MAP_ARGUMENT_COUNT_MISMATCH("iterating through %s values in zip map but template has %s declared arguments"), 
/* 40 */   ARGUMENT_COUNT_MISMATCH("passed %s arg(s) to template %s with %s declared arg(s)"), 
/* 41 */   EXPECTING_STRING("function %s expects a string not %s"), 
/* 42 */   WRITER_CTOR_ISSUE("%s(Writer) constructor doesn't exist"), 
/* 43 */   CANT_IMPORT("can't find template(s) in import \"%s\""), 
/*    */ 
/* 46 */   SYNTAX_ERROR("%s"), 
/* 47 */   TEMPLATE_REDEFINITION("redefinition of template %s"), 
/* 48 */   EMBEDDED_REGION_REDEFINITION("region %s is embedded and thus already implicitly defined"), 
/* 49 */   REGION_REDEFINITION("redefinition of region %s"), 
/* 50 */   MAP_REDEFINITION("redefinition of dictionary %s"), 
/* 51 */   ALIAS_TARGET_UNDEFINED("cannot alias %s to undefined template: %s"), 
/* 52 */   TEMPLATE_REDEFINITION_AS_MAP("redefinition of template %s as a map"), 
/* 53 */   LEXER_ERROR("%s"), 
/* 54 */   NO_DEFAULT_VALUE("missing dictionary default value"), 
/* 55 */   NO_SUCH_FUNCTION("no such function: %s"), 
/* 56 */   NO_SUCH_REGION("template %s doesn't have a region called %s"), 
/* 57 */   NO_SUCH_OPTION("no such option: %s"), 
/* 58 */   INVALID_TEMPLATE_NAME("invalid template name or path: %s"), 
/* 59 */   ANON_ARGUMENT_MISMATCH("anonymous template has %s arg(s) but mapped across %s value(s)"), 
/* 60 */   REQUIRED_PARAMETER_AFTER_OPTIONAL("required parameters (%s) must appear before optional parameters"), 
/*    */ 
/* 63 */   INTERNAL_ERROR("%s"), 
/* 64 */   WRITE_IO_ERROR("error writing output caused by"), 
/* 65 */   CANT_LOAD_GROUP_FILE("can't load group file %s");
/*    */ 
/*    */   public String message;
/*    */ 
/* 69 */   private ErrorType(String m) { this.message = m; }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.ErrorType
 * JD-Core Version:    0.6.2
 */