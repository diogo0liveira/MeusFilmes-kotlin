package com.dao.mobile.artifact.common;

import org.jetbrains.annotations.Contract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

/**
 * Created on 06/05/2015 11:49.
 *
 * @author Diogo Oliveira.
 */
public class DateTime
{
    private static String DATE_MEDIUM = "dd/MM/yyyy";
    private static String TIME_MEDIUM = "HH:mm:ss";

    private static String DATE_SHORT = "dd/MM/yy";
    private static String TIME_SHORT = "HH:mm";

    /**
     * Data e hora atual em milissegundo.
     *
     * @return (Data e hora em milissegundo)
     */
    public static long current()
    {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Instancia de "Calendar"
     *
     * @return (instancia atual)
     */
    public static Calendar calendar()
    {
        return Calendar.getInstance();
    }

    /**
     * Forma data no formato: <b>20 de fevereiro de 2015</b>
     *
     * @param date (data em milissegundos)
     *
     * @return (data formatada)
     */
    @Contract("null, -> null")
    public static String dateFormatLong(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            return DateFormat.getDateInstance(DateFormat.LONG).format(new Date(date));
        }
    }

    /**
     * Forma data no formato: <b>20 de fev de 2017</b>
     *
     * @param date (data em milissegundos)
     *
     * @return (data formatada)
     */
    @Contract("null, -> null")
    public static String dateFormatMedium(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            return new SimpleDateFormat(DATE_MEDIUM, Locale.getDefault()).format(new Date(date));
        }
    }

    /**
     * Forma data no formato: <b>20/05/2015</b>
     *
     * @param date (data em milissegundos)
     *
     * @return (data formatada)
     */
    @Contract("null, -> null")
    public static String dateFormatShort(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            return new SimpleDateFormat(DATE_SHORT, Locale.getDefault()).format(new Date(date));
        }
    }

    /**
     * Forma data no formato: <b>20 de fevereiro de 2015</b>
     *
     * @param date (data em milissegundos)
     *
     * @return (data formatada)
     */
    @Contract("null, -> null")
    public static String dateFormatLongMonthShort(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);

            String day = String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.DAY_OF_MONTH));
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            String year = String.valueOf(calendar.get(Calendar.YEAR));

            if(Locale.getDefault().equals(Locale.US))
            {
                return String.format(Resources.string(R.string.mask_date_long_month_short), month, day, year);
            }
            else
            {
                return String.format(Resources.string(R.string.mask_date_long_month_short), day, month, year);
            }
        }
    }

    /**
     * Formata hora no formato: <b>12:30</b>
     *
     * @param date (data hora em milissegundos)
     *
     * @return (hora formatada)
     */
    @Contract("null, -> null")
    public static String dateTimeFormatShort(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            return new SimpleDateFormat(build(DATE_SHORT, TIME_SHORT), Locale.getDefault()).format(new Date(date));
        }
    }

    /**
     * Formata hora no formato: <b>12:30:00</b>
     *
     * @param date (data hora em milissegundos)
     *
     * @return (hora formatada)
     */
    @Contract("null, -> null")
    public static String dateTimeFormatMedium(@Nullable Long date)
    {
        if(Numbers.isEmpty(date))
        {
            return null;
        }
        else
        {
            SimpleDateFormat format = new SimpleDateFormat(build(DATE_MEDIUM, TIME_MEDIUM), Locale.getDefault());
            return format.format(new Date(date));
        }
    }

    /**
     * Formata hora no formato: <b>12:30:05</b>
     *
     * @param time (data e hora em milissegundos)
     *
     * @return (hora formatada)
     */
    @Contract("null, -> null")
    public static String timeFormatMedium(@Nullable Long time)
    {
        if(Numbers.isEmpty(time))
        {
            return null;
        }
        else
        {
            return new SimpleDateFormat(TIME_MEDIUM, Locale.getDefault()).format(new Date(time));
        }
    }

    /**
     * Formata hora no formato: <b>12:30</b>
     *
     * @param time (data e hora em milissegundos)
     *
     * @return (hora formatada)
     */
    @Contract("null, -> null")
    public static String timeFormatShort(@Nullable Long time)
    {
        if(Numbers.isEmpty(time))
        {
            return null;
        }
        else
        {
            return new SimpleDateFormat(TIME_SHORT, Locale.getDefault()).format(new Date(time));
        }
    }

    /**
     * Formata hora no formato: <b>12:30</b>
     *
     * @param time (hora e minuto em milissegundos)
     *
     * @return (hora formatada)
     */
    public static String timeFormat(@Nullable Long time)
    {
        if(Numbers.isEmpty(time))
        {
            return null;
        }
        else
        {
            return String.format(Locale.getDefault(), "%02d:%02d", ((time / (1000 * 60 * 60)) % 24), ((time / (1000 * 60)) % 60));
        }
    }

    /**
     * Converte hora e minuto para milissegundos.
     *
     * @param hour   "quantidade de horas"
     * @param minute "quantidade de minutos"
     *
     * @return (hora e minuto em milissegundos)
     */
    public static long milliseconds(int hour, int minute)
    {
        return (TimeUnit.MILLISECONDS.convert(hour, TimeUnit.HOURS) + TimeUnit.MILLISECONDS.convert(minute, TimeUnit.MINUTES));
    }

    /**
     * Verifica se o período é válido.
     *
     * @param start (data inicial)
     * @param end   (data final)
     *
     * @return (true se período valido)
     */
    public static boolean validate(Date start, Date end)
    {
        return ((start != null) && (end != null) && start.compareTo(end) <= 0);
    }

    public static Date truncate(long date)
    {
        Calendar calendar = calendar();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Converte String no formato "/dd/MM/yyyy HH:mm:ss" para Date.
     *
     * @param date String no formato "/dd/MM/yyyy HH:mm:ss".
     *
     * @return date ou null no caso de date inválida.;
     */
    @Contract("null, -> null")
    public static Date parse(@Nullable String date)
    {
        if(!Strings.isEmpty(date))
        {
            try
            {
                return new SimpleDateFormat(build(DATE_MEDIUM, TIME_MEDIUM), Locale.getDefault()).parse(date);
            }
            catch(ParseException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static int toHours(long time)
    {
        return ((int)((time / (1000 * 60 * 60)) % 24));
    }

    public static int toMinutes(long time)
    {
        return ((int)((time / (1000 * 60)) % 60));
    }

    private static String build(String text1, String text2)
    {
        return (text1 + " " + text2);
    }
}
