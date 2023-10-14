package com.buffer.lorena.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

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
        if (try {
            // If about is not t5 for subreddit or t2 for user
            val json = restTemplate.getForObject("$url/about.json", About::class.java) ?: return
            (redditLink.startsWith("r/") && json.kind != "t5") ||
                    (redditLink.startsWith("u/") && json.kind != "t2")
        } catch (e: HttpClientErrorException) {
            // If it's a Forbidden, the sub exists, but we can't go to it
            logger.warn("Caught {}", e::class.simpleName)
            e !is HttpClientErrorException.Forbidden
        } catch (e: Exception) {
            // Other exceptions we log and ignore
            logger.warn("Caught {}", e::class.simpleName, e)
            true
        }) {
            return
        }
        event.channel.sendMessage(url)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class About (
    val kind: String?
)
