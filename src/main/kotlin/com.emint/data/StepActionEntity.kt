package com.emint.data

import com.emint.enum.ActionStatus
import com.emint.enum.StepStatus
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
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = null,

    @Column(name = "strategy_id")
    var strategyId: UUID?,

    @Column(name = "step_no")
    var stepNo: Int?,

    @Column(name = "status")
    var status: StepStatus?
)