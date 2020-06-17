package com.sew.labelviewer.activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import com.sew.labelviewer.R;
import com.sew.labelviewer.model.Label;
import com.sew.labelviewer.respository.LabelRepository;
import com.sew.labelviewer.utility.BasicUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ProgressDialog progressDialog;
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
    @SuppressLint("StaticFieldLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageTheme(this);
        setContentView(R.layout.activity_main);


        currentThemeSelection = getSharedPreferences(USER_PREFERENCE_THEME, MODE_PRIVATE)
                .getInt(CURRENT_PREFERENCE_THEME, DEFAULT_THEME);
        defaultThemeCode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


        ArrayList<Label> labelArrayList = BasicUtilities.getLabelList();

        if (labelArrayList == null || labelArrayList.size() == 0) {
            new AsyncTask<Void, Void, ArrayList<Label>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle(getString(R.string.loading_labels));
                    progressDialog.setMessage(getString(R.string.please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected ArrayList<Label> doInBackground(Void... voids) {
                    String json;
                    ArrayList<Label> labelArrayList;
                    try {
                        InputStream is = getAssets().open(BaseActivity.LABEL_ASSET_DIR);
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        json = new String(buffer, StandardCharsets.UTF_8);
                        JSONArray allLabels = new JSONArray(json);
                        labelArrayList = new ArrayList<>(allLabels.length());

                        for (int i = 0; i < allLabels.length(); i++) {
                            JSONObject singleLabel = allLabels.getJSONObject(i);
                            labelArrayList.add(Label.getLabelFromJSON(singleLabel));
                            if (i % 10 == 0)
                                Thread.sleep(1);

                        }

                    } catch (Exception e) {
                        labelArrayList = new ArrayList<>();
                    }

                    return labelArrayList;
                }

                @Override
                protected void onPostExecute(ArrayList<Label> labelArrayList) {
                    super.onPostExecute(labelArrayList);
                    LabelRepository.getInstance().setLabelArrayList(labelArrayList);
                    progressDialog.dismiss();
                    showToast("Size = " + BasicUtilities.getLabelList().size());
                    Intent intent = new Intent(MainActivity.this, ShowLabelActivity.class);
                    startActivity(intent);
                    finish();
                }
            }.execute();
        } else {
            Intent intent = new Intent(MainActivity.this, ShowLabelActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
