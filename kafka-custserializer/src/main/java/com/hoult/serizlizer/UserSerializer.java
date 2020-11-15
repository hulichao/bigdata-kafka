package com.hoult.serizlizer;

import entity.User;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, User user) {

        try {
        if (user == null)
            return null;
        else {
            final Integer userId = user.getUserId();
            final String username = user.getUsername();

            if (userId != null) {
                if (username != null) {
                    final byte[] bytes = username.getBytes("UTF-8");
                    int length = bytes.length;

                    //第一个字节，用于存储userid的值
                    //第二个字节，用于存储username字节数组的长度
                    //第三个字节，用于存放username序列化后的字节数组
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4 + 4 + length);
                    byteBuffer.putInt(userId);
                    byteBuffer.putInt(length);
                    byteBuffer.put(bytes);

                    return byteBuffer.array();
                }
            }
        }
        } catch (Exception e) {
            throw new SerializationException("数据序列化失败");
        }
        return new byte[0];
    }

    @Override
    public void close() {
        //do nothin
        //用户关闭资源等操作，需要幂等，即多次调用，效果是一样的
    }
}
