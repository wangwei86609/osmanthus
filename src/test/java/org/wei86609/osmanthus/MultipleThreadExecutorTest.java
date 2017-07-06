package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

import junit.framework.TestCase;

public class MultipleThreadExecutorTest extends TestCase {

    public void testAddMultiEvent() {
        Event event=new Event();
        event.setEventId("multiflow");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
            MultipleThreadExecutor executor= new MultipleThreadExecutor();
            executor.addMultiEvent(event, null);
            executor.awaitTermination(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
