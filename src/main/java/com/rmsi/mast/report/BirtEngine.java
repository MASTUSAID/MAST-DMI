/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */
package com.rmsi.mast.report;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import javax.servlet.*;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

public class BirtEngine {

	private static IReportEngine birtEngine = null;

	private static Properties configProps = new Properties();

	private final static String configFile = "BirtConfig.properties";

	public static synchronized void initBirtConfig() {
		loadEngineProps();
	}

	public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
		if (birtEngine == null) {
			EngineConfig config = new EngineConfig();
			if (configProps != null) {
				String logLevel = configProps.getProperty("logLevel");
				Level level = Level.OFF;
				if ("SEVERE".equalsIgnoreCase(logLevel)) {
					level = Level.SEVERE;
				} else if ("WARNING".equalsIgnoreCase(logLevel)) {
					level = Level.WARNING;
				} else if ("INFO".equalsIgnoreCase(logLevel)) {
					level = Level.INFO;
				} else if ("CONFIG".equalsIgnoreCase(logLevel)) {
					level = Level.CONFIG;
				} else if ("FINE".equalsIgnoreCase(logLevel)) {
					level = Level.FINE;
				} else if ("FINER".equalsIgnoreCase(logLevel)) {
					level = Level.FINER;
				} else if ("FINEST".equalsIgnoreCase(logLevel)) {
					level = Level.FINEST;
				} else if ("OFF".equalsIgnoreCase(logLevel)) {
					level = Level.OFF;
				}

				config.setLogConfig(configProps.getProperty("logDirectory"),
						level);
			}

			config.setEngineHome("");
			IPlatformContext context = new PlatformServletContext(sc);
			config.setPlatformContext(context);

			try {
				Platform.startup(config);
			} catch (BirtException e) {
				e.printStackTrace();
			}

			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			birtEngine = factory.createReportEngine(config);

		}
		return birtEngine;
	}

	public static synchronized void destroyBirtEngine() {
		if (birtEngine == null) {
			return;
		}
		birtEngine.shutdown();
		Platform.shutdown();
		birtEngine = null;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static void loadEngineProps() {
		try {
			// Config File must be in classpath
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream in = null;
			in = cl.getResourceAsStream(configFile);
			configProps.load(in);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
