package org.pirlo.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;
import org.joda.time.Weeks;

public class Utils
{

    public static final String SUCCESS = "success";
    public static final String INFO = "info";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

    static public void addInfo(String id, String key)
    {
        FacesContext.getCurrentInstance().addMessage(
                id,
                new FacesMessage(FacesMessage.SEVERITY_INFO, Utils.INFO, Utils
                        .getMessageFromBundle(key)));
    }

    static public void addWarn(String id, String key)
    {
        FacesContext.getCurrentInstance().addMessage(
                id,
                new FacesMessage(FacesMessage.SEVERITY_WARN, Utils.WARN, Utils
                        .getMessageFromBundle(key)));
    }

    static public void addError(String id, String key)
    {
        FacesContext.getCurrentInstance().addMessage(
                id,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.ERROR,
                        Utils.getMessageFromBundle(key)));
    }

    static public void addSuccess(String id, String key)
    {
        FacesContext.getCurrentInstance().addMessage(
                id,
                new FacesMessage(FacesMessage.SEVERITY_INFO, Utils.SUCCESS,
                        Utils.getMessageFromBundle(key)));
    }

    static public String getMessageFromBundle(String key)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Locale locale = context.getViewRoot().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("resources.bundle", locale);

            String message = bundle.getString(key);
            return message;
        } catch (Exception e)
        {
            // TODO: handle exception
            return key;
        }

    }

    public static String convertToSHA256(String s)
            throws NoSuchAlgorithmException
    {
        if (s == null)
        {
            throw new IllegalArgumentException("null argument");
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());

        byte byteData[] = md.digest();

        // convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();

    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {

        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(getStartOfDay(result));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static Calendar calendarFromDate(Date date)
    {
        try
        {
            if (date == null)
            {
                return null;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Date decrementNDays(Date date, int n)
    {
        Calendar cal = calendarFromDate(date);
        cal.add(Calendar.DATE, -n);
        return cal.getTime();
    }

    public static Date incrementNDays(Date date, int n)
    {
        Calendar cal = calendarFromDate(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static int getNumOfWeeksBtnTwoDates(Date d1, Date d2)
    {
        DateTime dateTime1 = new DateTime(d1);
        DateTime dateTime2 = new DateTime(d2);
        return Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
    }

    public static Date getStartOfDay(Date date)
    {
        Calendar cal = calendarFromDate(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndOfDay(Date date)
    {
        Calendar cal = calendarFromDate(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date convertStringToDate(String dateStr)
    {
        Date date;
        try
        {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            date = df.parse(dateStr);
            return date;
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static int daysBetween(Date d1, Date d2)
    {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String toHex(String arg)
    {
        return String.format("%040x", new BigInteger(1, arg.getBytes()));
    }

    public static String generateHexColor(String input)
    {
        String hex = toHex(input);
        return hex.substring(hex.length() - 6, hex.length());
    }

}
