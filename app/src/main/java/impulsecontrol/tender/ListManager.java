package impulsecontrol.tender;

import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hummel on 12/14/14.
 * <p/>
 * Create and populate a Fragment with Card View
 */
public class ListManager {

    public ListView listView;
    public ListAdapter listadapter;
    private ArrayList<Expense> Expenses;
    private ArrayList<Card> Cards;
    private Date date;

    private Fragment thisFragment;

    ListManager(Context context) {
        listView = new ListView(context);
        listadapter = new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
    }

    public void populateList(Fragment fragment, Interval interval) {
        //thisFragment = fragment
        //date = Get Date;
        //Get Cards from Interval Enum
        //Expenses = DataBase get(Interval,Date)
        //For (Size of Expenses)
        // {
        // Card = CreateCard(Expense[i]
        // thisFragment.addCard(Card)
        // }
    }

    public void SetDate(Date date) {
        //this.date = date;
        //updateFragment();

    }


    private void CreateCard(Expense Expense) {

    }

    private void updateFragment() {

    }


}
