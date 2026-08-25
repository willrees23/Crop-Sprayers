package com.github.willrees23.util;

import lombok.experimental.UtilityClass;

// YES, MathsUtil because UK spelling... im sorry
@UtilityClass
public class MathsUtil {

    public int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
