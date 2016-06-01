package Esper.chap5;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class insertIntoWithSensorData {
	public static void main(String[] arges) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// add EventType
		String Sensor = SensorData.class.getName();
		admin.getConfiguration().addEventType("Sensor", Sensor);

		// Sensor Stream divide into 3 different Stream
		String eplforTemp = "insert into tempSensor select * from  Sensor(id='Temp') ";
		String eplforHumidity = "insert into humiditySensor select * from   Sensor(id='Humidity') ";
		String eplforLight = "insert into lightSensor select * from  Sensor(id='Light') ";
		admin.createEPL(eplforTemp);
		admin.createEPL(eplforHumidity);
		admin.createEPL(eplforLight);

		// Epl insert
		// Syntax: insert [istream | irstream | rstream] into event_stream_name[(property_name [, property_name] ) ]

		String insertEPL = "insert into newSensor(newId, newValue) select id, value*2 from tempSensor";
		admin.createEPL(insertEPL);
		String selectEPL = "select * from newSensor";
		EPStatement stateinsertEPL = admin.createEPL(selectEPL);
		stateinsertEPL.addListener(new AggergationListener());

		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		// l.start();
		// h.start();

	}
}
