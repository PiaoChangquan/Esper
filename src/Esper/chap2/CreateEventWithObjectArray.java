package Esper.chap2;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import Esper.unit.Listener.GeneralListener;


public class CreateEventWithObjectArray
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();


		
		String[] SensorPropName = { "id", "timestamp","value" };
		Object[] SensorPropType = { String.class, String.class, double.class};
		admin.getConfiguration().addEventType("Sensor", SensorPropName, SensorPropType);
		
		String epl = "select * from Sensor";	

		EPStatement state = admin.createEPL(epl);
		state.addListener(new GeneralListener());
		
		System.out.println("Send id:temp, timestamp:2016-05-30 11:23:18  value:23.0");		
		runtime.sendEvent(new Object[] {"temp","2016-05-30 11:23:18", 23.0}, "Sensor");
		

		
	}
}

