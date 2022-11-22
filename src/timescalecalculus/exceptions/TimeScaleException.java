package timescalecalculus.exceptions;

/**
 * 
 * The superclass of all exceptions relevant to time scales.
 * 
 * @author Richard Williams
 * @since 11/22/2022
 */
public class TimeScaleException extends Exception {

	public TimeScaleException() {
		super("Time scale error!");
	}
	
	public TimeScaleException(String msg) {
		super(msg);
	}
}
