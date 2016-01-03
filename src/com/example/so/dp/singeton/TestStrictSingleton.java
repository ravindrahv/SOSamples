package com.example.so.dp.singeton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**
 * <p> Class to test Singleton </p>
 * <p> 03 Jan 2016 </p>
 * @author Ravindra HV
 *
 */
public class TestStrictSingleton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello!");
		testInstantiationThroughReflection();
		StrictSingleton.defaultInstance().sayHello();
		System.out.println("Bye!");
	}
	
	private static void testInstantiationThroughReflection(){
		System.out.println("Default Test");
		try {
			StrictSingleton.class.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Override Test");
		Constructor defualtConstructorRef = StrictSingleton.class.getDeclaredConstructors()[0];
		defualtConstructorRef.setAccessible(true);
		try {
			defualtConstructorRef.newInstance(new Object[]{});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		System.out.println("Resist null reset test");
		Field defaultInstanceField = StrictSingleton.class.getDeclaredFields()[0];
		defaultInstanceField.setAccessible(true);
		try {
			defaultInstanceField.set(null, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
	}

}



