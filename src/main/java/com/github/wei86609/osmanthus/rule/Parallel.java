package com.github.wei86609.osmanthus.rule;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("parallel")
public class Parallel extends Rule{

    @XStreamImplicit
    private List<Line> lines;

    @XStreamAsAttribute
    private boolean sync;

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    @Override
    public TYPE getType() {
        return TYPE.PARALLEL;
    }

}
