package PDFComparator;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageRenderListener
  implements RenderListener
{
  int pat;
  int pgeNum;
  List<HashMap<String, Object>> imgMapLst;

  public ImageRenderListener(int paramInt1, int paramInt2)
  {
    this.pat = paramInt1;
    this.pgeNum = paramInt2;
    if (paramInt1 == 1)
    {
      if (PDFComparer.imgLst.size() > paramInt2 - 1)
        this.imgMapLst = ((List)PDFComparer.imgLst.get(paramInt2 - 1));
    }
    else
      this.imgMapLst = new ArrayList();
  }

  public List<HashMap<String, Object>> getImgMapLst()
  {
    return this.imgMapLst;
  }

  public void beginTextBlock()
  {
  }

  public void renderText(TextRenderInfo paramTextRenderInfo)
  {
  }

  public void endTextBlock()
  {
  }

  public void renderImage(ImageRenderInfo paramImageRenderInfo)
  {
    try
    {
      PdfImageObject localPdfImageObject = paramImageRenderInfo.getImage();
      if (this.pat == 0)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("basImg", localPdfImageObject.getBufferedImage());
        localHashMap.put("basCoord", paramImageRenderInfo.getImageCTM());
        this.imgMapLst.add(localHashMap);
      }
      else if ((this.imgMapLst != null) && (this.imgMapLst.size() > PDFComparer.imgCntPge))
      {
        ((HashMap)this.imgMapLst.get(PDFComparer.imgCntPge)).put("newImg", localPdfImageObject.getBufferedImage());
        ((HashMap)this.imgMapLst.get(PDFComparer.imgCntPge)).put("newCoord", paramImageRenderInfo.getImageCTM());
      }
      else
      {
        PDFComparer.newImgSiz += 1;
      }
      PDFComparer.imgCntPge += 1;
    }
    catch (IOException localIOException)
    {
      Logger.getLogger(ImageRenderListener.class.getName()).log(Level.SEVERE, null, localIOException);
    }
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     PDFComparator.ImageRenderListener
 * JD-Core Version:    0.6.2
 */