package com.fun.jzoffer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun 2020/04/08.
 *
 * Parses the string argument as a signed integer in the radix
 * specified by the second argument. The characters in the string
 * must all be digits of the specified radix (as determined by
 * whether {@link java.lang.Character#digit(char, int)} returns a
 * nonnegative value), except that the first character may be an
 * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
 * indicate a negative value or an ASCII plus sign {@code '+'}
 * ({@code '\u005Cu002B'}) to indicate a positive value. The
 * resulting integer value is returned.
 *
 * <p>An exception of type {@code NumberFormatException} is
 * thrown if any of the following situations occurs:
 * <ul>
 * <li>The first argument is {@code null} or is a string of
 * length zero.
 *
 * <li>The radix is either smaller than
 * {@link java.lang.Character#MIN_RADIX} or
 * larger than {@link java.lang.Character#MAX_RADIX}.
 *
 * <li>Any character of the string is not a digit of the specified
 * radix, except that the first character may be a minus sign
 * {@code '-'} ({@code '\u005Cu002D'}) or plus sign
 * {@code '+'} ({@code '\u005Cu002B'}) provided that the
 * string is longer than length 1.
 *
 * <li>The value represented by the string is not a value of type
 * {@code int}.
 * </ul>
 *
 * <p>Examples:
 * <blockquote><pre>
 * parseInt("0", 10) returns 0
 * parseInt("473", 10) returns 473
 * parseInt("+42", 10) returns 42
 * parseInt("-0", 10) returns 0
 * parseInt("-FF", 16) returns -255
 * parseInt("1100110", 2) returns 102
 * parseInt("2147483647", 10) returns 2147483647
 * parseInt("-2147483648", 10) returns -2147483648
 * parseInt("2147483648", 10) throws a NumberFormatException
 * parseInt("99", 8) throws a NumberFormatException
 * parseInt("Kona", 10) throws a NumberFormatException
 * parseInt("Kona", 27) returns 411787
 * </pre></blockquote>
 */
public class Str2Int {

  public static int parseInt(String s, int radix) {
    if (StringUtils.isBlank(s)) {
      throw new NumberFormatException("s is blank");
    }
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      throw new IllegalArgumentException(String
          .format("invalid radix, radix between %s and %s", Character.MIN_RADIX,
              Character.MAX_RADIX));
    }
    char firstChar = s.charAt(0);
    int i = 0;
    int limit = -Integer.MAX_VALUE;
    boolean negative = false;
    if (firstChar < '0') {
      if (firstChar == '-') {
        limit = Integer.MIN_VALUE;
        negative = true;
        i++;
      } else if (firstChar != '+') {
        throw new NumberFormatException(String.format("invalid char %s", firstChar));
      }
    }
    int len = s.length();
    int charVal;
    int result = 0;
    char currentChar;
    while (i < len) {
      currentChar = s.charAt(i++);
      charVal = Character.digit(currentChar, radix);
      if (charVal < 0) {
        throw new NumberFormatException(String.format("invalid char %s", currentChar));
      }
      result = result * radix;
      if (result - charVal < limit) {
        throw new NumberFormatException("overflow");
      }
      result -= charVal;
    }
    return negative ? result : -result;
  }

  @Test
  public void test() throws Exception {
    System.out.println(parseInt("0", 10));
    System.out.println(parseInt("473", 10));
    System.out.println(parseInt("-2147483648", 10));
    //System.out.println(parseInt("2147483648", 10));
    //System.out.println(parseInt("99", 8));
    //System.out.println(parseInt("Kona", 10));
    System.out.println(parseInt("Kona", 27));
  }

}
