package com.emint.data

import com.emint.enum.Segment
import com.emint.type.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.util.*

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "strategy_leg")
data class StrategyLegEntity(

    // TODO: If InstrumentId is present, exchange, segment, symbol are all unnecessary
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "user_id")
    var strategyId: UUID,

    @Column(name = "leg_name")
    var legName: String = "Leg1",

    @Column(name = "order_time")
    val orderTime: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "transactionType")
    val transactionType: TransactionType,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "price")
    var price: Double? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "orderType")
    val orderType: OrderType,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: OrderStatus?,

    @Column(name = "instrumentId")
    val instrumentId: String,

    @Column(name = "instrumentSymbol")
    val instrumentSymbol: String,

    @Column(name = "brokerSymbol")
    val brokerSymbol: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "segment")
    val segment: Segment,

    @Enumerated(EnumType.STRING)
    @Column(name = "exchange")
    val exchange: Exchange,

    @Enumerated(EnumType.STRING)
    @Column(name = "variety")
    val variety: Variety = Variety.REGULAR,

    @Enumerated(EnumType.STRING)
    @Column(name = "product")
    val product: Product
)
