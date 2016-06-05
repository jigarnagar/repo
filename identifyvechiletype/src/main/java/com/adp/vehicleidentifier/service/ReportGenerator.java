/**
 * 
 */
package com.adp.vehicleidentifier.service;

/**
 * This Interface is used to generate the PDF report 
 * In future, we can give support to generate different type of report - HTML,CSV,XLS more
 * 
 * @author Jigar Nagar
 * 
 */
public interface ReportGenerator<E> {
    /**
     * This is used to generate the PDF report 
     * @param reportData
     */
    void generateReport(E reportData);
}
