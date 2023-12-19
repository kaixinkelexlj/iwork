package com.fun.zk;

/*import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;*/

/**
 * @author xulujun
 * @date 2018/07/25
 */
public class ZkTest {

  /*private static final String HOSTS = "10.95.178.36:2181,10.94.130.128:2181,10.95.176.252:2181";
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
  private static final String ROOT_PATH = "/hosts/dps/dps-notify-center";

  private CuratorFramework cf;

  private CuratorWatcher watcher;

  @Before
  public void setUp() throws Exception{
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 10);
    cf = CuratorFrameworkFactory.builder()
        .connectString(HOSTS)
        .sessionTimeoutMs(60000)
        .connectionTimeoutMs(15000)
        .retryPolicy(retryPolicy)
        .build();

    watcher = event -> {
      //System.out.println("watching==>" + JSON.toJSONString(event));
    };

    cf.start();
  }

  @After
  public void destroy() throws Exception{
    CloseableUtils.closeQuietly(cf);
  }

  @Test
  public void test() throws Exception {

    if (cf.checkExists().forPath(ROOT_PATH) != null) {
      cf.delete().deletingChildrenIfNeeded().forPath(ROOT_PATH);
    }

    //backgroupd listener
    *//*cf.getCuratorListenable().addListener(new CuratorListener() {
      @Override
      public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        System.out.println("listen==>" + JSON.toJSONString(event));
      }
    });*//*

    cf.create().creatingParentContainersIfNeeded()
        .withMode(CreateMode.PERSISTENT)
        .forPath(ROOT_PATH,
            ZkData.of("dps-notify-center-parent"));

    watchChildren(cf, ROOT_PATH);
    //cf.checkExists().usingWatcher(watcher).forPath(ROOT_PATH + "/host1");
    cf.create().withMode(CreateMode.EPHEMERAL)
        .forPath(ROOT_PATH + "/host1", ZkData.of("ip1"));
    //cf.checkExists().usingWatcher(watcher).forPath(ROOT_PATH + "/host2");
    cf.create().withMode(CreateMode.EPHEMERAL)
        .forPath(ROOT_PATH + "/host2", ZkData.of("ip2"));
    //cf.checkExists().usingWatcher(watcher).forPath(ROOT_PATH + "/host3");
    cf.create().withMode(CreateMode.EPHEMERAL)
        .forPath(ROOT_PATH + "/host3", ZkData.of("ip3"));

    //watchChildren(cf, ROOT_PATH);
    //System.out.println(ZkData.of(cf.getData().forPath(ROOT_PATH)));
    *//*
    List<String> childList = cf.getChildren().forPath(ROOT_PATH);
    Optional.ofNullable(childList).orElseGet(ArrayList::new)
        .forEach(System.out::println);

    Stat stat = cf.setData().forPath(ROOT_PATH + "/host3", ZkData.of("ip3-new"));
    //System.out.println(JSON.toJSONString(stat));

    cf.checkExists().usingWatcher(watcher).forPath(ROOT_PATH + "/host3");
    cf.delete().forPath(ROOT_PATH + "/host3");
    stat = cf.checkExists().forPath(ROOT_PATH + "/host3");
    if (stat == null) {
      System.out.println("delete success");
    } else {
      System.err.println("delete fail!!" + "==> " + JSON.toJSONString(stat));
    }
    childList = cf.getChildren().forPath(ROOT_PATH);
    Optional.ofNullable(childList).orElseGet(ArrayList::new)
        .forEach(System.out::println);*//*

    Thread.sleep(3000);

  }


  private void watchChildren(CuratorFramework cf, String path) throws Exception {
    try {
      cf.getChildren().usingWatcher(new CuratorWatcher() {
        @Override
        public void process(WatchedEvent event) throws Exception {
          System.out.println("watching children==>" + JSON.toJSONString(event));
          watchChildren(cf, path);
        }
      }).forPath(path);
    } catch (NoNodeException ex) {
      System.err.println("no node for[" + path + "]");
    }
  }


  public static class ZkData {

    private static final byte[] EMPTY = new byte[0];

    private byte[] data;

    public static byte[] of(String data) {
      return Optional.ofNullable(data).map(d -> d.getBytes(DEFAULT_CHARSET)).orElse(EMPTY);
    }

    public static String of(byte[] data) {
      return data == null || data.length == 0 ? "" : new String(data, DEFAULT_CHARSET);
    }

  }*/


}
