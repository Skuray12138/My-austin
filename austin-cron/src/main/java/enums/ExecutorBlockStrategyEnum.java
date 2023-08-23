package enums;

/**
 * @Author Skuray
 * @Date 2023/8/22 14:28
 * 执行阻塞队列
 */

public enum ExecutorBlockStrategyEnum {

    /**
     * 单机串行
     */
    SERIAL_EXECUTION,

    /**
     * 丢弃后续调度
     */
    DISCARD_LATER,

    /**
     * 覆盖之前调度
     */
    COVER_EARLY;

    ExecutorBlockStrategyEnum() {
    }
}
