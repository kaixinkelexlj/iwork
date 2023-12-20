/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.work.job.mem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;

import com.work.job.MemUtils;

/**
 * @author lujun.xlj on 2017/3/8.
 */
public class MemTest {


    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println(MemUtils.total(MemUtils.Unit.MB));
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(MemUtils.usedM());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        /*List<TestObj> list = null;
        while (input.hasNext()) {
            cmd = input.next();
            if ("clean".equals(cmd)) {
                list.clear();
                System.gc();
            }
            if ("init".equals(cmd)) {
                list = payLoad(500000);
            }
            if ("exit".equals(cmd)) {
                break;
            }
        }*/

        String cmd = null;
        List<Integer> list = new ArrayList<>(0);
        while (input.hasNext()) {
            cmd = input.next();
            if ("add".equalsIgnoreCase(cmd)) {
                System.out.println("add start");
                for (int i = 0; i < 5000000; i++) {
                    list.add(RandomUtils.nextInt());
                }
                System.out.println("add end");
            }
            Thread.sleep(2000L);
            if ("exit".equals(cmd)) {
                break;
            }
        }

        System.exit(0);

    }

    public static List<TestObj> payLoad(int size) {
        List<TestObj> list = new ArrayList<TestObj>(size);
        for (int i = 0; i < size; i++) {
            list.add(new TestObj());
        }
        return list;
    }

    public static class TestObj implements Serializable {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -5452885371548365216L;

        /**
         * 主键
         */
        private Long id;

        /**
         * 影院标识
         */
        private Long cinemaId;

        /**
         * 影院名称
         */
        private String cinemaName;

        /**
         * 影院标准8位码
         */
        private String standardId;

        /**
         * 城市标识
         */
        private Long cityId;

        /**
         * 城市名称
         */
        private String cityName;

        /**
         * 省份
         */
        private Long provinceId;

        /**
         * 省份名称
         */
        private String provinceName;

        /**
         * 区域id
         */
        private Long districtId;

        /**
         * 区域名称
         */
        private String districtName;

        /**
         * 影管公司code
         */
        private String companyCode;

        /**
         * 影管公司名称
         */
        private String companyName;

        /**
         * 专资办票房，单位分
         */
        private Long zzbBoxOffice;

        /**
         * 专资办人次
         */
        private Integer zzbTicketTotal;

        /**
         * 淘票票票房，单位分
         */
        private Long tppBoxOffice;

        /**
         * 淘票票出票（张）
         */
        private Integer tppTicketTotal;

        /**
         * 淘票票票房占比
         */
        private Double tppBoxOfficePercent;

        /**
         * 淘票票人次占比
         */
        private Double tppTicketPercent;

        /**
         * 活动票总数
         */
        private Integer actTicketTotal;

        /**
         * 补贴金额,单位分
         */
        private Long subsidyTotal;

        /**
         * 活动票占比
         */
        private Double actTicketPercent;

        /**
         * 淘票票排名
         */
        private Integer tppRank;

        /**
         * 专资办排名
         */
        private Integer zzbRank;

        /**
         * 淘票票总购票金额/总活动补贴金额
         */
        private Double roi;

        /**
         * 单张票补，分,淘票票总活动补贴金额/总活动补贴票数
         */
        private Long subsidySingle;

        /**
         * 单票成本,淘票票总活动补贴金额/总出票数
         */
        private Long costSingle;

        /**
         * 专资办结算均价
         */
        private Long zzbSettlementAvg;

        /**
         * 淘票票结算均价
         */
        private Long tppSettlementAvg;

        /**
         * 专资办结算均价-淘票票结算均价,单位分
         */
        private Long settlementAvgDiff;

        /**
         * 与大盘份额差值,当前影院或城市淘票票人次占比减去淘票票全国人次占比
         */
        private Double ticketPercentDiff;

        /**
         * 份额环比，本期与大盘份额差值-上期与大盘份额差值
         */
        private Double diffThanBefore;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getCinemaId() {
            return cinemaId;
        }

        public void setCinemaId(Long cinemaId) {
            this.cinemaId = cinemaId;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public String getStandardId() {
            return standardId;
        }

        public void setStandardId(String standardId) {
            this.standardId = standardId;
        }

        public Long getZzbBoxOffice() {
            return zzbBoxOffice;
        }

        public void setZzbBoxOffice(Long zzbBoxOffice) {
            this.zzbBoxOffice = zzbBoxOffice;
        }

        public Integer getZzbTicketTotal() {
            return zzbTicketTotal;
        }

        public void setZzbTicketTotal(Integer zzbTicketTotal) {
            this.zzbTicketTotal = zzbTicketTotal;
        }

        public Long getTppBoxOffice() {
            return tppBoxOffice;
        }

        public void setTppBoxOffice(Long tppBoxOffice) {
            this.tppBoxOffice = tppBoxOffice;
        }

        public Integer getTppTicketTotal() {
            return tppTicketTotal;
        }

        public void setTppTicketTotal(Integer tppTicketTotal) {
            this.tppTicketTotal = tppTicketTotal;
        }

        public Double getTppBoxOfficePercent() {
            return tppBoxOfficePercent;
        }

        public void setTppBoxOfficePercent(Double tppBoxOfficePercent) {
            this.tppBoxOfficePercent = tppBoxOfficePercent;
        }

        public Double getTppTicketPercent() {
            return tppTicketPercent;
        }

        public void setTppTicketPercent(Double tppTicketPercent) {
            this.tppTicketPercent = tppTicketPercent;
        }

        public Integer getActTicketTotal() {
            return actTicketTotal;
        }

        public void setActTicketTotal(Integer actTicketTotal) {
            this.actTicketTotal = actTicketTotal;
        }

        public Long getSubsidyTotal() {
            return subsidyTotal;
        }

        public void setSubsidyTotal(Long subsidyTotal) {
            this.subsidyTotal = subsidyTotal;
        }

        public Double getActTicketPercent() {
            return actTicketPercent;
        }

        public void setActTicketPercent(Double actTicketPercent) {
            this.actTicketPercent = actTicketPercent;
        }

        public Integer getTppRank() {
            return tppRank;
        }

        public void setTppRank(Integer tppRank) {
            this.tppRank = tppRank;
        }

        public Integer getZzbRank() {
            return zzbRank;
        }

        public void setZzbRank(Integer zzbRank) {
            this.zzbRank = zzbRank;
        }

        public Double getRoi() {
            return roi;
        }

        public void setRoi(Double roi) {
            this.roi = roi;
        }

        public Long getSubsidySingle() {
            return subsidySingle;
        }

        public void setSubsidySingle(Long subsidySingle) {
            this.subsidySingle = subsidySingle;
        }

        public Long getCostSingle() {
            return costSingle;
        }

        public void setCostSingle(Long costSingle) {
            this.costSingle = costSingle;
        }

        public Long getZzbSettlementAvg() {
            return zzbSettlementAvg;
        }

        public void setZzbSettlementAvg(Long zzbSettlementAvg) {
            this.zzbSettlementAvg = zzbSettlementAvg;
        }

        public Long getTppSettlementAvg() {
            return tppSettlementAvg;
        }

        public void setTppSettlementAvg(Long tppSettlementAvg) {
            this.tppSettlementAvg = tppSettlementAvg;
        }

        public Long getSettlementAvgDiff() {
            return settlementAvgDiff;
        }

        public void setSettlementAvgDiff(Long settlementAvgDiff) {
            this.settlementAvgDiff = settlementAvgDiff;
        }

        public Double getTicketPercentDiff() {
            return ticketPercentDiff;
        }

        public void setTicketPercentDiff(Double ticketPercentDiff) {
            this.ticketPercentDiff = ticketPercentDiff;
        }

        public Double getDiffThanBefore() {
            return diffThanBefore;
        }

        public void setDiffThanBefore(Double diffThanBefore) {
            this.diffThanBefore = diffThanBefore;
        }

        public Long getCityId() {
            return cityId;
        }

        public void setCityId(Long cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Long provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public Long getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Long districtId) {
            this.districtId = districtId;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

    }

}
