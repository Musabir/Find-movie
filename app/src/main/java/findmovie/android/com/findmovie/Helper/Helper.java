package findmovie.android.com.findmovie.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import findmovie.android.com.findmovie.R;


/**
 * Created by Musabir on 4/3/2018.
 */

    public class Helper {

    public static void showCustomAlert(Context context,String txt)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Call toast.xml file for toast layout
        View toast = inflater.inflate(R.layout.custom_toast, null);
        TextView toast_msg = toast.findViewById(R.id.toast_msg);
        toast_msg.setText(txt);
        Toast toastt = new Toast(context);

        // Set layout to toast
        toastt.setView(toast);

        toastt.setDuration(Toast.LENGTH_SHORT);
        toastt.show();
    }
}
