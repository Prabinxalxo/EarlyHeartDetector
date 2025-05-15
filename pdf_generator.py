from reportlab.lib.pagesizes import letter
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib import colors
from io import BytesIO
from datetime import date

def generate_pdf(user_data, prediction_result, diet_recommendations):
    """
    Generate a PDF report with the user's assessment results and recommendations.
    
    Parameters:
    user_data (dict): Dictionary containing user information
    prediction_result (bool): Whether heart disease was predicted
    diet_recommendations (dict): Dictionary of diet recommendations
    
    Returns:
    BytesIO: PDF content as bytes
    """
    buffer = BytesIO()
    doc = SimpleDocTemplate(buffer, pagesize=letter)
    styles = getSampleStyleSheet()
    
    # Create custom styles
    title_style = ParagraphStyle(
        'Title',
        parent=styles['Heading1'],
        fontSize=18,
        alignment=1,  # Center
    )
    
    subtitle_style = ParagraphStyle(
        'Subtitle',
        parent=styles['Heading2'],
        fontSize=14,
    )
    
    normal_style = styles['Normal']
    
    # Document elements
    elements = []
    
    # Title
    elements.append(Paragraph("Heart Health Assessment Report", title_style))
    elements.append(Spacer(1, 20))
    
    # Date
    today = date.today().strftime("%B %d, %Y")
    elements.append(Paragraph(f"Date: {today}", normal_style))
    elements.append(Spacer(1, 20))
    
    # Personal Information
    elements.append(Paragraph("Personal Information", subtitle_style))
    elements.append(Spacer(1, 10))
    
    data = [
        ["Name:", user_data["name"]],
        ["Age:", str(user_data["age"])],
        ["Sex:", user_data["sex"]],
        ["Blood Pressure:", f"{user_data['blood_pressure']} mm Hg"],
        ["Cholesterol:", f"{user_data['cholesterol']} mg/dL"],
        ["Chest Pain Type:", user_data["chest_pain_type"]]
    ]
    
    info_table = Table(data, colWidths=[150, 350])
    info_table.setStyle(TableStyle([
        ('GRID', (0, 0), (-1, -1), 0.5, colors.grey),
        ('BACKGROUND', (0, 0), (0, -1), colors.lightgrey),
    ]))
    
    elements.append(info_table)
    elements.append(Spacer(1, 20))
    
    # Assessment Result
    elements.append(Paragraph("Assessment Result", subtitle_style))
    elements.append(Spacer(1, 10))
    
    result_text = "Heart disease detected" if prediction_result else "No heart disease detected"
    result_color = colors.red if prediction_result else colors.green
    
    result_style = ParagraphStyle(
        'Result',
        parent=normal_style,
        textColor=result_color,
        fontSize=12,
        bold=True
    )
    
    elements.append(Paragraph(result_text, result_style))
    elements.append(Spacer(1, 20))
    
    # Diet Recommendations
    elements.append(Paragraph("Recommended Diet Plan", subtitle_style))
    elements.append(Spacer(1, 10))
    
    for category, items in diet_recommendations.items():
        elements.append(Paragraph(f"<b>{category}:</b>", normal_style))
        elements.append(Spacer(1, 5))
        
        for item in items:
            elements.append(Paragraph(f"• {item}", normal_style))
        
        elements.append(Spacer(1, 10))
    
    # Footer
    elements.append(Spacer(1, 30))
    disclaimer = (
        "Disclaimer: This assessment is based on limited information and is not a medical diagnosis. "
        "Please consult with a healthcare professional for proper medical advice and treatment."
    )
    elements.append(Paragraph(disclaimer, ParagraphStyle(
        'Disclaimer',
        parent=normal_style,
        fontSize=8,
        textColor=colors.grey
    )))
    
    # Build PDF
    doc.build(elements)
    buffer.seek(0)
    
    return buffer.getvalue()
