package base.storage.dao;

import base.storage.entities.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Transactional
@Component
public class FeedStorage {
    private final String INSERT_FEED = "" +
            "INSERT INTO feeds(title, description, link)" +
            " VALUES (:title, :description, :link)";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public void storeFeed(Feed feed) {
        Map args = new HashMap();
        args.put("title", feed.getTitle());
        args.put("description", feed.getDescription());
        args.put("link", feed.getLink());
        args.put("createdDate", (new Date()).toString());
        jdbcTemplate.update(INSERT_FEED, args);
    }
}
