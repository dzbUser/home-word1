package test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {
    @Test
    public void mapTest() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }

//        for (Integer i:map.values()){
//            if (i%3 == 0){
//                map.remove(i);
//            }
//        }

        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Integer> entry = it.next();
            if (entry.getKey() % 3 == 0) {
                it.remove();
            }
        }

    }
}
