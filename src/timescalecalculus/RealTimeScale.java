package timescalecalculus;

/**
 * 
 * An implementation of the time scale of the real numbers
 * 
 * @author Richard Williams
 * @since 11/22/2022
 */
public class RealTimeScale extends TimeScale {
	
	@Override
	public boolean isInTimeScale(double t) {
		return true;
	}
	
	@Override
	public double sigma(double t) {
		return 0;
	}

	@Override
	public double rho(double t) {
		return 0;
	}

	@Override
	public TimeScale getTKappa() {
		// Sup{this} = INFINITY, so return this TimeScale
		return this;
	}

}
