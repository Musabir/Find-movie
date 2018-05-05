package findmovie.android.com.findmovie.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import findmovie.android.com.findmovie.Helper.Helper;
import findmovie.android.com.findmovie.MovieDetails;
import findmovie.android.com.findmovie.R;

/**
 * Created by Musabir on 5/3/2018.
 */

public class SearchFragment extends PreferenceFragment {
    View view;
    AppCompatEditText sh;
    AppCompatButton btn;
    String url = "http://www.omdbapi.com/?apikey=6f7926cf&t=";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_movie, container, false);
        sh = view.findViewById(R.id.verification_code_edittext);
        btn = view.findViewById(R.id.next_btn_verification);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url ="http://www.omdbapi.com/?apikey=6f7926cf&t=";
                if(sh.getText().toString().trim()!=null){
                    String a = sh.getText().toString().trim().replace(' ','_');
                    url+=a;
                    GetGameByIdPostRequest();
                }
                else Helper.showCustomAlert(getActivity(),"Please enter a movie name");
            }
        });


        getActivity().setTitle("Find Movie");

        return view;
    }



    public void GetGameByIdPostRequest() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
         pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Responsses of game by id", response);

                        pDialog.dismiss();
                        int code = 0;

                        try {
                            JSONObject obj = new JSONObject(response);
                                if (obj.getBoolean("Response") == true) {
                                    Intent intent =  new Intent(getActivity(),MovieDetails.class);
                                    intent.putExtra("response",response);
                                    getActivity().startActivity(intent);
                            }
                            else Helper.showCustomAlert(getActivity(),"No any movie found");

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage() + " message");
                        pDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> parameters = new HashMap<>();
                return parameters;
            }
        };
        queue.add(postRequest);


    }
}
