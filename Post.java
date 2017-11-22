import java.util.Date;

class Post {
    protected String source;
    protected String pid;
    protected Date date;
    protected String link;
    protected String description;
    protected String imageUrl;
    protected Integer rate;


    Post (String source, String pid, Date date, String link, String description, String imageUrl, Integer rate){
        this.source = source;
        this.pid = pid;
        this.date = date;
        this.link = link;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rate = rate;
    }
}
