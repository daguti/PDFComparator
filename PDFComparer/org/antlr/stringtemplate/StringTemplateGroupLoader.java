package org.antlr.stringtemplate;

public abstract interface StringTemplateGroupLoader
{
  public abstract StringTemplateGroup loadGroup(String paramString);

  public abstract StringTemplateGroup loadGroup(String paramString, StringTemplateGroup paramStringTemplateGroup);

  public abstract StringTemplateGroup loadGroup(String paramString, Class paramClass, StringTemplateGroup paramStringTemplateGroup);

  public abstract StringTemplateGroupInterface loadInterface(String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.StringTemplateGroupLoader
 * JD-Core Version:    0.6.2
 */