package impulsecontrol.tender;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by kelseykerr on 1/19/15.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {

        this.expenseList = expenseList;
    }


    @Override
    public int getItemCount() {

        return expenseList.size();
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder expenseViewHolder, int i) {
        Expense e = expenseList.get(i);
        expenseViewHolder.vDescription.setText(e.getDescription());
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(e.getDate());
        expenseViewHolder.vDate.setText(formattedDate);
        expenseViewHolder.vCategory.setText(e.getCategory().getName());
        expenseViewHolder.vAmount.setText(e.getAmount().toString());
        expenseViewHolder.descriptionLayout.setVisibility(LinearLayout.GONE);
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
        protected CardView card;

        protected LinearLayout descriptionLayout;

        public ExpenseViewHolder(View v) {
            super(v);
            CardView card = (CardView) v.findViewById(R.id.expense_card_view);
            vDescription =  (TextView) v.findViewById(R.id.description);
            vDate = (TextView)  v.findViewById(R.id.date);
            vCategory = (TextView)  v.findViewById(R.id.category_type);
            vAmount = (TextView) v.findViewById(R.id.amount);
            descriptionLayout = (LinearLayout) v.findViewById(R.id.description_layout);
        }

    }
}
