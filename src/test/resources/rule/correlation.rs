<?xml version="1.0" encoding="UTF-8"?>
<general name="correlation" id="correlation">
    <rule id="cor1" priority="0" exclusive="true">
        <condition><![CDATA[salary>3500]]></condition>
        <action><![CDATA[result='通过']]></action>
    </rule>
 	<rule id="cor1" priority="1">
		<condition><![CDATA[salary<=3500]]></condition>
		<action><![CDATA[lowIncome=true]]></action>
	</rule>
	<rule id="cor2" priority="2">
		<condition><![CDATA[lowIncome]]></condition>
		<action><![CDATA[weight=weight-500]]></action>
	</rule>
 	<rule id="cor3" priority="3">
		<condition><![CDATA[weight<=500]]></condition>
		<action><![CDATA[result='不通过']]></action>
	</rule>
</general>