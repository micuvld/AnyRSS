package base.storage.mappers;

import base.storage.entities.DisplayableFeedEntry;
import base.storage.entities.FeedEntry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DisplayableFeedEntryMapper implements RowMapper<DisplayableFeedEntry> {
    @Nullable
    @Override
    public DisplayableFeedEntry mapRow(ResultSet resultSet, int i) throws SQLException {
        return DisplayableFeedEntry.builder()
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .link(resultSet.getString("link"))
                .pubDate(resultSet.getTimestamp("pubDate"))
                .parentFeedTitle(resultSet.getString("parent_feed_title"))
                .build();
    }
}
