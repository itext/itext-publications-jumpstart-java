/*
 * This example is part of the iText 7 tutorial.
 */
package tutorial.chapter04;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Simple link annotation example.
 */
public class C04E01_02_LinkAnnotation {

    public static final String DEST = "results/chapter04/link_annotation.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E01_02_LinkAnnotation().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF writer
        OutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        //Initialize document
        Document document = new Document(pdf);

        //Create link annotation
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(new Rectangle(0, 0))
                .setAction(PdfAction.createURI("http://itextpdf.com/"));
        Link link = new Link("here", annotation);
        Paragraph p = new Paragraph("The example of link annotation. Click ")
                .add(link.setUnderline())
                .add(" to learn more...");
        document.add(p);

        //Close document
        document.close();

    }
}