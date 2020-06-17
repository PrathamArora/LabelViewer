package com.sew.labelviewer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.sew.labelviewer.R;

public class BaseActivity extends AppCompatActivity {
    public static final String LABEL_ASSET_DIR = "labeltable.json";
    public static final String CURRENT_LABEL = "currentLabel";

    public static final int BATTERY_SAVER_THEME = -1;
    public static final int DEFAULT_THEME = 0;
    public static final int LIGHT_THEME = 1;
    public static final int DARK_THEME = 2;

    public static final String USER_PREFERENCE_THEME = "themeKey";
    public static final String CURRENT_PREFERENCE_THEME = "themeCurrentKey";


    public void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void manageTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE);
        if (!sharedPreferences.contains(CURRENT_PREFERENCE_THEME)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();


            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                editor.putInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY) {
                editor.putInt(CURRENT_PREFERENCE_THEME, BATTERY_SAVER_THEME);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                editor.putInt(CURRENT_PREFERENCE_THEME, DARK_THEME);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                editor.putInt(CURRENT_PREFERENCE_THEME, LIGHT_THEME);
            } else {
                editor.putInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
            }

            editor.apply();
        }

        int currentUserThemeChoice = sharedPreferences.getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);

        if (currentUserThemeChoice == DARK_THEME) {
            context.setTheme(R.style.DarkTheme);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentUserThemeChoice == LIGHT_THEME) {
            context.setTheme(R.style.LightTheme);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            currentUserThemeChoice = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

            switch (currentUserThemeChoice) {
                case Configuration.UI_MODE_NIGHT_NO:
                    context.setTheme(R.style.LightTheme);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                case Configuration.UI_MODE_NIGHT_MASK:
                    context.setTheme(R.style.DarkTheme);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }
    }
}
