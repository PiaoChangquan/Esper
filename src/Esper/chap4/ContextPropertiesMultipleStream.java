package Esper.chap4;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import Esper.unit.Listener.AggergationListener;

class Student
{

	private int id;
	private String name;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	

}
class Teacher{
	private int tid;
	private String name;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}



public class ContextPropertiesMultipleStream
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String student = Student.class.getName();
		String teacher = Teacher.class.getName();
		String epl1 = "create context MultiTest partition by id from " + student+", tid from "+teacher;																							// context.id针对不同的Student的id,price建立�?个context，如果事件的id和price相同，则context.id也相同，即表明事件进入了同一个context
		String epl2 = "context MultiTest select context.id, context.name, context.key1 from " + student;
		String epl3 = "context MultiTest select context.id, context.name, context.key1 from " + teacher;

	
		
		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new AggergationListener());
		EPStatement state1 = admin.createEPL(epl3);
		state1.addListener(new AggergationListener());
		
		Student e1 = new Student();
		e1.setId(10);
		e1.setName("james");
		System.out.println("sendEvent: id=10, name=james");
		runtime.sendEvent(e1);

		Teacher t1 = new Teacher();
		t1.setTid(10);
		t1.setName("jake");
		System.out.println("sendEvent: tid=10, name=jake");
		runtime.sendEvent(t1);

		Student e2 = new Student();
		e2.setId(32);
		e2.setName("Tom");
		System.out.println("sendEvent: id=32, name=Tom");
		runtime.sendEvent(e2);

		Teacher t2 = new Teacher();
		t2.setTid(32);
		t2.setName("Park");
		System.out.println("sendEvent: tid=32, name=Park");
		runtime.sendEvent(t2);
		
		Student e3 = new Student();
		e3.setId(21);
		e3.setName("jiang");
		System.out.println("sendEvent: id=21, name=jiang");
		runtime.sendEvent(e3);

		Student e4 = new Student();
		e4.setId(21);
		e4.setName("Jhon");
		System.out.println("sendEvent: id=21, name=Jhon");
		runtime.sendEvent(e4);
		
	
	}
}