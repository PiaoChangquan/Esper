package Esper.unit.Listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AggergationListener implements UpdateListener{
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		// TODO Auto-generated method stub
		for(EventBean bean:newEvents){
			System.out.println("Received Events: "+bean.getUnderlying());
			
		}
	}

}
