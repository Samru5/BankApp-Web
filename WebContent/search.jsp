<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<style>
.addr {
	display: inline-block;
}

.navi a {
	color: tomato;
	padding-left: 15px
}

.navi {
	background-color: black;
	padding: 20px
}
</style>
<title>Account Details</title>
<style>
.navi a {
	color: tomato;
	padding-left: 15px
}

.navi {
	background-color: black;
	padding: 20px
}
</style>
</head>
<div>
	<nav class="navi">
		<a href="Bank Home.html">Home</a> <a href="Open New Account.html">New
			Account</a> <a href="Withdraw.html">Withdraw</a> <a href="Deposit.html">Deposit</a>
		<a href="Fund transfer.html">Fund Transfer</a> <a
			href="Search Account.html">Search Account</a> <a
			href="Check Balance.html">Check Balance</a> <a
			href="Display All BankAccount Details.do">Display All
			BankAccount Details</a> <a href="Delete Account.html">Delete Account</a>

		<a href="Update BankaccountDetails.html">Update BankAccount
			Details</a>
	</nav>
</div>
<br>
<body>
<div class="container">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>BankAccount ID</th>
					<th>Customer Name</th>
					<th>Account Type</th>
					<th>Account balance</th>

				</tr>
			</thead>
			<tbody>
					<tr>

						<td>${accounts.accountId}</td>
						<td>${accounts.accountHolderName}</td>
						<td>${accounts.accountType}</td>
						<td>${accounts.accountBalance}</td>

					</tr>

    </tbody>
		</table>
	</div>
</body>
</html>