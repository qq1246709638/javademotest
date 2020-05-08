import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : duanruiqiang
 * create at:  2020/5/8  8:03 下午
 * @description: 集合测试类
 */
public class MyList {
    private static List<List<Integer>> data = new ArrayList<>();

    public static void main(String[] args) {
        //将会oom，强引用1000个0-10w的list
//        for (int i = 0; i < 1000; i++) {
//            List<Integer> collect = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
//            data.add(collect.subList(0,1));
//        }

        //jdk1.8的removeif，安全删除集合中元素
        String[] strings = {"1", "2", "3","4"};
        ArrayList<String> strings1 = new ArrayList<>(Arrays.asList(strings));
        strings1.removeIf(s -> "1".equals(s));
        //java.util.ConcurrentModificationException
//        for (String str : strings1) {
//            if ("2".equals(str)){
//                strings1.remove(str);
//            }
//        }
        System.out.println(strings1.toString());
    }
}
