package timescalecalculus;

import mathutil.Function;
import mathutil.Interval;
import timescalecalculus.exceptions.*;
import timescalecalculus.timescales.DefaultTimeScale;
import timescalecalculus.timescales.IntegerTimeScale;
import timescalecalculus.timescales.RealTimeScale;

/**
 * 
 * A demonstration on how to use the timescalecalculus package.
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class Demo {

	public static void main(String[] args) {

		try {

			TimeScale reals = new RealTimeScale();
			TimeScale ints = new IntegerTimeScale();
			DefaultTimeScale ts = new DefaultTimeScale();
			
			for(int i = 0; i < 5; i += 2)
				ts.addInterval(new Interval(i, i+1));
			
			// ts = [0,1], [2,3], [4,5]
			
			// f(x) = 3x^2 + 2x
			Function f = (x) -> {
				return 3*x*x + 2*x;
			};
			
			// T = R
				// f' 	 = 6x + 2
				// f'(2) = 14
			System.out.print("T=R  @ 2, f'= ");
			System.out.println(reals.deltaDerivative(f, 2));
			
			
			// T = Z
				// delta f    = 3(x+1)^2 + 2(x+1) - 3x^2 - 2x
				//		      = 6x + 5
			    // delta f(1) = 11
			System.out.print("T=Z  @ 1, f'= ");
			System.out.println(ints.deltaDerivative(f, 1));
			
			// T = ts
				// f delta(2) = 14
				// f delta(1) = 11
			System.out.println("T=ts @ 2, f'= " + ts.deltaDerivative(f, 2));
			System.out.println("T=ts @ 1, f'= " + ts.deltaDerivative(f, 1));

		} catch (TimeScaleException tse) {
			tse.printStackTrace();
		}

	}

}
