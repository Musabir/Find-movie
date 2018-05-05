package findmovie.android.com.findmovie.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import findmovie.android.com.findmovie.Adapter.FavouriteAdapter;
import findmovie.android.com.findmovie.Model.MovieModel;
import findmovie.android.com.findmovie.R;
import io.realm.Realm;

/**
 * Created by Musabir on 5/3/2018.
 */

public class HistoryFragment extends PreferenceFragment {
    View view;
    Realm realm;
    FavouriteAdapter favouriteAdapter;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        realm = Realm.getDefaultInstance();


        ArrayList<MovieModel> movieModels = new ArrayList(realm.where(MovieModel.class).findAll());
        Log.d("------><>>><>>",movieModels.size()+" size");
        gridView = view.findViewById(R.id.favourite_grid_view);
        favouriteAdapter = new FavouriteAdapter(getActivity(),movieModels);
        gridView.setAdapter(favouriteAdapter);
        getActivity().setTitle("History");
        return view;
    }
}
