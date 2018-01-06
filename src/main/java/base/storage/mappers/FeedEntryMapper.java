package base.storage.mappers;

import base.storage.entities.FeedEntry;
import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class FeedEntryMapper implements RowMapper<FeedEntry> {
    public FeedEntry getFeedFromSyndEntry(SyndEntry syndFeed) {
        return FeedEntry.builder()
                .title(syndFeed.getTitle())
                .description(syndFeed.getDescription().getValue())
                .link(syndFeed.getLink())
                .pubDate(new Timestamp(syndFeed.getPublishedDate().getTime()))
                .build();
    }

    @Nullable
    @Override
    public FeedEntry mapRow(ResultSet resultSet, int i) throws SQLException {
        return FeedEntry.builder()
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .link(resultSet.getString("link"))
                .createdDate(resultSet.getTimestamp("createdDate"))
                .pubDate(resultSet.getTimestamp("pubDate"))
                .parentFeed(resultSet.getInt("parent_feed"))
                .build();
    }
}
