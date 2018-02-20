package com.example.pkix.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * <p> 
 * Given a sequence of certificate paths, validates if the preceding certificate has signed the succeeding certificate. 
 * Eg: root.cer -> intermediate.cer -> final.cer 
 * (where "->"  is to be read as "has signed")
 * </p>
 * @author Ravindra
 * @since 20 Feb 2018
 *
 */
public class X509CertificateChainValidator {
	
	public static final String CERTIFICATE_TYPE_X509 = "X.509";
	
	
	public static void main(String[] args) {
		
		if(args == null || args.length < 3) {
			printUsage();
		}
		
		System.out.println();
		System.out.println("Args :"+Arrays.toString(args));
		System.out.println();

		
		List<String> inputArgs = extractInput(args);
		System.out.println("Args-Two :" +inputArgs);
		
		handleChainValidation(inputArgs);
	}
	
	private static void printUsage() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Usage : ");
		stringBuilder.append("$java -jar validate-cert-chain.jar  ");
		stringBuilder.append(X509CertificateChainValidator.class.getName());
		stringBuilder.append(" /path/to/root.cer ");
		stringBuilder.append(" /path/to/intermediate.cer ");
		stringBuilder.append(" /path/to/final.cer ");
		
		System.out.println(stringBuilder.toString());
	}
	
	private static List<String> extractInput(String[] args) {
		List<String> inputArgs = Arrays.asList(args);
		return inputArgs;
	}
	
	
	private static void handleChainValidation(List<String> certs) {
		
		for(int i=0; i<certs.size(); i++) {
			System.out.println();
			
			if( certs.size() > 1 && (i+1) == certs.size() ) {
				System.out.println("Done!");
				break;
			}
			Certificate certificateIssuerTemp = loadCertificate(certs.get(i));
			Certificate certificateSubjectTemp = (certs.size() == 1) ? certificateIssuerTemp : loadCertificate(certs.get(i+1));
			boolean validationResultTemp = validateCertificate(certificateIssuerTemp, certificateSubjectTemp);

			X509Certificate x509CertificateSubjectTemp = (X509Certificate) certificateSubjectTemp;
			X509Certificate x509CertificateIssuerTemp = (X509Certificate) certificateIssuerTemp;

			System.out.println("["+x509CertificateSubjectTemp.getSubjectDN()+"] is signed by ["+x509CertificateIssuerTemp.getSubjectDN()+"] ?"+validationResultTemp);
		}
		System.out.println();
		
	}
	
	private static boolean validateCertificate(Certificate issuer, Certificate subject) {
		boolean result = false;
		try {
			subject.verify(issuer.getPublicKey());
			result=true;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static Certificate loadCertificate(String certPath) {
		
		Certificate certificateResult = null;
		boolean hasErrors = false;
		File certFile = new File(certPath);
		if( ! certFile.exists() ) {
			System.out.println("File not found at :"+certPath);
			return null;
		}
		
		FileInputStream fileInputStream = null;
		try {
			hasErrors=true;
			fileInputStream = new FileInputStream(certFile);
			hasErrors=false;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if( hasErrors) {
			return null;
		}
		
		CertificateFactory certificateFactory = null;
		
		try {
			hasErrors=true;
			certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE_X509);
			certificateResult = certificateFactory.generateCertificate(fileInputStream);
			hasErrors=false;
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		
		if( hasErrors) {
			return null;
		}
		
		return certificateResult;
		
	}

}
