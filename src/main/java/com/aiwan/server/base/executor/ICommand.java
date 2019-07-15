package com.aiwan.server.base.executor;

/**
 * 任务接口
 *
 * @author
 */
public interface ICommand {

    /**
     * 获取command主键
     */
    Object getKey();

    /**
     * 执行任务逻辑
     */
    void active();

    /**
     * 获取执行线程的线程编号
     *
     * @param poolSize 线程数
     */
    int modIndex(int poolSize);

    /**
     * 是否已经被取消
     */
    boolean isCanceled();

    /**
     * 取消Command
     */
    void cancel();

    /**
     * 刷新状态
     */
    void refreshState();

    /**
     * 获取任务名
     */
    String getTaskName();

}
