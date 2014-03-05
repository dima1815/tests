package com.mycomp.execspec.jiraplugin.dto.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/2/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NarrativeModel {

    private String text;

    private final Line[] lines;

    public NarrativeModel(Line[] lines) {
        this.lines = lines;
    }

    public Line[] getLines() {
        return lines;
    }

}
