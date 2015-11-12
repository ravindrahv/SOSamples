package com.example.so.statistics;


/**
 * <p> Code snippet to calculate statistical-mean [(x1+x2+x3+....xn)/n] using a variable of the same bit-length (rather than requiring twice bit-length)</p>
 * <p> 12Nov2015 </p>
 * @author Ravindra HV
 *
 */
public class StatisticsUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		float[] inputOne = {1,2,3,4,5};
		System.out.println( calcStatisticalMeanUsingVariableOfSameBitLength(inputOne));
		float[] inputTwo = {1,3,5,7,11};
		System.out.println( calcStatisticalMeanUsingVariableOfSameBitLength(inputTwo) );
		float[] inputThree = {-6,-4,-2,1,3,5,7,11};
		System.out.println( calcStatisticalMeanUsingVariableOfSameBitLength(inputThree) );
	}
	
	
	public static float calcStatisticalMeanUsingVariableOfSameBitLength(float[] num) {
		double sum = num[0];
		float tempMean = num[0];
		for(int i=1; i<num.length;i++) {
			tempMean = tempMean + (num[i]-tempMean)/(i+1);
			sum = sum + num[i];
		}
		System.out.println(tempMean + " : " + (sum/num.length) );
		return tempMean;
	}

}
