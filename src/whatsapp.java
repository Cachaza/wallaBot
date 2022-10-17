import com.twilio.Twilio;
import com.twilio.rest.chat.v1.service.channel.Message;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;

import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

import static java.lang.ref.Cleaner.create;


public class whatsapp {
    public static final String ACCOUNT_SID = "***REMOVED***";
    public static final String AUTH_TOKEN = "[Redacted]";



        public void enviarActualizacion(String nombreProducto, String precioProducto, String urlProducto) throws Exception {
            String mensaje = "El producto " + nombreProducto + " ha cambiado de precio a " + precioProducto + ". Puedes verlo en " + urlProducto;
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new String("whatsapp:+14155238886"),
                    new String("***REMOVED***"),
                    mensaje).create();



            System.out.println(message.getSid());
        }

}
