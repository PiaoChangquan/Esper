package Esper.chap10;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Stream.StreamThread;

public class function {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// add EventType
		String Sensor = SensorData.class.getName();
		admin.getConfiguration().addEventType("Sensor", Sensor);

		// Sensor Stream divide into 3 different Stream
		String eplforTemp = "insert into tempSensor select * from  Sensor(id='Temp') ";
		String eplforHumidity = "insert into humiditySensor select * from   Sensor(id='Humidity') ";
		String eplforLight = "insert into lightSensor select * from  Sensor(id='Light') ";
		admin.createEPL(eplforTemp);
		admin.createEPL(eplforHumidity);
		admin.createEPL(eplforLight);

//		// Epl: cast
//		String cast = "select cast('20100502', date, dateformat: 'yyyyMMdd') from Sensor";
//		EPStatement statecast = admin.createEPL(cast);
//		statecast.addListener(new AggergationListener());
		
//		// Epl: istream
//		String istream = "select irstream *, istream() from tempSensor.win:time(3 sec)";
//		EPStatement stateistream = admin.createEPL(istream);
//		stateistream.addListener(new AggergationListener());
		
//		select cast('20100502', date, dateformat: 'yyyyMMdd') from OrderEvent
		// Epl: current_evaluation_context()
//		String current_evaluation_context = "select "
//				+ "current_evaluation_context().getEngineURI() as engineURI,* from Sensor";
//		EPStatement statecurrent_evaluation_context = admin.createEPL(current_evaluation_context);
//		statecurrent_evaluation_context.addListener(new AggergationListener());
		
		// Epl:coalesce
//		String coalesce = "select coalesce(null, 'foo') from Sensor";
//		EPStatement statecoalesce = admin.createEPL(coalesce);
//		statecoalesce.addListener(new AggergationListener());
		
		// Epl:Previous
//		String Previous = "select prev(2, value),* from Sensor.win:length(10)";
//		String Previous = "select prev(0, value), prev(1, value), prev(2, value)"
//				+ "	from Sensor.ext:sort(3, value desc)";
//		
//		EPStatement statePrevious = admin.createEPL(Previous);
//		statePrevious.addListener(new AggergationListener());
		
		// Epl: prevtail
//		String prevtail = "select prevtail(value),* from Sensor.win:length(3)";
//		String prevtail = "select prevtail(0, value), prevtail(1, value), prevtail(2, value)"
//		+ "	from Sensor.ext:sort(3, value desc)";
//		String prevtail = "select prevtail(0, S) from Sensor.win:length(3) as S";
//		EPStatement stateprevtail = admin.createEPL(prevtail);
//		stateprevtail.addListener(new AggergationListener());
		
		// Epl: prevwindow
//		String prevwindow = "select prevwindow(S) from Sensor.win:length(3) as S";
//		EPStatement stateprevwindow = admin.createEPL(prevwindow);
//		stateprevwindow.addListener(new AggergationListener());
		
//		 Epl: prevcount
//		String prevcount = "select prevcount(value) from Sensor.win:length(4) ";
//		String prevcount = "select prevcount(S) from Sensor.win:length(4) as S";
//		String prevcount = "select id, value, prevcount(value) from Sensor.std:groupwin(id).win:length(4)";
//		EPStatement stateprevcount = admin.createEPL(prevcount);
//		stateprevcount.addListener(new AggergationListener());
		
		// Epl: prior
//		String prior = "select prior(1,S) from Sensor as S";
//		String prior = "select prior(2,value) from Sensor";
//		EPStatement stateprior = admin.createEPL(prior);
//		stateprior.addListener(new AggergationListener());

		// Epl: window
//		String window = "select window(*) from Sensor.win:time(10 sec)";
//		EPStatement statewindow = admin.createEPL(window);
//		statewindow.addListener(new AggergationListener());
	
//		// Epl: sorted
//		String sorted = "select sorted(value),value,id from Sensor.win:length_batch(10) as s group by id";
//		EPStatement statesorted = admin.createEPL(sorted);
//		statesorted.addListener(new AggergationListener());
		
		
		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
//		l.start();
//		h.start();
	}
}
