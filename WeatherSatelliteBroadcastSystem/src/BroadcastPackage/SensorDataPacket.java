// Leo Zobel
// 8/8/2023

package BroadcastPackage;

import java.nio.ByteBuffer;
import java.util.Random;

public class SensorDataPacket {
    private float temperature;
    private float humidity;
    private float pressure;

    // Convert the SensorDataPacket to bytes
    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES * 3);
        buffer.putFloat(temperature);
        buffer.putFloat(humidity);
        buffer.putFloat(pressure);
        return buffer.array();
    }

    // Create a SensorDataPacket from bytes
    public static SensorDataPacket fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        float temperature = buffer.getFloat();
        float humidity = buffer.getFloat();
        float pressure = buffer.getFloat();
        return new SensorDataPacket(temperature, humidity, pressure);
    }

    // Constructors
    SensorDataPacket() {
        generateRandomData();
    }

    SensorDataPacket(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    // Generate random float within a range
    private float generateRandomFloat(float min, float max) {
        Random r = new Random();
        return min + (max - min) * r.nextFloat();
    }

    // Generate random sensor data
    private void generateRandomData() {
        this.temperature = generateRandomFloat(60, 86);
        this.humidity = generateRandomFloat(60, 92);
        this.pressure = generateRandomFloat(30, 31);
    }

    // Getters and setters
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}
