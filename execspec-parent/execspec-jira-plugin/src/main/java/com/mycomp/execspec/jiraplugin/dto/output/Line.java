package com.mycomp.execspec.jiraplugin.dto.output;

import com.mycomp.execspec.jiraplugin.dto.LineKind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/4/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Line {

    private final String text;

    private final LineToken[] tokens;

    private final LineKind kind;

    public Line(String text, LineToken[] tokens, LineKind kind) {
        this.text = text;
        this.tokens = tokens;
        this.kind = kind;
    }

    public String getText() {
        return text;
    }

    public LineToken[] getTokens() {
        return tokens;
    }

    public LineKind getKind() {
        return kind;
    }
}
