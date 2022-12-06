package viewPdf;

import com.example.booking.models.Reserva;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class reservaExporterPdf {

    private Reserva reserva;

    private void escribirCabeceraHotel(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.RED);
        celda.setPadding(5);



        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Datos Hotel",fuente));
        tabla.addCell(celda);

    }
    private void escribirCabeceraCliente(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.RED);
        celda.setPadding(5);



        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Datos Cliente",fuente));
        tabla.addCell(celda);

    }
    private void escribirCabeceraReserva(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.RED);
        celda.setPadding(5);



        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Datos de la Reserva",fuente));
        tabla.addCell(celda);

    }

    private void escribirTablaReserva(PdfPTable tabla){
        tabla.addCell(String.valueOf("Fecha de inicio:" + " " + reserva.getFechaInicio()));
        tabla.addCell(String.valueOf("Fecha de final:" + " " + reserva.getFechaFin()));
        tabla.addCell(String.valueOf("Capacidad de la habitación:" + " " + reserva.getHabitacion().getCapacidad()));
        tabla.addCell(String.valueOf("Total a pagar:" + " " + reserva.getPrecio_total() + " " + "€"));


    }

    private void escribirTablaCliente(PdfPTable tabla){
        tabla.addCell(String.valueOf("Nombre del Cliente:" + " " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellidos()));
        tabla.addCell(String.valueOf("Dni:" + " " + reserva.getUsuario().getDni()));
        tabla.addCell(String.valueOf("Teléfono del cliente:" + " " + reserva.getUsuario().getTelefono()));
        tabla.addCell(String.valueOf("Email de contacto:" + " " + reserva.getUsuario().getEmail()));

    }



    private void escribirTablaHotel(PdfPTable tabla){
        tabla.addCell(String.valueOf("Nombre del hotel:" + " " + reserva.getHabitacion().getHotel().getNombre()));
        tabla.addCell(String.valueOf("Ciudad donde se ubica:" + " " + reserva.getHabitacion().getHotel().getCiudad()));
        tabla.addCell(String.valueOf("Cif del hotel:" + " " + reserva.getHabitacion().getHotel().getCif()));
        tabla.addCell(String.valueOf("Teléfono de contacto:" + " " + reserva.getHabitacion().getHotel().getTelefono()));

    }

    private void escribirTabla(PdfPTable tabla){
        tabla.addCell(String.valueOf(reserva.getUsuario().getNombre()));
        tabla.addCell(String.valueOf(reserva.getHabitacion().getHotel().getNombre()));
        tabla.addCell(String.valueOf(reserva.getPrecio_total()));
        tabla.addCell(String.valueOf(reserva.getFechaInicio()));
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento,response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.RED);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Factura de la reserva", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{3f});
        tabla.setWidthPercentage(110);
        escribirCabeceraHotel(tabla);
        escribirTablaHotel(tabla);
        escribirCabeceraCliente(tabla);
        escribirTablaCliente(tabla);
        escribirCabeceraReserva(tabla);
        escribirTablaReserva(tabla);


        documento.add(tabla);
        documento.close();

    }


}
