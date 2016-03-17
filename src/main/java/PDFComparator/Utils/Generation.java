package PDFComparator.Utils;

import PDFComparator.PDFComparer;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.Matrix;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Generation
{
  private static List<HashMap<String, String>> coordLst;

  public static String generateDiffPdf()
    throws IOException, DocumentException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
    Date localDate = new Date();
    PdfReader localPdfReader = new PdfReader(PDFComparer.newPdfNam);
    String str;
    if (PDFComparer.newPdfNam.lastIndexOf("/") == -1)
      str = PDFComparer.newPdfNam.substring(0, PDFComparer.newPdfNam.lastIndexOf("\\") + 1) + "Diff-" + localSimpleDateFormat.format(localDate) + "-" + PDFComparer.newPdfNam.substring(PDFComparer.newPdfNam.lastIndexOf("\\") + 1, PDFComparer.newPdfNam.length());
    else
      str = PDFComparer.newPdfNam.substring(0, PDFComparer.newPdfNam.lastIndexOf("/") + 1) + "Diff-" + localSimpleDateFormat.format(localDate) + "-" + PDFComparer.newPdfNam.substring(PDFComparer.newPdfNam.lastIndexOf("/") + 1, PDFComparer.newPdfNam.length());
    PdfStamper localPdfStamper = new PdfStamper(localPdfReader, new FileOutputStream(str));
    BaseFont localBaseFont = BaseFont.createFont("Helvetica", "Cp1252", false);
    localBaseFont.setSubset(true);
    for (int i = 1; i <= localPdfReader.getNumberOfPages(); i++)
    {
      Rectangle localRectangle = localPdfReader.getPageSize(i);
      int j = localPdfReader.getPageRotation(i);
      float f = j == 0 ? localRectangle.getHeight() : localRectangle.getWidth();
      PdfContentByte localPdfContentByte = localPdfStamper.getOverContent(i);
      if (!((List)PDFComparer.imgDifPgeLst.get(i - 1)).isEmpty())
        stampImagesDifferences(i, localPdfContentByte);
      PDFComparer.difLst = (List)PDFComparer.difPgeLst.get(i - 1);
      Iterator localIterator = PDFComparer.difLst.iterator();
      while (localIterator.hasNext())
      {
        HashMap localHashMap = (HashMap)localIterator.next();
        if ((localHashMap.get("New") != null) && (localHashMap.get("Base") != null) && (((String)localHashMap.get("New")).contains("++")))
        {
          coordLst = (List)PDFComparer.coordBasPgeLst.get(i - 1);
          Search.searchOnBasePdf(localHashMap, f, localPdfContentByte, localBaseFont);
        }
        else if ((localHashMap.get("New") != null) && (localHashMap.get("Base") != null))
        {
          Search.searchOnNewPdf(localHashMap, f, localPdfContentByte, localBaseFont, i);
        }
      }
      localPdfContentByte.closePath();
    }
    localPdfStamper.close();
    localPdfReader.close();
    return str;
  }

  private static void stampImagesDifferences(int paramInt, PdfContentByte paramPdfContentByte)
  {
    List localList = (List)PDFComparer.imgDifPgeLst.get(paramInt - 1);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      HashMap localHashMap = (HashMap)localIterator.next();
      if (!localHashMap.isEmpty())
      {
        Matrix localMatrix = (Matrix)localHashMap.get("newCoord");
        paramPdfContentByte.setRGBColorStroke(255, 0, 0);
        paramPdfContentByte.setLineWidth(3.0F);
        paramPdfContentByte.rectangle(localMatrix.get(6), localMatrix.get(7), localMatrix.get(0), localMatrix.get(4));
        paramPdfContentByte.stroke();
      }
    }
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     Utils.Generation
 * JD-Core Version:    0.6.2
 */