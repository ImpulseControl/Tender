package impulsecontrol.tender;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

/**
 * Created by kelseykerr on 1/11/15.
 */
public class CategoryFragment extends Fragment {
    private ListManager list;
    Context context;
    private ImageButton fab;
    private DatabaseHelper helper;
    private String name;
    private String interval;
    private Double budget;
    private CategoryAdapter categoryAdapter;

    public void setContext(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);

        fab = (ImageButton) view.findViewById(R.id.add_button);

        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                LayoutInflater li = LayoutInflater.from(context);
                View categoryDialogView = li.inflate(R.layout.category_dialog, null);

                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setTitle("Add A New Category")
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }
                        )
                        .setView(categoryDialogView)
                        .create();

                final EditText nameResult = (EditText) categoryDialogView
                        .findViewById(R.id.editName);

                final Spinner intervalResult = (Spinner) categoryDialogView
                        .findViewById(R.id.editInterval);

                addIntervalsToSpinner(intervalResult);

                final EditText budgetResult = (EditText) categoryDialogView
                        .findViewById(R.id.editBudget);

                alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);

                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                boolean canSave = true;

                                if (nameResult.getText().toString().equals("") ||
                                        String.valueOf(intervalResult.getSelectedItem()).equals("") ||
                                        budgetResult.getText().toString().equals("")) {
                                    Toast.makeText(context, "Please Complete all Fields",
                                            Toast.LENGTH_LONG).show();
                                    canSave = false;
                                } else {
                                    name = nameResult.getText().toString().trim();
                                    interval = String.valueOf(intervalResult.getSelectedItem()).trim();
                                    budget = Double.parseDouble(budgetResult.getText().toString().trim());
                                    try {
                                        Dao<Category, Integer> categoryDao = helper.getCategoryDao();
                                        Category newCategory = new Category(name, budget, Interval.valueOfString(interval));
                                        categoryDao.create(newCategory);
                                        categoryAdapter.addCategory(newCategory);
                                        categoryAdapter.notifyDataSetChanged();
                                    } catch (SQLException e) {
                                        Log.e("CategoryFragment", "unable to add new category");
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerList = (RecyclerView) getActivity().findViewById(R.id.category_list);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(llm);

        List<Category> categoryList = new ArrayList<Category>();
        try {
            Dao<Category, Integer> categoryDao = helper.getCategoryDao();
            categoryList = categoryDao.queryBuilder().where().eq("hidden",false).query();
        } catch (SQLException e) {
            Log.e("CategoryFragment", "unable to retrieve categories from database");
        }
        categoryAdapter = new CategoryAdapter(categoryList);
        recyclerList.setAdapter(categoryAdapter);

    }

    private void addIntervalsToSpinner(Spinner categorySpinner) {
        Interval[] intervals = Interval.values();
        List<String> intervalNames = new ArrayList<String>();
        for (int i =0; i<intervals.length; i++) {
            intervalNames.add(intervals[i].toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                simple_spinner_item, intervalNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

    }
}
