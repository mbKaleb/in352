<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.unit7.demo.UserStore" %>

<!DOCTYPE html>
<html>
<head>
    <title>Grade Statistics</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Grade Statistics</h1>
        
        <%
            String filePath = application.getRealPath("/grades.xml");
            UserStore store = new UserStore(filePath);
            
            double avg = store.getAverage();
            double highest = store.getHighest();
            double lowest = store.getLowest();
        %>
        
        <table>
            <tr>
                <th>Statistic</th>
                <th>Value</th>
            </tr>
            <tr>
                <td>Average Grade</td>
                <td><%= String.format("%.2f", avg) %></td>
            </tr>
            <tr>
                <td>Highest Grade</td>
                <td><%= String.format("%.2f", highest) %></td>
            </tr>
            <tr>
                <td>Lowest Grade</td>
                <td><%= String.format("%.2f", lowest) %></td>
            </tr>
        </table>
    </div>
</body>
</html>