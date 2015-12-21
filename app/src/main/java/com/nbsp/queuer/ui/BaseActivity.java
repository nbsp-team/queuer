package com.nbsp.queuer.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
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
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

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
