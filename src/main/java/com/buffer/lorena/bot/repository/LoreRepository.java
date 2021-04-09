package com.buffer.lorena.bot.repository;

import com.buffer.lorena.db.entity.Lore;
import com.buffer.lorena.db.entity.LoreId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Lore repository.
 */
public interface LoreRepository extends JpaRepository<Lore, LoreId> {
}
