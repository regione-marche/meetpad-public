package conferenza.firma.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.europa.esig.dss.DSSDocument;
import eu.europa.esig.dss.DSSException;
import eu.europa.esig.dss.FileDocument;
import eu.europa.esig.dss.InMemoryDocument;
import eu.europa.esig.dss.client.crl.OnlineCRLSource;
import eu.europa.esig.dss.client.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.client.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.validation.SignedDocumentValidator;
import eu.europa.esig.dss.validation.policy.rules.Indication;
import eu.europa.esig.dss.validation.reports.Reports;
import eu.europa.esig.dss.validation.reports.SimpleReport;
import eu.europa.esig.dss.x509.CertificateSource;


@Service
public class DSSValidationService {
	private static final Logger log = LoggerFactory.getLogger(DSSValidationService.class.getName());
	
    public boolean validateBytesArray(byte[] bytesArray)
    {
        DSSDocument dssDocument = new InMemoryDocument(bytesArray);
        return validateDSSDcoument(dssDocument);
    }


    public boolean validateFile(File file)
    {
        DSSDocument dssDocument = new FileDocument( file );
        return validateDSSDcoument(dssDocument);
    }
    
    private boolean validateDSSDcoument(DSSDocument dssDocument)
    {
        boolean result = false;

        CertificateSource trustedCertSource = null;
        CertificateSource adjunctCertSource = null;

        try
        {
            CertificateVerifier certificateVerifier = new CommonCertificateVerifier();
            certificateVerifier.setDataLoader(new CommonsDataLoader());
            certificateVerifier.setOcspSource(new OnlineOCSPSource());
            certificateVerifier.setCrlSource(new OnlineCRLSource());
            certificateVerifier.setTrustedCertSource(trustedCertSource);
            certificateVerifier.setAdjunctCertSource(adjunctCertSource);

            SignedDocumentValidator documentValidator = SignedDocumentValidator.fromDocument(dssDocument);
            documentValidator.setCertificateVerifier(certificateVerifier);

            Reports reports = documentValidator.validateDocument();

            SimpleReport simpleReport = reports.getSimpleReport();

            int signaturesCount = simpleReport.getSignaturesCount();
            int validSignaturesCount = simpleReport.getValidSignaturesCount();
            if (signaturesCount == 0)
            {
                log.info("===>>>IL DOCUMENTO NON È FIRMATO<<<===");
                System.err.println("===>>>IL DOCUMENTO NON È FIRMATO<<<===");
                result = false;
            }
            else
            {
                if (validSignaturesCount == 0)
                {
                    log.info("===>>>IL DOCUMENTO CONTIENE FIRME DIGITALI NON VALIDE<<<===");
                    System.err.println("===>>>IL DOCUMENTO CONTIENE FIRME DIGITALI NON VALIDE<<<===");
                    result = true;
                }
                else
                {
                    log.info("===>>>IL DOCUMENTO CONTIENE FIRME DIGITALI VALIDE<<<===");
                    System.err.println("===>>>IL DOCUMENTO CONTIENE FIRME DIGITALI VALIDE<<<===");
                    result = true;
                }

                // Indication.TOTAL_PASSED
                Indication indication = simpleReport.getIndication(simpleReport.getFirstSignatureId());
                log.info("===>>>ESITO: " + indication.name() + "<<<===");
            }
        }
        catch (DSSException exc)
        {
            System.err.println("===>>>IL DOCUMENTO EXCEPTION<<<===" + exc.getMessage());
            if (exc.getMessage().equalsIgnoreCase("Document format not recognized/handled")){
                throw new DSSFormatNotRecognizedException("Il formato del file fornito non è verificabile");
            }
            else {
                throw exc;
            }
        }
        return result;
    }
    
}
