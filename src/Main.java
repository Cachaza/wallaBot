import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws Exception {

        String url = "https://es.wallapop.com/app/search?keywords=";
        String query = "mx keys";
        sendPost sendPost = new sendPost();
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        System.out.println("Esta es tu ip: "+ip);
        JsonObject respuestaJSON = sendPost.sendPost(ip);

        String latitud = respuestaJSON.get("latt").getAsString();
        String longitud = respuestaJSON.get("longt").getAsString();
        System.out.println("Esta es tu latitud: "+latitud);
        System.out.println("Esta es tu longitud: "+longitud);

        System.out.println("Buscando: " + query);
        Document document = Jsoup.connect(url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud).get();
        System.out.println("Buscando en url: "+url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud);
        System.out.println("Total elements: " + document);


    }

}