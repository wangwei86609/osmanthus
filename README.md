## what is the Osmanthus?
Osmanthus is a framework for rules & flow engines, a lightweight library and based on MVEL library. It is more convenient than drools, developers define rules & flows with XML file, it supplies below useful XML node:
### Rule node:
* rule: a single rule, example as below:

<rule id="rulefirst" name="rulefirst" priority="0" exclusive="true" multipleTimes="5" valid="true">
    <condition><![CDATA[true]]></condition>
    <action><![CDATA[System.out.println("this is a rule,hah")]]></action>
</rule>
    
* ruleset: set of rules, it extends the rule, so that means it has all attributes that rule has.

<ruleset name="set" id="set">
    <rule id="rulefirst" name="rulefirst" priority="0" exclusive="true" multipleTimes="5" valid="true">
        <condition><![CDATA[true]]></condition>
        <action><![CDATA[System.out.println("this is the first rule,hah")]]></action>
    </rule>
    <rule id="rulesecond" name="rulesecond" priority="0" exclusive="true" multipleTimes="5" valid="true">
        <condition><![CDATA[true]]></condition>
        <action><![CDATA[System.out.println("this is the second rule,hah")]]></action>
    </rule>
</ruleset>

### Flow Control node:
* split, one flow control node, also extends rule node, has more than one "constraint", but only one "constraint"'s condition is true.

<split id="split1" >
    <constraint toNodeId="card">
        <condition><![CDATA[fee>1]]></condition>
    </constraint>
    <constraint toNodeId="end">
        <condition><![CDATA[fee<=1]]></condition>
    </constraint>
</split>

* parallel, also a flow control node, it controls the execution of rules with parallel model. if your defined flow XML file contains this sort of node, only run this flow with MultipleThreadExecutor Java class. one "line" node that means the system will create a new thread to execute its next nodes.

<parallel id="multiple">
    <line toNodeId="p1"/>
    <line toNodeId="p2"/>
</parallel>

* merge, a merge node, that means this node can merge the execution results of multiple thread. You must define the "lineCnt" attribute, that means you should tell it the number of threads need to be merged.
<merge id="merge" fromNodeId="p3,p4" lineCnt="2" toNodeId="p5"/>

 supports very complex & complicated rules and logics. Such as: it can help us to implement decision tree and score card rules, execute multiple rules with parallel mode.

## Core features

 * Lightweight library and easy to learn API
 * Development with xml programming model
 * All conditions & Actions supports MVEL expression languages
 * Useful abstractions to define business rules and apply them easily with Java
 * The ability to create composite rules from primitive xml codes.
 * Parallel mode to execute multiple rules.
 * Support decision tree and score cards rules.
 
 ## Hello world(Score Card rules)

### Rules in XML file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ruleset name="card" id="card">
 	<rule id="gc1">
		<condition><![CDATA[fee<=1]]></condition>
		<action><![CDATA[weight=weight-100]]></action>
	</rule>
	<rule id="gc2">
		<condition><![CDATA[isBlackName]]></condition>
		<action><![CDATA[weight=weight-200]]></action>
	</rule>
 	<rule id="gc3">
		<condition><![CDATA[name=='test']]></condition>
		<action><![CDATA[weight=weight-500]]></action>
	</rule>
	<rule id="gc4">
		<condition><![CDATA[reg ~= "[1-9][0-9]{4,}"]]></condition>
		<action><![CDATA[weight=weight-600]]></action>
	</rule>
</ruleset>
```

### Java code:

```java
package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

import junit.framework.TestCase;

public class SingleThreadExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setEventId("singleflow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
           new SingleThreadExecutor().executeRule(event, "card");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

### Output log:
```log
2017-07-02 22:23:54,392-DEBUG [main]->(GeneralRuleSetExecutor.java:33) The ruleset[card] of the event{Event [eventId=null, threadId=null, model=FIRST, parameters={fee=500, isBlackName=true, weight=500, reg=12312, name=test, salary=5000}]} has [4] rules
2017-07-02 22:23:54,469-DEBUG [main]->(RuleExecutor.java:22) The node[gc2] of the event {null} condition=[isBlackName] is true and action=[weight=weight-200]
2017-07-02 22:23:54,470-DEBUG [main]->(RuleExecutor.java:22) The node[gc3] of the event {null} condition=[name=='test'] is true and action=[weight=weight-500]
2017-07-02 22:23:54,471-DEBUG [main]->(RuleExecutor.java:22) The node[gc4] of the event {null} condition=[reg ~= "[1-9][0-9]{4,}"] is true and action=[weight=weight-600]
2017-07-02 22:23:54,472-DEBUG [main]->(EmptyNodeExecutor.java:13) The empty node[end] of the event{Event [eventId=null, threadId=null, model=FIRST, parameters={fee=500, isBlackName=true, weight=-800, reg=12312, name=test, salary=5000}]} executed.
Event [eventId=null, threadId=null, model=FIRST, parameters={fee=500, isBlackName=true, weight=-800, reg=12312, name=test, salary=5000}]
```
 ## (Decision tree rules)
 In order to implments the decision tree data structure of rules, we have to use the "SPlIT" node to help us to create more than one branch, but here we call the banrach as "constraint", that contatins one condition, if its condition is true, it will go to "toNodeId" node.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<flow id="flow1">
    <start id="start" toNodeId="feerule"/>
    <ruleset id="feerule" fromNodeId="start" toNodeId="split1" external="true"/>
    <split id="split1" fromNodeId="feerule">
        <constraint toNodeId="card">
            <condition><![CDATA[fee>1]]></condition>
        </constraint>
        <constraint toNodeId="end">
            <condition><![CDATA[fee<=1]]></condition>
        </constraint>
    </split>
    <ruleset id="card" fromNodeId="split1" toNodeId="p5" external="true"/>
    <rule id="p5" fromNodeId="merge" toNodeId="end">
        <condition><![CDATA[2==2]]></condition>
        <action><![CDATA[rule5="p5"]]></action>
    </rule>
    <end id="end"/>
</flow>
```
### Java Code
```Java
package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

import junit.framework.TestCase;

public class SingleThreadExecutorTest extends TestCase {

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
           new SingleThreadExecutor().executeFlow(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

```
 ## Multiple Thread to run rules
If you want to run rules with multiple thead model, you have to use the "Parallel" node to help us to create more than one line, xml code as below：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<flow id="multiflow">
    <start id="start" toNodeId="feerule"/>
    <ruleset id="feerule" fromNodeId="start" toNodeId="split1" external="true"/>
    <split id="split1" fromNodeId="feerule">
        <constraint toNodeId="card">
            <condition><![CDATA[fee>1]]></condition>
        </constraint>
        <constraint toNodeId="end">
            <condition><![CDATA[fee<=1]]></condition>
        </constraint>
    </split>
    <ruleset id="card" fromNodeId="split1" toNodeId="mlines" external="true"/>
    <parallel id="mlines" fromNodeId="card">
        <line toNodeId="p1"/>
        <line toNodeId="p2"/>
    </parallel>
    <rule id="p1" fromNodeId="mlines" toNodeId="end">
        <condition><![CDATA[1==1]]></condition>
        <action><![CDATA[rule1="p1"]]></action>
    </rule>
    <rule id="p2" fromNodeId="mlines" toNodeId="mlines2">
        <condition><![CDATA[2==2]]></condition>
        <action><![CDATA[rule2="p2"]]></action>
    </rule>
    <parallel id="mlines2" fromNodeId="p2">
        <line toNodeId="p3"/>
        <line toNodeId="p4"/>
    </parallel>
    <rule id="p3" fromNodeId="mlines2" toNodeId="merge">
        <condition><![CDATA[1==1]]></condition>
        <action><![CDATA[rule3="p3"]]></action>
    </rule>
    <rule id="p4" fromNodeId="mlines2" toNodeId="merge">
        <condition><![CDATA[2==2]]></condition>
        <action><![CDATA[rule4="p4"]]></action>
    </rule>
    <merge id="merge" fromNodeId="p3,p4" lineCnt="2" toNodeId="p5"/>
    <rule id="p5" fromNodeId="merge" toNodeId="end">
        <condition><![CDATA[2==2]]></condition>
        <action><![CDATA[rule5="p5"]]></action>
    </rule>
    <end id="end"/>
</flow>
```

### Java Code
```Java
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
           new MultipleThreadExecutor().addMultiEvent(event, null);
           Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
 ## Example of "Guess Number"
 The "Guess Number" is a classical example to help us to understand the rule engine, so here can also implement the example by Osmanthus.
 
 ```xml
 <?xml version="1.0" encoding="UTF-8"?>
 <rule id="guessnumber">
     <condition><![CDATA[true]]></condition>
     <action><![CDATA[
    import java.io.*;
    import java.util.Random;
    //
    // Seed the random number
    //

    $num = new Random().nextInt(100);
    System.out.println($num);
    $guesses = 0;
    $in = -1;

    //
    // Setup the STDIN line reader.
    //

    $linereader = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("I'm Thinking of a Number Between 1 and 100... Can you guess what it is? ");

    //
    // Main program loop
    //

    while ($in != $num) {
        if ($in != -1) {
            System.out.print("Nope.  The number is: " + ($num < $in ? "Lower" : "Higher") + ".  What's your next guess? ");
        }
        if (($in = $linereader.readLine().trim()) == empty) $in = -2;
            $guesses++;
    }

    System.out.println("You got it!  It took you " + $guesses + " tries");    
]]></action>
 </rule>
 
 ```
 ### Code example
 ```java
 package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

import junit.framework.TestCase;

public class SingleThreadExecutorTest extends TestCase {

    public void testExecute() {
        Event event=new Event();
        event.setEventId("singleflow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
          // new SingleThreadExecutor().executeFlow(event);
            new SingleThreadExecutor().executeSingleRule(event, "guessnumber");
            
           // System.out.println(new Random().nextInt(100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

 ```
 
