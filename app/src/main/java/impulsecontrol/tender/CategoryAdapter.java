package impulsecontrol.tender;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

/**
 * Created by kelseykerr on 1/11/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {

        this.categoryList = categoryList;
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

        public CategoryViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.name);
            vInterval = (TextView)  v.findViewById(R.id.interval);
            vBudget = (TextView)  v.findViewById(R.id.budget);
        }
    }
}
