package org.wei86609.osmanthus;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;

public class OsmanthusTask implements Runnable{

    private final static Logger logger = Logger.getLogger(OsmanthusTask.class);

    private Event event;

    private String nodeId;

    private FlowEngine flowEngine;

    public OsmanthusTask() {
        super();
    }

    public FlowEngine getFlowEngine() {
        return flowEngine;
    }

    public void setFlowEngine(FlowEngine flowEngine) {
        this.flowEngine = flowEngine;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void run() {
        try {
            if(flowEngine!=null){
                event.setThreadId(Thread.currentThread().getName());
                flowEngine.execute(event,nodeId);
            }
        } catch (Exception e) {
            logger.error("FlowEngine execute current event={"+event+"} error",e);
        }
    }




}
