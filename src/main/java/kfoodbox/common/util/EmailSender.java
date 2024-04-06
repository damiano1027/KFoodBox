package kfoodbox.common.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {
    private final AmazonSimpleEmailServiceAsync amazonSimpleEmailServiceAsync;
    private final String SIGNUP_AUTHENTICATION_TITLE = "[K-Food Box] The authentication number to sign up";
    private final String SIGNUP_AUTHENTICATION_CONTENT_PREFIX = "Your authentication number is";

    @Value("${aws.ses.from}")
    private String FROM;

    public SendEmailResult sendAuthenticationNumber(String to, String authenticationNumber) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(
                        new Message()
                        .withBody(new Body()
                        .withHtml(new Content()
                        .withCharset("UTF-8").withData(String.format("%s %s.", SIGNUP_AUTHENTICATION_CONTENT_PREFIX, authenticationNumber))))
                        .withSubject(new Content()
                        .withCharset("UTF-8").withData(SIGNUP_AUTHENTICATION_TITLE))
                )
                .withSource(FROM);

        return amazonSimpleEmailServiceAsync.sendEmail(request);
    }
}
