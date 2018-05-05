package findmovie.android.com.findmovie;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RetrieveData {
    static int counter = 0;
    public static void retrieveData(String name) throws IOException {
        final String FILENAME = "filename.txt";
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);

            Document doc = Jsoup
                    .connect(
                            "https://www.imdb.com/title/tt1258197/reviews")
                    .get();
            Elements content = doc
                    .getElementsByClass("pagecontent");
            Elements content1 = content.get(0).getElementsByClass(
                    "lister-item mode-detail imdb-user-review  collapsable");

            System.out.println(content1.size() + "size");
            for (Element link : content1) {
//							counter++;
                Elements content2 = link
                        .getElementsByClass("text show-more__control");


                Elements content3 = link
                        .getElementsByClass("lister-item-content");
                Elements content4 = content3.get(0)
                        .getElementsByClass("ipl-ratings-bar");
                if(content4.size()>0){
                    String t = content4.get(0).text();
                    System.out.println(t);
                    String[] t1 = t.split("/");
                    System.out.println(t1[0]);

                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

}
