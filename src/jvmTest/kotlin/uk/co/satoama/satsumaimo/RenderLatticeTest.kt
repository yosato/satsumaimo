package uk.co.satoama.satsumaimo

import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class RenderLatticeTest {
    private val lexemes = HiraganaNonInflLex.lexemes + OneCharNonInflLex.lexemes +
        MultiCharNonInflLex.lexemes + InflLex.lexemes

    private val texts = listOf(
        "さつまいもたべてみたい",
        "けぬべらめら"
    )

    @Test
    fun write_lattices_of_the_example_strings() {
        val dot = lattices_to_dot(texts.map { it to Lattice(lexemes, it) })
        File("build/lattice.dot").writeText(dot)
        assertTrue(dot.isNotEmpty())
    }
}
