package com.buffer.lorena.entity

import com.buffer.lorena.bot.entity.LoreId
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

//@Entity
//@Table(name = "thisfucksnoodlesdb")
data class KotlinLore(
    @EmbeddedId var loreId: LoreId,
    @Column(name = "createdAt") var createdAt: Timestamp,
    @Column(name = "updatedAt") var updatedAt: Timestamp?,
) {
    public constructor(idServer: Long, idUser: Long, idMessage: Long) :
            this(LoreId(idServer, idUser, idMessage), Timestamp.from(Instant.now()), null)

    companion object {
        fun method() {
            val lore = KotlinLore(1, 1, 1).copy(updatedAt = Timestamp.from(Instant.now()))
        }
    }
}