/* SOLAR IT CONSULTANT CO.,LTD.
 * http://www.solar.co.th
 * (C) 2008, Solar it consultant co.,ltd.
 *
 * ETA - SOBRR Project. 
 *  
 */
package co.th.aten.network.report;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;


/**
 * TODO Purpose of
 * <p>
 * Abstract class for export Jasper Report
 * </p>.
 * 
 * @author i.am.rungu@gmail.com
 * @since 1.0.0
 */
public abstract class AbstractReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String HAATITLE = "HAA - Highway Administration Application";
	public static final String SCWTITLE = "SCW - Supervisor Console Workstation";
	public static final String CSATITLE = "CSA - Central System Administration Application";
	public static final String POSTITLE = "POS - Point of Sales";
	
	public static final short HAA = 99;
	/** JasperReport jasperReport for compiled report */
	protected JasperReport jasperReport;

	/** JasperPrint jasperPrint for */
	protected JasperPrint jasperPrint;
	protected Map<String, Object> parameters;
	// private Image image;

	public AbstractReport() {
		parameters = new HashMap<String, Object>();
		try {
			//FacesContext facesContext = FacesContext.getCurrentInstance();
//			ServletContext servletContext = (ServletContext) facesContext
//			.getExternalContext()
//			.getContext();
//			parameters.put("logo_file", servletContext.getRealPath("/img/logo_becl.jpg"));
//			Locale locale = (Locale) Component.getInstance("locale", true);
			parameters.put("REPORT_LOCALE", new Locale("th","TH"));
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Error new report instance : " + e.getMessage());
		}

	}
	/**
	 * Fill.
	 */
	public abstract void fill();
	
	//public abstract void fill(String reportName);

	/**
	 * Export report to html format.
	 * 
	 * @param writer the ResponseWriter writer for output report
	 * @param request the HttpServletRequest for store imageMap to session
	 */
	public void exportHtml( ResponseWriter writer, HttpServletRequest request ) {
		try {
		JRHtmlExporter exporter = new JRHtmlExporter();
		// Map imagesMap = new HashMap();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext
		.getExternalContext()
		.getContext();
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, servletContext
				.getRealPath("/resources/imgages/report/"));

		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, servletContext.getContextPath()
				+ "/resources/imgages/report/");

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, writer);
		// exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// "c:/Test.xhtml");


			exporter.exportReport();
		} catch (JRException e) {
			// TODO Handle JRException
			e.printStackTrace();
			System.out.println("Error export report to html : " + e.getMessage());
			// throw (RuntimeException) new RuntimeException().initCause(e);
		}
	}

	/**
	 * Export pdf.
	 */
	public byte[] exportPDF() {
		byte[] output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			output = baos.toByteArray();
		} catch (JRException e) {
			// TODO Handle JRException
			System.out.println("Error export report to pdf : " + e.getMessage());
			e.printStackTrace();
			// throw (RuntimeException) new RuntimeException().initCause(e);
		}

		return output;
	}
	
	public byte[] exportToExcel() throws JRException{
		byte[] output = null;
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			JRExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
			exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			output = baos.toByteArray();

//			JRCsvExporter exporter = new JRCsvExporter();
//
//			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
//			exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, "\n");
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//			exporter.exportReport();
//			output = baos.toByteArray();
			
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}

		return output;
	}


	/**
	 * Export CSV.
	 */
	public byte[] exportCSV() {
		byte[] output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRCsvExporter exporter = new JRCsvExporter();
			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
			exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, "\n");
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, "TIS-620");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			output = baos.toByteArray();
		} catch (JRException e) {
			System.out.println("Error export report to csv : " + e.getMessage());
			e.printStackTrace();
		}
		return output;
	}
	
	public byte[] printService(){
		System.out.println("******77775665656576");
		byte[] output = null;
		//int tmp;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(MediaSizeName.ISO_A4);

		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		//printServiceAttributeSet.add(new PrinterName("Epson Stylus 800 ESC/P 2", null));
		printServiceAttributeSet.add(new PrinterName("Canon iP3300", null));
		
		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, "test");
		exporter.setParameter(JRPrintServiceExporterParameter.INPUT_STREAM, printRequestAttributeSet);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
		output = baos.toByteArray();
		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint( JasperPrint jasperPrint ) {
		this.jasperPrint = jasperPrint;
	}

}
