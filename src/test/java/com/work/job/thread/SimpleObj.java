package com.work.job.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleObj {
    private String lastName;
    private Integer nameCount;
    private List<String> nameList;
    private AtomicBoolean in;

    public SimpleObj() {
        lastName = null;
        nameCount = 0;
        nameList = new ArrayList<String>();
        in = new AtomicBoolean(false);
    }

    public void addName(String name) {
        System.out.println(Thread.currentThread().getName() + " addName..");
        in.set(true);
        synchronized (this) {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " sleep over..");
            } catch (InterruptedException ex) {
                System.out.println("Interrupted:" + ex.getMessage());
            }
            lastName = name;
            nameCount++;
        }
        nameList.add(name);
    }

    public String getLastName() {
        /*while (!in.get()) {
        }*/
        return lastName;
    }

    public List<String> getNameList() {
        while (!in.get()) {
        }
        return nameList;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
