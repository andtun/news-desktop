import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

import java.io.*;

public class example {
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(getHTML("https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000342484-MacBook-Air-Compiling-Error"));
    }
}
