package impl;

import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import sun.security.x509.X500Name;


public class MyCertificateVerifier implements IceSSL.CertificateVerifier
{
	public boolean verify(IceSSL.NativeConnectionInfo info)
	{
		X509Certificate cert0 = (X509Certificate)info.nativeCerts[0];
        if (info.nativeCerts[0] != null)
        {
    		System.out.println("**** verify:" + cert0.getSubjectDN());

    		X500Principal p = cert0.getIssuerX500Principal();
    		try {
				cert0.checkValidity();
			} catch (CertificateExpiredException e) {
				System.err.println("--------- ERROR: CA Expired");
				e.printStackTrace();
			} catch (CertificateNotYetValidException e) {
				System.err.println("--------- ERROR: CA Not Valid");
				e.printStackTrace();
			}
    		
    		System.out.println(cert0.getPublicKey());
    		System.out.println(cert0.getSerialNumber());
    		System.out.println(cert0.getSubjectUniqueID());
    		System.out.println(cert0.getSubjectDN());
    		System.out.println(p.getName());
    		
			if (true)
			{
				return true;			
			}
		}
		System.err.println("--------- ERROR: Incorrect CA");
		return false;
	}
}

