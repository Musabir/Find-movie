package findmovie.android.com.findmovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import findmovie.android.com.findmovie.Helper.Helper;
import findmovie.android.com.findmovie.Model.MovieModel;
import findmovie.android.com.findmovie.NetworkAviability.CheckNetworkAviability;
import io.realm.Realm;

public class MovieDetails extends AppCompatActivity {
    ImageView anchor;
    TextView rating, game_play_count, date, game_page_game_name, game_description, gerne, actors, writer,awards;
    SimpleRatingBar overall_ratingBar;
    Button play_button;
    String response = "";
    Realm realm;
    String trainFile = "Train.data", vocabFile = "Vocab.data", stopwordFile = "stopwords.txt";
    private MenuItem favMenuItem;
    private Toolbar toolbar;
    private TextView mTitle;
    private AppBarLayout mAppBar;
    private Gson gson;
    private String id;
    MovieModel movieModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        realm = Realm.getDefaultInstance();
        movieModel = realm.where(MovieModel.class).equalTo("imdbID",id).findFirst();

        gson = new Gson();

        anchor = findViewById(R.id.anchor);
        rating = findViewById(R.id.rating);
        game_play_count = findViewById(R.id.game_play_count);
        date = findViewById(R.id.date);
        game_page_game_name = findViewById(R.id.game_page_game_name);
        game_description = findViewById(R.id.game_description);
        gerne = findViewById(R.id.gerne);
        actors = findViewById(R.id.actors);
        writer = findViewById(R.id.writer);
        awards = findViewById(R.id.awards);
        overall_ratingBar = findViewById(R.id.overall_ratingBar);
        play_button = findViewById(R.id.play_button);
        toolbar = findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mAppBar = (AppBarLayout) findViewById(R.id.appBarLayout);
        mTitle.setTextColor(Color.parseColor("#ffffff"));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        response = getIntent().getStringExtra("response");
        setSupportActionBar((Toolbar) toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (response != null) {
            try {
                getDataFromResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("-----><>>>",verticalOffset+" ver");
                if (verticalOffset > -260) {
                    mTitle.setTextColor(Color.parseColor("#00000000"));

                } else {
                    mTitle.setTextColor(Color.WHITE);
                }

            }


        });


    }

    public void getDataFromResponse(String response) throws JSONException, IOException,NumberFormatException {
        JSONObject obj = new JSONObject(response);
        if (!MovieDetails.this.isFinishing()) {
            if (obj.getBoolean("Response") == true) {
                game_page_game_name.setText(obj.getString("Title"));
                date.setText(obj.getString("Released"));
                game_description.setText(obj.getString("Plot"));
                actors.setText(obj.getString("Actors"));
                writer.setText(obj.getString("Writer"));
                gerne.setText(obj.getString("Genre"));
                awards.setText(obj.getString("Awards"));
                rating.setText(obj.getString("imdbRating"));
                overall_ratingBar.setRating(Float.parseFloat(obj.getString("imdbRating")));
                loadImage(obj.getString("Poster"),anchor);
                mTitle.setText(game_page_game_name.getText().toString());
                id = obj.getString("imdbID");
                retrieveData(obj.getString("Title"));

                movieModel = gson.fromJson(response.toString(),MovieModel.class);
                movieModel.setIsFav(0);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(movieModel);
                realm.commitTransaction();


            }
        }
    }


    public void retrieveData(String name) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String d = "";

                try {
            Document doc = Jsoup
                    .connect(
                            "https://www.imdb.com/title/"+id+"/reviews").get();
            Elements content = doc
                    .getElementsByClass("pagecontent");
            Elements content1 = content.get(0).getElementsByClass(
                    "lister-item mode-detail imdb-user-review  collapsable");

            System.out.println(content1.size() + "size");
            for (Element link : content1) {
//							counter++;
                Elements content2 = link
                        .getElementsByClass("text show-more__control");
                d += content2.get(0).text() + "\n";

//
//                Elements content3 = link
//                        .getElementsByClass("lister-item-content");
//                Elements content4 = content3.get(0)
//                        .getElementsByClass("ipl-ratings-bar");
//                if(content4.size()>0){
//                    String t = content4.get(0).text();
//                    System.out.println(t);
//                    String[] t1 = t.split("/");
//                    System.out.println(t1[0]);
//
//                }

                findWordFromDict(d);

            }
        } catch (IOException e) {

            e.printStackTrace();

        }
                }
            }).start();
    }


    private void NBClassifier(boolean binaryNB, String trainFile, String testFile, String vocabFile, String stopwordFile, boolean removeStopwords) throws IOException{
        long time = System.currentTimeMillis();
        Log.d("------>>>>><>","hereeeee ");

        String s;
        BufferedReader br;
        HashSet<Integer> stopwords = new HashSet<>();
        int distinctWords = 0;

        HashSet<String> stopwordsStr = new HashSet<>();
        if(removeStopwords) {
            br = new BufferedReader(new InputStreamReader(getAssets().open(stopwordFile)));
            while((s = br.readLine())!=null)	stopwordsStr.add(s);
            br.close();
        }

        br = new BufferedReader(new InputStreamReader(getAssets().open(vocabFile)));
        while((s = br.readLine())!=null) {
            if(stopwordsStr.contains(s))	stopwords.add(distinctWords);
            distinctWords++;
        }
        br.close();
        int[] countPos = new int[distinctWords];//countPos[0] = Count(word=vocab[0] && Review=positive)
        int[] countNeg = new int[distinctWords];
        int posReviews = 0, negReviews = 0, totalWordsInPosReviews = 0, totalWordsInNegReviews = 0;

        br = new BufferedReader(new InputStreamReader(getAssets().open(trainFile)));
        while((s = br.readLine())!=null) {
            StringTokenizer st = new StringTokenizer(s," :");
            if(st.countTokens()==0)	continue;
            int rating = Integer.parseInt(st.nextToken());
            //System.out.println(rating);
            if(rating > 5) { // Positive review
                posReviews++;
                while(st.hasMoreTokens()) {
                    //System.out.println(st.nextToken());
                    int word = Integer.parseInt(st.nextToken());
                    int freq = Integer.parseInt(st.nextToken());
                    //System.out.println(word +":"+freq);

                    freq = binaryNB ? 1 : freq;
                    if(stopwords.contains(word))	continue;
                    countPos[word]+=freq;
                    totalWordsInPosReviews+=freq;
                }
            }else { // Negative Review
                negReviews++;
                while(st.hasMoreTokens()) {
                    int word = Integer.parseInt(st.nextToken());
                    int freq = Integer.parseInt(st.nextToken());
                    freq = binaryNB ? 1 : freq;
                    if(stopwords.contains(word))	continue;
                    countNeg[word]+=freq;
                    totalWordsInNegReviews+=freq;
                }
            }
        }
        br.close();
        Reader inputString = new StringReader(testFile);
        Log.d("------><>>>>>",testFile);
        br = new BufferedReader(inputString);
        int truePositive = 0, falsePositive = 0, falseNegative = 0, correctClassification = 0, incorrectClassification = 0;
        while((s = br.readLine())!=null) {
            StringTokenizer st = new StringTokenizer(s, " :");
            double rating = Double.parseDouble(st.nextToken());
            System.out.println(rating+" rating");
            int actual = rating>5 ? 1 : 0;//1-->yes, 0-->no
            double probOfPos = Math.log(posReviews/(posReviews+negReviews+0.0));
            double probOfNeg = Math.log(negReviews/(posReviews+negReviews+0.0));
            while(st.hasMoreTokens()) {

                int word = Integer.parseInt(st.nextToken());
                int freq = Integer.parseInt(st.nextToken());

                freq = binaryNB ? 1 : freq;
                if(stopwords.contains(word))	continue;
                probOfPos+=freq*Math.log((countPos[word]+1)/(totalWordsInPosReviews+distinctWords+0.0));
                probOfNeg+=freq*Math.log((countNeg[word]+1)/(totalWordsInNegReviews+distinctWords+0.0));
            }
            final int predicted = (probOfPos>probOfNeg ? 1 : 0);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if(predicted==0)
                        game_play_count.setText("Not recommended");
                    else
                        game_play_count.setText("Recommended");
                }
            });

            Log.d("------>>>>><>",predicted+" predicted");
            if(predicted  == actual )	correctClassification++;
            else 						incorrectClassification++;
            System.out.println(actual);
            if(predicted==1 && actual==1)			truePositive++;
                else if(predicted==1 && actual==0)		falseNegative++;
            else if(predicted==0 && actual==1)		falsePositive++;
        }
        br.close();
        double accuracy = correctClassification/(correctClassification + incorrectClassification + 0.0);
        double precision = truePositive/(truePositive+falsePositive+0.0);
        double recall = truePositive/(truePositive+falseNegative+0.0);
        double fscore = 2*precision*recall/(precision+recall);
        System.out.println("Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore);
        time = System.currentTimeMillis()-time;
        System.out.println("Time:"+time/1000d+"s");
    }


    @SuppressWarnings("resource")
    private void findWordFromDict(String testFile) throws IOException{
        String s;
        BufferedReader br;
        BufferedReader brr;

        List<String> ar = new ArrayList<>();

        Map<String,Integer> wordMap = new HashMap<String,Integer>();
        Map<Integer,Integer> wordInDict = new HashMap<Integer,Integer>();


        Reader inputString = new StringReader(testFile);
        br = new BufferedReader(inputString);
        while((s = br.readLine())!=null) {
            ar = new ArrayList<>();
            ar =  Arrays.asList(s.split("\\s+"));
            for(int i=0;i<ar.size();i++) {
                String word = ar.get(i).toLowerCase();
                if(wordMap.get(word)!=null) {
                    wordMap.put(word, new Integer(wordMap.get(word).intValue()+1));
                }
                else
                    wordMap.put(word, 1);
            }




            for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                brr = new BufferedReader(new InputStreamReader(getAssets().open(vocabFile)));
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(getAssets().open(vocabFile)));
                String key = entry.getKey();
                int value = entry.getValue();
                wordInDict.put(0,value);
                //System.out.println(key);

                while (reader.readLine()!=null) {

                    s=brr.readLine();
                    //System.out.println(key +" "+ s);

                    if(key.toLowerCase().equals(s.toLowerCase())){
                        int lineNumber = reader.getLineNumber();
                        //System.out.println(lineNumber+" " + key);
                        wordInDict.remove(0);
                        wordInDict.put(lineNumber,value);
                        break;
                    }

                }


                brr.close();
            }


            //System.out.println(wordInDict.toString());




        }
        Map<Integer,Integer> sortedList = new TreeMap<Integer,Integer>(wordInDict);

        int h = 0;
        String a = 6+" ";

        for (Map.Entry<Integer, Integer> entry : sortedList.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            a +=key+":" + value+" ";
            //System.out.println(key+" " + value);

        }
        NBClassifier(false, trainFile, a, vocabFile, stopwordFile, false);

//		System.out.println(a);
        br.close();

    }

    private void loadImage(String url, final ImageView imageView) {


        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(rotate);
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_loading_mark)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.clearAnimation();

                        return false;


                    }
                }).into(imageView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_details_menu, menu);
        favMenuItem = menu.findItem(R.id.favourite);
//        int isFavorite = gameDetailsByIDMapper.getIsFavourite();
//
//        if (isFavorite == 0) {
//            isFaved = false;
//            favMenuItem.setIcon(R.drawable.ic_fav_icon);
//        } else {
//            favMenuItem.setIcon(R.drawable.ic_favourite_gold);
//            isFaved = true;
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.favourite) {

            if(movieModel!=null) {
                Log.d("--->>?>???","hereeee");
                if (movieModel.getIsFav() == 1) {
                    movieModel.setIsFav(0);
                    favMenuItem.setIcon(R.drawable.ic_favourite);

                } else {
                    movieModel.setIsFav(1);

                    favMenuItem.setIcon(R.drawable.ic_favourite_gold);

                }
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(movieModel);
                realm.commitTransaction();
            }
        }
            else if (item.getItemId() == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Movie Link";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://www.imdb.com/title/" + id);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }

        return super.onOptionsItemSelected(item);
    }


}
