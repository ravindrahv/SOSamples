package com.example.so.dp.singeton;


/**
 * <p> Singleton proof-of-concept class that resists instantiation using reflection. This, ideally, should never be required. </p>
 * <p> The approach however is useful to detect use of reflection to override the pattern and for troubleshooting. </p>
 * <p> Use with caution. With Great Power Comes Great Responsibility. </p>
 * <p> 03 Jan 2016 </p>
 * @author Ravindra HV 
 */
public class StrictSingleton {
	
	/*
	 * -- // the 'final' keyword is used to ensure that the reference object will not be overridden to null using reflection
	 */
	private static final StrictSingleton DEFAULT_INTANCE = new StrictSingleton();
	
	/*
	 * -- // 'private' constructor is used to prevent direct instantiation and hence to indicate that this class is intended to be a singleton. 
	 */
	private StrictSingleton() {
		/*
		 * Logic to detect use of reflection
		 */
		if(DEFAULT_INTANCE != null) {
			System.out.println("Unexpected singleton constructor access. "+StrictSingleton.class);
			String message = StrictSingleton.class + "is intended to be a strict-singleton!";
			throw new RuntimeException(message);
		}
	}
	
	
	public static StrictSingleton defaultInstance() {
		return DEFAULT_INTANCE;
	}
	
	public void sayHello() {
		System.out.println("HelloWorld!");
	}

}
