package Esper.chap6;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class TestListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               ==" + "idï¼? "
						+ event.get("id") + ", timestampï¼? " + event.get("timestamp") + ", valueï¼? " + event.get("value")
						+ "    ==");
			}
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
		}
		if (oldEvents != null) {
			for (int i = 0; i < oldEvents.length; i++) {
				EventBean event = oldEvents[i];
				System.out.println("=========================OLD===========================================" + "idï¼? "
						+ event.get("id") + ", timestampï¼? " + event.get("timestamp") + ", valueï¼? "
						+ event.get("value"));
			}

		}
	}
}

class MuiltListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//

			System.out.println(
					"=~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~=");
			for (int i = 0; i < newEvents.length; i++) {
				String timestamp2 = dateFormat.format(new Date());
				System.out.println("=t.value: " + newEvents[i].get("t.value") + "  t.timestamp : "
						+ newEvents[i].get("t.timestamp") + "  h.value: " + newEvents[i].get("h.value")
						+ "  h.timestamp : " + newEvents[i].get("h.timestamp") + " =" + "   [nowtime is: " + timestamp2
						+ "]");
			}
		}
		System.out.println(
				"=~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~=");
	}
}

class MatchListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               ==" + " A_idï¼? "
						+ event.get("a_id") + ", A_valueï¼? " + event.get("a_value") + " B_idï¼? " + event.get("b_id")
						+ ", B_valueï¼? " + event.get("b_value") + " C_idï¼? " + event.get("c_id") + ", C_valueï¼? "
						+ event.get("c_value"));

			}
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
		}
		if (oldEvents != null) {
			for (int i = 0; i < oldEvents.length; i++) {
				EventBean event = oldEvents[i];
				System.out.println("=========================OLD===========================================" + " A_idï¼? "
						+ event.get("a_id") + ", A_valueï¼? " + event.get("a_value") + " B_idï¼? " + event.get("b_id")
						+ ", B_valueï¼? " + event.get("b_value"));
			}

		}
	}
}

class MatchListener1 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               ==" + "  A_valueï¼? "
						+ event.get("a_value") + ", B_valueï¼? " + event.get("b_value") + ", C_valueï¼? "
						+ event.get("c_value"));

			}
			System.out.println("==                                                               ="
					+ "==---------------------------------------------------------------=");
		}
	}
}

class PatternFollowedListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==                                                               ="
					+ "==--------------------------------------------------------------------------------------------=");

			for (int i = 0; i < newEvents.length; i++) {
				EventBean event = newEvents[i];
				System.out.println("==                                                               =="
				+ " A value : "	+ event.get("a.value") + " timestamp : " + event.get("a.timestamp") 
				+ " B value : "	+ event.get("b.value") + " timestamp : " + event.get("b.timestamp"));
			}
			System.out.println("==                                                               ="
					+ "==--------------------------------------------------------------------------------------------=");

		}
	}
}

public class EPLtest {
	public static void main(String[] args) throws InterruptedException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//

		Random random = new Random();

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String temp = TempSensor.class.getName();
		String humidity = HumiditySensor.class.getName();

		 String epl = "select * from " + temp + ".win:time_batch(3) output snapshot";
		// String epl1 = "select * from " + humidity + ".win:time_batch(3)
		// output snapshot";
		 EPStatement state1 = admin.createEPL(epl);
		// EPStatement state2 = admin.createEPL(epl1);
		 state1.addListener(new TestListener());
//		 state2.addListener(new TestListener());
		//

		// =================================Mutiple Stream test=====================================
		// String eplMutipleStream = "select t.id, t.timestamp, t.value, h.id,
		// h.timestamp, h.value from " + temp
		// + ".win:time_batch(3) as t, " + humidity + ".win:time_batch(3) as h
		// where t.value = h.value ";
		//
		// EPStatement stateMutipleStream = admin.createEPL(eplMutipleStream);
		// stateMutipleStream.addListener(new MuiltListener());
		// =================================Mutiple Stream test=====================================

		// =================================Order BY test=====================================
		// String eplOrderBy = "select * from " + temp + ".win:length_batch(4)
		// order by value";
		// EPStatement stateOrderBy = admin.createEPL(eplOrderBy);
		// stateOrderBy.addListener(new TestListener());
		// =================================Order BY test=====================================

		// =================================match_recognize test=====================================
//		String EplMatch = "select irstream * from " + temp + " match_recognize ( " + " partition by id   "
//				+ "measures A.id as a_id, B.id as b_id, A.value as  a_value, B.value as b_value " + "pattern (A B) "
//				+ "define C as Math.abs(B.value - A.value) >= 10 " + ") ";
//
////		String EplMatch1 = "select irstream * from " + temp + " match_recognize ( " + " partition by id   "
////				+ " measures A.value as  a_value, B.value as b_value, C.value as  c_value" + " pattern (A (B | C)) "
////				+ " define" + " A as A.value >= 50," + " B as B.value <= 45,"
////				+ " C as Math.abs(C.value - A.value) >= 10" + " ) ";
//
//		 EPStatement stateEplMatch = admin.createEPL(EplMatch);
//		 stateEplMatch.addListener(new MatchListener1());

		// =================================match_recognize test=====================================
		
		// =================================pattern test=====================================
		
//		String EplPattern = "select * from pattern @SuppressOverlappingMatches"
//				+ "[every a=" + temp + " -> b=" + humidity + "(value < a.value)]";

		String EplPattern = "select * from pattern @DiscardPartialsOnMatch"
				+ "[every a=" + temp + " -> b=" + humidity + "(value < a.value)]";
		
//		String EplPattern = "select * from pattern @SuppressOverlappingMatches "  @DiscardPartialsOnMatch
//				+ "[every a=" + temp + " -> b=" + humidity + "(value > a.value)]";
//		EPStatement stateEplPattern = admin.createEPL(EplPattern);
//		stateEplPattern.addListener(new PatternFollowedListener());
		// =================================pattern test=====================================
			
		
		

		for (int i = 0; i < 100; i++) {
			String timestamp = dateFormat.format(new Date());// timestampìƒì„±
			int s = random.nextInt(100);// value ìƒì„± 0-100

			TempSensor Temp = new TempSensor();
			Temp.setId("Temp");
			Temp.setTimestamp(timestamp.toString());
			Temp.setValue(s);
			runtime.sendEvent(Temp);
			System.out.println(Temp);

			int delay = Math.abs(random.nextInt() % 3);// 0-2
			Thread.sleep(delay * 1000);
			if (delay == 2) {
				continue;
			}
			// delayì‹œê°„ 2ë•? humidity sensor dateë¥? ì•ˆë°›ì••ë‹ˆë‹?.

			String timestamp1 = dateFormat.format(new Date());
			int s1 = random.nextInt(50);// 0-50

			HumiditySensor Humidity = new HumiditySensor();
			Humidity.setId("Humidity");
			Humidity.setTimestamp(timestamp1.toString());
			Humidity.setValue(s1);
			runtime.sendEvent(Humidity);
			System.out.println(Humidity);

			int delay1 = Math.abs(random.nextInt() % 2);
			Thread.sleep(delay1 * 1000);
		}

	}
}
