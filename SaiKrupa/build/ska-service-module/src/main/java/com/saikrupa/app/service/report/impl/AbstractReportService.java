package com.saikrupa.app.service.report.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.saikrupa.app.service.report.ReportService;
import com.saikrupa.app.service.report.common.ReportPageHeader;

public abstract class AbstractReportService implements ReportService {

	private void addHeader(PdfWriter writer) {
		ReportPageHeader header = new ReportPageHeader();
		writer.setPageEvent(header);
	}

	public Document createBlankDocument(final String reportFilePathName) {
		Document document = new Document(PageSize.A4);

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(reportFilePathName));
			addHeader(writer);
			document.open();
			addMetaData(document);
			addTitlePage(document);
			createPageTableContent(document);
			addEmptyLine(new Paragraph(), 1);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}


	public abstract void createPageTableContent(Document document) throws DocumentException;

	public abstract void addMetaData(Document document);

	public void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private void addTitlePage(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		preface.add(new Paragraph(getPageTitle(), catFont));
		addEmptyLine(preface, 1);
		document.add(preface);
	}

	public abstract String getPageTitle();

	
}
