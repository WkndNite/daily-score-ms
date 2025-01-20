**接口名称:**

register

**接口描述:**



**请求路径:**

/user/register

**请求方式:**

POST

**请求参数:**

- Header

|参数名|参数值|必填|描述|
|:-----|:-----|:-----|:-----|
|Content-Type|application/json|Y||


- Param

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|MODEL_KEY_PREFIX|String|N||
|NESTED_PATH_SEPARATOR|String|N||


- Body

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|username|String|N|用户名|
|password|String|N|密码|
|identity|Integer|N|身份（1：管理员；2：老师；3：学生）|
|phone|String|N|电话号码|
|code|String|N||


**请求示例:**

```Form
MODEL_KEY_PREFIX=null&NESTED_PATH_SEPARATOR=null
```

```JSON
{
    "username": "",
    "password": "",
    "identity": 0,
    "phone": "",
    "code": ""
}
```

**返回参数:**

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|code|Integer|N||
|msg|String|N||
|data|Object|N||


**返回示例:**

```JSON
{
    "code": 0,
    "msg": "",
    "data": {}
}
```

**接口名称:**

loginAccount

**接口描述:**



**请求路径:**

/user/loginAccount

**请求方式:**

POST

**请求参数:**

- Header

|参数名|参数值|必填|描述|
|:-----|:-----|:-----|:-----|
|Content-Type|application/json|Y||


- Param

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|MODEL_KEY_PREFIX|String|N||
|NESTED_PATH_SEPARATOR|String|N||


- Body

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|username|String|N|用户名|
|password|String|N|密码|


**请求示例:**

```Form
MODEL_KEY_PREFIX=null&NESTED_PATH_SEPARATOR=null
```

```JSON
{
    "username": "",
    "password": ""
}
```

**返回参数:**

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|code|Integer|N||
|msg|String|N||
|data|Object|N||


**返回示例:**

```JSON
{
    "code": 0,
    "msg": "",
    "data": {}
}
```

**接口名称:**

loginPhone

**接口描述:**



**请求路径:**

/user/loginPhone

**请求方式:**

POST

**请求参数:**

- Header

|参数名|参数值|必填|描述|
|:-----|:-----|:-----|:-----|
|Content-Type|application/json|Y||


- Param

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|MODEL_KEY_PREFIX|String|N||
|NESTED_PATH_SEPARATOR|String|N||


- Body

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|phone|String|N|电话号码|
|code|String|N||


**请求示例:**

```Form
MODEL_KEY_PREFIX=null&NESTED_PATH_SEPARATOR=null
```

```JSON
{
    "phone": "",
    "code": ""
}
```

**返回参数:**

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|code|Integer|N||
|msg|String|N||
|data|Object|N||


**返回示例:**

```JSON
{
    "code": 0,
    "msg": "",
    "data": {}
}
```

**接口名称:**

code

**接口描述:**



**请求路径:**

/user/code

**请求方式:**

POST

**请求参数:**

- Header

|参数名|参数值|必填|描述|
|:-----|:-----|:-----|:-----|
|Content-Type|application/json|Y||


- Param

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|MODEL_KEY_PREFIX|String|N||
|NESTED_PATH_SEPARATOR|String|N||


- Body

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|code|String|N||
|phone|String|N||
|updateTime|LocalDateTime|N||


**请求示例:**

```Form
MODEL_KEY_PREFIX=null&NESTED_PATH_SEPARATOR=null
```

```JSON
{
    "code": "",
    "phone": "",
    "updateTime": null
}
```

**返回参数:**

|参数名|类型|必选|描述|
|:-----|:-----|:-----|:-----|
|code|Integer|N||
|msg|String|N||
|data|Object|N||


**返回示例:**

```JSON
{
    "code": 0,
    "msg": "",
    "data": {}
}
```

