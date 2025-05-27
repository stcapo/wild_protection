# 野生动物保护网站重构指南

## 重构概述

本次重构实现了以下主要功能：

### 1. 用户角色系统
- 在数据库中添加了用户角色字段（admin/user）
- 实现了管理员和普通用户的登录区分
- 添加了管理员专用登录页面

### 2. 管理员后台系统
- 创建了管理员后台主页（admin-dashboard.html）
- 实现了物种管理功能（增删改查）
- 添加了管理员权限验证过滤器

### 3. 数据库驱动的内容管理
- 创建了濒危物种数据表
- 将前端页面从静态内容改为动态加载
- 提供了RESTful API接口

## 新增文件列表

### 前端页面
- `admin-login.html` - 管理员登录页面
- `admin-dashboard.html` - 管理员后台主页

### 后端Java类
- `AdminLoginServlet.java` - 管理员登录处理
- `AdminSpeciesServlet.java` - 物种管理API
- `SpeciesApiServlet.java` - 物种数据API
- `EndangeredSpecies.java` - 濒危物种模型
- `EndangeredSpeciesDAO.java` - 濒危物种数据访问
- `AdminAuthenticationFilter.java` - 管理员权限过滤器

### 数据库更新
- 更新了 `database.sql` 文件，添加了角色字段和物种表

## 功能特性

### 登录系统改进
1. **普通用户登录**：
   - 访问 `login.html`
   - 登录后跳转到 `home.html`
   - 右上角有"管理员登录"链接

2. **管理员登录**：
   - 访问 `admin-login.html`
   - 只有admin角色用户可以登录
   - 登录后跳转到 `admin-dashboard.html`

### 管理员后台功能
1. **仪表盘**：
   - 显示系统统计信息
   - 快速操作按钮
   - 系统概览

2. **物种管理**：
   - 查看所有濒危物种列表
   - 添加新物种
   - 编辑现有物种信息
   - 删除物种记录

### 前端动态化
1. **濒危物种页面**：
   - 从数据库动态加载物种数据
   - 支持图片加载失败处理
   - 响应式布局优化

## 测试步骤

### 1. 数据库设置
```sql
-- 运行更新后的 database.sql 文件
-- 这将创建新的表结构和示例数据
```

### 2. 编译和部署
```bash
mvn clean package
# 将生成的 WAR 文件部署到 Tomcat
```

### 3. 测试用户登录
- 普通用户：username: `user`, password: `user123`
- 管理员：username: `admin`, password: `admin123`

### 4. 测试功能
1. **普通用户流程**：
   - 访问 `login.html`
   - 使用普通用户账号登录
   - 查看 `endangered-species.html` 页面的动态数据

2. **管理员流程**：
   - 访问 `admin-login.html`
   - 使用管理员账号登录
   - 在后台管理物种信息
   - 测试添加、编辑、删除功能

## API接口

### 物种数据API
- `GET /api/species` - 获取所有物种数据
- `POST /admin/species` - 管理物种（需要管理员权限）
  - `action=add` - 添加物种
  - `action=update` - 更新物种
  - `action=delete` - 删除物种

## 安全特性

1. **权限控制**：
   - 管理员页面需要admin角色验证
   - 普通用户无法访问管理功能

2. **会话管理**：
   - 30分钟会话超时
   - 安全的登录验证

3. **数据验证**：
   - 前端表单验证
   - 后端数据校验

## 下一步扩展

1. **其他内容管理**：
   - 新闻动态管理
   - 志愿服务管理
   - 捐赠项目管理

2. **用户管理**：
   - 用户列表查看
   - 用户权限管理
   - 用户活动统计

3. **系统设置**：
   - 网站配置管理
   - 系统参数设置
   - 日志管理

## 注意事项

1. 确保数据库连接配置正确
2. 检查所有依赖包是否正确导入
3. 验证Tomcat部署配置
4. 测试所有API接口的响应

这次重构成功地将静态网站转换为动态的、可管理的系统，为后续功能扩展奠定了良好的基础。
