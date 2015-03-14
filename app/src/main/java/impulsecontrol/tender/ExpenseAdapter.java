package impulsecontrol.tender;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.layout.simple_spinner_item;

/**
 * Created by kelseykerr on 1/19/15.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {

        this.expenseList = expenseList;
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
    }

    public void deleteExpense(int position) {
        this.expenseList.remove(position);
    }


    @Override
    public int getItemCount() {

        return expenseList.size();
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder expenseViewHolder, int i) {
        Expense e = expenseList.get(i);
        expenseViewHolder.id = e.getId();
        expenseViewHolder.index = i;
        expenseViewHolder.vDescription.setText(e.getDescription());
        String formattedDate = new SimpleDateFormat("MM-dd-yyyy").format(e.getDate());
        expenseViewHolder.vDate.setText(formattedDate);
        expenseViewHolder.vCategory.setText(e.getCategory().getName());
        NumberFormat format = NumberFormat.getCurrencyInstance();
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        String amnt = n.format(e.getAmount());
        expenseViewHolder.vAmount.setText(amnt);
        expenseViewHolder.adapter = this;
        expenseViewHolder.descriptionLayout.setVisibility(LinearLayout.GONE);
        expenseViewHolder.iconLayout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.expense_cardview, viewGroup, false);

        return new ExpenseViewHolder(itemView);
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        protected TextView vDate;
        protected TextView vDescription;
        protected TextView vCategory;
        protected TextView vAmount;
        protected Integer id;
        protected int index;
        protected CardView card;
        protected DatabaseHelper helper;
        protected ExpenseAdapter adapter;
        protected LinearLayout descriptionLayout;
        protected LinearLayout iconLayout;

        public ExpenseViewHolder(View v) {
            super(v);
            helper = new DatabaseHelper(v.getContext());
            CardView card = (CardView) v.findViewById(R.id.expense_card_view);
            vDescription =  (TextView) v.findViewById(R.id.description);
            vDate = (TextView)  v.findViewById(R.id.date);
            vCategory = (TextView)  v.findViewById(R.id.category_type);
            vAmount = (TextView) v.findViewById(R.id.amount);
            descriptionLayout = (LinearLayout) v.findViewById(R.id.description_layout);
            iconLayout = (LinearLayout) v.findViewById(R.id.icon_layout);
            setEditListener(v);
            setDeleteListener(v);

        }

        public void setDeleteListener(final View v) {
            Button deleteButton = (Button) v.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    AlertDialog builder = new AlertDialog.Builder(v.getContext())
                        .setMessage("Are you sure you want to delete " + vCategory.getText() + " - " + vAmount.getText() + "?")
                        .setTitle("Delete Expense")
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {
                                        try {
                                            helper.getExpenseDao().deleteById(id);
                                            adapter.deleteExpense(index);
                                            adapter.notifyDataSetChanged();
                                        } catch(SQLException e) {
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
            // add button listener
            Button editButton = (Button) v.findViewById(R.id.edit_button);

            editButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {

                    // get expense_dialog.xml view
                    LayoutInflater li = LayoutInflater.from(v.getContext());
                    View expenseDialogView = li.inflate(R.layout.expense_dialog, null);

                    final AlertDialog alertDialogBuilder = new AlertDialog.Builder(v.getContext())
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
                    descriptionResult.setText(vDescription.getText());
                    final Spinner categoryResult = (Spinner) expenseDialogView
                            .findViewById(R.id.editCategory);
                    addCategoriesToSpinner(categoryResult, v.getContext());
                    final EditText amountResult = (EditText) expenseDialogView
                            .findViewById(R.id.editAmount);

                    amountResult.setText(vAmount.getText());

                    final DatePicker dateResult = (DatePicker) expenseDialogView
                            .findViewById(R.id.editDate);
                    final String currentDate = vDate.getText().toString();
                    String[] dateFormat = currentDate.split("-");
                    int year = Integer.parseInt(dateFormat[2]);
                    int month = Integer.parseInt(dateFormat[0]) - 1;
                    int day = Integer.parseInt(dateFormat[1]);
                    dateResult.init(year, month, day, null);
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
                                        Toast.makeText(v.getContext(), "Please Complete all Fields",
                                                Toast.LENGTH_LONG).show();
                                        canSave = false;
                                    } else {
                                        String description = descriptionResult.getText().toString().trim();
                                        String amnt = amountResult.getText().toString().trim();
                                        amnt = amnt.replace("$", "");
                                        Double amount = Double.parseDouble(amnt);
                                        int day = dateResult.getDayOfMonth();
                                        int month = dateResult.getMonth();
                                        int year = dateResult.getYear();

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, month, day);

                                        Date date = calendar.getTime();
                                        String stringCategory = String.valueOf(categoryResult.getSelectedItem()).trim();

                                        try {
                                            Dao<Category, Integer> categoryDao = helper.getCategoryDao();
                                            List<Category> results = categoryDao.queryBuilder().where().
                                                    eq("name", stringCategory).query();
                                            Category category = results.get(0);
                                            Expense currentExpense = helper.getExpenseDao().queryForId(id);
                                            currentExpense.setRealDate(date);
                                            currentExpense.setDescription(description);
                                            currentExpense.setCategory(category);
                                            currentExpense.setAmount(amount);
                                            helper.getExpenseDao().createOrUpdate(currentExpense);
                                            adapter.notifyItemChanged(index);
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

        private void addCategoriesToSpinner(Spinner categorySpinner, Context context) {
            List<Category> categories = new ArrayList<Category>();
            try {
                Dao<Category, Integer> categoryDao = helper.getCategoryDao();
                categories = categoryDao.queryForAll();
            } catch (SQLException e) {
                Log.e("ERROR", "unable to find categories in database");
            }

            List<String> categoryNames = new ArrayList<String>(categories.size());
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    simple_spinner_item, categoryNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(dataAdapter);
            for (int i = 0; i<categories.size(); i++) {
                if (vCategory.getText().equals(categories.get(i).getName())) {
                    categorySpinner.setSelection(i);
                }
            }

        }

    }
}
