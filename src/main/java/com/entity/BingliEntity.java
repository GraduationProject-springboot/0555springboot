package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 档案
 *
 * @author 
 * @email
 */
@TableName("bingli")
public class BingliEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public BingliEntity() {

	}

	public BingliEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 档案编号
     */
    @ColumnInfo(comment="档案编号",type="varchar(200)")
    @TableField(value = "bingli_uuid_number")

    private String bingliUuidNumber;


    /**
     * 用户
     */
    @ColumnInfo(comment="用户",type="int(11)")
    @TableField(value = "yonghu_id")

    private Integer yonghuId;


    /**
     * 医生
     */
    @ColumnInfo(comment="医生",type="int(11)")
    @TableField(value = "yisheng_id")

    private Integer yishengId;


    /**
     * 检查时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="检查时间",type="timestamp")
    @TableField(value = "jiancha_time")

    private Date jianchaTime;


    /**
     * 病人自述
     */
    @ColumnInfo(comment="病人自述",type="text")
    @TableField(value = "bingrenzishu_content")

    private String bingrenzishuContent;


    /**
     * 检查结果
     */
    @ColumnInfo(comment="检查结果",type="text")
    @TableField(value = "jianchajieguo_content")

    private String jianchajieguoContent;


    /**
     * 医生开方
     */
    @ColumnInfo(comment="医生开方",type="text")
    @TableField(value = "yishengkaifang_content")

    private String yishengkaifangContent;


    /**
     * 医生建议
     */
    @ColumnInfo(comment="医生建议",type="text")
    @TableField(value = "yishengjianyi_content")

    private String yishengjianyiContent;


    /**
     * 医保信息
     */
    @ColumnInfo(comment="医保信息",type="varchar(200)")
    @TableField(value = "bingli_yibao")

    private String bingliYibao;


    /**
     * 花费金额
     */
    @ColumnInfo(comment="花费金额",type="decimal(10,2)")
    @TableField(value = "huafeijine")

    private Double huafeijine;


    /**
     * 下次就诊时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="下次就诊时间",type="timestamp")
    @TableField(value = "xiacijiuzhen_time")

    private Date xiacijiuzhenTime;


    /**
     * 年龄
     */
    @ColumnInfo(comment="年龄",type="int(11)")
    @TableField(value = "bingli_nianling")

    private Integer bingliNianling;


    /**
     * 身体状况
     */
    @ColumnInfo(comment="身体状况",type="int(11)")
    @TableField(value = "bingli_types")

    private Integer bingliTypes;


    /**
     * 录入时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="录入时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间  listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：档案编号
	 */
    public String getBingliUuidNumber() {
        return bingliUuidNumber;
    }
    /**
	 * 设置：档案编号
	 */

    public void setBingliUuidNumber(String bingliUuidNumber) {
        this.bingliUuidNumber = bingliUuidNumber;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }
    /**
	 * 设置：用户
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：医生
	 */
    public Integer getYishengId() {
        return yishengId;
    }
    /**
	 * 设置：医生
	 */

    public void setYishengId(Integer yishengId) {
        this.yishengId = yishengId;
    }
    /**
	 * 获取：检查时间
	 */
    public Date getJianchaTime() {
        return jianchaTime;
    }
    /**
	 * 设置：检查时间
	 */

    public void setJianchaTime(Date jianchaTime) {
        this.jianchaTime = jianchaTime;
    }
    /**
	 * 获取：病人自述
	 */
    public String getBingrenzishuContent() {
        return bingrenzishuContent;
    }
    /**
	 * 设置：病人自述
	 */

    public void setBingrenzishuContent(String bingrenzishuContent) {
        this.bingrenzishuContent = bingrenzishuContent;
    }
    /**
	 * 获取：检查结果
	 */
    public String getJianchajieguoContent() {
        return jianchajieguoContent;
    }
    /**
	 * 设置：检查结果
	 */

    public void setJianchajieguoContent(String jianchajieguoContent) {
        this.jianchajieguoContent = jianchajieguoContent;
    }
    /**
	 * 获取：医生开方
	 */
    public String getYishengkaifangContent() {
        return yishengkaifangContent;
    }
    /**
	 * 设置：医生开方
	 */

    public void setYishengkaifangContent(String yishengkaifangContent) {
        this.yishengkaifangContent = yishengkaifangContent;
    }
    /**
	 * 获取：医生建议
	 */
    public String getYishengjianyiContent() {
        return yishengjianyiContent;
    }
    /**
	 * 设置：医生建议
	 */

    public void setYishengjianyiContent(String yishengjianyiContent) {
        this.yishengjianyiContent = yishengjianyiContent;
    }
    /**
	 * 获取：医保信息
	 */
    public String getBingliYibao() {
        return bingliYibao;
    }
    /**
	 * 设置：医保信息
	 */

    public void setBingliYibao(String bingliYibao) {
        this.bingliYibao = bingliYibao;
    }
    /**
	 * 获取：花费金额
	 */
    public Double getHuafeijine() {
        return huafeijine;
    }
    /**
	 * 设置：花费金额
	 */

    public void setHuafeijine(Double huafeijine) {
        this.huafeijine = huafeijine;
    }
    /**
	 * 获取：下次就诊时间
	 */
    public Date getXiacijiuzhenTime() {
        return xiacijiuzhenTime;
    }
    /**
	 * 设置：下次就诊时间
	 */

    public void setXiacijiuzhenTime(Date xiacijiuzhenTime) {
        this.xiacijiuzhenTime = xiacijiuzhenTime;
    }
    /**
	 * 获取：年龄
	 */
    public Integer getBingliNianling() {
        return bingliNianling;
    }
    /**
	 * 设置：年龄
	 */

    public void setBingliNianling(Integer bingliNianling) {
        this.bingliNianling = bingliNianling;
    }
    /**
	 * 获取：身体状况
	 */
    public Integer getBingliTypes() {
        return bingliTypes;
    }
    /**
	 * 设置：身体状况
	 */

    public void setBingliTypes(Integer bingliTypes) {
        this.bingliTypes = bingliTypes;
    }
    /**
	 * 获取：录入时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：录入时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间  listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间  listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Bingli{" +
            ", id=" + id +
            ", bingliUuidNumber=" + bingliUuidNumber +
            ", yonghuId=" + yonghuId +
            ", yishengId=" + yishengId +
            ", jianchaTime=" + DateUtil.convertString(jianchaTime,"yyyy-MM-dd") +
            ", bingrenzishuContent=" + bingrenzishuContent +
            ", jianchajieguoContent=" + jianchajieguoContent +
            ", yishengkaifangContent=" + yishengkaifangContent +
            ", yishengjianyiContent=" + yishengjianyiContent +
            ", bingliYibao=" + bingliYibao +
            ", huafeijine=" + huafeijine +
            ", xiacijiuzhenTime=" + DateUtil.convertString(xiacijiuzhenTime,"yyyy-MM-dd") +
            ", bingliNianling=" + bingliNianling +
            ", bingliTypes=" + bingliTypes +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
