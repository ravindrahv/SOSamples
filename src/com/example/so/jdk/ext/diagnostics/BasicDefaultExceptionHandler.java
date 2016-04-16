package com.example.so.jdk.ext.diagnostics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
 * @author Ravindra HV
 * @since 2016/04/17
 * @version 0.2
 */
public class BasicDefaultExceptionHandler {
	
	public static final String SYSTEM_PROPERTIES_KEY__FILE_PATH_TO_LOG = "FILE_PATH_TO_LOG";
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	static {
		handleStaticBlock();
	}

	private BasicDefaultExceptionHandler() {
	}

	private static void handleStaticBlock() {
		String message = "Inside "+BasicDefaultExceptionHandler.class.getSimpleName()+"#handleStaticBlock() ..";
		handlePrintln(message);
		handleRegisterUncaughtExceptionHandler();
	}
	
	private static PrintStream streamToLog() {
		PrintStream printStream = null;
		String logToFile = System.getProperty(SYSTEM_PROPERTIES_KEY__FILE_PATH_TO_LOG);
		if(logToFile != null && logToFile.trim().length() > 0 ) {
			File file = new File(logToFile);
			
			if( ! file.exists() ) {
				handlePrintln("File :"+file +" not found. Defaulting to console logging!");
			}
			else if( ! file.canWrite() ) {
				handlePrintln("File :"+file +" cannot be written to. Defaulting to console logging!");
			}
			else {
				try {
					printStream = new PrintStream(file);
					handlePrintln("Using file (always open and resets on jvm start):"+file.getAbsoluteFile());
				} catch (FileNotFoundException e) {
					String errorMessage = "Error while creating file stream. Defaulting to console logging!";
					handlePrintln(errorMessage);
					e.printStackTrace();
				}
			}
		}
		
		if( printStream == null ){
			printStream = System.err;
			handlePrintln("Using console.");
		}
		return printStream;
	}
	
	
	private static void handlePrintln(Object message) {
		String prefix = new Date() +". "+BasicDefaultExceptionHandler.class+": ";
		String messageToLog = prefix + message;
		System.out.println(messageToLog);
	}
	
	
	private static void handleRegisterUncaughtExceptionHandler() {
		
		Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		if( defaultUncaughtExceptionHandler == null ) {
			Thread.UncaughtExceptionHandler customUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
				PrintStream printStream = streamToLog();
				
				public void uncaughtException(Thread t, Throwable e) {
					String message =new Date()+ ". ("+ Thread.currentThread().getName() +")" + ". Handling uncaught-exception from thread :"+t.getName()+". Error Message is :"+e;
					printStream.append(message);
					printStream.append(NEW_LINE);
					e.printStackTrace(printStream);
					printStream.append(NEW_LINE);
					printStream.flush();
				}
			};
			Thread.setDefaultUncaughtExceptionHandler(customUncaughtExceptionHandler);
			handlePrintln("Registered un-caught exception handler.");
		}
		else {
			handlePrintln("Found existing DefaultUncaughtExceptionHandler implementation :"+defaultUncaughtExceptionHandler.getClass()+". Aborting !");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String message ="This class is intended to be used for registering a default-uncaught-exception-handler, "+NEW_LINE;
		message = message + "given that one is not already present at the point of the class being loaded.";
		handlePrintln(message);
		
		String usageOptions = " Usage : "+NEW_LINE;
		usageOptions = usageOptions + "java -cp DefaultExceptionHandler.jar com.example.application.Main"+NEW_LINE;
		usageOptions = usageOptions + "java -cp DefaultExceptionHandler.jar -DFILE_PATH_TO_LOG=/path/to/file.log com.example.application.Main"+NEW_LINE;
		handlePrintln(usageOptions);
		handleTestUnhandledExceptionLogging();
	}
	
	
	private static void handleTestUnhandledExceptionLogging() {
		Runnable runnableCustom = new Runnable() {
			
			public void run() {
				String message = "Testing unhandled-exception-logging...";
				throw new RuntimeException(message);
			}
		};
		
		Thread threadCustom = new Thread(runnableCustom);
		threadCustom.start();
		
		Runnable runnableNPE = new Runnable() {
			
			public void run() {
				Object test = null;
				test.getClass();
			}
		};
		
		Thread threadNPE = new Thread(runnableNPE);
		threadNPE.start();
		
	}

}
