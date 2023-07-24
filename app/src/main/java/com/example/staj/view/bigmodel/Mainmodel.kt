package com.example.staj.view.bigmodel

import com.example.staj.models.EventItem
import com.example.staj.view.model.ExclusiveRatio
import com.example.staj.view.model.FinalRatio

data class Mainmodel(
    val matchTag: EventItem,
    val finalRatio: FinalRatio,
    val exclusiveRatio: ExclusiveRatio
)
