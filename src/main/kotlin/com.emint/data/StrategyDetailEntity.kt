package com.emint.data

import com.emint.enum.StrategyStatus
import com.emint.model.StrategyRequest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.vladmihalcea.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "strategy_detail")
data class StrategyDetailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "timestamp")
    var timestamp: Long?,

    @Column(name = "user_id")
    var userId: String?,

    @Type(JsonType::class)
    @Column(name = "strategy_request", columnDefinition = "jsonb")
    var strategyRequest: StrategyRequest?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: StrategyStatus?
)
