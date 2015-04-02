package com.example.so.physics;

import java.math.BigInteger;

/**
 * @author Ravindra HV
 * @see http://physics.stackexchange.com/a/173013/31729
 */
public class GravityAccelerationSimulator {
	
	public static final double GRAVITATIONAL_CONSTANT = 6.67384/100000000000L;
	public static final BigInteger MASS_EXPONENT = BigInteger.valueOf(10).pow(24);
	
	/*
	 * http://nssdc.gsfc.nasa.gov/planetary/factsheet/
	 */
	public enum PlanetaryBody {
		MERCURY(0.330, 4879),
		VENUS(4.87, 12104),
		EARTH(5.97, 12756)
		;
		
		private double mass; //  to be multiplied by 10^24
		private double diameter; // in km
		
		/**
		 * @param mass (10^24)
		 * @param diameter (km)
		 */
		private PlanetaryBody(double mass, double diameter) {
			this.mass=mass;
			this.diameter=diameter;
		}
		
		public double getDiameter() {
			return diameter;
		}
		
		public double getMass() {
			return mass;
		}
		
		public double surfaceGravity() {
			double gravitationalConstantSansExponent = 6.67384;
			double radius = (diameter/2.0);
			double gravity = (gravitationalConstantSansExponent*mass)/(radius*radius)*(10000000);
			return gravity;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Start !");

		//System.out.println(MASS_EXPONENT);
		
		System.out.println("GravitationalConstant : "+GRAVITATIONAL_CONSTANT);
		System.out.println("Mercury - SurfaceGravity, Mass(x 10^24), Diameter(km) : "+PlanetaryBody.MERCURY.surfaceGravity() + ","+PlanetaryBody.MERCURY.getMass() + "," + PlanetaryBody.MERCURY.getDiameter() );
		System.out.println("Venus - SurfaceGravity, Mass(x 10^24), Diameter(km) : "+PlanetaryBody.VENUS.surfaceGravity() + ","+PlanetaryBody.VENUS.getMass() + "," + PlanetaryBody.VENUS.getDiameter() );
		System.out.println("Earth - SurfaceGravity, Mass(x 10^24), Diameter(km) : "+PlanetaryBody.EARTH.surfaceGravity() + ","+PlanetaryBody.EARTH.getMass() + "," + PlanetaryBody.EARTH.getDiameter() );
		
		double venusEarthDistance=7500;
		double mercuryEarthDistance=7500;
		double distanceLimit=6500;
		runTest(venusEarthDistance, mercuryEarthDistance, distanceLimit);

		System.out.println("Done !");
		
	}
	
	/**
	 * 
	 * venus, earth, mercury 
	 * 
	 * @param venusEarthDistance
	 * @param mercuryEarthDistance
	 */
	public static void runTest(double venusEarthDistance, double mercuryEarthDistance, double distanceLimit) {
		
		
		if( ((venusEarthDistance > distanceLimit) && (mercuryEarthDistance > distanceLimit)) == false) {
			System.out.println("Distance limit reached :"+distanceLimit);
			return;
		}
		
		System.out.println("*******************");
		double cogDistVenusAndEarth = venusEarthDistance;
		double cogDistEarthAndMercury = mercuryEarthDistance;
		double cogDistVenusAndMercury = cogDistVenusAndEarth+cogDistEarthAndMercury;
		
		double gravityVenusToEarth = accelerationDueToGravity(PlanetaryBody.VENUS, cogDistVenusAndEarth);
		double gravityVenusToMercury = accelerationDueToGravity(PlanetaryBody.VENUS, cogDistVenusAndMercury);

		double gravityEarthToVenus = accelerationDueToGravity(PlanetaryBody.EARTH, cogDistVenusAndEarth);
		double gravityEarthToMercury = accelerationDueToGravity(PlanetaryBody.EARTH, cogDistEarthAndMercury);

		double gravityMercuryToEarth = accelerationDueToGravity(PlanetaryBody.MERCURY, cogDistEarthAndMercury);
		double gravityMercuryToVenus = accelerationDueToGravity(PlanetaryBody.MERCURY, cogDistVenusAndMercury);

		System.out.println("cogDistVenAndEar : "+cogDistVenusAndEarth);
		System.out.println("cogDistEarAndMer : "+cogDistEarthAndMercury);
		System.out.println("cogDistVenAndMer : "+cogDistVenusAndMercury);
		
		System.out.println("gvtyVenToEar : "+gravityVenusToEarth);
		System.out.println("gvtyAcclnVenToMer : "+gravityVenusToMercury);
		
		System.out.println("gvtyAcclnEarToVen : "+gravityEarthToVenus);
		System.out.println("gvtyEarToMer : "+gravityEarthToMercury);

		System.out.println("gvtyMerToEar : "+gravityMercuryToEarth);
		System.out.println("gvtyMerToVen : "+gravityMercuryToVenus);

		cogDistVenusAndEarth = cogDistVenusAndEarth - (gravityVenusToEarth + gravityEarthToVenus) - gravityMercuryToVenus + gravityMercuryToEarth;
		cogDistEarthAndMercury = cogDistEarthAndMercury - (gravityEarthToMercury + gravityMercuryToEarth) - gravityVenusToMercury + gravityVenusToEarth;

		if( ((cogDistVenusAndEarth > distanceLimit) && (cogDistEarthAndMercury > distanceLimit)) == false) {
			System.out.println("COG - Venus, Earth : "+venusEarthDistance +" - " + gravityVenusToEarth + " - " +gravityEarthToVenus + " - " + gravityMercuryToVenus + " + " +  gravityMercuryToEarth);
			System.out.println("COG - Earth, Mercury : "+mercuryEarthDistance +" - " + gravityEarthToMercury + " - " +gravityMercuryToEarth + " - " + gravityVenusToMercury + " + " +  gravityVenusToEarth);
		}

		runTest(cogDistVenusAndEarth, cogDistEarthAndMercury, distanceLimit);

	}
	
	
	
	public static double accelerationDueToGravity(PlanetaryBody body, double cogDistance) {
		double gravitationalConstantSansExponent = 6.67384;
		double gravity = (gravitationalConstantSansExponent*body.getMass())/(cogDistance*cogDistance)*(10000000);
		return gravity;
	}

}
