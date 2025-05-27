-- Create database
Drop database wp_db;
CREATE DATABASE IF NOT EXISTS wp_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wp_db;

-- Create users table with role field
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('user', 'admin') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample admin user (username: admin, password: admin123)
INSERT INTO users (username, password, email, full_name, role)
VALUES ('admin', 'admin123', 'admin@example.com', '系统管理员', 'admin')
ON DUPLICATE KEY UPDATE role = 'admin';

-- Insert sample regular user (username: user, password: user123)
INSERT INTO users (username, password, email, full_name, role)
VALUES ('user', 'user123', 'user@example.com', '普通用户', 'user')
ON DUPLICATE KEY UPDATE role = 'user';

-- Insert additional test users
INSERT INTO users (username, password, email, full_name, role) VALUES
('zhangsan', 'password123', 'zhangsan@example.com', '张三', 'user'),
('lisi', 'password123', 'lisi@example.com', '李四', 'user'),
('wangwu', 'password123', 'wangwu@example.com', '王五', 'user'),
('zhaoliu', 'password123', 'zhaoliu@example.com', '赵六', 'user'),
('volunteer1', 'vol123', 'volunteer1@example.com', '志愿者小明', 'user'),
('volunteer2', 'vol123', 'volunteer2@example.com', '志愿者小红', 'user'),
('researcher', 'res123', 'researcher@example.com', '研究员博士', 'user'),
('admin2', 'admin456', 'admin2@example.com', '副管理员', 'admin')
ON DUPLICATE KEY UPDATE username = VALUES(username);

-- Create endangered species table
CREATE TABLE IF NOT EXISTS endangered_species (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(100),
    image_url TEXT,
    description TEXT,
    conservation_status VARCHAR(50),
    population_count VARCHAR(100),
    habitat VARCHAR(200),
    threats TEXT,
    conservation_efforts TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample endangered species data
INSERT INTO endangered_species (name, scientific_name, image_url, description, conservation_status, population_count, habitat, threats, conservation_efforts) VALUES
('犀牛', 'Rhinocerotidae', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3628/image/p1300x1300/Rhinocerous0002_reduced.webp',
'犀牛是地球上最古老的哺乳动物之一，目前全球仅存约2.7万只。由于非法盗猎和栖息地丧失，犀牛种群数量持续下降。犀牛角在传统医药中的使用需求，导致盗猎活动猖獗。',
'极度濒危', '约27,000只', '非洲和亚洲的草原、森林', '非法盗猎、栖息地丧失', '反盗猎巡逻、栖息地保护、国际合作'),

('远东豹', 'Panthera pardus orientalis', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3803/image/p1300x1300/leopard_2.webp',
'远东豹是世界上最稀有的豹亚种之一，主要分布在俄罗斯远东地区和中国东北部。由于栖息地破碎化和人类活动的影响，其种群数量急剧下降。目前野外数量不足100只。',
'极度濒危', '不足100只', '俄罗斯远东地区和中国东北部森林', '栖息地破碎化、人类活动干扰', '建立保护区、跨国合作保护'),

('猩猩', 'Pongo', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3848/image/p1300x1300/AdobeStock_384667533_431571.webp',
'猩猩是人类的近亲，具有高度的智慧和社会性。由于森林砍伐、疾病传播和非法捕猎，猩猩的生存受到严重威胁。目前野外数量持续下降。',
'极度濒危', '约104,000只', '东南亚热带雨林', '森林砍伐、疾病传播、非法捕猎', '栖息地保护、反盗猎、疾病防控'),

('大猩猩', 'Gorilla', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3980/image/p1300x1300/Mignon-Gorilla5_reduced.webp',
'大猩猩是人类的近亲，具有高度的智慧和社会性。由于森林砍伐、疾病传播和非法捕猎，大猩猩的生存受到严重威胁。山地大猩猩和东部低地大猩猩都处于极度濒危状态。',
'极度濒危', '约1,000只', '中非热带雨林', '森林砍伐、疾病传播、战争冲突', '栖息地保护、社区参与、疾病监控'),

('非洲森林象', 'Loxodonta cyclotis', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3635/image/p1300x1300/Ele_daloa4_reduced.webp',
'非洲森林象是非洲象的一个亚种，体型较小，象牙更直。由于栖息地丧失和非法捕猎，其种群数量持续下降。保护非洲森林象对于维持生态系统的平衡具有重要意义。',
'极度濒危', '约415,000只', '中非和西非森林', '象牙贸易、栖息地丧失', '反盗猎、象牙贸易禁令、栖息地保护'),

('剑角牛', 'Pseudoryx nghetinhensis', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3981/image/p1300x1300/WR202308_Saola_001_437460_edited.webp',
'剑角牛是1992年才被科学界发现的新物种，被称为"亚洲独角兽"。由于栖息地丧失和非法捕猎，这种神秘的动物正面临灭绝威胁。目前野外数量极少，保护工作刻不容缓。',
'极度濒危', '不足100只', '越南和老挝边境山区', '栖息地丧失、非法捕猎', '栖息地保护、反盗猎、科学研究'),

('鸮鹦鹉', 'Strigops habroptilus', 'https://d1jyxxz9imt9yb.cloudfront.net/medialib/3848/image/p1300x1300/AdobeStock_384667533_431571.webp',
'鸮鹦鹉是新西兰特有的夜行性鹦鹉，由于外来物种入侵和栖息地丧失，其种群数量急剧下降。目前仅存约200只，是世界上最濒危的鸟类之一。',
'极度濒危', '约200只', '新西兰原始森林', '外来物种入侵、栖息地丧失', '人工繁殖、外来物种控制、栖息地恢复');

-- Create volunteer activities table
CREATE TABLE IF NOT EXISTS volunteer_activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    activity_date DATE NOT NULL,
    location VARCHAR(200) NOT NULL,
    max_participants INT DEFAULT 50,
    current_participants INT DEFAULT 0,
    status ENUM('open', 'closed', 'completed') DEFAULT 'open',
    requirements TEXT,
    contact_info VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create volunteer registrations table
CREATE TABLE IF NOT EXISTS volunteer_registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    activity_id INT NOT NULL,
    user_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('registered', 'cancelled', 'completed') DEFAULT 'registered',
    notes TEXT,
    FOREIGN KEY (activity_id) REFERENCES volunteer_activities(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_registration (activity_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample volunteer activities
INSERT INTO volunteer_activities (title, description, activity_date, location, max_participants, requirements, contact_info) VALUES
('湿地保护行动', '参与江苏盐城湿地的生态保护工作，包括清理垃圾、植被恢复等活动。', '2024-04-05', '江苏盐城湿地', 40, '身体健康，年龄18-65岁，需自备防水靴', '联系人：张老师 13800138001'),
('森林防火宣传', '在长白山保护区进行森林防火知识宣传，向游客发放宣传资料。', '2024-04-12', '吉林长白山保护区', 60, '具备良好的沟通能力，普通话标准', '联系人：李老师 13800138002'),
('海洋垃圾清理', '组织志愿者清理海南三亚海滩的海洋垃圾，保护海洋生态环境。', '2024-04-19', '海南三亚海滩', 80, '会游泳者优先，需自备防晒用品', '联系人：王老师 13800138003'),
('濒危植物保护', '协助植物学家进行濒危植物的调查和保护工作。', '2024-04-26', '云南西双版纳', 25, '对植物学有基础了解，能够长时间野外工作', '联系人：赵老师 13800138004'),
('野生动物救助', '学习野生动物救助技能，参与受伤动物的救治工作。', '2024-05-03', '北京野生动物救助中心', 30, '有爱心，不怕脏累，需接受专业培训', '联系人：陈老师 13800138005'),
('环保教育活动', '在小学开展环保教育活动，向儿童普及环保知识。', '2024-05-10', '上海市各小学', 50, '有教学经验或师范专业背景优先', '联系人：刘老师 13800138006'),
('湿地鸟类调查', '协助专业人员进行鄱阳湖湿地鸟类种群调查。', '2024-05-17', '江西鄱阳湖', 20, '早起不困难，有望远镜使用经验', '联系人：周老师 13800138007'),
('生态修复项目', '参与山西吕梁山的生态修复工程，植树造林。', '2024-05-24', '山西吕梁山', 100, '身体健康，能够进行体力劳动', '联系人：吴老师 13800138008');

-- Create wildlife observation guides table
CREATE TABLE IF NOT EXISTS wildlife_guides (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(100),
    icon VARCHAR(100),
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample wildlife observation guides
INSERT INTO wildlife_guides (title, content, category, icon, display_order) VALUES
('选择合适的观察时间', '野生动物的活动时间因种类而异，清晨和黄昏是大多数动物最活跃的时间段。鸟类通常在日出后1-2小时内最为活跃，而许多哺乳动物则偏好黄昏时分觅食。了解目标动物的生活习性，选择最佳观察时机。', '基础准备', 'fas fa-clock', 1),
('穿着合适的服装', '选择与环境颜色相近的衣物，避免鲜艳的颜色，以减少对野生动物的干扰。推荐穿着棕色、绿色或灰色的服装。同时要注意保暖和防护，根据季节和地形选择合适的鞋子和外套。', '基础准备', 'fas fa-tshirt', 2),
('携带必备装备', '包括望远镜、相机、笔记本、指南针、急救包和足够的水和食物。望远镜是观察的核心工具，建议选择8x42或10x42规格。相机用于记录珍贵瞬间，笔记本记录观察数据。', '装备指南', 'fas fa-backpack', 3),
('了解观察区域', '提前研究观察区域的地形、气候和常见野生动物，制定合理的观察路线。查阅相关资料，了解当地的生态环境和动物分布情况，这将大大提高观察成功率。', '前期准备', 'fas fa-map', 4),
('保持安静', '尽量减少噪音，轻声交谈，避免突然的动作，以免惊吓野生动物。关闭手机铃声，穿着不会发出摩擦声的衣物。移动时要缓慢而谨慎，避免踩断树枝或踢到石头。', '观察技巧', 'fas fa-volume-mute', 5),
('使用望远镜', '望远镜可以帮助您在不打扰动物的情况下进行远距离观察，选择适合的放大倍数。学会正确调节焦距和瞳距，保持稳定的握持姿势。在使用前要熟悉望远镜的各项功能。', '装备使用', 'fas fa-binoculars', 6),
('记录观察结果', '使用笔记本或手机记录观察到的动物种类、数量、行为和栖息地信息。记录时间、地点、天气条件等环境因素。详细的记录对科学研究和个人经验积累都很有价值。', '数据记录', 'fas fa-clipboard', 7),
('避免干扰动物', '不要试图接近或触摸野生动物，保持安全距离，尊重它们的自然行为。不要投喂野生动物，这会改变它们的自然习性。观察时要有耐心，让动物自然地展现行为。', '伦理规范', 'fas fa-hand-paper', 8),
('注意天气变化', '提前查看天气预报，携带防雨装备，避免在恶劣天气下进行观察。雨天虽然不利于观察，但雨后往往是动物活动的好时机。要学会根据天气调整观察计划。', '安全须知', 'fas fa-cloud-rain', 9),
('遵守当地法规', '了解并遵守观察区域的保护法规，不进入禁止区域，不破坏自然环境。尊重当地社区的规定，获得必要的许可证。保护环境是每个观察者的责任。', '法规遵守', 'fas fa-gavel', 10),
('团队协作', '如果是团队观察，提前分配任务，保持沟通，确保每个人的安全。建立手势信号系统，避免大声交流。团队合作可以提高观察效率和安全性。', '团队合作', 'fas fa-users', 11),
('识别动物踪迹', '学习识别动物的足迹、粪便和巢穴，这些线索可以帮助您找到动物。不同动物留下的痕迹各有特点，掌握这些知识能大大提高发现动物的概率。', '追踪技巧', 'fas fa-paw', 12),
('使用伪装技巧', '在观察点搭建简易伪装，如使用树枝和树叶，以减少被动物发现的概率。选择合适的隐蔽位置，利用自然地形和植被作为掩护。耐心等待是成功的关键。', '伪装技术', 'fas fa-mask', 13),
('注意个人卫生', '在野外避免随意丢弃垃圾，使用环保袋携带废弃物，保护自然环境。不要在水源附近使用肥皂或洗涤剂，尊重野生动物的生存环境。', '环保意识', 'fas fa-leaf', 14),
('学习急救知识', '掌握基本的急救技能，如处理割伤、扭伤和动物咬伤，确保自身安全。携带急救包，了解常见野外伤害的处理方法。在偏远地区观察时，急救知识尤为重要。', '安全防护', 'fas fa-first-aid', 15),
('观察鸟类技巧', '鸟类观察时，注意倾听鸟鸣声，使用鸟类图鉴识别种类，记录行为特征。不同季节鸟类的活动模式不同，迁徙季节是观鸟的最佳时机。', '专项技巧', 'fas fa-dove', 16),
('夜间观察准备', '夜间观察时，携带头灯或手电筒，注意避免强光直射动物，以免惊扰它们。使用红光滤镜可以减少对动物的影响。夜间观察需要更多的耐心和技巧。', '夜间观察', 'fas fa-moon', 17),
('观察水生动物', '在水边观察时，注意防滑，避免靠近深水区，使用防水装备保护电子设备。水生动物的观察需要特殊技巧，了解水体生态系统很重要。', '水域观察', 'fas fa-fish', 18),
('分享观察经验', '将您的观察结果和经验分享给其他爱好者，促进知识交流和环境保护意识。参加观察小组活动，与同好交流心得。分享不仅能帮助他人，也能提升自己。', '经验分享', 'fas fa-share', 19),
('持续学习', '通过书籍、纪录片和专家讲座不断学习野生动物知识，提升观察技能。关注最新的研究成果和保护动态，保持对自然世界的好奇心和学习热情。', '持续提升', 'fas fa-graduation-cap', 20);

-- Create conservation news table
CREATE TABLE IF NOT EXISTS conservation_news (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    summary VARCHAR(500),
    category VARCHAR(100),
    badge_color VARCHAR(50) DEFAULT 'bg-info',
    image_url TEXT,
    author VARCHAR(100),
    publish_date DATE NOT NULL,
    is_featured BOOLEAN DEFAULT FALSE,
    is_published BOOLEAN DEFAULT TRUE,
    view_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample conservation news
INSERT INTO conservation_news (title, content, summary, category, badge_color, image_url, author, publish_date, is_featured) VALUES
('大熊猫野外种群数量增长',
'最新监测数据显示，我国野生大熊猫数量已达1800余只，较十年前增长了16.8%。这一成果得益于国家对大熊猫栖息地的有效保护和科学管理。专家表示，大熊猫种群的稳定增长标志着我国野生动物保护工作取得了显著成效。

据国家林草局最新发布的第四次全国大熊猫调查结果，全国野生大熊猫种群数量达到1864只，比第三次调查时的1596只增加了268只，增长率为16.8%。大熊猫栖息地面积也从第三次调查的2.3万平方公里增加到2.58万平方公里。

保护区建设是大熊猫种群恢复的关键因素。目前，全国已建立大熊猫自然保护区67处，形成了以自然保护区为主体的大熊猫就地保护网络体系。同时，人工繁育技术的不断完善也为野外种群的补充提供了重要支撑。',
'最新监测数据显示，我国野生大熊猫数量已达1800余只，较十年前增长了16.8%。',
'保护成果', 'bg-success', 'https://images.unsplash.com/photo-1564349683136-77e08dba1ef7?w=400', '国家林草局', '2024-03-10', TRUE),

('云南发现非法贩卖野生动物案件',
'执法部门在云南边境查获一起大规模野生动物走私案，解救多种珍稀保护动物。此次行动共查获国家一级保护动物15只，二级保护动物32只，涉案金额超过500万元。

据云南省森林公安局通报，此次专项行动历时3个月，通过多部门联合执法，成功打掉了一个跨境野生动物走私犯罪团伙。被解救的动物包括穿山甲、蟒蛇、猕猴等多种珍稀物种，目前已全部送往专业救护机构进行康复治疗。

专家指出，野生动物非法贸易不仅威胁物种生存，还可能传播疾病，对公共卫生安全构成威胁。相关部门将继续加大打击力度，严厉惩处违法犯罪行为。',
'执法部门在云南边境查获一起大规模野生动物走私案，解救多种珍稀保护动物。',
'紧急状况', 'bg-danger', 'https://images.unsplash.com/photo-1544735716-392fe2489ffa?w=400', '云南森林公安', '2024-03-08', TRUE),

('新研究揭示气候变化对迁徙鸟类影响',
'中国科学院最新研究表明，气候变化正在改变多种候鸟的迁徙路线和时间。研究团队通过对近20年的鸟类迁徙数据分析发现，全球变暖导致许多鸟类提前开始春季迁徙，延迟秋季迁徙。

研究显示，气温上升使得鸟类的繁殖地和越冬地发生变化，一些物种被迫寻找新的栖息地。这种变化不仅影响鸟类的生存，也对整个生态系统产生连锁反应。

科研人员建议，应加强对候鸟迁徙路线的监测和保护，建立更多的候鸟保护区，为它们提供安全的中转站。同时，国际合作对于候鸟保护至关重要，需要各国共同努力应对气候变化带来的挑战。',
'中国科学院最新研究表明，气候变化正在改变多种候鸟的迁徙路线和时间。',
'研究成果', 'bg-info', 'https://images.unsplash.com/photo-1444927714506-8492d94b5ba0?w=400', '中科院研究团队', '2024-03-05', FALSE),

('我国将加强湿地保护力度',
'国家林草局发布新政策，未来五年将新建50处湿地保护区，恢复湿地面积200万公顷。这一举措旨在进一步加强湿地生态系统保护，维护生物多样性。

根据新发布的《全国湿地保护规划（2024-2030年）》，我国将投入专项资金300亿元，用于湿地保护和恢复工作。重点保护长江、黄河、珠江等重要流域的湿地生态系统，以及东北、华北、西北等地区的重要湿地。

湿地被誉为"地球之肾"，具有调节气候、净化水质、防洪抗旱等重要生态功能。目前我国湿地面积约5635万公顷，居世界第四位。通过加强保护，预计到2030年，我国湿地保护率将达到60%以上。',
'国家林草局发布新政策，未来五年将新建50处湿地保护区，恢复湿地面积200万公顷。',
'政策动态', 'bg-warning', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400', '国家林草局', '2024-03-01', FALSE),

('长江江豚保护取得新进展',
'最新调查显示，长江江豚种群数量止跌回升，目前约有1012头，比2012年增加了23%。这一成果标志着长江生态保护取得重要进展。

长江江豚是长江生态系统的旗舰物种，被誉为长江生态的"活化石"。近年来，通过实施长江禁渔、建立保护区、人工繁育等综合措施，江豚的生存环境得到明显改善。

专家表示，江豚数量的回升反映了长江水质的改善和鱼类资源的恢复。未来将继续加强长江流域生态保护，确保江豚等珍稀物种的长期生存。',
'最新调查显示，长江江豚种群数量止跌回升，目前约有1012头，比2012年增加了23%。',
'保护成果', 'bg-success', 'https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=400', '长江江豚保护联盟', '2024-02-28', FALSE),

('雪豹保护国际合作项目启动',
'中国与蒙古国、俄罗斯等国共同启动雪豹跨境保护合作项目，旨在加强雪豹栖息地的跨国保护。该项目将建立统一的监测体系，共享保护经验和技术。

雪豹是高山生态系统的顶级捕食者，目前全球野生雪豹数量仅有4000-6500只。由于栖息地破碎化和人类活动干扰，雪豹面临严重的生存威胁。

此次合作项目将重点关注雪豹的跨境迁徙通道保护，建立国际合作监测网络，开展联合科研和保护行动。预计项目实施后，将有效提升雪豹保护的整体效果。',
'中国与蒙古国、俄罗斯等国共同启动雪豹跨境保护合作项目，旨在加强雪豹栖息地的跨国保护。',
'国际合作', 'bg-primary', 'https://images.unsplash.com/photo-1551969014-7d2c4cddf0b6?w=400', '国际雪豹基金会', '2024-02-25', FALSE),

('海南热带雨林发现新物种',
'科研人员在海南热带雨林国家公园发现了3个植物新物种和2个昆虫新物种，进一步证实了海南热带雨林的生物多样性价值。这些新发现为保护区的科学管理提供了重要依据。

新发现的物种包括海南特有的兰科植物、苔藓植物以及两种特有的甲虫。这些物种的发现丰富了我们对热带雨林生态系统的认识，也突显了保护原始森林的重要性。

专家指出，新物种的不断发现说明我国生物多样性保护工作成效显著。海南热带雨林国家公园作为我国首批国家公园之一，将继续加强科研监测，为全球生物多样性保护贡献中国智慧。',
'科研人员在海南热带雨林国家公园发现了3个植物新物种和2个昆虫新物种。',
'科学发现', 'bg-info', 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=400', '海南大学科研团队', '2024-02-20', FALSE),

('东北虎豹国家公园野生东北虎数量创新高',
'东北虎豹国家公园管理局发布最新监测数据，园区内野生东北虎数量已达50只，东北豹数量达到60只，均创历史新高。这一成果得益于严格的保护措施和科学的管理方式。

通过红外相机监测、GPS项圈跟踪等现代技术手段，科研人员对园区内的大型猫科动物进行了全面监测。数据显示，东北虎豹的繁殖成功率显著提高，幼崽存活率也大幅提升。

国家公园的建立为东北虎豹提供了完整的栖息地保护，同时通过生态廊道建设，促进了中俄边境地区虎豹种群的基因交流。专家预测，在持续保护下，东北虎豹种群将继续稳定增长。',
'东北虎豹国家公园管理局发布最新监测数据，园区内野生东北虎数量已达50只。',
'保护成果', 'bg-success', 'https://images.unsplash.com/photo-1605979399824-6d3de7b0c4b5?w=400', '东北虎豹国家公园', '2024-02-15', TRUE);