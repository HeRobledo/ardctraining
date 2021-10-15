<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<template:page pageTitle="Feedback">
<html>
<head>
    <title>Feedback</title>

    <c:choose>
        <c:when test="${not empty sentFeedback}">
            <div class="alert alert-primary" role="alert">
                Your feedback have been sent, thank you!
            </div>
        </c:when>
        <c:when test="${not empty emptyFeedback}">
            <div class="alert alert-warning" role="alert">
              Some fields are missing, be sure to fill them
            </div>
        </c:when>
    </c:choose>

</head>

<body>
<div style="margin: 2%">

<h1>Customer Feedback</h1>
<h3>Share your comments/suggestions with us!</h3>
<br>

<form:form id="feedbackform" action="/ardctrainingstorefront/feedback/save" method="POST" modelAttribute="feedbackform">
	<div class="form-group">

			<label class="control-label" for="subject">SUBJECT</label>
			<form:input class="form-control" type="text" id="subject" name="subject" maxlength="50" path="subject"/>

			<label class="control-label" for="message">MESSAGE</label>
            <form:textarea class="form-control" id="message" rows="3" maxlength="500" path="message"></form:textarea>
        </div>
			<div style="text-align:right">
				<button type="submit" class="btn btn-primary" id="submit">Send</button>
				<button type="reset" class="btn btn-secondary" id="cancel">Cancel</button>
			</div>


</form:form>
<br>
<h3>Your feedback</h3>
    <div>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>SUBJECT</th>
                    <th>MESSAGE</th>
                    <th style="text-align:left">DATE</th>

                    </tr>
            </thead>

        <tbody>
        <c:forEach items="${feedbacks}" var="feedback" >
        <tr>
            <td>${feedback.subject}</td>
            <td>${feedback.message}</td>
            <td>${feedback.submittedDate}</td>

        </tr>
        </c:forEach>
        </tbody>
        </table>

    </div>
</div>

</body>
</html>
</template:page>