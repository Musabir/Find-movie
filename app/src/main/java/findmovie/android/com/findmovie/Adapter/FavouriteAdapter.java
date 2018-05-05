package findmovie.android.com.findmovie.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.HashMap;

import findmovie.android.com.findmovie.Model.MovieModel;
import findmovie.android.com.findmovie.R;
import io.realm.Realm;

/**
 * Created by Musabir on 5/3/2018.
 */

public class FavouriteAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MovieModel> list;

    public FavouriteAdapter(Context mContext, ArrayList<MovieModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MovieModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        final ImageView favourite_game_image;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_custom_favorite_gridview, viewGroup, false);
        }
        Log.d("------>>><><>>",getItem(i).toString());
        favourite_game_image = (ImageView) convertView.findViewById(R.id.favourite_image);
        MovieModel movieModel = getItem(i);
        TextView game_name = (TextView) convertView.findViewById(R.id.favourite_game_name);
        RelativeLayout top_layout = (RelativeLayout) convertView.findViewById(R.id.item);
        final RelativeLayout header_fav = (RelativeLayout) convertView.findViewById(R.id.header_fav);

        final SimpleRatingBar ratingBar = (SimpleRatingBar) convertView.findViewById(R.id.ratingBar);
        final RelativeLayout game_header_gradient = (RelativeLayout) convertView.findViewById(R.id.game_header_gradient);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        favourite_game_image.startAnimation(rotate);
        Glide.with(mContext)
                .load(movieModel.getPoster())
                .placeholder(R.drawable.ic_loading_mark)
                .override(200, 200)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        favourite_game_image.clearAnimation();
                        header_fav.bringToFront();

                        return false;


                    }
                }).into(favourite_game_image);

        ratingBar.setRating((Float.parseFloat(list.get(i).getImdbRating()) / 2));


        ImageView fav_icon = (ImageView) convertView.findViewById(R.id.fav_icon);
        fav_icon.setImageResource(R.drawable.ic_favourite_gold);
        if(getItem(i).getIsFav()==0){
            top_layout.setVisibility(View.GONE);
            fav_icon.setVisibility(View.GONE);
        }
        else {
            top_layout.setVisibility(View.VISIBLE);
            fav_icon.setVisibility(View.VISIBLE);

        }
        game_name.setText(list.get(i).getTitle());

        return convertView;
    }
}
