package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class StartEvent {
}

class EndEvent {
}

class OtherEvent {
	private int id;
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class NoOverLappingContextTest3 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			System.out.println("Class:" + event.getUnderlying().getClass().getName() + ", id:" + event.get("id")
					+ ", price:" + event.get("price"));
		}
	}
}

class NoOverLappingContextTest2 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			System.out.println("context.id:" + event.get("id"));
		}
	}
}

public class NoOverLappingContextTest {
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String start = StartEvent.class.getName();
		String end = EndEvent.class.getName();
		String other = OtherEvent.class.getName();
		
		String epl1 = "create context NoOverLapping start " + start + " end " + end;
		String epl11 = "context NoOverLapping select context.id from " + start;
		String epl2 = "context NoOverLapping select * from " + other + " output snapshot";

		admin.createEPL(epl1);

		EPStatement state = admin.createEPL(epl2);
		state.addListener(new NoOverLappingContextTest3());

		EPStatement state1 = admin.createEPL(epl11);
		state1.addListener(new NoOverLappingContextTest2());

		OtherEvent o0 = new OtherEvent();
		o0.setId(1);
		o0.setPrice(20);
		System.out.println("sendEvent: OtherEvent id = 0 price = 20 ");
		runtime.sendEvent(o0);

		StartEvent s = new StartEvent();
		System.out.println("sendEvent: StartEvent+++++1+++++");
		runtime.sendEvent(s);

		OtherEvent o = new OtherEvent();
		o.setId(2);
		o.setPrice(20);
		System.out.println("sendEvent: OtherEvent id = 2 price = 20  ");
		runtime.sendEvent(o);

		EndEvent e = new EndEvent();
		System.out.println("sendEvent: EndEvent-------1-----");
		runtime.sendEvent(e);

		OtherEvent o2 = new OtherEvent();
		o2.setId(4);
		o2.setPrice(20);
		System.out.println("sendEvent: OtherEvent id = 4 price = 20  ");
		runtime.sendEvent(o2);

		StartEvent s2 = new StartEvent();
		System.out.println("sendEvent: StartEvent+++++2+++++");
		runtime.sendEvent(s2);

		OtherEvent o3 = new OtherEvent();
		o3.setId(5);
		o3.setPrice(15);
		System.out.println("sendEvent: OtherEvent id = 5 price = 15  ");
		runtime.sendEvent(o3);


		StartEvent s3 = new StartEvent();
		System.out.println("sendEvent: StartEvent+++++3+++++");
		runtime.sendEvent(s3);

		OtherEvent o5 = new OtherEvent();
		o5.setId(7);
		o5.setPrice(17);
		System.out.println("sendEvent: OtherEvent id = 7 price = 17  ");
		runtime.sendEvent(o5);


		EndEvent e3 = new EndEvent();
		System.out.println("sendEvent: EndEvent-------3-----");
		runtime.sendEvent(e3);
	}
}