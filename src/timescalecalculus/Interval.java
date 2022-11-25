package timescalecalculus;

/**
 * 
 * A representation of a closed interval
 * 
 * @author Richard Williams
 * @since 11/24/2022
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
}
