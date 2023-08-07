package com.ray.austin.pending;

import com.ray.austin.common.domain.TaskInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author Skuray
 * @Date 2023/8/6 17:07
 * Task执行器 <p>
 * 1.丢弃消息 <p>
 * 2.屏蔽消息 <p>
 * 3.通用去重功能 <p>
 * 4.发送消息
 *
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{

    private TaskInfo taskInfo;

    @Override
    public void run() {
        // 1.丢弃消息

        // 2.屏蔽消息

        // 3.通用去重功能

        // 4.发送消息
    }
}
