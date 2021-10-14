<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<template:page pageTitle="Feedback">
<html>
<head>
    <title>Feedback</title>
</head>

<body>
<h1>Customer Feedback</h1>
<h3>Share your comments/suggestions with us</h3>


<form:form id="feedbackform" action="/ardctrainingstorefront/feedback/save" method="POST" modelAttribute="feedbackform">
	<div class="form-group">

			<label class="control-label" for="subject">SUBJECT</label>
			<form:input class="form-control" type="text" id="subject" name="subject" maxlength="50" path="subject"/>

			<label class="control-label" for="message">MESSAGE</label>
            <form:textarea class="form-control" id="message" rows="3" maxlength="500" path="message"></form:textarea>
        </div>
			<li>
				<button type="submit" class="button" id="submit">Send</button>
				<button href="/feedback" class="button" id="cancel">Cancel</button>
			</li>


</form:form>

    <div>
        <c:forEach items="${feedbacks}" var="feedbacks" varStatus="loop">
            <p>${feedback.subject}</p>
            <p>${feedback.message}</p>
            <p>${feedback.submittedDate}</p>
        </c:forEach>
    </div>
</body>
</html>
</template:page>