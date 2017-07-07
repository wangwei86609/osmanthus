package org.wei86609.osmanthus.event;

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
    
    private final List<Supervisor> supervisorList=new ArrayList<Supervisor>();
    
    private String eventId;
    
    private String flowId;
    
    private String threadId;
    
    private MODEL model=MODEL.FIRST;
    
    private boolean error;
    
    public boolean canRunNextNode(){
        return (MODEL.FIRST.equals(model) && error)?false:true;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
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

    public List<Supervisor> getSupervisorList() {
        return supervisorList;
    }
    
    public void addSupervisor(Supervisor supervisor) {
        supervisorList.add(supervisor);
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", threadId=" + threadId
                + ", model=" + model + ", parameters=" + parameters + "]";
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Event createNewEvent(){
        Event event=new Event();
        event.setEventId(this.getEventId());
        event.setModel(this.getModel());
        event.getVars().putAll(this.getVars());
        return event;
    }
}
