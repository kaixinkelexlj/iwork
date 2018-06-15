/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.serializertest;

import java.io.Serializable;

/**
 * @author lujun.xlj
 * @date 2017/9/1
 */
public class SerializableObject implements Serializable {

    private static final long serialVersionUID = -157829194704117362L;

    private Long id;

    private NonSerializableObject nonSerializableObject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NonSerializableObject getNonSerializableObject() {
        return nonSerializableObject;
    }

    public void setNonSerializableObject(NonSerializableObject nonSerializableObject) {
        this.nonSerializableObject = nonSerializableObject;
    }
}
