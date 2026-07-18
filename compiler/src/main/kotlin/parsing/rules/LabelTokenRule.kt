package io.cuttlefish.parsing.rules

import io.cuttlefish.parsing.*

class LabelTokenRule : TokenRule {
    private val regex = Regex(
        """
        ^([a-zA-Z0-9_.]*):
    """.trimIndent()
    )

    override fun match(
        source: String, index: Int, line: Int, column: Int
    ): TokenRule.MatchResult? {
        val match = regex.find(source.substring(index)) ?: return null
        if (match.range.first != 0) return null
        val labelName = match.groups[1]!!

        return TokenRule.MatchResult(
            LabelDefToken(labelName.value, "NO LEXEME!", line, column), match.value.length
        )
    }


}