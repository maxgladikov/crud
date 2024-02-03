package com.gladikov.crud.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
    	ResourceProvider rp = null;
    	DataSource ds = null;
    	try {
	    		rp = new ResourceProvider();
		    	ds = rp.getDatasource();
		    	
	    	} catch(Exception e) {
	    		log.error(e.getCause().toString());
	    	}
    	ctx.setAttribute("ResourceProvider", rp);
    	ctx.setAttribute("DataSource", ds);
    	log.info("Datasource was initialized for Application.");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	log.info("Application shut down.");
    }
    
    
	
}
