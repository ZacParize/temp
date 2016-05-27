package ru.mycompany;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pdv on 09.07.2015.
 */

public class LockObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean flag;
    private volatile int value;

    public int getValue() {
        return this.value;
    }

    public /*synchronized*/ void setValue(int value) throws IOException {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        Class tClass = this.getClass();
        Method[] methods = tClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            result.append(methods[i] + "\n");
        }
        Field[] fields = tClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            result.append(fields[i]  + "\n");
        }
        return result.toString();
    }

}
