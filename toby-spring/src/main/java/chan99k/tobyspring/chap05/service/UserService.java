package chan99k.tobyspring.chap05.service;

import java.util.List;
import java.util.Properties;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import chan99k.tobyspring.chap05.dao.UserDao;
import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Setter;

@Setter
public class UserService {
	public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	private UserDao userDao;
	private PlatformTransactionManager txManager;
	private MailSender mailSender;

	public void upgradeLevels() {
		TransactionStatus status = this.txManager.getTransaction(new DefaultTransactionDefinition());
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			this.txManager.commit(status);
		} catch (Exception e) {
			this.txManager.rollback(status);
			throw e;
		}
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		return switch (currentLevel) {
			case BASIC -> (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
			case SILVER -> (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD -> false;
		};
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEmail(user);
	}

	private void sendUpgradeEmail(User user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setFrom("useradmin@ksug.org");
		message.setSubject("User upgrade 안내");
		message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드되었습니다.");

		this.mailSender.send(message);
	}

	private void legacySendUpgradeEmail(User user) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.ksug.org");
		Session s = Session.getInstance(props, null);

		MimeMessage message = new MimeMessage(s);
		try {
			message.setFrom(new InternetAddress("useradmin@ksug.org"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setSubject("Upgrade 안내");
			message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드되었습니다.");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
