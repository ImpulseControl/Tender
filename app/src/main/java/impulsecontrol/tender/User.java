package impulsecontrol.tender;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by kelseykerr on 12/14/14.
 */
@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "first_name")
    private String firstName;

    @DatabaseField(columnName = "last_name")
    private String lastName;

    @DatabaseField
    private String email;


    User() {

    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
