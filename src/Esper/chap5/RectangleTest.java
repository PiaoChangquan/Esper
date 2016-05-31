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


public class RectangleTest {

	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String Rectangle = Rectangle.class.getName();
//		String insertEPL = "insert rstream into Computer(cid,csize) select id,size from " + Rectangle + ".win:length(1)";
//		String insertSelectEPL = "select * from Computer";
		
		
		String insertEPL = "select length * width as area from " + Rectangle;

		EPStatement state1 = admin.createEPL(insertEPL);
//		EPStatement state1 = admin.createEPL(insertSelectEPL);
		state1.addListener(new AggergationListener());

		EPRuntime runtime = epService.getEPRuntime();

		Rectangle apple1 = new Rectangle();
		apple1.setWidth(2);
		apple1.setLength(3);
		System.out.println("Send Rectangle: " + apple1);
		runtime.sendEvent(apple1);

		Rectangle apple2 = new Rectangle();
		apple2.setWidth(2);
		apple2.setLength(1);
		System.out.println("Send Rectangle: " + apple2);
		runtime.sendEvent(apple2);

		Rectangle apple3 = new Rectangle();
		apple3.setWidth(3);
		apple3.setLength(3);
		System.out.println("Send Rectangle: " + apple3);
		runtime.sendEvent(apple3);

		Rectangle apple4 = new Rectangle();
		apple4.setWidth(4);
		apple4.setLength(4);
		System.out.println("Send Rectangle: " + apple4);
		runtime.sendEvent(apple4);

		Rectangle apple5 = new Rectangle();
		apple5.setWidth(5);
		apple5.setLength(3);
		System.out.println("Send Rectangle: " + apple5);
		runtime.sendEvent(apple5);

		Rectangle apple6 = new Rectangle();
		apple6.setWidth(6);
		apple6.setLength(4);
		System.out.println("Send Rectangle: " + apple6);
		runtime.sendEvent(apple6);
	}
}