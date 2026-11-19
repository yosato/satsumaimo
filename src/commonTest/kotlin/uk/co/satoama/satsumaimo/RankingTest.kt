package uk.co.satoama.satsumaimo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val normaliser = Normaliser()

private fun assert_ranked_by_tier(
    input: String,
    plausible: Set<String>,
    implausible: Set<String>,
    unacceptable: Set<String>
) {
    plausible.forEach { assertTrue(normaliser.score(input, it) >= 0.80, "$it should be plausible") }
    implausible.forEach { assertTrue(normaliser.score(input, it) > 0.10 && normaliser.score(input, it) < 0.80, "$it should be implausible") }
    unacceptable.forEach { assertTrue(normaliser.score(input, it) <= 0.10, "$it should be unacceptable") }
    val ranked = normaliser.rank(input, (unacceptable + implausible + plausible).toList())
    assertEquals(plausible.size + implausible.size + unacceptable.size, ranked.size)
    val tier = { candidate: String -> if (candidate in plausible) 0 else if (candidate in implausible) 1 else 2 }
    assertEquals(ranked.sortedBy(tier), ranked)
    assertTrue(normaliser.canonical_form(input) in plausible)
}

class RankingTest {
    @Test
    fun verb_phrase_in_kana() {
        assert_ranked_by_tier(
            "たべてみたい",
            plausible = setOf("食べてみたい"),
            implausible = setOf("食べて見たい", "たべてみたい"),
            unacceptable = setOf("食べてみた胃", "食べてミタイ")
        )
    }

    @Test
    fun noun_in_kana() {
        assert_ranked_by_tier(
            "さつまいも",
            plausible = setOf("薩摩芋", "さつま芋", "サツマイモ", "さつまいも", "薩摩イモ"),
            implausible = setOf("サツマいも", "薩摩いも", "さつまイモ", "サツマ芋", "薩摩薯"),
            unacceptable = setOf("サツマイも", "薩摩伊藻", "さつま胃も", "去つま芋", "さつマイモ")
        )
    }

    @Test
    fun noun_and_verb_phrase_in_kana() {
        assert_ranked_by_tier(
            "さつまいもたべてみたい",
            plausible = setOf("さつま芋食べてみたい", "サツマイモ食べてみたい"),
            implausible = setOf("さつまいもたべてみたい", "サツマいも食べてみたい"),
            unacceptable = setOf("サツマイも食べてみたい", "さつまいも食べてみた胃", "薩摩胃も食べてみたい")
        )
    }

    @Test
    fun okurigana_variants_equally_plausible_in_a_verb() {
        assert_ranked_by_tier(
            "向かう",
            plausible = setOf("向かう", "向う"),
            implausible = emptySet(),
            unacceptable = setOf("無かう", "向科う")
        )
    }

    @Test
    fun okurigana_variants_skewed_in_a_verb() {
        assert_ranked_by_tier(
            "変わる",
            plausible = setOf("変わる"),
            implausible = setOf("変る"),
            unacceptable = setOf("代わる", "変輪る")
        )
    }

    @Test
    fun okurigana_variants_skewed_in_an_adjective() {
        assert_ranked_by_tier(
            "少ない",
            plausible = setOf("少ない"),
            implausible = setOf("少い"),
            unacceptable = setOf("小ない", "少内")
        )
    }

    @Test
    fun okurigana_variants_equally_plausible_in_an_adjective() {
        assert_ranked_by_tier(
            "短い",
            plausible = setOf("短い", "短かい"),
            implausible = emptySet(),
            unacceptable = setOf("短胃", "短かイ")
        )
    }

    @Test
    fun okurigana_variants_equally_plausible_in_a_derived_noun() {
        assert_ranked_by_tier(
            "問い合わせ",
            plausible = setOf("問い合わせ", "問合せ"),
            implausible = emptySet(),
            unacceptable = setOf("問い合わ背", "訪い合わせ")
        )
    }

    @Test
    fun okurigana_variants_equally_plausible_in_another_derived_noun() {
        assert_ranked_by_tier(
            "打ち合わせ",
            plausible = setOf("打ち合わせ", "打合せ"),
            implausible = emptySet(),
            unacceptable = setOf("打ち合輪せ", "討ち合わせ")
        )
    }

    @Test
    fun okurigana_variants_skewed_in_a_derived_noun() {
        assert_ranked_by_tier(
            "受付け",
            plausible = setOf("受付", "受け付け"),
            implausible = setOf("受付け"),
            unacceptable = setOf("受け付毛", "受付ケ")
        )
    }

    @Test
    fun trailing_long_vowel_may_be_dropped_in_server() {
        assert_ranked_by_tier(
            "サーバー",
            plausible = setOf("サーバー", "サーバ"),
            implausible = emptySet(),
            unacceptable = setOf("サ一バ一", "さーばー")
        )
    }

    @Test
    fun trailing_long_vowel_dropped_in_cooler_is_implausible() {
        assert_ranked_by_tier(
            "クーラー",
            plausible = setOf("クーラー"),
            implausible = setOf("クーラ"),
            unacceptable = setOf("くーらー")
        )
    }

    @Test
    fun oov_word_in_kana() {
        assert_ranked_by_tier(
            "ぬぺらぽ",
            plausible = setOf("ヌペラポ"),
            implausible = setOf("ぬぺらぽ"),
            unacceptable = setOf("奴ぺ等ぽ", "ぬ弊等ぽ")
        )
    }

    @Test
    fun oov_word_in_known_context() {
        assert_ranked_by_tier(
            "ぬぺらぽをたべてみたい",
            plausible = setOf("ヌペラポを食べてみたい"),
            implausible = setOf("ぬぺらぽを食べてみたい", "ヌペラポをたべてみたい", "ヌペラポを食べて見たい"),
            unacceptable = setOf("奴ぺ等ぽを食べてみたい", "ヌペラポを食べてみた胃")
        )
    }
}
