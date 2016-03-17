package HTMLOutput;

import PDFComparator.PDFComparer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

public class HTMLGeneration
{
  private final String tplPth = this.jarFilPth.substring(0, this.jarFilPth.lastIndexOf(File.separator)) + File.separator + "templates";
  private final String jarFilPth = new File(HTMLGeneration.class.getProtectionDomain().getCodeSource().getLocation().getPath()) + "";
  private final StringTemplate st;
  private String resFil;
  private int numTstOk = 0;
  private int numTstFail = 0;

  public HTMLGeneration()
  {
    StringTemplateGroup localStringTemplateGroup = new StringTemplateGroup("myGroup", this.tplPth, DefaultTemplateLexer.class);
    this.st = localStringTemplateGroup.getInstanceOf("selenium");
  }

  public void generateHTML()
  {
    HashMap localHashMap = new HashMap();
    if ((PDFComparer.imgDif | PDFComparer.txtDif))
    {
      PDFComparer.filesOk = "failed";
      localHashMap.put("ok", "failed");
      this.numTstFail += 1;
    }
    else
    {
      if (!PDFComparer.filesOk.equals("failed"))
        PDFComparer.filesOk = "passed";
      this.numTstOk += 1;
      localHashMap.put("ok", "passed");
    }
    localHashMap.put("url", PDFComparer.newPdfNam);
    localHashMap.put("name", PDFComparer.newPdfNam.substring(PDFComparer.newPdfNam.lastIndexOf("\\") + 1, PDFComparer.newPdfNam.length()));
    localHashMap.put("baseUrl", PDFComparer.basePdfNam);
    localHashMap.put("baseName", PDFComparer.basePdfNam.substring(PDFComparer.basePdfNam.lastIndexOf("\\") + 1, PDFComparer.basePdfNam.length()));
    localHashMap.put("dffUrl", PDFComparer.difPdfPth);
    localHashMap.put("dffName", PDFComparer.difPdfPth.substring(PDFComparer.difPdfPth.lastIndexOf("\\") + 1, PDFComparer.difPdfPth.length()));
    localHashMap.put("reason", PDFComparer.errMsg);
    PDFComparer.listF.add(localHashMap);
  }

  public void generateHTMLFile()
  {
    File localFile = new File(this.resFil);
    try
    {
      this.st.setAttribute("filesOk", PDFComparer.filesOk);
      this.st.setAttribute("title", "PDF COMPARISON");
      this.st.setAttribute("nOk", this.numTstOk);
      this.st.setAttribute("nTestFails", this.numTstFail);
      this.st.setAttribute("nFails", this.numTstFail);
      this.st.setAttribute("nErr", 0);
      if (this.numTstFail == 0)
        this.st.setAttribute("ok", "passed");
      else
        this.st.setAttribute("ok", "failed");
      this.st.setAttribute("nTest", PDFComparer.listF.size());
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      this.st.setAttribute("files", PDFComparer.listF);
      this.st.setAttribute("time", Long.valueOf((System.currentTimeMillis() - PDFComparer.startTime) / 1000L));
      localFileOutputStream.write(this.st.toString().getBytes());
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Logger.getLogger(HTMLGeneration.class.getName()).log(Level.SEVERE, null, localFileNotFoundException);
    }
    catch (IOException localIOException)
    {
      Logger.getLogger(HTMLGeneration.class.getName()).log(Level.SEVERE, null, localIOException);
    }
  }

  public String getresFil()
  {
    return this.resFil;
  }

  public void setResFil(String paramString)
  {
    this.resFil = paramString;
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     HTMLOutput.HTMLGeneration
 * JD-Core Version:    0.6.2
 */