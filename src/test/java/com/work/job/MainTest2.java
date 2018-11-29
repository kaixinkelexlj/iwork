package com.work.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fun.codec.TestRSA;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Files;
import com.work.AbstractTest;
import com.work.job.bean.Son;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.util.Base64Utils;
import org.springframework.util.ClassUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ReflectionUtils;

@SuppressWarnings("all")
public class MainTest2 extends AbstractTest {

  private static final Integer[] ORDER_STATUS_INCLUDE = new Integer[]{2, 6};

  public MainTest2() {

  }

  public static void main(String[] args) throws Exception {
    /*System.out.println(Integer.MAX_VALUE);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse("20181115"));
    calendar.add(Calendar.MONTH, -11);
    calendar.add(Calendar.DATE, -15);
    System.out.println(calendar.getTime());*/

    String url = "http://bigdata-test.xiaojukeji.com/report/v1/fetch-tool/redirect/v1?batchId=352849&batchOwner=xxx";
    System.out.println(URLEncoder.encode(url, "utf-8"));

  }

  @Test
  public void testURL() throws Exception{
    URL url = new URL("http://www.baidu.com?kw=test");
    System.out.println(url.toString());
  }

  @Test
  public void testUriEncode() throws Exception{
    System.out.println(URLEncoder.encode("http://bigdata-test.xiaojukeji.com/job-center"));
    String a = "http%3A%2F%2Fmis.diditaxi.com.cn%2Fauth%2F%3Fjumpto%3Dhttp%253A%252F%252Fbigdata-test.xiaojukeji.com%252Fdps_index%26app_id%3D204%26callback_index%3D0";
    System.out.println(URLDecoder.decode(a, "utf-8"));
  }

  @Test
  public void testReplace() throws Exception{
    String a = "hello \n world";
    System.out.println(a);
    System.out.println(a.replaceAll("\n", "\n\n"));
    System.out.println(a.replaceAll("\n", "<br/>"));
  }

  @Test
  public void testPattern() throws Exception{
    Pattern pattern = Pattern.compile("year=(.*)/month=(.*)/day=(.*)");
    System.out.println(pattern.matcher("year=2018/month=216/day=11/hour=111").find());
    System.out.println(pattern.matcher("year=2018/month=216/day=11/hour=111").matches());
  }

  @Test
  public void testBase64() throws Exception {
    String encode = Base64Utils.encodeToUrlSafeString("xxfsfds_fs%fds".getBytes("utf-8"));
    System.out.println(encode);
    System.out.println(new String(Base64Utils.decodeFromUrlSafeString(encode), "utf-8"));
  }

  @Test
  public void testCache() throws Exception {
    com.google.common.cache.Cache<String, String> cache = CacheBuilder.newBuilder()
        .expireAfterAccess(3, TimeUnit.SECONDS).build();
    cache.put("hello", "world");
    cache.put("xx", "gg");

    System.out.println(cache.size());

    cache.invalidate("xx");
    System.out.println(cache.size());
    System.out.println(TimeUnit.SECONDS.toMillis(5));
    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    System.out.println(cache.size());
    System.out.println(cache.getIfPresent("hello"));

  }

  @Test
  public void testAtomicInteger() throws Exception {
    System.out.println(new AtomicInteger(Integer.MAX_VALUE).incrementAndGet());
  }

  @Test
  public void testProcessBuilder() throws Exception {
    ProcessBuilder processBuilder = new ProcessBuilder("sleep", "100");
    processBuilder.redirectErrorStream(true);
    Process process = processBuilder.start();
    Thread thread = new Thread(() -> {
      try {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
        for (String line = null; (line = reader.readLine()) != null; ) {
          System.out.println(line);
        }
        Thread.sleep(100000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    thread.setDaemon(true);
    thread.start();
    thread.join(3000);
    process.destroy();
    System.out.println(process.waitFor());
    //System.out.println(process.waitFor(4, TimeUnit.SECONDS));
  }

  @Test
  public void testRenamePackage() throws Exception {

  }

  @Test
  public void test() throws Exception {
    Integer a = null;
    System.out.println(a);
    System.out.println("null".equals(JSON.toJSONString(null)));
  }

  @Test
  public void testArrayCast() throws Exception {
    Integer[] values = new Integer[]{};
    Object[] args = new Object[0];
    System.out.println((values instanceof Object[]));
    System.out.println(args instanceof Object[]);
    System.out.println(args instanceof Object);
  }

  @Test
  public void testMapCast() throws Exception {
  }

  @Test
  public void testGetStringPattern() throws Exception {
    String template = "this is {{name}} {{name2}}";
    Pattern pattern = Pattern.compile("\\{\\{(.+?)}}");
    Matcher matcher = pattern.matcher(template);
    System.out.println(matcher.replaceAll("{{#" + matcher.group(1) + "}}"));
  }

  @Test
  public void testTypeName() throws Exception {
    System.out.println(String.class.getTypeName());
  }

  @Test
  public void testFieldGeneric() throws Exception {
    System.out.println(MainTest2.class.getDeclaredField("ORDER_STATUS_INCLUDE").getName());
  }

  @Test
  public void testBreak() throws Exception {
    int code = 100;
    for (int i = 0; i < 10; i++) {
      switch (code) {
        case 100:
          System.out.println(code);
          break;
        default:
      }
    }
  }

  @Test
  public void testAssign() throws Exception {
    System.out.println(ClassUtils.isAssignable(String.class, Object.class));
    System.out.println(ClassUtils.isAssignable(Object.class, String.class));
  }

  @Test
  public void testSortRule() throws Exception {

    Long locateCityId = 100L;

    final List<Show> showList = new ArrayList<>();

    showList.add(new Show(200L, "show-200", 100));
    showList.add(new Show(200L, "show-200", 200));
    showList.add(new Show(100L, "show-200", 100));
    Arrays.asList(1, 2, 3, 4, 5).stream()
        .forEach(i -> showList.add(new Show((long) i, "show_" + i, RandomUtils.nextInt(100))));

    List<Show> sortedList = showList.stream().sorted(Comparator.<Show, Long>comparing(show -> {
      //cityId正序，boxOffice倒序
      long cityId = Optional.ofNullable(show.getCityId()).orElse((long) Integer.MAX_VALUE);
      if (cityId == locateCityId) {
        return 0L;
      }
      return cityId;
    }).thenComparing(show -> 0 - Optional.ofNullable(show.getBoxOffice()).orElse(0)))
        .collect(Collectors.toList());

    System.out.println(JSON.toJSON(sortedList));

  }

  public static class Show {

    private Long cityId;
    private String showName;
    private Integer boxOffice;

    public Show(Long cityId, String showName, Integer boxOffice) {
      this.cityId = cityId;
      this.showName = showName;
      this.boxOffice = boxOffice;
    }

    public Long getCityId() {
      return cityId;
    }

    public void setCityId(Long cityId) {
      this.cityId = cityId;
    }

    public String getShowName() {
      return showName;
    }

    public void setShowName(String showName) {
      this.showName = showName;
    }

    public Integer getBoxOffice() {
      return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
      this.boxOffice = boxOffice;
    }
  }

  @Test
  public void testgetLocalAddress() throws Exception {
    System.out.println(getLocalAddress());
    System.out.println(InetAddress.getLocalHost().getHostAddress());
  }

  public static String getLocalAddress() {
    try {
      // 遍历网卡，查找一个非回路ip地址并返回
      Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
      ArrayList<String> ipv4Result = new ArrayList<String>();
      ArrayList<String> ipv6Result = new ArrayList<String>();
      while (enumeration.hasMoreElements()) {
        final NetworkInterface networkInterface = enumeration.nextElement();
        final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
        while (en.hasMoreElements()) {
          final InetAddress address = en.nextElement();
          if (!address.isLoopbackAddress()) {
            if (address instanceof Inet6Address) {
              ipv6Result.add(normalizeHostAddress(address));
            } else {
              ipv4Result.add(normalizeHostAddress(address));
            }
          }
        }
      }

      // 优先使用ipv4
      if (!ipv4Result.isEmpty()) {
        for (String ip : ipv4Result) {
          if (ip.startsWith("127.0") || ip.startsWith("192.168") || ip.startsWith("172.")) {
            continue;
          }

          return ip;
        }

        // 取最后一个
        return ipv4Result.get(ipv4Result.size() - 1);
      }
      // 然后使用ipv6
      else if (!ipv6Result.isEmpty()) {
        return ipv6Result.get(0);
      }
      // 然后使用本地ip
      final InetAddress localHost = InetAddress.getLocalHost();
      return normalizeHostAddress(localHost);
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String normalizeHostAddress(final InetAddress localHost) {
    if (localHost instanceof Inet6Address) {
      return "[" + localHost.getHostAddress() + "]";
    } else {
      return localHost.getHostAddress();
    }
  }

  @Test
  public void testDtField() throws Exception {
    System.out.println(Calendar.getInstance().get(Calendar.HOUR));
    System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
  }

  @Test
  public void testTypeConvert() throws Exception {
    System.out.println(String.class.isAssignableFrom(String.class));
    System.out.println(String.class.isAssignableFrom(String.class));
  }

  @Test
  public void testGeneric() throws Exception {
    Map<String, Integer> map = new HashMap<>(0);
    map.put("test", 1000);
    Map<String, String> map2 = JSON
        .parseObject(JSON.toJSONString(map), new TypeReference<Map<String, String>>() {
        });
    System.out.println(map2.get("test").toString());
    System.out.println(JSON.toJSONString(MainTest2.class));

  }

  @Test
  public void testAssignable() throws Exception {
    System.out.println(Object.class.isAssignableFrom(Date.class));
  }

  @Test
  public void testRegExp2() throws Exception {
    //source.replaceAll("[^\u4e00-\u9fa5，。~`?？><！\\]\\[\\{\\}、 ……,#$%^&*!@.!a-zA-Z\\d]+","");
    System.out.println("fdsfds徐禄军12121###".replaceAll("[^\u4e00-\u9fa5#a-zA-Z]+", ""));
  }

  @Test
  public void testStringFormat3() throws Exception {
    System.out.println(String.format("%s/%s", null, null));
  }

  @Test
  public void testEq() throws Exception {
    Double f = 0.0d;
    System.out.println(f.doubleValue() == 0);
    System.out.println("it is null==>" + null);

  }

  @Test
  public void testTimer() throws Exception {
    ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    timer.scheduleAtFixedRate(new Runnable() {

      @Override
      public void run() {
        System.out.println(new SimpleDateFormat("yyyyMMddHH:mm:ss").format(new Date()));
      }
    }, 0, 20, TimeUnit.SECONDS);
    while (true) {
      ;
    }
  }

  @Test
  public void testNull() throws Exception {
    System.out.println(StringUtils.isBlank(String.valueOf(null)));
  }

  @Test
  public void testRe() throws Exception {
    Pattern pattern = Pattern.compile("WEB3=(\\w+);");
    String source = "PHPSESSID=h0rtvvicmbn6s1qfmghdumut93; path=/, WEB3=8b710afb399cd501a459af9c5a9c34f1;Path=/";
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      System.out.println(matcher.group(1));
    }

    pattern = Pattern.compile("PHPSESSID=(\\w+);");
    matcher = pattern.matcher(source);
    while (matcher.find()) {
      System.out.println(matcher.group(1));
    }
  }

  @Test
  public void testHash() {
    int a = 1;
    int b = a | a >>> 1;
    System.out.println(a | 0);
    System.out.println(b);
  }

  @Test
  public void testCastList() {
    List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2));
    System.out.println((ArrayList<Integer>) ids);
    System.out.println((ArrayList) ids);
    //System.out.println((ArrayList<Long>)ids);

  }

  public static void testJjz() throws Exception {
    String filePath = "c:/Users/user.user-PC/Desktop/232_Request.txt";
    List<String> lines = Files.readLines(new File(filePath), Charset.forName("utf-8"));
    //System.out.println(URLDecoder.decode(lines.get(lines.size() - 1), "utf-8"));
    String line = lines.get(lines.size() - 1);
    line = URLDecoder.decode(line, "utf-8");
    final TreeMap<String, String> map = new TreeMap<>();
    Stream.of(line.split("&")).forEach(kv -> {
      String[] arr = kv.split("=");
      String key = arr[0];
      String val = arr.length > 1 ? arr[1] : null;
      if ("sign".equals(key) || key.endsWith("photo")) {
        if ("sign".equals(key)) {
          System.out.println(kv);
        }
        return;
      }
      map.put(key, val);
    });
    System.out.println(new ObjectMapper().writeValueAsString(map));
    StringBuilder sb = new StringBuilder();
    map.forEach((key, val) -> {
      sb.append(key).append("=").append(val).append("&");
    });
    String result = sb.substring(0, sb.length() - 1);
    System.out.println(result);
    System.out.println(DigestUtils.md5DigestAsHex(result.getBytes()));
  }

  public static void testRandom() throws Exception {
    int size = 0;
    for (int i = 0; i < 10009; i++) {
      if (RandomUtils.nextInt(10000) < 1000) {
        size++;
      }
    }
    System.out.println(size);

    size = 0;
    for (int i = 0; i < 10009; i++) {
      if (RandomUtils.nextInt(10000) < 6000) {
        size++;
      }
    }
    System.out.println(size);
  }

  public static void testGetMapType() throws Exception {

    Map map = new HashMap();
    map.put("name", null);

    Map<String, String> b = (Map) map;

    Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();

    System.out.println(entry.getKey().getClass());
    System.out.println(entry.getValue().getClass());

    //System.out.println(b.get("name"));

    System.out.println(map.getClass().getGenericInterfaces()[0]);

    Map<String, Integer> c1 = new HashMap<>();
    c1.put("test", 123);
    Map<String, String> c2 = new HashMap<>();
    //c2.putAll(c1);//compile error
    System.out.println(c2);

  }

  public static String shorten(String source, int size) {
    if (StringUtils.isBlank(source)) {
      return source;
    }
    if (source.length() <= size) {
      return source;
    }
    try {
      int endIndex = Math.max(size, source.length() - 200);
      return source.substring(0, size) + "......" + source.substring(endIndex, source.length());
    } catch (Exception ex) {
      return source;
    }
  }

  public void testDecimal() {
    System.out.println(
        new BigDecimal(String.valueOf(0.3d)).subtract(new BigDecimal(String.valueOf(0.1d)))
            .doubleValue());
    System.out.println(4.4 / 0.1);
    System.out.println(new BigDecimal(String.valueOf("4.015")).multiply(new BigDecimal(100)));

    System.out.println(
        new BigDecimal(1).divide(new BigDecimal(3), 4, RoundingMode.HALF_UP).doubleValue());
    System.out.println(1.0 / 3.0);
  }

  public static void testScheduledTask() throws Exception {
    ScheduledExecutorService recommendExecutor = Executors.newSingleThreadScheduledExecutor();
    final AtomicInteger i = new AtomicInteger(0);
    final CountDownLatch l = new CountDownLatch(1);
    ScheduledFuture f = recommendExecutor.scheduleWithFixedDelay(new Runnable() {

      @Override
      public void run() {
        p("scheduled-" + System.currentTimeMillis());
        try {

          if (i.getAndIncrement() == 5) {
            throw new RuntimeException("interruppt!!");
          }
          l.countDown();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }, 10, 3, TimeUnit.SECONDS);
    try {
      l.await();
      f.get();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public static void testStringFormat2() {
    p(String.format("%sabc%s", 1, 1));
    p(String.format("%1$sabc%1$s", 1));
  }

  public static void testBigDecimal() {
    //System.out.println(new BigDecimal("1.11111111111xx").movePointRight(2).longValue());
    System.out.println(new BigDecimal("1.1").movePointRight(2).longValue());
    System.out.println(new BigDecimal("1").movePointRight(2).longValue());

    Double d = 10201 * 188 / 1000.0;
    p(d);
    p(d.longValue());
  }

  public static void testRandomString() {
    //System.out.println(RandomStringUtils.random(10, true, true));
    //System.out.println(RandomStringUtils.randomAlphabetic(6));
    //System.out.println(RandomStringUtils.randomAlphanumeric(6));
    //System.out.println(RandomStringUtils.randomAscii(6));
    //System.out.println(RandomStringUtils.randomNumeric(6));

    //System.out.println(RandomStringUtils.random(5, "0123456789abcdefghijklmnopqrstuvwxyz"));

    Set<String> set = new HashSet<>(1000000);
    String code = null;
    for (int i = 0; i < 100000; i++) {
      code = "v" + RandomStringUtils.random(5, "0123456789abcdefghijklmnopqrstuvwxyz");
      System.out.println(code);
      set.add(code);
    }
    System.out.println(set.size());
  }

  public void testDateLoop() throws Exception {

        /*CalUserPriceTotalTaskRequest request = new CalUserPriceTotalTaskRequest();
        request.setGmtEnd(new Date());
        Date gmtStart = CommonUtils.calDate(request.getGmtEnd(), Calendar.DATE, -1);
        // CommonUtils.setTime(gmtStart, 0, 0, 0);
        request.setGmtStart(gmtStart);
        CallId callId = CallId.create("test");
        
        Date loopEnd = request.getGmtEnd();
        Date loopStart = CommonUtils.calDate(loopEnd, Calendar.DATE, -1);
        loopStart = CommonUtils.setTime(loopStart, 0, 0, 0);
        if (request.getGmtStart().compareTo(loopStart) >= 0) {
            loopStart = request.getGmtStart();
        }
        
        LoggerUtils.info(taskLogger, callId, "task start==>request-end[{}],request-start[{}]", CommonUtils.date2Str(request.getGmtEnd()), CommonUtils.date2Str(request.getGmtStart()),
                         0);
        for (;;) {
            LoggerUtils.info(taskLogger, callId, "loop==>end[{}],start[{}],userCount[{}]", CommonUtils.date2Str(loopEnd), CommonUtils.date2Str(loopStart), 0);
        
            loopEnd = loopStart;
            loopStart = CommonUtils.calDate(loopEnd, Calendar.DATE, -1);
            loopStart = CommonUtils.setTime(loopStart, 0, 0, 0);
            // 鏈�悗涓�釜loop
            if (request.getGmtStart().compareTo(loopStart) >= 0) {
                loopStart = request.getGmtStart();
            }
            if (loopEnd.compareTo(request.getGmtStart()) <= 0) {
                break;
            }
        
        }*/

  }

  public static void testBigdecimal() throws Exception {
    Double vald = (NumberUtils.toDouble("0.00", 0.00) * 100L);
    Long val = vald.longValue();
    System.out.println(val);
  }

  public static void testThreadPool() throws Exception {

  }

  public static void genSql() throws Exception {
    String pattern = "select * from ylb_user_00%s";
    List<String> buff = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      buff.add(String.format(pattern, StringUtils.leftPad(String.valueOf(i), 2, '0')));
    }
    System.out.println("select * from (\n" + StringUtils.join(buff, "\nunion all\n") + "\n)");
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
        /*FastClass cls = FastClass.create(MainTest.class);
        FastMethod m = cls.getMethod("test", new Class[0]);
        m.invoke(new MainTest(), null);*/
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

    Pattern p = Pattern.compile("#.+?#");
    String txt = "aaaaaaaaaaaa#  鏈�編涓浗  #fsdfs#a##b#\r\naaaaaaaaaaaa#!!鏈�編涓浗2         #";

    Matcher m = p.matcher(txt);

    while (m.find()) {
      System.out.println(m.group(0));
    }

    Matcher m2 = p.matcher("aaa##bb");

    while (m2.find()) {
      System.out.println(m2.group(1));
    }

  }

  @Test
  public void testSubList() {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
    System.out.println(list.subList(0, list.size() - 1));
    list = Arrays.asList(1, 2, 3, 4, 5, 6);
    System.out.println(list.subList(6, 6));

    System.out.println(list.subList(0, 2));
    System.out.println(list.subList(2, 4));
    System.out.println(list.subList(4, 6));
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
    DateFormat df = new SimpleDateFormat("M閺堝潐閺冿拷");
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
    System.out
        .println(Thread.currentThread().getContextClassLoader().getResource("").toURI().toString());
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

  public void testSplit() {
    String a = "xlj.121=hhq";
    String[] arr = a.split("\\s*=\\s*");
    p(arr[0].split("\\.")[1]);
    a = "xlj|hhq|zzz|";
    p(a.split("\\|").length);
    arr = a.split("\\|", -1);
    p(arr[3]);
  }

  public void arrCopyTest() {
    String[] a = new String[]{"1", "2", "3", "4", "5"};
    String[] b = Arrays.copyOfRange(a, 5, 5);
    p(StringUtils.join(b, "#"));
  }

  public void asserTest() {
    // 鏉╂劘顢戦弮鍓佲敋瀵拷婵绱戦崗锟�-ea
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
    String a = "閹恒儱褰涢柌鎴︻杺[%s] 鑴�[%s]%%";
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
    String[] showCodes = {"UT-46-QuanGuoJiuDianTeHuiQi-1*2-$clickPos",
        "-507-XingHuaLingQu-5-$clickPos", "null-629-ShuMaJiaDian*10-10-$clickPos",
        "UT-86-313331-ShiPinBaoJian*TuiJian*2*2-$clickPos",
        "UT-86-327473-1*1*3-$clickPos", "UT-86-226169-MeiRongZhuBao*TuiJian*1*3-$clickPos",
        "UT-603-17.5YingCheng(XiXiangDian )-2-$clickPos"};

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
   * 濮濓絽鍨崜宥囩伄閸滃苯鎮楁い锟�
   */
  public void testQZHG() {

    String a = "a=[1212|121]|b=[1fdslkfjsd]";
    p(a.split("(?<=\\])\\|")[0]);

    // .*$缂佹挸鐔稊瀣娑撳秷鍏橀弰顖欑瑝閸滃矁鐨�
    String reg = "^(?!.*(娑撳秴鎮庣拫锟�).*$";// 閻€劌鍩屾禍鍡楀閻鐘咃拷
    System.out.println("娑撳秶顓告穱鈥茬瑝娣囷拷,閸欏秵顒滈悳鏉挎躬瀵板牅绗夐崥鍫ｇ毉".matches(reg));// false娑撳秹锟芥俺绻冭伣鑱�
    System.out.println("娑撳秶顓告穱鈥茬瑝娣囷拷,閸欏秵顒滈悳鏉挎躬闂堢偛鐖堕崥鍫ｇ毉".matches(reg));// true闁俺绻冭伣鑱�
    System.out.println("娑撳秴鎮庣拫鎰躬閺屾劕娴楅弰顖涙珮闁秴鐡ㄩ崷銊ф畱".matches(reg));// false娑撳秹锟芥俺绻冭伣鑱�

    // 缂佹挸鐔稊瀣娑撳秵妲告稉宥呮値鐠嬶拷
    reg = "^.*(?<!(娑撳秴鎮庣拫锟�)$";// 閻€劌鍩屾禍鍡楁倵妞ら煭鐘咃拷
    System.out.println("娑撳秶顓告穱鈥茬瑝娣囷拷,閸欏秵顒滈悳鏉挎躬瀵板牅绗夐崥鍫ｇ毉".matches(reg));// false娑撳秹锟芥俺绻冭伣鑱�
    System.out.println("娑撳秶顓告穱鈥茬瑝娣囷拷,閸欏秵顒滈悳鏉挎躬闂堢偛鐖堕崥鍫ｇ毉".matches(reg));// true闁俺绻冭伣鑱�
    System.out.println("娑撳秴鎮庣拫鎰躬閺屾劕娴楅弰顖涙珮闁秴鐡ㄩ崷銊ф畱".matches(reg));// true闁俺绻冭伣鑱�

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

  // 閺傚洣娆㈤崘锟�
  public void testFileW() throws Exception {
    String filePath = "D:\\My\\filew\\filew2\\test.txt";
    File f = new File(filePath);
    if (!f.exists()) {
      filePath = filePath.replaceAll("\\\\\\\\", File.separator).replace('\\', File.separatorChar)
          .replace('/', File.separatorChar);
      int lastIndex = filePath.lastIndexOf(File.separator);
      String dirPath = filePath.substring(0, lastIndex);
      String fileName = filePath.substring(lastIndex + 1);
      File dir = new File(dirPath);
      dir.mkdirs();
      f = new File(dir, fileName);
    }
    OutputStream os = new FileOutputStream(f);
    OutputStreamWriter ow = new OutputStreamWriter(os);
    ow.write("鏉╂瑦妲告稉锟芥稉顏冭厬閼昏鲸鏋冨ǎ宄版値閻ㄥ嫭绁寸拠鏇礉ok?");
    ow.close();
    os.close();
  }

  // 閺傚洣娆㈢拠锟�
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

  // 閺傚洣娆㈤崚鐘绘珟
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

  // 濞村鐦锝呭灟鐞涖劏鎻锟�
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

  // 濞村鐦�妤冾儊娑擄拷
  public void testString() {
    String a = "this,is,xlj,";
    p(a.split(",").length);
    p(a.split(",", -1).length);
    p(a.substring(a.length() - 1, a.length()));
    p(a.charAt(a.length() - 1));
  }

  // 濞村鐦弫鎵矋
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

  // 濞村鐦顏嗗箚
  public void testLoop() {
    for (int i = 0; ; i++) {
      if (i == 5) {
        break;
      }
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
    // 閺嶇厧绱￠崠鏍у嬀閺堝牊妫�
    // %t娑斿鎮楅悽鈻傜悰銊с仛鏉堟挸鍤弮銉︽埂閻ㄥ嫬鍕炬禒鏂ょ礄2娴ｅ秵鏆熼惃鍕嬀閿涘苯顩�9閿涳拷
    // %t娑斿鎮楅悽鈺╃悰銊с仛鏉堟挸鍤弮銉︽埂閻ㄥ嫭婀�禒鏂ょ礉%t娑斿鎮楅悽鈺犵悰銊с仛鏉堟挸鍤弮銉︽埂閻ㄥ嫭妫╅崣锟�
    System.out.printf("%1$ty-%1$tm-%1$td; %2$ty-%2$tm-%2$td%n", date, dataL);
    // %t娑斿鎮楅悽鈺曠悰銊с仛鏉堟挸鍤弮銉︽埂閻ㄥ嫬鍕炬禒鏂ょ礄4娴ｅ秵鏆熼惃鍕嬀閿涘绱�
    // %t娑斿鎮楅悽藱鐞涖劎銇氭潏鎾冲毉閺冦儲婀￠惃鍕箑娴犵晫娈戠�灞炬殻閸氬稄绱�%t娑斿鎮楅悽鈺炵悰銊с仛鏉堟挸鍤弮銉︽埂閻ㄥ嫭婀�禒鐣屾畱缁狅拷缁夛拷
    System.out.printf("%1$tY-%1$tB-%1$td; %2$tY-%2$tb-%2$td%n", date, dataL);
  }

  public void testByteInt() {
    // 鐏烇拷闁劌褰夐柌蹇撶箑妞よ鍨庨柊宥咃拷锟�
        /*int a;
        p(a + 100);*/

    int a = 1024;
    byte[] targets = new byte[4];
    targets[0] = (byte) (a & 0xff);// 閺堬拷娴ｅ簼缍�
    targets[1] = (byte) ((a >> 8) & 0xff);// 濞嗏�缍嗘担锟�
    targets[2] = (byte) ((a >> 16) & 0xff);// 濞嗭繝鐝担锟�
    targets[3] = (byte) (a >>> 24);// 閺堬拷妤傛ü缍�閺冪姷顑侀崣宄板礁缁夋眹锟斤拷
    p(targets[3] + "," + targets[2] + "," + targets[1] + "," + targets[0]);
    p((targets[0] & 0xff) | (targets[1] << 8 & 0xff00) | (targets[2] << 16 & 0xff0000) | (
        targets[3] << 24 & 0xff000000));
  }
}
