package dev.rickyshd.dbcore;

import java.io.*;
import java.sql.Timestamp;
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
        if (content.get(label) instanceof Reader reader) return getClobString(reader);
        return get(label, String.class);
    }

    @Override
    public Reader getClob(String label) {
        Object o = content.get(label);
        if (o == null) return null;
        return get(label, Reader.class);
    }

    private String getClobString(Reader reader) {
        try (StringWriter writer = new StringWriter()) {
            reader.transferTo(writer);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getBinary(String label) {
        return getBytes(label);
    }

    public byte[] getBytes(String label) {
        if (content.get(label) instanceof InputStream stream) return getBlobBytes(stream);
        return get(label, byte[].class);
    }

    @Override
    public InputStream getBlob(String label) {
        return get(label, InputStream.class);
    }

    private byte[] getBlobBytes(InputStream stream) {
        try (stream) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
