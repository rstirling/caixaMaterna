package org.caixaMaterna.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.caixaMaterna.sensors.OneWireTempSensor;
import org.caixaMaterna.service.GpioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;

//@Service("gpioService")
public class GpioServicePi implements GpioService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GpioServicePi.class);
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	
	private static final String DISPLAY_THREAD_INFO = "INFO";
	private static final String DISPLAY_THREAD_CLOCK = "CLOCK";
    
    public final static int LCD_ROWS = 2;
    public final static int LCD_ROW_1 = 0;
    public final static int LCD_ROW_2 = 1;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS = 4;

	
	private GpioController gpio;
	private GpioPinDigitalOutput pin;
	private OneWireTempSensor tempSensor;
	private GpioLcdDisplay lcd;
	
	private Thread displayThread;
	protected boolean state;
	
	
	public GpioServicePi(){
        startGpio();
        tempSensor = new OneWireTempSensor();
	}
	
	private void startGpio(){
		gpio  = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        
        try {
			setupLcd();
		} catch (InterruptedException e) {
			LOGGER.error("Error initializing LCD: ", e);
		} catch (IOException e) {
			LOGGER.error("Error initializing LCD: ", e);
		}
	}
	
	public Collection<GpioPin> getPins(){
		return gpio.getProvisionedPins();
	}
	
	public boolean setPinState(PinState state){
		
		if(pin == null){
			startGpio();
		}
		
		if(state.equals(PinState.HIGH)){
			pin.high();
			return true;
		}else{
			pin.low();
			return true;
		}
	}
	
	public PinState getPinState(){
		return pin.getState();
	}

	@Override
	public Double getTemperature() {
		
		Double temp = tempSensor.getTemp();
		return temp;
	}
	
	private void setupLcd() throws InterruptedException, IOException{
		
		lcd = new GpioLcdDisplay(LCD_ROWS,          // number of row supported by LCD
                LCD_COLUMNS,       // number of columns supported by LCD
                RaspiPin.GPIO_06,  // LCD RS pin
                RaspiPin.GPIO_05,  // LCD strobe pin
                RaspiPin.GPIO_04,  // LCD data bit 1
                RaspiPin.GPIO_00,  // LCD data bit 2
                RaspiPin.GPIO_02,  // LCD data bit 3
                RaspiPin.GPIO_03); // LCD data bit 4           // LCD data bit 8 (set to 0 if using 4 bit communication)

		// clear LCD
        lcd.clear();
        
        // write line 1 to LCD
        //Lcd.lcdPosition (lcdHandle, 0, 0) ; 
        lcd.writeln(LCD_ROW_1, "Caixa Materna", LCDTextAlignment.ALIGN_CENTER) ;
        // write line 2 to LCD
        lcd.writeln(LCD_ROW_2, NetworkInfo.getIPAddresses()[0], LCDTextAlignment.ALIGN_CENTER);
	}

	@Override
	public boolean setDisplayText(int line, String text) {
		lcd.clear(line);
		lcd.writeln(line, text, LCDTextAlignment.ALIGN_CENTER);
		return false;
	}
	
	public boolean showClock(){
		
		if(displayThread != null && !displayThread.getName().contentEquals(DISPLAY_THREAD_CLOCK)){
			state = false;
		}
		
		if(displayThread == null || displayThread.isInterrupted()){
			state = true;
			displayThread = new Thread(new Runnable() {
				@Override
				public void run() {
					setDisplayText(LCD_ROW_1, "Caixa Materna");
					
					while(state){
						// update time every one second
			            // write time to line 2 on LCD
						setDisplayText(LCD_ROW_2, formatter.format(new Date()));
			            try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						}
			        }
					
				}
			});
	        displayThread.start();
		}
		return true;
	}
	
	public boolean showInfo(){
		
		if(displayThread != null && !displayThread.getName().contentEquals(DISPLAY_THREAD_INFO)){
			state = false;
		}
		
		if(displayThread == null || displayThread.isInterrupted()){
			state = true;
			displayThread = new Thread(new Runnable() {
				@Override
				public void run() {
					
					while(state){
			            try {
			            	lcd.clear();
			            	lcd.writeln(LCD_ROW_1, "IP ADDRESS", LCDTextAlignment.ALIGN_CENTER);
			            	lcd.writeln(LCD_ROW_2, NetworkInfo.getIPAddresses()[0], LCDTextAlignment.ALIGN_CENTER);
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						} catch (IOException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						}
			            
			            try {
			            	lcd.clear();
			            	lcd.writeln(LCD_ROW_1, "HOST NAME", LCDTextAlignment.ALIGN_CENTER);
			            	lcd.writeln(LCD_ROW_2, NetworkInfo.getHostname(), LCDTextAlignment.ALIGN_CENTER);
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						} catch (IOException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						}
			            
			            try {
			            	lcd.clear();
			            	lcd.writeln(LCD_ROW_1, "CPU TEMP.", LCDTextAlignment.ALIGN_CENTER);
			            	lcd.writeln(LCD_ROW_2, String.valueOf(SystemInfo.getCpuTemperature()), LCDTextAlignment.ALIGN_CENTER);
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						} catch (IOException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						}
			            
			            try {
			            	lcd.clear();
			            	lcd.writeln(LCD_ROW_1, "FREE MEM.", LCDTextAlignment.ALIGN_CENTER);
			            	lcd.writeln(LCD_ROW_2, String.valueOf(SystemInfo.getMemoryFree()), LCDTextAlignment.ALIGN_CENTER);
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						} catch (IOException e) {
							LOGGER.error("Error on CLOCK LCD Update: ", e);
						}
			        }
					
				}
			});
			displayThread.setName(DISPLAY_THREAD_INFO);
	        displayThread.start();
		}
		return true;
	}

}