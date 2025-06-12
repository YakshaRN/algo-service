package com.emint.model.emint

import com.emint.type.Exchange
import com.emint.type.Product
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.swing.text.Segment

@JsonIgnoreProperties
data class PositionsInUi(
    @JsonProperty("exchange") val exchange: Exchange?,
    @JsonProperty("instrumentId") val instrumentId: String?,
    @JsonProperty("segmentType") val segmentType: Segment?,
    @JsonProperty("product") val product: Product?,
    @JsonProperty("instrumentSymbol") val instrumentSymbol: String?,
    @JsonProperty("buyQuantity") val buyQuantity: Double? = 0.0,
    @JsonProperty("buyPrice") val buyPrice: Double? = 0.0,
    @JsonProperty("sellQuantity") val sellQuantity: Double? = 0.0,
    @JsonProperty("sellPrice") val sellPrice: Double? = 0.0,
    @JsonProperty("brokerSymbol") val brokerSymbol: String?,
    @JsonProperty("totalQuantity") val totalQuantity: Double? = 0.0,
    @JsonProperty("totalPrice") val totalPrice: Double? = 0.0,
    @JsonProperty("realisedPnl") val realisedPnl: Double? = 0.0,
    @JsonProperty("unSettledPrice") val unSettledPrice: Double? = 0.0,
    @JsonProperty("createdOn") var createdOn: Long
)
