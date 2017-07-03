package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

import junit.framework.TestCase;

public class OsmanthusExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setEventId("flow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
            OsmanthusExecutor.getExecutor().withMultipleThreadModel().executeNewEvent(event, null);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
