package org.microservice.utils.service;

import com.google.zxing.WriterException;
import org.microservice.utils.util.dto.LoanDTO;

import java.io.IOException;

public interface UtilService {

    String generateQRCodeBase64(String data, int width, int height) throws WriterException, IOException;
    String generateQRCodeBase64(LoanDTO data, int width, int height) throws WriterException, IOException;
    String uploadImageImageBB(String base64) throws IOException, InterruptedException;
}
