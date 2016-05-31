package Esper.unit.EventType;

public class SensorData {
	private String id;
	private String timestamp;
	private double value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "SensorData [id=" + id + ", timestamp=" + timestamp + ", value=" + value + "]";
	}
	
}
