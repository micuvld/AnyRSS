package base.storage.mappers;

import base.storage.entities.Feed;
import base.storage.entities.FeedEntry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedMapper implements RowMapper<Feed> {
    @Nullable
    @Override
    public Feed mapRow(ResultSet resultSet, int i) throws SQLException {
        return Feed.builder()
                .title(resultSet.getString("title"))
                .link(resultSet.getString("link"))
                .createdDate(resultSet.getTimestamp("createdDate"))
                .build();
    }
}
