package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Olá seja bem vindo ao DIO Bank");
        while (true){
            System.out.println("Selecione a operação desejada");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Fazer um investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Historico de conta");
            System.out.println("14 - Sair");

            var option = scanner.nextInt();
            switch (option){
                case 1: createAccount();
                case 2: createInvestment();
                case 3:
                case 4: deposit();
                case 5: withdraw();
                case 6:
                case 7:
                case 8:
                case 9: accountRepository.list().forEach(System.out::println);
                case 10: investmentRepository.list().forEach(System.out::println);
                case 11: investmentRepository.listWallets().forEach(System.out::println);
                case 12:
                case 13: {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 14: System.exit(0);
                default: System.out.println("Opção inválida");

            }
        }

    }

    private static void createAccount(){
        System.out.println("Informe as chaves pix (separadas por ':");
        var pix = Arrays.stream(scanner.next().split(":")).toList();
        System.out.println("Informe o valor inicial de deposito: ");
        var amount = scanner.nextLong();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada com sucesso: " + wallet);
    }

    private static void createInvestment(){
        System.out.println("Informe a taxa do investimento: ");
        var tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de deposito: ");
        var initialFunds = scanner.nextLong();
        var investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado com sucesso: " + investment);
    }

    private static void deposit(){
        System.out.println("Informe a chave pix da conta para deposito: ");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        var amount = scanner.nextLong();
        try{
            accountRepository.deposit(pix, amount);
        }
        catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void withdraw(){
        System.out.println("Informe a chave pix da conta para saque: ");
        var pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        var amount = scanner.nextLong();
        try{
            accountRepository.withdraw(pix, amount);
        }
        catch (NoFundsEnoughException | AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}