package org.microservice.utils.service.implementation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.json.JSONObject;
import org.microservice.utils.service.UtilService;
import org.microservice.utils.util.dto.LoanDTO;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public String generateQRCodeBase64(String data, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);
        return getBase64(bitMatrix);
    }

    @Override
    public String generateQRCodeBase64(LoanDTO data, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, width, height, hintMap);
        return  getBase64(bitMatrix);
    }

    private String getBase64(BitMatrix bitMatrix) throws WriterException, IOException {
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        //return "data:image/png;base64,".concat(base64Image);
        return base64Image;
    }


    @Override
    public String uploadImageImageBB(String base64) throws IOException, InterruptedException {
        String url = "https://api.imgbb.com/1/upload?expiration=600&key=a3d1b74ce17fdaf5e53592b7bf47cbf2";
        String body = "------boundary123\r\n" +
                "Content-Disposition: form-data; name=\"image\"\r\n\r\n" +
                base64 + "\r\n" +
                "------boundary123--";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "multipart/form-data; boundary=----boundary123")
                .POST(BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.statusCode());
        //System.out.println(response.body());
        JSONObject jsonObject = new JSONObject(response.body());
        return jsonObject.getJSONObject("data").getString("display_url");
    }

}
