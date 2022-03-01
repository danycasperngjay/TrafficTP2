package simulator.model;


public class CityRoad extends Road {

    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
        currentSpeedLimit = this.getMaxSpeed();
    }

    @Override
    void reduceTotalContamination(){
    	int x = 2;
    	Weather w = getWeather();
        switch (w){
            case WINDY: x = 10; break;
            case STORM : x = 10; break;
            default: x = 2; break;
        }
        this.totalContamination -= x;
    }

    @Override
    void updateSpeedLimit() {
    	currentSpeedLimit = this.getMaxSpeed();
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
    	int s = this.getSpeedLimit();
    	int f = v.getContClass();
   	
    	return (((11-f)*s)/11);    
    }
}
