package chien.demo.shopdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import chien.demo.shopdemo.util.Utilities;

/**
 * AuthenticationService.
 */
public class AbstractMessage implements MessageSourceAware {

    private static final Logger logger =
            LoggerFactory.getLogger(AbstractMessage.class);

    private MessageSource messageSource;

    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * getMessage.
     */
    public String getMessage(String code, Object[] args) {
        try {
            return this.messageSource.getMessage(code, args, null);
        } catch (Exception e) {
            logger.debug(code);
            logger.error("Could not get message source: \n{}", e.getMessage(), e);
            return "";
        }
    }

    public String getMessageStart(String blNo, String method) {
        return getMessage("common.start.method", new String[]{blNo, method});
    }

    public String getMessageEnd(String blNo, String method) {
        return getMessage("common.end.method", new String[]{blNo, method});
    }

    public String getMessageInputParam(String blNo, String param, Object value) {
        return getMessage("common.input.parameter", new String[]{blNo, param, Utilities.parseString(value)});
    }

    public String getMessageOutputParam(String blNo, String param, Object value) {
        return getMessage("common.output.parameter", new String[]{blNo, param, Utilities.parseString(value)});
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
