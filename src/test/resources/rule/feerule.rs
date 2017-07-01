<?xml version="1.0" encoding="UTF-8"?>
<general name="feerule" id="feerule">
	<rule id="step1" multipleTimes="0" exclusive="false">
		<condition><![CDATA[salary<=3500]]></condition>
		<action><![CDATA[fee=0]]></action>
	</rule>
	<rule id="step2" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>3500 && salary<=5000]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.03]]></action>
	</rule>
 	<rule id="step3" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>5000 && salary<=8000]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.1-105]]></action>
	</rule>
	<rule id="step4" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>8000 && salary<=12500]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.2-555]]></action>
	</rule>
	<rule id="step5" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>12500 && salary<=38500]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.25-1005]]></action>
	</rule>
	<rule id="step6" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>38500 && salary<=58500]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.3-2755]]></action>
	</rule>
	<rule id="step7" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>58500 && salary<=83500]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.35-5505]]></action>
	</rule>
	<rule id="step8" multipleTimes="0" exclusive="true">
		<condition><![CDATA[salary>83500]]></condition>
		<action><![CDATA[fee=(salary-3500)*0.45-13505]]></action>
	</rule>
</general>