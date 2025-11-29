package dev.rickyshd.dbcore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

public abstract class LargeObjectWrapper {

    public static LargeObjectWrapper ofCharacters(StringReader stringReader) {
        return new StringReaderLargeObjectWrapper(stringReader);
    }

    public static LargeObjectWrapper ofCharacters(String string) {
        return new StringReaderLargeObjectWrapper(new StringReader(string));
    }

    public static LargeObjectWrapper ofBytes(byte[] bytes) {
        return new InputStreamLargeObjectWrapper(new ByteArrayInputStream(bytes));
    }

    static class StringReaderLargeObjectWrapper extends LargeObjectWrapper {
        private final StringReader reader;

        private StringReaderLargeObjectWrapper(StringReader reader) {
            this.reader = reader;
        }

        public StringReader reader() {
            return reader;
        }
    }

    static class InputStreamLargeObjectWrapper extends LargeObjectWrapper {
        private final InputStream stream;

        private InputStreamLargeObjectWrapper(InputStream stream) {
            this.stream = stream;
        }

        public InputStream stream() {
            return stream;
        }
    }

}
