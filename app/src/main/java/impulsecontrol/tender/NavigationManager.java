package impulsecontrol.tender;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hummel on 12/14/14.
 */
public class NavigationManager {
    static public ArrayList<ExpenseFragment> Fragments;
    static public Fragment currentListFragment;
    static public Fragment currentChartFragment;

    NavigationManager(Context context) {
        //For Types of Interval Create Fragment;
        Fragments = new ArrayList<ExpenseFragment>();
        for (Interval interval : Interval.values()) {
            Bundle args = new Bundle();
            args.putInt("interval", interval.getCode());
            ExpenseFragment newFrag = new ExpenseFragment();
            newFrag.setContext(context);
            newFrag.setArguments(args);
            Fragments.add(newFrag);
        }
    }

    public static Fragment getFragmentFromPosition(int position) {
        return Fragments.get(position);
    }

}


