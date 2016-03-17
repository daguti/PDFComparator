package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public abstract interface DebugEventListener
{
  public static final String PROTOCOL_VERSION = "2";
  public static final int TRUE = 1;
  public static final int FALSE = 0;

  public abstract void enterRule(String paramString1, String paramString2);

  public abstract void enterAlt(int paramInt);

  public abstract void exitRule(String paramString1, String paramString2);

  public abstract void enterSubRule(int paramInt);

  public abstract void exitSubRule(int paramInt);

  public abstract void enterDecision(int paramInt, boolean paramBoolean);

  public abstract void exitDecision(int paramInt);

  public abstract void consumeToken(Token paramToken);

  public abstract void consumeHiddenToken(Token paramToken);

  public abstract void LT(int paramInt, Token paramToken);

  public abstract void mark(int paramInt);

  public abstract void rewind(int paramInt);

  public abstract void rewind();

  public abstract void beginBacktrack(int paramInt);

  public abstract void endBacktrack(int paramInt, boolean paramBoolean);

  public abstract void location(int paramInt1, int paramInt2);

  public abstract void recognitionException(RecognitionException paramRecognitionException);

  public abstract void beginResync();

  public abstract void endResync();

  public abstract void semanticPredicate(boolean paramBoolean, String paramString);

  public abstract void commence();

  public abstract void terminate();

  public abstract void consumeNode(Object paramObject);

  public abstract void LT(int paramInt, Object paramObject);

  public abstract void nilNode(Object paramObject);

  public abstract void errorNode(Object paramObject);

  public abstract void createNode(Object paramObject);

  public abstract void createNode(Object paramObject, Token paramToken);

  public abstract void becomeRoot(Object paramObject1, Object paramObject2);

  public abstract void addChild(Object paramObject1, Object paramObject2);

  public abstract void setTokenBoundaries(Object paramObject, int paramInt1, int paramInt2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.debug.DebugEventListener
 * JD-Core Version:    0.6.2
 */