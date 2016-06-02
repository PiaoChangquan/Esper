package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import Esper.unit.Listener.AggergationListener;

class ESB3 {
	private int id;
	private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

public class CategoryContext {
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String esb = ESB3.class.getName();
		String epl1 = "create context esbtest group by id<0 as low, group by id>0 and "
				+ "id<10 as middle,group by id>10 as high from " + esb;
		String epl2 = "context esbtest select context.id,context.name," + "context.label, price from " + esb;

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new AggergationListener());
		

		ESB3 e1 = new ESB3();
		e1.setId(1);
		e1.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e1);

		ESB3 e2 = new ESB3();
		e2.setId(0);
		e2.setPrice(30);
		System.out.println("sendEvent: id=0, price=30");
		runtime.sendEvent(e2);

		ESB3 e3 = new ESB3();
		e3.setId(11);
		e3.setPrice(20);
		System.out.println("sendEvent: id=11, price=20");
		runtime.sendEvent(e3);

		ESB3 e4 = new ESB3();
		e4.setId(-1);
		e4.setPrice(40);
		System.out.println("sendEvent: id=-1, price=40");
		runtime.sendEvent(e4);
	}
}