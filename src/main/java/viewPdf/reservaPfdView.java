package viewPdf;


import com.example.booking.models.Hotel;
import com.example.booking.models.Pago;
import com.example.booking.models.Reserva;
import com.example.booking.models.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("detallesReserva")
public class reservaPfdView extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {


        Reserva reserva = (Reserva) model.get("reservaUsuario");
        Usuario usuario = (Usuario) model.get("nombreUsuarioDetallesReserva");


        PdfPTable tabla = new PdfPTable(1);
        tabla.addCell("Datos del cliente");
        tabla.addCell(usuario.getNombre() + " " + usuario.getApellidos());
        tabla.addCell(usuario.getEmail());
        tabla.addCell(usuario.getTelefono());

        PdfPTable tabla2= new PdfPTable(1);
        tabla.addCell("Datos del Hotel");
        tabla.addCell(reserva.getHabitacion().getHotel().getNombre());
        tabla.addCell(reserva.getHabitacion().getHotel().getCiudad());
        tabla.addCell(reserva.getHabitacion().getHotel().getTelefono());

        PdfPTable tabla3= new PdfPTable(1);
        tabla.addCell("Datos de la Reserva");
        tabla.addCell(String.valueOf(reserva.getFechaInicio()));
        tabla.addCell(String.valueOf(reserva.getFechaFin()));
        tabla.addCell(String.valueOf(reserva.getPrecio_total()));

        document.add(tabla);
        document.add(tabla2);
        document.add(tabla3);







    }
}
