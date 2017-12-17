package com.saintdan.framework.tool

import org.apache.commons.text.CharacterPredicates
import org.apache.commons.text.RandomStringGenerator

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
class Generator {
  companion object {
    fun generatorOfLetterAndDigit(): RandomStringGenerator {
      return RandomStringGenerator.Builder()
          .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
          .build()
    }
  }
}