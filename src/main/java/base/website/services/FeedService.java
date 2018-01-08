package base.website.services;

import base.rss.FeedReader;
import base.storage.dao.FeedStorage;
import base.storage.entities.DisplayableFeedEntry;
import base.storage.entities.Feed;
import base.storage.mappers.FeedEntryMapper;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class FeedService {

    @Autowired
    FeedStorage feedStorage;

    @Autowired
    FeedEntryMapper feedEntryMapper;

    public void storeFeed(String feedURL) throws IOException, FeedException {
        SyndFeed fetchedFeed = FeedReader.getFeed(feedURL);

        Timestamp createdDate = new Timestamp(new Date().getTime());

        Feed feed = Feed.builder()
                .link(feedURL)
                .title(fetchedFeed.getTitle())
                .createdDate(createdDate)
                .build();

        feedStorage.storeFeed(feed);
    }

    public List<DisplayableFeedEntry> getDisplayableFeedEntries(int feedBatchSize, int feedBatchNumber) {
        return feedStorage.getDisplayableFeedEntries(feedBatchSize, feedBatchNumber);
    }

    public List<Feed> getFeedsForUser() {
        return feedStorage.getAllFeeds();
    }

    public void deleteFeed(String feedLink) {
        feedStorage.deleteFeed(feedLink);
    }
}
