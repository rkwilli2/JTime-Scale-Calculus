package timescalecalculus;

import timescalecalculus.exceptions.*;

/**
 * 
 * A demonstration on how to use the timescalecalculus package.
 * 
 * @author Richard Williams
 * @since 11/24/2022
 */
public class Demo {

	public static void main(String[] args) {

		try {

			TimeScale reals = new RealTimeScale();
			TimeScale ints = new IntegerTimeScale();

			// f(x) = x^3 + x^2
			Function f = (x) -> {
				return x * x * x + x * x;
			};
			
			// T = R
				// f'(x) = 3x^2 + 2x
				// f'(1) = 3 + 2 = 5
				// Output will be 5 + some error proportional to TimeScale.DIFF_STEP ^ 2

			System.out.print("f', T=R @ 1:\t");
			System.out.println(reals.deltaDerivative(f, 1));
			
			// T = Z
				// delta f(x) = (x+1)^3 + (x+1)^2 - (x^3 + x^2)
				// delta f(x) = x^3 + 3x^2 + 3x + 1 + x^2 + 2x + 1 - x^3 - x^2
				// delta f(x) = 3x^2 + 5x + 2
				// delta f(1) = 3 + 5 + 2 = 10
				// Output is 10
			
			System.out.print("f', T=Z @ 1:\t");
			System.out.println(ints.deltaDerivative(f, 1));

		} catch (TimeScaleException tse) {
			tse.printStackTrace();
		}

	}

}
