package com.rmsi.mast.studio.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
 
/**
 * Used to serialize Java.util.Date, which is not a common JSON
 * type, so we have to create a custom serialize method;.
 *
 */

public class JsonDateSerializer extends JsonSerializer<Date>{
 
    private static final SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
 
        String formattedDate = jsonDateFormat.format(date);
 
        gen.writeString(formattedDate);
    }
 
}