package com.ray.austin.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/8/21 10:26
 * 上传后成功返回素材的id
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseVo {

    private String id;
}
