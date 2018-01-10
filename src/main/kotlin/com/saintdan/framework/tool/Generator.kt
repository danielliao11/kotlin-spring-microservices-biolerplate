package com.saintdan.framework.tool

import org.apache.commons.text.CharacterPredicates
import org.apache.commons.text.RandomStringGenerator

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
object Generator {

  fun generatorOfLetterAndDigitAndSymbol(): RandomStringGenerator =
      RandomStringGenerator.Builder()
          .withinRange(33, 126)
          .build()

  fun generatorOfLetterAndDigit(): RandomStringGenerator =
      RandomStringGenerator.Builder()
          .withinRange(33, 126)
          .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
          .build()

  fun generatorOfDigit(): RandomStringGenerator =
      RandomStringGenerator.Builder()
          .withinRange(33, 126)
          .filteredBy(CharacterPredicates.DIGITS)
          .build()
}