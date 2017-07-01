package org.wei86609.osmanthus.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Event {

    public static enum MODEL{
        FIRST,LAST,WEIGHT
    }

    private String eventId;

    private MODEL model=MODEL.FIRST;

    public MODEL getModel() {
        return model;
    }

    public void setModel(MODEL model) {
        this.model = model;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    private final Map<String, Object> parameters = new HashMap<String, Object>();

    public void add(String key, Object value) {
        if (!StringUtils.isEmpty(key)) {
            parameters.put(key, value);
        }
    }

    public Object get(String key) {
        if (!StringUtils.isEmpty(key)) {
            return parameters.get(key);
        }
        return null;
    }

    public Map<String, Object> getVars() {
        return parameters;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", model=" + model + ", paramMaps="
                + parameters + "]";
    }

    public Event createNewEvent(){
        Event event=new Event();
        event.setEventId(this.getEventId());
        event.setModel(this.getModel());
        event.getVars().putAll(this.getVars());
        return event;
    }
}
