package ru.otus.redisdemo;

import com.google.gson.Gson;
import lombok.val;
import redis.clients.jedis.Jedis;
import ru.otus.redisdemo.model.Phone;
import ru.otus.redisdemo.model.SmartPhone;
import ru.otus.redisdemo.template.RedisTemplateImpl;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class Demo {
    private static final int REDIS_PORT = 6379;
    private static final String REDIS_HOST = "localhost"; // Работа ,tp DockerToolbox
    //private static final String REDIS_HOST = "192.168.99.100"; // Работа через DockerToolbox

    public static void main(String[] args) throws Throwable {
        val motorolaC350 = new Phone(UUID.randomUUID().toString(),
                "C350", "silver", "000001");
        val sonyEricssonZ800i = new Phone(UUID.randomUUID().toString(),
                "Z800i", "silver", "000002");
        val huaweiP20 = new SmartPhone(UUID.randomUUID().toString(),
                "p20", "black", "000003", "Android");

        val mapper = new Gson();
        val jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        val redisTemplate = new RedisTemplateImpl(jedis, mapper);

        jedis.flushAll();

        redisTemplate.insert(motorolaC350.getId(), motorolaC350);
        redisTemplate.insert(sonyEricssonZ800i.getId(), sonyEricssonZ800i);
        redisTemplate.insert(huaweiP20.getId(), huaweiP20);


        val motorolaC350Optional = redisTemplate.findOne(motorolaC350.getId(), Phone.class);
        motorolaC350Optional.ifPresent(sm -> System.out.printf("Phone from db is:\n%s", sm));

        System.out.println("\n");

        val motorolaZ800iOptional = redisTemplate.findOne(sonyEricssonZ800i.getId(), Phone.class);
        motorolaZ800iOptional.ifPresent(sm -> System.out.printf("Phone from db is:\n%s", sm));

        System.out.println("\n");

        val huaweiP20Optional = redisTemplate.findOne(huaweiP20.getId(), SmartPhone.class);
        huaweiP20Optional.ifPresent(sm -> System.out.printf("Smartphone from db is:\n%s", sm));

        System.out.println("\n");

        val allPhones = redisTemplate.findAll(Phone.class);
        System.out.println("All phones from db:\n" + allPhones.stream()
                .map(Objects::toString).collect(Collectors.joining("\n")));

        System.out.println();
    }
}
