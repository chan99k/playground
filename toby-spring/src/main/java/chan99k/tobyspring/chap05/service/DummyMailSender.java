package chan99k.tobyspring.chap05.service;

import java.util.Arrays;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import lombok.Setter;

@Setter
public class DummyMailSender implements MailSender {
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		System.out.println(simpleMessage.toString());
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		System.out.println(Arrays.toString(simpleMessages));
	}
}
