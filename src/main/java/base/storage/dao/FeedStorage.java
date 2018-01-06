package base.storage.dao;

import base.storage.entities.Feed;
import base.storage.entities.FeedEntry;
import base.storage.mappers.FeedEntryMapper;
import base.storage.mappers.FeedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Transactional
@Component
@Slf4j
public class FeedStorage {
    private final String INSERT_FEED_ENTRY = "" +
            "INSERT IGNORE INTO feed_entries(title, description, link, createdDate, pubDate)" +
            " VALUES (:title, :description, :link, :createdDate, :pubDate)";

    private final String INSERT_FEED = "" +
            "INSERT INTO feeds(link, title, createdDate)" +
            " VALUES (:link, :title, :createdDate)";

    private final String GET_ALL_FEED_ENTRIES = "" +
            "SELECT * FROM feed_entries";
    private final String GET_ALL_FEEDS = "" +
            "SELECT * FROM feeds";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    FeedEntryMapper feedEntryMapper;

    @Autowired
    FeedMapper feedMapper;

    public void storeFeed(Feed feed) {
        Map args = new HashMap();
        args.put("link", feed.getLink());
        args.put("title", feed.getTitle());
        args.put("createdDate", feed.getCreatedDate());
        jdbcTemplate.update(INSERT_FEED, args);
    }

    public void storeFeedEntries(List<FeedEntry> feedEntries) {
        List<Map<String, Object>> batchValues = new ArrayList<>(feedEntries.size());
        Timestamp createdDate = new Timestamp(new Date().getTime());

        for (FeedEntry entry : feedEntries) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", entry.getTitle());
            map.put("description", entry.getDescription());
            map.put("link", entry.getLink());
            map.put("pubDate", entry.getPubDate());
            map.put("createdDate", createdDate);

            batchValues.add(map);
        }

        jdbcTemplate.batchUpdate(INSERT_FEED_ENTRY, batchValues.toArray(new Map[batchValues.size()]));

        logStoredEntries(batchValues);
    }

    private void logStoredEntries(List<Map<String, Object>> batchValues) {
        log.info("Wrote following entries:");

        batchValues.stream().forEach(map -> {
            log.info(map.toString());
        });
    }

    public List<FeedEntry> getAllFeedEntries(String userId) {
        Map args = new HashMap();
        args.put("user_id", userId);
        return jdbcTemplate.query(GET_ALL_FEED_ENTRIES, feedEntryMapper);
    }

    public List<Feed> getFeedsForUser(String userId) {
        Map args = new HashMap();
        args.put("user_id", userId);
        return jdbcTemplate.query(GET_ALL_FEEDS, feedMapper);
    }
}
