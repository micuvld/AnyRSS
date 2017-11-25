package base.website.controllers;

import base.storage.dao.FeedStorage;
import base.utils.AjaxResponse;
import base.website.services.FeedService;
import com.sun.syndication.io.FeedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@Slf4j
public class FeedController {

    @Autowired
    FeedService feedService;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return new ModelAndView("dashboard");
    }

//    @RequestMapping(value = "/feeds", method = RequestMethod.GET)
//    @ResponseBody
//    public String getFeeds() {
//
//    }

    @RequestMapping(value = {"/feeds"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse saveFeed(@RequestParam("feed_url") String feedURL) {
        try {
            feedService.storeFeed(feedURL);
            return AjaxResponse.buildSuccessResponse("Succeeded");
        } catch (IOException | FeedException e) {
            e.printStackTrace();
            return AjaxResponse.buildErrorResponse("Failed");
        }
    }
}
