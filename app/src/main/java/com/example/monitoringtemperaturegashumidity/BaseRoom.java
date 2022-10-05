package com.example.monitoringtemperaturegashumidity;

public class BaseRoom {
    //Bien thanh phanh up len firebase
    private String Temperature;
    private String Humidity;
    private String Gas;
    private String Fan;
    private String Fan1;
    private String Mist;
    private String Name = "";

    public BaseRoom(String fan, String fan1, String gas, String humidity, String mist, String temperature, String name) {
        Fan = fan;
        Fan1 = fan1;
        Gas = gas;
        Humidity = humidity;
        Mist = mist;
        Temperature = temperature;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        this.Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        this.Humidity = humidity;
    }

    public String getGas() {
        return Gas;
    }

    public void setGas(String gas) {
        this.Gas = gas;
    }

    public String getFan() {
        return Fan;
    }

    public void setFan(String fan) {
        this.Fan = fan;
    }

    public String getFan1() {
        return Fan1;
    }

    public void setFan1(String fan1) {
        this.Fan1 = fan1;
    }

    public String getMist() {
        return Mist;
    }

    public void setMist(String mist) {
        this.Mist = mist;
    }

    public BaseRoom() {}

    public String toString() {
        return this.Name;
    }
}
