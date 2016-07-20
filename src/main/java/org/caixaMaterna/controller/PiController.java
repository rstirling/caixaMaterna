package org.caixaMaterna.controller;

import java.util.Collection;
import java.util.List;

import org.caixaMaterna.domain.Temperature;
import org.caixaMaterna.service.GpioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.temperature.TemperatureScale;


@RestController
public class PiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PiController.class);
	
	@Autowired
	@Qualifier("gpioService")
	public GpioService service;

    @RequestMapping(value = "/on", method = RequestMethod.POST)
    @ResponseBody
    public String setLedOn() {
        LOGGER.debug("turn led on");
        service.setPinState(PinState.HIGH);
        LOGGER.debug("--> GPIO state should be: ON");
        return "HIGH";
    }
    
    @RequestMapping(value = "/off", method = RequestMethod.POST)
    @ResponseBody
    public String setLedOff() {
        LOGGER.debug("turn led off");
        service.setPinState(PinState.LOW);
        LOGGER.debug("--> GPIO state should be: OFF");
        return "LOW";
    }
    
    @RequestMapping(value = "/trigger", method = RequestMethod.POST)
    @ResponseBody
    public void triggerLed() throws InterruptedException {
        LOGGER.debug("trigger led");
    }
    
    @RequestMapping(value = "/state", method = RequestMethod.GET)
    @ResponseBody
    public PinState getPinState() throws InterruptedException {
    	
    	PinState state = service.getPinState();
        LOGGER.debug("The pin state is [{}]", state);
        return state;
    }
    
    @RequestMapping(value = "/temperature", method = RequestMethod.GET)
    @ResponseBody
    public Temperature getTemp() throws InterruptedException {
    	Double temp = service.getTemperature();
        LOGGER.debug("The room temperature is [{0,number,#.##}C]", temp);
        Temperature value = new Temperature(TemperatureScale.CELSIUS, temp); 
        return value;
    }
    
    @RequestMapping(value = "/display/clock", method = RequestMethod.PUT)
    @ResponseBody
    public void setDisplayClock() throws InterruptedException {
    	LOGGER.debug("Show Clock on Display");
    	service.showClock();
    }
    
    @RequestMapping(value = "/display/info", method = RequestMethod.PUT)
    @ResponseBody
    public void setDisplayInfo() throws InterruptedException {
    	LOGGER.debug("Show Info on Display");
    	service.showClock();
    }
    
    @RequestMapping(value = "/display/{line}/{text}", method = RequestMethod.PUT)
    @ResponseBody
    public void setDisplayText(@PathVariable("line") Integer line, @PathVariable("text") String text) throws InterruptedException {
    	Object[] logArgs = {line, text};
    	LOGGER.debug("Set Display line[{}] to [{}]", logArgs);
    	service.setDisplayText(line, text);
    }
    
    @RequestMapping(value = "/pins", method = RequestMethod.GET)
    @ResponseBody
    public List<GpioPin> getPins() throws InterruptedException {
    	Collection<GpioPin> pins = service.getPins();
        return (List<GpioPin>) pins;
    }
}
