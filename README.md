# WP - Java Web Application

一个简单的Java Web应用程序，使用MySQL数据库进行用户认证。

A simple Java web application with user authentication using MySQL database.

## 环境要求 (Prerequisites)

- JDK 1.8
- MySQL 5.7
- Apache Tomcat 8.5+
- Maven 3.6+

## 项目结构 (Project Structure)

```
wp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── wp/
│   │   │           ├── controller/
│   │   │           │   ├── LoginServlet.java
│   │   │           │   └── LogoutServlet.java
│   │   │           ├── dao/
│   │   │           │   └── UserDAO.java
│   │   │           ├── filter/
│   │   │           │   └── AuthenticationFilter.java
│   │   │           ├── model/
│   │   │           │   └── User.java
│   │   │           ├── service/
│   │   │           └── util/
│   │   │               └── DBUtil.java
│   │   ├── resources/
│   │   │   ├── db.properties
│   │   │   └── database.sql
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── css/
│   │       ├── js/
│   │       ├── images/
│   │       ├── login.html
│   │       └── home.html
└── pom.xml
```

## 数据库设置 (Database Setup)

1. 打开MySQL命令行或MySQL客户端（如MySQL Workbench）
2. 运行位于`src/main/resources/database.sql`的SQL脚本

## 构建和运行 (Building and Running)

1. 使用Maven构建项目:
   ```
   mvn clean package
   ```

2. 将生成的WAR文件部署到Tomcat:
   - 将`target/wp.war`文件复制到Tomcat的`webapps`目录
   - 启动Tomcat服务器

3. 访问应用程序:
   ```
   http://localhost:8080/wp/
   ```

## 演示账号 (Demo Accounts)

- 用户名: admin, 密码: admin123
- 用户名: user, 密码: user123

## 功能特点 (Features)

- 用户认证（登录/退出）
- 会话管理
- 受保护页面需要认证
- 使用Bootstrap的响应式UI

## 使用的技术 (Technologies Used)

- Java Servlets
- MySQL数据库
- HTML/CSS/JavaScript
- Bootstrap 5
- jQuery
- Font Awesome
- Maven 

请仔细阅读我的项目，重点关注我项目的页面上的数据是如何渲染和处理的，以及各个组件之间的逻辑。
目前的项目没后台管理页面，首先需要在登录页面中的右上角加一个选项”管理员登录“，用户点击后可以跳转到管理员登录页面，该页面的UI风格要求只要与里login.html 稍有区别即可，接着可能需要在系统中加入区分管理员和普通用户登录的逻辑区分，
这样的验证可以在登录后导航到不同的页面，在用户登录界面即当前已经存在的login.html文件登录后跳转到home.html，而管理员页面登录后需要重新编写跳转页面
管理员的后台页面功能如下：
样例：
1. 物种保护管理-- 管理员在后台可以对对应的前台页面（endangered-species.html)物种保护页面显示的内容进行修改，修改内容包括图片的url地址，物种名称以及物种描述，在这之前，我希望把整个系统重构，前端显示的信息需要从数据库中查询并加载，数据库文件为database.sql,你可以在该文件中添加和修改相应的代码。所以后台管理的逻辑可以顺理成章
其他信息和页面也同理，类比第一个进行修改和添加后台管理页面！现在开始重构！