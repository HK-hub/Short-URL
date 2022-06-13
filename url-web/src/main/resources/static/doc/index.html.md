# short-url
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2022-06-12 23:20:24|2022-06-12 23:20:24|auto|@HK意境|Created by smart-doc



## 
### 
**URL:** http://127.0.0.1:80/access/{shortUrl}

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** : 根据短链接跳转到对应的长链接

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
shortUrl|string|短链接字符串|true|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/access/www.beaulah-rath.org
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─shortUrl|string|No comments found.|-
└─longUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─updateTime|string|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 835,
  "message": "success",
  "data": {
    "shortUrl": "www.beaulah-rath.org",
    "longUrl": "www.beaulah-rath.org",
    "type": 354,
    "createTime": "2022-06-12 23:20:27",
    "expirationTime": "2022-06-12 23:20:27",
    "updateTime": "2022-06-12 23:20:27"
  },
  "dateTime": "2022-06-12 23:20:27",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

## 
## 
## 
### 
**URL:** http://127.0.0.1:80/long-url/get/lurl

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** : 根据长链接对象的 longUrl 字符串进行查找

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
longUrlString|string|No comments found.|false|-

**Request-example:**
```
curl -X POST -i http://127.0.0.1:80/long-url/get/lurl
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─url|string|No comments found.|-
└─uri|string|No comments found.|-
└─type|int32|No comments found.|-
└─protocol|string|No comments found.|-
└─callerVersion|string|No comments found.|-
└─host|string|No comments found.|-
└─port|int32|No comments found.|-
└─method|string|No comments found.|-
└─params|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─version|int32|No comments found.|-
└─createTime|string|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 763,
  "message": "success",
  "data": {
    "id": "25",
    "url": "www.beaulah-rath.org",
    "uri": "460yjy",
    "type": 301,
    "protocol": "a0b0kj",
    "callerVersion": "2.8.9",
    "host": "kimhhy",
    "port": 789,
    "method": "cb4ikb",
    "params": "iye3dq",
    "visible": true,
    "version": 981,
    "createTime": "2022-06-12 23:20:28",
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/get/all

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取全部的 长链接对象，可选对外不可见的，删除的

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
visible|boolean|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/long-url/get/all?visible=false&deleted=false --data '"false"'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 712,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/get/{id}

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 通过 id 查询长链接

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/long-url/get/25
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─url|string|No comments found.|-
└─uri|string|No comments found.|-
└─type|int32|No comments found.|-
└─protocol|string|No comments found.|-
└─callerVersion|string|No comments found.|-
└─host|string|No comments found.|-
└─port|int32|No comments found.|-
└─method|string|No comments found.|-
└─params|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─version|int32|No comments found.|-
└─createTime|string|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 226,
  "message": "success",
  "data": {
    "id": "25",
    "url": "www.beaulah-rath.org",
    "uri": "q8mkfc",
    "type": 199,
    "protocol": "vollmt",
    "callerVersion": "2.8.9",
    "host": "4hecos",
    "port": 849,
    "method": "m43h9i",
    "params": "rg94oy",
    "visible": true,
    "version": 720,
    "createTime": "2022-06-12 23:20:28",
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/save

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 保存/新增/添加一个 longUrl 长链接

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
url|string|No comments found.|false|-
uri|string|No comments found.|false|-
type|int32|No comments found.|false|-
protocol|string|No comments found.|false|-
callerVersion|string|No comments found.|false|-
host|string|No comments found.|false|-
port|int32|No comments found.|false|-
method|string|No comments found.|false|-
params|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
version|int32|No comments found.|false|-
createTime|string|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/long-url/save --data '{
  "id": "25",
  "url": "www.beaulah-rath.org",
  "uri": "a4m122",
  "type": 263,
  "protocol": "dxb3n0",
  "callerVersion": "2.8.9",
  "host": "hpeje8",
  "port": 275,
  "method": "bxon0c",
  "params": "3lszvf",
  "visible": true,
  "version": 647,
  "createTime": "2022-06-12 23:20:28",
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 650,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/delete

**Type:** DELETE

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 根据 longUrl 的id 删除长链接对象

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
url|string|No comments found.|false|-
uri|string|No comments found.|false|-
type|int32|No comments found.|false|-
protocol|string|No comments found.|false|-
callerVersion|string|No comments found.|false|-
host|string|No comments found.|false|-
port|int32|No comments found.|false|-
method|string|No comments found.|false|-
params|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
version|int32|No comments found.|false|-
createTime|string|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X DELETE -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/long-url/delete --data '{
  "id": "25",
  "url": "www.beaulah-rath.org",
  "uri": "0lr1a9",
  "type": 85,
  "protocol": "n06ps7",
  "callerVersion": "2.8.9",
  "host": "j6xcp9",
  "port": 642,
  "method": "42vc6t",
  "params": "zahim3",
  "visible": true,
  "version": 602,
  "createTime": "2022-06-12 23:20:28",
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 654,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/delete/{id}

**Type:** DELETE

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据 longUrl 的id 删除长链接对象

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://127.0.0.1:80/long-url/delete/25
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 341,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/long-url/update

**Type:** PUT

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 根据 长链接id ,修改对象， 返回成功修改后的对象

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
url|string|No comments found.|false|-
uri|string|No comments found.|false|-
type|int32|No comments found.|false|-
protocol|string|No comments found.|false|-
callerVersion|string|No comments found.|false|-
host|string|No comments found.|false|-
port|int32|No comments found.|false|-
method|string|No comments found.|false|-
params|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
version|int32|No comments found.|false|-
createTime|string|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/long-url/update --data '{
  "id": "25",
  "url": "www.beaulah-rath.org",
  "uri": "joooe9",
  "type": 228,
  "protocol": "5kjyzp",
  "callerVersion": "2.8.9",
  "host": "p21rgi",
  "port": 804,
  "method": "6xjk91",
  "params": "mld74o",
  "visible": true,
  "version": 872,
  "createTime": "2022-06-12 23:20:28",
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 466,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

## 
### 
**URL:** http://127.0.0.1:80/short-url/new

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 通过长链接字符串，生成对应的短链接对象,设置过期策略，生成对应的长链接对象，长短链接映射关系

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
longUrl|string|长链接字符串|false|-
time|int32|时间度量,默认-1|false|-
timeUnit|string|时间单位，默认day|false|-

**Request-example:**
```
curl -X POST -i http://127.0.0.1:80/short-url/new --data 'time=-1&timeUnit=day'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─shortUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─no|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─version|int32|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 595,
  "message": "success",
  "data": {
    "id": "25",
    "shortUrl": "www.beaulah-rath.org",
    "type": 554,
    "no": "gxpayb",
    "visible": true,
    "createTime": "2022-06-12 23:20:28",
    "expirationTime": "2022-06-12 23:20:28",
    "version": 368,
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/get/surl

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据短链接字符串 进行查询

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
shortUrl|string|短链接字符串|true|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/short-url/get/surl?shortUrl=www.beaulah-rath.org --data 'www.beaulah-rath.org'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─shortUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─no|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─version|int32|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 344,
  "message": "success",
  "data": {
    "id": "25",
    "shortUrl": "www.beaulah-rath.org",
    "type": 580,
    "no": "x4rmyd",
    "visible": true,
    "createTime": "2022-06-12 23:20:28",
    "expirationTime": "2022-06-12 23:20:28",
    "version": 462,
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/get/all

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取全部 短链接对象列表，可选是否可见，过期，删除的

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
visible|boolean|是否查询过期的短链接|false|-
deleted|boolean|是否查询逻辑删除的短链接|false|-
expired|boolean|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/short-url/get/all?visible=false&deleted=false&expired=false --data '"false"'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 442,
  "message": "success",
  "data": {},
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/get/{id}

**Type:** GET

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据 shortUrl 对象id 值查询

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|shortUrl 对象id值|true|-

**Request-example:**
```
curl -X GET -i http://127.0.0.1:80/short-url/get/25
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─shortUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─no|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─version|int32|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 340,
  "message": "success",
  "data": {
    "id": "25",
    "shortUrl": "www.beaulah-rath.org",
    "type": 802,
    "no": "hlxgyj",
    "visible": true,
    "createTime": "2022-06-12 23:20:28",
    "expirationTime": "2022-06-12 23:20:28",
    "version": 814,
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/save

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 添加或者保存 shortUrl 对象, 返回成功保存后的对象和保存结果

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
shortUrl|string|No comments found.|false|-
type|int32|No comments found.|false|-
no|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
createTime|string|No comments found.|false|-
expirationTime|string|No comments found.|false|-
version|int32|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/short-url/save --data '{
  "id": "25",
  "shortUrl": "www.beaulah-rath.org",
  "type": 258,
  "no": "xgolqz",
  "visible": true,
  "createTime": "2022-06-12 23:20:28",
  "expirationTime": "2022-06-12 23:20:28",
  "version": 293,
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─shortUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─no|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─version|int32|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 919,
  "message": "success",
  "data": {
    "id": "25",
    "shortUrl": "www.beaulah-rath.org",
    "type": 336,
    "no": "yuj35s",
    "visible": true,
    "createTime": "2022-06-12 23:20:28",
    "expirationTime": "2022-06-12 23:20:28",
    "version": 937,
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/update

**Type:** PUT

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 根据 id 值，更新 shortUrl 对象，返回受影响的行数

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
shortUrl|string|No comments found.|false|-
type|int32|No comments found.|false|-
no|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
createTime|string|No comments found.|false|-
expirationTime|string|No comments found.|false|-
version|int32|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/short-url/update --data '{
  "id": "25",
  "shortUrl": "www.beaulah-rath.org",
  "type": 140,
  "no": "4ei9ni",
  "visible": true,
  "createTime": "2022-06-12 23:20:28",
  "expirationTime": "2022-06-12 23:20:28",
  "version": 51,
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|object|No comments found.|-
└─id|string|No comments found.|-
└─shortUrl|string|No comments found.|-
└─type|int32|No comments found.|-
└─no|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─createTime|string|No comments found.|-
└─expirationTime|string|No comments found.|-
└─version|int32|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 257,
  "message": "success",
  "data": {
    "id": "25",
    "shortUrl": "www.beaulah-rath.org",
    "type": 348,
    "no": "xp5r8n",
    "visible": true,
    "createTime": "2022-06-12 23:20:28",
    "expirationTime": "2022-06-12 23:20:28",
    "version": 447,
    "updateTime": "2022-06-12 23:20:28",
    "deleted": true
  },
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/delete

**Type:** DELETE

**Author:** : HK意境

**Content-Type:** application/json; charset=utf-8

**Description:** 删除指定 shortUrl 对象，id必须值， 返回是否删除成功

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|No comments found.|false|-
shortUrl|string|No comments found.|false|-
type|int32|No comments found.|false|-
no|string|No comments found.|false|-
visible|boolean|No comments found.|false|-
createTime|string|No comments found.|false|-
expirationTime|string|No comments found.|false|-
version|int32|No comments found.|false|-
updateTime|string|No comments found.|false|-
deleted|boolean|No comments found.|false|-

**Request-example:**
```
curl -X DELETE -H 'Content-Type: application/json; charset=utf-8' -i http://127.0.0.1:80/short-url/delete --data '{
  "id": "25",
  "shortUrl": "www.beaulah-rath.org",
  "type": 847,
  "no": "ohal5w",
  "visible": true,
  "createTime": "2022-06-12 23:20:28",
  "expirationTime": "2022-06-12 23:20:28",
  "version": 994,
  "updateTime": "2022-06-12 23:20:28",
  "deleted": true
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 980,
  "message": "success",
  "data": true,
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/short-url/delete/{id}

**Type:** DELETE

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据短链接对象id 删除

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|string|shortUrl对象的id值|true|-

**Request-example:**
```
curl -X DELETE -i http://127.0.0.1:80/short-url/delete/25
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 609,
  "message": "success",
  "data": true,
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

## 
### 
**URL:** http://127.0.0.1:80/url-map/get/surl

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据短链接字符串获取全部的长链接实体

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
shortUrl|string|短链接字符串|false|-

**Request-example:**
```
curl -X POST -i http://127.0.0.1:80/url-map/get/surl
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|array|No comments found.|-
└─id|string|No comments found.|-
└─url|string|No comments found.|-
└─uri|string|No comments found.|-
└─type|int32|No comments found.|-
└─protocol|string|No comments found.|-
└─callerVersion|string|No comments found.|-
└─host|string|No comments found.|-
└─port|int32|No comments found.|-
└─method|string|No comments found.|-
└─params|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─version|int32|No comments found.|-
└─createTime|string|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 371,
  "message": "success",
  "data": [
    {
      "id": "25",
      "url": "www.beaulah-rath.org",
      "uri": "oksxei",
      "type": 617,
      "protocol": "xiqw9d",
      "callerVersion": "2.8.9",
      "host": "chv4ft",
      "port": 442,
      "method": "vpv5rs",
      "params": "179bmb",
      "visible": true,
      "version": 493,
      "createTime": "2022-06-12 23:20:28",
      "updateTime": "2022-06-12 23:20:28",
      "deleted": true
    }
  ],
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

### 
**URL:** http://127.0.0.1:80/url-map/get/sid

**Type:** POST

**Author:** : HK意境

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 通过 短链接 id 获取对应的长链接对象

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
sid|string|短链接id值|false|-

**Request-example:**
```
curl -X POST -i http://127.0.0.1:80/url-map/get/sid
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
code|int32|No comments found.|-
message|string|No comments found.|-
data|array|No comments found.|-
└─id|string|No comments found.|-
└─url|string|No comments found.|-
└─uri|string|No comments found.|-
└─type|int32|No comments found.|-
└─protocol|string|No comments found.|-
└─callerVersion|string|No comments found.|-
└─host|string|No comments found.|-
└─port|int32|No comments found.|-
└─method|string|No comments found.|-
└─params|string|No comments found.|-
└─visible|boolean|No comments found.|-
└─version|int32|No comments found.|-
└─createTime|string|No comments found.|-
└─updateTime|string|No comments found.|-
└─deleted|boolean|No comments found.|-
dateTime|string|No comments found.|-
traceId|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "code": 759,
  "message": "success",
  "data": [
    {
      "id": "25",
      "url": "www.beaulah-rath.org",
      "uri": "eehlgb",
      "type": 2,
      "protocol": "c0taa4",
      "callerVersion": "2.8.9",
      "host": "ij96ei",
      "port": 647,
      "method": "p4fxth",
      "params": "t4t3e8",
      "visible": true,
      "version": 402,
      "createTime": "2022-06-12 23:20:28",
      "updateTime": "2022-06-12 23:20:28",
      "deleted": true
    }
  ],
  "dateTime": "2022-06-12 23:20:28",
  "traceId": "d80dc17b-adb3-479f-8c45-7d84d8fd2f7f"
}
```

## 

