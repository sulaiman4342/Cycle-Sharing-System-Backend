package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.dto.ResultDTO;
import com.example.CycleSharingSystemBackend.repository.Bikerepository;
import com.example.CycleSharingSystemBackend.service.QRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRServiceImpl implements QRService {

    private final Bikerepository bikerepository;

    @Autowired
    public QRServiceImpl(Bikerepository bikerepository) {
        this.bikerepository = bikerepository;
    }

//    @Override
//    public ResultDTO processQR() {
//        return null;
//    }

    @Override
    public ResultDTO processQR(Bikes bike) {
        // Generate QR code value based on bike information
        String qrValue = generateQRValue(bike);

        // Save QR code image
        saveQRCodeImage(qrValue);

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setQrValue(qrValue);
        return resultDTO;
    }

    private String generateQRValue(Bikes bike) {
        // Generate QR code value based on bike information
        return bike.getBikeId() + "|" + bike.getBrand() + "|" + bike.getBikeModel() + "|" + bike.getBikeCode();
    }

    private void saveQRCodeImage(String qrValue) {
        // Specify the directory to save the image
        String directoryPath = "./src/main/resources";

        try {
            // Create directory if it doesn't exist
            Files.createDirectories(FileSystems.getDefault().getPath(directoryPath));

            // Set QR code parameters
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 1);

            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(qrValue, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Create QR code image
            BufferedImage qrImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            qrImage.createGraphics();
            Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 200, 200);
            graphics.setColor(Color.BLACK);

            // Render QR code to image
            for (int i = 0; i < 200; i++) {
                for (int j = 0; j < 200; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // Save QR code image to file
            File qrFile = new File(directoryPath + File.separator + "qr_code.png");
            ImageIO.write(qrImage, "png", qrFile);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            // Handle exception accordingly
        }
    }
}
