package PDFComparator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

public class PrintTextLocations extends PDFTextStripper
{
  int count = 0;
  HashMap<String, String> wrdMap;
  String word;
  boolean fst = true;

  public PrintTextLocations()
    throws IOException
  {
    super.setSortByPosition(true);
  }

  public void printLocations(String paramString, int paramInt)
    throws Exception
  {
    PDDocument localPDDocument = null;
    try
    {
      File localFile = new File(paramString);
      localPDDocument = PDDocument.load(localFile);
      if (localPDDocument.isEncrypted())
        localPDDocument.decrypt("");
      PrintTextLocations localPrintTextLocations = new PrintTextLocations();
      List localList = localPDDocument.getDocumentCatalog().getAllPages();
      for (int i = 0; i < localList.size(); i++)
      {
        PDFComparer.coordLst = new ArrayList();
        PDPage localPDPage = (PDPage)localList.get(i);
        PDStream localPDStream = localPDPage.getContents();
        if (localPDStream != null)
          localPrintTextLocations.processStream(localPDPage, localPDPage.findResources(), localPDPage.getContents().getStream());
        if (paramInt == 0)
          PDFComparer.coordPgeLst.add(PDFComparer.coordLst);
        else if (paramInt == 1)
          PDFComparer.coordBasPgeLst.add(PDFComparer.coordLst);
      }
    }
    finally
    {
      if (localPDDocument != null)
        localPDDocument.close();
    }
  }

  protected void processTextPosition(TextPosition paramTextPosition)
  {
    if ((PDFComparer.txtY != paramTextPosition.getYDirAdj()) || (paramTextPosition.getCharacter().equals(" ")) || (paramTextPosition.getXDirAdj() - PDFComparer.txtX - paramTextPosition.getWidthDirAdj() > 8.0F))
    {
      if (!this.fst)
      {
        this.wrdMap.put("word", this.word);
        this.wrdMap.put("finX", Float.toString(PDFComparer.txtX));
        this.wrdMap.put("wid", Float.toString(paramTextPosition.getWidth()));
        PDFComparer.coordLst.add(this.wrdMap);
      }
      PDFComparer.txtY = paramTextPosition.getYDirAdj();
      this.wrdMap = new HashMap();
      this.wrdMap.put("iniX", Float.toString(paramTextPosition.getXDirAdj()));
      this.wrdMap.put("hgh", Float.toString(paramTextPosition.getHeightDir()));
      this.wrdMap.put("Y", Float.toString(paramTextPosition.getYDirAdj()));
      this.word = paramTextPosition.getCharacter();
      this.fst = false;
    }
    else
    {
      this.word += paramTextPosition.getCharacter();
    }
    PDFComparer.txtX = paramTextPosition.getXDirAdj();
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     PDFComparator.PrintTextLocations
 * JD-Core Version:    0.6.2
 */