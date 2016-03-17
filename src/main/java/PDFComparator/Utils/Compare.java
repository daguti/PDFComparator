package PDFComparator.Utils;

import PDFComparator.PDFComparer;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Compare
{
  public static boolean compareImages()
  {
    boolean bool = false;
    Iterator localIterator1 = PDFComparer.imgLst.iterator();
    while (localIterator1.hasNext())
    {
      List localList = (List)localIterator1.next();
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
      {
        HashMap localHashMap = (HashMap)localIterator2.next();
        BufferedImage localBufferedImage1 = (BufferedImage)localHashMap.get("basImg");
        BufferedImage localBufferedImage2 = (BufferedImage)localHashMap.get("newImg");
        if (!compareBufferedImages(localBufferedImage1, localBufferedImage2))
        {
          localArrayList.add(localHashMap);
          bool = true;
        }
      }
      PDFComparer.imgDifPgeLst.add(localArrayList);
    }
    return bool;
  }

  private static boolean compareBufferedImages(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2)
  {
    if ((paramBufferedImage1.getWidth() == paramBufferedImage2.getWidth()) && (paramBufferedImage1.getHeight() == paramBufferedImage2.getHeight())) {
      for (int i = 0; i < paramBufferedImage1.getWidth(); i++) {
        for (int j = 0; j < paramBufferedImage1.getHeight(); j++) {
          if (paramBufferedImage1.getRGB(i, j) != paramBufferedImage2.getRGB(i, j)) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  public static boolean compareText(List<String> paramList1, List<String> paramList2)
  {
    HashMap localHashMap = new HashMap();
    boolean bool = false;
    PDFComparer.difLst = new ArrayList();
    for (int k = 0; k < paramList1.size(); k++)
    {
      String str3 = (String)paramList1.get(k);
      String str4 = (String)paramList2.get(k);
      str3 = str3.replaceAll("--", "  ");
      str4 = str4.replaceAll("--", "  ");
      if (str3.length() != str4.length())
        if (str4.length() - str3.length() > 5)
        {
          str3 = PDFComparer.newBigLength(str3, str4, 0);
          paramList1.remove(k);
          paramList1.add(k, str3);
        }
        else if (str3.length() - str4.length() > 5)
        {
          str4 = PDFComparer.newBigLength(str4, str3, 1);
          paramList2.remove(k);
          paramList2.add(k, str4);
        }
      int i = ((String)paramList1.get(k)).indexOf("\n");
      int j = ((String)paramList2.get(k)).indexOf("\n");
      String str1 = ((String)paramList1.get(k)).substring(0, i);
      for (String str2 = ((String)paramList2.get(k)).substring(0, j); (((String)paramList1.get(k)).length() > i) || (((String)paramList2.get(k)).length() > j); str2 = str4.substring(0, j))
      {
        if ((!str1.equals(str2)) && (!str1.contains("--")) && (!str2.contains("++")) && (!str1.equals(" ")))
        {
          if ((!str1.contains("Created on")) && (!str1.contains("Version:")))
          {
            localHashMap = new HashMap();
            if (str1.length() > 0)
              if (str1.substring(str1.length() - 1, str1.length()).equals("\r"))
                localHashMap.put("Base", str1.substring(0, str1.length() - 1));
              else
                localHashMap.put("Base", str1);
            if (str2.length() > 0)
              if (str2.substring(str2.length() - 1, str2.length()).equals("\r"))
                localHashMap.put("New", str2.substring(0, str2.length() - 1));
              else
                localHashMap.put("New", str2);
            PDFComparer.difLst.add(localHashMap);
            if (!localHashMap.isEmpty())
              localHashMap = new HashMap();
          }
        }
        else if (((str1.contains("--")) || (str2.contains("++"))) && (localHashMap.get("New") == null))
        {
          String str5 = str2 + str4.substring(j, str4.indexOf("\n"));
          String str6 = str1 + str3.substring(i, str3.indexOf("\n"));
          localHashMap.put("New", str5);
          localHashMap.put("Base", str6);
          i = str3.indexOf("\n") + 1;
          j = str4.indexOf("\n") + 1;
          str5 = "";
          str6 = "";
          PDFComparer.difLst.add(localHashMap);
          localHashMap = new HashMap();
        }
        if ((i != str3.length()) && (j != str4.length()))
        {
          str3 = str3.substring(i + 1, str3.length());
          str4 = str4.substring(j + 1, str4.length());
          i = str3.indexOf("\n");
          j = str4.indexOf("\n");
        }
        if ((i == -1) || (j == -1))
        {
          i = str3.length();
          j = str4.length();
        }
        else
        {
          if ((i == str3.length()) || (j == str4.length()))
            break;
        }
        str1 = str3.substring(0, i);
      }
      PDFComparer.difPgeLst.add(PDFComparer.difLst);
      if ((!PDFComparer.difLst.isEmpty()) && (!bool))
        bool = true;
    }
    return bool;
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     Utils.Compare
 * JD-Core Version:    0.6.2
 */