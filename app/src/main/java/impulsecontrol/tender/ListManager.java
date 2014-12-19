package impulsecontrol.tender;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hummel on 12/14/14.
 *
 * Create and populate a Fragment with Card View
 */
public class ListManager {

    private ArrayList<Expense> Expenses;
    private ArrayList<Card> Cards;
    private Date date;
    
    private Fragment thisFragment;

    public void populateList(Fragment fragment, Interval interval)
    {
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

    public void SetDate(Date date)
    {
        //this.date = date;
        //updateFragment();

    }


    private void CreateCard(Expense Expense)
    {

    }

    private void updateFragment()
    {

    }




}
