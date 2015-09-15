<%@page import="database.Product" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
	List<Product> products = new ArrayList<Product>((ArrayList) session.getAttribute("products_list"));
	System.out.println(products.size());
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/detail_list.css" type="text/css" media="screen" />
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/products.js" type="text/javascript"></script>
  <script src="js/live_search.js" type="text/javascript"></script>

  <title>PTL - Products</title>
</head>
<body>
    <div id="distance"></div>
    <div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <header>
      <form id="search_form">
        <input type="text" name="search" id="search" placeholder="Suchtext" maxlength="30" autocomplete="off">
        <input type="submit" value="" id="search_button" class="icon-search" />
      </form>
    </header>

    <h1><span>Produkte</span></h1>

    <section id="item_header" class="color_without_hover">
      <div class="name">Name</div>
      <div class="name">Beschreibung</div>
      <div class="name">Unteres Limit</div>
      <div class="name">Anzahl</div>
      <div class="details">Details</div>
    </section>
    
    <section id="item_list">
    <%
    	for (int i = 0; i < products.size(); i++) {    		
    %>
		<section class="item color_discreet" id="<%=products.get(i).getId() %>">
			<div class="name"><%=products.get(i).getName() + "&nbsp;" %> </div>
			<div class="name"><%=products.get(i).getDescription() + "&nbsp;"%></div>
			<div class="name"><%=products.get(i).getMinimumLimit() + " " + products.get(i).getUnity().getName()  + "&nbsp;"%></div>
			<div class="name"><%=products.get(i).getProductElements().size() + " " + products.get(i).getUnity().getName()  + "&nbsp;"%></div>
			<button class="details color"> &#062 &#062 </button>
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
