//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.30 at 12:10:34 AM IST 
//


package com.adp.vehicleidentifier.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.adp.vehicleidentifier.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.adp.vehicleidentifier.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Vehicles }
     * 
     */
    public Vehicles createVehicles() {
        return new Vehicles();
    }

    /**
     * Create an instance of {@link Vehicles.Vehicle }
     * 
     */
    public Vehicles.Vehicle createVehiclesVehicle() {
        return new Vehicles.Vehicle();
    }

    /**
     * Create an instance of {@link Vehicles.Vehicle.Wheels }
     * 
     */
    public Vehicles.Vehicle.Wheels createVehiclesVehicleWheels() {
        return new Vehicles.Vehicle.Wheels();
    }

    /**
     * Create an instance of {@link Vehicles.Vehicle.Frame }
     * 
     */
    public Vehicles.Vehicle.Frame createVehiclesVehicleFrame() {
        return new Vehicles.Vehicle.Frame();
    }

    /**
     * Create an instance of {@link Vehicles.Vehicle.Powertrain }
     * 
     */
    public Vehicles.Vehicle.Powertrain createVehiclesVehiclePowertrain() {
        return new Vehicles.Vehicle.Powertrain();
    }

    /**
     * Create an instance of {@link Vehicles.Vehicle.Wheels.Wheel }
     * 
     */
    public Vehicles.Vehicle.Wheels.Wheel createVehiclesVehicleWheelsWheel() {
        return new Vehicles.Vehicle.Wheels.Wheel();
    }

}