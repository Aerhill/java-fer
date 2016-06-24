<html>
<head></head>
<body>

	${sessionScope["registrationError"]}
	<form action="register" method="post">
		Ime: <input type="text" name="name"> <br>
		Prezime: <input type="text" name="surname"> <br>
		Email: <input type="email" name="email"> <br>
		Neek: <input type="text" name="neek"> <br>
		Password: <input type="password" name="pass"> <br>
		<button type="submit">Register</button>
	</form>
	
	<p>
	<a href="/blog/">Go to Index</a>
</body>

</html>