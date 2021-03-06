package ua.kiev.prog.ShortLinkServlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UrlDatabase {

    public final static UrlDatabase INSTANCE = new UrlDatabase();

    private final Map<String, UrlRecord> db = new HashMap<>();
    private long n;

    private UrlDatabase() {
    }

    public synchronized String save(String url) {
        String key = null;
        if (!db.containsValue(url)) {
            key = RandomString.generate(7);

            UrlRecord value = new UrlRecord();
            value.setUrl(url);

            db.put(key, value);
        }
        return key;
    }
    public synchronized String get(String shortUrl) {
        UrlRecord value = db.get(shortUrl);
        value.inc();

        return value.getUrl();
    }


    public synchronized Collection<UrlRecord> getStats() {
        return db.values();
    }

    static public class UrlRecord {

        String url;
        long counter;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UrlRecord urlRecord = (UrlRecord) o;
            return Objects.equals(url, urlRecord.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url);
        }

        public void inc() {
            counter++;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCounter() {
            return counter;
        }

        public void setCounter(long counter) {
            this.counter = counter;
        }
    }
}
