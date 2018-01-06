package base.schedule;

import lombok.Builder;
import lombok.Data;
import org.quartz.JobDetail;
import org.quartz.Trigger;

@Data
@Builder
public class ScheduleData {
    private JobDetail jobDetail;
    private Trigger trigger;
}
