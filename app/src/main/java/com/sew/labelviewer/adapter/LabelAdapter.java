package com.sew.labelviewer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sew.labelviewer.R;
import com.sew.labelviewer.activity.BaseActivity;
import com.sew.labelviewer.activity.ShowLabelDetailsActivity;
import com.sew.labelviewer.model.Label;
import com.sew.labelviewer.respository.LabelRepository;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.LabelViewHolder> {

    private Context context;
    private ArrayList<Label> labelArrayList;

    public LabelAdapter(Context context, ArrayList<Label> labelArrayList) {
        this.context = context;
        this.labelArrayList = labelArrayList;
    }

    @NonNull
    @Override
    public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.label_layout, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelViewHolder holder, int position) {
        holder.setLabelDetails(labelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return labelArrayList.size();
    }

    class LabelViewHolder extends RecyclerView.ViewHolder {

        private TextView tvControlID, tvControlText, tvLanguageCode;
        private ImageView imgCancel;

        LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvControlID = itemView.findViewById(R.id.tvControlID);
            tvControlText = itemView.findViewById(R.id.tvControlText);
            tvLanguageCode = itemView.findViewById(R.id.tvLanguageCode);

            itemView.findViewById(R.id.imgCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = LabelViewHolder.this.getLayoutPosition();
                    labelArrayList.remove(position);
                    LabelRepository.getInstance().setLabelArrayList(labelArrayList);
                    notifyDataSetChanged();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = LabelViewHolder.this.getLayoutPosition();
                    Label currentLabel = labelArrayList.get(position);

                    Intent intent = new Intent(context, ShowLabelDetailsActivity.class);
                    intent.putExtra(BaseActivity.CURRENT_LABEL, currentLabel);
                    context.startActivity(intent);
                }
            });
        }

        void setLabelDetails(Label label) {
            tvControlID.setText(label.getControlId());
            tvLanguageCode.setText(label.getLanguageCode());
            tvControlText.setText(label.getControlText());

            if (label.getControlText().length() == 0 || label.getControlText().equals("null")) {
                tvControlText.setText(label.getErrorMessage());
            }

        }

    }
}
