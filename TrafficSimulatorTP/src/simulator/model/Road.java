package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Road extends SimulatedObject {

    private Junction sourceJunction;
    private Junction destinationJunction;
    private int length;
    private int maximumSpeed;
    private int currentSpeedLimit;
    private int contaminationAlarmLimit;
    private Weather weatherConditions;
    private int totalContamination;
    private List<Vehicle> vehicles;

    Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception{
        super(id);
        //NOT SURE : "The constructor should add the road as an incoming road to its destination junction, and
        //as an outgoing road of its source junction" dk if i should do anything extra
        if (srcJunc == null){
            throw new Exception ("Source Junction is null");
        } else {
            this.sourceJunction = srcJunc;
        }
        if (destJunc == null){
            throw new Exception ("Destination Junction is null");
        } else {
            this.destinationJunction = destJunc;
        }
        if (length <= 0){
            throw new Exception("Length is negative");
        } else {
            this.length = length;
        }
        if (maxSpeed <= 0){
            throw new Exception("Maximum speed is negative");
        } else {
            this.maximumSpeed = maxSpeed;
        }
        if(contLimit < 0){
            throw new Exception("Contamination Alarm Limit negative or equals to zero");
        } else {
            this.contaminationAlarmLimit = contLimit;
        }
        if(weather == null){
            throw new Exception("Weather invalid");
        } else {
            this.weatherConditions = weather;
        }
        this.currentSpeedLimit = this.maximumSpeed;
        this.totalContamination = 0;
        this.vehicles = new ArrayList<>();
    }

    void enter(Vehicle v) throws Exception{
        if (v.getLocation() != 0 || v.getSpeed() != 0){
            throw new Exception("Error : Couldn't add the vehicle to the road");
        } else {
            this.vehicles.add(v);
        }
    }

    void exit(Vehicle v){
        this.vehicles.remove(v);
    }

    void setWeather(Weather w) throws Exception{
        if(w == null){
            throw new Exception ("Weather invalid");
        } else {
            this.weatherConditions = w;
        }
    }

    void addContamination(int c) throws Exception{
        if(c < 0){
            throw new Exception("Contamination is negative");
        } else {
            this.totalContamination += c;
        }
    }

    abstract void reduceTotalContamination() throws Exception;

    abstract void updateSpeedLimit();

    abstract int calculateVehicleSpeed(Vehicle v);

    //NOT FINISHED
    @Override
    void advance(int time) throws Exception {
        reduceTotalContamination();
        updateSpeedLimit();
        for (Vehicle vehicle : this.vehicles) {
            calculateVehicleSpeed(vehicle);
            vehicle.advance(time);
        }
        //WRONG : have to sort by location descending order -> will fix
       this.vehicles.getLocation();
    }

    //NOT DONE
    @Override
    public JSONObject report() {
        return null;
    }

    //Public getters
    public int getLength() {
        return length;
    }

    public Junction getDest(){
        return destinationJunction;
    }

    public Junction getSrc(){
        return sourceJunction;
    }

    public Weather getWeather(){
        return weatherConditions;
    }

    public int getContLimit(){
        return contaminationAlarmLimit;
    }

    public int getMaxSpeed(){
        return maximumSpeed;
    }

    public int getTotalCO2(){
        return totalContamination;
    }

    public int getSpeedLimit(){
        return currentSpeedLimit;
    }

    public List<Vehicle> getVehicles(){
        return Collections.unmodifiableList(vehicles);
    }

    //additional private setters?

}
