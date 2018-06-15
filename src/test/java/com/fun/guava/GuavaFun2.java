package com.fun.guava;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.work.AbstractTest;

@SuppressWarnings("deprecation")
public class GuavaFun2 extends AbstractTest {

    public static void main(String[] args) throws Exception {
        //testSplitor();
        testCache();
    }

    public static void testCache() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(3, TimeUnit.SECONDS).build();
        cache.invalidate(100);
        cache.put(100, "ok");
        while (true) {
            String val = cache.getIfPresent(100);
            if (val == null) {
                break;
            } else {
                p(val);
                cache.invalidate(100);
            }
        }
        p("end");
    }

    public static void filter() throws Exception {
        List<Integer> lists = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> l = Lists.newArrayList(Collections2.filter(lists, new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
                return input % 2 == 1;
            }

        }));
        p(l);
    }

    public static void objects() throws Exception {
        Object val = Objects.firstNonNull(null, 100);
        p(val);
        val = Objects.firstNonNull(200, 100);
        p(val);
        val = Objects.firstNonNull(null, null);
        p(val);
    }

    public static void testSplitor() throws Exception {
        System.out.println(Splitter.on(",").omitEmptyStrings().trimResults().splitToList("a,  b  ,c,c,c,ccc,,,,,"));
        System.out.println(Joiner.on(";;").join(Splitter.on(",").omitEmptyStrings().trimResults().splitToList("a,  b  ,c,c,c,ccc,,,,,")));
    }

    public static void strings() throws Exception {
        p(Strings.commonPrefix("xlj.test", "xlj.t121"));
        p(Strings.nullToEmpty(null));
    }

    public static void preconditions() throws Exception {
        Preconditions.checkArgument(false);
    }

    public static void ordering() throws Exception {
        //Ordering<T>
        //ComparisonChain.start().compare(o2.getItemTotal(), o1.getItemTotal()).compare(o1.getName(), o2.getName()).result();
    }

    public static void multiset() throws Exception {
        Multiset<Object> myMultiset = HashMultiset.create();

        Object myObject = new Object();
        myMultiset.add(myObject);
        myMultiset.add(myObject); // add it a second time.  

        System.out.println(myMultiset.count(myObject)); // 2  
        myMultiset.remove(myObject);
        System.out.println(myMultiset.count(myObject)); // 1  

    }

    @SuppressWarnings("unused")
    public static void listsAndmaps() throws Exception {
        List<String> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        ImmutableList<Integer> ilist = ImmutableList.of(1, 2, 3);
        ImmutableMap<String, String> imap = ImmutableMap.of("k1", "v1");
        ImmutableMap.Builder<String, String> b = ImmutableMap.builder();
        b.put("k1", "v1");
        //Lists.transform(fromList, function)
        //Collections2.filter(unfiltered, predicate)
    }

    public static void streams() throws Exception {

        CharStreams.readLines(new InputSupplier<InputStreamReader>() {
            @Override
            public InputStreamReader getInput() throws IOException {
                return null;
            }

        }, new LineProcessor<String>() {
            @Override
            public boolean processLine(String line) throws IOException {
                return false;
            }

            @Override
            public String getResult() {
                return null;
            }

        });

        //ByteStreams.copy(from, to)
    }

    public static void filesAndresources() throws Exception {
        //Files.readLines(file, charset);
        //Resources.getResource(resourceName)
    }

    public static void comparechain() throws Exception {
        //ComparisonChain.start().compare(left, right).compare(left, right).result();
    }

    public static void splitAndjoin() throws Exception {
        //Joiner.on(",").join(parts);
        // Splitter.on("[,|，]").splitToList(parts);
    }

    public static void test2() throws Exception {
        // List<String> lines = Files.readLines(new File("c:\\Users\\lujun.xlj\\Desktop\\invalid.txt"), Charset.forName("utf-8"));
        //String json = StringUtils.join(lines, "\r\n");

        //System.out.println(CommonUtils.fixInvalidJsonString(json));

        // System.out.println(JSON.toJSONString(JsonParse.getDetail(json)));

    }

    public static String readLine(String line) throws Exception {
        // System.out.println(line);

        String re = "\\\\[^0-7btnvfFrxu\"'\\\\/]";

        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(line);

        while (m.find()) {
            System.out.println(m.group(0));
            // System.out.println(line);
        }

        return null;

    }

    public static void handleData() throws Exception {
        String path = "c:\\Users\\lujun.xlj\\Desktop\\娱乐宝7期.csv";

        File f = new File(path);
        if (!f.exists()) {
            System.err.println(path + " not exists");
            System.exit(1);
        }

        Charset charset = Charset.forName("GBK");
        final Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        Files.readLines(f, charset, new LineProcessor<Object>() {

            @Override
            public boolean processLine(String line) throws IOException {
                String[] arr = line.split(",");
                String mobile = arr[1];
                String pname = arr[0];
                Set<String> set = map.get(mobile);
                if (set == null) {
                    set = new HashSet<String>();
                    map.put(mobile, set);
                }
                set.add("<" + pname + ">");
                return true;
            }

            @Override
            public Object getResult() {
                return null;
            }

        });

        Set<String> mobileset = map.keySet();
        File info = new File("c:\\Users\\lujun.xlj\\Desktop\\info.txt");
        File result = new File("c:\\Users\\lujun.xlj\\Desktop\\result.txt");
        if (info.exists()) {
            info.delete();
        }
        if (result.exists()) {
            result.delete();
        }

        for (Iterator<String> itr = mobileset.iterator(); itr.hasNext();) {
            String mobile = itr.next();
            Set<String> nameSet = map.get(mobile);
            if (nameSet.size() > 1) {
                String line = "###" + mobile + " has more than 1 project";
                System.out.println(line);
                Files.append(line + "\r\n", info, charset);
            }
        }
        Files.append("mobile,nameurl\r\n", result, charset);
        for (Iterator<String> itr = mobileset.iterator(); itr.hasNext();) {
            String mobile = itr.next();
            Set<String> nameSet = map.get(mobile);
            String list = org.apache.commons.lang.StringUtils.join(nameSet, "、");
            String line = String.format("%s,%s可以领取收益啦！点击http://tb.cn/9EsRPox", mobile, list);
            System.out.println(line);
            Files.append(line + "\r\n", result, charset);
        }
    }

    public static void multimap() throws Exception {
        Multimap<String, String> myMultimap = ArrayListMultimap.create();

        // Adding some key/value  
        myMultimap.put("Fruits", "Bannana");
        myMultimap.put("Fruits", "Apple");
        myMultimap.put("Fruits", "Pear");
        myMultimap.put("Vegetables", "Carrot");

        // Getting the size  
        int size = myMultimap.size();
        System.out.println(size); // 4  

        // Getting values  
        Collection<String> fruits = myMultimap.get("Fruits");
        System.out.println(fruits); // [Bannana, Apple, Pear]  

        Collection<String> vegetables = myMultimap.get("Vegetables");
        System.out.println(vegetables); // [Carrot]  

        // Iterating over entire Mutlimap  
        for (String value : myMultimap.values()) {
            System.out.println(value);
        }

        // Removing a single value  
        myMultimap.remove("Fruits", "Pear");
        System.out.println(myMultimap.get("Fruits")); // [Bannana, Pear]  

        // Remove all values for a key  
        myMultimap.removeAll("Fruits");
        System.out.println(myMultimap.get("Fruits")); // [] (Empty Collection!)  
    }

}
