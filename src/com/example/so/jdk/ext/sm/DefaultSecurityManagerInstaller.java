package com.example.so.jdk.ext.sm;

import java.util.Date;

/**
 * 
 * <p> When bundled in a jar and placed in - 'jre/lib/ext', is useful in ensuring that a default security manager is in 
 * place for all applications using the given installation. </p>
 * 
 * @author Ravindra HV
 * @since 2016/03/05
 * @version 0.2
 *
 */
public class DefaultSecurityManagerInstaller {
	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	/*
	 * Note : If a 'public static void main(String[] args){}' method is present in this class,
	 *  		then that is the one that is invoked when runnable jars are invoked rather than
	 *  		the method in the intended class!
	 */

	static {
		handleStaticBlockInit();
	}
	
	private static void handleStaticBlockInit() {
		printNote();
		if( System.getSecurityManager() == null ) {
			handlePrintln(DefaultSecurityManagerInstaller.class+":"+DefaultSecurityManagerInstaller.class.getProtectionDomain().getCodeSource());
			SecurityManager securityManager = new SecurityManager();
			System.setSecurityManager(securityManager);
			handlePrintln("Done installing security manager !");
			
		}
	}
	
	private static void printNote() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Checks if any java.lang.SecurityManager exists");
		stringBuilder.append(NEW_LINE);
		stringBuilder.append("Installs one if not present");
		stringBuilder.append(NEW_LINE);
		stringBuilder.append("This class should ideally be placed in jre/lib/ext");
		stringBuilder.append(NEW_LINE);
		stringBuilder.append("Primarily useful to identify reflection use within an application");
		stringBuilder.append(NEW_LINE);
		
		handlePrintln(stringBuilder.toString() );

	}
	
	private static void handlePrintln(Object message) {
		String prefix = new Date() +". "+DefaultSecurityManagerInstaller.class+": ";
		String messageToLog = prefix + message;
		System.out.println(messageToLog);
	}

	/*
	public static void main(String[] args) {
	}
	*/

}
