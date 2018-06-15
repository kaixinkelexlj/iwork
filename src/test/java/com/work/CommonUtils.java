package com.work;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

public class CommonUtils {

    private static String       CODEC_AES            = "AES";
    private static String       CODEC_AES_MODE       = "AES/CBC/PKCS5Padding";

    public static final Date    MIN_DATE;
    public static final Date    MAX_DATE;

    public static String        CODEC_DEFAULTCHARSET = "UTF-8";

    public static final Pattern emoji                = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    static {
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 1, 0, 0, 0);
        MIN_DATE = cal.getTime();
        cal.set(9999, 0, 1, 0, 0, 0);
        MAX_DATE = cal.getTime();
    }

    /**
     * 获取本机IP地址
     * 
     * @return
     */
    public static String getServerIP() {
        String ip = "unknown";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {

        }
        return ip;
    }

    public static boolean inStrList(long id, Object idsObj) {
        return inStrList(id, idsObj, ",");
    }

    public static boolean inStrList(long id, Object idsObj, String seperator) {
        if (null == idsObj || StringUtils.isBlank(idsObj.toString())) return false;

        String[] segments = StringUtils.split(idsObj.toString(), seperator);
        for (String segment : segments)
            if (segment.trim().equals(String.valueOf(id))) return true;

        return false;
    }

    public static <T> List<T> cutArrayList(List<T> list, int size) {
        if (list == null) {
            return new ArrayList<>();
        }
        if (size <= 0) {
            return list;
        }
        return list.size() > size ? new ArrayList<>(list.subList(0, size)) : list;
    }

    public static List<String> strSplit2List(String str, String separatorChars) {
        if (StringUtils.isBlank(str)) {
            return new ArrayList<>();
        }
        String[] strArray = StringUtils.split(str, separatorChars);
        if (strArray == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(strArray);
    }

    public static interface GetKey<K, V> {

        public K getKey(V v);
    }

    public static <K, V> Map<K, V> listToMap(List<V> list, GetKey<K, V> getKey) {
        Map<K, V> map = new HashMap<K, V>();
        for (V v : list) {
            map.put(getKey.getKey(v), v);
        }
        return map;
    }

    public static String cent2Yuan(long cent) {
        DecimalFormat format = new DecimalFormat("#.##");
        if (cent > 100 * 10000) {
            BigDecimal bd = new BigDecimal(cent / 10000).movePointLeft(2);
            return format.format(bd) + "万元";
        } else if (cent > 100 * 10000 * 10000) {
            BigDecimal bd = new BigDecimal(cent / 10000 * 10000).movePointLeft(2);
            return format.format(bd) + "亿元";
        } else {
            BigDecimal bd = new BigDecimal(cent).movePointLeft(2);
            return format.format(bd) + "元";
        }
    }

    public static String moneyFomat(long cent) {
        BigDecimal bd = new BigDecimal(cent).movePointLeft(2);
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(bd);
    }

    public static String moneyFomatFillZero(long cent) {
        BigDecimal bd = new BigDecimal(cent).movePointLeft(2);
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(bd);
    }

    public static String subscribeFormat(long num) {
        if (num > 1000000) {
            return (num / 10000) + "万人";
        }
        return num + "人";
    }

    public static String praiseFormat(String numStr) {
        long num = NumberUtils.toLong(numStr, -1L);
        if (num == -1) return numStr;
        if (num > 1000000) {
            return (num / 10000) + "w";
        }
        return "" + num;
    }

    /**
     * http://img01.daily.taobaocdn.net/tfscom/TB1vg9RXXXXXXa4bVXXXXXXXXXX.jpg 返回 .jpg
     * http://img01.daily.taobaocdn.net/tfscom/TB1vg9RXXXXXXa4bVXXXXXXXXXX.jpg.png 返回 .jpg.png
     * http://img01.daily.taobaocdn.net/tfscom/TB1vg9RXXXXXXa4bVXXXXXXXXXX 返回 空串
     * 
     * @param url
     * @return
     */
    public static String getUrlSuffix(String url) {
        String suffix = "";
        if (StringUtils.isBlank(url)) {
            return suffix;
        }
        int i = url.lastIndexOf("/");
        int j = url.indexOf(".", i);

        if (j > -1) {
            suffix = url.substring(j);
        }

        return suffix;
    }

    public static <T> List<T> notNull(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>(0);
        }
        return list;
    }

    public static <K, V> Map<K, V> notNull(Map<K, V> map) {
        if (map == null) {
            map = new HashMap<K, V>(0);
        }
        return map;
    }

    public static int getDayInt(Date date) {
        return getDayInt(date, "yyyyMMdd");
    }

    public static int getMonthInt(Date date) {
        return getMonthInt(date, "yyyyMM");
    }

    public static int getDayInt(Date date, String format) {
        if (date == null) {
            return 0;
        }
        try {
            return NumberUtils.toInt(new SimpleDateFormat(format).format(date), 0);
        } catch (Exception ex) {

        }
        return 0;
    }

    public static int getMonthInt(Date date, String format) {
        if (date == null) {
            return 0;
        }
        try {
            return NumberUtils.toInt(new SimpleDateFormat(format).format(date), 0);
        } catch (Exception ex) {

        }
        return 0;
    }

    public static Date str2Date(String source, String pattern) {
        if (StringUtils.isBlank(source)) {
            throw new RuntimeException("invalid date source");
        }
        if (StringUtils.isBlank(pattern)) {
            throw new RuntimeException("invalid pattern");
        }
        try {
            Date dt = new SimpleDateFormat(pattern).parse(source);
            return dt;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Date str2Date(String source) {
        return str2Date(source, "yyyy-MM-dd HH:mm:ss");
    }

    public static String date2Str(Date source, String pattern) {
        if (source == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).format(source);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String date2Str(Date source) {
        return date2Str(source, "yyyy-MM-dd HH:mm:ss");
    }

    public static Long fomatLong(Object obj) {
        return null == obj ? 0L : Long.parseLong("" + obj);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成TraceId
     * 
     * @return
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取本机IP
     * 
     * @return
     */
    public static String getLocalHostIp() {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration<InetAddress> nii = ni.getInetAddresses();
                while (nii.hasMoreElements()) {
                    ip = (InetAddress) nii.nextElement();
                    if (ip.getHostAddress().indexOf(":") == -1 && ip.isSiteLocalAddress()) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {

        }

        return null;
    }

    public static String formatString(String format, Object... args) {
        if (args == null || args.length == 0) {
            return format;
        }
        try {
            format = format.replaceAll("\\{\\}", "%s");
            return String.format(format, args);
        } catch (Exception ex) {
            return format;
        }
    }

    public static Date calDate(Date source, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(source);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date setTime(Date source, int hour, int minite, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(source);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), hour, minite, second);
        return cal.getTime();
    }

    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }

    public static String encryptWithAes(String source, String keySeed) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance(CODEC_AES);
        kgen.init(128, new SecureRandom(keySeed.getBytes(CODEC_DEFAULTCHARSET)));// 128bit密钥
        SecretKey secretKey = kgen.generateKey();

        byte[] enCodeFormat = secretKey.getEncoded();
        // System.out.println(bytesToHexString(enCodeFormat));//
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, CODEC_AES);
        Cipher cipher = Cipher.getInstance(CODEC_AES_MODE);// 创建密码器
        byte[] byteContent = source.getBytes(CODEC_DEFAULTCHARSET);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return bytesToHexString(result);// 加密结果

    }

    public static String decryptWithAes(String encryptString, String keySeed) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance(CODEC_AES);
        kgen.init(128, new SecureRandom(keySeed.getBytes(CODEC_DEFAULTCHARSET)));
        SecretKey secretKey = kgen.generateKey();

        byte[] enCodeFormat = secretKey.getEncoded();
        // System.out.println(bytesToHexString(enCodeFormat));
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, CODEC_AES);
        Cipher cipher = Cipher.getInstance(CODEC_AES_MODE);// 创建密码器

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));// 初始化
        byte[] result = cipher.doFinal(hexStringToByte(encryptString));
        return new String(result, CODEC_DEFAULTCHARSET);// 解密结果

    }

    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    public static final String bytesToHexString(byte[] bArray) {

        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String encodeURIComponent(String source) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        String result = URLEncoder.encode(source, CODEC_DEFAULTCHARSET);
        result = result.replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~");
        return result;
    }

   /* // base64编码，针对url做特殊处理，替换+ / 两个特殊字符，移除尾部的一个或两个=
    public static String base64EncodeForUrl(String s, String charset) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        String enStr = Base64.encodeToString(s.getBytes(charset), false);
        enStr = enStr.replace('+', '-').replace('/', '_').replace("=", "");
        return enStr;
    }*/

    // base64解码，针对url做特殊处理，替换+ /两个特殊字符，补足尾部的一个或两个=
    /*public static String base64DecodeForUrl(String s, String charset) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        String str = s.replace('-', '+').replace('_', '/');
        int mod4 = str.length() % 4;
        if (mod4 == 3) {
            str += "=";
        } else if (mod4 == 2) {
            str += "==";
        }
        byte[] deBytes = Base64.decode(str);
        return deBytes != null ? new String(deBytes, charset) : "";
    }*/

    public static boolean isEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        Matcher m = emoji.matcher(source);
        return m.find();
    }

    public static <T> List<T> subList(List<T> sourceList, int startIndex, int endIndex) {
        if (sourceList == null) {
            return null;
        }
        if (sourceList.size() == 0) {
            return new ArrayList<T>(0);
        }
        if (startIndex < 0) {
            return new ArrayList<T>(0);
        }
        if (startIndex > endIndex) {
            return new ArrayList<T>(0);
        }
        if (startIndex > sourceList.size()) {
            return new ArrayList<T>(0);
        }
        if (endIndex > sourceList.size()) {
            endIndex = sourceList.size();
        }
        return sourceList.subList(startIndex, endIndex);
    }

    public static String obj2String(Object obj) {
        if (obj == null) {
            return "<null>";
        }
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String simpleObj2String(Object obj) {
        if (obj == null) {
            return "<null>";
        }
        return obj.toString();
    }

    public static  String secureKeyword(String keyword){
        if(StringUtils.isBlank(keyword)){
            return keyword;
        }
        return keyword.replaceAll("%","\\\\%").replaceAll("_", "\\\\_");
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public CommonUtils(){
    }

    /**
     * 日期字符串转int，非法返回null
     * 
     * @param bizDate
     * @return
     */
    public static Integer date2IntPattern(String bizDate) {
        if (bizDate != null && NumberUtils.isDigits(bizDate)) {
            return NumberUtils.toInt(bizDate);
        }
        return null;
    }

    public static String int2DateStr(Integer bizDate) {
        if (bizDate == null) {
            return null;
        }
        return String.valueOf(bizDate);
    }

    public static Long bigDecimal2Long(BigDecimal d) {
        if (d == null) {
            return null;
        }
        return d.setScale(0, BigDecimal.ROUND_HALF_DOWN).longValue();
    }

    public static String Long2Str(Long d) {
        if (d == null) {
            return null;
        }
        return String.valueOf(d);
    }

    public static Integer bigDecimal2Integer(BigDecimal d) {
        if (d == null) {
            return null;
        }
        return d.setScale(0, BigDecimal.ROUND_HALF_DOWN).intValue();
    }

    /**
     * 分隔String， 转换成list数字
     * 只要大于0的合法数字
     * @param str
     * @param separator
     * @return
     */
    public static List<Long> strSplit2LongList(String str, char separator) {
        List<Long> idList = new ArrayList<>();
        if (StringUtils.isBlank(str)) {
            return idList;
        }
        String[] strIds = StringUtils.split(str, separator);
        if (ArrayUtils.isNotEmpty(strIds)) {
            for (String strId : strIds) {
                Long id = NumberUtils.toLong(strId);
                if (id != null && id > 0) {
                    idList.add(id);
                }
            }
        }
        return idList;
    }

    /**
     * Number转成字符串返回，用于DTO转VO时非空校验
     * 
     * @param num
     * @return
     */
    public static <T extends Number> String num2Str(T num) {
        if (num == null) {
            return null;
        }
        return num.toString();
    }

    /**
     * Number转成字符串返回，用于DTO转VO时非空校验<br/>
     * 1. 小数点保留几点<br/>
     * 2. 如果是0.0*格式，调整为0.0
     * 
     * @param num
     * @return
     */
    public static <T extends Number> String num2Str(T num, Integer maximumFractionDigits) {
        if (num == null) {
            return null;
        }
        // String s = num.toString();
        // String s1 = StringUtils.replace(s, ".", "");
        // String reg = "^0+$";
        // if (s1.matches(reg)) {
        // return "0.0";
        // }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(maximumFractionDigits);
        String cc = nf.format(num);
        return cc;
    }

    /**
     * d1 / d2, 正常逻辑d1<d2
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal calculateRate(Long d1, Long d2, int scale) {
        if (d1 == null || d1 == 0 || d2 == null || d2 == 0) {
            return BigDecimal.valueOf(0L);
        }
        if (d1 > d2) {
            return BigDecimal.valueOf(1L);
        }
        return BigDecimal.valueOf(d1).divide(BigDecimal.valueOf(d2), scale, BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal calculateRate(Long d1, Long d2) {
        return calculateRate(d1, d2, 5);
    }

    public static BigDecimal calculateAvg(Long d1, Long d2, int scale) {
        if (d1 == null || d1 == 0 || d2 == null || d2 == 0) {
            return BigDecimal.valueOf(0L);
        }
        return BigDecimal.valueOf(d1).divide(BigDecimal.valueOf(d2), 5, BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal calculateAvg(Long d1, Long d2) {
        return calculateAvg(d1, d2, 5);
    }

}
