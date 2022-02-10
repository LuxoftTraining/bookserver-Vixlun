package luxoft.training.bookserver.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLogger implements CacheEventListener<Object, Object> {
    private final Logger LOG = LoggerFactory.getLogger(CacheLogger.class);
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        LOG.info("Book name: {} | EventType: {}",
                cacheEvent.getKey(), cacheEvent.getType());
    }
}
