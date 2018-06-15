package com.work.job;



/**
 * 远程调用的返回值
 * 
 * @author enji.lj
 * 
 * @param <T>
 */
public class ResultDO<T>  {

    /**
     * 操作是否成功
     */
    private final boolean success;

    /**
     * 返回值
     */
    private final T result;
    
    public static <T> ResultDO<T> getSuccessResult(T result){
        ResultDO<T> resultDO = new ResultDO<T>(result);
        
        return resultDO;
    }
    

    public ResultDO(T result) {
        this.success = true;
        this.result = result;
    }

    /**
     * 操作是否成功
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * 返回值
     */
    public T getResult(Class<T> inst) {
        return null; 
    }
  
}

