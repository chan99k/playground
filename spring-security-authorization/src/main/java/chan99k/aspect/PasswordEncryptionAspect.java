package chan99k.aspect;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import chan99k.annotation.PasswordEncryption;
import chan99k.service.EncryptService;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class PasswordEncryptionAspect {

	private final EncryptService encryptService;

	@Around("execution(* chan99k.controller..*.*(..))")
	public Object passwordEncryptionAspect(ProceedingJoinPoint pjp) throws Throwable {
		Arrays.stream(pjp.getArgs())
			.forEach(this::fieldEncryption);

		return pjp.proceed();
	}

	public void fieldEncryption(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			return;
		}

		FieldUtils.getAllFieldsList(object.getClass())
			.stream()
			.filter(filter -> !(Modifier.isFinal(filter.getModifiers()) && Modifier.isStatic(filter.getModifiers())))
			.forEach(field -> {
				try {
					boolean encryptionTarget = field.isAnnotationPresent(PasswordEncryption.class);
					if (!encryptionTarget) {
						return;
					}

					Object encryptionField = FieldUtils.readField(field, object, true);
					if (!(encryptionField instanceof String)) {
						return;
					}

					String encrypted = encryptService.encrypt((String) encryptionField);
					FieldUtils.writeField(field, object, encrypted);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
	}
}
