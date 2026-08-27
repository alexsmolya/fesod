package org.apache.fesod.sheet.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.apache.fesod.sheet.converters.localdatetime.LocalDateTimeNumberConverter;
import org.apache.fesod.sheet.metadata.GlobalConfiguration;
import org.apache.fesod.sheet.metadata.data.ReadCellData;
import org.apache.fesod.sheet.metadata.property.DateTimeFormatProperty;
import org.apache.fesod.sheet.metadata.property.ExcelContentProperty;
import org.apache.fesod.sheet.util.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalDateTimeNumberConverterTest {

    @Test
    void defaultAnnotationUsesGlobal1904WindowingForReadAndWrite() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        globalConfiguration.setUse1904windowing(Boolean.TRUE);
        ExcelContentProperty contentProperty = new ExcelContentProperty();
        contentProperty.setDateTimeFormatProperty(new DateTimeFormatProperty("yyyy-MM-dd", null));
        LocalDateTimeNumberConverter converter = new LocalDateTimeNumberConverter();
        ReadCellData<?> cellData = new ReadCellData<>(BigDecimal.ONE);

        LocalDateTime expectedRead = DateUtils.getLocalDateTime(1, true);
        LocalDateTime actualRead = converter.convertToJavaData(cellData, contentProperty, globalConfiguration);
        BigDecimal expectedWrite = BigDecimal.valueOf(
                org.apache.poi.ss.usermodel.DateUtil.getExcelDate(expectedRead, true));
        BigDecimal actualWrite = (BigDecimal) converter
                .convertToExcelData(expectedRead, contentProperty, globalConfiguration)
                .getNumberValue();

        Assertions.assertEquals(expectedRead, actualRead);
        Assertions.assertEquals(expectedWrite, actualWrite);
    }
}
