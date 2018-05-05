package findmovie.android.com.findmovie.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import findmovie.android.com.findmovie.Adapter.FavouriteAdapter;
import findmovie.android.com.findmovie.Model.MovieModel;
import findmovie.android.com.findmovie.MovieDetails;
import findmovie.android.com.findmovie.R;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Musabir on 5/3/2018.
 */

public class FavoriteFragment extends PreferenceFragment {
    View view;
    Realm realm;
    FavouriteAdapter favouriteAdapter;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        realm = Realm.getDefaultInstance();


        ArrayList<MovieModel> movieModels = new ArrayList(realm.where(MovieModel.class).equalTo("isFav",1).findAll());
        gridView = view.findViewById(R.id.favourite_grid_view);
        favouriteAdapter = new FavouriteAdapter(getActivity(),movieModels);
        gridView.setAdapter(favouriteAdapter);
        getActivity().setTitle("Favorite");
        return view;
    }
}
