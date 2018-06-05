package io.freshworks.eao.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import java.util.*

@ParseClassName("Inspection")
class Inspection : ParseObject(){

    var isSubmitted: Boolean?
        get() = getBoolean("isSubmitted")
        set(value) { put("isSubmitted", value) }

    var userId: String?
        get() = getString("userId")
        set(value) { put("userId", value) }

    var project: String?
        get() = getString("project")
        set(value) { put("project", value) }

    var title: String?
        get() = getString("title")
        set(value) { put("title", value) }

    var inspector: String?
        get() = getString("subtext")
        set(value) { put("subtext", value) }

    var number: String?
        get() = getString("number")
        set(value) { put("number", value) }

    var startDate: Date?
        get() = getDate("start")
        set(value) { put("start", value) }

    var endDate: Date?
        get() = getDate("end")
        set(value) { put("end", value) }

}