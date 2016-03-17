package org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;

public abstract interface PDFTemplateBuilder
{
  public abstract void createAffineTransform(byte[] paramArrayOfByte);

  public abstract void createPage(PDVisibleSignDesigner paramPDVisibleSignDesigner);

  public abstract void createTemplate(PDPage paramPDPage)
    throws IOException;

  public abstract void createAcroForm(PDDocument paramPDDocument);

  public abstract void createSignatureField(PDAcroForm paramPDAcroForm)
    throws IOException;

  public abstract void createSignature(PDSignatureField paramPDSignatureField, PDPage paramPDPage, String paramString)
    throws IOException;

  public abstract void createAcroFormDictionary(PDAcroForm paramPDAcroForm, PDSignatureField paramPDSignatureField)
    throws IOException;

  public abstract void createSignatureRectangle(PDSignatureField paramPDSignatureField, PDVisibleSignDesigner paramPDVisibleSignDesigner)
    throws IOException;

  public abstract void createProcSetArray();

  public abstract void createSignatureImage(PDDocument paramPDDocument, InputStream paramInputStream)
    throws IOException;

  public abstract void createFormaterRectangle(byte[] paramArrayOfByte);

  public abstract void createHolderFormStream(PDDocument paramPDDocument);

  public abstract void createHolderFormResources();

  public abstract void createHolderForm(PDResources paramPDResources, PDStream paramPDStream, PDRectangle paramPDRectangle);

  public abstract void createAppearanceDictionary(PDXObjectForm paramPDXObjectForm, PDSignatureField paramPDSignatureField)
    throws IOException;

  public abstract void createInnerFormStream(PDDocument paramPDDocument);

  public abstract void createInnerFormResource();

  public abstract void createInnerForm(PDResources paramPDResources, PDStream paramPDStream, PDRectangle paramPDRectangle);

  public abstract void insertInnerFormToHolerResources(PDXObjectForm paramPDXObjectForm, PDResources paramPDResources);

  public abstract void createImageFormStream(PDDocument paramPDDocument);

  public abstract void createImageFormResources();

  public abstract void createImageForm(PDResources paramPDResources1, PDResources paramPDResources2, PDStream paramPDStream, PDRectangle paramPDRectangle, AffineTransform paramAffineTransform, PDJpeg paramPDJpeg)
    throws IOException;

  public abstract void injectProcSetArray(PDXObjectForm paramPDXObjectForm, PDPage paramPDPage, PDResources paramPDResources1, PDResources paramPDResources2, PDResources paramPDResources3, COSArray paramCOSArray);

  public abstract void injectAppearanceStreams(PDStream paramPDStream1, PDStream paramPDStream2, PDStream paramPDStream3, String paramString1, String paramString2, String paramString3, PDVisibleSignDesigner paramPDVisibleSignDesigner)
    throws IOException;

  public abstract void createVisualSignature(PDDocument paramPDDocument);

  public abstract void createWidgetDictionary(PDSignatureField paramPDSignatureField, PDResources paramPDResources)
    throws IOException;

  public abstract PDFTemplateStructure getStructure();

  public abstract void closeTemplate(PDDocument paramPDDocument)
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDFTemplateBuilder
 * JD-Core Version:    0.6.2
 */