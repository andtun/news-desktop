//Импортируем все, что нам потребуется
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.*;

public class instagram {

    //Здесь весь кипиш
    public static void main(String[] args) throws IOException{

        // Список в котором мы будем хранить все данные
        List<Post> articleList = new ArrayList<>();

        // Get-запрос к Instagram
        Scanner in = new Scanner(System.in);
        System.out.print("Введите название профиля Instagram: ");
        String name = in.nextLine();
        String url = String.format("https://www.instagram.com/%s", name);
        Document doc = Jsoup.connect(url).get();
        String htmlString = doc.toString();

        // Делаем срез, по которому извлекаем JSON
        int start = htmlString.indexOf("{\"activity_counts\": null, \"config\": {\"csrf_token\":");
        int end = htmlString.indexOf("\"probably_has_app\": false, \"show_app_install\": true}");
        htmlString = htmlString.substring(start, end + "\"probably_has_app\": false, \"show_app_install\": true}".length());

        // Начинаем парсить
        JsonElement jelement = new JsonParser().parse(htmlString);
        JsonObject  jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("entry_data");
        JsonArray jarray = jobject.getAsJsonArray("ProfilePage");
        jobject = jarray.get(0).getAsJsonObject();
        jobject = jobject.getAsJsonObject("user");
        jobject = jobject.getAsJsonObject("media");
        JsonArray jarray2 = jobject.getAsJsonArray("nodes");

        // Проходимся по всему JSON и добавляем в созданный ранее список как объекты класса
        for (JsonElement user : jarray2){
            JsonObject userObject = user.getAsJsonObject();
            String description = "empty";

            try {
                userObject.get("caption");
                 description = userObject.get("caption").toString();
            }catch (NullPointerException e){
                 description = "empty";
            }

            finally {
                String sourse = null;
                String pid = null;
                userObject.get("code");
                String links = userObject.get("code").toString();
                String link = links.format("https://www.instagram.com/p/%s", links.substring(1, links.length() - 1));


                int reposts = 0;
                userObject.get("display_src");
                String imageUrl = userObject.get("display_src").toString();

                userObject.get("likes");
                String like = userObject.get("likes").toString();
                int start1 = like.indexOf("\"count\":");
                int end1 = like.indexOf("}");
                like = like.substring(start1 + "\"count\":".length(), end1);


                int likes = Integer.parseInt(like);

                userObject.get("date");
                String dates = userObject.get("date").toString();
                int date1 = Integer.parseInt(dates);

                int year = date1 / 10000;
                int month = (date1 % 10000) / 100;
                int day = date1 % 100;
                Date date = new GregorianCalendar(year, month, day).getTime();

                articleList.add(new Post(sourse,  pid, date, description, link, imageUrl, likes, reposts));
            }
        }

        // Печатаем по одному элементы списка(то есть объекты класса)
        articleList.forEach(System.out::println);
//        System.out.print(articleList);
    }
}
