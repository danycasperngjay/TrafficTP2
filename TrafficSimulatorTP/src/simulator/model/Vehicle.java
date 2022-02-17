package simulator.model;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{

    private List<Junction> itinerary;
    private int maximumpeed;
    private int currentSpeed;
    private VehicleStatus status;
    private Road road = null;
    private int location;
    private int contaminationClass;
    private int totalContamination;
    private int totalTraveledDistance;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if(id != null && id.isEmpty()) {
            try {
                throw new Exception("ID invalid");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    void advance(int time) {

    }

    @Override
    public JSONObject report() {
        return null;
    }
}
