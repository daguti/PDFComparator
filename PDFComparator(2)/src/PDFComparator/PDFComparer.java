package PDFComparator;

import HTMLOutput.HTMLGeneration;
import Utils.Compare;
import Utils.PDFElements;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFComparer
{
  public static List<List<HashMap<String, String>>> coordPgeLst = new ArrayList();
  public static List<List<HashMap<String, String>>> coordBasPgeLst = new ArrayList();
  public static List<HashMap<String, String>> coordLst = new ArrayList();
  public static List<List<HashMap<String, String>>> difPgeLst = new ArrayList();
  public static List<HashMap<String, String>> difLst = new ArrayList();
  public static float txtY = 0.0F;
  public static float txtX = 0.0F;
  public static String newPdfNam;
  public static String basePdfNam;
  public static List<List<HashMap<String, Object>>> imgLst = new ArrayList();
  public static List<List<HashMap<String, Object>>> imgDifPgeLst = new ArrayList();
  public static int imgCntPge;
  public static int basImgSiz;
  public static int newImgSiz;
  public static boolean txtDif = false;
  public static boolean imgDif = false;
  public static boolean guiCall = false;
  public static String difPdfPth;
  public static String resFil;
  public static List<Map<String, Object>> listF = new ArrayList();
  private static final HTMLGeneration htmlGen = new HTMLGeneration();
  public static String errMsg = "";
  public static String filesOk = "";
  public static long startTime;
  private static boolean fst = true;

  public static void main(String[] paramArrayOfString)
    throws IllegalArgumentException
  {
    startTime = System.currentTimeMillis();
    if (paramArrayOfString.length == 0)
      throw new IllegalArgumentException("PDF List Must be passed in execution Arguments");
    if (paramArrayOfString.length < 2)
      throw new IllegalArgumentException("Results directory Must be passed in execution Arguments");
    readFromFile(paramArrayOfString[0], paramArrayOfString[1]);
  }

  private static void readFromFile(String paramString1, String paramString2)
  {
    try
    {
      resFil = paramString2;
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString1));
      String str1;
      while ((str1 = localBufferedReader.readLine()) != null)
      {
        difPdfPth = "";
        String str2 = str1.substring(0, str1.indexOf("--") - 1);
        String str3 = str1.substring(str1.indexOf("--") + 3);
        launch(str2, str3);
        fst = false;
        imgLst.clear();
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Logger.getLogger(PDFComparer.class.getName()).log(Level.SEVERE, null, localFileNotFoundException);
    }
    catch (IOException localIOException)
    {
      Logger.getLogger(PDFComparer.class.getName()).log(Level.SEVERE, null, localIOException);
    }
    htmlGen.setResFil(resFil);
    htmlGen.generateHTMLFile();
  }

  public static HashMap<String, Object> launch(String paramString1, String paramString2)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    HashMap localHashMap = new HashMap();
    int i = 0;
    try
    {
      errMsg = "";
      newPdfNam = paramString2;
      basePdfNam = paramString1;
      FileInputStream localFileInputStream = new FileInputStream(basePdfNam);
      PDDocument localPDDocument1 = PDDocument.load(localFileInputStream);
      localFileInputStream.close();
      localFileInputStream = new FileInputStream(newPdfNam);
      PDDocument localPDDocument2 = PDDocument.load(localFileInputStream);
      localFileInputStream.close();
      PDFElements.getPdfPlainText(localPDDocument1, localArrayList1);
      PDFElements.getPdfPlainText(localPDDocument2, localArrayList2);
      localPDDocument1.close();
      localPDDocument2.close();
      PDFElements.getPdfImages(basePdfNam, 0);
      PDFElements.getPdfImages(newPdfNam, 1);
      if ((localArrayList1.size() == localArrayList2.size()) && (basImgSiz == newImgSiz))
      {
        txtDif = Compare.compareText(localArrayList1, localArrayList2);
        imgDif = Compare.compareImages();
        if ((txtDif) || (imgDif))
        {
          localObject1 = new PrintTextLocations();
          ((PrintTextLocations)localObject1).printLocations(newPdfNam, 0);
          localObject1 = new PrintTextLocations();
          ((PrintTextLocations)localObject1).printLocations(basePdfNam, 1);
          i = 1;
          localObject2 = difLst.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (HashMap)((Iterator)localObject2).next();
            errMsg = new StringBuilder().append(errMsg).append(localObject3).append("<br>").toString();
          }
        }
        else
        {
          errMsg = "";
        }
      }
      else
      {
        i = 2;
        if (localArrayList1.size() != localArrayList2.size())
        {
          errMsg = "The PDFs have different pages length: \n";
          errMsg = new StringBuilder().append(errMsg).append(localArrayList1.size() > localArrayList2.size() ? "\t Base PDF have more pages \n" : "\t New PDF have more pages \n").toString();
          i++;
        }
        if (basImgSiz != newImgSiz)
        {
          errMsg = new StringBuilder().append(errMsg).append("There are more images in one of the PDFs: \n").toString();
          errMsg = new StringBuilder().append(errMsg).append(basImgSiz > newImgSiz ? "\t Base PDF have more images \n" : "\t New PDF have more images \n").toString();
          i++;
        }
        basImgSiz = 0;
        newImgSiz = 0;
      }
    }
    catch (IOException localIOException)
    {
      Object localObject1;
      i = 2;
      errMsg = "Exception caused during execution \n";
      str1 = localIOException.getMessage();
      localHashMap.put("errMsg", errMsg);
      localHashMap.put("errChk", Integer.valueOf(i));
      localHashMap.put("errExc", str1);
      Logger.getLogger(PDFComparer.class.getName()).log(Level.SEVERE, errMsg, localIOException);
      localObject2 = localHashMap;
      return localObject2;
    }
    catch (Exception localException)
    {
      Object localObject3;
      i = 2;
      errMsg = "Exception caused during execution \n";
      String str1 = localException.getMessage();
      localHashMap.put("errMsg", errMsg);
      localHashMap.put("errChk", Integer.valueOf(i));
      localHashMap.put("errExc", str1);
      Logger.getLogger(PDFComparer.class.getName()).log(Level.SEVERE, errMsg, localException);
      Object localObject2 = localHashMap;
      return localObject2;
    }
    finally
    {
      generateResultDirectories();
      if (guiCall)
        GUI.MainWindow.difPth = difPdfPth;
      if ((txtDif) || (imgDif))
        String str2 = difPdfPth.lastIndexOf("/") != -1 ? difPdfPth.substring(difPdfPth.lastIndexOf("/") + 1) : difPdfPth.substring(difPdfPth.lastIndexOf("\\") + 1);
      htmlGen.generateHTML();
      if (guiCall)
        clearLists();
    }
    if (i == 0)
      errMsg = "PDF files contents are equals \n";
    localHashMap.put("errMsg", errMsg);
    localHashMap.put("errChk", Integer.valueOf(i));
    return localHashMap;
  }

  private static void clearLists()
  {
    coordPgeLst.clear();
    coordBasPgeLst.clear();
    coordLst.clear();
    difPgeLst.clear();
    difLst.clear();
    imgLst.clear();
    imgDifPgeLst.clear();
    txtY = 0.0F;
    txtX = 0.0F;
    imgCntPge = 0;
    basImgSiz = 0;
    newImgSiz = 0;
    newPdfNam = "";
    basePdfNam = "";
    difPdfPth = "";
    txtDif = false;
    imgDif = false;
    guiCall = false;
  }

  public static String newBigLength(String paramString1, String paramString2, int paramInt)
  {
    String str5 = "";
    int k = 0;
    String str1 = paramString1;
    String str2 = paramString2;
    int i = paramString1.indexOf("\r");
    int j = paramString2.indexOf("\r");
    String str3 = str1.substring(0, i);
    String str4 = str2.substring(0, j);
    while (paramString2.length() > j)
    {
      if (paramString1.length() == paramString2.length())
        return paramString1;
      if ((!str3.contains(str4)) && (!str3.equals(str4)) && (!str3.contains("Generado")) && (!str3.contains("Created")) && (!str3.contains("Version")) && (!str3.contains("OS acc")) && (!str3.contains("User")))
      {
        for (int m = 0; m < str4.length(); m++)
          if (str4.substring(m, m + 1).equals(" "))
            str5 = new StringBuilder().append(str5).append(" ").toString();
          else if (str4.substring(m, m + 1).equals("\n"))
            str5 = new StringBuilder().append(str5).append("").toString();
          else if (paramInt == 0)
            str5 = new StringBuilder().append(str5).append("-").toString();
          else if (paramInt == 1)
            str5 = new StringBuilder().append(str5).append("+").toString();
        str5 = new StringBuilder().append(str5).append("\r").toString();
        str1 = new StringBuilder().append(str5).append(str1).toString();
        m = paramString1.length() - str1.length() + str5.length();
        paramString1 = new StringBuilder().append(paramString1.substring(0, m + 1)).append(str1).toString();
      }
      if (k != 0)
        break;
      if (str5.length() > 0)
      {
        i = str5.length();
        str1 = str1.substring(i);
        i = str1.indexOf("\r");
        str5 = "";
        str2 = str2.substring(j + 1);
        j = str2.indexOf("\r");
        if ((j == -1) || (i == -1))
        {
          str4 = str2;
          str3 = str1;
        }
        else
        {
          str4 = str2.substring(0, j);
          str3 = str1.substring(0, i + 1);
        }
      }
      else
      {
        str1 = str1.substring(i + 1);
        i = str1.indexOf("\r");
        str2 = str2.substring(j + 1);
        j = str2.indexOf("\r");
        if ((i == -1) || (j == -1))
        {
          str3 = str1;
          str4 = str2;
          k = 1;
        }
        else
        {
          str3 = str1.substring(0, i);
          str4 = str2.substring(0, j);
        }
      }
    }
    return paramString1;
  }

  private static void generateResultDirectories()
  {
    String str = difPdfPth;
    if (fst)
      new File(resFil.substring(0, resFil.lastIndexOf(File.separator))).mkdirs();
    new File(str).renameTo(new File(difPdfPth));
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     PDFComparator.PDFComparer
 * JD-Core Version:    0.6.2
 */