package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class InitialEvent{}

class TerminateEvent{}

class SomeEvent
{
	private int id;
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}

class OverLappingContextListener implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			EventBean event = newEvents[0];
			System.out.println("context.id:" + event.get("id") + ", id:" + event.get("id"));
		}
	}
}

class OverLappingContextListener2 implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			EventBean event = newEvents[0];
			System.out.println("Class:" + event.getUnderlying().getClass().getName() + ", id:" + event.get("id")+", price:"+event.get("price"));
		}
	}
}

public class OverLappingContextTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String initial = InitialEvent.class.getName();
		String terminate = TerminateEvent.class.getName();
		String some = SomeEvent.class.getName();
		
		String epl1 = "create context OverLapping initiated " + initial + " terminated " + terminate;
		String epl2 = "context OverLapping select context.id from " + initial;
		String epl3 = "context OverLapping select * from " + some+" output snapshot";

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new OverLappingContextListener());
		
		EPStatement state1 = admin.createEPL(epl3);
		state1.addListener(new OverLappingContextListener2());

		
		
		SomeEvent s0 = new SomeEvent();
		s0.setId(0);
		s0.setPrice(20);
		System.out.println("sendEvent: SomeEvent   id = 0 price = 20");
		runtime.sendEvent(s0);
		
		InitialEvent i = new InitialEvent();
		System.out.println("sendEvent: InitialEvent +++++1++++");
		runtime.sendEvent(i);

		SomeEvent s = new SomeEvent();
		s.setId(2);
		s.setPrice(20);
		System.out.println("sendEvent: SomeEvent   id = 2 price = 20");
		runtime.sendEvent(s);

		TerminateEvent t = new TerminateEvent();
		System.out.println("sendEvent: TerminateEvent ------1----");
		runtime.sendEvent(t);
		
		
		SomeEvent s2 = new SomeEvent();
		s2.setId(4);
		s2.setPrice(20);
		System.out.println("sendEvent: SomeEvent    id = 4 price = 20");
		runtime.sendEvent(s2);
		
		InitialEvent i2 = new InitialEvent();
		System.out.println("sendEvent: InitialEvent +++++++2+++++");
		runtime.sendEvent(i2);


		SomeEvent s3 = new SomeEvent();
		s3.setId(5);
		s3.setPrice(15);
		System.out.println("sendEvent: SomeEvent    id = 5 price = 15");
		runtime.sendEvent(s3);

		
		
		InitialEvent i3 = new InitialEvent();
		System.out.println("sendEvent: InitialEvent +++++++3+++++");
		runtime.sendEvent(i3);
		
		SomeEvent s4 = new SomeEvent();
		s4.setId(7);
		s4.setPrice(17);
		System.out.println("sendEvent: SomeEvent    id = 7 price = 17");
		runtime.sendEvent(s4);
		
		
		TerminateEvent t1 = new TerminateEvent();
		System.out.println("sendEvent: TerminateEvent ------2----");
		runtime.sendEvent(t1);
	}
}