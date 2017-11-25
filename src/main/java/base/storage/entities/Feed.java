package base.storage.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Feed {
    private Integer id;
    private String title;
    private String description;
    private String link;
    private Date createdDate;

}
