package com.unifi.fatture.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFCreator {
	private Company selectedCompany;
	private Client selectedClient;
	private Invoice selectedInvoice;

	private float topInsets = 88;
	private float margin = 60;

	static final PDFont helveticaBoldFont = PDType1Font.HELVETICA_BOLD;
	static final PDFont helveticaFont = PDType1Font.HELVETICA;
	private PDPageContentStream contentStream;

	public PDFCreator(Company company, Client client, Invoice invoice) throws IOException {
		this.selectedCompany = company;
		this.selectedClient = client;
		this.selectedInvoice = invoice;
		create();
	}

	private void create() throws IOException {
		final PDPage singlePage = new PDPage();
		final PDDocument document = new PDDocument();

		document.addPage(singlePage);
		contentStream = new PDPageContentStream(document, singlePage);
		this.companyName( singlePage);
		this.billTo( singlePage);
		this.invoiceNumber(singlePage);
		this.invoiceContainer( singlePage);
		this.invoiceDescription();
		this.invoiceTotal();
		this.footer( singlePage);
		contentStream.close(); // Stream must be closed before saving document.
		document.save("Invoice.pdf");
	}

	private PDPageContentStream companyName( PDPage sp) throws IOException {
		int fontSize = 18;
		String stringToPrint = selectedCompany.getName();
		stringToPrint = stringToPrint.toUpperCase();
		float textWidth = getTextWidth(fontSize, stringToPrint);
		contentStream.beginText();
		contentStream.setFont(helveticaBoldFont, fontSize);
		contentStream.newLineAtOffset(verticalCenter(sp, textWidth), sp.getMediaBox().getHeight() - topInsets);
		contentStream.showText(stringToPrint);
		contentStream.endText();

		return contentStream;
	}

	private PDPageContentStream billTo( PDPage sp) throws IOException {
		int fontSize = 10;
		String stringToPrint = "Bill To:";
		contentStream.setFont(helveticaBoldFont, fontSize);
		float y = 140;
		contentStream.beginText();
		contentStream.newLineAtOffset(verticalLeft(), sp.getMediaBox().getHeight() - y);
		contentStream.showText(stringToPrint);
		contentStream.endText();
		fontSize = 9;
		float leading = 1.5f * fontSize;

		stringToPrint = selectedClient.getName() + " - " + selectedClient.getFiscalCode() + "\n"
				+ selectedClient.getCity() + " - " + selectedClient.getZip() + " - " + selectedClient.getCityResidence()
				+ " - " + selectedClient.getCountry() + "\n" + "Tel: " + selectedClient.getPhone() + " - "
				+ selectedClient.getEmail();

		// new line when finds \n
		ArrayList<String> lines = splitLines(stringToPrint);
		contentStream.setFont(helveticaFont, fontSize);
		y = 140 + leading;
		for (String line : lines) {
			contentStream.beginText();
			contentStream.newLineAtOffset(verticalLeft(), sp.getMediaBox().getHeight() - y);
			contentStream.showText(line);
			contentStream.endText();
			y = y + leading;
		}
		return contentStream;
	}

	private PDPageContentStream invoiceNumber( PDPage sp) throws IOException {
		int fontSize = 10;
		String stringToPrint = "Invoice #: ";
		float textWidth = getTextWidth(fontSize, "invoice date: dd/MM/yyyy   ");
		float leading = 1.5f * fontSize;
		contentStream.beginText();
		contentStream.setFont(helveticaBoldFont, fontSize);
		contentStream.newLineAtOffset(verticalRight(sp, textWidth) - 2, sp.getMediaBox().getHeight() - 140);
		contentStream.showText(stringToPrint);

		contentStream.setFont(helveticaFont, fontSize);
		contentStream.newLineAtOffset(54, 0);
		// invoice number here
		contentStream.showText(String.valueOf(selectedCompany.getCreatedInvoices()));

		stringToPrint = "Invoice date: ";
		contentStream.setFont(helveticaBoldFont, fontSize);
		contentStream.newLineAtOffset(-(textWidth / 2) + 6, -leading);
		contentStream.showText(stringToPrint);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new java.util.Date();
		String dateString = dateFormat.format(date);

		stringToPrint = dateString;
		contentStream.setFont(helveticaFont, fontSize);
		contentStream.newLineAtOffset(getTextWidth(fontSize, "invoice date: ") + 8, 0);
		contentStream.showText(stringToPrint);

		contentStream.endText();
		return contentStream;
	}

	private PDPageContentStream invoiceContainer( PDPage sp) throws IOException {
		// container title
		int fontSize = 18;
		String stringToPrint = "INVOICE";
		float x = verticalLeft();
		contentStream.beginText();
		contentStream.setFont(helveticaBoldFont, fontSize);
		contentStream.setNonStrokingColor(new Color(0, 51, 102));
		contentStream.newLineAtOffset(x, 578);
		contentStream.showText(stringToPrint);
		contentStream.endText();

		// create a rectangle
		float rectWidth = (sp.getMediaBox().getWidth() - (margin * 2));
		x = verticalLeft();
		contentStream.addRect(x, 550, rectWidth, 20);
		contentStream.fill();
		contentStream.setNonStrokingColor(Color.BLACK);

		// create table header
		fontSize = 12;
		stringToPrint = " #    Description";
		x = verticalLeft();

		contentStream.beginText();
		contentStream.setFont(helveticaBoldFont, fontSize);
		contentStream.setNonStrokingColor(Color.white);
		contentStream.newLineAtOffset(x, 556);
		contentStream.showText(stringToPrint);

		stringToPrint = " Price (ex VAT)";
		contentStream.newLineAtOffset(320, 0);
		contentStream.showText(stringToPrint);

		stringToPrint = " Total";
		contentStream.newLineAtOffset(132, 0);
		contentStream.showText(stringToPrint);

		contentStream.setNonStrokingColor(Color.black);
		contentStream.endText();
		return contentStream;
	}

	private PDPageContentStream invoiceDescription() throws IOException {
		// create table header
		int fontSize = 11;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		String stringToPrint = " 1      " + selectedInvoice.getName() + " (" + selectedInvoice.getDescription() + ")";
		float x = verticalLeft();
		contentStream.beginText();
		contentStream.setFont(helveticaFont, fontSize);
		contentStream.newLineAtOffset(x, 530);
		contentStream.showText(stringToPrint);

		stringToPrint = "  €  " + df.format(Float.parseFloat(getPriceExcVAT(selectedInvoice.getPrice())));
		contentStream.newLineAtOffset(320, 0);
		contentStream.showText(stringToPrint);

		stringToPrint = "  €  " + df.format(Float.parseFloat(selectedInvoice.getPrice()));
		contentStream.newLineAtOffset(126, 0);
		contentStream.showText(stringToPrint);

		contentStream.endText();
		return contentStream;
	}

	private PDPageContentStream invoiceTotal() throws IOException {
		// create table header
		int fontSize = 11;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		String stringToPrint = "Subtotal Ex. VAT:          €  "
				+ df.format(Float.parseFloat(getPriceExcVAT(selectedInvoice.getPrice())));

		contentStream.beginText();
		contentStream.setFont(helveticaFont, fontSize);
		contentStream.newLineAtOffset(390, 440);
		contentStream.showText(stringToPrint);

		double vat = Float.parseFloat(selectedInvoice.getPrice())
				- Float.parseFloat(getPriceExcVAT(selectedInvoice.getPrice()));

		stringToPrint = "Subtotal VAT:                €  " + df.format(vat);
		contentStream.newLineAtOffset(0, -20);
		contentStream.showText(stringToPrint);

		stringToPrint = "Total due:                      €  " + df.format(Float.parseFloat(selectedInvoice.getPrice()));
		contentStream.newLineAtOffset(0, -20);
		contentStream.showText(stringToPrint);

		contentStream.endText();
		return contentStream;
	}

	private PDPageContentStream footer( PDPage sp) throws IOException {
		int fontSize = 9;
		float leading = 1.5f * fontSize;

		String stringToPrint = selectedCompany.getName() + " - " + selectedCompany.getVatCode() + "\n"
				+ selectedCompany.getAddress() + " - " + selectedCompany.getZipCode() + " - "
				+ selectedCompany.getCity() + " - " + selectedCompany.getCountry() + "\n" + "Tel: "
				+ selectedCompany.getPhone() + " - " + selectedCompany.getEmail();
		float textWidth;

		// new line when finds \n
		ArrayList<String> lines = splitLines(stringToPrint);

		contentStream.setFont(helveticaFont, fontSize);
		float y = topInsets;
		for (String line : lines) {
			contentStream.beginText();
			textWidth = getTextWidth(fontSize, line);
			contentStream.newLineAtOffset(verticalCenter(sp, textWidth), y);
			contentStream.showText(line);
			contentStream.endText();
			y = y - leading;
		}
		return contentStream;
	}

	private ArrayList<String> splitLines(String stringToSplit) {
		ArrayList<String> lines = new ArrayList<>();

		for (String text : stringToSplit.split("\n")) {
			int lastSpace = -1;
			while (text.length() > 0) {
				int spaceIndex = text.indexOf(' ', lastSpace + 1);
				if (spaceIndex < 0)
					spaceIndex = text.length();
				if (spaceIndex == text.length()) {
					lines.add(text);
					text = "";
				} else {
					lastSpace = spaceIndex;
				}
			}
		}
		return lines;
	}

	private float verticalCenter(PDPage sp, float textWidth) {
		return (sp.getMediaBox().getWidth() / 2 - textWidth / 2);
	}

	private float verticalLeft() {
		return (margin);
	}

	private float verticalRight(PDPage sp, float textWidth) {
		return (sp.getMediaBox().getWidth() - margin - textWidth);
	}

	private float getTextWidth(int fontSize, String s) {
		Font titleFont = new Font("Helvetica", Font.PLAIN, fontSize);
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		return (float) titleFont.getStringBounds(s, frc).getWidth();
	}

	private String getPriceExcVAT(String price) {
		float p = Float.parseFloat(price);
		p = (float) (p / 1.22);
		return Float.toString(p);
	}
}