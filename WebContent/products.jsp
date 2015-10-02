<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Product" %>
<%@page import="database.Location" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
	Object obj = session.getAttribute("products_list");
	Object obj2 = session.getAttribute("location");
	Object obj3 = session.getAttribute("details");
	if (obj == null || obj2 == null || obj3 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	<% }
	List<Product> products = new ArrayList<Product>((ArrayList) session.getAttribute("products_list"));
	System.out.println(products.size());
	Location location = ((Location) session.getAttribute("location"));
	boolean show_details = ((boolean) session.getAttribute("details"));
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/detail_list.css" type="text/css" media="screen" />
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/detail_list_search.js" type="text/javascript"></script>
  <script src="js/products.js" type="text/javascript"></script>

  <title>PTL - Produkte</title>
</head>
<body>
    <div id="distance"></div>
    <div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>

    <h1><span>Produkte</span>
    	<% if (!show_details) { %>
    		- <%=location.getSpecificName() %>
    	<% } %>
    </h1>

	<form id="search_form">
      <input type="text" name="search" id="search" placeholder="Suchtext" maxlength="30" autocomplete="off">
      <input type="submit" value="" id="search_button" class="icon-search" />
    </form>
	
	<button id="show_not_active" class="color_discreet">Inaktive anzeigen</button>

    <section id="item_header" class="color_without_hover">
      <div class="name">Name</div>
      <div class="name">Beschreibung</div>
      <% if (show_details) { %>
	      <div class="name">Unteres Limit</div>
      <% } %>
      <div class="name">Anzahl</div>
      <% if (show_details) { %>
	      <div class="details">Details</div>
      <% } %>
    </section>
    
    <section id="item_list">
    <%
    	for (int i = 0; i < products.size(); i++) {    		
    %>
		<% if (products.get(i).getState() == Product.ProductState.ACTIVE) { %>
			<section class="item color_discreet active" id="<%=products.get(i).getId() %>">
		<% } else { %>
			<section class="item color_discreet not_active hidden" id="<%=products.get(i).getId() %>">
		<% } %>
			<div class="name"><%=products.get(i).getName() + "&nbsp;" %> </div>
			<div class="name"><%=products.get(i).getDescription() + "&nbsp;"%></div>
			<% if (show_details) { %>
				<div class="name"><%=products.get(i).getMinimumLimit() + " " + products.get(i).getUnity().getName()  + "&nbsp;"%></div>
			<% } %>
			<div class="name"><%=products.get(i).getQuantityOfSpecificLocation(location.getId()) + " " + products.get(i).getUnity().getName()  + "&nbsp;"%></div>
			<% if (show_details) { %>
				<button class="details color"> &#062 &#062 </button>
			<% } %>
		</section>
	<%
      	}	
	%>
	</section>
	
	<section id='add_button' class='color'></section>
    
    <footer></footer>
    
	</div>
</body>
</html>
