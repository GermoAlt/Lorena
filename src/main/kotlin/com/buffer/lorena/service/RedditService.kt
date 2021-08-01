package com.buffer.lorena.service

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.net.http.HttpClient

@Service
class RedditService (
    restTemplateBuilder: RestTemplateBuilder
){
    private val logger: Logger = LogManager.getLogger(RedditService::class.java)

    private val restTemplate = restTemplateBuilder.build()

    fun sendLink(
        redditLink: String,
        event: MessageCreateEvent,
    ) {
        val url = "https://reddit.com/$redditLink"
        if (redditLink.length > 23) return
        val result: String = try {
            restTemplate.getForObject("$url/about.json", String::class.java) ?: return
        } catch (_: Exception) {
            return
        }
//        logger.info("result is: {}", result)
        if (redditLink.startsWith("r/") && "\"kind\": \"Listening\"" in result) {
//            logger.info("Subreddit {} doesn't exist", redditLink)
            return
        }
        if (redditLink.startsWith("u/") && "\"kind\": \"t2\"" !in result) {
//            logger.info("Reddit user {} doesn't exist", redditLink)
            return
        }
        event.channel.sendMessage(url)
    }
}