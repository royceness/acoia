package io.acoia.numbers;

import static org.junit.Assert.*;

import org.junit.Test;

public class EuclidsGCDTest {

  @Test
  public void test() {
    assertEquals(2, EuclidsGCD.gcd(10, 2));
    assertEquals(17, EuclidsGCD.gcd(17*248, 19*17));
    assertEquals(17, EuclidsGCD.gcd(-19*17, 1024*17));
    assertEquals(Long.MAX_VALUE, EuclidsGCD.gcd(Long.MAX_VALUE, Long.MIN_VALUE-1));
  }

}
