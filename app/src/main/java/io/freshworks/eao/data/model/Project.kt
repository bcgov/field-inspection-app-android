package io.freshworks.eao.data.model

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Project")
class Project : ParseObject(){
    var name: String?
        get() = getString("name")
        set(value) { put("name", value) }

    var onlyLocal: Boolean?
        get() = getBoolean("onlyLocal")
        set(value) { put("onlyLocal", value) }
}
