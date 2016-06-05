package com.adp.vehicleidentifier;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.vehicleidentifier.pojo.VehiclePojo;
import com.adp.vehicleidentifier.pojo.WheelsPojo;
import com.adp.vehicleidentifier.utils.ReadPropertyUtil;
/**
 * Unit test for simple VehicleIdentityProcessor.
 */
public class VehicleIdentityProcessorTest 
    
{
    private static Map<String, String> PROPERY_CACHE;
    private static String OUTPUT_FILE_LOCATION;
    
    @BeforeClass 
    public static void initialize() throws IOException {
	PROPERY_CACHE = ReadPropertyUtil.getPropValues();
	StringBuilder fileNameSB = new StringBuilder(PROPERY_CACHE.get("output.generate.filelocation"));
	fileNameSB.append(File.separator);
	fileNameSB.append(PROPERY_CACHE.get("output.generate.filename"));
	OUTPUT_FILE_LOCATION = fileNameSB.toString();
	
	// remove the file from output folder if it is already exit
	File outputFile = new File(OUTPUT_FILE_LOCATION);
	if(outputFile.exists()) {
	    outputFile.delete();
	}
    }
    /**
     * TEST case for end to end happy flow 
     */
    @Test
    public void testVehicleIdentityProcess()
    {
	VehicleIdentityProcessor vProcessor = new VehicleIdentityProcessor();
	vProcessor.vehicleIdentityProcess();
	File file = new File(OUTPUT_FILE_LOCATION);
	Assert.assertTrue("Happy flow execute successfully", file.exists());
	
    }
    /**
     * Test Case for test the vehicle type based on parameter
     */
    @Test
    public void testGetVehicleName() {
	VehicleIdentityProcessor vProcessor = new VehicleIdentityProcessor();
	VehiclePojo vehiclePojo = new VehiclePojo();
	try {
	    Method accessPrivateMethod = VehicleIdentityProcessor.class.getDeclaredMethod("getVehicleName", VehiclePojo.class);
	    accessPrivateMethod.setAccessible(true);
	    //"Plastic", "Human", 3 - BIGWHEEL
	    vehiclePojo.setFrameMaterial("Plastic");
	    vehiclePojo.setPowerTrain("Human");
	    
	    List<WheelsPojo> wheelsList = new ArrayList<WheelsPojo>();
	    WheelsPojo wheels = new WheelsPojo();
	    wheels.setMaterial("");
	    
	    WheelsPojo wheels1 = new WheelsPojo();
	    wheels1.setMaterial("");
	    
	    WheelsPojo wheels2 = new WheelsPojo();
	    wheels2.setMaterial("");
	    
	    wheelsList.add(wheels);
	    wheelsList.add(wheels1);
	    wheelsList.add(wheels2);
	    
	    vehiclePojo.setWheelsList(wheelsList);
	    String output = (String)accessPrivateMethod.invoke(vProcessor, vehiclePojo);
	    
	    Assert.assertEquals("BIGWHEEL", output);
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
    }
}
