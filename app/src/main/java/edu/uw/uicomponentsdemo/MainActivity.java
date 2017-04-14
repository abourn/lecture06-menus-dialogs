package edu.uw.uicomponentsdemo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleButton(View v) {
        Log.v(TAG, "You clicked me!");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar.isShowing()) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

    // executed every time the operating system wants
    // to build the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // need to convert from XML to Java. The way we do that is inflate() them
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu); // second param is the menu that was given to us.
        return super.onCreateOptionsMenu(menu);
    }

    public void showHelloDialog() {
        Log.v(TAG, "Hi!");
        // Whenever you want to create a dialog, make a dialog fragment
        // the fragment acts as a wrapper around the dialog that we want to show
        HelloDialogFragment frag = HelloDialogFragment.newInstance();
        frag.show(getSupportFragmentManager(), null); // just giving it a null tag cause whatever rn, doesn't matter

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.hi_menu_item:
//                Log.v(TAG, "Hi!");
                showHelloDialog();
                return true;
            case R.id.bye_menu_item:
                Log.v(TAG, "Bye!");
                // we changed the MoviesFragment from extending Fragment to extending DialogFragment
                // that way, we can call the show() mehtod, which places the fragment all within a Dialog

                Context context = this;
                String message = "Goodbye!";
                int duration = Toast.LENGTH_LONG;

                // because we rarely call constructors in Android, there's a factory method to
                // make the toast...
                Toast toast = Toast.makeText(context, message, duration);
                toast.show();

//                MoviesFragment.newInstance("Bye").show(getSupportFragmentManager(), null);

                return true;
            case R.id.third_menu_item:
                Log.v(TAG, "whaddup");
                return true;
            default:
                // if the button didn't correspond to any of mine, we're gonna pass it up
                // to the parent method so that maybe they can deal with it.
                return super.onOptionsItemSelected(item);
        }
    }

    public static class HelloDialogFragment extends DialogFragment {

        public static HelloDialogFragment newInstance() {
            Bundle args = new Bundle();
            HelloDialogFragment fragment = new HelloDialogFragment();
            fragment.setArguments(args);
            return fragment;
        }

        // will get called by the Dialog class, and specify, "hey, once this
        // dialog has been shown on the screen, this is the dialog that we
        // should show within the fragment"
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // get Activity, because we need to pass the context in.  And this is a Fragment class, so that can't be the context. The activity needs to be the context.
            builder.setTitle("Hello!")
                    .setMessage("This is me saying hello: Hello!");
            builder.setPositiveButton("Hello to you!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.v(TAG, "They said hi back!");
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Log.v(TAG, "Button not clicked");
                }
            });
            AlertDialog dialog = builder.create();

            return dialog;
        }
    }
}
