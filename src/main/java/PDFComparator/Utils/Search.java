package PDFComparator.Utils;

import PDFComparator.PDFComparer;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Search
{
  public static void searchOnBasePdf(HashMap<String, String> paramHashMap, float paramFloat, PdfContentByte paramPdfContentByte, BaseFont paramBaseFont)
  {
    String str1 = "";
    float f1 = 0.0F;
    Iterator localIterator = PDFComparer.coordLst.iterator();
    while (localIterator.hasNext())
    {
      HashMap localHashMap = (HashMap)localIterator.next();
      String str2 = ((String)localHashMap.get("word")).replaceAll(" ", "");
      if ((((String)paramHashMap.get("New")).contains("++")) && (((String)paramHashMap.get("Base")).contains(str2)))
      {
        if ((str1.length() == 0) && (!str2.equals(":")))
        {
          f1 = Float.parseFloat((String)localHashMap.get("iniX"));
          str1 = str1 + str2;
        }
        else if (str1.length() > 0)
        {
          str1 = str1 + " " + str2;
        }
        if (str1.equals(paramHashMap.get("Base")))
        {
          float f2 = Float.parseFloat((String)localHashMap.get("finX"));
          paramPdfContentByte.beginText();
          paramPdfContentByte.setFontAndSize(paramBaseFont, 10.0F);
          paramPdfContentByte.setTextMatrix(f1, paramFloat - Float.parseFloat((String)localHashMap.get("Y")));
          paramPdfContentByte.endText();
          paramPdfContentByte.setRGBColorStroke(255, 0, 0);
          paramPdfContentByte.setLineWidth(1.0F);
          paramPdfContentByte.rectangle(f1 - 10.0F, paramFloat - (Float.parseFloat((String)localHashMap.get("Y")) + Float.parseFloat((String)localHashMap.get("hgh")) - 4.0F), f2 + Float.parseFloat((String)localHashMap.get("wid")) + 30.0F, Float.parseFloat((String)localHashMap.get("hgh")) * 2.0F);
          paramPdfContentByte.stroke();
          paramHashMap.put("Base", "");
          paramHashMap.put("New", "");
          str1 = " ";
        }
      }
    }
  }

  public static void searchOnNewPdf(HashMap<String, String> paramHashMap, float paramFloat, PdfContentByte paramPdfContentByte, BaseFont paramBaseFont, int paramInt)
  {
    String str1 = "";
    float f1 = 0.0F;
    Iterator localIterator = ((List)PDFComparer.coordPgeLst.get(paramInt - 1)).iterator();
    while (localIterator.hasNext())
    {
      HashMap localHashMap = (HashMap)localIterator.next();
      if ((localHashMap.get("word") != null) && (paramHashMap.get("New") != null))
      {
        String str2 = ((String)localHashMap.get("word")).replaceAll(" ", "");
        if (str2.equals(paramHashMap.get("New")))
        {
          paramPdfContentByte.beginText();
          paramPdfContentByte.setFontAndSize(paramBaseFont, 10.0F);
          paramPdfContentByte.setTextMatrix(Float.parseFloat((String)localHashMap.get("finX")) + 10.0F, paramFloat - Float.parseFloat((String)localHashMap.get("Y")));
          paramPdfContentByte.endText();
          paramPdfContentByte.setRGBColorStroke(255, 0, 0);
          paramPdfContentByte.setLineWidth(1.0F);
          paramPdfContentByte.ellipse(Float.parseFloat((String)localHashMap.get("iniX")) - 1.0F, paramFloat - (Float.parseFloat((String)localHashMap.get("Y")) + Float.parseFloat((String)localHashMap.get("hgh")) - 4.0F), Float.parseFloat((String)localHashMap.get("finX")) + 10.0F, paramFloat - (Float.parseFloat((String)localHashMap.get("Y")) - Float.parseFloat((String)localHashMap.get("hgh")) - 2.0F));
          paramPdfContentByte.stroke();
          paramHashMap.put("Base", "");
          paramHashMap.put("New", "");
        }
        else if ((((String)paramHashMap.get("Base")).contains("--")) && (((String)paramHashMap.get("New")).contains(str2)))
        {
          if ((str1.length() == 0) && (!str2.equals(":")) && (((String)paramHashMap.get("New")).startsWith(str2)))
          {
            f1 = Float.parseFloat((String)localHashMap.get("iniX"));
            str1 = str1 + str2;
          }
          else if (str1.length() > 0)
          {
            str1 = str1 + " " + str2;
          }
          if (str1.equals(paramHashMap.get("New")))
          {
            float f2 = Float.parseFloat((String)localHashMap.get("finX"));
            paramHashMap.put("Base", "");
            paramHashMap.put("New", "");
            paramPdfContentByte.setRGBColorStroke(255, 0, 0);
            paramPdfContentByte.setLineWidth(2.0F);
            paramPdfContentByte.moveTo(f1, paramFloat - (Float.parseFloat((String)localHashMap.get("Y")) - Float.parseFloat((String)localHashMap.get("hgh")) / 2.0F));
            paramPdfContentByte.lineTo(f2, paramFloat - (Float.parseFloat((String)localHashMap.get("Y")) - Float.parseFloat((String)localHashMap.get("hgh")) / 2.0F));
            paramPdfContentByte.stroke();
            str1 = "";
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     Utils.Search
 * JD-Core Version:    0.6.2
 */