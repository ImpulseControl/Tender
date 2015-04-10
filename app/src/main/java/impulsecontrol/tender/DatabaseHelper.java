package impulsecontrol.tender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by kelsey on 12/14/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "tenderData.db";

    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the category table
    private Dao<Category, Integer> categoryDao = null;
    private RuntimeExceptionDao<Category, Integer> categoryRuntimeDao = null;
    private Dao<Expense, Integer> expenseDao = null;
    private RuntimeExceptionDao<Expense, Integer> expenseRuntimeDao = null;
    private Dao<User, Integer> userDao = null;
    private RuntimeExceptionDao<User, Integer> userRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Expense.class);
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Expense.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our Category class. It will create it or just give the cached
     * value.
     */
    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Category class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Category, Integer> getCategoryRuntimeDao() {
        if (categoryRuntimeDao == null) {
            categoryRuntimeDao = getRuntimeExceptionDao(Category.class);
        }
        return categoryRuntimeDao;
    }

    public Dao<Expense, Integer> getExpenseDao() throws SQLException {
        if (expenseDao == null) {
            expenseDao = getDao(Expense.class);
        }
        return expenseDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        categoryDao = null;
        categoryRuntimeDao = null;
    }

    public List<Expense> getWeeklyList() throws SQLException {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c = setCalendarTime(c, 0, 0, 0, 0);
        Date startDate = c.getTime();
        c.add(Calendar.DATE, 6);
        Date endDate = c.getTime();
        return createExpenseQuery(startDate, endDate);
    }

    public List<Expense> getMonthlyList() throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar = setCalendarTime(calendar, 0, 0, 0, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar = setCalendarTime(calendar, 23, 59, 59, 999);
        Date endDate = calendar.getTime();

        return createExpenseQuery(startDate, endDate);
    }

    public List<Expense> getYearlyList() throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar = setCalendarTime(calendar, 0, 0, 0, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar = setCalendarTime(calendar, 23, 59, 59, 999);
        Date endDate = calendar.getTime();

        return createExpenseQuery(startDate, endDate);
    }

    private Calendar setCalendarTime(Calendar cal, int hour, int minute, int second, int millisecond) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal;
    }

    private List<Expense> createExpenseQuery(Date startDate, Date endDate) throws SQLException {
        QueryBuilder<Expense, Integer> queryBuilder =
                expenseDao.queryBuilder();
        Where<Expense, Integer> where = queryBuilder.where();
        where.ge("date", startDate);
        where.and();
        where.le("date", endDate);
        return queryBuilder.query();
    }

    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        try {
            Dao<Category, Integer> categoryDao = this.getCategoryDao();
            categoryList = categoryDao.queryBuilder().where().eq("hidden",false).query();
        } catch (SQLException e) {
            Log.e("CategoryFragment", "unable to retrieve categories from database");
        }
        return categoryList;
    }

    public List<Expense> getExpensesInCategory(Category category, Interval interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar = setCalendarTime(calendar, 0, 0, 0, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar = setCalendarTime(calendar, 23, 59, 59, 999);
        Date endDate = calendar.getTime();
        try {
            expenseDao = getExpenseDao();
            QueryBuilder<Expense, Integer> queryBuilder =
                    expenseDao.queryBuilder();
            Where<Expense, Integer> where = queryBuilder.where();
            where.eq("category_id", category.getId());
            where.and();
            where.ge("date", startDate);
            where.and();
            where.le("date", endDate);
            return queryBuilder.query();
        } catch (SQLException e) {
            Log.e("database error: ", "Unable to get expenses from database", e);
        }
        return null;

    }

}
