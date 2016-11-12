package com.saikrupa.app.service.report.common;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportPageHeader extends PdfPageEventHelper {

	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		Phrase header = new Phrase("Saikrupa Associates", new Font(Font.FontFamily.COURIER, 16, Font.ITALIC));
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
				(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
	}

	public ReportPageHeader() {
		// TODO Auto-generated constructor stub
	}

}
