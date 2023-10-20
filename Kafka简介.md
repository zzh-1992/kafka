# 1 Kafka简介
华为云-kafka
https://education.huaweicloud.com/courses/course-v1:HuaweiX+CBUCNXE195+Self-paced/courseware/bfc5fb305e684474a16360dada5d8c2e/19754ffc151e47429a528dd3c61473b9/

## 1.0 Kafka简介
- Kafka是最初由Linkedin公司开发，是一个分布式、分区的、多副本、多订阅者、基于zookeeper协调的分布式日志系统。
- 主要应用场景：日志收集系统和消息系统。
- 分布式消息传递基于可靠的消息队列，在客户端应用和消息系统之间异步传递消息。有两种主要的消息传递模式：点对点传递模式、发布-订阅模式。大部分的消息系统选用发布订阅模式。Kafka就是一种发布订阅模式。

## 1.1 点对点消息传递模式
- 在点对点消息系统中，消息持久化到一个队列中。此时，将有一个或多个消费者消费队列中的数据。但是一条消息只能被消费一次。当一个消费者消费了队列中的某条数据之后，该条数据则从消息队列中删除。该模式即使有多个消费者同时消费数据，也能保证数据处理的顺序。

## 1.2 发布-订阅消息传递模式
- 在发布-订阅消息系统中，消息被持久化到一个topic中。与点对点消息不同的是，消费者可以订阅一个或多个topic，消费者可以消费该topic中的所有数据，同一条数据可以被多个消费者消费，数据被消费后不会立马删除。在发布-订阅消息系统中，消息的生产者称为发布者，消费者称为订阅者。

## 1.3  Kafka特点
- 以时间复杂度为O(1)的方式提供消息持久化能力，即使对TB级以上的数据也能保证常数时间的访问性能。
- 高吞吐率。即使在廉价的商用机器上也能做到单机支持100K条消息的传输。
- 支持消息分区，及分布式消费，同时保证每个分区内消息顺序传输。
- 同时支持离线数据处理和实时数据处理。
- Scale out： 支持在线水平扩展。

# 2  Kafka架构与功能
## 2.1 Kafka拓扑结构图
## 2.2 Kafka基本概念 Kafka basic concepts
- Broker：Kafka集群包含一个或多个服务实例，这些服务实例被称为Broker。
- Topic：每条发布到Kafka集群到消息都有一个类别，这个类别被称为Topic。
- Partition：Kafka将Topic分成一个或者多个Partition，每个Patition在物理上对应一个文件夹，该文件夹下存储这个partition的所有消息。
- Producer：负责发布消息到Kafka Broker。
- Consumer：消息消费者，从KafkaBroker读取消息的客户端。
- ConsumerGroup： 每个Consumer属于一个特定的ConsumerGroup(可为每个Consumer指定group name)

## 2.3 Kafka Topics
- 每条发布到Kafka到消息都有一个类别，这个类别被称为Topic，也可以理解为一个存储消息的队列。

## 2.4 Kafka Partiton
- 为了提高Kafka的吞吐量，物理上把Topic分成一个或者多个Partiton，每个Partition都有序且不可变的消息队列。每个Partition在物理上对应一个文件夹，该文件夹下存储这个Partition的所有消息和索引文件。

## 2.5 Kafka Partition offest
- 每条消息在文件中的位置称为offest(偏移量)，offest是一个long型数字，它唯一标记一条消息。消费者通过(offest,partition,topic)跟踪记录。

## 2.5 offest存储机制
- Consumer从Broker中读取消息后，可以选择commint，该操作会在Kafka中保存该Consumer在该Partition中读取的消息的offest。该Consumer下一次再读该Partition时会从下一条开始读取。
- 通过这一特性可以保证同一消费者从Kafka中不会重复消费数据。
- 消费者group位移保存在_consumer_offets目录上：
- 计算公式：Math.abs(groupID.hashCode()) % 50;
- kafka-logs目录，里面有多个目录，因为kafka默认会生成50个 _consumer_offets目录。

## 2.6 Consumer group
- 每个consumer都属于一个consumer group，每条消息只能被consumer group中的一个Consumer消费，但可以被多个consumer group 消费。即组间数据是共享的，组内数据是竞争的。

## 2.7 Kafka的其他重要概念
- replica：partition的副本，保障partition的高可用。
- leader：replica中的一个角色，producer和consumer只和leader交互。
- follower：replica中的一个角色，从leader中复制数据。
- controller：kafka集群中的一个服务器，用来进行leader election以及各种failover。

# 3 Kafka数据管理

## 3.1 数据存储可靠性
## Kafka Partition Replica
## Kafka Partition 
## Kafka HA
- 同一个partition可能会有多个replica（对应server.properties配置中的default.replication.factor=N）
- 没有replica的情况下，一旦broker宕机，其上所有partition的数据都不可被消费，同时producer也不能再将数据存于其上的patiton。
- 引入replication之后，同一个patition可能会有多个replica，而这时需要在这些replica之间选出一个leader，producer和consumer只于这个leader交互，其他replica作为follower从leader中复制数据。

## Leader Failover（1）
- 当partiton对应的leader宕机时，需要从follower中选举出新的leader。在选举新leader时，一个基本原则是，新的leader必须拥有旧leader commit过的所有消息。
- 由于写入流程可知ISR表里面所有replica都跟上了leader，只有ISR里面的成员才能选举为leader
- 对与f+1个replica，partition可以在容忍f个replica失效的情况下保证消息不丢失。

## Leader Failover（2）
当所有replica都不工作时，有两种可行的方案：
- 等待ISR中的任一个replica活过来，并选举它作为leader。可保障数据不丢失，但时间可能相对较长。
- 选择第一个活过来的replica（不一定是ISR成员）作为leader。无法保障数据不丢失，但相对不可用时间较短。

## 3.2 Kafka消息可靠性
- Kafka所有消息都会被持久化到硬盘中，同时Kafka通过对Topic Partition设置Replication来保障数据可靠。

## 消息传递语义
- 消息传输保障通常有以下三种：
- 最多一次 At Most Once
  消息可能丢失
  消息不会重复发送和处理
- 最少一次 At Lease Once
  消息不会丢失
  消息可能重复发送和处理
- 仅有一次 Exactly Once
  消息不会丢失
  消息仅被处理一次

## 可靠性保证 幂等性
- 一个幂等性的操作就是一种被执行多次造成的影响和只执行一次造成的影响一样。
- 原理：
- 每次发送到Kafka到消息都将包含一个序列号，broker将使用这个序列号来删除重复数据。
- 这个序列号被持久化到副本日志，所以，即使分区的leader挂了，其他的broker来接管了leader，新leader 仍可以判断重新发送的师傅重复了。
- 这种机制开销非常低：每批消息只有几个额外的字段。

## 可靠性保证-acks机制
producer需要server接收到数据之后发出确认接收到信号，此项配置就是指procuder需要多少个这样确认信号。此配置实际上代表了数据备份到可用性。以下配置为常用选项：

- acks = 0:设置为0表示produder不需要等待任何确认收到的信息。副本立即驾到socket buffer并认为已经发送。没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）回馈的offest 会总是设置为-1；
- acks = 1:这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有的follower是否成功写入。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
- acks = all 这意味着leader需要等待所有备份都写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。

## 旧数据处理方式
- Kafka把Topic中的一个Partition大文件分成多个小文件，通过多个小文件段，就容易定期清楚或删除已经消费完文件，减少磁盘占用。
- 对于传统的message queue而言，一般会删除已经被消费的消息，而Kafka集群会保留所有的消息，无论其被消费与否。当然，因为磁盘限制，不可能永久保留所有数据（ 实际上也没必要），因此kafka需要处理旧数据。
- 配置位置：$KAFAK_HOME/config/server.properties

## kafka Log cleaup
-日志的清理方式有两种：delete和compact
-删除的阈值有两种：过期的时间和分区内日志大小
| 配置参数                | 默认值    | 参数解释                                     | 取值范围                   |
| ------------------- | ------ | ---------------------------------------- | ---------------------- |
| Log.cleanup.policy  | Delete | 当日志过期时（超过了要保存的时间），采用的清除策略，可以取值为删除或者压缩。   | Delete/compact         |
| Log.retention.hours | 168    | 日志数据文件保留的最长时间。单位：小时。                     | 1~2147483647           |
| Log.retention.bytes | 1      | 指定每个partition上的日志数据所能达到的最大字节。默认情况下无限制。单位：字节。 | -1~9223372036854775807 |





