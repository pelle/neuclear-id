/*
 * $Id: TimeTools.java,v 1.1 2003/09/19 14:41:15 pelle Exp $
 * $Log: TimeTools.java,v $
 * Revision 1.1  2003/09/19 14:41:15  pelle
 * Initial revision
 *
 * Revision 1.3  2003/02/10 22:30:22  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.2  2003/02/09 00:15:56  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.1  2003/02/09 00:03:10  pelle
 * Clean ups. Got rid of things that have been moved elsewhere
 *
 * Revision 1.1  2003/01/18 18:12:30  pelle
 * First Independent commit of the Independent XML-Signature API for NeuDist.
 *
 * Revision 1.2  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *

*/
package org.neuclear.time;
import org.neuclear.utils.NeudistException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author
 * @version 1.0
 */

public class TimeTools {


    public static java.util.Date addDaysToDate(java.util.Date d, int iDays)
    {
        //create Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        //add days
        cal.add(Calendar.DATE, iDays);

        //create Date
        return cal.getTime();
    }

    public static java.util.Date addMonthsToDate(java.util.Date d, int iMonths)
    {
        //create Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        //add months
        cal.add(Calendar.MONTH, iMonths);

        //create Date
        return cal.getTime();
    }

    public static String createTimeStamp() {
        return formatTimeStamp(new Date());
    }

    public static Timestamp convertDateToTimestamp(Date date) {
        if (date instanceof Timestamp)
            return (Timestamp)date;
        return new Timestamp(date.getTime());
    }

    public static Timestamp parseTimeStamp(String ts) throws NeudistException{
        try {
            return convertDateToTimestamp(getDateFormatter().parse(ts));
        } catch (ParseException e) {
            throw new NeudistException(e);
        }

    }

    public static String formatTimeStamp(Date time) {
        return getDateFormatter().format(time);
    }

    private static SimpleDateFormat getDateFormatter() {
        return DF;
    }

    /**
     * Base ISO 8601 Date format yyyy-MM-ddTHH:mm:ss,SSSzzz i.e., 2002-12-25T14:32:12,333-500 for
     * the 25th day of December in the year 2002 at 2 pm 32 min 12 secs 333 mills -5 hours from GMT
     */
    private static final String ISO_EXPANDED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss,SSSzzz";
    private static final SimpleDateFormat DF=new SimpleDateFormat(ISO_EXPANDED_DATE_TIME_FORMAT);
    {
        DF.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

}
