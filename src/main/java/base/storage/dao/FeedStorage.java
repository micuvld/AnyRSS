package base.storage.dao;

import base.storage.entities.DisplayableFeedEntry;
import base.storage.entities.Feed;
import base.storage.entities.FeedEntry;
import base.storage.mappers.DisplayableFeedEntryMapper;
import base.storage.mappers.FeedEntryMapper;
import base.storage.mappers.FeedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


@Transactional
@Component
@Slf4j
public class FeedStorage {
    private final String INSERT_FEED_ENTRY = "" +
            "INSERT IGNORE INTO feed_entries(title, description, link, createdDate, pubDate, parent_feed)" +
            " VALUES (:title, :description, :link, :createdDate, :pubDate, :parentFeed)";

    private final String INSERT_FEED = "" +
            "INSERT IGNORE INTO feeds(link, title, createdDate)" +
            " VALUES (:link, :title, :createdDate)";

    private final String GET_ALL_FEED_ENTRIES = "" +
            "SELECT * FROM feed_entries ORDER BY pubDate DESC";

    private final String GET_ALL_DISPLAYABLE_FEED_ENTRIES = "" +
            "SELECT fe.title as title, fe.description as description, fe.link as link," +
            "   fe.pubDate as pubDate, f.title as parent_feed_title" +
            " FROM feeds f, feed_entries fe " +
            " WHERE f.id = fe.parent_feed " +
            " ORDER BY pubDate DESC " +
            " LIMIT %d OFFSET %d";

    private final String GET_ALL_FEEDS = "" +
            "SELECT * FROM feeds";

    private final String GET_FEED_ID = "" +
            "SELECT id FROM feeds WHERE link = :feed_link";

    private final String DELETE_FEED = "" +
            "DELETE FROM feeds WHERE link = :feed_link";

    private final String DELETE_FEED_ENTRIES = "" +
            "DELETE FROM feed_entries WHERE parent_feed = :parent_id";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    FeedEntryMapper feedEntryMapper;

    @Autowired
    FeedMapper feedMapper;

    @Autowired
    DisplayableFeedEntryMapper displayableFeedEntryMapper;

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
            map.put("parentFeed", entry.getParentFeed());

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

    public List<DisplayableFeedEntry> getDisplayableFeedEntries(int feedBatchSize, int feedBatchNumber) {
        return jdbcTemplate.query(String.format(GET_ALL_DISPLAYABLE_FEED_ENTRIES,
                feedBatchSize,  //limit
                feedBatchNumber * feedBatchSize), //offset
                displayableFeedEntryMapper);
    }

    public List<FeedEntry> getAllFeedEntries() {
        return jdbcTemplate.query(GET_ALL_FEED_ENTRIES, feedEntryMapper);
    }

    public List<Feed> getFeedsForUser() {
        return jdbcTemplate.query(GET_ALL_FEEDS, feedMapper);
    }

    public void deleteFeed(String feedLink) {
        Map args = new HashMap();
        args.put("feed_link", feedLink);

        int feedId = (int) jdbcTemplate.query(GET_FEED_ID, args, new IntegerRowMapper()).get(0);
        deleteFeedEntries(feedId);

        jdbcTemplate.update(DELETE_FEED, args);
    }

    public void deleteFeedEntries(int feedId) {
        Map args = new HashMap();
        args.put("parent_id", feedId);

        jdbcTemplate.update(DELETE_FEED_ENTRIES, args);
    }

    public static class IntegerRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            return rs.getInt(1);
        }
    }
}
