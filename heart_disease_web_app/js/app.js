// Application state
let userData = {};
let predictionResult = false;
let dietRecommendations = {};

// Navigation between pages
function navigateTo(pageId) {
    // Hide all pages
    document.querySelectorAll('.page').forEach(page => {
        page.classList.remove('active');
    });
    
    // Show the target page
    document.getElementById(pageId).classList.add('active');
    
    // Scroll to top when changing pages
    window.scrollTo(0, 0);
}

// Form submission
function submitAssessment(event) {
    event.preventDefault();
    
    // Get form data
    const form = document.getElementById('assessment-form');
    const formData = new FormData(form);
    
    // Store user data
    userData = {
        name: formData.get('name'),
        age: parseInt(formData.get('age')),
        sex: formData.get('sex'),
        bloodPressure: parseInt(formData.get('bloodPressure')),
        cholesterol: parseInt(formData.get('cholesterol')),
        chestPain: parseInt(formData.get('chestPain'))
    };
    
    // Make prediction
    const sexValue = userData.sex === 'male' ? 1 : 0;
    predictionResult = predictHeartDisease(
        userData.age,
        sexValue,
        userData.chestPain,
        userData.bloodPressure,
        userData.cholesterol
    );
    
    // Get diet recommendations
    dietRecommendations = getDietRecommendation(predictionResult);
    
    // Display results
    displayResults();
    
    // Navigate to results page
    navigateTo('results-page');
}

// Display results on the results page
function displayResults() {
    // Fill in user information
    document.getElementById('result-name').textContent = userData.name;
    document.getElementById('result-age').textContent = userData.age;
    document.getElementById('result-sex').textContent = userData.sex === 'male' ? 'Male' : 'Female';
    document.getElementById('result-bp').textContent = `${userData.bloodPressure} mm Hg`;
    document.getElementById('result-cholesterol').textContent = `${userData.cholesterol} mg/dL`;
    
    // Convert chest pain value to descriptive text
    let chestPainText;
    switch(userData.chestPain) {
        case 0: chestPainText = '0 - No pain'; break;
        case 1: chestPainText = '1 - Mild pain'; break;
        case 2: chestPainText = '2 - Moderate pain'; break;
        case 3: chestPainText = '3 - Severe pain'; break;
        default: chestPainText = 'Unknown';
    }
    document.getElementById('result-chest-pain').textContent = chestPainText;
    
    // Set prediction result with appropriate styling
    const predictionElement = document.getElementById('result-prediction');
    predictionElement.textContent = predictionResult ? 'Heart disease detected' : 'No heart disease detected';
    predictionElement.className = 'prediction-result ' + (predictionResult ? 'prediction-positive' : 'prediction-negative');
    
    // Set background color of results container
    const resultsContainer = document.getElementById('results-container');
    if (predictionResult) {
        resultsContainer.style.backgroundColor = 'rgba(255, 99, 71, 0.1)';
    } else {
        resultsContainer.style.backgroundColor = 'rgba(46, 204, 113, 0.1)';
    }
    
    // Display diet recommendations
    const dietContainer = document.getElementById('diet-recommendations');
    dietContainer.innerHTML = ''; // Clear previous recommendations
    
    Object.keys(dietRecommendations).forEach(category => {
        const categoryDiv = document.createElement('div');
        categoryDiv.className = 'diet-category';
        
        const categoryTitle = document.createElement('div');
        categoryTitle.className = 'diet-category-title';
        categoryTitle.textContent = category + ':';
        categoryDiv.appendChild(categoryTitle);
        
        dietRecommendations[category].forEach(item => {
            const itemDiv = document.createElement('div');
            itemDiv.className = 'diet-item';
            itemDiv.textContent = item;
            categoryDiv.appendChild(itemDiv);
        });
        
        dietContainer.appendChild(categoryDiv);
    });
}

// Generate PDF report
function generatePDF() {
    // Create a styled container for the PDF
    const pdfContainer = document.createElement('div');
    pdfContainer.style.padding = '15px';
    pdfContainer.style.maxWidth = '800px';
    pdfContainer.style.margin = '0 auto';
    pdfContainer.style.fontFamily = 'Arial, sans-serif';
    pdfContainer.style.fontSize = '12px';
    
    // Add title and header in a compact format
    const header = document.createElement('div');
    header.style.display = 'flex';
    header.style.justifyContent = 'space-between';
    header.style.alignItems = 'center';
    header.style.marginBottom = '10px';
    
    const title = document.createElement('h1');
    title.textContent = 'Heart Health Assessment Report';
    title.style.textAlign = 'left';
    title.style.color = '#333';
    title.style.margin = '0';
    title.style.fontSize = '18px';
    header.appendChild(title);
    
    const date = document.createElement('p');
    const today = new Date();
    date.textContent = `Date: ${today.toLocaleDateString()}`;
    date.style.margin = '0';
    date.style.fontSize = '12px';
    header.appendChild(date);
    
    pdfContainer.appendChild(header);
    
    // Create a divider
    const divider = document.createElement('hr');
    divider.style.margin = '10px 0';
    divider.style.border = '0.5px solid #ddd';
    pdfContainer.appendChild(divider);
    
    // Create a two-column layout for patient info and result
    const infoSection = document.createElement('div');
    infoSection.style.display = 'flex';
    infoSection.style.justifyContent = 'space-between';
    infoSection.style.marginBottom = '15px';
    
    // Patient information column
    const patientInfo = document.createElement('div');
    patientInfo.style.width = '48%';
    
    const patientTitle = document.createElement('h2');
    patientTitle.textContent = 'Patient Information';
    patientTitle.style.fontSize = '14px';
    patientTitle.style.margin = '0 0 8px 0';
    patientInfo.appendChild(patientTitle);
    
    const infoTable = document.createElement('table');
    infoTable.style.width = '100%';
    infoTable.style.borderCollapse = 'collapse';
    infoTable.style.fontSize = '11px';
    
    // Add rows for patient data
    const infoRows = [
        { label: 'Name', value: userData.name },
        { label: 'Age', value: userData.age },
        { label: 'Sex', value: userData.sex === 'male' ? 'Male' : 'Female' },
        { label: 'Blood Pressure', value: `${userData.bloodPressure} mm Hg` },
        { label: 'Cholesterol', value: `${userData.cholesterol} mg/dL` },
        { label: 'Chest Pain Type', value: `${userData.chestPain} (${['No pain', 'Mild pain', 'Moderate pain', 'Severe pain'][userData.chestPain]})` }
    ];
    
    infoRows.forEach(row => {
        const tr = document.createElement('tr');
        
        const tdLabel = document.createElement('td');
        tdLabel.textContent = row.label + ':';
        tdLabel.style.fontWeight = 'bold';
        tdLabel.style.padding = '3px 0';
        tr.appendChild(tdLabel);
        
        const tdValue = document.createElement('td');
        tdValue.textContent = row.value;
        tdValue.style.padding = '3px 0';
        tr.appendChild(tdValue);
        
        infoTable.appendChild(tr);
    });
    
    patientInfo.appendChild(infoTable);
    infoSection.appendChild(patientInfo);
    
    // Result column
    const resultInfo = document.createElement('div');
    resultInfo.style.width = '48%';
    resultInfo.style.display = 'flex';
    resultInfo.style.flexDirection = 'column';
    resultInfo.style.alignItems = 'center';
    resultInfo.style.justifyContent = 'center';
    
    const resultTitle = document.createElement('h2');
    resultTitle.textContent = 'Assessment Result';
    resultTitle.style.fontSize = '14px';
    resultTitle.style.margin = '0 0 10px 0';
    resultTitle.style.alignSelf = 'flex-start';
    resultInfo.appendChild(resultTitle);
    
    const resultBox = document.createElement('div');
    resultBox.style.padding = '15px';
    resultBox.style.borderRadius = '5px';
    resultBox.style.textAlign = 'center';
    resultBox.style.width = '100%';
    resultBox.style.fontWeight = 'bold';
    resultBox.style.fontSize = '14px';
    
    if (predictionResult) {
        resultBox.textContent = 'Heart Disease Detected';
        resultBox.style.backgroundColor = 'rgba(255, 99, 71, 0.2)';
        resultBox.style.color = '#D32F2F';
    } else {
        resultBox.textContent = 'No Heart Disease Detected';
        resultBox.style.backgroundColor = 'rgba(46, 204, 113, 0.2)';
        resultBox.style.color = '#388E3C';
    }
    
    resultInfo.appendChild(resultBox);
    infoSection.appendChild(resultInfo);
    pdfContainer.appendChild(infoSection);
    
    // Add diet recommendations with food images
    const dietSection = document.createElement('div');
    dietSection.style.marginTop = '10px';
    
    const dietTitle = document.createElement('h2');
    dietTitle.textContent = 'Diet Recommendations';
    dietTitle.style.fontSize = '14px';
    dietTitle.style.margin = '0 0 10px 0';
    dietSection.appendChild(dietTitle);
    
    // Create a two-column layout for diet info and images
    const dietContent = document.createElement('div');
    dietContent.style.display = 'flex';
    dietContent.style.justifyContent = 'space-between';
    
    // Diet text column
    const dietText = document.createElement('div');
    dietText.style.width = '60%';
    
    // Only display 3-4 key categories to keep it on one page
    const categories = Object.keys(dietRecommendations);
    const priorityCategories = categories.slice(0, 4);
    
    priorityCategories.forEach(category => {
        const catTitle = document.createElement('h3');
        catTitle.textContent = category;
        catTitle.style.fontSize = '12px';
        catTitle.style.margin = '5px 0';
        catTitle.style.color = '#333';
        dietText.appendChild(catTitle);
        
        const list = document.createElement('ul');
        list.style.margin = '0 0 8px 0';
        list.style.paddingLeft = '18px';
        
        // Only show first 2-3 items from each category to keep report compact
        const items = dietRecommendations[category].slice(0, 2);
        
        items.forEach(item => {
            const listItem = document.createElement('li');
            listItem.textContent = item;
            listItem.style.fontSize = '10px';
            listItem.style.margin = '3px 0';
            list.appendChild(listItem);
        });
        
        dietText.appendChild(list);
    });
    
    dietContent.appendChild(dietText);
    
    // Diet images column
    const dietImages = document.createElement('div');
    dietImages.style.width = '38%';
    dietImages.style.display = 'flex';
    dietImages.style.flexDirection = 'column';
    dietImages.style.alignItems = 'center';
    dietImages.style.gap = '10px';
    
    // Add food images based on heart disease status
    const healthyFoodsImg = document.createElement('img');
    healthyFoodsImg.src = '/img/foods/heart_healthy_foods.svg';
    healthyFoodsImg.style.width = '100%';
    healthyFoodsImg.style.maxHeight = '80px';
    dietImages.appendChild(healthyFoodsImg);
    
    if (predictionResult) {
        // Add "foods to avoid" image for heart disease patients
        const avoidFoodsImg = document.createElement('img');
        avoidFoodsImg.src = '/img/foods/foods_to_avoid.svg';
        avoidFoodsImg.style.width = '100%';
        avoidFoodsImg.style.maxHeight = '80px';
        dietImages.appendChild(avoidFoodsImg);
    }
    
    dietContent.appendChild(dietImages);
    dietSection.appendChild(dietContent);
    pdfContainer.appendChild(dietSection);
    
    // Add disclaimer at bottom
    const disclaimer = document.createElement('p');
    disclaimer.textContent = 'Disclaimer: This assessment is based on limited information and is not a medical diagnosis. Please consult with a healthcare professional for proper medical advice and treatment.';
    disclaimer.style.fontSize = '9px';
    disclaimer.style.color = '#777';
    disclaimer.style.marginTop = '15px';
    disclaimer.style.fontStyle = 'italic';
    pdfContainer.appendChild(disclaimer);
    
    // Generate PDF with specific page size to ensure one page
    const opt = {
        margin: [10, 10, 10, 10], // top, right, bottom, left
        filename: `heart_health_report_${userData.name.replace(/\s+/g, '_')}.pdf`,
        image: { type: 'jpeg', quality: 0.95 },
        html2canvas: { scale: 2, useCORS: true },
        jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };
    
    html2pdf().from(pdfContainer).set(opt).save();
}

// Service Worker Registration for PWA
if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('/service-worker.js')
            .then(registration => {
                console.log('ServiceWorker registration successful');
            })
            .catch(error => {
                console.log('ServiceWorker registration failed: ', error);
            });
    });
}