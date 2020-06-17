package com.sew.labelviewer.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.sew.labelviewer.R;
import com.sew.labelviewer.adapter.LabelAdapter;
import com.sew.labelviewer.utility.BasicUtilities;

public class ShowLabelActivity extends BaseActivity {

    RecyclerView rvLanguageLabels;
    LabelAdapter labelAdapter;
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
        setContentView(R.layout.activity_show_label);

        currentThemeSelection = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
        defaultThemeCode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        rvLanguageLabels = findViewById(R.id.rvLanguageLabels);
        labelAdapter = new LabelAdapter(this, BasicUtilities.getLabelList());
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvLanguageLabels.setLayoutManager(recyclerLayoutManager);
        rvLanguageLabels.setAdapter(labelAdapter);

        findViewById(R.id.imgBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowLabelActivity.this, SettingsActivity.class));
            }
        });

    }

}
