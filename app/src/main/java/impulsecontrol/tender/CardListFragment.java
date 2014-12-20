package impulsecontrol.tender;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hummel on 12/20/14.
 */
public class CardListFragment extends Fragment
{
    private ListManager List;

    Context context;
    public void setContext(Context context)
    {
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        List = new ListManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //TEST





        //TODO Create List View
        //TOOO link List Adapter
        //TODO Add TesxtBox to List Adapter



        //Get Fragment Type (weekly, monthly, yearly)
        Bundle b = getArguments();
        int intervalValue = b.getInt("interval");
        Interval thisInterval  = Interval.get(intervalValue);

        //TODO populate with Cards
        //List.populateList(this,interval);
        return inflater.inflate(R.layout.fragment_layout, container, false);
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
