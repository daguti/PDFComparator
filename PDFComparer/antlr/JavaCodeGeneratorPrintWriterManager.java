package antlr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public abstract interface JavaCodeGeneratorPrintWriterManager
{
  public abstract PrintWriter setupOutput(Tool paramTool, Grammar paramGrammar)
    throws IOException;

  public abstract PrintWriter setupOutput(Tool paramTool, String paramString)
    throws IOException;

  public abstract void startMapping(int paramInt);

  public abstract void startSingleSourceLineMapping(int paramInt);

  public abstract void endMapping();

  public abstract void finishOutput()
    throws IOException;

  public abstract Map getSourceMaps();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.JavaCodeGeneratorPrintWriterManager
 * JD-Core Version:    0.6.2
 */