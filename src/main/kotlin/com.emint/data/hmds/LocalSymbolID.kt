package com.emint.data.hmds

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "local_symbol_id")
data class LocalSymbolID(
    @Id
    var id: Int? = null,

    var symbol: String? = null
)
