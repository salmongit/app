package com.salmon.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ExceptionCodeImpl {
    protected String message = null;
    protected Object[] params = null;
    protected int value = 0;
    protected String key = null;

    public ExceptionCodeImpl(int value, String key) {
        this.value = value;
        this.key = key;
    }

    public void params(Object... params) {
        this.params = params;
    }

    public int value() {
        return this.value;
    }

    public String message(ResourceBundle resourceBundle) {
        try {
            String var2 = resourceBundle.getString(this.key);
            this.message = MessageFormat.format(var2, this.params);
            return this.message;
        } catch (Exception var9) {
            return var9.getMessage();
        }
    }
}