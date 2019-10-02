package app.com.gymapp.Util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

public class DisplaySnackBar {
    private static Snackbar snackbar;

    public static void showSnackBar(final Activity activity, String message)
    {
        snackbar = Snackbar
                .make(activity.getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                .setAction("Okay", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(activity.getWindow().getDecorView(), "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });


        snackbar.show();
    }
}
