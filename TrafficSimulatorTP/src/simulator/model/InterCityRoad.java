package simulator.model;

public class InterCityRoad extends Road{

    InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination(){
        int tc = getTotalCO2();
        int x = 0;
        switch (x) {
            Weather w = getWeather();
            case w == Weather.SUNNY -> x = 2;
            case w == Weather.CLOUDY -> x = 3;
            case w == Weather.RAINY -> x = 10;
            case w == Weather.WINDY -> x = 15;
            //why is w "not initialized"?
            case w == Weather.STORM -> x = 20;
        }

        //this.getTotalCO2 or tc or what : where to store the answer
        Road.? -= (((100 - x) * tc) / 100);

    }

    //where to store the answer?
    @Override
    void updateSpeedLimit() {
        if(getTotalCO2() > getContLimit()) {
            this.getSpeedLimit() = getMaxSpeed() / 2;
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
