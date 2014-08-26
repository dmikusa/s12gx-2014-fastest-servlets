package com.pivotal.demos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BigDataFileServletContextListener implements
		ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		File bigData = new File(sce.getServletContext().getRealPath("/big-file.dat"));
		if (! bigData.exists()) {
			try (FileOutputStream out = new FileOutputStream(bigData)) {
				for (int i = 0; i < (5 * 1024); i++) {
					out.write(new byte[1024]);
				}
			} catch (IOException e) {
				sce.getServletContext().log("Failed creating `bigFile.dat`", e);
			}
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
