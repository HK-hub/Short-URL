package com.hk.surl.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;

/**
 * 滴滴TinyId 算法，分段发号器算法
 * @TableName tb_tiny_id
 */
@Data
@ToString
public class TinyId implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

    /**
     * 业务类型标识，唯一
     */
    private String bizType;

    /**
     * 开始id,仅记录初始值，初始时，值应该等于max_id
     */
    private Long beginId;

    /**
     * 当前最大id, 发号器目前发到的ID
     */
    private Long maxId;

    /**
     * 步长，号段长度
     */
    private Integer step;

    /**
     * 每次id 增量
     */
    private Integer delta;

    /**
     * 余量
     */
    private Integer remainder;

    /** 剩余率 **/
    private Double remainRate ;

    /**
     * 修改时间
     */
    private LocalDateTime createTime;

    /**
     * 跟新时间
     */
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    private Long version;

}