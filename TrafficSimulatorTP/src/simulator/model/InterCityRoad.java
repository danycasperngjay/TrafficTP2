package simulator.model;

import java.util.List;

public class InterCityRoad extends Road{

    InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() throws Exception {
        int tc = getTotalCO2();
        int x = 0;
        Weather w = getWeather();
        switch (w){
            case SUNNY -> x = 2;
            case CLOUDY -> x = 3;
            case RAINY -> x = 10;
            case WINDY -> x = 15;
            case STORM -> x = 20;
        }

        int value = (((100 - x) * tc) / 100);
        int diff = value - tc;

        addContamination(diff);

    }

    //where to store the answer?
    @Override
    void updateSpeedLimit() {
        if(getTotalCO2() > getContLimit()) {
            this.updateSpeedLimit(); = getMaxSpeed() / 2;
        } else {
            this.getSpeedLimit() = getMaxSpeed();
        }
    }

    //WRONG
    @Override
    int calculateVehicleSpeed(Vehicle v) {
        if(getWeather() == Weather.STORM) {
            return (getSpeedLimit() * 8) / 10;
        }
        //else ??
        return 0;
    }


}
