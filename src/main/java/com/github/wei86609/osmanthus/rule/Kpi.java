package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Author wangluxia
 * @CreateTime 2017/11/13
 */
@XStreamAlias("kpi")
public class Kpi {

    private String id;

    private Vocabulary primeAttribute;

    private Vocabulary subAttribute;

    private Vocabulary timeSlice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vocabulary getPrimeAttribute() {
        return primeAttribute;
    }

    public void setPrimeAttribute(Vocabulary primeAttribute) {
        this.primeAttribute = primeAttribute;
    }

    public Vocabulary getSubAttribute() {
        return subAttribute;
    }

    public void setSubAttribute(Vocabulary subAttribute) {
        this.subAttribute = subAttribute;
    }

    public Vocabulary getTimeSlice() {
        return timeSlice;
    }

    public void setTimeSlice(Vocabulary timeSlice) {
        this.timeSlice = timeSlice;
    }
}
