package base.storage.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class DisplayableFeedEntry {
    private String title;
    private String description;
    private String link;
    private Timestamp pubDate;
    private String parentFeedTitle;
}
