package com.nbsp.queuer.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nbsp.queuer.R;

/**
 * Created by nickolay on 20.12.15.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String PREF_THEME = "pref_theme";
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void initTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString(PREF_THEME, getString(R.string.theme_default));
        switch (theme) {
            case "blue":
                setTheme(R.style.ColorThemeBlue);
                break;
            case "orange":
                setTheme(R.style.ColorThemeOrange);
                break;
        }
    }
}
