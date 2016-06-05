package com.adp.vehicleidentifier.pojo;

import java.util.List;

/**
 * This is custom java POJO class is used to set/get the values
 * @author Jigar Nagar
 * 
 */
public class VehiclePojo {
    private String vehicleId;
    private String vehicleType;
    private String frameMaterial;
    private List<WheelsPojo> wheelsList;
    private String powerTrain;

    public String getVehicleType() {
	return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
	this.vehicleType = vehicleType;
    }

    public String getVehicleId() {
	return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
	this.vehicleId = vehicleId;
    }

    public String getFrameMaterial() {
	return frameMaterial;
    }

    public void setFrameMaterial(String frameMaterial) {
	this.frameMaterial = frameMaterial;
    }

    public List<WheelsPojo> getWheelsList() {
	return wheelsList;
    }

    public void setWheelsList(List<WheelsPojo> wheelsList) {
	this.wheelsList = wheelsList;
    }

    public String getPowerTrain() {
	return powerTrain;
    }

    public void setPowerTrain(String powerTrain) {
	this.powerTrain = powerTrain;
    }

}
