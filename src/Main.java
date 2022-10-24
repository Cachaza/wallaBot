import com.google.gson.JsonObject;

import java.net.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
b
import java.io.FileReader;





public class Main {
    public static void main(String[] args) throws Exception {

        sendPost sendPost = new sendPost();
        playwright playwright = new playwright();

        String query = null;
        String password = null;
        String username = null;
        String databaseUrl = null;

        try{
            FileReader fr = new FileReader("config.txt");
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while((linea=br.readLine())!=null){
                String[] parts = linea.split("=");
                if(parts[0].equals("query")){
                    query = parts[1];
                }
                if(parts[0].equals("password")){
                    password = parts[1];
                }
                if(parts[0].equals("username")){
                    username = parts[1];
                }
                if(parts[0].equals("databaseUrl")){
                    databaseUrl = parts[1];
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        String url = "https://es.wallapop.com/app/search?keywords=";









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

        String urlFinal = url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud;
        //Document document = Jsoup.connect(url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud).get();
        System.out.println("Buscando en url: "+urlFinal);

        String respuesta = playwright.getWallapop(urlFinal, query, password, username, databaseUrl);
        System.out.println(respuesta);



        //System.out.println(respuesta);
        //System.out.println("Total elements: " + document);


    }


}