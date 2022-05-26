package com.svo.svo.service.impl;

import com.svo.svo.model.EmailValuesDTO;
import com.svo.svo.model.TusuariosVO;
import com.svo.svo.repository.TusuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    private TusuariosRepository tusuariosRepository;

    @Value("${mail.urlFront}")
    private String urlFront;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lopezvazquezoline@gmail.com");
        message.setTo("lopezvazquezoline@gmail.com");
        message.setSubject("Prueba de envio de correo simple");
        message.setText("Esto es el contenido del email");

        javaMailSender.send(message);
    }

    public void sendEmailTemplate(EmailValuesDTO dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();

            TusuariosVO usuario = tusuariosRepository.findCorreo(dto.getMailTo());
            if (usuario != null) {
                model.put("userName", usuario.getIdPersona().getNombre());
                model.put("url", urlFront + dto.getMailTo());

                context.setVariables(model);

                String htmlText = templateEngine.process("email-template", context);
                helper.setFrom("lopezvazquezoline@gmail.com");//"lopezvazquezoline@gmail.com"
                helper.setTo(dto.getMailTo());
                helper.setSubject("Recuperar contraseña");
                helper.setText(htmlText, true);
                javaMailSender.send(message);
            } else {
                throw new RuntimeException("El correo no esta asociado a ninguna cuenta");
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
