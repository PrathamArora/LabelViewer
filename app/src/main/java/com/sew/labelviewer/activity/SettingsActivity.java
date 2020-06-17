package com.sew.labelviewer.activity;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sew.labelviewer.R;

public class SettingsActivity extends BaseActivity {

    private int currentThemeSelection = DEFAULT_THEME;
    private int defaultThemeCode = 1;

    private RadioButton rbLightTheme, rbDarkTheme, rbSystemTheme;

    @Override
    protected void onResume() {
        super.onResume();

        int previousThemeSelection = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);

        if ((currentThemeSelection != previousThemeSelection) ||
                (previousThemeSelection == DEFAULT_THEME
                        && defaultThemeCode != (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK))) {

            recreate();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageTheme(this);
        setContentView(R.layout.activity_settings);

        currentThemeSelection = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
        defaultThemeCode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        RadioGroup rgThemeSelection = findViewById(R.id.rgThemeSelection);
        rbLightTheme = rgThemeSelection.findViewById(R.id.rbLightTheme);
        rbDarkTheme = rgThemeSelection.findViewById(R.id.rbDarkTheme);
        rbSystemTheme = rgThemeSelection.findViewById(R.id.rbSystemTheme);

        initSelection();

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (rbLightTheme.isChecked()) {
                    editor.putInt(CURRENT_PREFERENCE_THEME, LIGHT_THEME);
                } else if (rbDarkTheme.isChecked()) {
                    editor.putInt(CURRENT_PREFERENCE_THEME, DARK_THEME);
                } else {
                    editor.putInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
                }

                editor.apply();

                finish();
            }
        });

        findViewById(R.id.imgBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        rgThemeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                if(checkedId == R.id.rbLightTheme){
//
//                }else if(checkedId == R.id.rbDarkTheme){
//
//                }else{
//
//                }
//            }
//        });

    }

    private void initSelection() {
        int currentUserThemeChoice = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);

        if (currentUserThemeChoice == DARK_THEME) {
            currentThemeSelection = DARK_THEME;
            rbDarkTheme.setChecked(true);
        } else if (currentUserThemeChoice == LIGHT_THEME) {
            currentThemeSelection = LIGHT_THEME;
            rbLightTheme.setChecked(true);
        } else {
            currentThemeSelection = DEFAULT_THEME;
            rbSystemTheme.setChecked(true);
        }
    }


}
