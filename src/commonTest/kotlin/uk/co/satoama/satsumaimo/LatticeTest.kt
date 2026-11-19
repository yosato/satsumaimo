package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val satsuma = NonInflLexeme("さつま:satsuma", "さつま", "noun", listOf("さつま", "サツマ", "薩摩"))
private val imo = NonInflLexeme("いも:potato", "いも", "noun", listOf("いも", "イモ", "芋"))
private val taberu = InflLexeme("たべる:eat", "たべる", "verb", listOf("たべる", "食べる"), Inflection.ichidan)
private val te1 = NonInflLexeme("て:and_then", "て", "particle", listOf("て"))
private val te2 = NonInflLexeme("て:hand", "て", "noun", listOf("て", "手"))

class LatticeTest {
    @Test
    fun edges_cover_surface_matches_in_kana() {
        val edges = Lattice(listOf(satsuma, imo), "さつまいも").edges
        assertTrue(Edge(0, 3, "さつま", satsuma) in edges)
        assertTrue(Edge(3, 5, "いも", imo) in edges)
    }

    @Test
    fun edges_cover_surface_matches_in_kanji_and_kana_mix() {
        val edges = Lattice(listOf(satsuma, imo), "薩摩いも").edges
        assertTrue(Edge(0, 2, "薩摩", satsuma) in edges)
        assertTrue(Edge(2, 4, "いも", imo) in edges)
    }

    @Test
    fun edges_cover_surface_matches_in_katakana_and_kanji_mix() {
        val edges = Lattice(listOf(satsuma, imo), "サツマ芋").edges
        assertTrue(Edge(0, 3, "サツマ", satsuma) in edges)
        assertTrue(Edge(3, 4, "芋", imo) in edges)
    }

    @Test
    fun inflected_surfaces_map_to_their_lemma() {
        val edges = Lattice(listOf(taberu), "たべて").edges
        assertTrue(Edge(0, 2, "たべ", taberu, Inflection.mizen) in edges)
        assertTrue(Edge(0, 2, "たべ", taberu, Inflection.renyo) in edges)
        assertTrue(Edge(0, 3, "たべて", taberu) !in edges)
    }

    @Test
    fun homonyms_are_distinct_edges() {
        val edges = Lattice(listOf(te1, te2), "て").edges
        assertTrue(Edge(0, 1, "て", te1) in edges)
        assertTrue(Edge(0, 1, "て", te2) in edges)
    }

    @Test
    fun unknown_characters_get_fallback_edges() {
        assertEquals(
            listOf(Edge(0, 1, "ぬ", null), Edge(1, 2, "ぺ", null)),
            Lattice(emptyList(), "ぬぺ").edges
        )
    }

    @Test
    fun empty_surfaces_are_ignored() {
        assertEquals(
            listOf(Edge(0, 1, "あ", null)),
            Lattice(listOf(NonInflLexeme("x:x", "x", "noun", listOf(""))), "あ").edges
        )
    }

    @Test
    fun paths_follow_the_edges() {
        assertEquals(
            listOf(listOf(Edge(0, 3, "さつま", satsuma), Edge(3, 5, "いも", imo))),
            Lattice(listOf(satsuma, imo), "さつまいも").paths()
        )
    }

    @Test
    fun overlapping_surfaces_give_every_split() {
        val satsu = NonInflLexeme("さつ:bill", "さつ", "noun", listOf("さつ"))
        val ma = NonInflLexeme("ま:space", "ま", "noun", listOf("ま"))
        val whole = NonInflLexeme("さつま:satsuma", "さつま", "noun", listOf("さつま"))
        assertEquals(
            setOf(
                listOf(Edge(0, 3, "さつま", whole)),
                listOf(Edge(0, 2, "さつ", satsu), Edge(2, 3, "ま", ma))
            ),
            Lattice(listOf(whole, satsu, ma), "さつま").paths().toSet()
        )
    }

    @Test
    fun long_vowel_mark_gets_no_fallback_edge() {
        val long = NonInflLexeme("あー:ah", "あー", "noun", listOf("あー"))
        assertEquals(
            listOf(Edge(0, 2, "あー", long), Edge(2, 3, "あ", null)),
            Lattice(listOf(long), "あーあ").edges
        )
    }

    @Test
    fun fallback_edges_are_only_added_where_a_path_needs_them() {
        val whole = NonInflLexeme("さつま:satsuma", "さつま", "noun", listOf("さつま"))
        assertEquals(
            listOf(Edge(0, 3, "さつま", whole)),
            Lattice(listOf(whole), "さつま").edges
        )
    }
}
