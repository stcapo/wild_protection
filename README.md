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