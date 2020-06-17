package com.sew.labelviewer.thread;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sew.labelviewer.R;
import com.sew.labelviewer.activity.BaseActivity;
import com.sew.labelviewer.datainterfaces.IGetLabelData;
import com.sew.labelviewer.model.Label;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoadAssetThread {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ArrayList<Label> labelArrayList;

    public LoadAssetThread(Context context){
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public ArrayList<Label> loadLabels() {
        try{

            labelArrayList = new AsyncTask<Void, Void, ArrayList<Label>>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected ArrayList<Label> doInBackground(Void... voids) {
                    String json;
                    try {
                        InputStream is = context.getAssets().open(BaseActivity.LABEL_ASSET_DIR);
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        json = new String(buffer, StandardCharsets.UTF_8);
                        labelArrayList = new ArrayList<>();
                        JSONArray allLabels = new JSONArray(json);

                        for(int i = 0; i < allLabels.length(); i++){
                            JSONObject singleLabel = allLabels.getJSONObject(i);
                            labelArrayList.add(Label.getLabelFromJSON(singleLabel));
                        }
                    }
                    catch (Exception e){
                        labelArrayList = new ArrayList<>();
                    }
                    return labelArrayList;
                }

                @Override
                protected void onPostExecute(ArrayList<Label> labelArrayList) {
                    super.onPostExecute(labelArrayList);
                }
            }.execute().get();

            return labelArrayList;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
}
