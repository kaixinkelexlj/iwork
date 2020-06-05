package com.work.job;

/**
 * @author xulujun 2020/06/04.
 */
public class TestOOM {

  public static void main(String[] args) throws Exception {
    Thread.sleep(3000);
    Thread thread = new Thread(() -> {
      int count = 1;
      while (true) {
        /*try {
          if (count++ < 10) {
            Thread.sleep(1000);
            throw new OutOfMemoryError();
          } else {
            break;
          }
        } catch (Error | InterruptedException error) {
          error.printStackTrace();
          // throw error;
        }*/
        count++;
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if(count % 10 == 0){
          System.out.println("new 200M data");
          LargeObject.newMBObject(200);
        }else{
          System.out.println("new 10M data");
          LargeObject.newMBObject(10);
        }
      }
    });
    thread.start();
    thread.join();
  }

  public static class LargeObject {

    private byte[] data;

    public static LargeObject newMBObject(int size) {
      return new LargeObject(size * 1024 * 1024);
    }

    public LargeObject(int size) {
      this.data = new byte[size];
    }

    public byte[] getData() {
      return data;
    }

    public void setData(byte[] data) {
      this.data = data;
    }
  }


}
