package base.website.services;

import base.rss.FeedReader;
import base.storage.dao.FeedStorage;
import base.storage.entities.Feed;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FeedService {

    @Autowired
    FeedStorage feedStorage;

    public void storeFeed(String feedURL) throws IOException, FeedException {
        //download feed
        SyndFeed syndFeed = FeedReader.getFeed(feedURL);
        //create feed object
        //TODO create mapper for syndFeed to Feed
        Feed feed = Feed.builder()
                .title(syndFeed.getTitle())
                .description(syndFeed.getDescription())
                .link(syndFeed.getLink())
                .build();
        //store feed using storage
        feedStorage.storeFeed(feed);
    }
}
