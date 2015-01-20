package impulsecontrol.tender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kelseykerr on 12/14/14.
 */
@DatabaseTable(tableName = "expense")
public class Expense {

    private static String expenses_table = "expenses";
    private static String expenses_id = "id";
    private static String expenses_date = "date";
    private static String expenses_description = "description";
    private static String expenses_category = "category";
    private static String expenses_amount = "amount";
    private static String expenses_user = "user";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String description;

    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    private Category category;

    @DatabaseField
    private Double amount;

    Expense() {

    }

    Expense(Date date, String description, Category category, Double amount) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
    //@DatabaseField(nullable = true)
    //User user;

    public int getId() {

        return this.id;
    }

    public Date getDate() {

        return this.date;
    }

    public void setDate(String date) throws ParseException {
        this.date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(date);
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Category getCategory() {

        return this.category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public Double getAmount() {

        return this.amount;
    }

    public void setAmount(Double amount) {

        this.amount = amount;
    }
}
