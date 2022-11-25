package timescalecalculus;

/**
 * 
 * A representation of a closed interval
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class Interval {

	private double leftEndpoint;
	private double rightEndpoint;
	
	/**
	 * Constructs a closed interval with the given endpoints.
	 * <br>
	 * leftEndpoint <= rightEndpoint
	 * @param leftEndpoint
	 * @param rightEndpoint
	 */
	public Interval(double leftEndpoint, double rightEndpoint) {
		this.leftEndpoint = leftEndpoint;
		this.rightEndpoint = rightEndpoint;
	}
	
	/**
	 * @param t
	 * @return whether or not t is contained in this interval
	 */
	public boolean contains(double t) {
		return leftEndpoint <= t && t <= rightEndpoint;
	}
	
	/**
	 * @return the right endpoint of this interval
	 */
	public double getRightEndpoint() {
		return rightEndpoint;
	}
	
	/**
	 * @return the left endpoint of this interval
	 */
	public double getLeftEndpoint() {
		return leftEndpoint;
	}
	
	/**
	 * @param interval
	 * @return whether the given interval can be "combined" with this interval
	 */
	public boolean canMerge(Interval interval) {
		return contains(interval.getLeftEndpoint()) || contains(interval.getRightEndpoint());
	}
	
	/**
	 * @param rightEndpoint - the new rightEndpoint of the interval. Must be >= leftEndpoint
	 * @return a new interval with an identical left endpoint and new right endpoint
	 */
	public Interval setRightEndpoint(double rightEndpoint) {
		return new Interval(this.leftEndpoint, rightEndpoint);
	}
	
	/**
	 * @param leftEndpoint - the new left endpoint of the interval. Must be <= rightEndpoint
	 * @return a new interval with an identical rightt endpoint and new left endpoint
	 */
	public Interval setLeftEndpoint(double leftEndpoint) {
		return new Interval(leftEndpoint, this.rightEndpoint);
	}
}
