package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.repository.EmailRepository;
import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EnvioEmailService {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    JavaMailSender mailSender;

    public void enviarEmail(List<String> listDestinatarios, String corpoEmail, String tituloEmail) throws Exception {
        if (Objects.isNull(listDestinatarios) || listDestinatarios.isEmpty()) return;

        try {
            for (String destinatario : listDestinatarios) {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");

                mail.setTo(destinatario);
                mail.setSubject(tituloEmail);
                mail.setText(corpoEmail, true);

                mailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new Exception("Erro ao enviar e-mail.", e);
        }
    }


}
