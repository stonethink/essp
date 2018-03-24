package net.sourceforge.ganttproject.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * This class groups static methods together to handle dates.
 * 
 * @author bbaranne (Benoit Baranne)
 */
public class DateUtils {
    /**
     * This method tries to parse the given date according to the given locale.
     * Actually, this method tries to parse the given string with several
     * DateFormat : Short, Medium, Long and Full. Normally if you give an
     * appropriate locale in relation with the string, this method will return
     * the correct date.
     * 
     * @param date
     *            String representation of a date.
     * @param locale
     *            Locale use to parse the date with.
     * @return A date object.
     * @throws ParseException
     *             Exception thrown if parsing is fruitless.
     */
    public static Date parseDate(String date, Locale locale)
            throws ParseException {
        Date res = null;
        DateFormat dfShort = DateFormat.getDateInstance(DateFormat.SHORT,
                locale);
        DateFormat dfMedium = DateFormat.getDateInstance(DateFormat.MEDIUM,
                locale);
        DateFormat dfLong = DateFormat.getDateInstance(DateFormat.LONG, locale);
        DateFormat dfFull = DateFormat.getDateInstance(DateFormat.FULL, locale);

        try {
            res = dfShort.parse(date);
        } catch (ParseException ex) {
            try {
                res = dfMedium.parse(date);
            } catch (ParseException ex2) {
                try {
                    res = dfLong.parse(date);
                } catch (ParseException ex3) {
                    res = dfFull.parse(date);
                }
            }
        }
        return res;
    }
}
