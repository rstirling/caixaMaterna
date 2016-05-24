package org.caixaMaterna.sensors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneWireTempSensor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OneWireTempSensor.class);
	
	private static final String BUS_SLAVE_PATH = "w1_slave";
	private static final String BUS_MASTER_PATH = "w1_bus_master";
	private static final String W1_PATH = "/sys/bus/w1/devices";
	
	private static final String anyThing=".*?";	// Non-greedy match on filler
    private static final String tempMarker="(t=)";	// Variable Name 1
    private static final String tempValue="(\\d+)";	// Integer Number 1
	
    private static Pattern p = Pattern.compile(anyThing+tempMarker+tempValue,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	private File sensorFile;
	private boolean ready;
	
	public OneWireTempSensor() throws IllegalStateException{
		try {
			init();
			setReady(true);
		} catch (Exception e) {
			LOGGER.error("Could not find w1 devices! Please ensure that modules w1-gpio and w1-therm are loaded.");
		}
	}
	
	private void init() throws IllegalStateException{
		File sensorFolder = new File(W1_PATH);
		if (!sensorFolder.exists()) {
			throw new IllegalStateException("Could not find w1 devices");
		}else{
			for (File f : sensorFolder.listFiles()) {
				if (!f.getName().startsWith(BUS_MASTER_PATH)) {
					sensorFile = new File(f, BUS_SLAVE_PATH);
				}
			}
		}
	}
	
	public Double getTemp(){
		
		Double temp = null;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(sensorFile))) {
			
		    String line;
			while ((line =  reader.readLine()) != null) {
				Matcher matcher = p.matcher(line);
				if (matcher.find())
			    {
			        String tempString = matcher.group(2);
			        temp = Double.valueOf(tempString) / 1000;
			        
			        LOGGER.info(MessageFormat.format("Current Temp is: {0,number,#.##} C",temp));
			    }
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Unnable To open 1W sensor file!", e);
		} catch (IOException e) {
			LOGGER.error("Unnable To open 1W sensor file!", e);
		}
		return temp;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

}
