package com.svo.svo.endpoint;

import com.svo.svo.model.EmailValuesDTO;
import com.svo.svo.other.Utils.ResponseBody;
import com.svo.svo.other.Utils.Utils;
import com.svo.svo.repository.TusuariosRepository;
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
    public ResponseEntity<ResponseBody<String>> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        ResponseEntity<ResponseBody<String>> res = null;
        try {
            emailService.sendEmailTemplate(dto);
            res = Utils.response200OK("Correo enviado con exito");
        } catch (Exception e) {
            res = Utils.response(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }

        return res;
    }
}
