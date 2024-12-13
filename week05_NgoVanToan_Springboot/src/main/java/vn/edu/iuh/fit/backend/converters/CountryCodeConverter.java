package vn.edu.iuh.fit.backend.converters;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CountryCodeConverter implements AttributeConverter<CountryCode, String> {

    @Override
    public String convertToDatabaseColumn(CountryCode countryCode) {
        return (countryCode != null) ? countryCode.getAlpha2() : null;
    }

    @Override
    public CountryCode convertToEntityAttribute(String dbData) {
        return (dbData != null) ? CountryCode.getByAlpha2Code(dbData) : null;
    }
}
