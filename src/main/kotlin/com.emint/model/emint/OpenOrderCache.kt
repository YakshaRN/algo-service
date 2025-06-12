package com.emint.model.emint

import com.emint.enum.Segment
import com.emint.type.*

data class OpenOrderCache(
    var orderId: String,
    var createdOn: Long?,
    var updatedOn: Long?,
    var userId: String,
    var exchange: Exchange,
    var instrumentSymbol: String,
    var instrumentId: String,
    var segmentType: Segment,
    var variety: Variety,
    var transactionType: TransactionType,
    var orderType: OrderType,
    var product: Product,
    var quantity: Int,
    var disclosedQuantity: Int?,
    var tradedQuantity: Int? = 0,
    var price: Double,
    var triggerPrice: Double?,
    var slPrice: Double?,
    var targetPrice: Double?,
    var tradedAvgPrice: Double?,
    var validTillDate: Long?,
    var status: OrderStatus,
    var reason: String?,
    var parentId: String?,
    var slTriggerPrice: Double?,
    var brokerSymbol: String?,
    var exchangeOrderId: String?
) {
    constructor() : this(
        orderId = "",
        createdOn = null,
        updatedOn = null,
        userId = "",
        exchange = Exchange.NSE,
        instrumentSymbol = "",
        instrumentId = "",
        segmentType = Segment.EQUITY,
        variety = Variety.REGULAR,
        transactionType = TransactionType.BUY,
        orderType = OrderType.LIMIT,
        product = Product.CNC,
        quantity = 0,
        disclosedQuantity = null,
        tradedQuantity = 0,
        price = 0.0,
        triggerPrice = null,
        slPrice = null,
        targetPrice = null,
        tradedAvgPrice = null,
        validTillDate = null,
        status = OrderStatus.RECEIVED,
        reason = null,
        parentId = null,
        slTriggerPrice = null,
        brokerSymbol = null,
        exchangeOrderId = null
    )
}
