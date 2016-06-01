package Esper.chap6;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class NameWindow {
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

		//EPL��NameWindow
		String CreateNameWindow = "create window TempWindow.win:length_batch(6) as tempSensor";
		String InsertDataToNameWindow = "insert into TempWindow select * from tempSensor";
		String SelsectFromWindow = "select * from TempWindow ";
		admin.createEPL(CreateNameWindow);
		admin.createEPL(InsertDataToNameWindow);
		EPStatement stateSelsect = admin.createEPL(SelsectFromWindow);
		stateSelsect.addListener(new GeneralListener());
		//EPL��NameWindow OnDelete
		String OnDelete = "on humiditySensor as humdt delete from TempWindow as temp where temp.value=humdt.value";
		EPStatement stateOnDelete = admin.createEPL(OnDelete);
		stateOnDelete.addListener(new AggergationListener());
		//EPL��NameWindow OnSelect
		String OnSelect = "on tempSensor(value<10) select temp.* from TempWindow as temp";
		EPStatement stateOnSelect = admin.createEPL(OnSelect);
		stateOnSelect.addListener(new AggergationListener());
		
		
		
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
