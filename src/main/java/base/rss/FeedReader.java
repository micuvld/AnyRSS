package base.rss;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.URL;

/**
 * check http://www.baeldung.com/rome-rss
 */
public class FeedReader {
    public static SyndFeed getFeed(String feedURL) throws IOException, FeedException {
        URL feedSource = new URL(feedURL);
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(feedSource));
    }
}
