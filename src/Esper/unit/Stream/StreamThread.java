package Esper.unit.Stream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import Esper.unit.EventType.SensorData;

public class StreamThread implements Runnable{
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Random random = new Random();
	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
	EPRuntime runtime = epService.getEPRuntime();
	private String id;
	
	public StreamThread(String id){
		this.id =id;
	}
	public void run() {
		for (int i = 0; i < 100; i++) {
			String timestamp = dateFormat.format(new Date());// timestamp생성
			int value = random.nextInt(50);// value 생성 0-50
			
			SensorData Sensor = new SensorData();
			Sensor.setId(id);
			Sensor.setTimestamp(timestamp);
			Sensor.setValue(value);
			runtime.sendEvent(Sensor);
			System.out.println(Sensor);
			
			
			int delay = Math.abs(random.nextInt() % 3);// 0-2
			try {
				Thread.sleep(delay * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

