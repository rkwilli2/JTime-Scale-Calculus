package timescalecalculus;

import timescalecalculus.exceptions.*;

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
				// F(x) = x^3 + x^2
				// int_{0}^{2} f = 2^3 + 2^2 - 0
				//				 = 8 + 4 = 12
			
			System.out.print("F, T=R for [0,2] = ");
			System.out.println(reals.deltaIntegral(f, 0, 2));
			
			// T = Z
				// F(x) = sum from i=0 to 1 of f(i)
				// int_{0}^{2} f = 3(0)^2 + 2(0) + 3(1)^2 + 2(1)
				// 				 =  0 + 3 + 2 = 5
			System.out.print("F, T=Z for [0,2] = ");
			System.out.println(ints.deltaIntegral(f, 0, 2));
			
			// T = ts
				// int_{0}^{2} f = int{0}^{1} f + int_{1}^{2} f
				//			     = (1)^3 + (1)^2 + 3(1)^2 + 2(1)
				//			     = 1 + 1 + 3 + 2 = 7
			System.out.print("F, T=R for [0,2] = ");
			System.out.println(ts.deltaIntegral(f, 0, 2));

		} catch (TimeScaleException tse) {
			tse.printStackTrace();
		}

	}

}
