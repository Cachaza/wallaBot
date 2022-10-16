import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.microsoft.playwright.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import com.google.gson.JsonObject;


import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class playwright {
    public String getWallapop(String url, String query) throws Exception {
        try (Playwright playwright = Playwright.create()) {
            database database = new database();
            System.out.println(query);

            database.crearTabla(query);


            final BrowserType chromium = playwright.chromium();
            final Browser browser = chromium.launch(new BrowserType.LaunchOptions().setHeadless(false));
            final Page page = browser.newPage();
            page.navigate(url);

            JsonArray arrayFinal = new JsonArray();
            AtomicReference<JsonObject> jsonFinal = new AtomicReference<>(new JsonObject());

            Locator links = (Locator) page.locator("a.ItemCardList__item.ng-star-inserted");



            synchronized (page) {
                try {
                    page.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (page.isVisible("text=Confirm suggested preferences")) {
                page.click("text=Confirm suggested preferences");
            }
            if (page.isVisible("text=CVer más productos")) {
                page.click("text=Ver más productos");
            }

            if (page.isVisible("text=Confirm suggested preferences")) {
                page.click("text=Confirm suggested preferences");
            }
            System.out.println("Mas productos");
            synchronized (page) {
                try {
                    page.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Pruductos totales: " + links.count());
            if (links.count() == 0) {
                return "No hay resultados";
            }
            for (int i = 0; i <= links.count(); i++) {

                try{
                    links.nth(i).click();
                    synchronized (page) {
                        try {
                            page.wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("Error en el wait "+ e.getMessage());
                        }
                    }

                    browser.contexts().forEach(context -> {
                        context.pages().forEach(page1 -> {
                            if (page1.url().contains("item")) {
                                System.out.println("Page "+": " + page1.url());
                                synchronized (page) {
                                    try {
                                        page.wait(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                String html = Jsoup.parse(page1.content()).getElementsByClass("container-detail-section with-header").html();
                                Elements titulo = Jsoup.parse(html).getElementsByClass("js__card-product-detail--title card-product-detail-title  card-product-detail-title--with-extra-info ");
                                Elements precio = Jsoup.parse(html).getElementsByClass("card-product-price-info");
                                Elements descripcion = Jsoup.parse(html).getElementsByClass("js__card-product-detail--description card-product-detail-description");
                                Elements fecha = Jsoup.parse(html).getElementsByClass("card-product-detail-user-stats-published");
                                Elements estado = Jsoup.parse(html).getElementsByClass("ExtraInfo__text");
                                String urlElemento = page1.url();
                                String imagen = Jsoup.parse(html).getElementsByClass("card-slider-main ").get(0).getElementsByTag("img").attr("src");


                                Map<String, String> resultado = Map.of(
                                        "titulo", titulo.text(),
                                        "precio", precio.text(),
                                        "descripcion", descripcion.text(),
                                        "fecha", fecha.text(),
                                        "estado", estado.text(),
                                        "url", urlElemento,
                                        "imagen", imagen
                                );
                                try {
                                    database.añadir(query, resultado);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                //Gson gsonObj = new Gson();
                                //String jsonStr = gsonObj.toJson(resultado);
                                //System.out.println(jsonStr);



                                //System.out.println("HTML: " + html);
                                page1.close();

                            }
                        });
                    });

                } catch (Exception e){
                    System.out.println("Error al hacer click en el producto");
                    System.out.println(e.getMessage());
                }


            }

            String respuesta = arrayFinal.toString();

            browser.close();
            return "Todo okay";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
