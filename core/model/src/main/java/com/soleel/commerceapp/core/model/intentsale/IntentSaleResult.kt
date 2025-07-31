package com.soleel.commerceapp.core.model.intentsale

import kotlinx.serialization.Serializable

@Serializable
data class IntentSaleResultExternal(
    val saleId: String = "",
    val status: Int,
    val message: String = "",
    val errorCode: String = "",
)

@Serializable
data class IntentSaleResultInternal(
    val saleId: String? = null,
    val status: IntentSaleStatusEnum,
    val message: String? = null,
    val errorCode: String? = null,
)

fun IntentSaleResultExternal.toInternal(): IntentSaleResultInternal {
    return IntentSaleResultInternal(
        saleId = saleId,
        status = IntentSaleStatusEnum.fromId(status),
        message = message,
        errorCode = errorCode
    )
}

