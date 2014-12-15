package impulsecontrol.tender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.ParseException;

import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
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

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<Category, Integer> dao = getCategoryDao();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        Category category = new Category();
        category.setName("test");
        category.setInterval(Interval.MONTHLY);
        category.setBudget(200.0);
        try{
            category.setStartDate("December 1. 2014");
            category.setEndDate("December 31, 2014");
        } catch (ParseException e) {
            Log.e(DatabaseHelper.class.getName(), "can't add dates to category", e);
        }
        dao.create(category);
        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
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
            // after we drop the old databases, we create the new ones
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
    public Dao<Category, Integer> getDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Category class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Category, Integer> getCategoryDao() {
        if (categoryRuntimeDao == null) {
            categoryRuntimeDao = getRuntimeExceptionDao(Category.class);
        }
        return categoryRuntimeDao;
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

}
