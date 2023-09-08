package com.tfxing.persondaily;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.healthmarketscience.jackcess.*;
import com.linuxense.javadbf.DBFWriter;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.PersonFather;
import com.tfxing.persondaily.entity.po.PersonSon;
import com.tfxing.persondaily.entity.po.User;
import com.tfxing.persondaily.entity.rbTree.RBNode;
import com.tfxing.persondaily.entity.rbTree.RBTree;
import com.tfxing.persondaily.utils.*;
import lombok.Data;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
class PersonDailyApplicationTests {

    @Test
    void contextLoads() {
        
    }
    
    @Test
    void testGeneric() throws IllegalAccessException {
        testGeneric01(new User(1L,"tfxing"));
    }

    /**
     * 泛型方法
     * @param t
     * @param <T>
     */
    public <T> void testGeneric01(T t) throws IllegalAccessException {
        Field[] allFields = t.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object vlaue = field.get(t);
            String desc = String.format("fieldName:%s -- value:%s",fieldName,vlaue);
            System.out.println(desc);
        }
    }
    
    @Test
    void testGeneric02() {
        /*List<String> strList = new ArrayList<>();
        strList.add("one");
        strList.add("two");*/
//        strList.add(1);
        
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add("one");
        System.out.println(list);
        
        List<? extends Person> plist = new ArrayList<>();
       testGeneric02(plist);
    }
    
    private void testGeneric02(List<? extends Person> list) {
        System.out.println(list);
    }
    
    @Test
    void testReflect() throws Exception {
        Class pClass = Person.class;
        Class aClass = Class.forName("com.tfxing.persondaily.entity.po.Person");

        Person person = new Person();
        Class clazz = person.getClass();
        //获取所有的字段
        Field[] allFields = clazz.getDeclaredFields();
        //获取指定的字段
        Field field = clazz.getDeclaredField("personName");
        //无视字段的访问权限
        field.setAccessible(true);
        //获取字段的名称
        String fieldName = field.getName();
        //获取字段的值，参数为获取值的指定对象
        Object value = field.get(person);
        //获取无参构造方法并创建一个实例
        Object obj = clazz.getConstructor().newInstance();
        //获取所有的方法
        Method[] allMethods = clazz.getDeclaredMethods();
        //获取指定的方法
        Method method = clazz.getDeclaredMethod("getPersonName");
        //执行方法，参数为使用方法的指定对象，该方法的参数
        method.invoke(person,"param");
        //获取所有的注解
        Annotation[] annotations = clazz.getAnnotations();
        //获取指定的注解
        Annotation annotation = clazz.getAnnotation(Data.class);

    }
    
    
    @Test
    void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("one","one");
        map.put("one","two");
        System.out.println(map.get("one"));
        System.out.println(null == map.get("two"));
    }

    Integer size = 100;
    
    @Test
    void testThread() {
        
        new Thread(()->{
            sub(size);
        },"thread1").start();

        new Thread(()->{
            sub(size);
        },"thread1").start();
        
    }

    public void sub(int size) {
        while (size > 0) {
            size--;
            System.out.println(size);
        }
    }
    
    @Data
    class TestGenericClass01{
        int a;
        int b;
    }

    @Data
    class TestGenericClass02{
        int a;
        int c;
    }
    
    @Test
    void testGeneric03() {
        TestGenericClass01 testGenericClass01 = new TestGenericClass01();
        testGenericClass01.setA(1);
        testGenericClass01.setB(2);
        TestGenericClass02 testGenericClass02 = new TestGenericClass02();
        testGenericClass02.setA(3);
        testGenericClass02.setC(4);
        System.out.println(testGeneric0301(testGenericClass01, testGenericClass02));

    }
    
    private <T,E> E testGeneric0301(T t,E e) {
        BeanUtils.copyProperties(t,e);
        return e;
    }
    
    @Test
    void testFor() {
        int i;
        for(i=0;i<10;i++);
        System.out.println(i);
    }
    
    @Test
    void testRBTreeDel() {
        RBNode<Integer> root = new RBNode<>(211,true);
        RBNode<Integer> rootL = new RBNode<>(195,false);
        root.setLeft(rootL);
        rootL.setParent(root);
        RBNode<Integer> rootLL = new RBNode<>(159, true);
        rootL.setLeft(rootLL);
        rootLL.setParent(rootL);
        RBNode<Integer> rootLR = new RBNode<>(200, true);
        rootL.setRight(rootLR);
        rootLR.setParent(rootL);
        RBNode<Integer> rootLLL = new RBNode<>(120, false);
        rootLL.setLeft(rootLLL);
        rootLLL.setParent(rootLL);
        RBNode<Integer> rootLLR = new RBNode<>(192, false);
        rootLL.setRight(rootLLR);
        rootLLR.setParent(rootLL);
        RBNode<Integer> rootR = new RBNode<>(324, false);
        root.setRight(rootR);
        rootR.setParent(root);
        RBNode<Integer> rootRR = new RBNode<>(728, true);
        rootR.setRight(rootRR);
        rootRR.setParent(rootR);
        RBNode<Integer> rootRL = new RBNode<>(245, true);
        rootR.setLeft(rootRL);
        rootRL.setParent(rootR);
        RBNode<Integer> rootRRR = new RBNode<>(985, false);
        rootRR.setRight(rootRRR);
        rootRRR.setParent(rootRR);
        RBNode<Integer> rootRRL = new RBNode<>(643, false);
        rootRRR.setLeft(rootRRL);
        rootRRL.setParent(rootRRR);

        RBTree<Integer> tree = new RBTree<>();
        tree.setRoot(root);
        
        tree.deleteNode(root);
    }
    
    @Test
    void testReflect03() throws Exception {
        /*System.out.println("hello world");
        
        Class<System> clazz = System.class;
        Field out = clazz.getDeclaredField("out");
        out.setAccessible(true);
        Object outObj = out.get(null);
        Class outClass = outObj.getClass();
        Method println = outClass.getDeclaredMethod("println");
        println.invoke(null,"hello world");*/

        /*User user = new User();
        user.setUserName("tfxing");
        Person person = new Person();
        person.setPersonName("personNameTfxing");

        Class<? extends Person> personClass = person.getClass();
        Field userField = personClass.getDeclaredField("user");
        userField.setAccessible(true);
        Object obj = userField.get(null);
        Class<?> userClass = obj.getClass();
        Method getUserName = userClass.getDeclaredMethod("getUserName");
        getUserName.setAccessible(true);
        Object invoke = getUserName.invoke(user);
        System.out.println(invoke.toString());

        Method staticMethod = userClass.getDeclaredMethod("staticMethod");
        staticMethod.invoke(null);*/

        Class<?> clazz = Class.forName("java.io.PrintStream");
        Method method = clazz.getMethod("println", String.class);
        Object out = System.out;
        method.invoke(out, "Hello World!");
    }

    @Test
    public void testSwitch() {
        System.out.println(parseDorcCn("D"));
    }

    private String parseDorcCn(String doRc) {
        String dorcCn = "";
        switch (doRc) {
            case "D": dorcCn = "借"; break;
            case "C": dorcCn = "贷"; break;
        }

        return dorcCn;
    }

    @Test
    public void testStrSplit() {
        String message = "";
        String[] split = message.split(":");
        if(split.length < 2) {
            return;
        }
        System.out.println(split[1]);
    }

    @Test
    public void testDuplicate() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("一班", "小明"));
        list.add(new Person("二班", "小芳"));
        list.add(new Person("一班", "小华"));

        Map<String, String> map = list.stream().collect(Collectors.toMap(Person::getClassName, Person::getPersonName));
        System.out.println(map);
    }

    @Test
    public void testDateUtil() {
        /*System.out.println(DateUtils.getMonthFirstDay(new Date()));
        System.out.println(DateUtils.getMonthLastDay(new Date()));*/
        List<Date> dateList = Arrays.asList(new Date(),new Date());

        Set<Date> set = new HashSet<>();

        for (Date date : dateList) {
            Date firstDate = DateUtils.getMonthFirstDay(date);
            set.add(firstDate);
        }

        for (Date date : set) {
            System.out.println(date);
        }
    }

    @Test
    public void testDateComplier() {
        Date date1 = new Date();  // 假设为当前日期和时间
        Date date2 = new Date(System.currentTimeMillis() + 86400000);  // 假设为明天的日期和时间

        System.out.println(date1.after(date2));
    }

    @Test
    public void testSj() {
        StringJoiner sj = new StringJoiner(",");
            sj.add("hasGrossProfit");
            sj.add("-");
            sj.add("hasPerfBase");
            sj.add("-");
            sj.add("hasBadDeb");
            sj.add("-");
            sj.add("hasExchange");
            sj.add("-");
            sj.add("hasReimburse");
            sj.add("-");

        sj.add("hasAdjustment");
        sj.add("-");

        String sjStr = sj.toString();
        sjStr = sjStr.substring(0,sjStr.length()-2);
        System.out.println(sjStr);

        String[] split = sjStr.split(",");

        for (String s : split) {
            System.out.print(s+"  ");
        }
    }

    @Test
    public void testColl() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(null);

        System.out.println(list);
    }

    @Test
    public void testGenNum() {
        String serialNumber = generateUniqueSerialNumber();
        System.out.println(serialNumber + "  " + serialNumber.length());
    }

    private static final String PREFIX = "BR";
    private static final int SERIAL_NUMBER_LENGTH = 10;

    public static String generateUniqueSerialNumber() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = generateRandomNumber();
        String uniqueSerialNumber = PREFIX + timestamp + random;

        // 如果流水号超过指定长度，可以根据需求进行截取或其他处理
        if (uniqueSerialNumber.length() > PREFIX.length() + SERIAL_NUMBER_LENGTH) {
            uniqueSerialNumber = uniqueSerialNumber.substring(0, PREFIX.length() + SERIAL_NUMBER_LENGTH);
        }

        return uniqueSerialNumber;
    }

    private static String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000000);  // 随机生成一个6位数
        return String.format("%06d", randomNumber);  // 格式化为6位数字
    }

    @Test
    public void testJsonObject() {
        String jsonStr = "{\n" +
                "\t\"code\": \"SUCCESS\",\n" +
                "\t\"data\": \"{\n" +
                "\t\t\"vchId\": \"34325\"\n" +
                "\t}\"\n" +
                "}";

    }

    public static void main(String[] args) throws IOException {

//这里同样支持mdb和accdb

        Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File("d:\\new.mdb"));

        Table newTable;

        try {

//刚才是创建文件，这里是在文件里创建表，字段名，字段类型

            newTable = new TableBuilder("Archives")

                    .addColumn(new ColumnBuilder("档案号")

                            .setSQLType(Types.VARCHAR))

                    .addColumn(new ColumnBuilder("编制单位")

                            .setSQLType(Types.VARCHAR))

                    .addColumn(new ColumnBuilder("案卷正题名")

                            .setSQLType(Types.VARCHAR))

                    .addColumn(new ColumnBuilder("案卷题目长度")

                            .setSQLType(Types.INTEGER))

                    .addColumn(new ColumnBuilder("档案盒规格")

                            .setSQLType(Types.VARCHAR))

                    .addColumn(new ColumnBuilder("编制单位长度")

                            .setSQLType(Types.INTEGER))

                    .toTable(db);

//插入一条数据测试

            newTable.addRow("12", "foo","212",44,"323",56);

        } catch (SQLException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

    }

    @Test

    public void operator() throws Exception {

        File mdbFile = new File("C:\\Users\\Administrator\\Desktop\\personal\\test.mdb");

        if (mdbFile.exists()) {

            Database dbin = DatabaseBuilder.open(mdbFile);

            Table table = dbin.getTable("Archives");

            table.addRow("档案号 新增测试", "编制单位新增测试", "案卷正题目新增测试",55, "档案盒规格测试", 5);

        }

    }

    @Test
    public void testWriteDbf() {
        File mdbFile = new File("C:\\Users\\Administrator\\Desktop\\personal\\test.dbf");

        List<String> columnNames = Arrays.asList("one","two","three");

        try (DBFWriter dbfWriter = new DBFWriter(new FileOutputStream(mdbFile), StandardCharsets.UTF_8)) {
            String[] rowData = new String[]{"one","two","three"};
            dbfWriter.addRecord(rowData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testStr() {
        String value = "5401";
        System.out.println(String.valueOf(value).replace(".",""));
    }

    @Test
    public void testArrayUtils() {
        List<Long> arr2 = Arrays.asList(1L,2L,3L);
        List<Long> arr1 = Arrays.asList(1L,4L,3L,5L);

        List<Long> result = new ArrayList<>(arr1); // 复制 arr1 到新的列表

        if (arr2 != null && !arr2.isEmpty()) {
            result.removeAll(arr2); // 移除 arr2 中的元素
        }
        System.out.println(result);
    }

    @Test
    public void testSplit() {
        System.out.println("借-D".split("-")[1]);
    }

    @Test
    public void testRelation() {
        Person link = new Person(1111L, "link");

        Person person = changePerson(link);
//        link = person;

        System.out.println(link);

    }

    private Person changePerson (Person link) {
        Person hello = new Person(1111L, "hello");

        link = hello;

        return link;
    }

    @Test
    public void testPredict() {
//        System.out.println(Long.valueOf(168411908).equals(168903908));
        String acctCode = "=<1001:1009>A@{%:1}";
        acctCode = acctCode.substring(acctCode.indexOf("<")+1,acctCode.indexOf(">"));
        if(acctCode.contains(":")) {
            String[] acctCodeArr = acctCode.split(":");
            for (String acctCodeItem : acctCodeArr) {
                System.out.println(acctCodeItem);
            }

        }
        System.out.println(acctCode);
    }

    /**
     * 4组
     * 万（筒、条）：1 ~ 9 每个数字4张，共 9 * 4 = 36 张
     * 花牌：东、南、西、北、中、发、白 每个1张，共 7 * 4 = 28 张
     * 共计：36 + 7 = 43 张（每个花色9张 * 3种 + 花牌7张）
     *
     * 14 13 13 13
     */
    @Test
    public void testMahjong() {
        String[] tong = {""};
//        String[] cardArr =

    }

    @Test
    public void testMd5() {
//        String salt = SecureUtil.md5(IdUtil.fastSimpleUUID());
        String salt = "16ec7e8dd518ddb7e3fdb7c26348577c";
        System.out.println(salt);
        String passwordEncode = new Md5Hash("Link1234" + salt).toHex();
        System.out.println(passwordEncode);

    }

    @Test
    public void testSplit1() {
        String str = "12,xx,,fad";

        String[] split = str.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void testMath() {
        List<String> strings = Arrays.asList("目的港文件费", "你好", "再煎");

    }

    @Test
    public void readMarkdown() {
        // 指定Markdown文件的路径
        String filePath = "src/main/resources/EnglishStudy.md";

        Arrays.asList("adj","n","vt","v","vi");


        try {
            // 创建一个BufferedReader来读取文件
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            // 逐行读取文件内容并打印到控制台
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 关闭文件流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReplace() {
        String str = "**xfsda*sdf*";
    }

    @Test
    public void testEng() {

    }

    @Data
    class WordItem {
        private String wordCn;

        private String wordEng;

        private List<String> exampleSentenceList;
    }

    private List<Integer> numbers;
    private Random random;
    private Map<String,WordItem> wordMap;
    private List<String> keyList;

    private Boolean isEnd = false;

    @Test
    public void testWordExam() {
        System.out.println("init....");
        numbers = new ArrayList<>();
        random = new Random();
        wordMap = new HashMap<>();
        keyList = new ArrayList<>();

        List<String> wordCnPrefixList = Arrays.asList("adj", "n", "vt", "v", "vi");

        // 指定Markdown文件的路径
        String filePath = "src/main/resources/EnglishStudy.md";

        try {
            // 创建一个BufferedReader来读取文件
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            WordItem wordItem = new WordItem();
            StringJoiner wordCn = new StringJoiner("\n");
            List<String> exampleSentenceList = new ArrayList<>();

            String line;
            // 逐行读取文件内容并打印到控制台
            while ((line = reader.readLine()) != null) {
                if(skip(line)) {
                    continue;
                }

                if(line.startsWith("**") && isEnd) {
                    String wordCnStr = wordCn.toString();

                    wordItem = new WordItem();
                    String wordEng = line.replaceAll("\\*","");
                    wordItem.setWordEng(wordEng);
                    wordItem.setWordCn(wordCn.toString());
                    wordItem.setExampleSentenceList(exampleSentenceList);

                    wordMap.put(wordCnStr, wordItem);
                    keyList.add(wordCnStr);

                    wordCn = new StringJoiner("\n");
                    exampleSentenceList = new ArrayList<>();
                    isEnd = false;

                    continue;
                }

                if (line.startsWith("**") && !isEnd) {
                    String wordEng = line.replaceAll("\\*","");
                    wordItem.setWordEng(wordEng);

                    isEnd = true;
                }

                if(containsWordPrefix(line,wordCnPrefixList)) {
                    wordCn.add(line);
                }

                if(isNumPrefix(line)) {
                    exampleSentenceList.add(line);
                }
            }

            // 关闭文件流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 初始化数字列表，每个数字出现2次
            for (int i = 0; i < keyList.size(); i++) {
                numbers.add(i);
                numbers.add(i);
            }

            System.out.println(wordMap);
        }
    }

    /**
     * 是否以数字.开头
     * @param line
     * @return
     */
    private boolean isNumPrefix(String line) {
        return line.matches("^\\d+\\..*");
    }

    /**
     * 以预设开头匹配
     * @param line
     * @param wordCnPrefixList
     * @return
     */
    private boolean containsWordPrefix(String line, List<String> wordCnPrefixList) {
        if(CollectionUtil.isEmpty(wordCnPrefixList)) {
            return false;
        }

        for (String wordCnPrefix : wordCnPrefixList) {
            if(line.startsWith(wordCnPrefix)) {
                return true;
            }
        }

        return false;
    }

    private boolean skip(String line) {
        return StringUtils.isBlank(line) ||
                line.startsWith("#") ||
                line.startsWith("_") ||
                line.startsWith("例句");
    }

    @Test
    public void testStartWith() {
//        System.out.println("# 英语资料".startsWith("#"));
        String text = "43. This species is extremely rare.";

        // 使用正则表达式匹配以数字字符串开头的文本
        if (text.matches("^\\d+\\..*")) {
            System.out.println("字符串以数字字符串开头。");
        } else {
            System.out.println("字符串不以数字字符串开头。");
        }
    }

}
