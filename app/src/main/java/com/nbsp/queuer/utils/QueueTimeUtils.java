package com.nbsp.queuer.utils;

import android.util.Log;

import com.nbsp.queuer.App;
import com.nbsp.queuer.R;
import com.nbsp.queuer.db.entity.DetailQueue;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by egor on 21.12.15.
 */
public class QueueTimeUtils {
    public static String timeUntilNLeavesQueue(int N, DetailQueue queue) throws ParseException {
        float peopleLeft = (queue.members().indexOf(queue.getCurrentMember()) + 0.5f); //не знаем когда уйдет текущий - пока считаем что он наполовину ушел
        if (N < peopleLeft - 1){
            throw new IllegalArgumentException("N already left queue");
        }

        Date queueStartTime = null;
        queueStartTime = dateFromTimestamp(queue.timestamp());
        Date now = Calendar.getInstance().getTime();
//        Log.d("4444", now.toString());
//        Log.d("4444", queueStartTime.toString());
//        Log.d("4444", queue.timestamp());

        long diff = now.getTime() - queueStartTime.getTime();

//        Log.d("rerere", String.valueOf(peopleLeft));
//        Log.d("rerere", String.valueOf(diff));


        float averageTimeInQueue = diff / peopleLeft;
//        Log.d("rerere", String.valueOf(averageTimeInQueue));
        long resultMillis = (long) ((N + 1) * averageTimeInQueue);
        long days = TimeUnit.MILLISECONDS.toDays(resultMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(resultMillis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(resultMillis) % 60;

        if(minutes == 0) {
            return "менее 1 мин.";
        }
        String result = String.format("%d " + App.context.getString(R.string.minutes_short), minutes);
        if(hours == 0) {
            return result;
        }
        result = String.format("%d " + App.context.getString(R.string.hours_short), hours) + " " + result;
        if(days == 0) {
            return result;
        }
        return String.format("%d " + App.context.getString(R.string.days_short), days) + " " + result;
    }



    public static Date dateFromTimestamp(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return dateFormat.parse(removeNanoFromStamp(timestamp));
    }

    private static String removeNanoFromStamp(String stamp) throws ParseException {
        int dot = stamp.indexOf('.');
        if (dot == -1) throw new ParseException("no dot in stamp", 0);
        String result;
        try {
            result = stamp.substring(0, dot) + stamp.substring(dot + 10);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("stamp too short", 0);
        }
        return result;
    }
}

