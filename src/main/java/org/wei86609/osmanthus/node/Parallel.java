package org.wei86609.osmanthus.node;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parallel")
public class Parallel extends Node{

    @XStreamImplicit
    private List<Line> lines;

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public TYPE getType() {
        return TYPE.PARALLEL;
    }

}
