package Esper.unit.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class GeneralListener implements UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		String timestamp2 = dateFormat.format(new Date());
		if (newEvents != null) {
			System.out.println("==             New Event "+timestamp2+"                     ="
					+ "==------------------------------------------------------------=");
			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               ==   " + "id�� "
						+ event.get("id") + ", timestamp�� " + event.get("timestamp") + ", value�� " + event.get("value")
						+ "   ==");
			}
			System.out.println("==                                                               ="
					+ "==------------------------------------------------------------=");
		}
		if (oldEvents != null) {
			for (int i = 0; i < oldEvents.length; i++) {
				EventBean event = oldEvents[i];
				System.out.println("=========================Old Event======================================" + "id�� "
						+ event.get("id") + ", timestamp�� " + event.get("timestamp") + ", value�� "
						+ event.get("value"));
			}

		}
	}
}