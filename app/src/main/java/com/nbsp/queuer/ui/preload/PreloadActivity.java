package com.nbsp.queuer.ui.preload;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nbsp.queuer.R;
import com.nbsp.queuer.ui.BaseActivity;
import com.nbsp.queuer.ui.MainActivity;

/**
 * Created by Dimorinny on 22.12.15.
 */
public class PreloadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        new Handler().postDelayed(() -> runOnUiThread(() -> {
            Intent intent = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }), 2000);
    }
}
