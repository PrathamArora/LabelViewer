package com.sew.labelviewer.datainterfaces;

import com.sew.labelviewer.model.Label;

import java.util.ArrayList;

public interface IGetLabelData {
    public void onLabelsLoaded(ArrayList<Label> labelArrayList);
}
