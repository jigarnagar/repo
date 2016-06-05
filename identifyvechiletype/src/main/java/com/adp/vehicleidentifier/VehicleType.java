package com.adp.vehicleidentifier;

/**
 * This ENUM class is used to define the different type of Vehicle.
 * 
 * @author jigar
 * 
 */
public enum VehicleType {

    BIGWHEEL("Plastic", "Human", 3),
    BICYCLE("Metal", "Human", 2),
    MOTORCYCLE("Metal", "Internal Combustion", 2);

    private String vehicleFrame;
    private String vehiclePowerTrain;
    private int vehicleCount;

    VehicleType(String frame, String powerTrain, int count) {
	vehicleFrame = frame;
	vehiclePowerTrain = powerTrain;
	vehicleCount = count;
    }

    public String getVehicleFrame() {
	return vehicleFrame;
    }

    public String getVehiclePowerTrain() {
	return vehiclePowerTrain;
    }

    public int getVehicleCount() {
	return vehicleCount;
    }   

}
