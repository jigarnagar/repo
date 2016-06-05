package com.adp.vehicleidentifier.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adp.vehicleidentifier.pojo.VehiclePojo;
import com.adp.vehicleidentifier.pojo.WheelsPojo;
import com.adp.vehicleidentifier.service.ReportGenerator;
import com.adp.vehicleidentifier.utils.ReadPropertyUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class is used to generate the PDF report
 * 
 * @author Jigar Nagar
 * 
 */
public class PDFReportGenerator implements ReportGenerator<List<VehiclePojo>> {
    private static Logger LOGGER = LoggerFactory.getLogger(PDFReportGenerator.class);
    private Map<String, String> propertyCache;
    private Map<String, Integer> vehicleTypeCount = new HashMap<String, Integer>();

    /**
     * Default Constructor is used to load the property file
     */
    public PDFReportGenerator() {
	try {
	    propertyCache = ReadPropertyUtil.getPropValues();
	} catch (IOException e) {
	    LOGGER.error("IOException occur while reading the property file "+e.getStackTrace());
	}
    }
    /**
     * This is the entry method to be used to generate the PDF file
     */
    @Override
    public void generateReport(List<VehiclePojo> reportData) {
	LOGGER.trace("Entering PDFReportGenerator - generateReport()");
	Document document = new Document();
	try {

	    PdfWriter writer = PdfWriter.getInstance(document,
		    new FileOutputStream(propertyCache.get("output.generate.filelocation")
			    + File.separator
			    + propertyCache.get("output.generate.filename")));
	    document.open();

	    Font headLineFont = new Font(FontFamily.TIMES_ROMAN, 12f,
		    Font.BOLD, BaseColor.RED);
	    
	    generatePDFHeader(reportData, document, headLineFont);

	    document.add(new Paragraph("Vehicle Details", headLineFont));

	    generateVehicleCount(reportData, document, headLineFont);
	    document.close();

	    writer.close();
	    LOGGER.trace("Exit PDFReportGenerator - generateReport()");
	} catch (DocumentException e) {
	    LOGGER.error("DocumentException occur while creating the DOCUMENT "+e.getStackTrace());
	} catch (FileNotFoundException e) {
	    LOGGER.error("FileNotFoundException occur while PdfWriter could not find the FILE "+e.getStackTrace());
	}
    }
    
    /**
     * This method is used to get total number of individual vehicle type and display
     * it on tabular format in PDF file
     * 
     * @param reportData
     * @param document
     * @param headLineFont
     * @throws DocumentException
     */
    private void generateVehicleCount(List<VehiclePojo> reportData,
	    Document document, Font headLineFont) throws DocumentException {
	LOGGER.trace("Entered PDFReportGenerator - generateVehicleCount() ");
	Font fontHeader = generateVehileDetailsInPDF(reportData, document);

	document.add(new Paragraph("Indivitual Total Number of vehicle ",
		headLineFont));

	PdfPTable vehicleTable = new PdfPTable(2); // 2 columns.
	vehicleTable.setWidthPercentage(100); // Width 100%
	vehicleTable.setSpacingBefore(10f); // Space before table
	vehicleTable.setSpacingAfter(10f); // Space after table
	// Set Column widths
	float[] vcolumnWidths = { 1f, 1f };
	vehicleTable.setWidths(vcolumnWidths);

	PdfPCell vTypeCell = new PdfPCell(new Paragraph("Vehicle Type",
		fontHeader));
	vTypeCell.setBorderColor(BaseColor.BLACK);

	PdfPCell vTypeCount = new PdfPCell(new Paragraph("Vehicle Count",
		fontHeader));
	vTypeCount.setBorderColor(BaseColor.BLACK);

	vehicleTable.addCell(vTypeCell);
	vehicleTable.addCell(vTypeCount);

	for (Map.Entry<String, Integer> vEntry : vehicleTypeCount.entrySet()) {
	    vehicleTable.addCell(vEntry.getKey());
	    vehicleTable.addCell(vEntry.getValue() + "");
	}

	document.add(vehicleTable);
	LOGGER.trace("Exiting PDFReportGenerator - generateVehicleCount() ");
    }
    /**
     * This method is used to generate vehicle details table in PDF file
     * @param reportData
     * @param document
     * @return
     * @throws DocumentException
     */
    private Font generateVehileDetailsInPDF(List<VehiclePojo> reportData,
	    Document document) throws DocumentException {
	LOGGER.trace("Entered PDFReportGenerator - generateVehileDetailsInPDF() ");
	PdfPTable table = new PdfPTable(5); // 5 columns.
	table.setWidthPercentage(100); // Width 100%
	table.setSpacingBefore(10f); // Space before table
	table.setSpacingAfter(10f); // Space after table
	// Set Column widths
	float[] columnWidths = { 1f, 1f, 1f, 1f, 1f };
	table.setWidths(columnWidths);

	Font fontHeader = new Font(FontFamily.TIMES_ROMAN, 12f, Font.BOLD,
		BaseColor.BLUE);

	PdfPCell vehicleIdCell = new PdfPCell(new Paragraph("Vehicle ID",
		fontHeader));
	vehicleIdCell.setBorderColor(BaseColor.BLACK);

	PdfPCell vehicleTypeCell = new PdfPCell(new Paragraph("Vehicle Type",
		fontHeader));
	vehicleTypeCell.setBorderColor(BaseColor.BLACK);

	PdfPCell frameCell = new PdfPCell(new Paragraph("Frame", fontHeader));
	frameCell.setBorderColor(BaseColor.BLACK);

	PdfPCell powerTrainCell = new PdfPCell(new Paragraph("Power Train",
		fontHeader));
	powerTrainCell.setBorderColor(BaseColor.BLACK);

	PdfPCell wheelsCell = new PdfPCell(new Paragraph("Wheels", fontHeader));
	wheelsCell.setBorderColor(BaseColor.BLACK);

	table.addCell(vehicleIdCell);
	table.addCell(vehicleTypeCell);
	table.addCell(frameCell);
	table.addCell(powerTrainCell);
	table.addCell(wheelsCell);

	for (VehiclePojo vehiclePojo : reportData) {
	    table.addCell(vehiclePojo.getVehicleId());
	    table.addCell(vehiclePojo.getVehicleType());
	    table.addCell(vehiclePojo.getFrameMaterial());
	    table.addCell(vehiclePojo.getPowerTrain());
	    List<WheelsPojo> wheelsList = vehiclePojo.getWheelsList();
	    StringBuilder sb = new StringBuilder();
	    sb.append(wheelsList.size());
	    for (WheelsPojo wheelsPojo : wheelsList) {
		sb.append(",").append(wheelsPojo.getPosition());
	    }
	    table.addCell(sb.toString());
	    Integer count = vehicleTypeCount.get(vehiclePojo.getVehicleType());
	    if (count == null) {
		vehicleTypeCount.put(vehiclePojo.getVehicleType(), 1);
	    } else {
		count += 1;
		vehicleTypeCount.put(vehiclePojo.getVehicleType(), count);
	    }
	}

	document.add(table);

	document.add(Chunk.NEWLINE);
	LOGGER.trace("Exiting PDFReportGenerator - generateVehileDetailsInPDF() ");
	return fontHeader;
    }
    /**
     * This method is used to generate the header section in PDF file and show total
     * number of vehicles in input XML file
     * 
     * @param reportData
     * @param document
     * @param headLineFont
     * @throws DocumentException
     */
    private void generatePDFHeader(List<VehiclePojo> reportData,
	    Document document, Font headLineFont) throws DocumentException {
	document.add(new Paragraph("Total Number of vehicle "
		+ reportData.size(), headLineFont));
	document.add(Chunk.NEWLINE);
	document.add(Chunk.NEWLINE);
    }

}
