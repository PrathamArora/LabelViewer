package com.sew.labelviewer.activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.sew.labelviewer.R;
import com.sew.labelviewer.model.Label;

import java.util.Objects;

public class ShowLabelDetailsActivity extends BaseActivity {

    private int currentThemeSelection = DEFAULT_THEME;
    private int defaultThemeCode = 1;

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
        setContentView(R.layout.activity_show_label_details);

        currentThemeSelection = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
        defaultThemeCode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        Label selectedLabel = (Label) getIntent().getSerializableExtra(BaseActivity.CURRENT_LABEL);

        ((TextView) findViewById(R.id.tvControlGuId)).setText(Objects.requireNonNull(selectedLabel).getControlGuId());

        ((TextView) findViewById(R.id.tvControlId)).setText(Objects.requireNonNull(selectedLabel).getControlId());

        ((TextView) findViewById(R.id.tvControlPlaceHolder)).setText(Objects.requireNonNull(selectedLabel).getControlPlaceHolder());

        ((TextView) findViewById(R.id.tvControlText)).setText(Objects.requireNonNull(selectedLabel).getControlText());

        ((TextView) findViewById(R.id.tvControlTitle)).setText(Objects.requireNonNull(selectedLabel).getControlTitle());

        ((TextView) findViewById(R.id.tvErrorMessage)).setText(Objects.requireNonNull(selectedLabel).getErrorMessage());

        ((TextView) findViewById(R.id.tvLanguageCode)).setText(Objects.requireNonNull(selectedLabel).getLanguageCode());

        ((TextView) findViewById(R.id.tvLastUpdated)).setText(Objects.requireNonNull(selectedLabel).getLastUpdated());

        ((TextView) findViewById(R.id.tvModuleGuId)).setText(Objects.requireNonNull(selectedLabel).getModuleGuId());

        findViewById(R.id.imgBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowLabelDetailsActivity.this, SettingsActivity.class));
            }
        });

    }

}
