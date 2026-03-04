package com.niulbird.clubmgr.bfc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * Scheduled task to log cache statistics for EHCache.
 * Provides visibility into cache hit/miss ratios for monitoring.
 */
@Component
public class CacheStatsTask {
    private static final Logger log = LoggerFactory.getLogger(CacheStatsTask.class);

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void logCacheStats() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            
            // Search for JCache statistics MBeans
            ObjectName queryName = new ObjectName("javax.cache:type=CacheStatistics,CacheManager=*,Cache=*");
            Set<ObjectName> names = mbs.queryNames(queryName, null);

            if (names.isEmpty()) {
                log.debug("No cache statistics MBeans found. Ensure management is enabled in ehcache.xml if needed.");
                return;
            }

            log.info("--- Cache Statistics Report ---");
            for (ObjectName name : names) {
                String cacheName = name.getKeyProperty("Cache");
                
                Long hits = (Long) mbs.getAttribute(name, "CacheHits");
                Long misses = (Long) mbs.getAttribute(name, "CacheMisses");
                Long gets = (Long) mbs.getAttribute(name, "CacheGets");
                Float hitPercentage = (Float) mbs.getAttribute(name, "CacheHitPercentage");
                
                log.info("Cache [{}]: Hits={}, Misses={}, Total={}, HitRate={} %", 
                    cacheName, hits, misses, gets, String.format("%.2f", hitPercentage));
            }
            log.info("-------------------------------");
            
        } catch (Exception e) {
            log.error("Error retrieving cache statistics: " + e.getMessage());
        }
    }
}
