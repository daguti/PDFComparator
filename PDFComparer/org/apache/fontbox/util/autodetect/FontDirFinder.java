package org.apache.fontbox.util.autodetect;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract interface FontDirFinder
{
  public abstract List<File> find()
    throws IOException;

  public abstract Map<String, String> getCommonTTFMapping();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.util.autodetect.FontDirFinder
 * JD-Core Version:    0.6.2
 */