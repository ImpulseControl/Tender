package impulsecontrol.tender;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kelsey on 12/14/14.
 */
public enum Interval {
    WEEK(0),
    MONTHLY(1),
    YEARLY(2);

    //Convert to Integer and Back
    private static final Map<Integer, Interval> lookup
            = new HashMap<Integer, Interval>();

    static {
        for (Interval s : EnumSet.allOf(Interval.class))
            lookup.put(s.getCode(), s);
    }

    private int code;

    private Interval(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Interval get(int code) {
        return lookup.get(code);
    }
}
