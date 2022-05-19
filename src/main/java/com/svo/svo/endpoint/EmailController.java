package com.svo.svo.endpoint;

import com.svo.svo.model.EmailValuesDTO;
import com.svo.svo.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/email/send")
    public ResponseEntity<?> sendEmail() {
        emailService.sendEmail();
        return new ResponseEntity("Correo enviado con exito", HttpStatus.OK);
    }

    /*

    */
    @PostMapping("/email/sendHTML")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        emailService.sendEmailTemplate(dto);
        return new ResponseEntity("Correo enviado con exito", HttpStatus.OK);
    }
}
