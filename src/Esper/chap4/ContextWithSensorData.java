package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class ContextWithSensorData {

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

		// Epl: Context
		// Syntax��create context context_name partition [by]
		// event_property [and event_property [and ...]] from stream_def
		// [, event_property [...] from stream_def]
		// [, ...]
		
		 String CreateContext = "create context SensorContext partition by id, value from Sensor ";
		 String Context = "context SensorContext select context.id,context.name , value from	 Sensor";
		 admin.createEPL(CreateContext);
		 EPStatement stateContext = admin.createEPL(Context);
		 stateContext.addListener(new AggergationListener());

		// Epl: Context whit filter
		// String CreateContext = "create context SensorContext partition by id,
		// value from Sensor(value>20) ";
		// String Context = "context SensorContext select context.id,
		// context.name, context.key1, context.key2 from Sensor";
		//
		// admin.createEPL(CreateContext);
		// EPStatement stateContext = admin.createEPL(Context);
		// stateContext.addListener(new AggergationListener());

		// Epl: Hash Context
		// Syntax��create context context_name coalesce [by]
		// hash_func_name(hash_func_param) from stream_def
		// [, hash_func_name(hash_func_param) from stream_def ]
		// [, ...]
		// granularity granularity_value
		// [preallocate]
		// create context ETest coalesce by hash_code(id) from EStream
		// granularity 10 preallocate
		//
		// String CreateContext = "create context SensorContext coalesce by
		// hash_code(value) from tempSensor granularity 100 ";
		// String Context = "context SensorContext select context.id,
		// context.name, value from tempSensor";
		//
		// admin.createEPL(CreateContext);
		// EPStatement stateContext = admin.createEPL(Context);
		// stateContext.addListener(new AggergationListener());

		// Epl: Category Context
		// Syntax: create context context_name
		// group [by] group_expression as category_label
		// [, group [by] group_expression as category_label]
		// [,...]
		// from stream_def
		
		// String CreateContext = "create context SensorContext "
		// + "group by value<10 as low, "
		// + "group by value>20 and value<30 as middle, "
		// + "group by value>30 as high "
		// + "from tempSensor";
		//
		// String Context = "context SensorContext select context.id,
		// context.name, context.label, value from tempSensor";
		//
		// admin.createEPL(CreateContext);
		// EPStatement stateContext = admin.createEPL(Context);
		// stateContext.addListener(new AggergationListener());

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
