package com.emint.type

// this class is closely coupled with class (OpenOrderCache or its dependencies) in order validation Service;
// any change (addition/removal) in pojo should also be reflected in order validation
// any change in nullable not nullable should also be reflected in order validation
enum class Exchange {
    NSE,
    BSE
}
