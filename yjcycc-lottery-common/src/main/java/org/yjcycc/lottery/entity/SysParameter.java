package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 系统参数(SysParameter)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("SysParameter")
public class SysParameter extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 315653510588907186L;
    // 代码
    private String code;
    // 值
    private String value;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}