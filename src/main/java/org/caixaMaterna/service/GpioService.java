package org.caixaMaterna.service;

import java.util.Collection;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;
import org.springframework.stereotype.Service;


public interface GpioService {
	boolean setPinState(PinState state);
	PinState getPinState();
	Double getTemperature();
	Collection<GpioPin> getPins();
	boolean setDisplayText(int line, String text);
	boolean showClock();
	boolean showInfo();
}
