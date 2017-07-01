package org.wei86609.osmanthus;

import junit.framework.TestCase;

import org.wei86609.osmanthus.event.Event;

public class OsmanthusExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setEventId("flow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
            OsmanthusExecutor executor=  new OsmanthusExecutor();
            executor.newEvent(event, null);
            while(true){
                Thread.sleep(1000);
                if(executor.stop()){
                    break;
                }
            }
            System.out.println(event.getVars());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
