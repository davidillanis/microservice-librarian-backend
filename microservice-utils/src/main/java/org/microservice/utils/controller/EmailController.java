package org.microservice.utils.controller;

import org.microservice.utils.service.UtilService;
import org.microservice.utils.util.dto.EmailDTO;
import org.microservice.utils.util.dto.EmailFileDTO;
import org.microservice.utils.service.EmailService;
import org.microservice.utils.util.dto.EmailLoanDTO;
import org.microservice.utils.util.dto.LoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UtilService utilService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){

        System.out.println("Mensaje Recibido " + emailDTO);

        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){

        try {
            String fileName = emailFileDTO.getFile().getOriginalFilename();

            Path path = Paths.get("src/mail/resources/files/" + fileName);

            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();

            emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", fileName);

            return ResponseEntity.ok(response);

        } catch (Exception e){
            throw new RuntimeException("Error al enviar el Email con el archivo. " + e.getMessage());
        }
    }

    @PostMapping("/send-message-template")
    private ResponseEntity<?> receiveRequestEmailWithTemplate(@RequestBody EmailLoanDTO emailLoanDTO) {
        try {
            System.out.printf("Mensaje Recibido para el estudiante: {}", emailLoanDTO.getIdStudent());
            String urlImage= utilService.uploadImageImageBB(
                    utilService.generateQRCodeBase64(
                            new LoanDTO(emailLoanDTO.getIdLoan(), emailLoanDTO.getIdStudent(), emailLoanDTO.getCopyBookCode()),
                            300,
                            300));

            StringBuilder template = new StringBuilder();
            template.append("<!DOCTYPE html>")
                    .append("<html lang=\"es\">")
                    .append("<head>")
                    .append("<meta charset=\"UTF-8\">")
                    .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                    .append("<title>Solicitud Aceptada</title>")
                    .append("<style>")
                    .append("* { margin: 0; padding: 0; box-sizing: border-box; }")
                    .append("body { font-family: 'Arial', sans-serif; background-color: #f0f4f8; height: 100vh; display: flex; justify-content: center; align-items: center; margin: 0; }")
                    .append(".container { text-align: center; background-color: #ffffff; padding: 50px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); width: 600px; }")
                    .append("h1 { font-size: 2.5em; color: #333; margin-bottom: 20px; }")
                    .append("p { font-size: 1.2em; color: #666; margin-bottom: 30px; }")
                    .append(".button { display: inline-block; padding: 10px 20px; background-color: #3498db; color: white; text-decoration: none; border-radius: 5px; font-size: 1.1em; transition: background-color 0.3s ease; }")
                    .append(".button:hover { background-color: #2980b9; }")
                    .append("</style>")
                    .append("</head>")
                    .append("<body>")
                    .append("<div class=\"container\">")
                    .append("<h1>Hola ").append(emailLoanDTO.getIdStudent()).append("</h1>")
                    .append("<p>Tu solicitud fue aceptada para el libro con código: ").append(emailLoanDTO.getCopyBookCode()).append("</p>")
                    .append("<img src='").append(urlImage).append("' alt='QR Code'>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>");
            emailService.sendEmail(emailLoanDTO.getToUser(), emailLoanDTO.getSubject(), template.toString());

            Map<String, String> response = new HashMap<>();
            response.put("status", "Successful");

            return ResponseEntity.ok(response);
        } catch (MailException e) {
            System.out.printf("Error this send email: {}", e.getMessage());
            throw new RuntimeException("Error this send email: " + e.getMessage(), e);
        }  catch (Exception e) {
            System.out.printf("Error: {}", e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

}
