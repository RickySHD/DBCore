package dev.rickyshd.dbcore;

import org.jetbrains.annotations.NotNull;

import java.time.ZoneOffset;
import java.util.*;

public interface DataRow {
    static @NotNull DataRow of(LinkedHashMap<String, Object> content) {
        return new DataRowBase(content);
    }

    static @NotNull DataRow empty() {
        return new DataRowBase(new LinkedHashMap<>());
    }

    boolean isEmpty();

    List<String> labels();

    Object get(String label);

    <T> T get(String label, Class<T> type);

    String getString(String label);
    
    byte[] getBinary(String label);
    
    boolean getBoolean(String label);

    short getShort(String label);

    int getInt(String label);

    long getLong(String label);

    float getFloat(String label);

    double getDouble(String label);

    Date getDatetime(String label, ZoneOffset offset);

    Date getDatetime(String label);
}
