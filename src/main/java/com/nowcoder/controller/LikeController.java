package com.nowcoder.controller;

import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventProducer;
import com.nowcoder.async.EventType;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.NewsService;
import com.nowcoder.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luotj on 2017/5/12.
 */
@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = {"/like"})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        News news = newsService.getById(newsId);

        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.like(userId,newsId, EntityType.ENTITY_NEWS);
        newsService.updateLikeCount(newsId,(int)likeCount);

        eventProducer.fireEvent(new EventModel().setActorId(hostHolder.getUser().getId())
                .setEntityId(newsId).setEntityType(EntityType.ENTITY_NEWS).setType(EventType.LIKE)
        .setEntityOwnerId(news.getUserId()));

        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(value = {"/dislike"})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.dislike(userId, newsId, EntityType.ENTITY_NEWS);
        newsService.updateLikeCount(newsId,(int)likeCount);
        return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
