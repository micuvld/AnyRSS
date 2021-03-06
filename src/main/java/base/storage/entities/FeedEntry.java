package base.storage.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class FeedEntry {
    private Integer id;
    private String title;
    private String description;
    private String link;
    private Timestamp createdDate;
    private Timestamp pubDate;
    private Integer parentFeed;
}
