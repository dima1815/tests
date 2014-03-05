package com.mycomp.execspec.jiraplugin.dto.output;

import com.mycomp.execspec.jiraplugin.dto.LineTokenKind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/2/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LineToken {

    private final LineTokenKind type;

    private final String text;

    public LineToken(String text, LineTokenKind type) {
        this.type = type;
        this.text = text;
    }

    public LineTokenKind getType() {
        return type;
    }

    public String getText() {
        return text;
    }


}
