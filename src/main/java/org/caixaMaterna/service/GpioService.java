package org.caixaMaterna.service;

import java.util.Collection;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;

public interface GpioService {
	public boolean setPinState(PinState state);
	public PinState getPinState();
	public Double getTemperature();
	public Collection<GpioPin> getPins();
	public boolean setDisplayText(int line, String text);
	public boolean showClock();
	public boolean showInfo();
}
