package parsing

import io.cuttlefish.parsing.Lexer
import org.junit.jupiter.api.Assertions.*
import java.awt.SystemColor.text
import kotlin.test.Test

class LexerTest {

    @Test
    fun testOnlyWhitespaceAndCommentsReturnEmpty() {
        val text = """
            // This is a single-line comment
            
            /* This is a 
               multi-line block comment */
               
        """.trimIndent()

        val lexer = Lexer(text)
        val tokens = lexer.tokenise()

        assertTrue(tokens.isEmpty(), "Expected all whitespace and comments to be skipped.")


    }
}