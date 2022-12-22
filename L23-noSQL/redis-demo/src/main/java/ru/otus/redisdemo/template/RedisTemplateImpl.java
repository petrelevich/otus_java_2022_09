package ru.otus.redisdemo.template;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.val;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RedisTemplateImpl implements RedisTemplate {

    private final Jedis jedis;
    private final Gson mapper;

    private String buildKey(Class<?> tClass, String id) {
        return String.format("%s:%s", tClass.getName(), id);
    }

    @Override
    public <T> void insert(String id, T value) {
        val key = buildKey(value.getClass(), id);
        jedis.set(key, mapper.toJson(value));
    }

    @Override
    public <T> Optional<T> findOne(String id, Class<T> tClass) {
        val key = buildKey(tClass, id);
        return Optional.ofNullable(jedis.get(key)).map(v -> mapper.fromJson(v, tClass));

    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        val keys = jedis.keys(String.format("%s:*", tClass.getName()));
        val values = jedis.mget(keys.toArray(new String[0]));
        return values.stream().map(s -> mapper.fromJson(s, tClass)).collect(Collectors.toList());
    }
}
