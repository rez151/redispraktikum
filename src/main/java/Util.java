/**
 * Created by reserchr on 27.10.17.
 */
public class Util {
    static long toDay(String timestamp) {

        StringBuilder timestampBuilder = new StringBuilder(timestamp);
        while (timestampBuilder.length() < 13) timestampBuilder.append("0");
        timestamp = timestampBuilder.toString();
        //timestamp = timestamp.substring(0, 13);
        long ts = Long.parseLong(timestamp);
        return ts - ts % 86400000;
    }
}
