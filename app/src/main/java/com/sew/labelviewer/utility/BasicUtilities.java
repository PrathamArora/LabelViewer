package com.sew.labelviewer.utility;

import com.sew.labelviewer.model.Label;
import com.sew.labelviewer.respository.LabelRepository;

import java.util.ArrayList;

public class BasicUtilities {

    public static ArrayList<Label> getLabelList() {
        return LabelRepository.getInstance().getLabelArrayList();
    }

}
