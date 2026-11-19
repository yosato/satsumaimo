package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertTrue

private val te1 = NonInflLexeme("て:and_then", "て", "particle", listOf("て"))
private val te2 = NonInflLexeme("て:hand", "て", "noun", listOf("て", "手"))
private val taberu = InflLexeme("たべる:eat", "たべる", "verb", listOf("たべる"), Inflection.ichidan)

class LatticeDotTest {
    @Test
    fun each_lexeme_gets_its_own_edge() {
        val dot = lattice_to_dot(Lattice(listOf(te1, te2), "て"))
        assertTrue("0 -> 1 [label=\"て:particle:and_then\"];" in dot)
        assertTrue("0 -> 1 [label=\"て:noun:hand\"];" in dot)
    }

    @Test
    fun edge_without_a_lexeme_is_labelled_unk() {
        val dot = lattice_to_dot(Lattice(emptyList(), "ぬ"))
        assertTrue("0 -> 1 [label=\"ぬ:unk:unk\"];" in dot)
    }

    @Test
    fun inflected_edge_label_has_class_and_form() {
        val dot = lattice_to_dot(Lattice(listOf(taberu), "たべ"))
        assertTrue("0 -> 2 [label=\"たべ:一段未然:eat\"];" in dot)
        assertTrue("0 -> 2 [label=\"たべ:一段連用:eat\"];" in dot)
    }

    @Test
    fun output_is_a_directed_graph_left_to_right() {
        val dot = lattice_to_dot(Lattice(emptyList(), "ぬ"))
        assertTrue(dot.startsWith("digraph lattice {"))
        assertTrue("rankdir=LR;" in dot)
        assertTrue(dot.trimEnd().endsWith("}"))
    }

    @Test
    fun edges_that_cannot_reach_the_end_are_left_out() {
        val a = NonInflLexeme("あ:ah", "あ", "noun", listOf("あ"))
        val ai = NonInflLexeme("あい:love", "あい", "noun", listOf("あい"))
        val isa = NonInflLexeme("いサ:x", "いサ", "noun", listOf("いサ"))
        val dot = lattice_to_dot(Lattice(listOf(a, ai, isa), "あいサ"))
        assertTrue("0 -> 1 [label=\"あ:noun:ah\"];" in dot)
        assertTrue("1 -> 3 [label=\"いサ:noun:x\"];" in dot)
        assertTrue("あい:noun:love" !in dot)
    }
}
