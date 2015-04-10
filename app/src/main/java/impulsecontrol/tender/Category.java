package impulsecontrol.tender;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.dao.GenericRawResults;


/**
 * Created by kelseykerr on 12/14/14.
 */
@DatabaseTable(tableName = "category")
public class Category {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private Interval interval;

    @DatabaseField
    private Double budget;

    @DatabaseField
    private Boolean hidden;

    private Double amountSpent;

    Category() {
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
    }

    Category(String name, Double budget, Interval interval) {
        this.name = name;
        this.budget = budget;
        this.interval = interval;
        this.hidden = false;
    }

    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Interval getInterval() {

        return this.interval;
    }

    public void setInterval(Interval interval) {

        this.interval = interval;
    }

    public Double getBudget() {

        return this.budget;
    }

    public void setBudget(Double budget) {

        this.budget = budget;
    }

    public Boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }


}
