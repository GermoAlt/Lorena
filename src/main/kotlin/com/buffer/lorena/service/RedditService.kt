package com.buffer.lorena.service

import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Service

@Service
class RedditService {
    fun sendLink(
        redditLink: String,
        event: MessageCreateEvent,
    ) {
        event.channel.sendMessage("https://reddit.com/$redditLink")
    }
}