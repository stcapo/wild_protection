// 社区论坛页面脚本
document.addEventListener('DOMContentLoaded', function() {
    console.log('Forum scripts loaded');
    
    // 发布新话题表单处理
    const newTopicForm = document.querySelector('.new-post-form');
    const publishButton = document.getElementById('publishTopicBtn');
    console.log('Init script looking for publish button:', publishButton);
    
    if (publishButton) {
        console.log('Publish button found');
        publishButton.addEventListener('click', function() {
            console.log('Publish button clicked');
            
            // 获取表单数据
            const title = document.getElementById('topicTitle').value;
            const category = document.getElementById('topicCategory').value;
            const content = document.getElementById('topicContent').value;
            const followTopic = document.getElementById('followTopic').checked;
            
            // 表单验证
            if (!title || title.length < 5) {
                showAlert('请输入至少5个字符的标题', 'danger');
                return;
            }
            
            if (!category) {
                showAlert('请选择话题分类', 'danger');
                return;
            }
            
            if (!content || content.length < 10) {
                showAlert('请输入至少10个字符的内容', 'danger');
                return;
            }
            
            // 模拟发布成功
            console.log('Publishing topic:', { title, category, content, followTopic });
            
            // 隐藏发布话题模态框
            const modal = bootstrap.Modal.getInstance(document.getElementById('newTopicModal'));
            modal.hide();
            
            // 显示成功模态框
            setTimeout(function() {
                const successModal = new bootstrap.Modal(document.getElementById('publishSuccessModal'));
                successModal.show();
            }, 500); // 延迟一点以确保上一个模态框完全隐藏
            
            // 模拟页面刷新，实际应用中应该发送到服务器然后刷新或更新页面
            setTimeout(function() {
                //window.location.reload();
                // 由于是展示项目，这里只清空表单而不刷新页面
                newTopicForm.reset();
            }, 2000);
        });
    } else {
        console.error('Publish button not found');
    }
    
    // 验证模态框事件绑定
    const newTopicModalElement = document.getElementById('newTopicModal');
    if (newTopicModalElement) {
        newTopicModalElement.addEventListener('shown.bs.modal', function () {
            console.log('Modal shown');
        });
    } else {
        console.error('New topic modal not found');
    }
    
    // 辅助函数：显示提示信息
    function showAlert(message, type) {
        // 创建提示框
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show forum-alert`;
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        
        // 添加到页面
        document.querySelector('.forum-container').prepend(alertDiv);
        
        // 2秒后自动关闭
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alertDiv);
            bsAlert.close();
        }, 2000);
    }
    
    // 话题过滤器
    const filterButtons = document.querySelectorAll('.nav-pills .nav-link');
    filterButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            // 移除所有按钮的active类
            filterButtons.forEach(btn => btn.classList.remove('active'));
            
            // 添加active类到当前按钮
            this.classList.add('active');
            
            // 在实际应用中，这里应该发送请求或过滤话题列表
            console.log('Filter topics by:', this.textContent.trim());
        });
    });
});