package com.capgemini.bankapp.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;


@WebServlet(urlPatterns = { "*.do" }, loadOnStartup = 1)
public class BankAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BankAccountService bankService;
	private BankAccount account;

	static final Logger logger = Logger.getLogger(BankAccountController.class);

	public BankAccountController() {

		bankService = new BankAccountServiceImpl();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String path = request.getServletPath();
		if (path.equals("/Display All BankAccount Details.do")) {
			List<BankAccount> bankAccounts = bankService.findAllBankAccounts();

			RequestDispatcher dispatcher = request.getRequestDispatcher("Display All BankAccount Details.jsp");
			request.setAttribute("accounts", bankAccounts);
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String path = request.getServletPath();
		System.out.println(path);

		if (path.equals("/addNewBankAccount.do")) {
			String customerName = request.getParameter("customerName");
			String accountType = request.getParameter("type");

			double balance = Double.parseDouble(request.getParameter("balance"));

			BankAccount account = new BankAccount(customerName, accountType, balance);

			if (bankService.addNewBankAccount(account)) {
				out.println("<h2>Account is created successfully!!!");
				// out.println("<h2><a href='Bank Home.html'>|Home|</h2>");
				out.close();

			}

		}

		if (path.equals("/checkbalance.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));

			try {
				double balance = bankService.checkBalance(accountID);
				out.println("<h2>Your balance is: " + balance + "</h2>");
				out.close();

			} catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");

				logger.error("Exception", e);
			}

		}

		if (path.equals("/deleteAccount.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));

			try {
				if (bankService.deleteBankAccount(accountID)) {
					out.println("<h2>Account is deleted successfully!!!</h2>");
					out.close();

				}

			} catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");
				logger.error("Exception", e);
			}

		}

		if (path.equals("/deposit.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));
			double amount = Double.parseDouble(request.getParameter("amount"));

			try {
				double balance = bankService.deposit(accountID, amount);
				out.println("<h2>Your balance is: " + balance + "</h2>");
				out.close();
			}

			catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");

				logger.error("Exception", e);
			}

		}

		if (path.equals("/fundTransfer.do")) {
			long fromaccountID = Long.parseLong(request.getParameter("fromaccount"));
			long toaccountID = Long.parseLong(request.getParameter("toaccount"));

			double amount = Double.parseDouble(request.getParameter("amount"));

			try {
				double balance = bankService.fundTransfer(fromaccountID, toaccountID, amount);
				out.println("<h2>After fund transfer your balance is: " + balance + "</h2>");

			} catch (LowBalanceException e) {
				out.println("<h2>You do not have sufficient fund to transfer!<h2>");

				logger.error("Exception", e);
			} catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");

				logger.error("Exception", e);
			}

		}

		if (path.equals("/searchAccount.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));

			try {
				BankAccount account=bankService.searchAccount(accountID);
				RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");

				request.setAttribute("accounts", account);
				dispatcher.forward(request, response);


			} 
			catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");
				logger.error("Exception", e);		
				
			}

		}
		

		if (path.equals("/updateAccount.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));
			//String name=account.getAccountHolderName();
			//String type=account.getAccountType();
			try {
				account = bankService.searchAccount(accountID);
				RequestDispatcher dispatcher = request.getRequestDispatcher("update.jsp");

				request.setAttribute("accounts", account);
				dispatcher.forward(request, response);

			} catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");
				logger.error("Exception", e);					
				
			}
		}
			
			if (path.equals("/update.do")) {
				long accountID = Long.parseLong(request.getParameter("accountId"));
				
				String name=request.getParameter("customerName");
				
				String type=request.getParameter("type");
				
				try {
					boolean account = bankService.updateBankAccountDetails(accountID, name, type);
					if(account)
					{
						response.sendRedirect("Display All BankAccount Details.do");
					}
					
				} catch (BankAccountNotFoundException e) {
					out.println("<h2>Bank Account doesn't exist</h2>");
					logger.error("Exception", e);					
					
				}
				
			

		}
		

		if (path.equals("/withdraw.do")) {
			long accountID = Long.parseLong(request.getParameter("accountId"));

			double amount = Double.parseDouble(request.getParameter("amount"));

			try {
				double balance = bankService.withdraw(accountID, amount);
				out.println("<h2>After withdraw your balance is: " + balance + "</h2>");

			} catch (LowBalanceException e) {

				out.println("<h2>You do not have sufficient fund to transfer!<h2>");
				logger.error("Exception", e);

			} catch (BankAccountNotFoundException e) {
				out.println("<h2>Bank Account doesn't exist</h2>");
				logger.error("Exception", e);

			}

		}

	}

}
