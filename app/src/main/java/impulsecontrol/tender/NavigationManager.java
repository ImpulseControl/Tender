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
        Log.w("Bug", "Attempt: Interval For Loop");
        for (Interval interval : Interval.values()) {
            Log.w("Bug", "Attempt: New Budble");
            Bundle args = new Bundle();
            Log.w("Bug", "Attempt: Add to Budle");
            args.putInt("interval", interval.getCode());
            Log.w("Bug", "Attempt: New Card Fragement");
            ExpenseFragment newFrag = new ExpenseFragment();
            newFrag.setContext(context);
            Log.w("Bug", "Attempt: Set Bundle to Card");
            newFrag.setArguments(args);
            Log.w("Bug", "Attempt: Add Fragment");
            Fragments.add(newFrag);
        }
    }

    public static Fragment getFragmentFromPosition(int position) {
        return Fragments.get(position);
    }

}


