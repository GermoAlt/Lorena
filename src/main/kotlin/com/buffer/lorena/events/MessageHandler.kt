package com.buffer.lorena.events

import org.javacord.api.listener.message.MessageCreateListener
import com.buffer.lorena.bot.service.LorenaService
import com.buffer.lorena.service.RedditService
import com.buffer.lorena.service.UnitConversionService
import org.apache.logging.log4j.LogManager
import org.javacord.api.entity.server.Server
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.stereotype.Component
import java.util.Locale

/**
 * The type Message handler.
 */
@Component
class MessageHandler(
        private val lorenaService: LorenaService,
        private val redditService: RedditService,
        private val unitConversionService: UnitConversionService,
) : MessageCreateListener {
    private val logger = LogManager.getLogger(MessageHandler::class.java)

    /**
     * On message create.
     *
     * @param event the event
     */
    override fun onMessageCreate(event: MessageCreateEvent) {
        logger.info("message received in server {}: {}", event.server.map { obj: Server -> obj.name }.get(), event.messageContent)

        val parsedMessage = event.messageContent.split(" ")

        when {
            (parsedMessage[0].equals("!lore", ignoreCase = true)
                    || parsedMessage[0].equals("!lorebot", ignoreCase = true))
                    && !event.messageAuthor.isBotUser ->
                when (parsedMessage[1].lowercase(Locale.ROOT)) {
                    "ping" -> event.channel.sendMessage("Pong!")
                    "votes" -> lorenaService.changeUserVoteThreshold(event, parsedMessage[2])
                    "setlorechannel" -> lorenaService.setServerLoreChannel(event, parsedMessage[2])
                    "dolore" -> lorenaService.sendRandomLore(event)
                    else -> event.channel.sendMessage("fuck off")
                }
            // TODO How come?
//            parsedMessage.any { it.equals(LORENA_TEXT, true) } -> lorenaService.sendRandomLore(event)
            event.messageContent.contains(LORENA_TEXT) -> lorenaService.sendRandomLore(event)
        }

        parsedMessage.filter { it.isReddit() }.forEach {
            redditService.sendLink(it, event)
        }

        unitConversionService.parseMessage(event.messageContent, event)
    }

    private fun String.isReddit(): Boolean = this.matches(REDDIT_REGEX)

    companion object {
        private const val LORENA_TEXT = "lorena"
        private val REDDIT_REGEX = """\b([ru])/([a-zA-Z0-9-_]{3,21})\b""".toRegex()
    }
}