package com.tfxing.persondaily;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.healthmarketscience.jackcess.*;
import com.linuxense.javadbf.DBFWriter;
import com.mybatisflex.core.util.DateUtil;
import com.tfxing.persondaily.entity.enums.StatusCodeEnum;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.User;
import com.tfxing.persondaily.entity.rbTree.RBNode;
import com.tfxing.persondaily.entity.rbTree.RBTree;
import com.tfxing.persondaily.utils.*;
import lombok.Data;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
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

    /**
     * 横向的填充
     *
     * @since 2.1.1
     */
    /*@Test
    public void horizontalFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName =
                "src/main/resources/" + "demo" + File.separator + "fill" + File.separator + "horizontal.xlsx";

        String fileName = "src/main/resources/" + "horizontalFill" + System.currentTimeMillis() + ".xlsx";
        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            excelWriter.fill(data(), fillConfig, writeSheet);

            Map<String, Object> map = new HashMap<>();
            map.put("date", "2019年10月9日13:28:28");
            excelWriter.fill(map, writeSheet);
        }
    }

    private Object data() {
        return null;
    }*/
    @Test
    public void testDynamicImportExcel() {
        String excelFilePath = "src/main/resources/response1.xls"; // 替换为实际的 Excel 文件路径

        // 使用 EasyExcel 读取 Excel 文件，通过 AnalysisEventListener 处理数据
        EasyExcel.read(new File(excelFilePath), new AnalysisEventListener<List<String>>() {
            List<String> headers = new ArrayList<>(); // 用于存储 Excel 表头信息
            List<List<String>> data = new ArrayList<>(); // 用于存储数据行

            @Override
            public void invoke(List<String> rowData, AnalysisContext context) {
                if (context.getCurrentRowNum() == 0) {
                    // 处理表头行
                    headers.addAll(rowData);
                } else {
                    // 处理数据行
                    data.add(rowData);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 表头和数据读取完成后，可以根据 headers 动态创建数据对象

                // 示例：动态创建一个数据对象并将数据映射到对象列表中
                List<DynamicDataObject> dynamicDataList = new ArrayList<>();
                for (List<String> rowData : data) {
                    DynamicDataObject dynamicData = new DynamicDataObject();
                    for (int i = 0; i < headers.size(); i++) {
                        String columnName = headers.get(i);
                        String cellValue = rowData.get(i);

                        // 根据 columnName 动态设置对象的属性
                        // 这里可以使用反射或其他动态属性设置方法

                        // 示例：将数据映射到对象的属性
                        if ("Column1".equals(columnName)) {
                            dynamicData.setColumn1(cellValue);
                        } else if ("Column2".equals(columnName)) {
                            dynamicData.setColumn2(cellValue);
                        }
                        // 添加其他列的映射
                    }
                    dynamicDataList.add(dynamicData);
                }

                // 现在，dynamicDataList 包含了从 Excel 表中读取的数据，可以进行后续处理
                for (DynamicDataObject dynamicData : dynamicDataList) {
                    System.out.println(dynamicData);
                }
            }
        }).sheet().doRead();
    }

    // 动态数据对象，根据 Excel 表动态创建属性
    @Data
    static class DynamicDataObject {
        private String column1;
        private String column2;

        // 添加其他动态属性

        // Getter 和 Setter 方法
    }


    @Test
    public void testMath1() {
//        int i = 1000 * 60 * 60 * 12 * 2 / 1000;
        int i = 81066 / 60 / 60;
        System.out.println(i);
    }

    @Test
    public void testEnum() {
        System.out.println(StatusCodeEnum.SUCCESS.name());
    }

    @Test
    public void freeTime() {

        System.out.println("hello world");
        // 测试
    }

    @Test
    public void testQr() {

    }

    @Test
    public void dailyCode() {
        String[] strArr = new String[]{"eat","tea","tan","ate","nat","bat"};

        List<Map<String,Integer>> mapList = new ArrayList<>();
        Map<Map<String,Integer>,List<String>> strListMap = new HashMap<>();
        for (String str : strArr) {
            Map<String,Integer> map = new HashMap<>();
            String[] charArr = str.split("");
            for (String ch : charArr) {
                Integer count = map.get(ch);
                if(null != count) {
                    count += 1;
                    map.put(ch, count);
                } else {
                    map.put(ch, 1);
                }
            }
            List<String> strings = strListMap.get(map);
            if(CollectionUtil.isEmpty(strings)) {
                strListMap.put(map,Arrays.asList(str));
            } else {

                for (String string : strings) {

                }
                strListMap.put(map,strings);
            }
            mapList.add(map);
        }

        Map<Map<String, Integer>, List<Map<String, Integer>>> collect = mapList.stream().collect(Collectors.groupingBy(item -> item));
        for (Map.Entry<Map<String, Integer>, List<Map<String, Integer>>> mapListEntry : collect.entrySet()) {
            Map<String, Integer> key = mapListEntry.getKey();
            List<String> strings = strListMap.get(key);
            System.out.println(strings);
        }

    }


    @Test
    public void testGenerateNum() {
        int length = 4;
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", ""); // 移除 UUID 中的短横线

        // 截取指定长度的子字符串
        if (uuidStr.length() >= length) {
            uuidStr = uuidStr.substring(0, length);
        } else {
            // 如果生成的 UUID 长度不足指定长度，可以根据需要进行填充
            while (uuidStr.length() < length) {
                uuidStr += "0"; // 填充 0
            }
        }
        System.out.println(uuidStr);
    }


    public class UniqueSequenceGenerator {
        private final long epoch = new Date().getTime(); // 自定义一个起始时间，这里为2021-01-01 00:00:00的时间戳
        private final long workerIdBits = 5L;
        private final long datacenterIdBits = 5L;
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
        private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        private final long sequenceBits = 12L;
        private final long workerIdShift = sequenceBits;
        private final long datacenterIdShift = sequenceBits + workerIdBits;
        private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);

        private long workerId;
        private long datacenterId;
        private long lastTimestamp = -1L;
        private long sequence = 0L;

        public UniqueSequenceGenerator(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException("Worker ID can't be greater than " + maxWorkerId + " or less than 0");
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException("Datacenter ID can't be greater than " + maxDatacenterId + " or less than 0");
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        public synchronized long nextUniqueId() {
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
            }

            if (timestamp == lastTimestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return ((timestamp - epoch) << timestampLeftShift) |
                    (datacenterId << datacenterIdShift) |
                    (workerId << workerIdShift) |
                    sequence;
        }

        private long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }


    }
    @Test
    public void testGenerateUniqueNum() {
        UniqueSequenceGenerator generator = new UniqueSequenceGenerator(1, 1);

        for (int i = 0; i < 10; i++) {
            long uniqueId = generator.nextUniqueId();
            System.out.println("Generated Unique ID: " + uniqueId);
        }
    }

    @Test
    public void testChatGpt() {
        String str = "Hello, World!";
        String pattern = ", wrlD!";
        System.out.println(str.toLowerCase().contains(pattern.toLowerCase()));


    }
    public boolean isMatch(String str, String pattern) {
        // 将模式中的 % 替换成正则表达式 .*
        // 并在前后加上 ^ 和 $，表示必须从头到尾完全匹配
        pattern = "^" + pattern.replace("%", ".*") + "$";
        // 将 pattern 编译成正则表达式对象
        Pattern regex = Pattern.compile(pattern);
        // 对输入的字符串进行匹配
        Matcher matcher = regex.matcher(str);
        // 返回匹配结果
        return matcher.find();
    }

    @Test
    public void testArrays() {
        Map<String,String> map = new HashMap<>();
        String s = map.get(null);
        System.out.println(s);
//        System.out.println(Arrays.asList(null));
    }

    @Test
    public void testSubStr() {
        String contactMobileNo = "18370240624";
        System.out.println(contactMobileNo.substring(0, 3) + "****" + contactMobileNo.substring(7));
    }

    /**
     * 测试小孔成像
     */
    @Test
    public void testPinholeImaging() {
        /**
         * 构建一个二维坐标系
         * 固定镜头离原点(0,0)的距离为5
         */

        SinglePoint pointA = new SinglePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(20));
        SinglePoint pointB = new SinglePoint(BigDecimal.valueOf(20), BigDecimal.valueOf(20));
        SinglePoint pointC = new SinglePoint(BigDecimal.valueOf(0), BigDecimal.valueOf(20));


        System.out.println(getPinholeImaging(pointA));
        System.out.println(getPinholeImaging(pointB));
        System.out.println(getPinholeImaging(pointC));
    }

    class SinglePoint {
        BigDecimal x;
        BigDecimal y;

        public SinglePoint(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "("+x+","+y+")";
        }
    }

    public SinglePoint getPinholeImaging(SinglePoint point) {
        // 如果x坐标小于等于0，则消失
        if(point.x.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        if(BigDecimal.ZERO.equals(point.y)) {
            return new SinglePoint(BigDecimal.valueOf(10), point.y);
        }

        // 角度数
        BigDecimal divide = point.y.divide(point.x, 2, RoundingMode.HALF_UP);
        BigDecimal y = divide.multiply(BigDecimal.valueOf(10));
        SinglePoint singlePoint = new SinglePoint(BigDecimal.valueOf(10), y);

        return singlePoint;
    }

    @Test
    public void test3DImg() {
        ThreeDPoint a = new ThreeDPoint(0, 0, 10);
        ThreeDPoint a1 = new ThreeDPoint(1, 0, 10);
        ThreeDPoint b = new ThreeDPoint(0, 1, 10);
        ThreeDPoint b1 = new ThreeDPoint(1, 1, 10);

        Quadrilateral quadrilateral = new Quadrilateral(a, a1, b, b1);
        System.out.println(quadrilateral);

        Quadrilateral quadrilateralPinhole = getQuadrilateralPinhole(quadrilateral);
        System.out.println(quadrilateralPinhole);

    }

    private Quadrilateral getQuadrilateralPinhole(Quadrilateral quadrilateral) {
        BigDecimal quadrilateralX = getQuadrilateralX(quadrilateral, BigDecimal.valueOf(5));
        BigDecimal quadrilateralY = getQuadrilateralY(quadrilateral, BigDecimal.valueOf(5));

        ThreeDPoint point = new ThreeDPoint(0, 0, 5);
        Quadrilateral quadrilateralPinhole = new Quadrilateral(point, quadrilateralX, quadrilateralY);

        return quadrilateralPinhole;
    }

    private BigDecimal getQuadrilateralY(Quadrilateral quadrilateral, BigDecimal local) {
        ThreeDPoint a = quadrilateral.a;
        ThreeDPoint a1 = quadrilateral.a1;
        BigDecimal xP2pLength = a1.x.subtract(a.x);
        BigDecimal yP2pLength = a.z;

        BigDecimal quadrilateralY = local.multiply(xP2pLength.divide(yP2pLength));
        return quadrilateralY;
    }

    private BigDecimal getQuadrilateralX(Quadrilateral quadrilateral, BigDecimal local) {
        ThreeDPoint a = quadrilateral.a;
        ThreeDPoint a1 = quadrilateral.a1;
        BigDecimal xP2pLength = a1.x.subtract(a.x);
        BigDecimal yP2pLength = a.z;

        BigDecimal quadrilateralX = local.multiply(xP2pLength.divide(yP2pLength));
        return quadrilateralX;
    }

    class Quadrilateral {
        ThreeDPoint a;
        ThreeDPoint a1;
        ThreeDPoint b;
        ThreeDPoint b1;

        public Quadrilateral(ThreeDPoint a, ThreeDPoint a1, ThreeDPoint b, ThreeDPoint b1) {
            this.a = a;
            this.a1 = a1;
            this.b = b;
            this.b1 = b1;
        }

        public Quadrilateral(ThreeDPoint point, BigDecimal quadrilateralX, BigDecimal quadrilateralY) {
            this.a = point;
            this.a1 = null;
        }

        public String toString() {
            return a.toString()+"\n"+a1.toString()+"\n"+b.toString()+"\n"+b1;
        }
    }

    class ThreeDPoint {
        BigDecimal x;
        BigDecimal y;
        BigDecimal z;

        public ThreeDPoint(int x, int y, int z) {
            this.x = BigDecimal.valueOf(x);
            this.y = BigDecimal.valueOf(y);
            this.z = BigDecimal.valueOf(z);
        }

        public String toString() {
            return "("+x+","+y+","+z+")";
        }
    }

    @Test
    public void testSin() {
        System.out.println(Math.round(Math.sin(Math.toRadians(30)) * 100.0) / 100.0);
        System.out.println(Math.round(Math.cos(Math.toRadians(30)) * 100.0) / 100.0);

        System.out.println(Math.round(Math.cos(Math.toRadians(30)) * 100.0) / 100.0);

        double angleA = 30; // 角A的度数
        double sideB = 10; // 边b的长度
        double sideC = 10; // 边c的长度

        // 将角度转换为弧度
        double angleAInRadians = Math.toRadians(angleA);

        // 使用余弦定理计算边a的长度
        double sideA = Math.sqrt(Math.pow(sideB, 2) + Math.pow(sideC, 2) - 2 * sideB * sideC * Math.cos(angleAInRadians));

        System.out.println("边a的长度为：" + sideA);
    }

    @Test
    public void test3DLength() {
        double actualLength = 10; // 实际柱子长度
        double displacement = 5.2; // 平移距离
        double initialLengthOnPhoto = actualLength; // 初始在照片上的长度

        // 计算平移后在照片上的长度
        double lengthOnPhoto = (initialLengthOnPhoto * (actualLength - displacement)) / actualLength;

        System.out.println("柱子平移后在照片上的长度为：" + lengthOnPhoto + "米");
    }

    @Test
    public void testSet() {
        List<Person> one = Arrays.asList(new Person(1L,"link"));
        List<Person> two = Arrays.asList(new Person(1L,"link"), new Person(2L,"zelda"));


        Set<Person> set = new HashSet<>();
        set.addAll(one);
        set.addAll(two);

        for (Person integer : set) {
            System.out.println(integer);
        }
    }

    @Test
    public void testContains() {
        System.out.println(Arrays.asList(1,2).contains(null));
    }

    @Test
    public void testDistance() {
        double distance = (200 - 100) * Math.sin(Math.toRadians(50 / 2)) * 2;

        int ceil = (int) Math.ceil(distance);
        System.out.println(ceil);


        double i = 90d / Double.valueOf(90+ceil);
        System.out.println(i);
        System.out.println(i*90);

        /*double hight = ((y2*10-y1) / (y2*10-y1) + distance) * (y2*10-y1);

        double hightTop = (y2 * 10 + y1) + hight / 2;
        double hightButtom = (y2 * 10 + y1) - hight / 2;*/
    }

    @Test
    public void testFormat() throws ParseException {
        SimpleDateFormat simpleDateFormat = DateUtil.getSimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("1901-01-01");
        System.out.println(date);


    }

    @Test
    public  void testTrim() {
        String unloco = "fa aa,C A";
        String[] split = unloco.split("");

        String str = "";
        for (String s : split) {
            if(" ".equals(s)) {
                continue;
            }

            str += s;
        }

        unloco = str.toUpperCase();

        System.out.println(unloco);
    }

    @Test
    public void testDateFormat() {
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("YYYYMMdd");
        String format = yyyymmdd.format(new Date());
        System.out.println(format);
    }

    @Test
    public void testDateOffset() {
        Date dateParam = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParam);
        calendar.add(Calendar.MONTH, 1);
        Date date = new Date(calendar.getTimeInMillis());
        System.out.println(date);
    }

    @Test
    public void testStrSplit1() {
        System.out.println(trim("JIANGMEN (GD)"));
    }

    private String trim(String unloco) {
        String[] split = unloco.split("");

        boolean flag = false;
        String str = "";
        for (String s : split) {
            if(" ".equals(s)) {
                continue;
            }

            if("(".equals(s)) {
                flag = true;
                continue;
            }

            if(")".equals(s)) {
                flag = false;
                continue;
            }

            if(flag) {
                continue;
            }

            str += s;
        }

        unloco = str.toUpperCase();

        return unloco;
    }

@Test
public void testNextGlyp() {
    System.out.println(getNextGlypByGlyp(202212));
    System.out.println(getNextGlypByGlyp(202312));
    System.out.println(getNextGlypByGlyp(202310));
}
    private Integer getNextGlypByGlyp(Integer glyp) {
        Integer nextGlyp = 0;

        String glypStr = String.valueOf(glyp);
        if(glypStr.endsWith("12")) {
            String gly = glypStr.substring(0, 4);
            int glyInt = Integer.parseInt(gly);
            glyInt += 1;

            nextGlyp = Integer.parseInt(glyInt+"01");
        } else {
            nextGlyp = glyp + 1;
        }

        return nextGlyp;
    }

    @Test
    public void testMd51() {
//        String salt = SecureUtil.md5(IdUtil.fastSimpleUUID());
//        System.out.println(salt);
//
//        String passwordEncode = new Md5Hash("123456" + salt).toHex();
//        System.out.println(passwordEncode);
//
//        System.out.println();

        String hex = new Md5Hash("123456"+"85363b45a679ea274d016cd2a5f1eda9").toHex();

        System.out.println(hex);

        System.out.println("1548a6cb8f7f9ee9b12c71a02d8ba40d".equals(hex));
    }

    private static final AtomicInteger counter = new AtomicInteger(0);
    @Test
    public void testUUID() {

        SimpleDateFormat sdf = new SimpleDateFormat("SSSssSSS");
        String timestamp = sdf.format(new Date());

        int randomNum = (int)(Math.random() * 10000);

        int count = counter.incrementAndGet() % 10000;

        System.out.println(timestamp + String.format("%04d%04d", randomNum, count));
    }

    @Test
    public void testStrContain() {
        String str = "123412";
        System.out.println(str.contains("521"));
    }

    @Test
    public void testDecimal() {
        /*BigDecimal bigDecimal = new BigDecimal("<1002.01>");
        System.out.println(bigDecimal);*/

        System.out.println(isDecimal("-1"));
    }

    public static boolean isDecimal(String str) {
        if (str == null || str.isEmpty()) {
            // 空串或 null 不能被解析为数字
            return false;
        }

        int i = 0;
        if ((str.charAt(0) == '-' && str.length() > 1) || (str.charAt(0) == '+' && str.length() > 1)) {
            // 如果是负数或正数，下标 +1 开始判断
            i = 1;
        }

        boolean hasDecimalPoint = false;
        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '.') {
                if (hasDecimalPoint) {
                    // 不能有多个小数点
                    return false;
                } else {
                    hasDecimalPoint = true;
                }
            } else if (!Character.isDigit(c)) {
                // 非数字字符
                return false;
            }
        }

        return true;
    }

    @Test
    public void testSubString() {
        /*String glypStr = "202301";
        String gly = glypStr.substring(0, 4);
        String glp = glypStr.substring( 4);
        System.out.println(gly);
        System.out.println(glp);*/

        System.out.println(glypFormat(202301,"YYMM"));
    }

    private String glypFormat(Integer glyp, String formatType) {
        if(null == glyp) {
            return "";
        }

        String input = glyp.toString();
        String formattedDate = "";
        String year = input.substring(0, 4);
        String month = input.substring(4);

        switch (formatType) {
            case "YYYY-MM":
                formattedDate = year + "-" + month;
                break;
            case "MM-YYYY":
                formattedDate = month + "-" + year;
                break;
            case "YY-MM":
                formattedDate = year.substring(2) + "-" + month;
                break;
            case "MM-YY":
                formattedDate = month + "-" + year.substring(2);
                break;
            case "YYYYMM":
                formattedDate = year + month;
                break;
            case "MMYYYY":
                formattedDate = month + year;
                break;
            case "YYMM":
                formattedDate = year.substring(2) + month;
                break;
            case "MMYY":
                formattedDate = month + year.substring(2);
                break;
        }

        return formattedDate;
    }

    @Test
    public void testEq() {
        String str = "hello world";
        BigDecimal zero = BigDecimal.ZERO;
        System.out.println(str.equals(zero));
    }

    @Test
    public void testDateFormat1() {
        String format = "YYYMMDD";
        format = format.replaceAll("Y","y");
        format = format.replaceAll("D","d");

        System.out.println(format);
        Date date = DateUtils.str2Date("2023-12-31","yyyy-MM-dd");
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        System.out.println(sdf.format(date));
    }

    @Test
    public void testDateBefore() {
        System.out.println(new Date().before(DateUtils.str2Date("2023-12-31","yyyy-MM-dd")));
    }

    @Test
    public void testSort() {
        List<Integer> list = Arrays.asList(1,4,2,3);
        List<Integer> collect = list.stream().sorted(Comparator.comparing(item -> item, Comparator.reverseOrder())).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void parseStr() {
        GlmGenVchVo vo = new GlmGenVchVo();
        String result = "ERROR: {\"code\": \"NoGlpForArapDate\", \"params\": [\"2024-01-06\", \"54995540008\", \"CNY\", \"500.00\"]}";
        if (result.contains("在位置：")){
            int i = result.indexOf("在位置：");
            result = result.substring(0, i);
        }
        String s = result.replaceAll("ERROR:", "");
        GlmGenVchVo glmGenVchVo = JSONUtil.toBean(s,GlmGenVchVo.class);  //转成对象

        if ("SUCCESS".equals(glmGenVchVo.getCode())){
            vo.setVchid(glmGenVchVo.getVchid());
            vo.setStatus(true);
            System.out.println(vo);
        }


        System.out.println(glmGenVchVo.getCode());

        vo.setParams(glmGenVchVo.getParams());
        System.out.println(vo);
    }
}
