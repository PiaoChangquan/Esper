package Esper.chap9;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Stream.StreamThread;

public class Operators {
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

		// EPL: dot
//		String DotEPL = "select t.getValue() from tempSensor as t";
//		EPStatement stateEPLDotEPL = admin.createEPL(DotEPL);
//		stateEPLDotEPL.addListener(new AggergationListener());

		// EPL: in
//		String InEPL = "select * from tempSensor where value in [0:10]";
//		  String InEPL = "select * from Sensor where id in('Light','Humidity')";
//		EPStatement stateInEPL = admin.createEPL(InEPL);
//		stateInEPL.addListener(new AggergationListener());
		
//		 EPL: any
//		String AnyEPL = "select * from Sensor where id = any('Light','Humidity')";
//		EPStatement stateAnyEPL = admin.createEPL(AnyEPL);
//		stateAnyEPL.addListener(new AggergationListener());
	 
		//EPL:between
//		String betweenEPL = "select * from tempSensor where value between 40 and 60";
//		EPStatement statebetweenEPL = admin.createEPL(betweenEPL);
//		statebetweenEPL.addListener(new AggergationListener());
		
		//EPL:like
//		String likeEPL = "select * from Sensor where id like '%m_'";
//		EPStatement statelikeEPL = admin.createEPL(likeEPL);
//		statelikeEPL.addListener(new AggergationListener());	

//		//EPL:regexpL
//		String regexpEPL = "select * from Sensor where id regexp '.*em.*'";
//		EPStatement stateregexpEPL = admin.createEPL(regexpEPL);
//		stateregexpEPL.addListener(new AggergationListener());
		
		//EPL: new
//		String newEPL = "select new {id , value = value*2 } as newTemp from tempSensor";
//		EPStatement statenewEPL = admin.createEPL(newEPL);
//		statenewEPL.addListener(new AggergationListener());
//		
		//EPL: some
//		String someEPL = "select * from Sensor where id = some('Light','Humidity')";
//		EPStatement statesomeEPL = admin.createEPL(someEPL);
//		statesomeEPL.addListener(new AggergationListener());
	
		
		//EPL: all
		String allEPL = "select * from Sensor where id = all('Light')";
		EPStatement stateallEPL = admin.createEPL(allEPL);
		stateallEPL.addListener(new AggergationListener());

		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		l.start();
		h.start();

	}
}
