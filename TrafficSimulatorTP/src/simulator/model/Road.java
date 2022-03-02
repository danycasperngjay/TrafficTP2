package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Road extends SimulatedObject {

    protected Junction sourceJunction;
    protected Junction destinationJunction;
    protected int length;
    protected int maximumSpeed;
    protected int currentSpeedLimit;
    protected int contaminationAlarmLimit;
    protected Weather weatherConditions;
    protected int totalContamination;
    private List<Vehicle> vehicles;

    Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
        super(id);
        if (srcJunc == null){
        	throw new IllegalArgumentException ("Source Junction is null");
        } else {
            //srcJunc.addOutgoingRoad(this);
            this.sourceJunction = srcJunc;
        }
        if (destJunc == null){
        	throw new IllegalArgumentException ("Destination Junction is null");
        } else {
            //destJunc.addIncomingRoad(this);
            this.destinationJunction = destJunc;
        }
        if (length <= 0){
        	throw new IllegalArgumentException ("Length is negative");
        } else {
            this.length = length;
        }
        if (maxSpeed <= 0){
        	throw new IllegalArgumentException ("Maximum speed is negative");
        } else {
            this.maximumSpeed = maxSpeed;
        }
        if(contLimit < 0){
        	throw new IllegalArgumentException ("Contamination Alarm Limit negative or equals to zero");
        } else {
            this.contaminationAlarmLimit = contLimit;
        }
        if(weather == null){
        	throw new IllegalArgumentException ("Weather invalid");
        } else {
            this.weatherConditions = weather;
        }
        this.currentSpeedLimit = this.maximumSpeed;
        this.totalContamination = 0;
        this.vehicles = new ArrayList<Vehicle>();
        sourceJunction.addOutgoingRoad(this);
        destinationJunction.addIncomingRoad(this);
    }

    void enter(Vehicle v){
        if (v.getLocation() != 0 || v.getSpeed() != 0){
        	throw new IllegalArgumentException ("Error : Couldn't add the vehicle to the road");
        } else {
            this.vehicles.add(v);
        } 
    }

    void exit(Vehicle v){
        this.vehicles.remove(v);
    }

    void setWeather(Weather w){
        if(w == null){
        	throw new IllegalArgumentException ("Weather invalid");
        } else {
            this.weatherConditions = w;
        }
    }

    void addContamination(int c){
        if(c < 0){
        	throw new IllegalArgumentException ("Contamination is negative");
        } else {
            this.totalContamination += c;
        }
    }

    abstract void reduceTotalContamination();

    abstract void updateSpeedLimit();

    abstract int calculateVehicleSpeed(Vehicle v);

    @Override
    void advance(int time) {
        reduceTotalContamination();
        updateSpeedLimit();
        for (Vehicle vehicle : this.vehicles) {
            vehicle.setSpeed(calculateVehicleSpeed(vehicle));
            vehicle.advance(time);
        }
        this.vehicles.stream().sorted(Comparator.comparing(Vehicle :: getLocation, Comparator.reverseOrder())).collect(Collectors.toList()); // Sort List 
    }

    @Override
    public JSONObject report() {
        JSONObject jo = new JSONObject();

        jo.put("id", this._id);
        jo.put("speedlimit", this.currentSpeedLimit);
        jo.put("weather", this.weatherConditions);
        jo.put("co2", this.totalContamination);

        List<Vehicle> vehiclesTraveling = new ArrayList<>();
        for(Vehicle v : this.vehicles){
            if(v.getStatus() == VehicleStatus.TRAVELING){
                vehiclesTraveling.add(v);
            }
        }
        jo.put("vehicles", vehiclesTraveling);

        return jo;

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

}
