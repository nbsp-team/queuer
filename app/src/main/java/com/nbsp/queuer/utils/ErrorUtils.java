package com.nbsp.queuer.utils;

import android.content.Context;
import android.widget.Toast;


public class ErrorUtils {
    public static void showError(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}