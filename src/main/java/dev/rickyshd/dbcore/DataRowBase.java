package dev.rickyshd.dbcore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class DataRowBase implements DataRow {
    private final LinkedHashMap<String, Object> content;

    public DataRowBase(LinkedHashMap<String, Object> _content) {
        content = _content;
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public List<String> labels() {
        return content.keySet().stream().toList();
    }

    @Override
    public Object get(String columnLabel) {
        return content.getOrDefault(columnLabel, null);
    }

    @Override
    public <T> T get(String columnLabel, Class<T> type) {
        return type.cast(content.getOrDefault(columnLabel, null));
    }

    @Override
    public String getString(String label) {
        return get(label, String.class);
    }

    @Override
    public byte[] getBinary(String label) {
        return get(label, byte[].class);
    }

    @Override
    public boolean getBoolean(String label) {
        return get(label, Boolean.class);
    }

    @Override
    public short getShort(String label) {
        return get(label, Short.class);
    }

    @Override
    public int getInt(String label) {
        return get(label, Integer.class);
    }

    @Override
    public long getLong(String label) {
        return get(label, Long.class);
    }

    @Override
    public float getFloat(String label) {
        return get(label, Float.class);
    }

    @Override
    public double getDouble(String label) {
        return get(label, Double.class);
    }

    public Date getDatetime(String label, ZoneOffset offset) {
        return Date.from((get(label, Timestamp.class)).toLocalDateTime().toInstant(offset));
    }

    @Override
    public Date getDatetime(String label) {
        return getDatetime(label, ZoneOffset.UTC);
    }
}
