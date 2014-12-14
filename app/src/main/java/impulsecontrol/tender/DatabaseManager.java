package impulsecontrol.tender;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kelsey on 12/14/14.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TenderData.sqlite3";
    //Path
    private static final String DATABASE_PATH = "/data/data/tender/databases";

    private static Context context;
    private static SQLiteDatabase USER_DATA2 = null;

    private static String expenses_table = "expenses";
    private static String expenses_id = "id";
    private static String expenses_date = "date";
    private static String expenses_description = "description";
    private static String expenses_category = "category";
    private static String expenses_amount = "amount";
    private static String expenses_user = "user";

    private static String categories_table = "categories";
    private static String categories_id = "id";
    private static String categories_name = "name";
    private static String categories_interval = "interval";
    private static String categories_budget = "budget";
    private static String categories_amount_spent = "amount_spent";
    private static String categories_start_date = "start_date";
    private static String categories_end_date = "end_date";
    private static String categories_is_current = "is_current";

    private static String users_table = "users";
    private static String users_id = "id";
    private static String users_first_name = "first_name";
    private static String users_last_name = "last_name";
    private static String users_email = "email";

    private static DateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        String create_expenses_table = "CREATE TABLE "
                + expenses_table + "(" +
                expenses_id + " INTEGER PRIMARY KEY," +
                expenses_date + " DATE," +
                expenses_description + " TEXT," +
                expenses_category + " TEXT," +
                expenses_amount + " DOUBLE," +
                expenses_user + " TEXT" +
                ")";
        db.execSQL(create_expenses_table);

        String create_categories_table = "CREATE TABLE "
                + categories_table + "(" +
                categories_id + " INTEGER PRIMARY KEY," +
                categories_name + " TEXT," +
                categories_interval + " TEXT," +
                categories_budget + " DOUBLE," +
                categories_amount_spent + " DOUBLE," +
                categories_start_date + " TEXT," +
                categories_end_date + " TEXT," +
                categories_is_current + " boolean" +
                ")";
        db.execSQL(create_categories_table);

        String create_users_table = "CREATE TABLE "
                + users_table + "(" +
                users_id + " INTEGER PRIMARY KEY," +
                users_first_name + " TEXT," +
                users_last_name + " TEXT," +
                users_email + " TEXT" +
                ")";
        db.execSQL(create_users_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + expenses_table);
        db.execSQL("DROP TABLE IF EXISTS " + categories_table);
        db.execSQL("DROP TABLE IF EXISTS " + users_table);

        // Create tables again
        onCreate(db);
    }


    public  int  addCategory(String name, Interval interval, Double budget, String startDate, String endDate) throws ParseException{
        SQLiteDatabase CATEGORY_DATA = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(categories_name,name);
        values.put(categories_interval,interval.toString());
        values.put(categories_budget,budget);


        values.put(categories_start_date, startDate);
        values.put(categories_end_date, endDate);

        Date currentDate = new Date();
        if (currentDate.after(dt.parse(startDate)) && currentDate.before(dt.parse(endDate))) {
            values.put(categories_is_current, 1);
        } else {
            values.put(categories_is_current, 0);
        }
        long id = CATEGORY_DATA.insert(categories_table, null, values);
        CATEGORY_DATA.close();
        //return Category ID;
        return (int)id;
    }

    public void updateCategory(String name, Interval interval, Double budget, String startDate, String endDate, int categoryId) throws ParseException{

        SQLiteDatabase CATEGORY_DATA = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(categories_name,name);
        values.put(categories_interval,interval.toString());
        values.put(categories_budget,budget);
        values.put(categories_start_date, startDate);
        values.put(categories_end_date, endDate);

        Date currentDate = new Date();
        if (currentDate.after(dt.parse(startDate)) && currentDate.before(dt.parse(endDate))) {
            values.put(categories_is_current, 1);
        } else {
            values.put(categories_is_current, 0);
        }

        CATEGORY_DATA.update(categories_table, values, categories_id + "=" + categoryId, null);

    }

}
