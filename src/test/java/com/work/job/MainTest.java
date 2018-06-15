package com.work.job;

import com.fun.codec.TestRSA;
import com.work.AbstractTest;
import com.work.job.bean.Son;
import com.work.job.thread.SimpleObj;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class MainTest extends AbstractTest {

    public MainTest() {
    }

    public static void main(String[] args) throws Exception {
        MainTest main = new MainTest();
        // main.testAssignableFrom();
        // testCglib();
        // testInjectPrivate();
        // testScript();
        /*List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        System.out.println(Optional.<List>ofNullable(list2).filter(list -> list != null && list.size() >0).map(list -> {return null;}).orElseGet(ArrayList::new));*/
    }

    @Test
    public void test() throws Exception {
        Method method = MainTest.class.getDeclaredMethod("testStringFormatNull");
        if (method != null) {
            String name = method.getDeclaringClass().getName() + "#" + method.getName();
            System.out.println(name);
            //com.work.job.MainTest#testStringFormatNull
            //com.work.job.MainTest.testStringFormatNull
            //com.alipic.agravity.member.client.api.MemberService.getMyPermission()
            Assert.assertTrue("com.work.job.MainTest.testStringFormatNull".equals(name));
            return;
        }
        Assert.fail();
    }

    @Test
    public void testStringFormatNull() throws Exception {
        System.out.println(String.format("==>%s", null));
    }

    @Test
    public void testTryFinally() throws Exception {
        SimpleObj obj = new SimpleObj();
        try {
            System.out.println("normal");
            obj.setLastName("normal");
            throw new RuntimeException("error");
        } catch (Exception ex) {
            System.out.println("exception");
            obj.setLastName("exception");
        } finally {
            System.out.println("finally");
            obj.setLastName("finally");
        }
        System.out.println(obj.getLastName());
    }

    @Test
    public void testHashCode() throws Exception {
        SimpleObj obj = new SimpleObj();
        System.out.println(obj.toString());
    }


    @Test
    public void testBreak() throws Exception {
        for (TestEnum id : TestEnum.values()) {
            switch (id) {
                case instA:
                    System.out.println(id.name());
                    continue;
                default:
                    System.out.println("invalid");
                    continue;
            }
        }
    }

    @Test
    public void testDate2() throws Exception {
        Date dt = new SimpleDateFormat("yyyyMMdd").parse("20170330");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        System.out.println(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
        calendar.set(Calendar.MONTH, calendar.get(calendar.MONTH) - 1);
        System.out.println(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
    }

    public static void testBinary() throws Exception {
        System.out.println(Long.toBinaryString(25027028159L));
        System.out.println(Long.toBinaryString(-742775617L));
    }

    public static void testInjectPrivate() throws Exception {
        IocTest obj = new IocTest();
        BeanUtils.setProperty(obj, "xh", "yuanbao");
        obj.iloveu();
        Field f = IocTest.class.getDeclaredField("xh");
        f.setAccessible(true);
        ReflectionUtils.setField(f, obj, "yuanbao");
        obj.iloveu();
    }

    public static void testCglib() throws Exception {
        FastClass cls = FastClass.create(MainTest.class);
        FastMethod m = cls.getMethod("test", new Class[0]);
        m.invoke(new MainTest(), null);
    }

    public static void testConvert() throws Exception {
        TypeConverter c = new SimpleTypeConverter();
        Integer val = c.convertIfNecessary("0x7fff", Integer.class);
        p(val);
    }

    public void testAssignableFrom() throws Exception {
        //List a = new ArrayList<Object>();
        //System.out.println(a instanceof Serializable);
        System.out.println(Object.class.isAssignableFrom(String.class));// true
        System.out.println(Serializable.class.isAssignableFrom(ArrayList.class));// true
        System.out.println(Map.class.isAssignableFrom(Serializable.class));// false
    }

    public static void testMap2() throws Exception {
        Map<String, Long> map = new HashMap<String, Long>();
        Long c = map.get("key");
        for (int i = 0; i < 100; i++) {
            if (c == null) {
                c = 0L;
                map.put("key", c);
            }
            map.put("key", ++c);
        }
        System.out.println(map);
    }

    public static void testScript() throws Exception {
        /*String a = "aa<script>bb";
        Pattern p = Pattern.compile("[^\\w]+script[^\\w]+");
        Matcher m = p.matcher(a);
        System.out.println(m.find());
        
        String b = "<script>";
        m = p.matcher(b);
        System.out.println(m.find());
        
        String c = "javascript>";
        m = p.matcher(c);
        System.out.println(m.find());*/

        Pattern p = Pattern.compile("#.+#");
        String txt = "#最美中国#fsdfs#a##b#";

        Matcher m = p.matcher(txt);

        while (m.find()) {
            System.out.println(m.group(0));
        }

    }

    public static void testSubList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(list.subList(0, list.size() - 1));
        list = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(list.subList(6, 6));
    }

    public void testSubString() {
        String a = "20150508";
        System.out.println(a.substring(0, 4));
        System.out.println(a.substring(4, 6));
    }

    public void testStringIndex() throws Exception {
        String a = "http://img04.daily.taobaocdn.net/tfscom/TB1aTaJXXXXXXb4XFXXSutbFXXX";
        String b = "http://img01.daily.taobaocdn.net/tfscom/TB1vg9RXXXXXXa4bVXXXXXXXXXX.jpgfsd.fs";

        int i = b.lastIndexOf("/");
        int j = b.indexOf(".", i);
        p(b.substring(j));

        i = a.lastIndexOf("/");
        j = a.indexOf(".", i);

        if (j > -1) {
            p(a.substring(j));
        } else {
            p("<empty>");
        }
    }

    public void testDateFmt() throws Exception {
        DateFormat df = new SimpleDateFormat("M鏈坉鏃�");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, 12);
        System.out.println(df.format(new Date()));
        System.out.println(df.format(cal.getTime()));
        df = new SimpleDateFormat("yyyy-M-d HH:mm");
        System.out.println(df.format(cal.getTime()));
        System.out.println(df.format(new Date()));
    }

    public void testGetAllMethod() throws Exception {
        Son son = new Son();

        //1
        /*Map<String, Object> map = BeanUtils.describe(son);
        System.out.println(new ObjectMapper().writeValueAsString(map));

        //2
        for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(Son.class).getPropertyDescriptors()) {

            // propertyEditor.getReadMethod() exposes the getter
            // btw, this may be null if you have a write-only property
            System.out.println(propertyDescriptor.getReadMethod());
        }

        //3
        for (Method m : getAccessibleMethods(Son.class)) {
            System.out.println(m.getName());
        }*/

    }

    public static Method[] getAccessibleMethods(Class clazz) {
        List<Method> result = new ArrayList<Method>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                int modifiers = method.getModifiers();
                if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                    result.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result.toArray(new Method[result.size()]);
    }

    public void testHexString() throws Exception {
        String a = "12021ffffdaa";
        System.out.println(isHex(a));
    }

    public static boolean isHex(String a) {
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                continue;
            }
            return false;
        }
        return true;
    }

    public void testByte() throws Exception {
        byte[] b = new byte[4];
        b[0] = 100;
        System.out.println(b[0]);
    }

    public void testRef() throws Exception {
        MemberClass m = null;
        List<MemberClass> list = new ArrayList<MemberClass>();
        for (int i = 0; i < 100; i++) {
            m = new MemberClass();
            m.setVal(i);
            list.add(m);
        }
        List<MemberClass> list2 = new ArrayList<MemberClass>();
        int j = 0;
        for (MemberClass mm : list) {
            m = new MemberClass();
            m.setVal(j++);
            list2.add(m);
        }
        for (MemberClass mm : list2) {
            System.out.println(mm.getVal());
        }
    }

    public void testClassRef() {
        MemberClass m = new MemberClass();
        TopClass top = new TopClass();
        top.setMember(m);
        m.setVal(1000);
        System.out.println(top.getMember().getVal());
    }

    public void testCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
        System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(cal.getTime()));
    }

    public void testFilePath() throws Exception {
        System.out.println(System.getProperty("user.dir"));
        File f = new File("");
        System.out.println(f.getAbsolutePath());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().toString());
    }

    public void testMem() throws Exception {
        System.out.println(Runtime.getRuntime().maxMemory() / 1000 / 1000);
        System.out.println(Runtime.getRuntime().totalMemory() / 1000 / 1000);
        System.out.println(Runtime.getRuntime().freeMemory() / 1000 / 1000);
        for (int i = 0; i < 1000000; i++) {

        }
    }

    public void testFileGet() throws Exception {
        URL url = TestRSA.class.getResource("certs/public-rsa-10years.cer");
        File f = new File(url.toURI());
        System.out.println(f.exists());
        System.out.println(url);
        //SecurityService.getInstance().init(publicCAFile, privateCAFile);
    }

    public void testGetMethod() throws Exception {
        Method m = this.getClass().getDeclaredMethod("priM");
        //Method m = this.getClass().getMethod("priM");// exception
        m.setAccessible(true);
        m.invoke(this);
    }

    private void priM() {
        System.out.println("called");
    }

    public void testCast() {
        Integer a = null;
        System.out.println(String.valueOf(a));
    }

    public void wapTinyurl() {
        //String url = "http://m.nuomi.com/hrb/deal/zjvvv33b";
        String url = "http://m.nuomi.com/mianyang/deal/l5kkhcqr";
        Pattern p = Pattern.compile("^http://\\w+\\.nuomi\\.com/\\w+/deal/(\\w+$)");
        Matcher m = p.matcher(url);
        if (m.find()) {
            p(m.group(1));
        }
    }

    public void wapQueryString() {
        String query1 = "tinyurl=erqarrf3&hoff=1&foff=1&s=1&utm_source=nuomiapp&utm_medium=app&utm_campaign=nuomi";
        String query2 = "tinyurl=tmmbyqj5";
        String query3 = "areaId=3000010000&tinyurl=yw74uqbe&from=0&previousUrl=http://m.nuomi.com/touch/deal/view";

        int start = query1.indexOf("tinyurl=");
        int end = query1.indexOf("&", start);
        p(query1.substring(start + 8, end == -1 ? query1.length() : end));

        start = query2.indexOf("tinyurl=");
        end = query2.indexOf("&", start);
        p(query2.substring(start + 8, end == -1 ? query2.length() : end));

        start = query3.indexOf("tinyurl=");
        end = query3.indexOf("&", start);
        p(query3.substring(start + 8, end == -1 ? query3.length() : end));
    }

    @Test
    public void testSplit() {
        /*String a = "xlj.121=hhq";
        String[] arr = a.split("\\s*=\\s*");
        p(arr[0].split("\\.")[1]);
        a = "xlj|hhq|zzz|";
        p(a.split("\\|").length);
        arr = a.split("\\|", -1);
        p(arr[3]);*/
        String a = "1##2#3";
        System.out.println(a.split("#"));
    }

    public void arrCopyTest() {
        String[] a = new String[]{"1", "2", "3", "4", "5"};
        String[] b = Arrays.copyOfRange(a, 5, 5);
        p(StringUtils.join(b, "#"));
    }

    public void asserTest() {
        // 杩愯鏃剁┚寮�濮嬪紑鍏� -ea
        assert false;
        p("hello");
    }

    public void equalsTest() {
        Long a = 1000L;
        Long b = 1000L;
        p(a == b);
        p(a.equals(b));
        Integer c = 1000;
        Integer d = 1000;
        p(c == d);
        p(c.equals(d));
    }

    public void testIpConvert(String ip) {
        String[] ips = ip.split("\\.");
        long ipLong = 0;
        for (int i = 0; i < ips.length; i++) {
            ipLong = ipLong << 8;
            ipLong += Long.valueOf(ips[i]);
        }
        p(ipLong);
    }

    public void testList() {
        List<String> a = new ArrayList<String>();
        a.add("this");
        a.add("is");
        a.add("a");
        a.add("test");
        p(a.size());
        a.clear();
        p(a.size());
    }

    public void testStringFormat() {
        String a = "鎺ュ彛閲戦[%s] 脳 [%s]%%";
        p(String.format(a, 1, 2));
        System.out.println((Float) (2 / 1000f));
        Double num = 10 / 30.00;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        p(String.format("%.2f", (Float) (2 / 1000f)));
        p(String.format("%.2f", num));
        p(nf.format((Float) (2 / 1000f)));
        p(nf.format(num));
    }

    public String underscoreName(String name) {
        StringBuffer result = new StringBuffer();
        if ((name != null) && (name.length() > 0)) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); ++i) {
                String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase())) {
                    result.append("_");
                    result.append(s.toLowerCase());
                } else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }

    public void testDate() throws Exception {
        /*Date d = new Date(Long.MIN_VALUE);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(d));*/
        Calendar cal = Calendar.getInstance();
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-01-01 00:00:00");
        cal.setTime(d);
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));

        d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-12-31 11:59:59");
        cal.setTime(d);
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));

        cal.setTime(new Date());
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
    }

    public void testBase() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> itr = list.iterator();
        while (itr.hasNext()) {
            p(itr.next());
        }
        itr = list.iterator();
        while (itr.hasNext()) {
            p(itr.next());
        }
    }

    public void testExtract() throws Exception {
        Pattern p = Pattern.compile("(\\w+)\\.\\d+");
        Matcher m = p.matcher("d3277639312b99cd3a56567535f59b0a.2");
        m.find();
        p(m.group(0));
    }

    public void testShowCode() throws Exception {
        String[] showCodes = {"UT-46-QuanGuoJiuDianTeHuiQi-1*2-$clickPos", "-507-XingHuaLingQu-5-$clickPos", "null-629-ShuMaJiaDian*10-10-$clickPos", "UT-86-313331-ShiPinBaoJian*TuiJian*2*2-$clickPos",
                "UT-86-327473-1*1*3-$clickPos", "UT-86-226169-MeiRongZhuBao*TuiJian*1*3-$clickPos", "UT-603-17.5YingCheng(XiXiangDian )-2-$clickPos"};

        Pattern p = Pattern.compile("(?:\\w+)?-(\\w+)-(.+)-(\\w+(?:\\*.+)?)-.?\\w+\\.?");
        for (String showCode : showCodes) {
            Matcher m = p.matcher(showCode);
            //p(m.matches());
            while (m.find()) {
                p(showCode + ":" + m.group(1) + "," + m.group(2) + "," + m.group(3));
            }
        }
    }

    public void testNmCode() throws Exception {
        //String nmCode = "R1-38-black&HuoGuo-6-a.R1-40-ErQiQu-5-a.R1-581-RenMinGonGYuanDongMen-19-a.R1-54-143877-1*1-img.R2-543-143877-1-a.UT-601-143877-1*1-img";
        //String nmCode = "R2-639-143877-1-a.UT-601-14387999-1*1-img.";
        String nmCode = "E1-541-392740-1-img.E1-543-392740-1-a.E1-4-DengLu-1-a.E1-38-red&ChaDian-1-a.UT-601-392740-2*2-a";
        //Pattern p = Pattern.compile("\\w+-(?:543|601)-(\\w+)-(\\d+(\\*\\d+)?)-\\w+\\.?");
        Pattern p = Pattern.compile("(\\w+)-(\\w+)-(\\w+)-(\\d+(\\*\\d+)?)-\\w+\\.?");
        //Pattern p = Pattern.compile(".+-(?:639|640)-(\\w+)-(\\d+(\\*\\d+)?)-.*");
        Matcher m = p.matcher(nmCode);
        while (m.find()) {
            p(m.group(1) + "," + m.group(2) + "," + m.group(3));
        }
    }

    public void testBase3() throws Exception {
        //String p = "referer=\\[http://huodong\\.nuomi\\.com/pinpai.*\\]$";
        //String p = ".*//huodong\\..*/pinpai.*";
        //p("referer=[http://huodong.nuomi.com/pinpai]".matches(p));
        //String p = ".*-620-.*";
        //p("E2-17-XiaYiYe-8-a.E2-38-black&HuoGuo-2-a.E2-40-NaNAnQu-3-a.E2-620-WanDa-3-a.E2-54-215966-1*2-a.UT-543-215966-1-a".matches(p));

        /*String p = "referer=\\[http://\\w{2,}.nuomi.com/\\]$";
        p("referer=[http://qd.nuomi.com/]".matches(p));*/

        /*String p = "\\w+-54-\\w+-.*\\*.*-.*";
        p("R2-54-FengShuiZhen-1*1-$clickPos]".matches(p));*/

        /*String p = ".+-1\\*1-.+";
        p("R2-54-FengShuiZhen-1*1-$clickPos]".matches(p));*/

        /*String p = "\\w+-2-RenRen-.+-.+";
        p("E2-2-RenRen-2-$clickPos".matches(p));*/

        /*String a = "user_cookie=\\[(.+)\\]";
        p("user_cookie=[004b67d97dee7776c73911d92a438e38]".matches(a));*/

        p("time=[2013-06-10 16:33:19]".substring(6, 16));
    }

    public void testBase2() throws Exception {
        String a = "54 ";
        p(a.matches("^(541|540|70)$"));
    }

    public void testBase1() throws Exception {
        //String p = "http://\\w{2,}.nuomi.com(?:/category/2)?.*";
        //String p = "http://(bj).nuomi.com(/category/2.*|/\\?origURL=.*|$)";
        //String p = "http://(bj).nuomi.com";
        String p = "referer=\\[http://\\w{2,}.nuomi.com/(\\]|\\?[^w].+\\])";
        //String p = "referer=\\[http://\\w{2,}\\.nuomi\\.com/\\]";
        p("referer=[http://bj.nuomi.com/category/2-all-0-0-0-pd-t-0-0-0]".matches(p));
        p("referer=[http://bj.nuomi.com/]".matches(p));
        p("referer=[http://bj.nuomi.com/?origURL=]".matches(p));
    }

    public void testHomePage() {
        String p = "http://\\w{2,}\\.nuomi\\.com/($|\\?[^(wg)].+$)";
        p("http://bj.nuomi.com/".matches(p));
        p("http://bj.nuomi.com/?origURL=".matches(p));
        p("http://bj.nuomi.com/?wgfirst=".matches(p));

        p("http://bj.nuomi.com/".replaceAll("\\|", "_@@_"));
    }

    /**
     * 姝ｅ垯鍓嶇灮鍜屽悗椤�
     */
    public void testQZHG() {

        String a = "a=[1212|121]|b=[1fdslkfjsd]";
        p(a.split("(?<=\\])\\|")[0]);

        // .*$缁撳熬涔嬪墠涓嶈兘鏄笉鍜岃皭
        String reg = "^(?!.*(涓嶅悎璋�)).*$";// 鐢ㄥ埌浜嗗墠鐬宦犅�
        System.out.println("涓嶇淇′笉淇�,鍙嶆鐜板湪寰堜笉鍚堣皭".matches(reg));// false涓嶉�氳繃聽聽
        System.out.println("涓嶇淇′笉淇�,鍙嶆鐜板湪闈炲父鍚堣皭".matches(reg));// true閫氳繃聽聽
        System.out.println("涓嶅悎璋愬湪鏌愬浗鏄櫘閬嶅瓨鍦ㄧ殑".matches(reg));// false涓嶉�氳繃聽聽

        // 缁撳熬涔嬪墠涓嶆槸涓嶅悎璋�
        reg = "^.*(?<!(涓嶅悎璋�))$";// 鐢ㄥ埌浜嗗悗椤韭犅�
        System.out.println("涓嶇淇′笉淇�,鍙嶆鐜板湪寰堜笉鍚堣皭".matches(reg));// false涓嶉�氳繃聽聽
        System.out.println("涓嶇淇′笉淇�,鍙嶆鐜板湪闈炲父鍚堣皭".matches(reg));// true閫氳繃聽聽
        System.out.println("涓嶅悎璋愬湪鏌愬浗鏄櫘閬嶅瓨鍦ㄧ殑".matches(reg));// true閫氳繃聽聽

    }

    public static Date addDate(Date cur, int val) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cur);
        cal.add(Calendar.DATE, val);
        return cal.getTime();
    }

    public void objectTest() {
        //Object o = new Object();
        //o.equals()
        //o.getClass();
        //o.hashCode();
        //o.notify();
        //o.notifyAll();
        //o.toString();
        //o.wait();
    }

    // 鏂囦欢鍐�
    public void testFileW() throws Exception {
        String filePath = "D:\\My\\filew\\filew2\\test.txt";
        File f = new File(filePath);
        if (!f.exists()) {
            filePath = filePath.replaceAll("\\\\\\\\", File.separator).replace('\\', File.separatorChar).replace('/', File.separatorChar);
            int lastIndex = filePath.lastIndexOf(File.separator);
            String dirPath = filePath.substring(0, lastIndex);
            String fileName = filePath.substring(lastIndex + 1);
            File dir = new File(dirPath);
            dir.mkdirs();
            f = new File(dir, fileName);
        }
        OutputStream os = new FileOutputStream(f);
        OutputStreamWriter ow = new OutputStreamWriter(os);
        ow.write("杩欐槸涓�涓腑鑻辨枃娣峰悎鐨勬祴璇曪紝ok?");
        ow.close();
        os.close();
    }

    // 鏂囦欢璇�
    public void testFileR() throws Exception {
        File f = new File("D:\\My\\filew\\filew2\\test.txt");
        InputStream in = new FileInputStream(f);
        InputStreamReader ir = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(ir);
        String c = null;
        while ((c = br.readLine()) != null) {
            p(c);
        }
        br.close();
        in.close();
    }

    // 鏂囦欢鍒犻櫎
    public void testFileD() throws Exception {
        File f = new File("D:\\My\\filew");
        delSub(f);
    }

    private void delSub(File f) {
        File[] subs = f.listFiles();
        if (subs == null || subs.length == 0) {
            p("del1:" + f.getAbsolutePath());
            f.delete();
            return;
        }
        for (File sub : subs) {
            if (sub.isDirectory()) {
                delSub(sub);
            }
            p("del2:" + sub.getAbsolutePath());
            sub.delete();
        }
        p("del0:" + f.getAbsolutePath());
        f.delete();
    }

    // 娴嬭瘯姝ｅ垯琛ㄨ揪寮�
    public void testRegExp() {
        /*String a = "this is a test for regexp by xlj";
        String reg = "\\b(\\w+)\\b";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(a);
        while(m.find()){
        	p(m.group(0));
        	for(int i = 1; i < m.groupCount(); i++){
        		p(m.group(i));
        	}
        }*/
        //String url = "http://bj.nuomi.com/?wgfirst=wanggou&subCatalogId=353&price=0&wgsort=f&goflush=gn&thdCatalogId=0";
        //p(url.matches("http://.+wgfirst=wanggou&.+"));
        //String a = "null-671-KTV-1-aUTUT-null-PuTuoQu-3-a";
        //String a = "null-654-MeiShideal-1-a";
        //String a = "12121";
        //String reg = "\\w+-(\\d+)-.+?-(\\d+)-\\w+";
        //String reg = "(?:\\w+)?-(\\w+)-(.+)-(\\w+(?:\\*.+)?)-.?\\w+\\.?";
        //String reg = ".+-(\\d+)-(\\w+)-(\\d+(\\*\\d+)?)-.*";
        //String reg = "(dt)?+.+";
        //String reg = "^\\d+$";
        /*String reg = "http://\\w{2,}\\.nuomi\\.com/$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(a);
        while(m.find()){
            p(m.group(0));
        }*/

        String re = "(ftp|http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";
        Pattern p = Pattern.compile(re);
        System.out.println(p.matcher("http://").matches());
        System.out.println(p.matcher("http://xlj").matches());
        System.out.println(p.matcher("https://xlj/xljfsdfds").matches());
    }

    // 娴嬭瘯瀛楃涓�
    public void testString() {
        String a = "this,is,xlj,";
        p(a.split(",").length);
        p(a.split(",", -1).length);
        p(a.substring(a.length() - 1, a.length()));
        p(a.charAt(a.length() - 1));
    }

    // 娴嬭瘯鏁扮粍
    public void testArray() {
        String[] a = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        a[0] = "xlj test";
        p(a[0]);
        List<String> list = Arrays.asList(a);
        p(list.get(9));//10
        String[] b = Arrays.copyOf(a, 5);//1,2,3,4,5
        p(b[4]);//5
        System.arraycopy(a, 7, b, 2, 3);
        p(b[4]);//10
        Arrays.sort(b);
        Arrays.sort(b, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return -o1.compareTo(o2);
            }
        });
        System.out.println(Arrays.asList(b));
    }

    // 娴嬭瘯寰幆
    public void testLoop() {
        for (int i = 0; ; i++) {
            if (i == 5) break;
            p(i);
        }
    }

    public void testStringFmt() {
        String a = String.format("%S = %s%n", "Name", "Zhangsan");
        p(a);
        a = String.format("%b%n", false);
        p(a);
        Date date = new Date();
        long dataL = date.getTime();
        // 鏍煎紡鍖栧勾鏈堟棩
        // %t涔嬪悗鐢▂琛ㄧず杈撳嚭鏃ユ湡鐨勫勾浠斤紙2浣嶆暟鐨勫勾锛屽99锛�
        // %t涔嬪悗鐢╩琛ㄧず杈撳嚭鏃ユ湡鐨勬湀浠斤紝%t涔嬪悗鐢╠琛ㄧず杈撳嚭鏃ユ湡鐨勬棩鍙�
        System.out.printf("%1$ty-%1$tm-%1$td; %2$ty-%2$tm-%2$td%n", date, dataL);
        // %t涔嬪悗鐢╕琛ㄧず杈撳嚭鏃ユ湡鐨勫勾浠斤紙4浣嶆暟鐨勫勾锛夛紝
        // %t涔嬪悗鐢˙琛ㄧず杈撳嚭鏃ユ湡鐨勬湀浠界殑瀹屾暣鍚嶏紝 %t涔嬪悗鐢╞琛ㄧず杈撳嚭鏃ユ湡鐨勬湀浠界殑绠�绉�
        System.out.printf("%1$tY-%1$tB-%1$td; %2$tY-%2$tb-%2$td%n", date, dataL);
    }

    public void testByteInt() {
        // 灞�閮ㄥ彉閲忓繀椤诲垎閰嶅��
        /*int a;
        p(a + 100);*/

        int a = 1024;
        byte[] targets = new byte[4];
        targets[0] = (byte) (a & 0xff);// 鏈�浣庝綅
        targets[1] = (byte) ((a >> 8) & 0xff);// 娆′綆浣�
        targets[2] = (byte) ((a >> 16) & 0xff);// 娆￠珮浣�
        targets[3] = (byte) (a >>> 24);// 鏈�楂樹綅,鏃犵鍙峰彸绉汇��
        p(targets[3] + "," + targets[2] + "," + targets[1] + "," + targets[0]);
        p((targets[0] & 0xff) | (targets[1] << 8 & 0xff00) | (targets[2] << 16 & 0xff0000) | (targets[3] << 24 & 0xff000000));
    }
}
