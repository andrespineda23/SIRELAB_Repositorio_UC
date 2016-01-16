/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.Persona;
import com.sirelab.utilidades.Utilidades;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
public class EnvioCorreo {

    static Logger logger = Logger.getLogger(EnvioCorreo.class);
    public EnvioCorreo() {
    }

    /**
     * Metodo encargado de enviar via e-mail al usuario que solicito la
     * recuperación de la contraseña un correo con la información de su nueva
     * contraseña
     *
     * @param personaRecuperada Usuario que solicita el cambio de contraseña
     */
    public void enviarCorreoRecuperacion(Persona personaRecuperada, String nuevaContrasenia) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("proyecto.sirelab@gmail.com", "ucentral");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("proyecto.sirelab@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(personaRecuperada.getEmailpersona()));
            message.setSubject("Recuperación de Contraseña - SIRELAB UC");
            message.setText("Se solicito la recuperación de la contraseña de SIRELAB, la contraseña restaurada es la siguiente: " + nuevaContrasenia + " . Se solicita ingresar al sistema y cambiar la contraseña.");

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Error envio de correo: "+e.toString());
        }
    }

    public void enviarCorreoCreacionCuenta(String correo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("proyecto.sirelab@gmail.com", "ucentral");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("proyecto.sirelab@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
            message.setSubject("Creación Cuenta - SIRELAB UC");
            message.setText("Se ha creado una cuenta al sistema SIRELAB - UC (Sistema de información de recursos de laboratorio - Universidad Central). Sus credenciales de ingreso (usuario y contraseña) son su numero de indentificación, recuerde realizar el cambio de su contraseña al momento de ingresar al sistema.");
            Transport.send(message);
            
        } catch (MessagingException e) {
            logger.error("Error envio de correo: "+e.toString());
        }
    }

}
