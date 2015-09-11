<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/editable.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product_list.css" type="text/css" media="screen" />
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/products.js" type="text/javascript"></script>
  <script src="js/live_search.js" type="text/javascript"></script>

  <title>PTL - Products</title>
</head>
<body>
    <div id="distance"></div>
    <div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="welcome.html"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
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
      <section class="item color_discreet" id="12">
        <div class="name">Zurrgurte groß</div>
        <div class="name">3 Meter...</div>
        <div class="name">3 Stück</div>
        <div class="name">5 Stück</div>
		<button class="details color">&#062 &#062</button>
	  </section>
      <section class="item color_discreet" id="12">
        <div class="name">Zurrgurte groß</div>
        <div class="name">3 Meter...</div>
        <div class="name">3 Stück</div>
        <div class="name">5 Stück</div>
		<button class="details color">&#062 &#062</button>
	  </section>
      <section class="item color_discreet" id="12">
        <div class="name">Zurrgurte groß</div>
        <div class="name">3 Meter...</div>
        <div class="name">3 Stück</div>
        <div class="name">5 Stück</div>
		<button class="details color">&#062 &#062</button>
	  </section>
      <section class="item color_discreet" id="12">
        <div class="name">Zurrgurte groß</div>
        <div class="name">3 Meter...</div>
        <div class="name">3 Stück</div>
        <div class="name">5 Stück</div>
		<button class="details color">&#062 &#062</button>
	  </section>
      <section class="item color_discreet" id="12">
        <div class="name">Zurrgurte groß</div>
        <div class="name">3 Meter...</div>
        <div class="name">3 Stück</div>
        <div class="name">5 Stück</div>
		<button class="details color">&#062 &#062</button>
	  </section>
    </section>
	
	<section id='add_button' class='color'></section>
    
    <footer></footer>
    
	</div>
</body>
</html>
