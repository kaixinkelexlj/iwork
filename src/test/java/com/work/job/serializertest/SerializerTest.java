/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.serializertest;

import org.junit.Test;

import java.io.*;

/**
 * @author lujun.xlj
 * @date 2017/7/13
 */
public class SerializerTest {

    @Test
    public void testSimpleObject() throws Exception {

        ObjectSerializer<SimpleObject> serializer = new ObjectSerializer<>();

        SimpleObject obj = new SimpleObject();
        obj.setId(10010L);
        obj.setName("行默");
        obj.setAge(18);
        byte[] bytes = serializer.serialize(obj);

        SimpleObject o1 = serializer.deserializer(bytes);
        System.out.println(o1);
        SimpleObject o2 = serializer.deserializer(bytes);
        System.out.println(o2);

        System.out.println(o1 == o2);
    }

    @Test
    public void testSingleton() throws Exception {

    }

    @Test
    public void testSpecial() throws Exception {

        ObjectSerializer<SerializableObject> serializer = new ObjectSerializer<>();
        NonSerializableObject nobj = new NonSerializableObject();
        nobj.setId(10010L);
        SerializableObject object = new SerializableObject();
        object.setId(100L);
        object.setNonSerializableObject(nobj);

        byte[] bytes = serializer.serialize(object);

        SerializableObject objectNew = serializer.deserializer(bytes);

        System.out.println(objectNew.getNonSerializableObject().getId());


    }

    static class ObjectSerializer<T extends Serializable> {

        public byte[] serialize(T obj) throws Exception {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);) {
                objectOutputStream.writeObject(obj);
                return bos.toByteArray();
            }
        }

        public T deserializer(byte[] bytes) throws Exception {
            try (ByteArrayInputStream is = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(is);) {
                return (T) ois.readObject();
            }
        }

    }

}
