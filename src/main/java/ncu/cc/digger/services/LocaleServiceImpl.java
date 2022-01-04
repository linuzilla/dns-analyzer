package ncu.cc.digger.services;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Service
public class LocaleServiceImpl implements LocaleService {
    @Override
    public Locale currentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
