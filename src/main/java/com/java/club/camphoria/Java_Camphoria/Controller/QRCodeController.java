package com.java.club.camphoria.Java_Camphoria.Controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.java.club.camphoria.Java_Camphoria.Model.QRCodeEntity;
import com.java.club.camphoria.Java_Camphoria.Repo.QRCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class QRCodeController {

    @Autowired
    QRCodeRepository qrCodeRepository;

    @PostMapping("/generateQR")
public ResponseEntity<byte[]> generateQRCode(@RequestBody String userId, @RequestBody String eventId) {
    try {
if(userId == null || eventId == null)
  System.out.println("No");
else
System.out.println("userId = " +userId+ "eventId = " +eventId);

        String qrContent = userId + "|" + eventId + "|" + System.currentTimeMillis();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300, hints);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        byte[] qrBytes = baos.toByteArray();

        // Set filename
        String filename = userId + "_" + eventId + ".png";

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename) // Forces download with custom filename
                .header("Content-Type", "image/png")
                .body(qrBytes);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);
    }

}



    @PostMapping("/validateQR")
    public ResponseEntity<String> validateQRCode(@RequestParam String qrContent) {
        Optional<QRCodeEntity> qrCodeEntity = qrCodeRepository.findByQrContent(qrContent);

        if (qrCodeEntity.isPresent()) {
            QRCodeEntity qr = qrCodeEntity.get();

            if (!qr.isUsed()) {
                qr.setUsed(true);
                qrCodeRepository.save(qr); // Mark QR as used
                return ResponseEntity.ok("✅ QR Code Validated Successfully!");
            } else {
                return ResponseEntity.status(400).body("⚠️ QR Code Already Used!");
            }
        } else {
            return ResponseEntity.status(400).body("❌ Invalid QR Code!");
        }
    }


    private void markQRAsUsed(String userId, String eventId) {
        System.out.println("OR Code marked as used for user : "+userId);
    }

    private boolean checkIfQRIsValid(String userId, String eventId) {
        return true;
    }

}



