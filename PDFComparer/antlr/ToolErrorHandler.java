package antlr;

abstract interface ToolErrorHandler
{
  public abstract void warnAltAmbiguity(Grammar paramGrammar, AlternativeBlock paramAlternativeBlock, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2, int paramInt3);

  public abstract void warnAltExitAmbiguity(Grammar paramGrammar, BlockWithImpliedExitPath paramBlockWithImpliedExitPath, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ToolErrorHandler
 * JD-Core Version:    0.6.2
 */