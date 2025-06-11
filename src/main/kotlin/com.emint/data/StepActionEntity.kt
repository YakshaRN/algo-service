package com.emint.data

import com.emint.enum.ActionStatus
import com.emint.model.Action
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.util.*
import com.vladmihalcea.hibernate.type.json.JsonType

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "step_action")
data class StepActionEntity(

    @Id
    @Column(name = "request_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(name = "strategy_id")
    var strategyId: UUID?,

    @Column(name = "step_no")
    var stepNo: Int?,

    @Type(JsonType::class)
    @Column(name = "actions", columnDefinition = "jsonb")
    var actions: Action,

    @Column(name = "status")
    var status: ActionStatus?
)