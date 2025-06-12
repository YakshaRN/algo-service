package com.emint.service

import com.emint.model.FunctionDispatcherKey
import org.springframework.stereotype.Service

@Service
class FunctionDispatcher(
    private val resolverService: FunctionResolvers
) {
    val functionDispatchMap: Map<FunctionDispatcherKey, (List<Any>) -> Double> = mapOf(
        FunctionDispatcherKey("WD", 1) to { args -> resolverService.compareLtp(args[0] as String, args[1] as String, args[2] as Int) },
        FunctionDispatcherKey("WD", 2) to { args -> resolverService.resolveWD(args[0] as Int, args[1] as Int) },
        FunctionDispatcherKey("WD", 3) to { args -> resolverService.resolveWD(args[0] as String, args[1] as Int, args[2] as Int) },
        FunctionDispatcherKey("MD", 2) to { args -> resolverService.resolveMD(args[0] as String, args[1] as Int) },
        FunctionDispatcherKey("GD", 2) to { args -> resolverService.resolveGD(args[0] as String, args[1] as Int) }
    )
}
