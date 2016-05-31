package Esper.chap2;


import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.Listener.GeneralListener;



public class CreateEventWithMap
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();
		
		Map<String,Object> Sensor = new HashMap<String,Object>();
		Sensor.put("id", String.class);
		Sensor.put("timestamp", String.class);
		Sensor.put("value", double.class);
		
		admin.getConfiguration().addEventType("sensor", Sensor);
		
		String epl = "select * from sensor";	

		EPStatement state = admin.createEPL(epl);
		state.addListener(new GeneralListener());
	
		Map<String, Object> SensorMap = new HashMap<String, Object>();
		SensorMap.put("id", "temp");	
		SensorMap.put("timestamp", "2016-05-30 11:23:18");
		SensorMap.put("value", 23.1);
		runtime.sendEvent(SensorMap, "sensor");
		System.out.println(SensorMap);
			
		
	}
}