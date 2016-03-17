package com.itextpdf.text.pdf.security;

import org.bouncycastle.cms.Recipient;
import org.bouncycastle.cms.RecipientId;

public abstract interface ExternalDecryptionProcess
{
  public abstract RecipientId getCmsRecipientId();

  public abstract Recipient getCmsRecipient();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ExternalDecryptionProcess
 * JD-Core Version:    0.6.2
 */