package com.kenda.kotlin.restful.basic

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test

internal class NullSafetyTest {

    private var allowNull: String? = "Kenda" // Allow null and modification

    private var notNull: String = "Kenda" // Not null and modification

    @Test
    fun allowNull() {
        assertEquals(allowNull?.length, 5)

        allowNull = null
        assertEquals(allowNull?.length, null)
    }

    @Test
    fun notNull() {
        assertEquals(notNull.length, 5)
    }

    @Test
    fun allowNullObject() {
        val person: Person? = Person(country = Country("ID"), nama = "Harus ada")

        val code = person?.country?.code

        assertEquals(code, "ID")

        val personNull: Person? = Person(country = Country(null), nama = "Harus ada")

        val codeNull = personNull?.country?.code

        assertNull(codeNull)
    }

    @Test
    fun letMethod() {
        val first = "Kenda"
        val second = "Sukenda"

        val names = listOf(first, null, null, second)

        var fullName = listOf<String?>()

        names.forEach { t: String? -> t?.let { fullName = fullName.plus(t) } }
        assertEquals(2, fullName.size)

        for (data in names) {
            data?.let { fullName = fullName.plus(data); it }?.also { println("Non nullable value $it") }
        }

        assertEquals(4, fullName.size)

        assertTrue { fullName.contains(first) }
        assertTrue { fullName.contains(second) }

        val nullFilters = names.filterNotNull()
        assertEquals(2, nullFilters.size)

    }

    @Test
    fun getNullableValue() {
        var value: String? = "Kenda"

        assertEquals(value!!.length, 5)

        value = null

        assertNull(value)
    }

    data class Person(val nama: String, val country: Country?)

    data class Country(val code: String?)

}