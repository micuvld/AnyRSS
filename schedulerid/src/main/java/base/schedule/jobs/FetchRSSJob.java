package base.schedule.jobs;

import base.rss.FeedReader;
import base.schedule.ScheduleData;
import base.storage.dao.FeedStorage;
import base.storage.entities.Feed;
import base.storage.entities.FeedEntry;
import base.storage.mappers.FeedEntryMapper;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class FetchRSSJob {

    @Autowired
    FeedStorage feedStorage;

    @Autowired
    FeedEntryMapper feedEntryMapper;

    public void setFeedStorage(FeedStorage feedStorage) {
        this.feedStorage = feedStorage;
    }

    public void setFeedEntryMapper(FeedEntryMapper feedEntryMapper) {
        this.feedEntryMapper = feedEntryMapper;
    }

    @Scheduled(fixedRate = 5000)
    protected void execute() throws JobExecutionException {
        List<Feed> rssFeedURLs = feedStorage.getFeedsForUser("someUser");
        List<FeedEntry> feedEntries = new ArrayList<>();

        for (Feed feed : rssFeedURLs) {
            SyndFeed fetchedFeed = null;

            try {
                //download feed
                fetchedFeed = FeedReader.getFeed(feed.getLink());
            } catch (IOException | FeedException e) {
                e.printStackTrace();
            }

            List<SyndEntry> feedList = fetchedFeed.getEntries();
            for (SyndEntry syndFeed : feedList) {
                FeedEntry feedEntry = feedEntryMapper.getFeedFromSyndEntry(syndFeed);
                feedEntries.add(feedEntry);
            }
        }

        feedStorage.storeFeedEntries(feedEntries);
    }
}
