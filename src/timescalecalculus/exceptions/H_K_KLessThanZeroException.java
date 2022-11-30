package timescalecalculus.exceptions;

/**
 * 
 * The case for when the k in TimeScale.h_k is less than 0
 * 
 * @author Richard Williams
 * @since 11/28/2022
 */
public class H_K_KLessThanZeroException extends TimeScaleException {

	public H_K_KLessThanZeroException() {
		super("h_k() - k cannot be less than 0");
	}
	
}
