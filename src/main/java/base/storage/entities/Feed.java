package base.storage.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class Feed {
    private String link;
    private String title;
    private Timestamp createdDate;
}
