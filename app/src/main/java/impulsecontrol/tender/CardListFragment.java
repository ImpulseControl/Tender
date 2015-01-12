package impulsecontrol.tender;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_spinner_item;
import static android.text.TextUtils.isEmpty;

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
    private DatabaseHelper helper;


    public void setContext(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List = new ListManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO Create List View
        //TOOO link List Adapter
        //TODO Add TextBox to List Adapter

        //Get Fragment Type (weekly, monthly, yearly)
        Bundle b = getArguments();
        int intervalValue = b.getInt("interval");
        Interval thisInterval = Interval.get(intervalValue);

        view = inflater.inflate(R.layout.expense_fragment, container, false);
        fab = (ImageButton) view.findViewById(R.id.add_button);

        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                // get expense_dialog.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View expenseDialogView = li.inflate(R.layout.expense_dialog, null);

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle("Add An Expense")
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }
                        )
                        .setView(expenseDialogView)
                        .create();

                final EditText descriptionResult = (EditText) expenseDialogView
                        .findViewById(R.id.editDescription);

                final Spinner categoryResult = (Spinner) expenseDialogView
                        .findViewById(R.id.editCategory);

                addCategoriesToSpinner(categoryResult);

                final EditText amountResult = (EditText) expenseDialogView
                        .findViewById(R.id.editAmount);

                final DatePicker dateResult = (DatePicker) expenseDialogView
                        .findViewById(R.id.editDate);

                alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);

                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                boolean canSave = true;

                                if (descriptionResult.getText().toString().equals("") ||
                                        String.valueOf(categoryResult.getSelectedItem()).equals("") ||
                                        amountResult.getText().toString().equals("")) {
                                    Toast.makeText(context, "Please Complete all Fields",
                                            Toast.LENGTH_LONG).show();
                                    canSave = false;
                                } else {
                                    description = descriptionResult.getText().toString().trim();
                                    category = String.valueOf(categoryResult.getSelectedItem()).trim();
                                    amount = Double.parseDouble(amountResult.getText().toString().trim());
                                    int day = dateResult.getDayOfMonth();
                                    int month = dateResult.getMonth();
                                    int year = dateResult.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);

                                    date = calendar.getTime();

                                    alertDialogBuilder.dismiss();
                                }
                            }
                        });
                    }
                });

                alertDialogBuilder.show();
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

    private void addCategoriesToSpinner(Spinner categorySpinner) {
        List<Category> categories = new ArrayList<Category>();
        try {
            Dao<Category, Integer> categoryDao = helper.getCategoryDao();
            categories = categoryDao.queryForAll();
        } catch (SQLException e) {
            //TODO: throw useful exception
        }

        List<String> categoryNames = new ArrayList<String>(categories.size());
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                simple_spinner_item, categoryNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

    }
}
