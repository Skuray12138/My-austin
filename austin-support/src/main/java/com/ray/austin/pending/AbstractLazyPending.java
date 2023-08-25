package com.ray.austin.pending;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.ray.austin.config.SupportThreadPoolConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author Skuray
 * @Date 2023/8/24 20:57
 * 延迟消费 阻塞队列-消费者和生产者实现 <p>
 * 通过while(true)无限循环拿取pendingParam.getQueue元素，并加入tasks，等tasks数量达到阈值或等待达到阈值后 <p>
 * 将tasks赋值给任务列表taskRef，自己重新初始化为空，任务列表被其他具体实现类进行真正的处理 (abstract) doHandle(taskRef)
 */
@Data
@Slf4j
public abstract class AbstractLazyPending<T> {

    /**
     * 子类构造方法必须初始化该参数
     */
    protected PendingParam<T> pendingParam;

    /**
     * 批量装载任务
     */
    private List<T> tasks = new ArrayList<>();

    /**
     * 上次执行时间
     */
    private Long lastHandleTime = System.currentTimeMillis();

    /**
     * 是否终止线程
     */
    private volatile Boolean stop = false;

    /**
     * 单线程消费 阻塞队列的数据  <p>
     * 等到积压到给定的 size和timeout就会给实际消费者进行处理
     */
    @PostConstruct
    public void initConsumePending(){
        // 生成线程池
        ExecutorService executorService = SupportThreadPoolConfig.getPendingSingleThreadPool();
        executorService.execute(() -> {
            while (true){
                try {
                    // poll(): 检索并删除此队列的头部，若需要元素变得可用，则等待指定的等待时间
                    // 等待batch触发执行时间后 队列中弹出的元素可用
                    T obj = pendingParam.getQueue().poll(pendingParam.getTimeThreshold(), TimeUnit.MILLISECONDS);
                    if (obj != null){
                        tasks.add(obj);
                    }
                    // 判断是否停止当前线程池
                    if (stop && CollUtil.isEmpty(tasks)){
                        executorService.shutdown();
                        break;
                    }
                    // 处理条件： 1.数量超载 2.时间超限
                    if (CollUtil.isNotEmpty(tasks) && dataReady()){
                        List<T> taskRef = tasks; // taskRef为任务列表
                        tasks = Lists.newArrayList();
                        lastHandleTime = System.currentTimeMillis(); //刷新上次执行时间
                        // 具体执行逻辑
                        // 调用消费者线程执行
                        pendingParam.getExecutorService().execute(() -> this.handle(taskRef));
                    }
                } catch (Exception e){
                    log.error("Pending#initConsumePending failed:{}", Throwables.getStackTraceAsString(e));
                }
            }
        });

    }

    /**
     * 消费 阻塞队列中的元素
     * @param taskRef
     */
    private void handle(List<T> taskRef) {
        if (taskRef.isEmpty()){
            return;
        }
        try {
            doHandle(taskRef);
        } catch (Exception e){
            log.error("Pending#handle failed:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 处理阻塞队列的元素 真正方法
     * @param taskRef
     */
    public abstract void doHandle(List<T> taskRef);

    /**
     * 判断数据是否准备好   <p>
     * 1. 数量超限   <p>
     * 2. 时间超限
     * @return
     */
    private boolean dataReady() {
        return (tasks.size() >= pendingParam.getNumThreshold()) ||
                (System.currentTimeMillis() - lastHandleTime >= pendingParam.getTimeThreshold());
    }

    /**
     * 将元素放入阻塞队列中
     * @param t
     */
    public void pending(T t){
        try {
            pendingParam.getQueue().put(t);
        } catch (InterruptedException e){
            log.error("Pending#pending error:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
