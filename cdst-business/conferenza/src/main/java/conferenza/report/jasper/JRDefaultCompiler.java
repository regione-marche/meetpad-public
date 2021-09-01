package conferenza.report.jasper;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRCompiler;
import net.sf.jasperreports.engine.design.JRJavacCompiler;
import net.sf.jasperreports.engine.design.JRJdk13Compiler;
import net.sf.jasperreports.engine.design.JRJdtCompiler;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fill.JREvaluator;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRProperties;

public class JRDefaultCompiler implements JRCompiler{

/*	
	@Override
	public JasperReport compileReport(JasperDesign jasperDesign) throws JRException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JREvaluator loadEvaluator(JasperReport jasperReport) throws JRException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JREvaluator loadEvaluator(JasperReport jasperReport, JRDataset dataset) throws JRException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JREvaluator loadEvaluator(JasperReport jasperReport, JRCrosstab crosstab) throws JRException {
		// TODO Auto-generated method stub
		return null;
	}
*/	
	/**
	 *
	 */
	private static final JRDefaultCompiler instance = new JRDefaultCompiler();

		
	/**
	 *
	 */
	private JRDefaultCompiler()
	{
	}

		
	/**
	 *
	 */
	public static JRDefaultCompiler getInstance()
	{
		return instance;
	}

		
	/**
	 *
	 */
	@Override
	public JasperReport compileReport(JasperDesign jasperDesign) throws JRException
	{
		JRCompiler jrCompiler = null;

		String compiler = JRProperties.getProperty(JRProperties.COMPILER_CLASS);
		if (
			(compiler == null 
			|| compiler.trim().length() == 0)
			&& JRReport.LANGUAGE_GROOVY.equals(jasperDesign.getLanguage())
			)
		{
			compiler = "net.sf.jasperreports.compilers.JRGroovyCompiler";
		}

		if (compiler == null || compiler.trim().length() == 0)
		{
			jrCompiler = getJavaCompiler();
		}
		else
		{
			try 
			{
				Class clazz = JRClassLoader.loadClassForName(compiler);
				jrCompiler = (JRCompiler)clazz.newInstance();
			}
			catch (Exception e)
			{
				throw new JRException("Could not instantiate report compiler : " + compiler, e);
			}
		}
		
		return jrCompiler.compileReport(jasperDesign);
	}


	/**
	 *
	 */
	
	private static JRCompiler getJavaCompiler()
	{
		JRCompiler compiler = null;

		try 
		{
			JRClassLoader.loadClassForRealName("org.eclipse.jdt.internal.compiler.Compiler");
			compiler = new JRJdtCompiler();
		}
		catch (Exception e)
		{
		}


		if (compiler == null)
		{
			compiler = new JRJavacCompiler();
		}
		
		return compiler;
	}


	private static JRCompiler getCompiler(JasperReport jasperReport) throws JRException
	{
		JRCompiler compiler = null;
		
		String compilerClassName = jasperReport.getCompilerClass();

		Class compilerClass = null;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null)
		{
			try
			{
				compilerClass = classLoader.loadClass(compilerClassName);
			}
			catch(ClassNotFoundException e)
			{
			}
		}
		
		if (compilerClass == null)
		{
			classLoader = JRDefaultCompiler.class.getClassLoader();
			try
			{
				if (classLoader == null)
				{
					compilerClass = Class.forName(compilerClassName);
				}
				else
				{
					compilerClass = classLoader.loadClass(compilerClassName);
				}
			}
			catch(ClassNotFoundException e)
			{
				throw new JRException("Report compiler class not found : " + compilerClassName);
			}
		}


		try
		{
			compiler = (JRCompiler)compilerClass.newInstance();
		}
		catch (Exception e)
		{
			throw new JRException("Could not instantiate report compiler : " + compilerClassName, e);
		}
		return compiler;
	}

	
	/**
	 *
	 */
	@Override
	public JREvaluator loadEvaluator(JasperReport jasperReport, JRDataset dataset) throws JRException
	{
		JRCompiler compiler = getCompiler(jasperReport);
		
		return compiler.loadEvaluator(jasperReport, dataset);
	}

	@Override
	public JREvaluator loadEvaluator(JasperReport jasperReport, JRCrosstab crosstab) throws JRException
	{
		JRCompiler compiler = getCompiler(jasperReport);
		
		return compiler.loadEvaluator(jasperReport, crosstab);
	}

	@Override	
	public JREvaluator loadEvaluator(JasperReport jasperReport) throws JRException
	{
		return loadEvaluator(jasperReport, jasperReport.getMainDataset());
	}
	

}
