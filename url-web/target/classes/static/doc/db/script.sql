create table tb_anonymous_user
(
    secret_key  varchar(64)          not null comment '密钥',
    short_url   varchar(64)          null comment '短链接',
    long_url    varchar(1024)        null comment '长链接，可能带有参数',
    create_time datetime             null comment '匿名用户创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '匿名用户，临时用户';

create index idx_au_secret_key
    on tb_anonymous_user (secret_key);

create index idx_au_ssl
    on tb_anonymous_user (secret_key, short_url, long_url);

create table tb_app_user
(
    id          varchar(32)          not null comment '用户ID号，临时用户，不登录用户没有ID，只有注册用户才有ID'
        primary key,
    username    varchar(32)          null comment '用户名',
    password    varchar(16)          null comment '密码',
    email       varchar(32)          null comment '注册邮箱：日后进行数据报表发送',
    vip         tinyint(1) default 0 null comment '是否为会员',
    secret_key  varchar(64)          null comment '密钥，用户哪来获取报表数据信息的验证',
    visible     tinyint(1) default 1 null comment '可见性',
    create_time datetime             null comment '创建时间',
    update_time datetime             null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '逻辑删除'
)
    comment '用户User，Sass对外提供接口的使用用户';

create index idx_api_email
    on tb_app_user (email);

create index idx_apu_uname_passwd
    on tb_app_user (username, password);

create table tb_log_trance
(
    trance_id       varchar(128)  not null comment '链路追踪id'
        primary key,
    business_type   varchar(32)   null comment '业务类型',
    business_method varchar(64)   null comment '业务处理方法',
    level           varchar(4)    null comment '业务类型',
    path            varchar(128)  null comment '请求路径',
    ip_address      varchar(64)   null comment 'ipv4 的客户端ip 地址',
    user_agent      varchar(128)  null comment '客户端浏览器，操作系统',
    location        varchar(128)  null comment '客户端地址：国家，省份，城市',
    parameters      varchar(256)  null comment '请求参数',
    request_method  varchar(8)    null comment '请求方法',
    operate         varchar(64)   null comment '执行的操作',
    operator        varchar(64)   null comment '操作用户',
    result          varchar(1024) null comment '响应结果',
    code            int           null comment '响应状态码',
    msg             varchar(1024) null comment '请求备注，出错原因',
    create_time     datetime      null comment '请求创建时间',
    execute_time    varchar(64)   null comment '请求调用链路执行时间',
    end_time        datetime      null comment '结束时间'
)
    comment '系统调用链路日志';

create table tb_long_url
(
    id             varchar(64)          not null comment '长链接ID号'
        primary key,
    url            varchar(1024)        not null comment '完整的URL链接，可以是普通http 请求url ,qrcode ，base64等',
    uri            varchar(256)         null comment 'URI 统一资源标识符,url 最后一部分',
    type           int        default 0 null comment 'url 资源类型: request 请求类型，二维码类型，base64类型',
    protocol       varchar(32)          null comment '调用协议类型:HHTP,RPC',
    caller_version varchar(16)          null comment '远端调用，长链接的请求协议版本',
    host           varchar(64)          null comment '长链接主机地址',
    port           int                  null comment '长链接的端口',
    method         varchar(16)          null comment '长链接的请求端口',
    params         varchar(1024)        null comment 'url 链接地址请求参数，? 分割',
    visible        tinyint(1) default 1 null comment '是否可见1可见，0不可见',
    version        int                  null comment '乐观锁',
    deleted        tinyint(1) default 0 null comment '逻辑删除:0未删除，1已删除
',
    create_time    datetime             null comment '创建时间',
    update_time    datetime             null comment '更新时间'
)
    comment '长链接';

create index idx_lurl_url_param
    on tb_long_url (url);

create table tb_short_url
(
    id              varchar(64)          not null comment '短链接ID号'
        primary key,
    short_url       varchar(32)          not null comment '生成后的短链接',
    type            int                  null comment '短链接类型: http 请求链接，二维码，base64',
    no              mediumtext           null comment '编号:目前作用未知',
    visible         tinyint(1) default 1 null comment '可见性:1可见，0不可见',
    create_time     datetime             null comment '创建时间',
    expiration_time datetime             null comment '过期时间：表示短链接从创建经过到使用到消亡的时间，是指失效的时间：
expiration_time=create_time+有效时间',
    version         int                  null comment '乐观锁',
    update_time     datetime             null comment '跟新时间
',
    deleted         tinyint(1) default 0 null comment '是否可见：1可见，0不可见'
)
    comment '短链接表';

create index idx_surl_url_type
    on tb_short_url (short_url, type);

create index idx_sutl_url_exptime
    on tb_short_url (short_url, expiration_time);

create table tb_tiny_id
(
    id          bigint auto_increment comment '自增id'
        primary key,
    biz_type    varchar(64)                        null comment '业务类型标识，唯一',
    begin_id    bigint   default 0                 null comment '开始id,仅记录初始值，初始时，值应该等于max_id',
    max_id      bigint   default 0                 null comment '当前最大id, 发号器目前发到的ID',
    step        int      default 10000             null comment '步长，号段长度',
    delta       int      default 1                 null comment '每次id 增量',
    remainder   int      default 10000             null comment '余量',
    remain_rate double   default 0.2               not null comment '剩余率，当剩余率低于这个值的时候就会去申请新的号段',
    create_time datetime                           null comment '修改时间',
    update_time datetime default CURRENT_TIMESTAMP null comment '跟新时间',
    version     bigint   default 1                 null comment '乐观锁',
    constraint tb_tiny_id_biz_type_uindex
        unique (biz_type)
)
    comment '滴滴TinyId 算法，分段发号器算法';

create table tb_url_map
(
    short_id        varchar(64)          not null comment '短链接ID号',
    short_url       varchar(32)          null comment '短链接url',
    long_id         varchar(64)          not null comment '长链接ID',
    long_url        varchar(1024)        null comment '长链接url',
    long_md         varchar(64)          null comment '长链接的MD5值',
    visible         tinyint(1) default 1 null comment '映射关系是否可见',
    create_time     datetime             null comment '映射关系创建时间',
    expiration_time datetime             null comment '映射关系失效时间=创建时间+有效时长',
    version         int                  null comment '乐观锁',
    update_time     datetime             null comment '更新时间',
    deleted         tinyint(1) default 0 null comment '逻辑删除:1删除，0未删除',
    primary key (short_id, long_id)
)
    comment '长链接和短链接的映射关系';

create index idx_um_slurl
    on tb_url_map (short_url, long_url);

create table tb_url_type
(
    id          int                                   not null comment 'id 号'
        primary key,
    type        varchar(32) default 'url'             not null,
    deleted     tinyint(1)  default 0                 null comment '是否删除',
    create_time datetime                              null,
    update_time datetime    default CURRENT_TIMESTAMP null
)
    comment 'url 链接数据类型';

create table tb_visit_log
(
    id           varchar(32)          not null comment 'ID号'
        primary key,
    short_url    varchar(64)          null comment '短链接',
    long_url     varchar(512)         null comment '长链接',
    visitor_ip   varchar(64)          null comment '访问者的IP地址',
    request_host varchar(64)          null comment '请求的域名',
    referrer     varchar(64)          null comment '请求者来源网站',
    target_host  varchar(64)          null comment '长链接(目标链接)域名',
    equipment    varchar(64)          null comment '访问设备:操作系统类型，浏览器类型',
    visitor_area varchar(125)         null comment '访问地区：国家，省份，市级，区级',
    method       varchar(8)           null comment '访问方法',
    create_time  datetime             null comment '访问时间',
    update_time  datetime             null comment '跟新时间',
    deleted      tinyint(1) default 0 null comment '逻辑删除：1删除，0未删除'
)
    comment '短链接访问日志';

create index idx_vl_sid_lid
    on tb_visit_log (short_url, long_url);


