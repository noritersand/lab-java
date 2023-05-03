package jdk.java.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link ArrayList} 테스트
 *
 * @author fixalot
 * @since 2017-07-27
 */
@Slf4j
public class ArrayListTest {

    @Test
    void testAdd() {
        List<Object> list = new ArrayList<>();
        list.add(null);
    }

    @Test
    void wrongWayClone() {
        ArrayList<ListTestModel> origins = new ArrayList<>();
        origins.add(new ListTestModel("123"));
        origins.add(new ListTestModel("456"));
        origins.add(new ListTestModel("789"));

        // 이건 클론이 아니라 그냥 복사. 같은 인스턴스를 사용한다.
        ArrayList<ListTestModel> newbies = new ArrayList<>(origins);

        assertEquals(origins.get(0), newbies.get(0));
        assertSame(origins.get(0), newbies.get(0));
    }

    @Test
    void cloneManual() {
        ArrayList<ListTestModel> origins = new ArrayList<>();
        origins.add(new ListTestModel("123"));
        origins.add(new ListTestModel("456"));
        origins.add(new ListTestModel("789"));

        ArrayList<ListTestModel> newbies = new ArrayList<>();
        for (ListTestModel ele : origins) {
            newbies.add(new ListTestModel(ele.getName()));
        }

        assertNotEquals(origins.get(0), newbies.get(0));
        assertNotSame(origins.get(0), newbies.get(0));
    }

    @Test
    void getSize() {
        ArrayList<String> stringList = new ArrayList<>();
        assertEquals(0, stringList.size());
        stringList = new ArrayList<>(10); // 리스트의 capacity를 지정한다. size가 아니다.
        assertEquals(0, stringList.size()); // capacity 지정과 size는 관련 없음.
    }

    /**
     * 리스트 안의 특정 요소 삭제하기 #1
     *
     * @author fixalot
     */
    @Test
    void removeElementWithIterator() {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String s = iter.next(); // 반드시 remove() 전에 호출되어야 함.
            if ("a".equals(s)) {
                iter.remove();
            }
        }
        assertEquals(Arrays.asList("b", "c", "d"), list);
        assertEquals(3, list.size());
    }

    /**
     * 리스트 안의 특정 요소 삭제하기 #2
     *
     * @author fixalot
     */
    @Test
    void removeElementByIndex() {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if ("a".equals(str)) {
                list.remove(i);
                i--;
            }
        }
        assertEquals(Arrays.asList("b", "c", "d"), list);
        assertEquals(3, list.size());
    }

    /**
     * 리스트 안의 특정 요소 삭제하기 #3
     *
     * @author fixalot
     */
    @Test
    void removeElementInWhile() {
        String[] strs = {"a", "b", "c", "d", "e"};

        // 앞 3개 지우기
        List<String> list = Arrays.stream(strs).collect(Collectors.toList());
        {
            int cnt = 0;
            while (true) {
                if (3 == cnt) {
                    break;
                }
                list.remove(0);
                cnt++;
            }
        }
        assertEquals("[d, e]", list.toString());

        // 뒤 3개 지우기
        list = Arrays.stream(strs).collect(Collectors.toList());
        for (int cnt = 0, i = list.size(); 0 <= i; i--) {
            if (3 > cnt) {
                list.remove(list.size() - 1);
                i++;
                cnt++;
            }
        }
        assertEquals("[a, b]", list.toString());
    }

//	@Test
//	public void removeElementWithStream() {
//		ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
//		list.stream().set
//	}

    /**
     * 리스트 검색 테스트 1: for문으로 전체 검색
     */
    @Test
    void search() {
        Integer[] values = {1, 3, 7};
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(values));
        final int targetValue = 3;
        int targetIndex = 99;

        for (int i = 0; i < list.size(); i++) {
            if (targetValue == list.get(i)) {
                targetIndex = i;
                break;
            }
        }
        assertEquals(1, targetIndex);
    }

    /**
     * 리스트 검색 테스트 3: java8의 StreamAPI 사용
     */
    @Test
    void searchWithStream() {
        List<HashMap<String, Object>> list = getSomeList();
        List<HashMap<String, Object>> searchResult
                = list.stream().filter(ele -> "b".equals(ele.get("key")) || "d".equals(ele.get("key"))).collect(Collectors.toList());
        assertEquals(4, list.size()); // 원래 리스트는 변하지 않음
        assertEquals(2, searchResult.size()); // filter의 조건에 맞는 새로운 리스트의 길이
        assertEquals("456", searchResult.get(0).get("value"));
        assertEquals("012", searchResult.get(1).get("value"));
    }

    @Test
    void testToArray() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(123);
        list.add(234);
        list.add(345);
        assertArrayEquals(new Integer[]{123, 234, 345}, list.toArray(new Integer[list.size()]));
    }

    @Test
    void fromArray() {
        Integer[] values = {1, 3, 7};
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(values));
        assertEquals("[1, 3, 7]", list.toString());
    }

    @Test
    void fromArrayByStream() {
        String[] strs = {"a", "b", "c", "d", "e"};
        List<String> stringList = Arrays.stream(strs).collect(Collectors.toList());
        assertEquals("[a, b, c, d, e]", stringList.toString());

        int[] spam = {1, 2, 3};
        List<Integer> integerList = Arrays.stream(spam).boxed().collect(Collectors.toList());
        assertEquals("[1, 2, 3]", integerList.toString());
    }

    @Test
    void testClear() {
        ArrayList<Integer> list = new ArrayList<>();
        assertNotNull(list);
        list.clear();
        assertNotNull(list);
    }

    @Test
    void arrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(8);
        list.add(7);
        list.add(6);
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        list.remove(1);

        assertEquals("[9, 7, 6, 5, 4, 3, 2, 1]", list.toString());
    }

    /**
     * sublist 테스트. substring처럼 인덱스 범위에 해당하는 요소를 추출한다.
     *
     * @author fixalot
     */
    @Test
    void testSublist() {
        List<Integer> numbers = Arrays.asList(5, 3, 1, 2, 9, 5, 0, 7);

        List<Integer> firstBorn = numbers.subList(0, 1); // 5
        assertEquals(1, firstBorn.size());
        assertEquals(List.of(5), firstBorn);

        List<Integer> head = numbers.subList(0, 4); // 5, 3, 1, 2
        assertEquals(4, head.size());
        assertEquals(Arrays.asList(5, 3, 1, 2), head);

        List<Integer> tail = numbers.subList(4, numbers.size()); // 9, 5, 0, 7
        assertEquals(4, tail.size());
        assertEquals(Arrays.asList(9, 5, 0, 7), tail);
    }

    @Test
    void testJoin() {
        List<String> texts = Arrays.asList("a", "b", "c");
        assertEquals("a, b, c", String.join(", ", texts));
    }

    @Test
    void testForEach() {
        Integer[] values = {1, 3, 7};
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(values));
        list.forEach(k -> {
            log.debug("ele: {}", k);
        });
    }

    @Test
    void testRemoveif() {
        Integer[] values = {1, 3, 7};
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(values));
        list.removeIf(p -> 3 == p);
        assertEquals("[1, 7]", list.toString());
    }

    private ArrayList<HashMap<String, Object>> getSomeList() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "a");
        map.put("value", "123");
        list.add(map);
        map = new HashMap<>();
        map.put("key", "b");
        map.put("value", "456");
        list.add(map);
        map = new HashMap<>();
        map.put("key", "c");
        map.put("value", "789");
        list.add(map);
        map = new HashMap<>();
        map.put("key", "d");
        map.put("value", "012");
        list.add(map);
        return list;
    }

    @SuppressWarnings("unused")
    private class ListTestModel {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ListTestModel(String name) {
            this.name = name;
        }
    }
}
