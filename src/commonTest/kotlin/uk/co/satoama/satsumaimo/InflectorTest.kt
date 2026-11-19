package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals

private fun form(form: String, surface: String) = InflectedForm(form, surface)

class InflectorTest {
    @Test
    fun ichidan_verb_in_kana() {
        assertEquals(
            setOf(
                form(Inflection.mizen, "たべ"),
                form(Inflection.renyo, "たべ"),
                form(Inflection.shushi, "たべる"),
                form(Inflection.rentai, "たべる"),
                form(Inflection.katei, "たべれ"),
                form(Inflection.meirei, "たべろ"),
                form(Inflection.meirei, "たべよ")
            ),
            Inflector.forms("たべる", Inflection.ichidan).toSet()
        )
    }

    @Test
    fun ichidan_verb_in_kanji_and_kana() {
        assertEquals(
            setOf(
                form(Inflection.mizen, "食べ"),
                form(Inflection.renyo, "食べ"),
                form(Inflection.shushi, "食べる"),
                form(Inflection.rentai, "食べる"),
                form(Inflection.katei, "食べれ"),
                form(Inflection.meirei, "食べろ"),
                form(Inflection.meirei, "食べよ")
            ),
            Inflector.forms("食べる", Inflection.ichidan).toSet()
        )
    }

    @Test
    fun godan_verb_with_sound_change() {
        assertEquals(
            setOf(
                form(Inflection.mizen, "かか"),
                form(Inflection.mizen, "かこ"),
                form(Inflection.renyo, "かき"),
                form(Inflection.renyoOnbin, "かい"),
                form(Inflection.shushi, "かく"),
                form(Inflection.rentai, "かく"),
                form(Inflection.katei, "かけ"),
                form(Inflection.meirei, "かけ")
            ),
            Inflector.forms("かく", Inflection.godan).toSet()
        )
    }

    @Test
    fun non_inflecting_category_gives_no_forms() {
        assertEquals(emptyList(), Inflector.forms("さつま", "普名"))
    }
}
