/**
 * 野生动物保护网 - 保护动态页面 JavaScript 功能
 * 实现新闻搜索、分类过滤、分页、订阅等功能
 */

document.addEventListener('DOMContentLoaded', function() {
    // 初始化页面
    initPage();

    // 设置图片占位符
    setupImagePlaceholders();
    
    // 搜索功能
    setupSearch();
    
    // 新闻分类筛选
    setupCategoryFilter();
    
    // 标签过滤
    setupTagFilter();
    
    // 分页功能
    setupPagination();
    
    // 订阅功能
    setupSubscription();
    
    // 文章阅读功能
    setupArticleReading();
});

/**
 * 初始化页面，加载新闻数据
 */
function initPage() {
    // 加载并显示新闻数据
    const newsData = getNewsData();
    
    // 在控制台输出数据（开发调试用）
    console.log('加载新闻数据:', newsData);
    
    // 这里可以执行其他初始化操作
}

/**
 * 设置图片占位符
 * 在实际项目中，这些图片会从服务器加载
 */
function setupImagePlaceholders() {
    const imagePlaceholders = {
        'featured-news-img': 'https://via.placeholder.com/800x400?text=青海湖保护区新增鸟类品种',
        'news-img': [
            'https://via.placeholder.com/400x200?text=大熊猫野外种群数量增长',
            'https://via.placeholder.com/400x200?text=云南发现非法贩卖野生动物案件',
            'https://via.placeholder.com/400x200?text=新研究揭示气候变化对迁徙鸟类影响',
            'https://via.placeholder.com/400x200?text=我国将加强湿地保护力度'
        ]
    };
    
    // 设置特色新闻图片
    const featuredImg = document.querySelector('.featured-news-img');
    if (featuredImg) {
        featuredImg.src = imagePlaceholders['featured-news-img'];
        featuredImg.alt = '青海湖保护区新增鸟类品种';
    }
    
    // 设置普通新闻图片
    const newsImgs = document.querySelectorAll('.news-img');
    newsImgs.forEach((img, index) => {
        if (index < imagePlaceholders['news-img'].length) {
            img.src = imagePlaceholders['news-img'][index];
        }
    });
}

/**
 * 配置搜索功能
 */
function setupSearch() {
    const searchBox = document.querySelector('.search-box');
    const searchBtn = document.querySelector('.search-btn');
    
    if (!searchBox || !searchBtn) return;
    
    // 搜索按钮点击事件
    searchBtn.addEventListener('click', function() {
        performSearch();
    });
    
    // 输入框回车事件
    searchBox.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            performSearch();
        }
    });
    
    // 执行搜索
    function performSearch() {
        const searchTerm = searchBox.value.trim();
        if (searchTerm === '') {
            // 如果搜索词为空，显示所有新闻
            showToast('请输入搜索关键词');
            return;
        }
        
        // 模拟搜索结果
        showToast(`正在搜索: "${searchTerm}"`, 'info');
        
        // 在实际项目中，这里会发送请求到服务器进行搜索
        // 为演示，我们使用模拟数据
        simulateSearchResults(searchTerm);
    }
    
    // 模拟搜索结果
    function simulateSearchResults(term) {
        // 获取所有新闻卡片
        const newsCards = document.querySelectorAll('.news-card');
        let foundResults = false;
        
        // 简单过滤：检查标题是否包含搜索词
        newsCards.forEach(card => {
            const title = card.querySelector('.card-title').textContent.toLowerCase();
            const content = card.querySelector('.card-text').textContent.toLowerCase();
            
            if (title.includes(term.toLowerCase()) || content.includes(term.toLowerCase())) {
                card.style.display = 'block';
                foundResults = true;
                // 高亮匹配文本（简化示例）
                card.classList.add('border-success');
                setTimeout(() => card.classList.remove('border-success'), 2000);
            } else {
                card.style.display = 'none';
            }
        });
        
        // 显示搜索结果消息
        if (foundResults) {
            showToast(`找到与 "${term}" 相关的结果`, 'success');
        } else {
            showToast(`未找到与 "${term}" 相关的结果`, 'warning');
            // 没有结果时，2秒后恢复显示所有新闻
            setTimeout(() => {
                newsCards.forEach(card => card.style.display = 'block');
            }, 2000);
        }
    }
}

/**
 * 配置新闻分类筛选功能
 */
function setupCategoryFilter() {
    const categoryLinks = document.querySelectorAll('.categories-list .list-group-item a');
    
    categoryLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 移除所有类别的active状态
            categoryLinks.forEach(l => l.parentElement.classList.remove('active'));
            
            // 设置当前类别为active
            this.parentElement.classList.add('active');
            
            // 获取分类名称
            const category = this.textContent.trim();
            
            // 显示选择的分类
            showToast(`正在显示分类: ${category}`, 'info');
            
            // 模拟过滤结果
            filterNewsByCategory(category);
        });
    });
    
    // 模拟按分类过滤新闻
    function filterNewsByCategory(category) {
        // 获取所有新闻卡片
        const newsCards = document.querySelectorAll('.news-card');
        
        // 如果是全部新闻，显示所有
        if (category === '全部新闻') {
            newsCards.forEach(card => {
                card.style.display = 'block';
                card.style.opacity = '1';
            });
            return;
        }
        
        // 否则，根据分类标签过滤
        newsCards.forEach(card => {
            const badge = card.querySelector('.news-badge');
            if (badge && badge.textContent.includes(category)) {
                card.style.display = 'block';
                // 添加淡入效果
                card.style.opacity = '0';
                setTimeout(() => {
                    card.style.opacity = '1';
                }, 100);
            } else {
                card.style.display = 'none';
            }
        });
    }
}

/**
 * 配置标签过滤功能
 */
function setupTagFilter() {
    const tagLinks = document.querySelectorAll('.card-body .badge');
    
    tagLinks.forEach(tag => {
        tag.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 获取标签文本
            const tagText = this.textContent.trim();
            
            // 显示选择的标签
            showToast(`正在显示标签: ${tagText}`, 'info');
            
            // 模拟标签过滤
            // 实际项目中，这里会根据标签请求相关新闻
            simulateTagFiltering(tagText);
        });
    });
    
    // 模拟标签过滤
    function simulateTagFiltering(tag) {
        // 将所有标签重置为浅色
        tagLinks.forEach(t => {
            t.classList.remove('bg-success');
            t.classList.add('bg-light');
            t.classList.add('text-dark');
        });
        
        // 高亮选中的标签
        document.querySelectorAll(`.badge:contains('${tag}')`).forEach(t => {
            t.classList.remove('bg-light', 'text-dark');
            t.classList.add('bg-success', 'text-white');
        });
        
        // 添加高亮效果到所有相关新闻卡片
        const newsCards = document.querySelectorAll('.news-card');
        let hasHighlighted = false;
        
        newsCards.forEach(card => {
            const title = card.querySelector('.card-title').textContent;
            const content = card.querySelector('.card-text').textContent;
            
            // 简单匹配：如果标题或内容包含标签关键词
            if (title.toLowerCase().includes(tag.toLowerCase().replace('#', '')) || 
                content.toLowerCase().includes(tag.toLowerCase().replace('#', ''))) {
                card.classList.add('border', 'border-success');
                hasHighlighted = true;
                
                // 2秒后移除高亮
                setTimeout(() => {
                    card.classList.remove('border', 'border-success');
                }, 2000);
            }
        });
        
        if (!hasHighlighted) {
            showToast(`未找到与标签 "${tag}" 相关的新闻`, 'warning');
        }
    }
    
    // 添加jQuery-like contains选择器功能
    document.querySelectorAll = function(selector) {
        if (selector.includes(':contains')) {
            const parts = selector.split(':contains');
            const baseSelector = parts[0];
            const searchText = parts[1].replace(/['"()]/g, '');
            
            const allElements = document.querySelectorAll(baseSelector);
            const filteredElements = Array.from(allElements).filter(el => 
                el.textContent.includes(searchText)
            );
            
            return filteredElements;
        }
        return document.querySelectorAll(selector);
    };
}

/**
 * 配置分页功能
 */
function setupPagination() {
    const pageLinks = document.querySelectorAll('.pagination .page-link');
    
    pageLinks.forEach(link => {
        if (link.parentElement.classList.contains('disabled')) {
            return; // 跳过禁用的分页项
        }
        
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 移除所有页面的active状态
            document.querySelectorAll('.pagination .page-item').forEach(item => {
                item.classList.remove('active');
            });
            
            const pageText = this.textContent.trim();
            
            // 处理上一页、下一页和具体页码
            if (pageText === '上一页') {
                const activeItem = document.querySelector('.pagination .page-item.active');
                if (activeItem && activeItem.previousElementSibling) {
                    activeItem.previousElementSibling.classList.add('active');
                }
            } else if (pageText === '下一页') {
                const activeItem = document.querySelector('.pagination .page-item.active');
                if (activeItem && activeItem.nextElementSibling) {
                    activeItem.nextElementSibling.classList.add('active');
                }
            } else {
                // 具体页码
                this.parentElement.classList.add('active');
            }
            
            // 显示当前页
            const currentPage = document.querySelector('.pagination .page-item.active .page-link').textContent;
            showToast(`正在加载第 ${currentPage} 页`, 'info');
            
            // 模拟页面切换
            simulatePageChange(currentPage);
        });
    });
    
    // 模拟页面切换
    function simulatePageChange(page) {
        // 模拟加载效果
        const newsContainer = document.querySelector('.row:has(.news-card)');
        if (newsContainer) {
            newsContainer.style.opacity = '0.5';
            
            // 0.5秒后"加载"完成
            setTimeout(() => {
                newsContainer.style.opacity = '1';
                showToast(`已加载第 ${page} 页内容`, 'success');
            }, 500);
        }
    }
}

/**
 * 配置订阅功能
 */
function setupSubscription() {
    const subscribeForm = document.querySelector('.card-body form');
    const emailInput = document.querySelector('.card-body form input[type="email"]');
    const subscribeBtn = document.querySelector('.card-body form button');
    
    if (!subscribeForm || !emailInput || !subscribeBtn) return;
    
    subscribeBtn.addEventListener('click', function(e) {
        e.preventDefault();
        
        const email = emailInput.value.trim();
        
        // 简单的邮箱验证
        if (!isValidEmail(email)) {
            showToast('请输入有效的邮箱地址', 'warning');
            emailInput.classList.add('is-invalid');
            return;
        }
        
        // 移除错误状态
        emailInput.classList.remove('is-invalid');
        
        // 显示订阅成功消息
        showToast('订阅成功！感谢您的关注', 'success');
        
        // 清空输入框
        emailInput.value = '';
        
        // 实际项目中，这里会发送请求到服务器处理订阅
    });
    
    // 简单的邮箱验证
    function isValidEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
}

/**
 * 配置文章阅读功能
 */
function setupArticleReading() {
    // 所有"阅读更多"和"阅读全文"按钮
    const readMoreBtns = document.querySelectorAll('a.btn:contains("阅读")');
    
    readMoreBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 获取文章标题
            let title = '文章';
            const card = btn.closest('.card');
            const featured = btn.closest('.featured-news');
            
            if (card) {
                const titleElement = card.querySelector('.card-title');
                if (titleElement) {
                    title = titleElement.textContent;
                }
            } else if (featured) {
                const titleElement = featured.querySelector('h1');
                if (titleElement) {
                    title = titleElement.textContent;
                }
            }
            
            // 显示阅读消息
            showToast(`正在加载: "${title}"`, 'info');
            
            // 模拟打开文章详情
            showArticleModal(title);
        });
    });
    
    // 模拟显示文章详情模态框
    function showArticleModal(title) {
        // 创建模态框元素
        const modal = document.createElement('div');
        modal.className = 'modal fade';
        modal.id = 'articleModal';
        modal.tabIndex = '-1';
        modal.setAttribute('aria-labelledby', 'articleModalLabel');
        modal.setAttribute('aria-hidden', 'true');
        
        // 设置模态框内容
        modal.innerHTML = `
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="articleModalLabel">${title}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <img src="https://via.placeholder.com/800x400?text=${encodeURIComponent(title)}" class="img-fluid mb-3" alt="${title}">
                        <p class="text-muted"><i class="far fa-calendar-alt me-1"></i> 2024年3月15日 | <i class="far fa-user me-1"></i> 野生动物保护网</p>
                        <p>这是"${title}"的详细内容。在实际项目中，这里会显示完整的文章内容。</p>
                        <p>野生动物保护是保护地球生物多样性的重要组成部分。通过保护濒危动物和它们的栖息地，我们不仅能够保存珍贵的基因资源，还能维护生态系统的平衡与稳定。</p>
                        <p>本文将详细介绍相关保护措施、研究成果以及未来展望。</p>
                        <div class="alert alert-success mt-3">
                            <i class="fas fa-info-circle me-1"></i> 本文内容仅供示例，完整内容将在实际项目中显示。
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary share-btn"><i class="fas fa-share-alt me-1"></i> 分享</button>
                    </div>
                </div>
            </div>
        `;
        
        // 将模态框添加到页面
        document.body.appendChild(modal);
        
        // 显示模态框
        const modalInstance = new bootstrap.Modal(modal);
        modalInstance.show();
        
        // 模态框关闭时移除元素
        modal.addEventListener('hidden.bs.modal', function() {
            document.body.removeChild(modal);
        });
        
        // 分享按钮功能
        const shareBtn = modal.querySelector('.share-btn');
        if (shareBtn) {
            shareBtn.addEventListener('click', function() {
                showToast('分享功能已触发', 'info');
                // 实际项目中，这里会实现社交媒体分享功能
            });
        }
    }
}

/**
 * 获取模拟新闻数据
 * 实际项目中，这会从服务器API获取
 */
function getNewsData() {
    return [
        {
            id: 1,
            title: '青海湖保护区新增鸟类品种',
            category: '重要发现',
            date: '2024-03-15',
            summary: '青海湖国家级自然保护区日前宣布，经过多年监测，保护区记录到的鸟类种类达189种，较建区初期增加了26种。',
            featured: true
        },
        {
            id: 2,
            title: '大熊猫野外种群数量增长',
            category: '保护成果',
            date: '2024-03-10',
            summary: '最新监测数据显示，我国野生大熊猫数量已达1800余只，较十年前增长了16.8%。'
        },
        {
            id: 3,
            title: '云南发现非法贩卖野生动物案件',
            category: '紧急状况',
            date: '2024-03-08',
            summary: '执法部门在云南边境查获一起大规模野生动物走私案，解救多种珍稀保护动物。'
        },
        {
            id: 4,
            title: '新研究揭示气候变化对迁徙鸟类影响',
            category: '研究成果',
            date: '2024-03-05',
            summary: '中国科学院最新研究表明，气候变化正在改变多种候鸟的迁徙路线和时间。'
        },
        {
            id: 5,
            title: '我国将加强湿地保护力度',
            category: '政策动态',
            date: '2024-03-01',
            summary: '国家林草局发布新政策，未来五年将新建50处湿地保护区，恢复湿地面积200万公顷。'
        }
    ];
}

/**
 * 显示消息提示（Toast）
 */
function showToast(message, type = 'success') {
    // 检查是否已存在Toast容器
    let toastContainer = document.querySelector('.toast-container');
    if (!toastContainer) {
        // 创建Toast容器
        toastContainer = document.createElement('div');
        toastContainer.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        document.body.appendChild(toastContainer);
    }
    
    // 创建唯一ID
    const id = 'toast-' + Date.now();
    
    // 根据类型设置背景色
    let bgClass = 'bg-success';
    switch(type) {
        case 'warning':
            bgClass = 'bg-warning';
            break;
        case 'danger':
            bgClass = 'bg-danger';
            break;
        case 'info':
            bgClass = 'bg-info';
            break;
    }
    
    // 创建Toast元素
    const toastElement = document.createElement('div');
    toastElement.className = `toast show ${bgClass} text-white`;
    toastElement.id = id;
    toastElement.setAttribute('role', 'alert');
    toastElement.setAttribute('aria-live', 'assertive');
    toastElement.setAttribute('aria-atomic', 'true');
    
    toastElement.innerHTML = `
        <div class="toast-header">
            <strong class="me-auto">保护动态</strong>
            <small>现在</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            ${message}
        </div>
    `;
    
    // 添加到容器
    toastContainer.appendChild(toastElement);
    
    // 3秒后自动关闭
    setTimeout(() => {
        const toast = document.getElementById(id);
        if (toast) {
            toast.classList.remove('show');
            setTimeout(() => {
                toastContainer.removeChild(toast);
                // 如果没有更多toast，移除容器
                if (toastContainer.children.length === 0) {
                    document.body.removeChild(toastContainer);
                }
            }, 300);
        }
    }, 3000);
}
