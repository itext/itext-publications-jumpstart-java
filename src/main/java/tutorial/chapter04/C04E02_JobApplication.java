/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
package tutorial.chapter04;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfChoiceFormField;
import com.itextpdf.forms.fields.PdfFormCreator;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.forms.fields.TextFormFieldBuilder;
import com.itextpdf.forms.fields.CheckBoxFormFieldBuilder;
import com.itextpdf.forms.fields.ChoiceFormFieldBuilder;
import com.itextpdf.forms.fields.PushButtonFormFieldBuilder;
import com.itextpdf.forms.fields.RadioFormFieldBuilder;
import com.itextpdf.forms.fields.properties.CheckBoxType;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.IOException;

/**
 * Simple widget annotation example.
 */
public class C04E02_JobApplication {

    public static final String DEST = "results/chapter04/job_application.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E02_JobApplication().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PageSize ps = PageSize.A4;
        pdf.setDefaultPageSize(ps);

        // Initialize document
        Document document = new Document(pdf);

        C04E02_JobApplication.addAcroForm(document);

        //Close document
        document.close();

    }

    public static PdfAcroForm addAcroForm(Document doc) {

        Paragraph title = new Paragraph("Application for employment")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        doc.add(title);
        doc.add(new Paragraph("Full name:").setFontSize(12));
        doc.add(new Paragraph("Native language:      English         French       German        Russian        Spanish").setFontSize(12));
        doc.add(new Paragraph("Experience in:       cooking        driving           software development").setFontSize(12));
        doc.add(new Paragraph("Preferred working shift:").setFontSize(12));
        doc.add(new Paragraph("Additional information:").setFontSize(12));

        //Add acroform
        PdfAcroForm form = PdfFormCreator.getAcroForm(doc.getPdfDocument(), true);

        //Create text field
        PdfTextFormField nameField = new TextFormFieldBuilder(doc.getPdfDocument(), "name")
                .setWidgetRectangle(new Rectangle(99, 753, 425, 15)).createText();
        nameField.setValue("");
        form.addField(nameField);

        //Create radio buttons
        RadioFormFieldBuilder builder = new RadioFormFieldBuilder(doc.getPdfDocument(), "language");
        PdfButtonFormField group = builder.createRadioGroup();
        group.setValue("", true);
        group.addKid(builder.createRadioButton("English", new Rectangle(130, 728, 15, 15)));
        group.addKid(builder.createRadioButton("French", new Rectangle(200, 728, 15, 15)));
        group.addKid(builder.createRadioButton("German", new Rectangle(260, 728, 15, 15)));
        group.addKid(builder.createRadioButton("Russian", new Rectangle(330, 728, 15, 15)));
        group.addKid(builder.createRadioButton("Spanish", new Rectangle(400, 728, 15, 15)));
        form.addField(group);

        //Create checkboxes
        for (int i = 0; i < 3; i++) {
            PdfButtonFormField checkField = new CheckBoxFormFieldBuilder(doc.getPdfDocument(), "experience".concat(String.valueOf(i + 1)))
                    .setWidgetRectangle(new Rectangle(119 + i * 69, 701, 15, 15))
                    .setCheckType(CheckBoxType.CHECK).createCheckBox();
            checkField.setValue("Off", true);
            form.addField(checkField);
        }

        //Create combobox
        String[] options = {"Any", "6.30 am - 2.30 pm", "1.30 pm - 9.30 pm"};
        PdfChoiceFormField choiceField = new ChoiceFormFieldBuilder(doc.getPdfDocument(), "shift")
                .setWidgetRectangle(new Rectangle(163, 676, 115, 15))
                .setOptions(options).createComboBox();
        choiceField.setValue("Any", true);
        form.addField(choiceField);

        //Create multiline text field
        PdfTextFormField infoField = new TextFormFieldBuilder(doc.getPdfDocument(), "info")
                .setWidgetRectangle(new Rectangle(158, 625, 366, 40)).createMultilineText();
        infoField.setValue("");
        form.addField(infoField);

        //Create push button field
        PdfButtonFormField button = new PushButtonFormFieldBuilder(doc.getPdfDocument(), "reset").setCaption("RESET")
                .setWidgetRectangle(new Rectangle(479, 594, 45, 15)).createPushButton();
        button.getFirstFormAnnotation().setAction(PdfAction.createResetForm(new String[] {"name", "language", "experience1", "experience2", "experience3", "shift", "info"}, 0));
        form.addField(button);

        return form;

    }
}
