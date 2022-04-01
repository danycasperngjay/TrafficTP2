package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject{
	
    private List<Junction> itinerary;
    protected int maximumSpeed;
    private int currentSpeed;
    private VehicleStatus status;
    private Road road = null;
    private int location;
    protected int contaminationClass;
    private int totalContamination;
    private int totalTraveledDistance;
    private int lastSeenJunction;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary){
        super(id);
        if (itinerary.size() < 2){
        	throw new IllegalArgumentException ("Itinerary is too small");
        } else {
            this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        }
        if (maxSpeed <= 0){
        	throw new IllegalArgumentException ("Max speed is negative");
        } else {
            this.maximumSpeed = maxSpeed;
        }
        this.currentSpeed = 0;
        this.status = VehicleStatus.PENDING;
        this.road = null;
        this.location = 0;
        if (contClass < 0 || contClass > 10){
        	throw new IllegalArgumentException ("Invalid contamination Class");
        } else {
            this.contaminationClass = contClass;
        }
        this.totalContamination = 0;
        this.totalTraveledDistance = 0;
        this.lastSeenJunction = 0;

    }

    void setSpeed(int s) {
        if (s < 0){
        	throw new IllegalArgumentException ("Current speed negative");
        } 
        else if (this.status == VehicleStatus.TRAVELING) {
        		if ( s < this.maximumSpeed)
        			this.currentSpeed = s;
        		else
        			 this.currentSpeed = this.maximumSpeed;
        }
    }

    void setContClass(int c){
        if(c < 0 || c > 10){
        	throw new IllegalArgumentException ("Contamination speed is not between 0 and 10");
        }
        this.contaminationClass = c;
    }

    @Override
    void advance(int time) {
        if(this.status != VehicleStatus.TRAVELING){
            return;
        }
        //(A)
        //int newLocation = Math.min(this.location + this.currentSpeed, this.road.getLength());
        int newLocation = this.location;
        if ((this.location + this.currentSpeed) < this.road.getLength())
        	newLocation = (this.location + this.currentSpeed);
        else
        	newLocation = this.road.getLength();
        
        //(B)
        int d = newLocation - this.location;
        totalTraveledDistance += d;
        int c = this.contaminationClass * d;
        
        this.totalContamination += c;
        this.road.addContamination(c);
        
        this.location = newLocation;
        // (C)
        if (this.location == this.road.getLength()) {
            //method of class Junction : enters queue of junction
        	road.getDest().enter(this);
            this.status = VehicleStatus.WAITING;
            this.currentSpeed = 0;
            //this.location = 0;
        }
    }

    void moveToNextRoad() {
    	
    	if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.WAITING )
    		throw new IllegalArgumentException  ("Cannot move to next road because the status is not pending or waiting");
    	
    	if (this.road != null)
    		this.road.exit(this); // exit current road
    	
    	if (this.lastSeenJunction == this.itinerary.size()-1)
    	{
    		this.status = VehicleStatus.ARRIVED;
    		this.road = null;
    		this.currentSpeed = 0;
    	}
    	else
    	{
    		this.road = itinerary.get(lastSeenJunction).roadTo(itinerary.get( lastSeenJunction + 1));
    		this.location = 0;
    		this.road.enter(this);
    		this.lastSeenJunction++;
    		this.status = VehicleStatus.TRAVELING;
    	}
    }

    @Override
    public JSONObject report() {
    	JSONObject jo = new JSONObject();
    	
    	jo.put("id", this._id);
    	jo.put("speed", this.currentSpeed);
    	jo.put("distance", this.totalTraveledDistance);
    	jo.put("co2", this.totalContamination);
    	jo.put("class", this.contaminationClass);
    	jo.put("status", this.status.toString());
    	if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
        	jo.put("road", this.road.getId());
        	jo.put("location", this.location);
    	}
        return jo;
    }

    //Public getters

    public List<Junction> getItinerary(){
        return this.itinerary;
    }
    public int getMaxSpeed(){
        return this.maximumSpeed;
    }
    public int getSpeed(){
        return this.currentSpeed;
    }
    public VehicleStatus getStatus(){
        return this.status;
    }
    public Road getRoad() {
        return this.road;
    }
    public int getLocation(){
        return this.location;
    }
    public int getContClass(){
        return this.contaminationClass;
    }
    public int getTotalCO2(){ return this.totalContamination; }
    public int getTotalTraveledDistance(){ return this.totalTraveledDistance; }
    public int getLastSeenJunction(){ return this.lastSeenJunction; }

}
