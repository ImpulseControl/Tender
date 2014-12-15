package impulsecontrol.tender;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by hummel on 12/14/14.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        //Pick Item to Display Based on Position
        //selectItem(position);
        Log.w("Nav", "Position: " + position);
    }
}

//    /** Swaps fragments in the main content
//     *  view */
//    private void selectItem(int position) {
//        // Create a new fragment and specify the planet to show based on position
////        Fragment fragment = new Fragment();
////        Bundle args = new Bundle();
////        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
////        fragment.setArguments();
////
////        // Insert the fragment by replacing any existing fragment
////        FragmentManager fragmentManager = getFragmentManager();
////        fragmentManager.beginTransaction()
////                .replace(R.id.content_frame, fragment)
////                .commit();
////
////        // Highlight the selected item, update the title, and close the drawer
////        mDrawerList.setItemChecked(position, true);
////        setTitle(mPlanetTitles[position]);
////        mDrawerLayout.closeDrawer(mDrawerList);
//    }
//
//    @Override
//    public void setTitle(CharSequence title) {
////        CharSequence mTitle = title;
////        getActionBar().setTitle(mTitle);
//    }