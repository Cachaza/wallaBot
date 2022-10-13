import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.*;
import java.io.*;

import com.google.gson.Gson;


import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();



    public static void main(String[] args) throws Exception {
        Main obj = new Main();
        String url = "https://es.wallapop.com/app/search?keywords=";
        String query = "mx keys";

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        System.out.println("Esta es tu ip: "+ip);
        //JsonObject respuestaJSON = obj.sendPost(ip);

        String latitud = "40.41406";//respuestaJSON.get("latt").getAsString();
        String longitud = "-3.70158";//respuestaJSON.get("longt").getAsString();
        System.out.println("Esta es tu latitud: "+latitud);
        System.out.println("Esta es tu longitud: "+longitud);




        System.out.println("Buscando: " + query);
        Document document = Jsoup.connect(url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud).get();
        System.out.println("Buscando en url: "+url + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&longitude="+longitud+"&latitude="+latitud);
        Elements allElements = document.getElementsByTag("a");
        System.out.println("Total elements: " + document);


    }

    private JsonObject sendPost(String ip) throws Exception {

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("locate", ip);
        data.put("geoit", "JSON");
        data.put("ts", System.currentTimeMillis());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("https://geocode.xyz"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code

        JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Body: " + response.body());
        return jsonObject;

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}