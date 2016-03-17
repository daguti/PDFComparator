package com.itextpdf.text.pdf.parser;

import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfObject;
import java.util.ArrayList;

public abstract interface ContentOperator
{
  public abstract void invoke(PdfContentStreamProcessor paramPdfContentStreamProcessor, PdfLiteral paramPdfLiteral, ArrayList<PdfObject> paramArrayList)
    throws Exception;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.ContentOperator
 * JD-Core Version:    0.6.2
 */