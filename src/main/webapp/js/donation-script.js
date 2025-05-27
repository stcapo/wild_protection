// 捐赠页面脚本
document.addEventListener('DOMContentLoaded', function() {
    // 处理自定义金额的显示/隐藏
    const amountCustom = document.getElementById('amountCustom');
    const customAmountContainer = document.getElementById('customAmountContainer');
    
    document.querySelectorAll('input[name="donationAmount"]').forEach(function(radio) {
        radio.addEventListener('change', function() {
            if(amountCustom.checked) {
                customAmountContainer.style.display = 'block';
            } else {
                customAmountContainer.style.display = 'none';
            }
        });
    });
    
    // 更新确认模态框中的金额和项目
    const donationConfirmBtn = document.querySelector('[data-bs-target="#donationConfirmModal"]');
    if (donationConfirmBtn) {
        donationConfirmBtn.addEventListener('click', function() {
            // 获取金额
            let amount = document.querySelector('input[name="donationAmount"]:checked').value;
            if (amount === 'custom') {
                amount = document.getElementById('customAmount').value || '0';
            }
            
            // 获取项目
            const projectSelect = document.getElementById('projectSelect');
            const projectName = projectSelect.options[projectSelect.selectedIndex].text;
            
            // 获取支付方式
            let paymentMethod = '微信支付';
            if (document.getElementById('aliPay').checked) {
                paymentMethod = '支付宝';
            } else if (document.getElementById('bankTransfer').checked) {
                paymentMethod = '银行转账';
            }
            
            // 更新模态框内容
            const amountEl = document.querySelector('.donation-summary .fw-bold');
            if (amountEl) {
                amountEl.textContent = '¥' + amount + '.00';
            }
            
            const spans = document.querySelectorAll('.donation-summary span');
            if (spans.length > 1) {
                spans[1].textContent = projectName;
            }
            if (spans.length > 2) {
                spans[2].textContent = paymentMethod;
            }
        });
    }
});