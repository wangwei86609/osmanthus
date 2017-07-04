## what is the Osmanthus?
Osmanthus is a framework for rules & logic engines, it is a lightweight library and based on MVEL library , but it is easier to use than drools, supports very complex & complicated rules and logics. Such as: it can help us to implement decision tree and score card rules, can execute multiple rules in parallel mode.

## Core features

 * Lightweight library and easy to learn API
 * Development with xml programming model
 * All conditions & Actions supports MVEL expression languages
 * Useful abstractions to define business rules and apply them easily with Java
 * The ability to create composite rules from primitive xml codes.
 * Parallel mode to execute multiple rules.
 * Support decision tree and score cards rules.
 
 ## Hello world(Score Card rules)

### First, define rules in one XML file:

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

### Then, write unit test Java code:

```java
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

```

### Last, output log:
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
