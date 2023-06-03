package com.tfxing.persondaily.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.tfxing.persondaily.entity.constant.JobOrderStateNodeConstant;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.utils.CommonUtils;
import com.tfxing.persondaily.utils.DateUtils;
import com.tfxing.persondaily.utils.EmailUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    @Test
    public void testSwitch() {
        switch (1) {
            case 1:
                System.out.println("one");
                break;
            default:
                System.out.println("null");
                break;
        }
    }

    @Test
    public void test07() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2023-05-12");

        Long dayCount = DateUtils.subDayCount(date);
        String content = "<h1>倒计时</h1> \n" +
                "距离《塞尔达传说王国之泪》发售，还剩【%s】天";
        content = String.format(content,dayCount);
        System.out.println(content);
    }

    @Test
    public void testSplit() {
        String acctcode = "1001.01.01";
//        int mid = acctcode.lastIndexOf(".");
//        String acctCodePre = acctcode.substring(0,mid);
//        String acctCodeEnd = acctcode.substring(mid+1);
//        System.out.println(acctCodePre);
//        System.out.println(acctCodeEnd);

        String[] acctCodeSplit = acctcode.split("\\.");
        System.out.println(acctCodeSplit);
        System.out.println(acctCodeSplit[2]);
    }

    @Test
    public void testSubHoursCount() {
        Integer integer = DateUtils.subHoursCount();
        System.out.println(integer);
    }

    @Test
    public <T> void testLambda() {
        /**
         * 消费型：泛型参数为传入参数
         * 没有返回值
         */
        Consumer<T> consumer = item -> {};

        Consumer<List<String>> consumer1 = item -> {
            for (String str : item) {
                System.out.println(str);
            }
        };
        consumer1.accept(Arrays.asList("one","two","three"));


        /**
         * 断定型：泛型参数为传入参数，返回值为boolean
         */
        Predicate<String> predicate = item -> {
            return item.length() > 10;
        };
        System.out.println(predicate.test("hello world"));

        /**
         * 供给型：泛型参数为返回值
         * 没有传入参数
         */
        Supplier<String> supplier = () -> {
            return "hello world";
        };
        System.out.println(supplier);


        /**
         * 函数型：泛型参数为传入参数，返回值为泛型参数
         */
        Function<String,Boolean> function = item -> {
            return item.length() > 10;
        };
        System.out.println(function.apply("hello world"));
    }

    @Test
    public void test08() {
        Supplier<List<String>> supplier = () -> {
            return Arrays.asList("one","two","three");
        };
        List<String> collection = CommonUtils.getCollection(supplier);
        System.out.println(collection.toString());
    }

    @Test
    public void testSort() {
        List<String> list = Arrays.asList("1000", "1001", "1101", "1006", "1003", "1002");
        Arrays.sort(list.toArray());
        System.out.println(list.toString());
    }

    @Test
    public void testSendMail() throws Exception {
        EmailUtil.sendMail("测试","hello world5","2867253802@qq.com");
    }

    @Test
    public void testInt() {
        Integer glyp = Integer.valueOf(String.format("%s%s",  2022, String.format("%0" + 2 + "d", 2)));
        System.out.println(glyp);
    }

    @Test
    public void testDateFormat() {
        SimpleGPT simpleGPT = new SimpleGPT();

        Map<String,String> baseMap = createBaseMap();
        simpleGPT.setBaseMap(baseMap);

        simpleGPT.listen("你是谁");
        System.out.println(simpleGPT.reply());
    }

    /**
     * 构建资料库
     * @return
     */
    private Map<String, String> createBaseMap() {
        HashMap<String, String> baseMap = new HashMap<>();
        baseMap.put("你","我");
        baseMap.put("是","是");
        baseMap.put("谁","simpleGPT");
        baseMap.put("?","。");
        baseMap.put("现在","现在");

        return baseMap;
    }

    @Test
    public void testStr() {
        String stringCellValue = "100023425253212312.00";
        String[] split = stringCellValue.split("\\.");
        String preValue = split[0];
        char[] chars = preValue.toCharArray();

        String str = "";
        for (int i = chars.length - 1,j=1; i >= 0; i--,j++) {
            str += chars[i];
            if(j % 3 == 0 && i != 0) {
                str += ",";
            }
        }
        str = reverse(str);
        str = str+"."+split[1];
        System.out.println(str);
    }

    private String reverse(String str) {
        return str == null ? null : (new StringBuilder(str)).reverse().toString();
    }

    @Test
    public void testSubYearCount() {
        System.out.println(getSubYearCount("2001-01-01"));
    }

    private Integer getSubYearCount(String datefm) {
        String yearStr = datefm.substring(0, 4);
        Integer year = Integer.parseInt(yearStr);

        Integer subYearCount = DateUtil.year(new Date()) - year;
        return subYearCount;
    }

    @Test
    public void test09() {
        System.out.println(splitFormula("=<1101>L-<1003>L"));

    }

    private Map<String,String> splitFormula(String formula) {
        if (StringUtils.isEmpty(formula)) return null;
        //去空格
        String formulaTrim = formula.replaceAll("\\s*", "").substring(1);
        //得到公式集合,LinkedHashMap 保证顺序
        Map<String,String> map = new HashMap<>();

        List<String> textList = new ArrayList<>();
        List<String> symbolList = new ArrayList<>();

        String text = "";
        for (char ch : formulaTrim.toCharArray()) {

            String symbol = String.valueOf(ch);
            if (Arrays.asList("+","-").contains(symbol)) {
                textList.add(text);
                symbolList.add(symbol);
                text = "";
            } else {
                text+=symbol;
            }
        }
        textList.add(text);

        if(symbolList.size()+1 != textList.size()) {
            throw new RuntimeException("");
        }

        if(symbolList.size() <= 0) {
            map.put(text,null);
            return map;
        }

        for (int i = 0; i < symbolList.size(); i++) {
            map.put(textList.get(i+1),symbolList.get(i));
        }

        return map;
    }

    @Test
    public void testDate() {
        int year = 2023;
        int month = 2;

        String dateStr = "202302"; // 指定年月
        LocalDate date = LocalDate.parse(dateStr + "01", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate dateEnd = date.with(TemporalAdjusters.lastDayOfMonth()); // 指定年月的最后一天
        String format = dateEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


    }

    public static String getLastDayofMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        return sdf.format(cal.getTime());
    }

    @Test
    public void testDateStr() {
        String str = "2021-04-30";

        System.out.println(DateUtil.format(DateUtils.str2Date(str,"yyyy-MM-dd"), "yyyy-MM-dd"));
    }
}
