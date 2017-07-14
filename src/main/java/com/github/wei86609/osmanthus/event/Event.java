package com.github.wei86609.osmanthus.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Event {

    public enum MODEL{
        FIRST,LAST,WEIGHT
    }

    private final Map<String, Object> parameters = new HashMap<String, Object>();

    private String eventId;

    private String flowId;

    private final List<Event> subEvents=new ArrayList<Event>();

    private MODEL model=MODEL.FIRST;

    private boolean error;

    public List<Event> getSubEvents() {
        return subEvents;
    }

    public void addSubEvent(Event subEvent){
        subEvents.add(subEvent);
    }

    public boolean canRunNextNode(){
        return (MODEL.FIRST.equals(model) && error)?false:true;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


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
        return "Event [parameters=" + parameters +" eventId=" + eventId + ", flowId="
                + flowId + ", subEvents'size=" + subEvents.size() + ", model=" + model
                + ", error=" + error + "]";
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Event copy(){
        Event event=new Event();
        event.setEventId(this.getEventId());
        event.setModel(this.getModel());
        event.setFlowId(this.getFlowId());
        event.getVars().putAll(this.getVars());
        return event;
    }
}
