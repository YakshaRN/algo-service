package com.emint.repo

import com.emint.data.Action
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActionRepo : JpaRepository<Action, UUID>
