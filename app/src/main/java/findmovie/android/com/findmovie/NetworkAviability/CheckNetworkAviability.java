package findmovie.android.com.findmovie.NetworkAviability;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Musabir on 12/11/2017.
 */

public class CheckNetworkAviability {

    public CheckNetworkAviability() {
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
