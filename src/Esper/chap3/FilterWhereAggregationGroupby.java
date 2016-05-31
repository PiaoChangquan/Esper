package Esper.chap3;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class FilterWhereAggregationGroupby {
	public static void main(String[] arges) {

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

		// Epl: filter
		// String filter = "select * from Sensor(value>20).win:length_batch(2)";
		// EPStatement statefilter = admin.createEPL(filter);
		// statefilter.addListener(new GeneralListener());

		// Epl: where
		// String where = "select * from Sensor.win:length_batch(2) where value >20";
		// EPStatement statewhere = admin.createEPL(where);
		// statewhere.addListener(new GeneralListener());

		// Epl: Aggregation
		// String Aggregation = "select avg(value) from Sensor.win:length_batch(2)";
		// EPStatement stateAggregation = admin.createEPL(Aggregation);
		// stateAggregation.addListener(new AggergationEPLListener());

		// Epl: group by
		String Groupby = "select avg(value), id from Sensor.win:length_batch(4) group by id";
		EPStatement stateGroupby = admin.createEPL(Groupby);
		stateGroupby.addListener(new AggergationListener());

	}
}
