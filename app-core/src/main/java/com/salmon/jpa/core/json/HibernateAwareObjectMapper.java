package com.salmon.jpa.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -8925948208400334478L;

	public HibernateAwareObjectMapper() {
        Hibernate5Module hm = new Hibernate5Module();
        this.registerModule(hm);
        //去掉默认的时间戳格式
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        //Include.Include.ALWAYS 默认
        //Include.NON_DEFAULT 属性为默认值不序列化
        //Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化
        //Include.NON_NULL 属性为NULL 不序列化
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //允许出现特殊字符和转义符
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //允许出现单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 字段和值都加引号
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }
}
