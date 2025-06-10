package com.emint.type

// this class is closely coupled with class (OpenOrderCache or its dependencies) in order validation Service;
// any change (addition/removal) in pojo should also be reflected in order validation
// any change in nullable not nullable should also be reflected in order validation
enum class OrderStatus {
    RECEIVED,
    PENDING,
    PLACED,
    COMPLETE,
    REJECTED,
    PARTIAL_COMPLETE,
    MODIFY_RECEIVED,
    MODIFY_PENDING,
    MODIFY_REJECTED,
    MODIFIED,
    NOT_MODIFIED,
    CANCEL_RECEIVED,
    CANCEL_PENDING,
    CANCELLED,
    TRIGGER_PENDING,
    TRIGGER_CREATED,
    TRIGGERED,
    TRIGGER_MODIFY_PENDING,
    TRIGGER_CANCEL_PENDING,
    TRIGGER_MODIFIED,
    PARTIAL_CANCELLED,
    NOT_CANCELLED
}
