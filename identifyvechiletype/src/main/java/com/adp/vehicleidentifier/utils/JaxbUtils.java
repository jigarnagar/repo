package com.adp.vehicleidentifier.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * This Utility class is used to process JAXB operation
 * 
 * @author Jigar Nagar
 * 
 * @param <T>
 */
public class JaxbUtils<T> {
    private static Logger LOGGER = LoggerFactory.getLogger(JaxbUtils.class);

    /**
     * This method is used to create JAXB instance
     * 
     * @param clazz
     * @param file
     * @return Class Object
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public T getInstance(Class<T> clazz, File file) throws JAXBException {
	LOGGER.trace("ENTERED - JaxbUtils getInstance()");
	JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	LOGGER.trace("EXIT - JaxbUtils getInstance()");
	return ((T) unmarshaller.unmarshal(file));
    }
    
    /**
     * This method is used to validate the XML request against xsd file 
     * @param xsdPath
     * @param xmlPath
     * @return boolean result, TRUE if valid otherwise FALSE
     */
    public boolean validateXMLSchema(String xsdPath, String xmlPath) {
	try {
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Schema schema = factory.newSchema(new File(xsdPath));
	    Validator validator = schema.newValidator();
	    validator.validate(new StreamSource(new File(xmlPath)));
	} catch (IOException e) {
	    LOGGER.error("Exception: " + e.getMessage());
	    //e.printStackTrace();
	    return false;
	} catch (SAXException e1) {
	    LOGGER.error("SAX Exception: " + e1.getMessage());
	    //e1.printStackTrace();
	    return false;
	}
	return true;
    }
}
