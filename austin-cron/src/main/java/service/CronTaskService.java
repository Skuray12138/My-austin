package service;

import com.ray.austin.common.vo.BasicResultVO;
import entity.XxlJobGroup;
import entity.XxlJobInfo;

/**
 * @Author Skuray
 * @Date 2023/8/22 14:30
 * 定时任务服务
 */

public interface CronTaskService {

    /**
     * 新增/修改 定时任务
     * @param xxlJobInfo
     * @return
     */
    BasicResultVO saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 删除定时任务
     * @param taskId
     * @return
     */
    BasicResultVO deleteCronTask(Integer taskId);

    /**
     * 启动定时任务
     * @param taskId
     * @return
     */
    BasicResultVO startCronTask(Integer taskId);

    /**
     * 暂停定时任务
     * @param taskId
     * @return
     */
    BasicResultVO stopCronTask(Integer taskId);

    /**
     * 得到执行器id
     * @param appName
     * @param title
     * @return
     */
    BasicResultVO getGroupId(String appName, String title);

    /**
     * 创建执行器
     * @param xxlJobGroup
     * @return
     */
    BasicResultVO createGroup(XxlJobGroup xxlJobGroup);

}
