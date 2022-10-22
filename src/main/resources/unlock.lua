--//获取线程标识
--String threadId =ID_PREFIX+ Thread.currentThread().getId();
--//获取锁中标识
--String id = stringRedisTemplate.opsForValue().get(KET_PREFIX + name);
--//判断时候一致
--if (StringUtils.equals(id,threadId)){
--//一致 释放锁
--stringRedisTemplate.delete(KET_PREFIX + name);
--}

-- 比较线程标示与锁中的标示是否一致
if (redis.call('get',KEYS[1])==ARGV[1]) then
            -- 释放锁 del key
    return redis.call('del',KEYS[1])
end
return 0
