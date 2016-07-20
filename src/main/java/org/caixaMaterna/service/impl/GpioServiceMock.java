package org.caixaMaterna.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.caixaMaterna.service.GpioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

@Profile("dev")
@Service("gpioService")
public class GpioServiceMock implements GpioService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GpioServiceMock.class);
	
	private PinState state = PinState.HIGH;
	
	@Override
	public boolean setPinState(PinState state) {
		if(state.equals(PinState.HIGH)){
			LOGGER.info("STATE ON");
		}else{
			LOGGER.info("STATE OFF");
		}
		this.state = state;
		return true;
	}

	@Override
	public PinState getPinState() {
		return this.state;
	}

	@Override
	public Double getTemperature() {
		Random random = new Random();
		
		return Double.valueOf((random.nextDouble() * (40 - 18)) + 18);
	}

	@Override
	public Collection<GpioPin> getPins() {
		List<GpioPin> pins = new ArrayList<>();
		pins.add(new GpioPin() {
			
			@Override
			public void unexport() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setTag(Object tag) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setShutdownOptions(Boolean unexport, PinState state,
					PinPullResistance resistance, PinMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setShutdownOptions(Boolean unexport, PinState state,
					PinPullResistance resistance) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setShutdownOptions(Boolean unexport, PinState state) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setShutdownOptions(Boolean unexport) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setShutdownOptions(GpioPinShutdown options) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setPullResistance(PinPullResistance resistance) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setProperty(String key, String value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setName(String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setMode(PinMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeProperty(String key) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isPullResistance(PinPullResistance resistance) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isMode(PinMode mode) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isExported() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasProperty(String key) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getTag() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public GpioPinShutdown getShutdownOptions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PinPullResistance getPullResistance() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public GpioProvider getProvider() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String key, String defaultValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProperty(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String> getProperties() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pin getPin() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public PinMode getMode() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void export(PinMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void clearProperties() {
				// TODO Auto-generated method stub
				
			}
		});
		
		return pins;
	}

	@Override
	public boolean setDisplayText(int line, String text) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showClock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showInfo() {
		// TODO Auto-generated method stub
		return false;
	}

}
