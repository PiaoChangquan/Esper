package Esper.chap5;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import Esper.unit.Listener.AggergationListener;

class Rectangle
{
	private int width;
	private int length;

	


public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "Rectangle [width=" + width + ", length=" + length + "]";
	}

}


public class SelectTest {

	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		admin.getConfiguration().addEventType("Rectangle", Rectangle.class.getName());
//		String insertEPL = "insert rstream into Computer(cid,csize) select id,size from " + Rectangle + ".win:length(1)";
//		String insertSelectEPL = "select * from Computer";
		
		//Epl: Select add expression 
		String SelectEPL = "select length * width as area, width as w, length as l from  Rectangle";

		EPStatement stateSelectEPL = admin.createEPL(SelectEPL);
		stateSelectEPL.addListener(new AggergationListener());

		EPRuntime runtime = epService.getEPRuntime();

		Rectangle Rectangle1 = new Rectangle();
		Rectangle1.setWidth(2);
		Rectangle1.setLength(3);
		System.out.println("Send Rectangle: " + Rectangle1);
		runtime.sendEvent(Rectangle1);

		Rectangle Rectangle2 = new Rectangle();
		Rectangle2.setWidth(2);
		Rectangle2.setLength(1);
		System.out.println("Send Rectangle: " + Rectangle2);
		runtime.sendEvent(Rectangle2);

		Rectangle Rectangle3 = new Rectangle();
		Rectangle3.setWidth(3);
		Rectangle3.setLength(3);
		System.out.println("Send Rectangle: " + Rectangle3);
		runtime.sendEvent(Rectangle3);

		Rectangle Rectangle4 = new Rectangle();
		Rectangle4.setWidth(4);
		Rectangle4.setLength(4);
		System.out.println("Send Rectangle: " + Rectangle4);
		runtime.sendEvent(Rectangle4);

		Rectangle Rectangle5 = new Rectangle();
		Rectangle5.setWidth(5);
		Rectangle5.setLength(3);
		System.out.println("Send Rectangle: " + Rectangle5);
		runtime.sendEvent(Rectangle5);

		Rectangle Rectangle6 = new Rectangle();
		Rectangle6.setWidth(6);
		Rectangle6.setLength(4);
		System.out.println("Send Rectangle: " + Rectangle6);
		runtime.sendEvent(Rectangle6);
	}
}