package ncu.cc.digger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class MessageResourceServiceImpl implements MessageResourceService {
    @Autowired
    private LocaleService localeService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String key, String... args) {
        return messageSource.getMessage(key, args, localeService.currentLocale());
    }
}
