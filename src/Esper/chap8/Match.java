package Esper.chap8;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Stream.StreamThread;

public class Match {
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
		//EPL: Concatenation
		String MatchConcatenation = "select * from tempSensor "
				+ " match_recognize ("
				+ " partition by id   "
				+ " measures A.id as a_id, B.id as b_id, A.value as  a_value, B.value as b_value "
				+ " pattern (A B) "
				+ " define B as Math.abs(B.value - A.value) >= 10"
				+ " ) ";
		

		 EPStatement stateMatchConcatenation = admin.createEPL(MatchConcatenation);
		 stateMatchConcatenation.addListener(new AggergationListener());
		
		//EPL:Alternation
//		String MatchAlternation = "select irstream * from tempSensor "
//				+ "match_recognize ( "
//				+ " partition by id   "
//				+ " measures A.value as  a_value, B.value as b_value, C.value as  c_value" + " pattern (A (B | C)) "
//				+ " define"
//				+ " A as A.value >= 20,"
//				+ " B as B.value <= 45,"
//				+ " C as Math.abs(C.value - A.value) >= 10"
//				+ " ) ";
////
//		 EPStatement stateMatchAlternation = admin.createEPL(MatchAlternation);
//		 stateMatchAlternation.addListener(new AggergationListener());
		
		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		// l.start();
//		h.start();
	}
}