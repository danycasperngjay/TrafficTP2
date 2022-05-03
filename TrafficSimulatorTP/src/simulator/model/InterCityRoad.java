package simulator.model;

public class InterCityRoad extends Road{

    InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination(){
        int tc = getTotalCO2();
        int x = 0;
        Weather w = getWeather();
        switch (w){
            case SUNNY : x = 2; break;
            case CLOUDY : x = 3; break;
            case RAINY : x = 10; break;
            case WINDY : x = 15; break;
            case STORM : x = 20; break;
        }
        this.totalContamination = (((100 - x) * tc) / 100);
    }
    
    @Override
    void updateSpeedLimit() {
        if(getTotalCO2() > getContLimit()) {
            this.currentSpeedLimit = getMaxSpeed() / 2;
        } else {
            this.currentSpeedLimit = getMaxSpeed();
        }
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        if(getWeather() == Weather.STORM) {
            return (getSpeedLimit() * 8) / 10;
        }
        else {
        return getSpeedLimit();
        }
    }
}
