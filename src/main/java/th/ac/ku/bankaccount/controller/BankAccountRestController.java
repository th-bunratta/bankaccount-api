package th.ac.ku.bankaccount.controller;

import org.springframework.web.bind.annotation.*;
import th.ac.ku.bankaccount.data.BankAccountRepository;
import th.ac.ku.bankaccount.model.BankAccount;
import th.ac.ku.bankaccount.model.Transaction;

import java.util.List;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountRestController {

    private BankAccountRepository repository;

    public BankAccountRestController(BankAccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        BankAccount record = repository.save(bankAccount);
        repository.flush();
        return record;
    }

    @PostMapping("/withdraw/{accountId}")
    public BankAccount withdraw(@RequestBody Transaction transaction, @PathVariable int accountId) {
        BankAccount record = repository.findById(accountId).get();
        double newBalance = record.getBalance() - transaction.amount;
        if (newBalance >= 0) {
            record.setBalance(newBalance);
            repository.save(record);
        }
        return record;
    }


    @PostMapping("/deposit/{accountId}")
    public BankAccount deposit(@RequestBody Transaction transaction, @PathVariable int accountId) {
        BankAccount record = repository.findById(accountId).get();
        double balance = record.getBalance();
        if (transaction.amount > 0) {
            double newBalance = balance + transaction.amount;
            record.setBalance(newBalance);
            repository.save(record);
        }
        return record;
    }


    @GetMapping("/customer/{customerId}")
    public List<BankAccount> getAllCustomerId(@PathVariable int customerId) {
        return repository.findByCustomerId(customerId);
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public BankAccount update(@PathVariable int id,
                              @RequestBody BankAccount bankAccount) {
        BankAccount record = repository.findById(id).get();
        record.setBalance(bankAccount.getBalance());
        repository.save(record);
        return record;
    }

    @GetMapping("/{id}")
    public BankAccount getOne(@PathVariable int id) {
        return repository.findById(id).get();
    }
    @DeleteMapping("/{id}")
    public BankAccount delete(@PathVariable int id) {
        BankAccount record = repository.findById(id).get();
        repository.deleteById(id);
        return record;
    }

}

