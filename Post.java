import java.util.Date;

interface Parser {
    public static void parse() throws IOException {

    }
}

public class Post {
    protected String sourse;
    protected String pid;
    protected Date date;
    protected String link;
    protected String description;
    protected String imageUrl;
    protected Integer likes;
    protected Integer reposts;

    @Override
    public String toString() {
        return "Post{" +
                "sourse:'" + sourse + '\'' +
                ", pid:'" + pid + '\'' +
                ", date:" + date +
                ", link:'" + link + '\'' +
                ", description:'" + description + '\'' +
                ", imageUrl:'" + imageUrl + '\'' +
                ", likes:" + likes +
                ", reposts:" + reposts +
                '}';
    }

    Post(String sourse, String pid, Date date,String description, String link, String imageUrl, Integer likes, Integer reposts){
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
        this.link = link;
        this.pid = pid;
        this.likes = likes;
        this.reposts = reposts;
        this.sourse = sourse;
    }

}
