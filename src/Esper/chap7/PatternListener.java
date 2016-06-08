package Esper.chap7;


import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PatternListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==--------------------------------------------------------------------------------------------=");

			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               =="
				+ " A value : "	+ event.get("a.value") + " timestamp : " + event.get("a.timestamp") 
				+ " B value : "	+ event.get("b.value") + " timestamp : " + event.get("b.timestamp"));
			}
			System.out.println("==                                                               ="
					+ "==--------------------------------------------------------------------------------------------=");

		}
	}
}


