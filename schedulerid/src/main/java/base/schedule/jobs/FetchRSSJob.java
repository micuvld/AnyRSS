package base.schedule.jobs;

import base.rss.FeedReader;
import base.storage.dao.FeedStorage;
import base.storage.entities.Feed;
import base.storage.entities.FeedEntry;
import base.storage.mappers.FeedEntryMapper;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FetchRSSJob {

    @Autowired
    FeedStorage feedStorage;

    @Autowired
    FeedEntryMapper feedEntryMapper;

    @Scheduled(fixedRate = 30000) //30 sedonds
    protected void execute() throws JobExecutionException {
        List<Feed> rssFeedURLs = feedStorage.getFeedsForUser();
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
                FeedEntry feedEntry;

                try {
                    feedEntry = feedEntryMapper.getFeedFromSyndEntry(syndFeed);
                    feedEntry.setParentFeed(feed.getId());
                    feedEntries.add(feedEntry);
                } catch (Exception e) {
                    log.info("Failed to fetch feed: " + syndFeed.getTitle());
                }
            }
        }

        feedStorage.storeFeedEntries(feedEntries);
    }
}
