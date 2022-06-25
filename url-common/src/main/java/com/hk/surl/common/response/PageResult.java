package com.hk.surl.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : HK意境
 * @ClassName : PageResult
 * @date : 2022/6/24 20:53
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class PageResult implements Serializable {

    // 本页多少条
    private Integer perPageSize ;
    // 当前是第几页
    private Integer pageNumber ;
    // 是否有上一页
    private Boolean hasPreviousPage ;
    // 是否还有下一页
    private Boolean hasNextPage ;
    // 一页多少条
    private Integer pageSize ;
    // 共多少页
    private Integer totalPages ;
    // 一共多少条
    private Long totalSize ;

}
