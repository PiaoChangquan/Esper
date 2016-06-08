package Esper.chap8;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MatchListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               ==" + "  A_value: "
						+ event.get("a_value") + ", B_value: " + event.get("b_value") + ", C_value :"
						+ event.get("c_value"));

			}
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
		}
	}
}