package org.wei86609.osmanthus;

import junit.framework.TestCase;

import org.wei86609.osmanthus.event.Event;

public class OsmanthusTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setFlowId("flow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
            OsmanthusBuilder.build().execute(event);
            System.out.println(event.getVars());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
