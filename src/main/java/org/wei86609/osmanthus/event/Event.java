package org.wei86609.osmanthus.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Event {

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

}
