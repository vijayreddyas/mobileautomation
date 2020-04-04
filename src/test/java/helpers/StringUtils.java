package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sagarpabba.
 */

public class StringUtils {
    public static String ALPHA_NUM = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static String NUMERIC = "0123456789";
    public static String ALPHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @author sagarpabba
     * This method is to generate random alpha-numeric string
     */
    public static String getRandomAlphaNumeric(int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            int ndx = (int) (Math.random() * (ALPHA_NUM.length()));
            sb.append(ALPHA_NUM.charAt(ndx));
        }
        return sb.toString();
    }

    /**
     * @author sagarpabba
     * This method is to generate random alpha string
     */
    public static String getRandomAlpha(int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            int ndx = (int) (Math.random() * (ALPHA.length()));
            sb.append(ALPHA.charAt(ndx));
        }
        return sb.toString();
    }

    /**
     * @author sagarpabba
     * This method is to generate random numeric string
     */
    public static String getRandomNumeric(int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            int ndx = (int) (Math.random() * (NUMERIC.length()));
            sb.append(NUMERIC.charAt(ndx));
        }
        return sb.toString();
    }

    /**
     * @author sagarpabba
     * This method is to generate string based on date pattern supplied
     */
    public static String getStringFromDateObject(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }
}
