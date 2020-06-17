package com.sew.labelviewer.respository;

import com.sew.labelviewer.model.Label;

import java.util.ArrayList;

public class LabelRepository {
    private ArrayList<Label> labelArrayList;
    private volatile static LabelRepository labelRepository;

    private LabelRepository() {
        labelArrayList = new ArrayList<>();
    }

    public static LabelRepository getInstance() {
        if (labelRepository == null) {
            synchronized (LabelRepository.class) {
                if (labelRepository == null) {
                    labelRepository = new LabelRepository();
                }
            }
        }
        return labelRepository;
    }

    public ArrayList<Label> getLabelArrayList() {
        return labelArrayList;
    }

    public void setLabelArrayList(ArrayList<Label> labelArrayList) {
        this.labelArrayList = labelArrayList;
    }
}
