import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws Exception {
        sendPost sendPost = new sendPost();
        playwright playwright = new playwright();

        String url = "https://es.wallapop.com/app/search?keywords=";
        String query = args[0];


        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        System.out.println("Esta es tu ip: "+ip);
        //JsonObject respuestaJSON = sendPost.sendPost(ip);

        String latitud = "40.41406";//respuestaJSON.get("latt").getAsString();
        String longitud = "-3.70158";//respuestaJSON.get("longt").getAsString();
        System.out.println("Esta es tu latitud: "+latitud);
        System.out.println("Esta es tu longitud: "+longitud);

        System.out.println("Buscando: " + query);

        String urlFinal = url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud;
        //Document document = Jsoup.connect(url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud).get();
        System.out.println("Buscando en url: "+urlFinal);

        String respuesta = playwright.getWallapop(urlFinal, query);
        System.out.println(respuesta);



        //System.out.println(respuesta);
        //System.out.println("Total elements: " + document);


    }

}