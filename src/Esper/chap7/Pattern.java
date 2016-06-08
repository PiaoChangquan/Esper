package Esper.chap7;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Stream.StreamThread;

public class Pattern {
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
		//EPL:follow
//		String EPLpettern = "select * from pattern [ every ( a= tempSensor ->b = humiditySensor)]";
//
//		EPStatement stateEPLpettern = admin.createEPL(EPLpettern);
//		stateEPLpettern.addListener(new PatternListener());

		
		//EPL:@SuppressOverlappingMatches
//		String EplPattern = "select * from pattern @SuppressOverlappingMatches"
//				+ "[every a=tempSensor -> b=humiditySensor(value < a.value)]";
		//EPL:@DiscardPartialsOnMatch
//		String EplPattern = "select * from pattern @DiscardPartialsOnMatch"
//				+ "[every a=tempSensor -> b=humiditySensor(value < a.value)]";
//		EPStatement stateEplPattern = admin.createEPL(EplPattern);
//		stateEplPattern.addListener(new PatternListener());
		
		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		// l.start();
		h.start();

	}
}
