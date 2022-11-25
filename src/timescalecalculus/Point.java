package timescalecalculus;

/**
 * 
 * Represents an isolated point in the form of interval
 * 
 * @author Richard Williams
 * @since 11/24/2022
 */
public class Point extends Interval {

	public Point(double value) {
		super(value, value);
	}
}
