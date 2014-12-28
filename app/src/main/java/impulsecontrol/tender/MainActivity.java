package impulsecontrol.tender;

import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;


public class MainActivity extends Activity {

    private static DatabaseHelper sessionData;

    private String[] NavTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    final Context context = this;
    private NavigationManager navigationManager;

    CardListFragment cardListFragment;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);


        Bundle args = new Bundle();
        args.putInt("interval", Interval.WEEK.getCode());

        //TODO Add test fragment to Framelayout
        Log.w("Bug", "Attempt: Navigation Manager");
        navigationManager = new NavigationManager(context);

        cardListFragment = new CardListFragment();
        cardListFragment.setContext(context);
        fragmentTransaction.add(R.id.content_frame, cardListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //Load/create sql
        sessionData = new DatabaseHelper(this);

        NavTitles = getResources().getStringArray(R.array.NavViews);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //navigationManager = new NavigationManager();

        // Set the adapter for the list view
        Log.d("this is my array", "arr: " + Arrays.toString(NavTitles));
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
            //Fragment newfragment = navigationManager.getFragmentFromPosition(position);


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
