package impulsecontrol.tender;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.layout.simple_spinner_item;


public class MainActivity extends Activity {

    private static DatabaseHelper sessionData;

    private String[] NavTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    final Context context = this;
    private NavigationManager navigationManager;

    ExpenseFragment cardListFragment;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);


        Bundle args = new Bundle();
        args.putInt("interval", Interval.WEEKLY.getCode());

        //TODO Add test fragment to Framelayout
        navigationManager = new NavigationManager(context);

        cardListFragment = new ExpenseFragment();
        cardListFragment.setArguments(args);
        cardListFragment.setContext(context);
        fragmentTransaction.add(R.id.content_frame, cardListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //Load/create sql
        sessionData = new DatabaseHelper(this);

        NavTitles = getResources().getStringArray(R.array.NavViews);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, NavTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        NavigationManager nav;

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

        mDrawerList.setItemChecked(position, true);
        setTitle("Title");
        mDrawerLayout.closeDrawer(mDrawerList);

        switch (position) {
            case 0:
                //selectGraphFragment(position);
                break;
            case 1:
            case 2:
            case 3:
                selectExpenseFragment(position);
                break;
            case 4:
                selectCategoryFragment(position);
                break;
        }
    }

    private void selectGraphFragment(int position) {
        GraphFragment fragment = new GraphFragment();
        fragment.setContext(context);

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(NavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void selectExpenseFragment(int position) {

        ExpenseFragment fragment = new ExpenseFragment();
        fragment.setContext(context);
        Bundle args = new Bundle();
        args.putInt("interval", position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(NavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void selectCategoryFragment(int position) {

        CategoryFragment fragment = new CategoryFragment();
        fragment.setContext(context);
        // Insert the fragment by replacing any existing fragment
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(NavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void clickCard(View v) {
        LinearLayout descriptionLayout = (LinearLayout) v.findViewById(R.id.description_layout);
        LinearLayout iconLayout = (LinearLayout) v.findViewById(R.id.icon_layout);
        if (descriptionLayout.getVisibility() == View.VISIBLE) {
            descriptionLayout.setVisibility(LinearLayout.GONE);
            iconLayout.setVisibility(LinearLayout.GONE);
        } else if (descriptionLayout.getVisibility() == View.GONE) {
            descriptionLayout.setVisibility(LinearLayout.VISIBLE);
            iconLayout.setVisibility(LinearLayout.VISIBLE);
        }

    }
}
