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
    const resultsContainer = document.getElementById('results-container').cloneNode(true);
    
    // Create a styled container for the PDF
    const pdfContainer = document.createElement('div');
    pdfContainer.style.padding = '20px';
    pdfContainer.style.maxWidth = '800px';
    pdfContainer.style.margin = '0 auto';
    
    // Add title
    const title = document.createElement('h1');
    title.textContent = 'Heart Health Assessment Report';
    title.style.textAlign = 'center';
    title.style.color = '#333';
    title.style.marginBottom = '20px';
    pdfContainer.appendChild(title);
    
    // Add date
    const date = document.createElement('p');
    const today = new Date();
    date.textContent = `Date: ${today.toLocaleDateString()}`;
    date.style.marginBottom = '20px';
    pdfContainer.appendChild(date);
    
    // Add content
    pdfContainer.appendChild(resultsContainer);
    
    // Add disclaimer
    const disclaimer = document.createElement('p');
    disclaimer.textContent = 'Disclaimer: This assessment is based on limited information and is not a medical diagnosis. Please consult with a healthcare professional for proper medical advice and treatment.';
    disclaimer.style.fontSize = '12px';
    disclaimer.style.color = '#777';
    disclaimer.style.marginTop = '30px';
    pdfContainer.appendChild(disclaimer);
    
    // Generate PDF
    const opt = {
        margin: 10,
        filename: `heart_health_report_${userData.name.replace(/\s+/g, '_')}.pdf`,
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { scale: 2 },
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