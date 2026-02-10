package com.example.Catalog.Managment.Service;

import com.example.Catalog.Managment.Dto.BillitemDto;
import com.example.Catalog.Managment.Dto.Response.BillResponseDto;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class Pdfservice {

    public byte[] generatebillpdf(BillResponseDto bill) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A6, 20, 20, 20, 20);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.COURIER, 16, Font.BOLD);
            Font normal = new Font(Font.COURIER, 10);
            Font bold = new Font(Font.COURIER, 10, Font.BOLD);

            // ===== HEADER =====
            Paragraph store = new Paragraph("*** RECEIPT ***", titleFont);
            store.setAlignment(Element.ALIGN_CENTER);
            document.add(store);

            document.add(new Paragraph("--------------------------------", normal));

            document.add(new Paragraph("Order ID : " + bill.getOrderId(), normal));
            document.add(new Paragraph("Date     : " + bill.getOrderDate(), normal));
            document.add(new Paragraph("Customer : " + bill.getCustomerName(), normal));
            document.add(new Paragraph("--------------------------------", normal));

            // ===== ITEMS =====
            for (BillitemDto item : bill.getItems()) {
                document.add(new Paragraph(
                        item.getProductName() + " x" + item.getQuantity(),
                        normal));

                document.add(new Paragraph(
                        "   " + item.getPrice() + "  =  " + item.getTotal(),
                        normal));
            }

            document.add(new Paragraph("--------------------------------", normal));

            // ===== TOTALS =====
            document.add(new Paragraph(
                    "Subtotal : " + bill.getSubtotal(), normal));

            document.add(new Paragraph(
                    "Tax (18%): " + bill.getTax(), normal));

            document.add(new Paragraph("--------------------------------", normal));

            document.add(new Paragraph(
                    "TOTAL : " + bill.getGrandTotal(), bold));

            document.add(new Paragraph("--------------------------------", normal));

            Paragraph thanks = new Paragraph("Thank you for shopping!", bold);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate receipt PDF", e);
        }
    }
}
