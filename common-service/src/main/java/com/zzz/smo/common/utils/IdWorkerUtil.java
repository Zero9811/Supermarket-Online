package com.zzz.smo.common.utils;


/**
 * @Author: Sean
 * @Date: 2019/1/26 15:09
 */
public class IdWorkerUtil {

    private final long startTime = 1545667200000L;

    private long workerId;
    private long datacenterId;
    private long sequence;
    //各字段位数
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long sequenceBits = 12L;
    //各字段最大值
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long maxSequence = -1L ^ (-1L << sequenceBits);
    //向左偏移量
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = workerIdBits+sequenceBits;
    private long timeStampShift = datacenterIdBits+workerIdBits+sequenceBits;

    private long lastTimeStamp = -1L;

    public IdWorkerUtil(long workerId, long datacenterId, long sequence) {
        if(workerId > maxWorkerId || workerId <0)
            throw new IllegalArgumentException(String.format("workerId的值要在%d和0之间",maxWorkerId));
        if (datacenterId > maxDatacenterId || datacenterId < 0)
            throw new IllegalArgumentException(String.format("datacenterId的值要在%d和0之间",maxDatacenterId));
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public synchronized long newId(){
        long timeStamp = System.currentTimeMillis();
        if (timeStamp<lastTimeStamp)
            throw new RuntimeException("时钟回退了，请检查当前时钟");
        if (timeStamp == lastTimeStamp){
            sequence = (sequence+1) & maxSequence;
            //当序列取完时，只能等到下一秒
            if (sequence == 0)
                timeStamp = nextMillisecond(lastTimeStamp);
        }
        else sequence = 0;
        lastTimeStamp = timeStamp;
        return ((timeStamp-startTime) << timeStampShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    private long nextMillisecond(long lastTimeStamp) {
        long timeStamp = System.currentTimeMillis();
        while (timeStamp <= lastTimeStamp)
            timeStamp = System.currentTimeMillis();
        return timeStamp;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
}
