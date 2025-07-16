package com.emint.repo

import com.emint.data.hmds.LocalSymbolID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface LocalSymbolIDRepo : JpaRepository<LocalSymbolID, Long> {
    fun findBySymbolIn(symbols: Set<String>): List<LocalSymbolID>

    fun findByIdIn(ids: List<Int>): List<LocalSymbolID>

    @Query("select max(id) from LocalSymbolID")
    fun fetchMaxID(): Int

    @Query("select c from LocalSymbolID c where c.id > :id order by c.id asc limit 5000")
    fun fetchGTGivenIDOrderByIDAsc(id: Int): List<LocalSymbolID>

    @Transactional
    @Modifying
    @Query(
        "delete from LocalSymbolID where symbol in :symbols"
    )
    fun deleteWithGivenSymbols(
        symbols: Set<String>
    ): Int
}
