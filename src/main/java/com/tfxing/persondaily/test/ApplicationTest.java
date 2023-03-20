package com.tfxing.persondaily.test;

import com.tfxing.persondaily.entity.constant.JobOrderStateNodeConstant;
import com.tfxing.persondaily.entity.po.Person;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class ApplicationTest {

    @Test
    public void test01() {
        ArrayList list = null;
        System.out.println(CollectionUtils.isEmpty(list));
//        System.out.println("hello world");
    }

    @Test
    public void test02() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal two = BigDecimal.valueOf(0);
        System.out.println(one.compareTo(two) > 0);
    }

    @Test
    public void test03() {
        Long l = 0L;
        Long l1 = 1L;
        l = l + l1;
        System.out.println(l);

        BigDecimal b = BigDecimal.ZERO;
        BigDecimal b1 = null;
        System.out.println(b.add(b1));
    }

    @Test
    public void test04() {
        System.out.println(Arrays.asList("PLACEORDER", "CANCELSTOWAGE", "BEPUTINSTORAGE", "CLEARANCEOFGOODS", "EXPORTCHECKINFORM",
                "EXPORTCHECK", "EXPORTPERMITTHROUGH", "OFFTHEHARBOUR", "ARRIVALATPORT", "IMPORTCHECKINFORM", "IMPORTCHECK", "IMPORTPERMITTHROUGH",
                "AFFIRMRECEIPT", "INFORMSUPPFOOD", "RECEIVESUPPFOOD", "HBLMATCHANORDER", "SENDBILL", "SENDAORN", "ACCOMPLISHCOPEWITH", "ACCOMPLISHRECEIVABLE",
                "ACCOMPLISHMAKEINVOICE", "GATHERINGACCOMPLISH", "PAYMENTAPPLYFOR", "PAYMENTACCOMPLISH",
                "HBLRELEASECARGO", "MBLRELEASECARGO", "HBLDETENTIONOFCARGO", "MBLDETENTIONOFCARGO").size());
    }

    @Test
    public void test05() {
        System.out.println(JobOrderStateNodeConstant.getJobOrderStateRuleMapByKey("LOT_BULK"));
    }

    @Test
    public void test06() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1L,"tfxing"));
        personList.add(new Person(2L,"zxyan"));
        personList.add(new Person(3L,"zbing"));
        Map<Long, Person> personMap = personList.stream().collect(Collectors.toMap(Person::getId, Function.identity(), (oldValue, newValue) -> newValue));
        Person person = personMap.get(1L);
        System.out.println(person.getPersonName());

    }

    @Test
    public void testSplitStr() {
        String str = "helloworld";
        String[] split = str.split(";");
        System.out.println(split.length);
    }

    @Test
    public void testReflect() throws NoSuchFieldException {
        Class<Person> clazz = Person.class;
        Field birth = clazz.getDeclaredField("birth");
        Class<?> type = birth.getType();
        System.out.println(type.equals(Date.class));
    }

    @Test
    public void testDiv() {
        System.out.println(1000 / 100);
    }

    @Test
    public void testSub() {
        Integer a = null;
        System.out.println(a - 1);
    }

    @Test
    public void testStream() {
        ArrayList<Integer> list = new ArrayList<>();
        List<Integer> integers = Arrays.asList(1, 2, 3);
        integers.stream().map(item -> {
            list.add(item);
            return item;
        }).collect(Collectors.toList());
        for (Integer integer : list) {
            System.out.print(integer);
        }
    }

    @Test
    public void testCompareDate() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime( date );
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -10 );
        Date orderDate = calendar.getTime();
        System.out.println(date +" "+orderDate);
        System.out.println(date.after(orderDate));
        long difference =  (date.getTime()-orderDate.getTime())/86400000;
        System.out.println(Math.abs(difference));

    }
}
