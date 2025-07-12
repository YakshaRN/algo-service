package com.emint.data

import com.emint.enum.StrategyStatus
import com.emint.model.StrategyRequest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "strategy_detail")
data class StrategyDetailEntity(

    @Id
    @Column(name = "request_id", nullable = false, unique = true)
    var requestId: Long?,

    @Column(name = "timestamp")
    var timestamp: Long?,

    @Column(name = "user_id")
    var userId: String?,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "strategy_request", columnDefinition = "jsonb")
    var strategyRequest: StrategyRequest?,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: StrategyStatus?
)
