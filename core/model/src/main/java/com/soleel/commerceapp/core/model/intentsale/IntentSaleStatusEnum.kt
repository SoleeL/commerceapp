package com.soleel.commerceapp.core.model.intentsale

import kotlinx.serialization.Serializable

@Serializable
enum class IntentSaleStatusEnum(
    val id: Int,
    val displayName: String
) {
    SUCCESS(1, "Exitosa"),
    CANCELLED(2, "Cancelada"),
    ERROR(3, "Erronea");
    
    companion object {
        fun fromId(id: Int): IntentSaleStatusEnum {
            val intentSaleStatusEnum: IntentSaleStatusEnum? = entries.find(predicate = { it.id == id })
            return intentSaleStatusEnum ?: ERROR
        }
    }
}