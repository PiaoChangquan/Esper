package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.addOtherListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class Context {

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

		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		l.start();
		// h.start();

		// Epl: Context
		 String CreateContext = "create context SensorContext partition by id , price from Sensor ";
		 String Context = "context SensorContext select context.id, context.key from Sensor";
		 EPStatement stateCreateContext = admin.createEPL(CreateContext);
		 EPStatement stateContext = admin.createEPL(Context);
		 stateContext.addListener(new addOtherListener());
	}

}
