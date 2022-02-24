package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject{

    private List<Junction> itinerary;
    private int maximumSpeed;
    private int currentSpeed;
    private VehicleStatus status;
    private Road road = null;
    private int location;
    private int contaminationClass;
    private int totalContamination;
    private int totalTraveledDistance;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws Exception {
        super(id);
        if(id == null && id.isEmpty()) {
            throw new Exception("ID invalid");
        }
        if (itinerary.size() < 2){
            throw new Exception("Itinerary is too small");
        } else {
            this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        }
        if (maxSpeed <= 0){
            throw new Exception("Max speed is negative");
        } else {
            this.maximumSpeed = maxSpeed;
        }
        this.currentSpeed = 0;
        this.status = VehicleStatus.PENDING;
        this.road = null;
        this.location = 0;
        if (contClass < 0 || contClass > 10){
            throw new Exception("Invalid contamination Class");
        } else {
            this.contaminationClass = contClass;
        }
        this.totalContamination = 0;
        this.totalTraveledDistance = 0;

    }

    void setSpeed(int s) throws Exception{
        if (s < 0){
            throw new Exception("Current speed negative");
        } else {
            this.currentSpeed = Math.min(s, this.maximumSpeed);
        }
    }

    void setContaminationClass(int c) throws Exception{
        if(c < 0 || c > 10){
            throw new Exception("Contamination speed is not between 0 and 10");
        }
        this.contaminationClass = c;
    }

//NOT FINISHED
    @Override
    void advance(int time) {
        if(this.status != VehicleStatus.TRAVELING){
            return;
        }
        //(A)
        int newLocation = Math.min(this.location + this.currentSpeed, this.road.getLength());
       
        //(B)
        int c = this.contaminationClass * (newLocation - this.location);
        this.totalContamination += c;
        this.road.addContamination(c);
        this.location = newLocation;
        
        // (C)
        if (this.location == this.road.getLength()) {
            //method of class Junction : enters queue of junction
            this.status = VehicleStatus.WAITING;
        }
    }

    //NOT FINISHED : need class JUNCTION
    void moveToNextRoad() throws Exception{
    	
    	if (this.status != VehicleStatus.PENDING || this.status != VehicleStatus.WAITING )
    		throw new Exception ("Cannot move to next road because the status is not pending or waiting");
    	if (this.road != null || this.itinerary.get(this.itinerary.size() - 1) == 0)
    		{
    		this.road.exit(this); // exit current road
        	this.road.enter(this);
        	this.location = 0;
    		};
    	
    }

    //NOT STARTED
    @Override
    public JSONObject report() {
    	JSONObject O = new JSONObject();
    	
    	O.put("id", this._id);
    	O.put("speed", this.currentSpeed);
    	O.put("distance", this.totalTraveledDistance);
    	O.put("co2", this.totalContamination);
    	O.put("class", this.contaminationClass);
    	O.put("status", this.status);
    	if (this.status != VehicleStatus.PENDING || this.status != VehicleStatus.ARRIVED) {
        	O.put("road", this.road);
        	O.put("location", this.location);
    	}
    	
    	
    	
    	
    	
        return O;
    }

    int getLocation(){
        return this.location;
    }

    int getSpeed(){
        if(this.status != VehicleStatus.TRAVELING){
            return 0;
        }
        return this.currentSpeed;
    }

    int getMaxSpeed(){
        return this.maximumSpeed;
    }

    int getContClass(){
        return this.contaminationClass;
    }

    VehicleStatus getStatus(){
        return this.status;
    }

    int getTotalCO2(){
        return this.totalContamination;
    }

    List<Junction> getItinerary(){
        return this.itinerary;
    }

    Road getRoad() {
        return this.road;
    }

    //additional private setters?

}
