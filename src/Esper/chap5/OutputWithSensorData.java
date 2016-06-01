package Esper.chap5;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class OutputWithSensorData {
	public static void main(String[] arges) throws InterruptedException{
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
		
		//Epl output 
//		String OutputEPL = "select avg(value),* from tempSensor.win:time(12 sec) "
//				+ "output   snapshot every 2 seconds";
//		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
//		stateOutputEPL.addListener(new AggergationListener());
		
		//Epl output with after
//		String OutputEPL = "select avg(value),* from tempSensor.win:length(3) "
//				+ "output after 2 events snapshot every 2 events";
//		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
//		stateOutputEPL.addListener(new AggergationListener());		
//		
		//Epl output with first & last
//		String OutputEPL = "select * from tempSensor.win:length(3) "
//				+ "output first every 2 events";
//		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
//		stateOutputEPL.addListener(new GeneralListener());
		

		//Epl output with last
//		String OutputEPL = "select * from tempSensor.win:length(3) "
//				+ "output last every 2 events";
//		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
//		stateOutputEPL.addListener(new GeneralListener());

		//Epl output with all
//		String OutputEPL = "select * from tempSensor.win:length(5) "
//				+ "output  every 2 events";
//		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
//		stateOutputEPL.addListener(new GeneralListener());

		//Epl output with all
		String OutputEPL = "select * from tempSensor.win:length(15) "
				+ "output  snapshot every 2 events";
		EPStatement stateOutputEPL = admin.createEPL(OutputEPL);
		stateOutputEPL.addListener(new GeneralListener());
		
		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
//		l.start();
//		h.start();

		// Epl: output
	
	}
}
