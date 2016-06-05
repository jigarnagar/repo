package com.adp.vehicleidentifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adp.vehicleidentifier.jaxb.Vehicles;
import com.adp.vehicleidentifier.jaxb.Vehicles.Vehicle;
import com.adp.vehicleidentifier.jaxb.Vehicles.Vehicle.Wheels.Wheel;
import com.adp.vehicleidentifier.pojo.VehiclePojo;
import com.adp.vehicleidentifier.pojo.WheelsPojo;
import com.adp.vehicleidentifier.service.ReportGenerator;
import com.adp.vehicleidentifier.service.impl.PDFReportGenerator;
import com.adp.vehicleidentifier.utils.JaxbUtils;
import com.adp.vehicleidentifier.utils.ReadPropertyUtil;

/**
 * This is the processor java class to find out the vehicle identity.
 * It is having three simple steps:
 * 	1. Convert XML data to OBJECT using JAXB
 * 	2. Read JAXB object and set it to custom POJO
 *      3. Generate the report in PDF format
 *      	
 * @author Jigar Nagar
 *
 */
public class VehicleIdentityProcessor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleIdentityProcessor.class);
    private static Map<String, String> propertyCache;
    
    /**
     * Default constructor is used to load the property 
     */
    public VehicleIdentityProcessor() {
	try {
	    propertyCache = ReadPropertyUtil.getPropValues();
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("PROPERTY FILE load successfully");
	    }
	} catch (IOException e) {
	    LOGGER.error("IOException occur while reading the property file "+e.getStackTrace());
	}
    }
    /**
     * This is the main execution method
     * @param args
     */
    public static void main(String[] args) {
	VehicleIdentityProcessor vehicleIdentity = new VehicleIdentityProcessor();
	vehicleIdentity.vehicleIdentityProcess();

    }
    /**
     * This is the main execution method to start vehicle identity process
     */
    public void vehicleIdentityProcess() {
	LOGGER.info("Execution START - VehicleIdentityProcessor");
	String requestXMLPath = propertyCache.get("input.file.location");
	File file = new File(requestXMLPath);
	try {
	    //Create JAXB object
	    JaxbUtils<Vehicles> jaxb= new JaxbUtils<Vehicles>();
	    //First check requested XML is valid
	    boolean isRequestedXmlValid=jaxb.validateXMLSchema(propertyCache.get("input.file.xsdlocation"), requestXMLPath);
	    if(isRequestedXmlValid) {
		Vehicles vehicles = jaxb.getInstance(Vehicles.class, file);
		if (vehicles!=null) {
    		   // Read Vehicle and Set in Custom Vehicle POJO
    		   List<VehiclePojo> vehicleCustomList = readJaxbObject(vehicles);
    
    		   // Generate the PDF report to show the out
    		   ReportGenerator<List<VehiclePojo>> repo = new PDFReportGenerator();
    		   repo.generateReport(vehicleCustomList);
		}
	    }
	    
	    LOGGER.info("Execution END - VehicleIdentityProcessor");
	} catch (JAXBException e) {
	    LOGGER.error("JAXBException occur "+e.getStackTrace());
	}
    }
    
    /**
     * This method is used to read from JAXB object and write data into Customer POJO 
     * @param vehicles
     * @return List<VehiclePojo>
     */
    private List<VehiclePojo> readJaxbObject(Vehicles vehicles) {
	LOGGER.info("START - VehicleIdentityProcessor - readJaxbObject()");
	
	List<Vehicle> vehicleList = vehicles.getVehicle();
	
	List<VehiclePojo> vehicleCustomList = new ArrayList<VehiclePojo>();
	for (Vehicle v : vehicleList) {
	    VehiclePojo vehiclePojo = new VehiclePojo();
	    vehiclePojo.setVehicleId(v.getId());
	    vehiclePojo.setFrameMaterial(v.getFrame().getMaterial());

	    List<Wheel> wheelList = v.getWheels().getWheel();
	    List<WheelsPojo> wheelsCustomList = new ArrayList<WheelsPojo>();
        	for (Wheel w : wheelList) {
        	    WheelsPojo wheelsPojo = new WheelsPojo();
        	    wheelsPojo.setMaterial(w.getMaterial());
        	    wheelsPojo.setPosition(w.getPosition());
        	    wheelsCustomList.add(wheelsPojo);
        	}
        	vehiclePojo.setWheelsList(wheelsCustomList);

        	vehiclePojo.setPowerTrain(v.getPowertrain().getHuman());
	
        	String vName = getVehicleName(vehiclePojo);
        	vehiclePojo.setVehicleType(vName);
	
        	vehicleCustomList.add(vehiclePojo);
	}
	LOGGER.info("END - VehicleIdentityProcessor - readJaxbObject()");
	return vehicleCustomList;
    }
    
    /**
     * This method is used to get vehicle TYPE based on parameters like frame,power train or count
     * @param vehiclePojo
     * @return vehicle name
     */
    private String getVehicleName(VehiclePojo vehiclePojo) {
	LOGGER.info("START - VehicleIdentityProcessor - getVehicleName()");
	String name = null;
	for (VehicleType vType : VehicleType.values()) {
	    if (vType.getVehicleFrame().equalsIgnoreCase(vehiclePojo.getFrameMaterial())
		&& vType.getVehiclePowerTrain().equalsIgnoreCase(vehiclePojo.getPowerTrain())
		&&  vType.getVehicleCount() == vehiclePojo.getWheelsList().size()) {
		name = vType.name();
	    }
	}
	LOGGER.info("END - VehicleIdentityProcessor - getVehicleName()");
	return name;
    }

}
