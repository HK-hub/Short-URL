package com.hk.surl.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 滴滴TinyId 算法，分段发号器算法
 * @TableName tb_tiny_id
 */
@Data
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TinyId other = (TinyId) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBizType() == null ? other.getBizType() == null : this.getBizType().equals(other.getBizType()))
            && (this.getBeginId() == null ? other.getBeginId() == null : this.getBeginId().equals(other.getBeginId()))
            && (this.getMaxId() == null ? other.getMaxId() == null : this.getMaxId().equals(other.getMaxId()))
            && (this.getStep() == null ? other.getStep() == null : this.getStep().equals(other.getStep()))
            && (this.getDelta() == null ? other.getDelta() == null : this.getDelta().equals(other.getDelta()))
            && (this.getRemainder() == null ? other.getRemainder() == null : this.getRemainder().equals(other.getRemainder()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBizType() == null) ? 0 : getBizType().hashCode());
        result = prime * result + ((getBeginId() == null) ? 0 : getBeginId().hashCode());
        result = prime * result + ((getMaxId() == null) ? 0 : getMaxId().hashCode());
        result = prime * result + ((getStep() == null) ? 0 : getStep().hashCode());
        result = prime * result + ((getDelta() == null) ? 0 : getDelta().hashCode());
        result = prime * result + ((getRemainder() == null) ? 0 : getRemainder().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bizType=").append(bizType);
        sb.append(", beginId=").append(beginId);
        sb.append(", maxId=").append(maxId);
        sb.append(", step=").append(step);
        sb.append(", delta=").append(delta);
        sb.append(", remainder=").append(remainder);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", version=").append(version);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}