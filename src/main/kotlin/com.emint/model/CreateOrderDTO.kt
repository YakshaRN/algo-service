package com.emint.model

import com.emint.data.StrategyLegEntity
import com.emint.enum.Segment
import com.emint.type.*

data class CreateOrderDTO(
    val exchange: Exchange,
    val instrumentId: String,
    val instrumentSymbol: String,
    val brokerSymbol: String,
    val transactionType: TransactionType,
    val variety: Variety,
    val orderType: OrderType,
    val product: Product,
    val segmentType: Segment? = null,
    val quantity: Int,
    var price: Double? = null,
    val triggerPrice: Double? = null,
    val slPrice: Double? = null,
    val slTriggerPrice: Double? = null,
    val targetPrice: Double? = null,
    val disclosedQuantity: Int ? = null,
    var platformId: Platform?,
    val meta: Map<String, Any>? = null
) {
    constructor(strategyLeg: StrategyLegEntity, meta: Map<String, Any>?) : this(

        exchange = strategyLeg.exchange,
        instrumentId = strategyLeg.instrumentId,
        instrumentSymbol = strategyLeg.instrumentSymbol,
        brokerSymbol = strategyLeg.brokerSymbol,
        transactionType = strategyLeg.transactionType,
        variety = strategyLeg.variety,
        orderType = strategyLeg.orderType,
        product = strategyLeg.product,
        segmentType = strategyLeg.segment, // If this is derivable, set it
        quantity = strategyLeg.quantity,
        price = strategyLeg.price,
        triggerPrice = null,
        slPrice = null,
        slTriggerPrice = null,
        targetPrice = null,
        disclosedQuantity = null,
        platformId = Platform.ALGO,
        meta = meta
    )
}
