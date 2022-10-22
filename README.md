# AuroraRemark
为深入学习redis而肝的项目，初定为曙光点评，寓意以曙光评判世界（项目原型为黑马点评）

## 技术栈
### 后端
Spring 相关：
Spring Boot 2.x
Spring MVC
数据存储层：

MySQL：存储数据
MyBatis Plus：数据访问框架
Redis 相关：

spring-data-redis：操作 Redis
Lettuce：操作 Redis 的高级客户端
Apache Commons Pool：用于实现 Redis 连接池
Redisson：基于 Redis 的分布式数据网格

工具库：
HuTool：工具库合集
Lombok：注解式代码生成工具

### 前端
原生 HTML、CSS、JS 三件套
Vue 2（渐进式使用）
Element UI 组件库
axios 请求库

### 目录
config 目录：存放项目依赖相关配置
MvcConfig：配置了登录、自动刷新登录 Token 的拦截器
MybatisConfig：配置 MyBatis Plus 分页插件
RedissonConfig：创建单例 Redisson 客户端
WebExceptionAdvice：全局响应拦截器

controller 目录：存放 Restful 风格的 API 接口

dto 目录：存放业务封装类，如 Result 通用响应封装（不推荐学习它的写法）

entity 目录：存放和数据库对应的 Java POJO，一般是用 MyBatisX 等插件自动生成

mapper 目录：存放操作数据库的代码，基本没有自定义 SQL，都是复用了 MyBatis Plus 的方法，不做重点学习。

service 目录：存放业务逻辑处理代码，需要重点学习

BlogServiceImpl：
基于 Redis 实现点赞、按时间排序的点赞排行榜；基于 Redis 实现拉模式的 Feed 流，推荐学习

FollowServiceImpl：
基于 Redis 集合实现关注、共同关注，推荐学习

ShopServiceImpl：
基于 Redis 缓存优化店铺查询性能；基于 Redis GEO 实现附近店铺按距离排序，推荐学习

UserServiceImpl：
基于 Redis 实现短信登录（分布式 Session），推荐学习（虽然没有真的实现短信登录，而是通过日志打印验证码代替）

VoucherOrderServiceImpl：
基于 Redis 分布式锁、Redis + Lua 两种方式，结合消息队列，共同实现了秒杀和一人一单功能，非常值得学习

VoucherServiceImpl：
添加优惠券，并将库存保存在 Redis 中，为秒杀做准备。

utils 目录：
存放项目内通用的工具类，需要重点学习

CacheClient：
封装了通用的缓存工具类，涉及泛型、函数式编程等知识点，值得学习
RedisConstants：
保存项目中用到的 Redis 键、过期时间等常量，值得学习
RedisIdWorker：
基于 Redis 的全局唯一自增 id 生成器，值得学习
RedisTokenInterceptor：
自动刷新 Redis 登录 Token 有效期，值得学习
SimpleRedisLock：
简单的 Redis 锁实现，了解即可，一般用 Redisson
UserHolder：
线程内缓存用户信息，可以学习

>注：启动之前，在Redis中添加消费者组
> XGROUP CREATE stream.orders g1 $ MKSTREAM