package com.work.job.sffun;

import com.work.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author xulujun
 * @date 2019/04/26
 */
public class IpConvertTest extends AbstractTest {


  @Test
  public void test() throws Exception {
    long value = 192L << 24;
    p(value);
    long val = ip2long("192.168.1.100");
    String ip = long2ip(val);
    p(val + "<==>" + ip);
  }

  public long ip2long(String ip) throws Exception {
    if (StringUtils.isBlank(ip)) {
      return -1;
    }
    String[] arr = ip.split("\\.");
    return (Long.parseLong(arr[0]) << 24)
        + (Long.parseLong(arr[1]) << 16)
        + (Long.parseLong(arr[2]) << 8)
        + (Long.parseLong(arr[3]));

  }


  public String long2ip(long ipValue) throws Exception {

    return ((ipValue >> 24) & 0xFF)
        + "." + ((ipValue >> 16) & 0xFF)
        + "." + ((ipValue >> 8) & 0xFF)
        + "." + (ipValue & 0xFF);

  }


}
