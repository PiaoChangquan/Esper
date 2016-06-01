package Esper.chap3;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class TimeLengthWindow {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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

		
		
//		// Epl: timeWindow - time
//		String timeWindowEPL = "select * from Sensor.win:time(4sec) output snapshot every 4 seconds ";
//		EPStatement statetimeWindowEPL = admin.createEPL(timeWindowEPL);
//		statetimeWindowEPL.addListener(new GeneralListener());
		
		// Epl: timeWindow - time_batch
//		String time_batchWindowEPL = "select * from Sensor.win:time_batch(2)";
//		EPStatement statetime_batchWindowEPL = admin.createEPL(time_batchWindowEPL);
//		statetime_batchWindowEPL.addListener(new GeneralListener());

		// Epl: lengthWindow - length
		 String lengthWindow = "select * from Sensor.win:length(5)";
		 EPStatement statelength = admin.createEPL(lengthWindow);
		 statelength.addListener(new GeneralListener());
		
		// Epl: lengthWindow - length_batch
//		 String length_batchWindow = "select * from Sensor.win:length_batch(2)";
//		 EPStatement statelength_batch = admin.createEPL(length_batchWindow);
//		 statelength_batch.addListener(new GeneralListener());
		
		
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
