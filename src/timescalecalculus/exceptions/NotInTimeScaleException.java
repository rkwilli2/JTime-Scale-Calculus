package timescalecalculus.exceptions;

/**
 * 
 * The case for when an operation is done on an element that isn't
 * in a given scale. (i.e, sigma(2.5) for T = Z, mu(2) for T = R / Q, etc.)
 * 
 * @author Richard Williams
 * @since 11/22/2022
 */
public class NotInTimeScaleException extends TimeScaleException {

	public NotInTimeScaleException(double t) {
		super("Given t=" + t + " is not in the time scale.");
	}
	
}
