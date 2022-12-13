package jdk.java.util;

import lab.dummy.generator.ListGenerator;
import org.apache.commons.lang3.time.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * java.util.Collections 테스트 슈트
 */
public class CollectionsTest {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(CollectionsTest.class);

    @Test
    public void testMin() {
        List<Integer> list = Arrays.asList(6, 3, 1, 56, 99, 2, 41, 27, 54, 3);
        assertEquals(Integer.valueOf(1), Collections.min(list));
    }

    @Test
    public void testMax() {
        List<Integer> list = Arrays.asList(6, 3, 1, 56, 99, 2, 41, 27, 54, 3);
        assertEquals(Integer.valueOf(99), Collections.max(list));
        list.get(0);
    }

    @Test
    public void testSort() {
        Integer[] chaos = new Integer[]{105, 99, 2, 44, 73, 99};
        Integer[] sortedAsc = new Integer[]{2, 44, 73, 99, 99, 105};
        Integer[] sortedDesc = new Integer[]{105, 99, 99, 73, 44, 2};
        List<Integer> list = Arrays.asList(chaos);

        // 그냥 호출하면 오름차순
        Collections.sort(list);
        assertArrayEquals(sortedAsc, list.toArray());

        // 내림차순
        Collections.sort(list, Collections.reverseOrder());
        assertArrayEquals(sortedDesc, list.toArray());

        // Comparator 직접 작성하기: 오름차순
        list = Arrays.asList(chaos);
        // 아래 줄은 요렇게 쓴 거랑 같음:
		/*
		Collections.sort(list, (o1, o2) -> {
		  return o1 - o2;
		});
		*/
        Collections.sort(list, (o1, o2) -> o1 - o2);
        // 음수를 반환하면 o1을 좌측으로, 0을 반환하게 하면 그대로 유지, 양수를 반환하면 o1을 우측으로 정렬한다.
        // 이 경우 음수가 반환되려면 o1이 o2보다 작아야하므로 작은 값이 좌측으로 정렬되는 오름차순 정렬이 된다.
        assertArrayEquals(sortedAsc, list.toArray());

        // Comparator 직접 작성하기: 내림차순
        list = Arrays.asList(chaos);
        Collections.sort(list, (o1, o2) -> o2 - o1);
        // 음수: o1을 좌측으로, 0: 유지, 양수: 01을 우측으로
        // 이 경우 음수가 반환되려면 o1이 o2보다 커야하므로 큰 값이 좌측으로 정렬되는 내림차순 정렬이다.
        assertArrayEquals(sortedDesc, list.toArray());
    }

    /**
     * 정렬 알고리즘 중 가장 빠르다는 이진 탐색 테스트. 빠르긴 한데 정렬이 불가능한 데이터에는 사용할 수 없는 한계가 있다.<p>
     * <p>
     * 경우에 따라 일반 탐색이 더 빠를 수도 있으나 일정한 결과를 보장하는 것은 아님.(eg. 정렬했더니 우연히도 찾으려는 데이터가 앞 쪽에 있다면?)<p>
     * <p>
     * 중요: binarySearch()를 사용하기 전에 리스트를 반드시 정렬해야 함. 안그러면 (-(리스트의 길이) - 1)이 반환됨. (eg. 길이가 10이면 -11)<p>
     * <p>
     * 정작 찾는 것보다 정렬이 더 오래 걸림:
     * <ul>
     *     <li>10만개의 문자열 정렬: 102,815,600 나노초</li>
     *     <li>정렬 후 찾기까지: 67,400 나노초</li>
     *     <li>45123 인덱스의 문자열을 순차 탐색: 4,995,501 나노초</li>
     * </ul>
     */
    @Test
    public void testBinarySearch() {
        final String findMe = "날찾아봐요요러케";
        List<String> list = ListGenerator.generateStringList(100000, 45123, findMe);

        // 만약 그냥 처음부터 찾는다면 경과 시간은?
        StopWatch watch = new StopWatch();
        watch.start(); // 시작

        Collections.sort(list);

        watch.suspend();
        long afterSortingTime = watch.getNanoTime();
        logger.debug("sorting time: {}", afterSortingTime);
        watch.resume();

        int found = Collections.binarySearch(list, findMe);

        watch.stop();
        long afterSearchTime = watch.getNanoTime();
        logger.debug("pure searching time: {}", afterSearchTime - afterSortingTime);

        assertEquals(findMe, list.get(found));
    }

    @Test
    public void testPlanSearch() {
        final String findMe = "날찾아봐요요러케";
        List<String> list = ListGenerator.generateStringList(100000, 45123, findMe);

        // 만약 그냥 처음부터 찾는다면 경과 시간은?
        StopWatch watch = new StopWatch();
        watch.start(); // 시작

        int found = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(findMe)) {
                found = i;
                break;
            }
        }

        watch.stop();
        logger.debug("searching time: {}", watch.getNanoTime());

        assertEquals(findMe, list.get(found));
    }

    /**
     * <pre>
     * An instantiation of a generic type where the type argument is a wildcard (as opposed to a concrete type). A wildcard parameterized
     * type is an instantiation of a generic type where at least one type argument is a wildcard. Examples of wildcard parameterized types
     * are Collection<?> , List<? extends Number> , Comparator<? super String> and Pair<String,?>. A wildcard parameterized type denotes a
     * family of types comprising concrete instantiations of a generic type. The kind of the wildcard being used determines which concrete
     * parameterized types belong to the family. For instance, the wildcard parameterized type Collection<?> denotes the family of all
     * instantiations of the Collection interface regardless of the type argument. The wildcard parameterized type List<? extends Number>
     * denotes the family of all list types where the element type is a subtype of Number. The wildcard parameterized type Comparator<?
     * super String> is the family of all instantiations of the Comparator interface for type argument types that are supertypes of String.
     *
     * A wildcard parameterized type is not a concrete type that could appear in a new expression. A wildcard parameterized type is similar
     * to an interface type in the sense that reference variables of a wildcard parameterized type can be declared, but no objects of the
     * wildcard parameterized type can be created. The reference variables of a wildcard parameterized type can refer to an object that is
     * of a type that belongs to the family of types that the wildcard parameterized type denotes.
     * </pre>
     *
     * <a href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/ParameterizedTypes.html#WIldcard+Instantiations">Generic And Parameterized Types</a>
     *
     * @author noritersand
     */
    @Test
    public void testWildcardParameterizedType() {
        List<? super String> strList = Arrays.asList(new String[]{"a", "b"});
        assertTrue(strList.contains("a"));
    }


}
