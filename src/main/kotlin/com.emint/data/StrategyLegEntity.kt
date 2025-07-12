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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "strategy_id")
    var strategyId: Long,

    @Column(name = "leg_name")
    var legName: String = "Leg1",

    @Column(name = "order_time")
    val orderTime: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    val transactionType: TransactionType,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "price")
    var price: Double? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    val orderType: OrderType,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: OrderStatus?,

    @Column(name = "instrument_id")
    val instrumentId: String,

    @Column(name = "instrument_symbol")
    val instrumentSymbol: String,

    @Column(name = "broker_symbol")
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
