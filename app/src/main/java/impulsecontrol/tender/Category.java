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

    private Double amountSpent;

    @DatabaseField(columnName = "start_date")
    private Date startDate;

    @DatabaseField(columnName = "end_date")
    private Date endDate;

    @DatabaseField(columnName = "is_current")
    private Boolean isCurrent;

    Category() {
        // all persisted classes must define a no-arg constructor
        // with at least package visibility
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

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) throws ParseException{
        this.startDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(startDate);
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) throws ParseException{
        this.endDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(endDate);
    }

    public Boolean getIsCurrent() {
        setIsCurrent();
        return this.isCurrent;
    }

    public void setIsCurrent() {
        Date currentDate = new Date();
        if (currentDate.after(this.startDate) && currentDate.before(this.endDate)) {
            this.isCurrent = true;
        } else {
            this.isCurrent = false;
        }
    }

    public Double getAmountSpent(Dao categoryDao) throws SQLException {
        GenericRawResults<Double> rawResults =
                categoryDao.queryRaw("select SUM(amount) from expense e inner join category c on e.category = c.id " +
                        "where c.id = " + this.getId());
        List<Double> results = rawResults.getResults();
        return results.get(0);

    }


}
