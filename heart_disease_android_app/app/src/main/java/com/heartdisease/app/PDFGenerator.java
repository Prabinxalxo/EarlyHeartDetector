package com.heartdisease.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PDFGenerator {
    private Context context;
    
    public PDFGenerator(Context context) {
        this.context = context;
    }
    
    /**
     * Generate and save a PDF report with the user's assessment details
     * 
     * @param name User's name
     * @param age User's age
     * @param sex User's sex (Male/Female)
     * @param bloodPressure User's blood pressure
     * @param cholesterol User's cholesterol
     * @param chestPainType User's chest pain type
     * @param hasHeartDisease Whether heart disease was detected
     */
    public void generateAndSavePDF(String name, int age, String sex, int bloodPressure, 
                                  int cholesterol, String chestPainType, boolean hasHeartDisease) {
        // Create a new PDF document
        PdfDocument document = new PdfDocument();
        
        // Page info (A4 size)
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        
        // Start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        
        // Paints for different text styles
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.rgb(33, 33, 33));
        titlePaint.setTextSize(24);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        
        Paint headerPaint = new Paint();
        headerPaint.setColor(Color.rgb(33, 33, 33));
        headerPaint.setTextSize(18);
        
        Paint normalPaint = new Paint();
        normalPaint.setColor(Color.rgb(33, 33, 33));
        normalPaint.setTextSize(14);
        
        Paint resultPaint = new Paint();
        resultPaint.setTextSize(16);
        resultPaint.setColor(hasHeartDisease ? Color.rgb(255, 0, 0) : Color.rgb(0, 128, 0));
        
        // Get today's date
        String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        
        // Draw title
        canvas.drawText("Heart Health Assessment Report", 297, 60, titlePaint);
        
        // Draw date
        canvas.drawText("Date: " + currentDate, 60, 100, normalPaint);
        
        // Draw personal information header
        canvas.drawText("Personal Information", 60, 140, headerPaint);
        
        // Draw personal information
        canvas.drawText("Name: " + name, 60, 180, normalPaint);
        canvas.drawText("Age: " + age, 60, 200, normalPaint);
        canvas.drawText("Sex: " + sex, 60, 220, normalPaint);
        canvas.drawText("Blood Pressure: " + bloodPressure + " mm Hg", 60, 240, normalPaint);
        canvas.drawText("Cholesterol: " + cholesterol + " mg/dL", 60, 260, normalPaint);
        canvas.drawText("Chest Pain Type: " + chestPainType, 60, 280, normalPaint);
        
        // Draw assessment result header
        canvas.drawText("Assessment Result", 60, 320, headerPaint);
        
        // Draw result
        String resultText = hasHeartDisease ? "Heart disease detected" : "No heart disease detected";
        canvas.drawText(resultText, 60, 350, resultPaint);
        
        // Draw diet recommendations header
        canvas.drawText("Recommended Diet Plan", 60, 390, headerPaint);
        
        // Get diet recommendations
        DietRecommendations dietRecommendations = new DietRecommendations();
        DietPlan dietPlan = dietRecommendations.getDietRecommendation(hasHeartDisease);
        
        // Draw diet recommendations
        int yPosition = 420;
        for (String category : dietPlan.getCategories()) {
            canvas.drawText(category + ":", 60, yPosition, headerPaint);
            yPosition += 25;
            
            for (String item : dietPlan.getItemsForCategory(category)) {
                canvas.drawText("• " + item, 80, yPosition, normalPaint);
                yPosition += 20;
                
                // Check if we need a new page
                if (yPosition > 800) {
                    document.finishPage(page);
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    yPosition = 60;
                }
            }
            
            yPosition += 10;
        }
        
        // Draw disclaimer
        yPosition += 20;
        Paint disclaimerPaint = new Paint();
        disclaimerPaint.setColor(Color.GRAY);
        disclaimerPaint.setTextSize(10);
        String disclaimer = "Disclaimer: This assessment is based on limited information and is not a medical diagnosis.";
        String disclaimer2 = "Please consult with a healthcare professional for proper medical advice and treatment.";
        canvas.drawText(disclaimer, 60, yPosition, disclaimerPaint);
        canvas.drawText(disclaimer2, 60, yPosition + 15, disclaimerPaint);
        
        // Finish the page
        document.finishPage(page);
        
        try {
            // Save the document to external storage
            String fileName = "heart_health_report_" + name.replace(" ", "_") + ".pdf";
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            FileOutputStream fos = new FileOutputStream(pdfFile);
            document.writeTo(fos);
            document.close();
            fos.close();
            
            // Show success message
            Toast.makeText(context, "Report saved: " + pdfFile.getName(), Toast.LENGTH_LONG).show();
            
            // Open the PDF
            openPdf(pdfFile);
            
        } catch (IOException e) {
            Toast.makeText(context, "Error creating PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * Open the generated PDF file
     * 
     * @param file The PDF file to open
     */
    private void openPdf(File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        
        // Check if there's an app that can handle PDF viewing
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
}