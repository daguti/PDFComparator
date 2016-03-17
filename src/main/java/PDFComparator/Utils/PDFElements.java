package PDFComparator.Utils;

import PDFComparator.ImageRenderListener;
import PDFComparator.PDFComparer;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFElements
{
  public static void getPdfImages(String paramString, int paramInt)
    throws IOException
  {
    PdfReader localPdfReader = new PdfReader(paramString);
    PdfReaderContentParser localPdfReaderContentParser = new PdfReaderContentParser(localPdfReader);
    for (int i = 1; i <= localPdfReader.getNumberOfPages(); i++)
    {
      ImageRenderListener localImageRenderListener = new ImageRenderListener(paramInt, i);
      localPdfReaderContentParser.processContent(i, localImageRenderListener);
      if (paramInt == 0)
      {
        PDFComparer.imgLst.add(localImageRenderListener.getImgMapLst());
        PDFComparer.basImgSiz += PDFComparer.imgCntPge;
        PDFComparer.imgCntPge = 0;
      }
      else if ((PDFComparer.imgLst != null) && (PDFComparer.imgLst.size() > i - 1) && (!((List)PDFComparer.imgLst.get(i - 1)).isEmpty()))
      {
        PDFComparer.imgLst.remove(i - 1);
        PDFComparer.imgLst.add(i - 1, localImageRenderListener.getImgMapLst());
        PDFComparer.newImgSiz += PDFComparer.imgCntPge;
        PDFComparer.imgCntPge = 0;
      }
    }
  }

  public static void getPdfPlainText(PDDocument paramPDDocument, List<String> paramList)
    throws IOException
  {
    for (int i = 1; i <= paramPDDocument.getNumberOfPages(); i++)
    {
      PDFTextStripper localPDFTextStripper = new PDFTextStripper();
      localPDFTextStripper.setStartPage(i);
      localPDFTextStripper.setEndPage(i);
      localPDFTextStripper.setSortByPosition(true);
      paramList.add(localPDFTextStripper.getText(paramPDDocument));
    }
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     Utils.PDFElements
 * JD-Core Version:    0.6.2
 */