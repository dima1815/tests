package com.mycomp.execspec.jiraplugin.dto.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 2/8/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioModel {

    private final Line[] lines;

    public ScenarioModel(Line[] lines) {
        this.lines = lines;
    }

    public Line[] getLines() {
        return lines;
    }


}
