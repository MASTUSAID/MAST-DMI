package com.rmsi.mast.studio.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

public class ConfigurationUtil implements MessageSourceAware {
    
    private static MessageSourceAccessor messageSourceAccessor = null;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public static MessageSourceAccessor getMessageSource() {
        return messageSourceAccessor;
    }
    
    public static String getProperty(String thePropKey)
    {
        return getMessageSource().getMessage(thePropKey);
    }

}