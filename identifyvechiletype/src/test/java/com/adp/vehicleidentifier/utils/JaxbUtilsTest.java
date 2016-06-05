package com.adp.vehicleidentifier.utils;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import com.adp.vehicleidentifier.jaxb.Vehicles;

/**
 * Test cases for JaxbUtils class
 * @author Jigar Nagar
 * 
 */
public class JaxbUtilsTest {
    
    /**
     * Check for IllegalArgumentException
     * {@link com.adp.vehicleidentifier.utils.JaxbUtils#getInstance(java.lang.Class, java.io.File)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetInstanceCheckForInvalidArgs() {
	JaxbUtils<Vehicles> jaxbUtils = new JaxbUtils<Vehicles>();
	try {
	    jaxbUtils.getInstance(null, null);
	} catch (JAXBException e) {
	    Assert.assertEquals(JAXBException.class, e.getCause().getClass());
	}
    }

    /**
     * Check for FileNotFoundException
     */
    @Test
    public void testGetInstanceCheckForFileNotFound() {
	JaxbUtils<Vehicles> jaxbUtils = new JaxbUtils<Vehicles>();
	File file = new File("test.txt");
	try {
	    jaxbUtils.getInstance(Vehicles.class, file);
	} catch (JAXBException e) {
	    Assert.assertEquals(FileNotFoundException.class, e.getCause()
		    .getClass());
	}
    }

    /**
     * Check for Invalid Requested XML
     */
    @Test
    public void testValidateXMLSchema() {
	JaxbUtils<Vehicles> jaxbUtils = new JaxbUtils<Vehicles>();
	boolean resultShouldBeFalse=jaxbUtils.validateXMLSchema("src/main/resources/xsd/vehicles.xsd", "src/test/resources/input/invalid.xml");
	Assert.assertFalse(resultShouldBeFalse);	
    }
    
    /**
     * Check for Missing XSD file
     */
    @Test
    public void testValidateXMLSchemaXsdMissing() {
	try {
	    JaxbUtils<Vehicles> jaxbUtils = new JaxbUtils<Vehicles>();
	    jaxbUtils.validateXMLSchema("src/main/resources/xsd/vehicles1.xsd", "src/test/resources/input/invalid.xml");
	} catch (Exception e) {
	    Assert.assertEquals(SAXParseException.class, e.getCause()
		    .getClass());
	}
    }

}
