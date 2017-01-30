package io.acoia.numbers;

public class EuclidsGCD {
  
  // Euclid's GCD is actually recursive, but I've converted
  // it to be iterative below.
  public static long gcd(long a, long b) {
    if (a < 0)
      a = -a;
    if (b < 0)
      b = -b;

    while (b != 0) {
      long temp = b;
      b = a % b;
      a = temp;
    }
    
    return a;
  }

}
