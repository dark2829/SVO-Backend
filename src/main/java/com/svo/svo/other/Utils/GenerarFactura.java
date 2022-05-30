package com.svo.svo.other.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.svo.svo.model.TcarritoVO;
import com.svo.svo.model.TcomprasVO;
import com.svo.svo.model.TpedidosVO;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GenerarFactura {
    private TpedidosVO pedido;

    public GenerarFactura(TpedidosVO pedido) {
        this.pedido = pedido;
    }

    public PdfPCell createCell(String value, Font font, int align_v, int align_h, int colspan, int rowspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(align_v);
        cell.setHorizontalAlignment(align_h);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    public Paragraph createParagraph(String value, Font font, int align) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(value, font));
        paragraph.setAlignment(align);
        return paragraph;
    }


    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            //table.setTotalWidth(maxWidth);
            //table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setSpacingBefore(10);
            table.setWidthPercentage(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    private void WriteTable(PdfPTable table) {
        Font fontText = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        if (pedido != null) {
            TcomprasVO compraPedido = pedido.getIdCompra();

            for (TcarritoVO productosCarrito : compraPedido.getCarrito()) {
                table.addCell(createCell(productosCarrito.getIdProducto().getCodigo_prod(), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
                table.addCell(createCell(productosCarrito.getIdProducto().getNombre(), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, 1));
                table.addCell(createCell(String.valueOf(productosCarrito.getCantidad()), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
                table.addCell(createCell(String.valueOf(productosCarrito.getIdProducto().getPrecio_venta()), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
                table.addCell(createCell(String.valueOf(productosCarrito.getIdProducto().getPrecio_descuento()), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
                table.addCell(createCell(String.valueOf(productosCarrito.getPrecio_total()), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));

            }


        }

    }

    public void Export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        com.lowagie.text.Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD);
        com.lowagie.text.Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font2.setStyle(com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font fontText = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        Paragraph title = new Paragraph("FACTURA\n", font1);
        title.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(title);

        URL url = new URL("https://scontent.fpbc4-1.fna.fbcdn.net/v/t39.30808-6/285026850_5076156955834682_7718996971497901747_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeG44Owj55Tm6buy86xQPv7YOSmIF91XsWM5KYgX3VexY653WqpLR_TNRTYPJhV4rsiq74Q_d3Qt22sy0-T2LLgS&_nc_ohc=PDLEu1XwpLIAX95xuWh&_nc_ht=scontent.fpbc4-1.fna&oh=00_AT89iF3EiOQrSLMnkLILNd310qPEa2ECFoUz1_5vJ4AKwQ&oe=62981BA9");
        com.lowagie.text.Image jpg = com.lowagie.text.Image.getInstance(url);
        jpg.scalePercent((float) 20);
        jpg.setAlignment(Image.ALIGN_RIGHT);
        document.add(jpg);


        Paragraph nCompra = new Paragraph("Codigo de compra:", font2);
        nCompra.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(nCompra);
        Paragraph nCompraText = new Paragraph(pedido.getIdCompra().getCodigo_compra(), fontText);
        nCompraText.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(nCompraText);

        document.add(createParagraph("Número: XD201602000003", fontText, Element.ALIGN_RIGHT));

        float[] widths = {15f, 15f, 18f, 18f, 11f, 10f, 11f, 12f};
        PdfPTable table = createTable(widths);

        table.addCell(createCell("Información del cliente", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 2));

        table.addCell(createCell("Nombre del cliente", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, 1));
        table.addCell(createCell(pedido.getIdCompra().getIdUsuario().getIdPersona().getNombre()
                + " " + pedido.getIdCompra().getIdUsuario().getIdPersona().getApellido_paterno() + " " + pedido.getIdCompra().getIdUsuario().getIdPersona().getApellido_materno(), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 6, 1));

        table.addCell(createCell("Dirección de contacto", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, 1));
        table.addCell(createCell(pedido.getIdCompra().getDireccion(), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 6, 1));


        table.addCell(createCell("Lista de productos", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 5));

        table.addCell(createCell("Código de producto", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 1));
        table.addCell(createCell("Descripcion del producto", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 2, 1));
        table.addCell(createCell("Cantidad", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 1));
        table.addCell(createCell("Precio unitario", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 1));
        table.addCell(createCell("Precio descuento", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 1));
        table.addCell(createCell("Subtotal", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 1));

        WriteTable(table);

        table.addCell(createCell("", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
        table.addCell(createCell("", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, 1));
        table.addCell(createCell("", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
        table.addCell(createCell("", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
        table.addCell(createCell("Total", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));
        table.addCell(createCell(String.valueOf(pedido.getIdCompra().getPago_total()), fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, 1));


        table.addCell(createCell("Confirmación del cliente", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 1, 3));

        table.addCell(createCell("Nota: He entendido completamente la función y el propósito del producto pedido, y lo compré voluntariamente de acuerdo con las necesidades reales", fontText, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 8, 1));

        document.add(table);

        document.close();
    }
}
