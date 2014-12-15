package impulsecontrol.tender;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by kelseykerr on 12/14/14.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

        public static void main(String[] args) throws SQLException, IOException {
            writeConfigFile("ormlite_config.txt");
        }

}
