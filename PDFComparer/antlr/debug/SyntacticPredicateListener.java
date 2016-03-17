package antlr.debug;

public abstract interface SyntacticPredicateListener extends ListenerBase
{
  public abstract void syntacticPredicateFailed(SyntacticPredicateEvent paramSyntacticPredicateEvent);

  public abstract void syntacticPredicateStarted(SyntacticPredicateEvent paramSyntacticPredicateEvent);

  public abstract void syntacticPredicateSucceeded(SyntacticPredicateEvent paramSyntacticPredicateEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.SyntacticPredicateListener
 * JD-Core Version:    0.6.2
 */