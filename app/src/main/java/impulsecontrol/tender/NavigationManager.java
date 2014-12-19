package impulsecontrol.tender;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * Created by hummel on 12/14/14.
 */
public class NavigationManager
{
    static public ArrayList<Fragment> Fragments;
    static public Fragment currentFragment;

     NavigationManager()
     {
         //For Types of Interval Create Fragment;
         for (Interval interval :Interval.values()) {
             Bundle args = new Bundle();
             args.putInt("interval", interval.getCode());
             CardListFragment newFrag = new CardListFragment();
             newFrag.setArguments(args);
             Fragments.add(newFrag);
         }
     }



    public static class CardListFragment extends Fragment
    {
        private ListManager List;
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            //Get Fragment Type (weekly, monthly, yearly)
            Bundle b = getArguments();
            int intervalValue = b.getInt("interval");
            Interval thisInterval  = Interval.get(intervalValue);

            //TODO populate with Cards
            //List.populateList(this,interval);
            return super.onCreateView(inflater, container, savedInstanceState);
        }


        @Override
        public void onResume()
        {
            //TODO Check If Changes to Expenses
            //List.checkChanges()
            super.onResume();
        }


        @Override
        public void onPause()
        {
            //Save Changes
            super.onPause();
        }
    }



    public static Fragment getFragmentFromPosition(int position)
    {
        return Fragments.get(position);
    }



}
