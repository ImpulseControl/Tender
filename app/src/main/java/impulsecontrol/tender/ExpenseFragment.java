package impulsecontrol.tender;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_spinner_item;
import static android.text.TextUtils.isEmpty;

/**
 * Created by hummel on 12/20/14.
 */
public class ExpenseFragment extends Fragment {
    private ListManager List;

    Context context;
    View view;
    private ImageButton fab;
    private Interval interval;
    private String description;
    private Category category;
    private Double amount;
    private Date date;
    private DatabaseHelper helper;
    private Dao<Expense, Integer> expenseDao;
    private Dao<Category, Integer> categoryDao;
    private RecyclerView recyclerList;
    private ExpenseAdapter expenseAdapter;


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

        //Get Fragment Type (weekly, monthly, yearly)
        Bundle b = getArguments();
        int intervalValue = b.getInt("interval");
        interval = Interval.get(intervalValue);
        view = inflater.inflate(R.layout.expense_fragment, container, false);
        fab = (ImageButton) view.findViewById(R.id.add_button);
        setFabListener();
        return view;
    }

    public void setFabListener() {
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
                                    amount = Double.parseDouble(amountResult.getText().toString().trim());
                                    int day = dateResult.getDayOfMonth();
                                    int month = dateResult.getMonth();
                                    int year = dateResult.getYear();

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, month, day);

                                    date = calendar.getTime();
                                    String stringCategory = String.valueOf(categoryResult.getSelectedItem()).trim();

                                    try{
                                        List<Category> results = categoryDao.queryBuilder().where().
                                                eq("name",stringCategory).query();
                                        category = results.get(0);
                                        Expense newExpense = new Expense(date, description, category, amount);
                                        expenseDao.createOrUpdate(newExpense);
                                        expenseAdapter.addExpense(newExpense);
                                        expenseAdapter.notifyDataSetChanged();

                                    } catch (SQLException e) {
                                        //do something with exception
                                    }
                                    alertDialogBuilder.dismiss();
                                }
                            }
                        });
                    }
                });

                alertDialogBuilder.show();
            }
        });
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerList = (RecyclerView) getActivity().findViewById(R.id.expense_list);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(llm);

        List<Expense> expenseList = new ArrayList<Expense>();
        try {
            categoryDao = helper.getCategoryDao();
            expenseDao = helper.getExpenseDao();
            Calendar c = Calendar.getInstance();
            switch (interval.getCode()) {
                case 1:
                    expenseList = helper.getWeeklyList();
                    break;
                case 2:
                    expenseList = helper.getMonthlyList();
                    break;
                case 3:
                    expenseList = helper.getYearlyList();
                    break;
            }
        } catch (SQLException e) {
            Log.e("ExpenseFragment", "database error");
        }
        expenseAdapter = new ExpenseAdapter(expenseList);
        recyclerList.setAdapter(expenseAdapter);
    }


}
