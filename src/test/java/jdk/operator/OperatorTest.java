package jdk.operator;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 연산자 테스트 슈트
 * 
 * @since 2017-07-27
 * @author fixalot
 */
@Slf4j
public class OperatorTest {

	/**
	 * 산술 연산자(arithmetic operator) 테스트
	 */
	@Test
	public void arithmeticOperator() {
		int n = 2665 / 1333; // 연산할 때 타입 명시가 없으면 int 타입으로 처리됨
        long l = 2665 / 1333;
        double d = 2665D / 1333D;

        assertEquals(1, n);
        assertEquals(1, l);
        assertEquals(1.9992498124531132783195798949737, d);
	}

	/**
	 * 음수 테스트
	 */
	@Test
    public void negativeNumberTest() {
        int p = 123;
        assertEquals(-123, -p);
        assertEquals(-123, -1 * p);

        int n = -456;
        assertEquals(456, -n);
        assertEquals(456, -1 * n);
    }

	/**
	 * 할당 연산자<sup>assignment operator</sup> 테스트
	 */
	@Test
	public void assignmentOperator() {
		// 할당
		int n = 1;
		assertEquals(1, n);
		
		// 산술 연산 후 할당
		n += 2;
		assertEquals(3, n);
		n -= 1;
		assertEquals(2, n);
		n *= 5;
		assertEquals(10, n);
		n /= 2;
		assertEquals(5, n);
		
		// 변수 하나 이상을 한 번에 초기화
		int num = n = 9 + 1;
		assertEquals(10, num);
		assertEquals("20", String.valueOf(num = 20));
		
		// 좀 더 많이 해볼까?
		int a, b, c, d, e;
		a = b = c = d = e = 100; // 다섯개를 한 번에 조로록
		assertTrue(a == b && b == c && c == d && d == e && e == 100); // 당연히 다섯 변수의 값은 같음
	}

	/**
	 * 음수 부호 테스트
	 */
	@Test
	public void negativeSign() {
		assertTrue(-3 < 0);
		final int three = 3;
		assertEquals(-3, -(three));
		assertEquals(-3, -three);
		assertEquals(3, -(-three));
		assertEquals(-3, -(-(-three)));

		// convert to negative value
		assertEquals(-45, 45 * -1);
		assertEquals(-90, 90 * -1);
		assertEquals(-312, 312 * -1);
	}
	
	@Test
	public void ternaryOperator() {
		String first = "1";
		String second = "2";
		String third = "3";
		String fourth = "";

		String result = (!fourth.isEmpty()) ? fourth : (!third.isEmpty()) ? third : (!second.isEmpty()) ? second : first;
		assertEquals("3", result);
	}

	@Test
	public void unaryOperator() {
		int a = 0;
		assertEquals(1, ++a); // 변수 반환 이전에 연산. 따라서 +1의 결과인 1
		assertEquals(1, a);
		assertEquals(1, a++); // 변수 반환 이후에 연산. 따라서 +1 하기 전의 결과인 1
		assertEquals(2, a);

		// 반복문에서 증감연산자는 피연산자의 어느쪽에 있어도 상관 없다.
		for (int i = 0; i < 2; ++i) {
			log.debug("{}", i);
		}
		for (int i = 0; i < 2; i++) {
			log.debug("{}", i);
		}
	}
	
	@Test
	public void binaryOperator() {
		assertEquals(2, 1 + 1);
	}
	
	@Test
	public void modulusOperator() {
		assertEquals(0, 0 % 3);
		assertEquals(1, 1 % 3);
		assertEquals(2, 2 % 3);
		assertEquals(0, 3 % 3);
		assertEquals(1, 4 % 3);
		assertEquals(2, 5 % 3);
		assertEquals(0, 6 % 3);
	}

	@Test
	public void leftShiftOperator() {
		int a = 9;
		assertEquals("1001", Integer.toBinaryString(a));
		assertEquals(36, a << 2);
		assertEquals("100100", Integer.toBinaryString(a << 2));
	}

	@Test
	public void rightShiftOperator() {
		int a = 9;
		assertEquals("1001", Integer.toBinaryString(a));
		assertEquals("10", Integer.toBinaryString(a >> 2));
		assertEquals(2, a >> 2);

		int b = -9;
		assertEquals("11111111111111111111111111110111", Integer.toBinaryString(b));
		assertEquals("11111111111111111111111111111101", Integer.toBinaryString(b >> 2));
		assertEquals(-3, b >> 2);
	}

	@Test
	public void unsignedRightShiftOperator() {
		int a = 9; // 1001
		assertEquals("1001", Integer.toBinaryString(a));
		assertEquals("10", Integer.toBinaryString(a >>> 2));
		assertEquals(2, a >>> 2);

		int b = -9; // 11111111111111111111111110011100
		assertEquals("11111111111111111111111111110111", Integer.toBinaryString(b));
		assertEquals(1073741821, b >>> 2);
		assertEquals("111111111111111111111111111101", Integer.toBinaryString(b >>> 2));
	}
}
