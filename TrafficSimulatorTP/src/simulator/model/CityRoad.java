package simulator.model;

//NOT DONE YET
public class CityRoad extends Road {

    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() throws Exception {

    }

    @Override
    void updateSpeedLimit() {

    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        return 0;
    }
}
