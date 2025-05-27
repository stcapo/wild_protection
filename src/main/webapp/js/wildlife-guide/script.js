document.addEventListener('DOMContentLoaded', function() {
    // Initialize image upload preview
    const uploadInput = document.getElementById('imageUpload');
    const previewContainer = document.getElementById('previewContainer');
    
    if (uploadInput && previewContainer) {
        uploadInput.addEventListener('change', function(event) {
            previewContainer.innerHTML = '';
            
            const files = event.target.files;
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                if (!file.type.startsWith('image/')) continue;
                
                const reader = new FileReader();
                reader.onload = function(e) {
                    const previewWrapper = document.createElement('div');
                    previewWrapper.className = 'preview-container mb-2';
                    
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.className = 'img-fluid';
                    
                    const removeBtn = document.createElement('div');
                    removeBtn.className = 'remove-preview';
                    removeBtn.innerHTML = '<i class="fas fa-times"></i>';
                    removeBtn.addEventListener('click', function() {
                        previewWrapper.remove();
                        // Reset file input if all previews are removed
                        if (previewContainer.children.length === 0) {
                            uploadInput.value = '';
                        }
                    });
                    
                    previewWrapper.appendChild(img);
                    previewWrapper.appendChild(removeBtn);
                    previewContainer.appendChild(previewWrapper);
                }
                reader.readAsDataURL(file);
            }
        });
    }
    
    // Form validation
    const observationForm = document.getElementById('observationForm');
    if (observationForm) {
        observationForm.addEventListener('submit', function(event) {
            event.preventDefault();
            
            // Simple form validation
            const requiredFields = observationForm.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    field.classList.add('is-invalid');
                } else {
                    field.classList.remove('is-invalid');
                }
            });
            
            if (isValid) {
                // Show success message
                const successAlert = document.createElement('div');
                successAlert.className = 'alert alert-success mt-3';
                successAlert.role = 'alert';
                successAlert.innerHTML = '<i class="fas fa-check-circle me-2"></i>观察记录已成功提交！感谢您对野生动物保护的贡献。';
                
                // Insert after form
                observationForm.after(successAlert);
                
                // Reset form
                observationForm.reset();
                
                // Clear previews
                if (previewContainer) {
                    previewContainer.innerHTML = '';
                }
                
                // Remove success message after 5 seconds
                setTimeout(() => {
                    successAlert.remove();
                }, 5000);
            }
        });
    }
    
    // Weather API placeholder
    const weatherWidget = document.getElementById('weatherWidget');
    if (weatherWidget) {
        // This would typically fetch from a weather API
        // For now, we'll just display placeholder content
        const locations = [
            { name: '北京海淀公园', condition: 'sunny', temp: 24 },
            { name: '青海湖自然保护区', condition: 'partly-cloudy', temp: 18 },
            { name: '云南西双版纳', condition: 'rain', temp: 26 },
            { name: '内蒙古呼伦贝尔', condition: 'clear', temp: 15 }
        ];
        
        const locationSelect = document.getElementById('locationWeather');
        if (locationSelect) {
            // Populate select options
            locations.forEach(location => {
                const option = document.createElement('option');
                option.value = location.name;
                option.textContent = location.name;
                locationSelect.appendChild(option);
            });
            
            // Update weather display when location is selected
            locationSelect.addEventListener('change', function() {
                const selectedLocation = locations.find(loc => loc.name === this.value);
                if (selectedLocation) {
                    updateWeatherDisplay(selectedLocation);
                }
            });
            
            // Set initial weather display
            updateWeatherDisplay(locations[0]);
        }
        
        function updateWeatherDisplay(location) {
            const weatherIcon = document.getElementById('weatherIcon');
            const weatherTemp = document.getElementById('weatherTemp');
            const weatherCondition = document.getElementById('weatherCondition');
            
            if (weatherIcon && weatherTemp && weatherCondition) {
                // Set icon based on condition
                let iconClass = 'fas fa-sun text-warning';
                switch(location.condition) {
                    case 'partly-cloudy':
                        iconClass = 'fas fa-cloud-sun text-secondary';
                        break;
                    case 'cloudy':
                        iconClass = 'fas fa-cloud text-secondary';
                        break;
                    case 'rain':
                        iconClass = 'fas fa-cloud-rain text-primary';
                        break;
                    case 'snow':
                        iconClass = 'fas fa-snowflake text-info';
                        break;
                    case 'clear':
                        iconClass = 'fas fa-moon text-warning';
                        break;
                }
                
                weatherIcon.className = iconClass + ' fa-3x mb-3';
                weatherTemp.textContent = `${location.temp}°C`;
                
                let conditionText = '晴朗';
                switch(location.condition) {
                    case 'partly-cloudy':
                        conditionText = '多云';
                        break;
                    case 'cloudy':
                        conditionText = '阴天';
                        break;
                    case 'rain':
                        conditionText = '降雨';
                        break;
                    case 'snow':
                        conditionText = '降雪';
                        break;
                    case 'clear':
                        conditionText = '晴朗';
                        break;
                }
                
                weatherCondition.textContent = conditionText;
            }
        }
    }
    
    // Initialize observation statistics counters (animated numbers)
    const statElements = document.querySelectorAll('.stat-number');
    if (statElements.length > 0) {
        statElements.forEach(el => {
            const targetValue = parseInt(el.getAttribute('data-value') || 0);
            let currentValue = 0;
            const duration = 2000; // 2 seconds
            const frameDuration = 1000 / 60; // 60fps
            const totalSteps = duration / frameDuration;
            const stepValue = targetValue / totalSteps;
            
            const counter = setInterval(() => {
                currentValue += stepValue;
                if (currentValue >= targetValue) {
                    el.textContent = targetValue.toLocaleString();
                    clearInterval(counter);
                } else {
                    el.textContent = Math.floor(currentValue).toLocaleString();
                }
            }, frameDuration);
        });
    }
});
