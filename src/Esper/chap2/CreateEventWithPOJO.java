package Esper.chap2;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.GeneralListener;

public class CreateEventWithPOJO {
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();
		
		
		String Sensor = SensorData.class.getName();

		admin.getConfiguration().addEventType("sensor", Sensor);

//////		String epl = "select * from " + Person+ "(age in[23:26)) ";	
//////		String epl = "select * from " + Person+ "(age >23) ";	
////
////		String epl = "select * from " + Person+ ".win:length_batch(2)  update istream "+Person+" set name='Piao' where age = 23 ";
////		
//		EPStatement state = admin.createEPL(epl);
//		state.addListener(new PeopleListener());
//		
//		
		String epl = "select * from sensor";	

		EPStatement state = admin.createEPL(epl);
		state.addListener(new GeneralListener());
		
		SensorData s1 = new SensorData();
		s1.setId("temp"); 
		s1.setTimestamp("2016-05-30 11:23:18");
		s1.setValue(23.1);
		System.out.println(s1);	
		runtime.sendEvent(s1);
		
		SensorData s2 = new SensorData();
		s2.setId("ligth");
		s2.setTimestamp("2016-05-30 11:23:18");
		s2.setValue(33.0);
		System.out.println(s2);
		runtime.sendEvent(s2);
		
	}
}
