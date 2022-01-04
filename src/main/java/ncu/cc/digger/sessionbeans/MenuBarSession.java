package ncu.cc.digger.sessionbeans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * @author Mac Liu (linuzilla@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public class MenuBarSession {
    public static class Entry implements Serializable {
        private String messageKey;
        private String message;
        private String urlpath;
        private List<Entry> subentries;
        private int order;

        public String getMessageKey() {
            return messageKey;
        }

        public void setMessageKey(String messageKey) {
            this.messageKey = messageKey;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUrlpath() {
            return urlpath;
        }

        public void setUrlpath(String urlpath) {
            this.urlpath = urlpath;
        }

        public List<Entry> getSubentries() {
            return subentries;
        }

        public void setSubentries(List<Entry> subentries) {
            this.subentries = subentries;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    private List<Entry> menu;
    private long code;
    private Locale locale;

    public List<Entry> getMenu() {
        return menu;
    }

    public void setMenu(List<Entry> menu) {
        this.menu = menu;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
