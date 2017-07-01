<?xml version="1.0" encoding="UTF-8"?>
<card name="card" id="card">
 	<rule id="gc1">
		<condition><![CDATA[salary<=3500]]></condition>
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
</card>