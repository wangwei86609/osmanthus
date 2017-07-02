package org.wei86609.osmanthus;

import junit.framework.TestCase;

import org.wei86609.osmanthus.event.Event;

public class OsmanthusExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
       // event.setEventId("helloworldflow");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
            OsmanthusExecutor executor=  new OsmanthusExecutor();
            executor.executeRule(event, "card");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
