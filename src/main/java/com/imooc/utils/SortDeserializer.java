package com.imooc.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page及其实现类PageImpl没有无参构造, 其超类Chunk也没有无参构造; 导致反序列化失败.-jackson(Sort也是如此)
 * 解决方案
 *  - 方案① 自定义序列化及反序列化Sort
 *  - 方案② 重写Sort
 *
 * @Author: yangfeng
 * @Date: 2019/9/19 14:48
 * Email: Feng.Yang@things-matrix.com
 */
public class SortDeserializer extends JsonDeserializer<Sort> {

    // createTime: DESC,id: DESC
    @Override
    public Sort deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String sorts = node.textValue();

        List<Sort.Order> orders = Arrays.stream(sorts.split(",")).map(s -> {
            String[] split = s.split(": ");
            return split[1].toUpperCase().equals(Sort.Direction.ASC.name()) ? Sort.Order.asc(split[0]) : Sort.Order.desc(split[0]);
        }).collect(Collectors.toList());

        return Sort.by(orders);
    }
}
