package org.apache.fesod.sheet.metadata.property;

import java.lang.reflect.Field;
import org.apache.fesod.sheet.annotation.format.DateTimeFormat;
import org.apache.fesod.sheet.enums.BooleanEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTimeFormatPropertyTest {

    @DateTimeFormat("yyyy-MM-dd")
    private String defaultWindowing;

    @DateTimeFormat(value = "yyyy-MM-dd", use1904windowing = BooleanEnum.TRUE)
    private String explicitWindowing;

    @Test
    void preservesDefaultWindowingAsNull() throws NoSuchFieldException {
        DateTimeFormat annotation = field("defaultWindowing").getAnnotation(DateTimeFormat.class);

        DateTimeFormatProperty property = DateTimeFormatProperty.build(annotation);

        Assertions.assertNull(property.getUse1904windowing());
    }

    @Test
    void preservesExplicitWindowingValue() throws NoSuchFieldException {
        DateTimeFormat annotation = field("explicitWindowing").getAnnotation(DateTimeFormat.class);

        DateTimeFormatProperty property = DateTimeFormatProperty.build(annotation);

        Assertions.assertEquals(Boolean.TRUE, property.getUse1904windowing());
    }

    private Field field(String name) throws NoSuchFieldException {
        return getClass().getDeclaredField(name);
    }
}
