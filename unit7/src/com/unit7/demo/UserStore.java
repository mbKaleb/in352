package com.unit7.demo;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class UserStore {
    private String xmlFilePath;
    
    public UserStore(String filePath) {
        this.xmlFilePath = filePath;
    }
    
    public double getAverage() {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            
            NodeList grades = doc.getElementsByTagName("grade");
            double sum = 0;
            
            for (int i = 0; i < grades.getLength(); i++) {
                Element gradeElement = (Element) grades.item(i);
                double grade = Double.parseDouble(gradeElement.getTextContent());
                sum += grade;
            }
            
            return sum / grades.getLength();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public double getHighest() {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            
            NodeList grades = doc.getElementsByTagName("grade");
            double highest = 0;
            
            for (int i = 0; i < grades.getLength(); i++) {
                Element gradeElement = (Element) grades.item(i);
                double grade = Double.parseDouble(gradeElement.getTextContent());
                if (grade > highest) highest = grade;
            }
            
            return highest;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public double getLowest() {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            
            NodeList grades = doc.getElementsByTagName("grade");
            double lowest = 100;
            
            for (int i = 0; i < grades.getLength(); i++) {
                Element gradeElement = (Element) grades.item(i);
                double grade = Double.parseDouble(gradeElement.getTextContent());
                if (grade < lowest) lowest = grade;
            }
            
            return lowest;
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }
}