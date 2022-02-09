package dev.warriorg.shop.infrastructure.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 高士勇
 * @date
 */
public class JSONMapper {
    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static class StaticSingletonHolder {
        private static final JSONMapper instance = new JSONMapper();
    }

    private ObjectMapper mapper;

    private JSONMapper() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 创建默认Mapper
     */
    public static JSONMapper getInstance() {
        return StaticSingletonHolder.instance;
    }

    /**
     * 对象转换成JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return getInstance().mapper.writeValueAsString(object);
        }
        catch (IOException e) {
            logger.debug("toJson出错:" + object, e);
            return null;
        }
    }

    /***
     * Pretty Print JSON i
     *
     * @param object
     * @return
     */
    public static String toPrettyJSONString(Object object)  {
        final  ObjectMapper objectMapper =  getInstance().mapper.copy();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return objectMapper.writeValueAsString(object);
        }
        catch (IOException e) {
            logger.debug("toJson出错:" + object, e);
            return null;
        }
    }

    /**
     * JSON转换成Java对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T>  T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        try {
            return mapper.readValue(json, clazz);
        }
        catch (IOException e) {
            logger.warn("fromJson出错:" + json, e);
            return null;
        }
    }

    /**
     * 字符串转类数组
     *
     * @param json
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, valueTypeRef);
        }
        catch (IOException e) {
            logger.warn("fromJson出错:" + json, e);
            return null;
        }
    }

    public <T> T fromJsonIgnoreCase(String json, TypeReference<T> valueTypeRef) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            final  ObjectMapper objectMapper = mapper.copy();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            return objectMapper.readValue(json, valueTypeRef);
        }
        catch (IOException e) {
            logger.warn("fromJson出错:" + json, e);
            return null;
        }
    }

    /**
     * JSON转换成Java对象
     *
     * @param json
     * @return
     */
    public HashMap<String, Object> json2Map(String json) {
        return fromJson(json, HashMap.class);
    }

    /**
     * 把object转出clazz对象， 比如POJO和Map互换，字符串转换成Date
     *
     * @param object 原对象
     * @param clazz  目标类型
     * @param <T>
     * @return
     */
    public <T> T convert(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }

        return mapper.convertValue(object, clazz);
    }


    /**
     * 如果jsons 是数组格式，则挨个转换成clazz对象返回list，否则直接尝试转换成clazz对象返回list
     *
     * @param jsons
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> fromJsons(String jsons, Class<T> clazz) throws IOException {
        if (jsons == null || jsons.trim().length() == 0) {
            return Collections.EMPTY_LIST;
        }

        List<T> list = new ArrayList<>();

        JsonNode jsonNode = mapper.readTree(jsons);
        if (jsonNode.isArray()) {
            // 是数组
            for (JsonNode child : jsonNode) {
                list.add(mapper.treeToValue(child, clazz));
            }
        }
        else {
            //不是数组
            list.add(fromJson(jsons, clazz));
        }

        return list;
    }

    /**
     * Json对象节点转类数组
     *
     * @param node
     * @param cla
     * @param <T>
     * @return
     */
    public <T> List<T> nodeToOjb(JsonNode node, Class<T> cla) {
        List<T> list = new ArrayList<>();
        try {
            if (node.isArray()) {
                for (JsonNode child : node) {
                    list.add(mapper.treeToValue(child, cla));
                }
            }
            else {
                list.add(mapper.treeToValue(node, cla));
            }
        }
        catch (JsonProcessingException e) {
            logger.error("对象节点转换错误", e);
        }
        return list;
    }

    /**
     * JSON转换成Java对象
     *
     * @param json
     * @param javaType
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, JavaType javaType) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        try {
            return mapper.readValue(json, javaType);
        }
        catch (IOException e) {
            logger.warn("fromJson出错:" + json, e);
            return null;
        }
    }


    /**
     * JSON转换成Java对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Type type) {
        return fromJson(json, getJavaType(type));
    }

    public JavaType getJavaType(Type type) {
        //判断是否带有泛型
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            //获取泛型类型
            Class rowClass = (Class) ((ParameterizedType) type).getRawType();

            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];

            for (int i = 0; i < actualTypeArguments.length; i++) {
                //泛型也可能带有泛型，递归获取
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }
            return TypeFactory.defaultInstance().constructParametricType(rowClass, javaTypes);
        } else {
            //简单类型直接用该类构建JavaType
            Class cla = (Class) type;
            return TypeFactory.defaultInstance().constructParametricType(cla, new JavaType[0]);
        }
    }



    public ObjectMapper getMapper() {
        return mapper;
    }

}
