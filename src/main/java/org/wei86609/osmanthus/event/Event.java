package org.wei86609.osmanthus.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Event {

    public static enum MODEL{
        FIRST,LAST,WEIGHT
    }

    private String flowId;

    private MODEL model=MODEL.FIRST;

    public MODEL getModel() {
        return model;
    }

    public void setModel(MODEL model) {
        this.model = model;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    private final Map<String, Object> paramMaps = new HashMap<String, Object>();

    public void add(String key, Object value) {
        if (!StringUtils.isEmpty(key)) {
            paramMaps.put(key, value);
        }
    }

    public Object get(String key) {
        if (!StringUtils.isEmpty(key)) {
            return paramMaps.get(key);
        }
        return null;
    }

    public Map<String, Object> getVars() {
        return paramMaps;
    }

    @Override
    public String toString() {
        return "Event [flowId=" + flowId + ", model=" + model + ", paramMaps="
                + paramMaps + "]";
    }

}
