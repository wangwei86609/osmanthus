package org.wei86609.osmanthus;

import java.util.List;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Supervisor;

import junit.framework.TestCase;

public class SingleThreadExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setFlowId("singleflow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
           new SingleThreadExecutor().executeFlow(event);
           List<Supervisor>  supervisors= event.getSupervisorList();
           // new SingleThreadExecutor().executeSingleRule(event, "guessnumber");
            for(Supervisor supervisor:supervisors){
                System.out.println(supervisor);
            }
           // System.out.println(new Random().nextInt(100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
