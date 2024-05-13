package com.entity.vo;

import com.entity.HuiyiwenjianEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 会议文件管理
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 * @author 
 * @email
 * @date 2021-03-16
 */
@TableName("huiyiwenjian")
public class HuiyiwenjianVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 会议id
     */

    @TableField(value = "huiyi_id")
    private Integer huiyiId;


    /**
     * 文件名
     */

    @TableField(value = "huiyiwenjian_name")
    private String huiyiwenjianName;


    /**
     * 文件大致简介
     */

    @TableField(value = "huiyiwenjian_content")
    private String huiyiwenjianContent;


    /**
     * 会议文件
     */

    @TableField(value = "huiyiwenjian_file")
    private String huiyiwenjianFile;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：会议id
	 */
    public Integer getHuiyiId() {
        return huiyiId;
    }


    /**
	 * 获取：会议id
	 */

    public void setHuiyiId(Integer huiyiId) {
        this.huiyiId = huiyiId;
    }
    /**
	 * 设置：文件名
	 */
    public String getHuiyiwenjianName() {
        return huiyiwenjianName;
    }


    /**
	 * 获取：文件名
	 */

    public void setHuiyiwenjianName(String huiyiwenjianName) {
        this.huiyiwenjianName = huiyiwenjianName;
    }
    /**
	 * 设置：文件大致简介
	 */
    public String getHuiyiwenjianContent() {
        return huiyiwenjianContent;
    }


    /**
	 * 获取：文件大致简介
	 */

    public void setHuiyiwenjianContent(String huiyiwenjianContent) {
        this.huiyiwenjianContent = huiyiwenjianContent;
    }
    /**
	 * 设置：会议文件
	 */
    public String getHuiyiwenjianFile() {
        return huiyiwenjianFile;
    }


    /**
	 * 获取：会议文件
	 */

    public void setHuiyiwenjianFile(String huiyiwenjianFile) {
        this.huiyiwenjianFile = huiyiwenjianFile;
    }
    /**
	 * 设置：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}