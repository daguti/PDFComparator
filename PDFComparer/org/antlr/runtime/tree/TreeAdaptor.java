package org.antlr.runtime.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public abstract interface TreeAdaptor
{
  public abstract Object create(Token paramToken);

  public abstract Object dupNode(Object paramObject);

  public abstract Object dupTree(Object paramObject);

  public abstract Object nil();

  public abstract Object errorNode(TokenStream paramTokenStream, Token paramToken1, Token paramToken2, RecognitionException paramRecognitionException);

  public abstract boolean isNil(Object paramObject);

  public abstract void addChild(Object paramObject1, Object paramObject2);

  public abstract Object becomeRoot(Object paramObject1, Object paramObject2);

  public abstract Object rulePostProcessing(Object paramObject);

  public abstract int getUniqueID(Object paramObject);

  public abstract Object becomeRoot(Token paramToken, Object paramObject);

  public abstract Object create(int paramInt, Token paramToken);

  public abstract Object create(int paramInt, Token paramToken, String paramString);

  public abstract Object create(int paramInt, String paramString);

  public abstract int getType(Object paramObject);

  public abstract void setType(Object paramObject, int paramInt);

  public abstract String getText(Object paramObject);

  public abstract void setText(Object paramObject, String paramString);

  public abstract Token getToken(Object paramObject);

  public abstract void setTokenBoundaries(Object paramObject, Token paramToken1, Token paramToken2);

  public abstract int getTokenStartIndex(Object paramObject);

  public abstract int getTokenStopIndex(Object paramObject);

  public abstract Object getChild(Object paramObject, int paramInt);

  public abstract void setChild(Object paramObject1, int paramInt, Object paramObject2);

  public abstract Object deleteChild(Object paramObject, int paramInt);

  public abstract int getChildCount(Object paramObject);

  public abstract Object getParent(Object paramObject);

  public abstract void setParent(Object paramObject1, Object paramObject2);

  public abstract int getChildIndex(Object paramObject);

  public abstract void setChildIndex(Object paramObject, int paramInt);

  public abstract void replaceChildren(Object paramObject1, int paramInt1, int paramInt2, Object paramObject2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeAdaptor
 * JD-Core Version:    0.6.2
 */