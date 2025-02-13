define({ "api": [
  {
    "type": "DELETE",
    "url": "/Apply",
    "title": "删除申请记录",
    "group": "Apply",
    "version": "0.0.1",
    "description": "<p>将申请记录删除</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动id</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"取消成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Apply",
    "name": "DeleteApply"
  },
  {
    "type": "GET",
    "url": "/Apply/get/U_id",
    "title": "查看申请人",
    "group": "Apply",
    "version": "0.0.1",
    "description": "<p>查看申请记录的申请人</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": ""
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "user",
            "description": "<p>申请人</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"搜索成功\"，\"user\":\"1\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Apply",
    "name": "GetApplyGetU_id"
  },
  {
    "type": "GET",
    "url": "/Apply/:u_id",
    "title": "查询用户所有的申请记录",
    "group": "Apply",
    "version": "0.0.1",
    "description": "<p>查询用户所有的申请记录</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "articles",
            "description": "<p>申请的活动的数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"查询成功\"，\"articles\":\"[1,2]\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Apply",
    "name": "GetApplyU_id"
  },
  {
    "type": "POST",
    "url": "/Apply",
    "title": "添加申请记录",
    "group": "Apply",
    "version": "0.0.1",
    "description": "<p>记录用户和活动的id从而记录申请操作</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动id</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"申请成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Apply",
    "name": "PostApply"
  },
  {
    "type": "DELETE",
    "url": "/Article/:a_id",
    "title": "删除活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>删除相应id的活动发布</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动序列号</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"删除成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "DeleteArticleA_id"
  },
  {
    "type": "GET",
    "url": "/Article",
    "title": "查看所有活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>查看所有活动</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "a_ids",
            "description": "<p>活动文章的序列号</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"搜索成功\"，\"a_id\":\"[1,3,4]\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticle"
  },
  {
    "type": "GET",
    "url": "/Article/:a_id",
    "title": "使用id查看活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>使用活动id查看该活动</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "Article",
            "optional": false,
            "field": "article",
            "description": "<p>活动内容</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"搜索成功\"，\"a_id\":\"xxx\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticleA_id"
  },
  {
    "type": "GET",
    "url": "/Article/search/:title",
    "title": "搜索活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>搜索近似活动</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "articles",
            "description": "<p>活动的数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"搜索成功\"，\"articles\":\"[1,2]\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticleSearchTitle"
  },
  {
    "type": "GET",
    "url": "/Article/:title",
    "title": "搜索活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>获取活动</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>活动名</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"查询成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticleTitle"
  },
  {
    "type": "GET",
    "url": "/Article/:title",
    "title": "获取活动id",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>获取搜索关键字有关的活动</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>活动名</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>活动名</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>活动的库内编号</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"id\":\"1\",\"msg\":\"活动1\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticleTitle"
  },
  {
    "type": "GET",
    "url": "/Article/:u_id",
    "title": "查看某用户所有活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>查看某个用户创建的所有活动</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>发布者id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "a_ids",
            "description": "<p>活动文章的序列号</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"搜索成功\"，\"a_id\":\"[1,3,4]\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "GetArticleU_id"
  },
  {
    "type": "POST",
    "url": "/Article/:a_id",
    "title": "修改活动信息",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>通过id修改相应活动的信息</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动序列号</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "body",
            "description": "<p>正文</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": true,
            "field": "type",
            "description": "<p>类别</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": true,
            "field": "state",
            "description": "<p>状态</p>"
          },
          {
            "group": "200",
            "type": "Date",
            "optional": true,
            "field": "date",
            "description": "<p>日期</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": true,
            "field": "emblem",
            "description": "<p>级别</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"修改成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "PostArticleA_id"
  },
  {
    "type": "POST",
    "url": "/register",
    "title": "创建活动",
    "group": "Article",
    "version": "0.0.1",
    "description": "<p>创建一个新活动</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "body",
            "description": "<p>正文</p>"
          },
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "type",
            "description": "<p>活动类型</p>"
          },
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "state",
            "description": "<p>活动状态</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "date",
            "description": "<p>日期</p>"
          },
          {
            "group": "Parameter",
            "type": "int[]",
            "optional": false,
            "field": "emblem",
            "description": "<p>活动级别</p>"
          },
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>发布者id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "201": [
          {
            "group": "201",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "201",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"创建成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Article",
    "name": "PostRegister"
  },
  {
    "type": "DELETE",
    "url": "/Attention/cancle",
    "title": "取消收藏",
    "group": "Attention",
    "version": "0.0.1",
    "description": "<p>取消收藏</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"取关成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Attention",
    "name": "DeleteAttentionCancle"
  },
  {
    "type": "GET",
    "url": "/Attention/:id",
    "title": "查找收藏",
    "group": "Attention",
    "version": "0.0.1",
    "description": "<p>查看某用户的所有收藏</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "articles",
            "description": "<p>活动的数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"查询成功\"，\"articles\":\"[1,2]\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Attention",
    "name": "GetAttentionId"
  },
  {
    "type": "POST",
    "url": "/Attention/add",
    "title": "添加收藏",
    "group": "Attention",
    "version": "0.0.1",
    "description": "<p>添加收藏</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "u_id",
            "description": "<p>用户</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "a_id",
            "description": "<p>活动</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"收藏成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Attention",
    "name": "PostAttentionAdd"
  },
  {
    "type": "GET",
    "url": "/connect",
    "title": "",
    "group": "Database",
    "version": "0.0.1",
    "description": "<p>获取数据库的连接</p>",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": ""
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Database",
    "name": "GetConnect"
  },
  {
    "type": "GET",
    "url": "/Emblem/:id",
    "title": "获取兴趣分类",
    "group": "Emblem",
    "version": "0.0.1",
    "description": "<p>获取用户兴趣分类</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int[]",
            "optional": false,
            "field": "code",
            "description": "<p>三个二进制码分别代表用户的三个兴趣类型</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"000\",\"msg\":\"无兴趣\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Emblem",
    "name": "GetEmblemId"
  },
  {
    "type": "POST",
    "url": "/Emblem/:id",
    "title": "初始化用户兴趣",
    "group": "Emblem",
    "version": "0.0.1",
    "description": "<p>初始化用户兴趣分类</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表无类别或有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"初始化成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Emblem",
    "name": "PostEmblemId"
  },
  {
    "type": "GET",
    "url": "/check/account",
    "title": "检查用户名",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>检查用户名是否已经注册</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1 代表不存在 0代表存在</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"0\",\"msg\":\"已存在该用户名\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetCheckAccount"
  },
  {
    "type": "GET",
    "url": "/check/password",
    "title": "检查用户密码",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>检查密码是否正确从而完成登录</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1 代表正确 0代表错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"0\",\"msg\":\"密码错误\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetCheckPassword"
  },
  {
    "type": "GET",
    "url": "/check/username",
    "title": "检查昵称",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>检查昵称是否已经注册</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1 代表不存在 0代表存在</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"0\",\"msg\":\"已存在该昵称\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetCheckUsername"
  },
  {
    "type": "GET",
    "url": "/get/account",
    "title": "获取用户名",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>根据id获取用户信息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetGetAccount"
  },
  {
    "type": "GET",
    "url": "/get/id",
    "title": "获取信息",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>根据用户名获取数据库内id</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetGetId"
  },
  {
    "type": "GET",
    "url": "/get/user",
    "title": "获取用户",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>使用usename查询用户</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>昵称</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "Users",
            "optional": false,
            "field": "user",
            "description": "<p>用户所有信息</p>"
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetGetUser"
  },
  {
    "type": "GET",
    "url": "/get/user",
    "title": "获取用户信息",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>根据id获取用户信息</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "user",
            "description": "<p>用户信息</p>"
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetGetUser"
  },
  {
    "type": "GET",
    "url": "/get/username",
    "title": "获取昵称",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>根据id获取昵称</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>昵称</p>"
          }
        ]
      }
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "GetGetUsername"
  },
  {
    "type": "PATCH",
    "url": "/users/:password",
    "title": "修改密码",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>修改密码</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>新头像</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"修改成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "PatchUsersPassword"
  },
  {
    "type": "POST",
    "url": "/register",
    "title": "注册用户",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>往数据库里加入一个新的用户，这里的Bitmap没有做压缩处理,这里没做重名判断</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Bitmap",
            "optional": false,
            "field": "image",
            "description": "<p>用户头像</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "sex",
            "description": "<p>0 女 1 男</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "githubname",
            "description": "<p>github名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>个人简介</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>密码</p>"
          },
          {
            "group": "Parameter",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "201": [
          {
            "group": "201",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "201",
            "type": "boolean",
            "optional": false,
            "field": "code",
            "description": "<p>1 代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"0\",\"msg\":\"注册成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "PostRegister"
  },
  {
    "type": "POST",
    "url": "/users/:account",
    "title": "修改(完善)用户头像",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>修改(完善)用户头像</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          },
          {
            "group": "200",
            "type": "Bitmap",
            "optional": false,
            "field": "bitmap",
            "description": "<p>新头像</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"修改成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "PostUsersAccount"
  },
  {
    "type": "POST",
    "url": "/users/:acount",
    "title": "修改(完善)用户信息",
    "group": "Users",
    "version": "0.0.1",
    "description": "<p>修改(完善)用户信息</p>",
    "parameter": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "name",
            "description": "<p>昵称</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": true,
            "field": "sex",
            "description": "<p>性别</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "githubname",
            "description": "<p>github名</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "description",
            "description": "<p>个人简介</p>"
          },
          {
            "group": "200",
            "type": "String",
            "optional": true,
            "field": "email",
            "description": "<p>邮箱</p>"
          },
          {
            "group": "200",
            "type": "Connection",
            "optional": false,
            "field": "conn",
            "description": "<p>连接</p>"
          }
        ],
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "account",
            "description": "<p>用户名</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>信息</p>"
          },
          {
            "group": "200",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>1代表无错误 0代表有错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "返回样例:",
          "content": "{\"code\":\"1\",\"msg\":\"修改成功\"}",
          "type": "json"
        }
      ]
    },
    "filename": "./DB.java",
    "groupTitle": "Users",
    "name": "PostUsersAcount"
  }
] });
