package org.caixaMaterna.domain;

import com.pi4j.temperature.TemperatureScale;

public class Temperature {
	
	TemperatureScale scale;
	Double value;
	
	public Temperature(TemperatureScale scale, Double value) {
		super();
		this.scale = scale;
		this.value = value;
	}
	public TemperatureScale getScale() {
		return scale;
	}
	public void setScale(TemperatureScale scale) {
		this.scale = scale;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
