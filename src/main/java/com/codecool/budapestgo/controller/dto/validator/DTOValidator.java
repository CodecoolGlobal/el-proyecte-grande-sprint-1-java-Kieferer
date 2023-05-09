package com.codecool.budapestgo.controller.dto.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;

public class DTOValidator {
    public static boolean registrationIsInvalid(Object object) throws InvocationTargetException, IllegalAccessException {
        RecordComponent[] fields = object.getClass().getRecordComponents();
        for (RecordComponent field : fields) {
            var value = field.getAccessor().invoke(object);
            if (value == null || value.toString().isBlank()) {
                return true;
            }
        }
        return false;
    }
}
