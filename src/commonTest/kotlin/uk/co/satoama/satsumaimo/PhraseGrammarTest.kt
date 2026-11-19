package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private val allLexemes = HiraganaNonInflLex.lexemes + OneCharNonInflLex.lexemes +
    MultiCharNonInflLex.lexemes + InflLex.lexemes

private fun edge(id: String, surface: String, form: String? = null): Edge =
    Edge(0, surface.length, surface, allLexemes.first { it.id == id }, form)

private fun unk(surface: String): Edge = Edge(0, surface.length, surface, null)

class PhraseGrammarTest {
    @Test
    fun noun_compound_is_a_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(edge("さつま:satsuma", "さつま"), edge("いも:potato", "いも"))))
    }

    @Test
    fun noun_with_a_case_particle_is_a_postpositional_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(edge("さつま:satsuma", "さつま"), edge("を:object", "を"))))
    }

    @Test
    fun case_particle_cannot_start_a_phrase() {
        assertFalse(PhraseGrammar.accepts(listOf(edge("を:object", "を"), edge("さつま:satsuma", "さつま"))))
    }

    @Test
    fun case_particle_may_be_followed_by_a_binding_particle() {
        assertTrue(
            PhraseGrammar.accepts(
                listOf(edge("さつま:satsuma", "さつま"), edge("に:location", "に"), edge("も:also", "も"))
            )
        )
    }

    @Test
    fun final_particle_closes_a_noun_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(edge("さつま:satsuma", "さつま"), edge("ね:tag", "ね"))))
    }

    @Test
    fun postpositional_phrase_precedes_a_verb_phrase() {
        assertTrue(
            PhraseGrammar.accepts(
                listOf(
                    edge("さつま:satsuma", "さつま"),
                    edge("を:object", "を"),
                    edge("たべる:eat", "たべ", Inflection.renyo),
                    edge("て:and_then", "て")
                )
            )
        )
    }

    @Test
    fun verb_te_verb_auxiliary_is_a_phrase() {
        assertTrue(
            PhraseGrammar.accepts(
                listOf(
                    edge("たべる:eat", "たべ", Inflection.renyo),
                    edge("て:and_then", "て"),
                    edge("みる:see", "み", Inflection.renyo),
                    edge("たい:want", "たい", Inflection.shushi)
                )
            )
        )
    }

    @Test
    fun negative_auxiliary_follows_mizen() {
        assertTrue(
            PhraseGrammar.accepts(
                listOf(edge("たべる:eat", "たべ", Inflection.mizen), edge("ぬ:negative", "ぬ", Inflection.shushi))
            )
        )
    }

    @Test
    fun negative_auxiliary_does_not_follow_renyo() {
        assertFalse(
            PhraseGrammar.accepts(
                listOf(edge("たべる:eat", "たべ", Inflection.renyo), edge("ぬ:negative", "ぬ", Inflection.shushi))
            )
        )
    }

    @Test
    fun auxiliary_cannot_start_a_phrase() {
        assertFalse(
            PhraseGrammar.accepts(
                listOf(
                    edge("ぬ:negative", "ぬ", Inflection.shushi),
                    edge("べら:wrasse", "べら"),
                    edge("め:eye", "め"),
                    edge("ら:plural", "ら")
                )
            )
        )
    }

    @Test
    fun auxiliary_does_not_follow_a_case_particle() {
        assertFalse(
            PhraseGrammar.accepts(
                listOf(edge("さつま:satsuma", "さつま"), edge("を:object", "を"), edge("ぬ:negative", "ぬ", Inflection.shushi))
            )
        )
    }

    @Test
    fun past_auxiliary_does_not_follow_a_noun() {
        assertFalse(
            PhraseGrammar.accepts(
                listOf(edge("さつま:satsuma", "さつま"), edge("た:past", "た", Inflection.shushi))
            )
        )
    }

    @Test
    fun unknown_word_alone_is_a_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(unk("ぬべらめら"))))
    }

    @Test
    fun unknown_word_then_suffix_is_a_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(unk("ぬべらめ"), edge("ら:plural", "ら"))))
    }

    @Test
    fun known_noun_then_unknown_word_is_a_phrase() {
        assertTrue(PhraseGrammar.accepts(listOf(edge("け:hair", "け"), unk("ぬべらめら"))))
    }

    @Test
    fun suffix_cannot_start_a_phrase() {
        assertFalse(PhraseGrammar.accepts(listOf(edge("ら:plural", "ら"), unk("ぬべらめ"))))
    }

    @Test
    fun two_unknown_words_may_be_joined_by_a_particle() {
        assertTrue(PhraseGrammar.accepts(listOf(unk("ぬ"), edge("を:object", "を"), unk("ぺ"))))
    }
}
