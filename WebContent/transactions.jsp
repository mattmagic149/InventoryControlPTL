<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Product" %>
<%@page import="database.Transaction" %>
<%@page import="database.Location" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="org.javatuples.Pair" %>
<%@page import="java.text.SimpleDateFormat"%>


<%
	Object obj = session.getAttribute("transactions_list");
	Object obj2 = session.getAttribute("location");
	if (obj == null || obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	<% }
	
	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
	
	List<Pair<Long,Transaction>> transactions_quantity = new ArrayList<Pair<Long,Transaction>>((ArrayList) session.getAttribute("transactions_list"));
	System.out.println(transactions_quantity.size());
	Location location = ((Location) session.getAttribute("location"));
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

  <title>PTL - Transaktionen</title>
</head>
<body>
    <div id="distance"></div>
    <div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>

    <h1><span>Produkte - Transaktionen</span>
    		- <%=location.getSpecificName() %>
    </h1>

	<form id="search_form">
      <input type="text" name="search" id="search" placeholder="Suchtext" maxlength="30" autocomplete="off">
      <input type="submit" value="" id="search_button" class="icon-search" />
    </form>
	
<!-- 	<button id="show_not_active" class="color_discreet">Inaktive anzeigen</button> -->

    <section id="item_header" class="color_without_hover">
      <div class="name">Datum</div>
      <div class="name">Name</div>
      <div class="name">Beschreibung</div>
      <div class="name">Anzahl</div>
    </section>
    
    <section id="item_list">
    <%
    	for (int i = 0; i < transactions_quantity.size(); i++) {  
    	Pair<Long, Transaction> pair = transactions_quantity.get(i);
    	long quantity = pair.getValue0();
    	Transaction transaction = pair.getValue1();
    	Product product = transaction.getElements().get(0).getProduct();
    %>
		<section class="item color_discreet active" id="<%= product.getId() %>">
		<div class="name"><%=format.format(transaction.getDateMoved()) + "&nbsp;" %> </div>
		<div class="name"><%=product.getName() + "&nbsp;"%></div>
		<div class="name"><%=product.getDescription()  + "&nbsp;"%></div>
		<div class="name"><%=quantity + " " + product.getUnity().getName() + "&nbsp;"%></div>
		<button class="details color"> &#062 &#062 </button>
		</section>
	<%
      	}	
	%>
	</section>
	
<!-- 	<section id='add_button' class='color'></section> -->
    
    <footer></footer>
    
	</div>
</body>
</html>
