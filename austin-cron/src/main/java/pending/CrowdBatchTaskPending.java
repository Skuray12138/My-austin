package pending;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.ray.austin.pending.AbstractLazyPending;
import com.ray.austin.pending.PendingParam;
import com.ray.austin.service.api.domain.BatchSendRequest;
import com.ray.austin.service.api.domain.MessageParam;
import com.ray.austin.service.api.enmus.BusinessCode;
import com.ray.austin.service.api.service.SendService;
import config.CronAsyncThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vo.CrowdInfoVo;
import xxl.constants.PendingConstant;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Skuray
 * @Date 2023/8/24 20:51
 * 延迟批量处理人群信息  <p>
 * 调用batch发送接口 进行消息推送  <p>
 * 从这里进入batchSend服务
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrowdBatchTaskPending extends AbstractLazyPending<CrowdInfoVo> {

    @Autowired
    private SendService sendService;

    public CrowdBatchTaskPending(){
        PendingParam<CrowdInfoVo> pendingParam = new PendingParam<>();
        pendingParam.setQueue(new LinkedBlockingQueue<>(PendingConstant.QUEUE_SIZE))
                .setTimeThreshold(PendingConstant.TIME_THRESHOLD)
                .setNumThreshold(PendingConstant.NUM_THRESHOLD)
                .setExecutorService(CronAsyncThreadPoolConfig.getConsumePendingThreadPool());
        this.pendingParam = pendingParam;
    }

    /**
     * 实际具体处理阻塞队列中的元素
     * @param crowdInfoVos
     */
    @Override
    public void doHandle(List<CrowdInfoVo> crowdInfoVos) {
        // 1.如果参数相同，组装成同一个MessageParam发送
        Map<Map<String, String>, String> paramMap = MapUtil.newHashMap();
        for (CrowdInfoVo crowdInfoVo : crowdInfoVos){
            String receiver = crowdInfoVo.getReceiver(); // 拿到接收者
            Map<String, String> vars = crowdInfoVo.getParams(); // 参数信息
            if (Objects.isNull(paramMap.get(vars))){ //若没有接收者,则将vars与接收者对应起来
                paramMap.put(vars, receiver);
            } else {
                // 若已有接收者，则将多个receiver拼接起来与vars对应
                // 将集合 以逗号拼接到一起形成新的字符串
                String newReceiver = StringUtils.join(new String[]{
                        paramMap.get(vars), receiver}, StrUtil.COMMA);
                paramMap.put(vars, newReceiver);
            }
        }
        // 2.组装参数
        List<MessageParam> messageParams = Lists.newArrayList();

        for (Map.Entry<Map<String, String>, String> entry : paramMap.entrySet()) {
            MessageParam messageParam = MessageParam.builder()
                    .receiver(entry.getValue()) //接收者
                    .variables(entry.getKey())  //可替换参数信息
                    .build();
            messageParams.add(messageParam);
        }
        // 3.调用批量发送接口 发送消息
        BatchSendRequest batchSendRequest = BatchSendRequest.builder()
                .code(BusinessCode.COMMON_SEND.getCode())
                .messageParamList(messageParams) //消息相关参数
                .messageTemplateId(CollUtil.getFirst(crowdInfoVos.iterator()).getMessageTemplateId()) //阻塞队列中第一个元素的模板id
                .build();
        sendService.batchSend(batchSendRequest);

    }
}
