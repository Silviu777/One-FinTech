package com.fintech.service.implementation;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import com.fintech.service.AccountService;
import com.fintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Account getAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public Account openAccount(User user, AccountType accountType, Currency currency) {
        Account account = new Account();
        account.setOwner(user);
        account.setAccountType(accountType);
        account.setCurrency(currency);
        account.setBalance(new BigDecimal(0.0));

        if (accountType.equals(AccountType.PRIMARY)) {
            account.setInterestRate(0.3);
        }
        else {
            account.setInterestRate(3);
        }
        // continue with user details
        if (account.getDateOpened() == null) {
            account.setDateOpened(new Date());
        }
        // iban!
        accountRepository.save(account);
        return account;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));
        transactionRepository.save(new Transaction(amount, account, new Date()));
        accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        accountRepository.save(account);
        return account;
    }

    @Override
    public void closeAccount(User user) {
        accountRepository.deleteAccountByOwner(user);
    }

    @Override
    public List<Transaction> viewAccountTransactions(Long accountId) {
        return transactionRepository.findAllByAccount(getAccount(accountId));
    }
}