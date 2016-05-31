package Esper.chap3.Listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AggergationEPLListener implements UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		// TODO Auto-generated method stub
		for(EventBean bean:newEvents){
			System.out.println("Received Events: "+bean.getUnderlying());
			
		}
	}

}
