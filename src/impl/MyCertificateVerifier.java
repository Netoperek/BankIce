package impl;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;


public class MyCertificateVerifier implements IceSSL.CertificateVerifier
{
	public boolean verify(IceSSL.NativeConnectionInfo info)
	{
		X509Certificate cert0 = (X509Certificate)info.nativeCerts[0];
        if (info.nativeCerts[0] != null)
        {
    		System.out.println("**** verify:" + cert0.getSubjectDN());

    		X500Principal p = cert0.getIssuerX500Principal();
			if (p.getName().indexOf("CN=Laboratorium Systemów Rozproszonych") != -1)
			{
				return true;			
			}
		}
		System.err.println("--------- ERROR: Incorrect CA");
		return false;
	}
}

