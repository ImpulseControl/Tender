package impulsecontrol.tender;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelseykerr on 3/21/15.
 */
public class GraphFragment extends Fragment {

    private ListManager list;
    Context context;
    private DatabaseHelper helper;

    public void setContext(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: allow the user to switch between intervals
        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        List<Category> categories = helper.getCategories();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        int i = 0;
        for (Category category: categories) {
            List<Expense> expenses = helper.getExpensesInCategory(category, Interval.get(2));
            Double total = 0.0;

            for (Expense expense: expenses) {
                total += expense.getAmount();
            }
            double percent = total/(category.getBudget()) * 100;
            DecimalFormat df = new DecimalFormat("#.##");
            percent = Double.valueOf(df.format(percent));
            entries.add(new BarEntry((float) percent, i++));
            labels.add(category.getName());
        }
        BarDataSet dataset = new BarDataSet(entries, "Expenses");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        HorizontalBarChart chart = new HorizontalBarChart(context);
        chart.setDescription(null);
        chart.animateY(1000);
        LinearLayout chartContainer = (LinearLayout) view.findViewById(
                R.id.expense_graph);
        chartContainer.addView(chart);
        BarData data = new BarData(labels, dataset);
        //LimitLine line = new LimitLine(100f);
        chart.setData(data);
        return view;
    }
}
