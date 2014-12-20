package impulsecontrol.tender;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity {

    private static DatabaseHelper sessionData;

    private String[] NavTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageButton fab;
    final Context context = this;

    private String description;
    private String category;
    private Double amount;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        //Load/create sql
        sessionData = new DatabaseHelper(this);

        NavTitles = getResources().getStringArray(R.array.NavViews);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        fab = (ImageButton) findViewById(R.id.add_button);

        // Set the adapter for the list view
        Log.d("this is my array", "arr: " + Arrays.toString(NavTitles));
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, NavTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        NavigationManager nav;

        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get expense_dialog.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View expenseDialogView = li.inflate(R.layout.expense_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set expense_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(expenseDialogView);

                final EditText descriptionResult = (EditText) expenseDialogView
                        .findViewById(R.id.editDescription);

                final EditText categoryResult = (EditText) expenseDialogView
                        .findViewById(R.id.editCategory);

                final EditText amountResult = (EditText) expenseDialogView
                        .findViewById(R.id.editAmount);

                final DatePicker dateResult = (DatePicker) expenseDialogView
                        .findViewById(R.id.editDate);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setTitle("Add An Expense")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to results
                                        //TODO:
                                        // - error check dialog
                                        // - if missing values, display message
                                        // - save new object to database
                                        // - create and display card
                                        description = descriptionResult.getText().toString().trim();
                                        category = categoryResult.getText().toString().trim();
                                        amount = Double.parseDouble(amountResult.getText().toString().trim());

                                        int day = dateResult.getDayOfMonth();
                                        int month = dateResult.getMonth();
                                        int year = dateResult.getYear();

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, month, day);

                                        date = calendar.getTime();

                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //TODO get Fragment
        //NavigationManager..

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //Pick Item to Display Based on Position
            selectItem(position);
            Log.w("Nav", "Position: " + position);
        }
    }


    /**
     * Swaps fragments in the main content
     * view
     */
    private void selectItem(int position) {
        // Create a new fragment and specify t
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        //fragment.setArguments();

        //Insert Fragment Using Fragment Manager();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle("Title");
        mDrawerLayout.closeDrawer(mDrawerList);
    }

//
//    @Override
//    public void setTitle(CharSequence title) {
////        CharSequence mTitle = title;
////        getActionBar().setTitle(mTitle);
//    }

}
