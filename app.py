import streamlit as st
import pandas as pd
import numpy as np
from model import predict_heart_disease
from diet_recommendations import get_diet_recommendation
from pdf_generator import generate_pdf
from images import get_image_urls
import base64
from io import BytesIO

# Configure the page
st.set_page_config(
    page_title="Early Heart Attack Prediction",
    page_icon="❤️",
    layout="wide",
    initial_sidebar_state="collapsed"
)

# Application states
if 'page' not in st.session_state:
    st.session_state.page = 'home'
if 'prediction_result' not in st.session_state:
    st.session_state.prediction_result = None
if 'user_data' not in st.session_state:
    st.session_state.user_data = {}
if 'pdf' not in st.session_state:
    st.session_state.pdf = None

def reset_app():
    st.session_state.page = 'home'
    st.session_state.prediction_result = None
    st.session_state.user_data = {}
    st.session_state.pdf = None
    st.rerun()

def home_page():
    # Get image URLs
    heart_images = get_image_urls('medical heart illustrations')
    
    # Create a social media like header
    col1, col2 = st.columns([1, 5])
    with col1:
        st.markdown("<h1 style='font-size: 2.5rem;'>❤️</h1>", unsafe_allow_html=True)
    with col2:
        st.markdown("<h1 style='font-size: 2.5rem;'>Heart Health Check</h1>", unsafe_allow_html=True)
    
    st.markdown("<hr>", unsafe_allow_html=True)
    
    # Main content with a featured image
    col1, col2 = st.columns([3, 2])
    
    with col1:
        st.markdown("### Welcome to Early Heart Attack Prediction")
        st.markdown("""
        This application helps you assess your risk of heart disease and provides
        personalized recommendations to improve your heart health.
        
        Simply fill out the form to get your personalized assessment.
        """)
        
        if st.button("Start Assessment", type="primary", use_container_width=True):
            st.session_state.page = 'assessment'
            st.rerun()
    
    with col2:
        st.image(heart_images[0], use_column_width=True)
    
    # Footer with health tips
    st.markdown("<hr>", unsafe_allow_html=True)
    st.markdown("### Heart Health Tips")
    
    col1, col2, col3 = st.columns(3)
    
    with col1:
        st.markdown("🥗 **Eat a balanced diet**")
        st.markdown("Include plenty of fruits, vegetables, and whole grains.")
    
    with col2:
        st.markdown("🏃 **Stay active**")
        st.markdown("Aim for at least 150 minutes of moderate exercise weekly.")
    
    with col3:
        st.markdown("😴 **Get enough sleep**")
        st.markdown("7-9 hours of quality sleep supports heart health.")

def assessment_page():
    st.markdown("### Heart Disease Risk Assessment")
    st.markdown("Please provide your information to receive a personalized assessment.")
    
    with st.form("assessment_form"):
        col1, col2 = st.columns(2)
        
        with col1:
            name = st.text_input("Name", placeholder="Enter your full name")
            age = st.number_input("Age", min_value=1, max_value=120, value=40)
            sex = st.selectbox("Sex", options=["Male", "Female"])
            sex_value = 1 if sex == "Male" else 0
            
        with col2:
            bp = st.number_input("Blood Pressure (mm Hg)", min_value=70, max_value=250, value=120)
            cholesterol = st.number_input("Cholesterol (mg/dL)", min_value=100, max_value=600, value=200)
            chest_pain_type = st.selectbox(
                "Chest Pain Type", 
                options=[
                    "0 - No pain", 
                    "1 - Mild pain", 
                    "2 - Moderate pain", 
                    "3 - Severe pain"
                ]
            )
            chest_pain_value = int(chest_pain_type[0])
        
        submitted = st.form_submit_button("Get Results", use_container_width=True)
        
        if submitted:
            if not name:
                st.error("Please enter your name.")
                return
            
            # Store user data
            st.session_state.user_data = {
                "name": name,
                "age": age,
                "sex": sex,
                "blood_pressure": bp,
                "cholesterol": cholesterol,
                "chest_pain_type": chest_pain_type
            }
            
            # Prepare data for prediction
            input_data = np.array([age, sex_value, chest_pain_value, bp, cholesterol]).reshape(1, -1)
            
            # Make prediction
            prediction = predict_heart_disease(input_data)
            st.session_state.prediction_result = prediction
            
            # Generate PDF report
            pdf_bytes = generate_pdf(
                st.session_state.user_data,
                prediction,
                get_diet_recommendation(prediction)
            )
            st.session_state.pdf = pdf_bytes
            
            # Move to results page
            st.session_state.page = 'results'
            st.rerun()

def results_page():
    prediction = st.session_state.prediction_result
    user_data = st.session_state.user_data
    
    # Set background color based on prediction
    if prediction:
        st.markdown(
            """
            <style>
            .result-container {
                background-color: rgba(255, 99, 71, 0.2);
                padding: 20px;
                border-radius: 10px;
                border: 2px solid #FF6347;
            }
            </style>
            """, 
            unsafe_allow_html=True
        )
        result_color = "#FF6347"  # Red
        result_emoji = "⚠️"
    else:
        st.markdown(
            """
            <style>
            .result-container {
                background-color: rgba(46, 204, 113, 0.2);
                padding: 20px;
                border-radius: 10px;
                border: 2px solid #2ECC71;
            }
            </style>
            """, 
            unsafe_allow_html=True
        )
        result_color = "#2ECC71"  # Green
        result_emoji = "✅"
    
    # Header
    st.markdown("## Assessment Results")
    
    # Results container
    st.markdown("<div class='result-container'>", unsafe_allow_html=True)
    
    # Show user information
    st.markdown("### Personal Information")
    col1, col2 = st.columns(2)
    
    with col1:
        st.markdown(f"**Name:** {user_data['name']}")
        st.markdown(f"**Age:** {user_data['age']}")
        st.markdown(f"**Sex:** {user_data['sex']}")
    
    with col2:
        st.markdown(f"**Blood Pressure:** {user_data['blood_pressure']} mm Hg")
        st.markdown(f"**Cholesterol:** {user_data['cholesterol']} mg/dL")
        st.markdown(f"**Chest Pain Type:** {user_data['chest_pain_type']}")
    
    # Prediction result
    st.markdown("### Heart Disease Assessment")
    result_text = "Heart disease detected" if prediction else "No heart disease detected"
    st.markdown(f"<h3 style='color: {result_color};'>{result_emoji} {result_text}</h3>", unsafe_allow_html=True)
    
    # Diet recommendations
    st.markdown("### Recommended Diet Plan")
    recommendations = get_diet_recommendation(prediction)
    for category, items in recommendations.items():
        st.markdown(f"**{category}:**")
        for item in items:
            st.markdown(f"- {item}")
    
    st.markdown("</div>", unsafe_allow_html=True)
    
    # Download report button
    if st.session_state.pdf:
        st.download_button(
            label="Download Report",
            data=st.session_state.pdf,
            file_name=f"heart_health_report_{user_data['name'].replace(' ', '_')}.pdf",
            mime="application/pdf",
            use_container_width=True
        )
    
    # Start over button
    if st.button("Start Over", use_container_width=True):
        reset_app()

# Main app logic
def main():
    if st.session_state.page == 'home':
        home_page()
    elif st.session_state.page == 'assessment':
        assessment_page()
    elif st.session_state.page == 'results':
        results_page()

if __name__ == "__main__":
    main()
