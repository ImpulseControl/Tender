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

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            //Get Current Date/MOnth/Year
                //TODO Make a cass do this
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            //Fill With Cards
            Bundle b = getArguments();
            int intervalValue = b.getInt("interval");
            Interval thisInterval  = Interval.get(intervalValue);

            //Get Cards from Interval Enum
            //ArrayList<Expense> Expenses = DataBase get(Interval,Week,Month,Year)
            //For (Size of Expenses)
            // {
            // Card = CreateCard(Expense[i]
            // this.addCard(Card)
            // }
            return super.onCreateView(inflater, container, savedInstanceState);
        }


        @Override
        public void onResume()
        {
            //CCheck if NEw Cards
                //Add/Delete Cards
            //Check IF Categories Change
                //UPdate Categories

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
