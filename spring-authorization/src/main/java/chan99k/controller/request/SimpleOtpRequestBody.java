package chan99k.controller.request;

public record SimpleOtpRequestBody(
	String userId,
	String otp
) {
}
