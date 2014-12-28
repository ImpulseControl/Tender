package impulsecontrol.tender;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hummel on 12/20/14.
 */
public class CardListFragment extends Fragment {
    private ListManager List;

    Context context;
    View view;
    private ImageButton fab;

    private String description;
    private String category;
    private Double amount;
    private Date date;


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List = new ListManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*//TODO Create List View
        //TOOO link List Adapter
        //TODO Add TextBox to List Adapter

        //Get Fragment Type (weekly, monthly, yearly)
        Bundle b = getArguments();
        int intervalValue = b.getInt("interval");
        Interval thisInterval = Interval.get(intervalValue);*/

        view = inflater.inflate(R.layout.expense_fragment, container, false);
        fab = (ImageButton) view.findViewById(R.id.add_button);

        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get expense_dialog.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View expenseDialogView = li.inflate(R.layout.expense_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set expense_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(expenseDialogView);

                final EditText descriptionResult = (EditText) expenseDialogView
                        .findViewById(R.id.editDescription);

                final EditText categoryResult = (EditText) expenseDialogView
                        .findViewById(R.id.editCategory);

                final EditText amountResult = (EditText) expenseDialogView
                        .findViewById(R.id.editAmount);

                final DatePicker dateResult = (DatePicker) expenseDialogView
                        .findViewById(R.id.editDate);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setTitle("Add An Expense")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to results
                                        //TODO:
                                        // - error check dialog
                                        // - if missing values, display message
                                        // - save new object to database
                                        // - create and display card
                                        description = descriptionResult.getText().toString().trim();
                                        category = categoryResult.getText().toString().trim();
                                        amount = Double.parseDouble(amountResult.getText().toString().trim());

                                        int day = dateResult.getDayOfMonth();
                                        int month = dateResult.getMonth();
                                        int year = dateResult.getYear();

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, month, day);

                                        date = calendar.getTime();

                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        return view;

    }


    @Override
    public void onResume() {
        //TODO Check If Changes to Expenses
        //List.checkChanges()
        super.onResume();
    }


    @Override
    public void onPause() {
        //Save Changes
        super.onPause();
    }
}
