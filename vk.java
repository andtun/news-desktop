//Импортируем все, что нам потребуется
import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.*;

public class vk {

    public static void main(String[] args) throws IOException {
            // Список в котором мы будем хранить все данные
            List<Post> articleList = new ArrayList<>();

            // Get-запрос к VK
            Scanner in = new Scanner(System.in);
            System.out.print("Введите id группы ВК : ");
            String name = in.nextLine();
            String url = String.format("https://api.vk.com/method/wall.get?owner_id=-%s&access_token=02585fa1b7e0b4296c1ef207d1410e8030a041b99f0c38d44cc3f84f786bde10261e38565b8f374564164&v=V", name);
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            String html = doc.toString();
            int start = html.indexOf("{\"response\":[");
            int end = html.indexOf("}]}");
            html = html.substring(start, end + "}]}".length());
            JsonElement jelement = new JsonParser().parse(html);
            JsonObject  jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("response");

            for(int i = 1; i < 10; i++) {
                jobject = jarray.get(i).getAsJsonObject();
                String description = jobject.get("text").toString();

                String sourse = null;
                String pid = null;
                String link = null;


                String dates = jobject.get("date").toString();
                int date1 = Integer.parseInt(dates);

                int year = date1 / 10000;
                int month = (date1 % 10000) / 100;
                int day = date1 % 100;
                Date date = new GregorianCalendar(year, month, day).getTime();

                String repost = jobject.get("reposts").toString();
                int start1 = repost.indexOf("\"count\":");
                int end1 = repost.indexOf(",\"user_reposted\"");
                repost = repost.substring(start1 + "\"count\":".length(), end1);
                int reposts = Integer.parseInt(repost);
                jobject = jobject.getAsJsonObject("likes");
                String like = jobject.get("count").toString();
                int likes = Integer.parseInt(like);



                String imageUrl = "";

                articleList.add(new Post(sourse, pid, date, description, link, imageUrl, likes, reposts));
            }
        articleList.forEach(System.out::println);
    }
}
