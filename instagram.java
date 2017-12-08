//Импортируем все, что нам потребуется
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class instagram {

    public static void main(String[] args) throws IOException{
        // Список в котором мы будем хранить все данные
        List<Article> articleList = new ArrayList<>();

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
            String comment = "empty";

            try {
                userObject.get("caption");
                 comment = userObject.get("caption").toString();
            }catch (NullPointerException e){
                 comment = "empty";
            }

            finally {
                userObject.get("display_src");
                String img_url = userObject.get("display_src").toString();

                userObject.get("likes");
                String likes = userObject.get("likes").toString();

                userObject.get("date");
                String date = userObject.get("date").toString();

                articleList.add(new Article(img_url, comment, likes, date));
            }
        }

        // Печатаем по одному элементы списка(то есть объекты класса)
        articleList.forEach(System.out::println);
    }
}

// Класс, в котором мы храним информацию про посты
class Article{
    private String img_url;
    private String comment;
    private String likes;
    private String date;

    public Article(String img_url, String comment, String likes, String date) {
        this.img_url = img_url;
        this.comment = comment;
        this.likes = likes;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "Article{" +
                "img_url='" + img_url + '\'' +
                ", comment='" + comment + '\'' +
                ", likes='" + likes + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
