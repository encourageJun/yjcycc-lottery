package org.yjcycc.lottery.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.yjcycc.tools.common.entity.BaseEntity;

/**
 * 数据字典(SysDict)实体类
 *
 * @author makejava
 * @since 2019-08-16 18:40:21
 */
@Alias("SysDict")
public class SysDict extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -32941685699889082L;
    // 标签/名称
    private String label;
    // 值
    private Integer value;
    // 编码
    private String code;
    // 字典类型
    private String type;
    // 描述
    private String description;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}