package impulsecontrol.tender;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

/**
 * Created by kelseykerr on 1/11/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;

    public void deleteCategory(int position) {
        this.categoryList.remove(position);
    }

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void addCategory(Category category) {
        this.categoryList.add(category);
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        Category c = categoryList.get(i);
        categoryViewHolder.vName.setText(c.getName());
        categoryViewHolder.vInterval.setText(c.getInterval().toString());
        categoryViewHolder.vBudget.setText(c.getBudget().toString());
        categoryViewHolder.id = c.getId();
        categoryViewHolder.adapter = this;
        categoryViewHolder.index = i;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.category_cardview, viewGroup, false);

        return new CategoryViewHolder(itemView);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vInterval;
        protected TextView vBudget;
        protected DatabaseHelper helper;
        protected Integer id;
        protected CategoryAdapter adapter;
        protected int index;

        public CategoryViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vInterval = (TextView) v.findViewById(R.id.interval);
            vBudget = (TextView) v.findViewById(R.id.budget);
            helper = new DatabaseHelper(v.getContext());
            setDeleteListener(v);
            setEditListener(v);
        }


        public void setDeleteListener(final View v) {
            Button deleteButton = (Button) v.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    AlertDialog builder = new AlertDialog.Builder(v.getContext())
                            .setMessage("Are you sure you want to delete the category: " + vName.getText() + "?")
                            .setTitle("Delete Category")
                            .setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {
                                            try {
                                                Category category = helper.getCategoryDao().queryForId(id);
                                                category.setHidden(true);
                                                helper.getCategoryDao().createOrUpdate(category);
                                                adapter.deleteCategory(index);
                                                adapter.notifyDataSetChanged();
                                            } catch (SQLException e) {
                                                Log.e("ERROR: ", "Can't delete expense with id: " + id, e);
                                            }
                                        }
                                    }
                            )
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }
                            )
                            .create();

                    builder.show();
                }
            });
        }

        public void setEditListener(final View v) {
            Button editButton = (Button) v.findViewById(R.id.edit_button);

            editButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {

                    // get expense_dialog.xml view
                    LayoutInflater li = LayoutInflater.from(v.getContext());
                    View categoryDialogView = li.inflate(R.layout.category_dialog, null);

                    final AlertDialog alertDialogBuilder = new AlertDialog.Builder(v.getContext())
                            .setCancelable(false)
                            .setTitle("Edit Category")
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
                    nameResult.setText(vName.getText());
                    final Spinner intervalResult = (Spinner) categoryDialogView
                            .findViewById(R.id.editInterval);

                    addIntervalsToSpinner(intervalResult, v.getContext());
                    final EditText budgetResult = (EditText) categoryDialogView
                            .findViewById(R.id.editBudget);
                    budgetResult.setText(vBudget.getText());
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
                                        Toast.makeText(v.getContext(), "Please Complete all Fields",
                                                Toast.LENGTH_LONG).show();
                                        canSave = false;
                                    } else {
                                        String name = nameResult.getText().toString().trim();
                                        String budget = budgetResult.getText().toString().trim();
                                        budget = budget.replace("$", "");
                                        Double budgetAmount = Double.parseDouble(budget);
                                        String stringInterval = String.valueOf(intervalResult.getSelectedItem()).trim();

                                        try {
                                            Interval interval = Interval.valueOf(stringInterval);
                                            Category currentCategory = helper.getCategoryDao().queryForId(id);
                                            currentCategory.setName(name);
                                            currentCategory.setBudget(budgetAmount);
                                            currentCategory.setInterval(interval);
                                            helper.getCategoryDao().createOrUpdate(currentCategory);
                                            adapter.notifyDataSetChanged();
                                        } catch (SQLException e) {
                                            Log.e("CategoryAdapter", "Unable to edit category: " + id);
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

        public void addIntervalsToSpinner(Spinner categorySpinner, Context context) {
            Interval[] intervals = Interval.values();
            List<String> intervalNames = new ArrayList<String>();
            for (int i =0; i<intervals.length; i++) {
                intervalNames.add(intervals[i].toString());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    simple_spinner_item, intervalNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(dataAdapter);
            for (int i = 0; i < intervalNames.size(); i++) {
                if (vInterval.getText().equals(intervalNames.get(i))) {
                    categorySpinner.setSelection(i);
                }
            }
        }
    }
}
