package Esper.chap10;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.AggergationListener;
import Esper.unit.Stream.StreamThread;

public class Function {
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
//+++++++++++++++++++++Single-row Function+++++++++++++++++++++++//
//		// Epl: cast
//		String cast = "select cast( timestamp, date, dateformat: 'yyyy-MM-dd HH:mm:ss'), id,timestamp from tempSensor";
//		String cast = "select cast(value, int), id, value from tempSensor";
//		EPStatement statecast = admin.createEPL(cast);
//		statecast.addListener(new AggergationListener());
		
		// Epl: exists
//		String exists = "select exists(id?) , id,timestamp from tempSensor";
//		EPStatement stateexists = admin.createEPL(exists);
//		stateexists.addListener(new AggergationListener());
		

////		Epl: current_timestamp
//		String current_timestamp = "select current_timestamp() , id,timestamp from tempSensor";
//		EPStatement statecurrent_timestamp = admin.createEPL(current_timestamp);
//		statecurrent_timestamp.addListener(new AggergationListener());
//		
//		Epl: instanceof
//		String instanceofEPL = "select instanceof(value?,String,int,double) , id,timestamp from tempSensor";
//		EPStatement stateinstanceof = admin.createEPL(instanceofEPL);
//		stateinstanceof.addListener(new AggergationListener());
//	
//		// Epl: istream
//		String istream = "select irstream *, istream() from tempSensor.win:time(3 sec)";
//		EPStatement stateistream = admin.createEPL(istream);
//		stateistream.addListener(new AggergationListener());
		
//		// Epl: min
//		String min = "select min(value) from tempSensor.win:length_batch(3)";
//		EPStatement statemin = admin.createEPL(min);
//		statemin.addListener(new AggergationListener());
		
//		// Epl: case
//		String caseEPL = "select case when value>10 then true else false end, id, value from tempSensor";
//		EPStatement statecaseEPL = admin.createEPL(caseEPL);
//		statecaseEPL.addListener(new AggergationListener());
//		
		
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
//		String prevcount = "select id, value, prevcount(value) from Sensor.std:groupwin(id).win:length(4)";
		String prevcount = "select prevcount(S) from Sensor.win:time(0.01 hour) as S";
		EPStatement stateprevcount = admin.createEPL(prevcount);
		stateprevcount.addListener(new AggergationListener());
		
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

		//+++++++++++++++++++++Single-row Function+++++++++++++++++++++++//

		//+++++++++++++++++++++Aggregate Function+++++++++++++++++++++++//
		//EPL: avg and count
//		String avg_count = "select avg(value),count(distinct value),count(value, value>10), "
//		 		+ "value from  tempSensor.win:length_batch(6)";
//		EPStatement stateavg_count = admin.createEPL(avg_count);
//		stateavg_count.addListener(new AggergationListener());
		//EPL: max_fmax_min_fmin
//		String max_fmax_min_fmin = "select max(value), fmax(value, value<40),"
//		 		+ "min(distinct value), fmin(value, value>40), "
//		 		+ "value from tempSensor.win:length_batch(6)";
//		EPStatement statemax_fmax_min_fmin = admin.createEPL(max_fmax_min_fmin);
//		statemax_fmax_min_fmin.addListener(new AggergationListener());
		
		//EPL: sum_median
//		String sum_median = "select sum(value),sum(distinct value, value<20), "
//				+ "median(value), value from tempSensor.win:length_batch(6)";
///		EPStatement statesum_median = admin.createEPL(sum_median);
//		statesum_median.addListener(new AggergationListener());
		
		//EPL: first_last
//		String first_last = "select first(value),last(value), value "
//				+ "from tempSensor.win:length_batch(3)";
//		EPStatement statefirst_last = admin.createEPL(first_last);
//		statefirst_last.addListener(new AggergationListener());

//		//EPL: maxby_maxbyever
//		String maxby_maxbyever = "select maxby(value),maxbyever(value), value from tempSensor.win:length_batch(3)";
//		EPStatement statemaxby_maxbyever = admin.createEPL(maxby_maxbyever);
//		statemaxby_maxbyever.addListener(new AggergationListener());
		
		//EPL: minby_minbyever
//		String minby_minbyever = "select minby(value),minbyever(value), value from tempSensor.win:length_batch(3)";
//		EPStatement stateminby_minbyever = admin.createEPL(minby_minbyever);
//		stateminby_minbyever.addListener(new AggergationListener());
		
		//EPL: avedev_stddev
//		String avedev_stddev = "select avedev(value),stddev(value) from tempSensor.win:length_batch(2) ";
//		EPStatement stateavedev_stddev = admin.createEPL(avedev_stddev);
//		stateavedev_stddev.addListener(new AggergationListener());	
		
		//EPL: typeof
//		String typeof = "select typeof(value) from tempSensor";
//		EPStatement statetypeof = admin.createEPL(typeof);
//		statetypeof.addListener(new AggergationListener());	
		//+++++++++++++++++++++Aggregate Function+++++++++++++++++++++++//
		
		
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
