package conferenza.report.jasper;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class JasperReportsUtils {
	/**
	 * Convert the given report data value to a JRDataSource.
	 * 
In the default implementation, a JRDataSource,
	 * java.util.Collection or object array is detected.
	 * The latter are converted to JRBeanCollectionDataSource
	 * or JRBeanArrayDataSource, respectively.
	 * @param value the report data value to convert
	 * @return the JRDataSource (never null)
	 * @throws IllegalArgumentException if the value could not be converted
	 * @see net.sf.jasperreports.engine.JRDataSource
	 * @see net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
	 * @see net.sf.jasperreports.engine.data.JRBeanArrayDataSource
	 */
	public static JRDataSource convertReportData(Object value) throws IllegalArgumentException {
		if (value instanceof JRDataSource) {
			return (JRDataSource) value;
		}
		else if (value instanceof Collection) {
			return new JRBeanCollectionDataSource((Collection) value);
		}
		else if (value instanceof Object[]) {
			return new JRBeanArrayDataSource((Object[]) value);
		}
		else {
			throw new IllegalArgumentException("Value [" + value + "] cannot be converted to a JRDataSource");
		}
	}

	/**
	 * Render the supplied JasperPrint instance using the
	 * supplied JRAbstractExporter instance and write the results
	 * to the supplied Writer.
	 * 

Make sure that the JRAbstractExporter implementation
	 * you supply is capable of writing to a Writer.
	 * @param exporter the JRAbstractExporter to use to render the report
	 * @param print the JasperPrint instance to render
	 * @param writer the Writer to write the result to
	 * @throws JRException if rendering failed
	 */
	public static void render(JRExporter exporter, JasperPrint print, Writer writer)
			throws JRException {

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
		exporter.exportReport();
	}

	/**
	 * Render the supplied JasperPrint instance using the
	 * supplied JRAbstractExporter instance and write the results
	 * to the supplied OutputStream.
	 * 

Make sure that the JRAbstractExporter implementation you
	 * supply is capable of writing to a OutputStream.
	 * @param exporter the JRAbstractExporter to use to render the report
	 * @param print the JasperPrint instance to render
	 * @param outputStream the OutputStream to write the result to
	 * @throws JRException if rendering failed
	 */
	public static void render(JRExporter exporter, JasperPrint print, OutputStream outputStream)
			throws JRException {

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
	}

	/**
	 * Render a report in CSV format using the supplied report data.
	 * Writes the results to the supplied Writer.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param writer the Writer to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsCsv(JasperReport report, Map parameters, Object reportData, Writer writer)
			throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		render(new JRCsvExporter(), print, writer);
	}

	/**
	 * Render a report in CSV format using the supplied report data.
	 * Writes the results to the supplied Writer.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param writer the Writer to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @param exporterParameters a {@link Map} of {@link JRExporterParameter exporter parameters}
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsCsv(JasperReport report, Map parameters, Object reportData, Writer writer,
			Map exporterParameters) throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameters(exporterParameters);
		render(exporter, print, writer);
	}



	/**
	 * Render a report in PDF format using the supplied report data.
	 * Writes the results to the supplied OutputStream.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param stream the OutputStream to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsPdf(JasperReport report, Map parameters, Object reportData, OutputStream stream)
			throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		render(new JRPdfExporter(), print, stream);
	}

	/**
	 * Render a report in PDF format using the supplied report data.
	 * Writes the results to the supplied OutputStream.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param stream the OutputStream to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @param exporterParameters a {@link Map} of {@link JRExporterParameter exporter parameters}
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsPdf(JasperReport report, Map parameters, Object reportData, OutputStream stream,
			Map exporterParameters) throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameters(exporterParameters);
		render(exporter, print, stream);
	}

	/**
	 * Render a report in XLS format using the supplied report data.
	 * Writes the results to the supplied OutputStream.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param stream the OutputStream to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsXls(JasperReport report, Map parameters, Object reportData, OutputStream stream)
			throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		render(new JRXlsExporter(), print, stream);
	}

	/**
	 * Render a report in XLS format using the supplied report data.
	 * Writes the results to the supplied OutputStream.
	 * @param report the JasperReport instance to render
	 * @param parameters the parameters to use for rendering
	 * @param stream the OutputStream to write the rendered report to
	 * @param reportData a JRDataSource, java.util.Collection
	 * or object array (converted accordingly), representing the report data to read
	 * fields from
	 * @param exporterParameters a {@link Map} of {@link JRExporterParameter exporter parameters}
	 * @throws JRException if rendering failed
	 * @see #convertReportData
	 */
	public static void renderAsXls(JasperReport report, Map parameters, Object reportData, OutputStream stream,
			Map exporterParameters) throws JRException {

		JasperPrint print = JasperFillManager.fillReport(report, parameters, convertReportData(reportData));
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameters(exporterParameters);
		render(exporter, print, stream);
	}
}
