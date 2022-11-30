package timescalecalculus.exceptions;

/**
 * 
 * To be thrown hen some arbitrary mathutil.Function doesn't contain enough arguments.
 * 
 * @author Richard Williams
 * @since 11/29/2022
 */
public class NotEnoughArgumentsException extends TimeScaleException {

	public NotEnoughArgumentsException(int numArgs, int givenArgs) {
		super("Expected at least " + numArgs + " arguments, recieved " + givenArgs + " arguments");
	}
	
}
