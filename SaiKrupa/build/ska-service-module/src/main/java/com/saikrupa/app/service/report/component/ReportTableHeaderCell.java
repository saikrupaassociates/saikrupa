package com.saikrupa.app.service.report.component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class ReportTableHeaderCell extends PdfPCell {

	public ReportTableHeaderCell(Phrase phrase) {
		Paragraph p = new Paragraph(phrase.getContent(), FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK));
		setBackgroundColor(new BaseColor(222, 222, 222));
		p.setAlignment(Element.ALIGN_CENTER);
		setVerticalAlignment(Element.ALIGN_CENTER);
		setPhrase(p);
	}
	
	public ReportTableHeaderCell(String headerText) {
		Paragraph p = new Paragraph(headerText, FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK));
		setBackgroundColor(new BaseColor(222, 222, 222));
		setVerticalAlignment(Element.ALIGN_CENTER);
		p.setAlignment(Element.ALIGN_CENTER);
		setPhrase(p);
	}

	public ReportTableHeaderCell() {
		// TODO Auto-generated constructor stub
	}

}
