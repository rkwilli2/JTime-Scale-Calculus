package timescalecalculus.timescales;

import java.util.ArrayList;
import java.util.List;

import mathutil.*;
import timescalecalculus.TimeScale;
import timescalecalculus.exceptions.*;

/**
 * 
 * Implementation for the arbitrary TimeScale made with a set of points and intervals
 * TODO make user friendly via auto-sorting the set when a new interval is added,
 * 		"merging" intervals when applicable upon insertion, etc.
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class DefaultTimeScale extends TimeScale {
	
	/**
	 * The list of intervals contained in this time scale.
	 * <br>
	 * It is not recommended that this should be used in time scales whose
	 * supremum is INFINITY. 
	 */
	protected List<Interval> set = null;
	
	/**
	 * Constructs an empty TimeScale.
	 */
	public DefaultTimeScale() {
		this.set = new ArrayList<Interval>();
	}
	
	/**
	 * Constructs a TimeScale from a given List of Intervals
	 * @param set - a List of a intervals that are already "merged" and sorted
	 */
	public DefaultTimeScale(List<Interval> set) {
		this.set = set;
	}
	
	@Override
	public boolean isInTimeScale(double t) {
		return getIndexOf(t) != -1;
	}

	@Override
	public Interval getIntervalFromValue(double t) throws NotInTimeScaleException {
		int index = getIndexOf(t);
		if(index == -1)
			throw new NotInTimeScaleException(t);
		return set.get(index);
	}
	
	@Override
	public double sigma(double t) throws NotInTimeScaleException {
		int index = getIndexOf(t);
		
		if(index != -1) {
			double rightEndpoint = set.get(index).getRightEndpoint();
			
			// 3 possibilities
			
			// 1.) t is the right endpoint of the interval
			// and t is not the supremum of this time scale
			if(t == rightEndpoint && index < set.size() - 1)
				return set.get(index + 1).getLeftEndpoint();
				
			// 2.) t is the right endpoint of the interval
			// and t is the supremum of this time time scale
			if(t == rightEndpoint && index == set.size() - 1)
				return t;
			
			// 3.) t is not the right endpoint of the interval
			return 0;
		}
		
		throw new NotInTimeScaleException(t);
	}

	@Override
	public double rho(double t) throws NotInTimeScaleException {
		int index = getIndexOf(t);
		
		if(index != -1) {
			double leftEndpoint = set.get(index).getLeftEndpoint();
			
			// 3 possibilities
			
			// 1.) t is the left endpoint of the interval
			// and t is not the infimum of this time scale
			if(t == leftEndpoint && index > 0)
				return set.get(0).getRightEndpoint();
			
			// 2.) t is the left endpoint of the interval
			// and t is the infimum of this time scale
			if(t == leftEndpoint && index == 0)
				return t;
			
			// 3.) t is not the left endpoint of the interval
			return 0;
		}
		
		throw new NotInTimeScaleException(t);
	}

	@Override
	public TimeScale getTKappa() {
		
		// Sup{this} < INFINITY, so return this \ (rho(sup{this}), sup{this})
		// TODO Implement this
		return this;
	}
	
	/**
	 * @return the supremum of this time scale
	 */
	public double getSupremum() {
		return set.get(set.size() - 1).getRightEndpoint();
	}
	
	/**
	 * @return the infimum of this time scale
	 */
	public double getInfimum() {
		return set.get(0).getLeftEndpoint();
	}
	
	/**
	 * Adds a new interval to this time scale
	 * @param interval
	 */
	public void addInterval(Interval interval) {
		set.add(interval);
	}
	
	/**
	 * Finds the index in this.set where a given value lives.
	 * @param t - some arbitrary real number
	 * @return the index where t lives. -1 if it's not in set
	 */
	private int getIndexOf(double t) {
		
		// Uses linear search, probably should use a better one TODO consider better searches
		for(int i = 0; i < set.size(); i++)
			if (set.get(i).contains(t))
				return i;
		return -1;
	}
}
