package com.company;

import com.esaulpaugh.headlong.abi.Tuple;
import com.hedera.hashgraph.sdk.*;
import com.hedera.hashgraph.sdk.proto.SignedTransaction;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Call {

    public static void main(String[] args) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        //Grab your Hedera Testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));



        //Create your Hedera Testnet client
        Client client = Client.forTestnet();

        //Set your account as the client's operator
        client.setOperator(myAccountId, myPrivateKey);

        //Set the default maximum transaction fee (in Hbar)
        client.setDefaultMaxTransactionFee(new Hbar(100));

        //Set the maximum payment for queries (in Hbar)
        client.setMaxQueryPayment(new Hbar(50));



        //Create the account info query
        AccountInfoQuery query = new AccountInfoQuery()
                .setAccountId(myAccountId);

        //Submit the query to a Hedera network
        AccountInfo accountInfo = query.execute(client);

        //Print the account key to the console
        System.out.println(accountInfo);

        ContractId contractId = ContractId.fromString("0.0.7764873");


        getOwner(client, contractId);

        addMyNewTodo(client, contractId, "Hello " + new Random().nextInt(100));

        getMyOpenTodos(client, contractId);

        finishMyTodo(client, contractId, "Hello " + new Random().nextInt(100));

        getMyDoneTodos(client, contractId);

    }

    private static void finishMyTodo(Client client, ContractId contractId, String message) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        // Calls a function of the smart contract
        ContractExecuteTransaction contractExecuteTransaction = new ContractExecuteTransaction()
                //Set the contract ID
                .setContractId(contractId)
                //Set the gas for the query
                .setGas(100000)
                //Set the parameters for the function call
                .setFunction("finishMyTodo", new ContractFunctionParameters().addString(message))
                //Set the payment amount for the function call
                .setMaxTransactionFee(new Hbar(2));

        //Sign with the client operator private key and submit the transaction to a Hedera network
        TransactionResponse contractExecuteTransactionResponse = contractExecuteTransaction.execute(client);

        //Request the receipt of the transaction
        contractExecuteTransactionResponse.getReceipt(client);
    }

    private static void getMyDoneTodos(Client client, ContractId contractId) throws TimeoutException, PrecheckStatusException {
        // Calls a function of the smart contract
        ContractCallQuery contractQuery = new ContractCallQuery()
                //Set the gas for the query
                .setGas(500000)
                //Set the contract ID to return the request for
                .setContractId(contractId)
                //Set the function of the contract to call
                .setFunction("getMyDoneTodos")
                //Set the query payment for the node returning the request
                //This value must cover the cost of the request otherwise will fail
                .setQueryPayment(new Hbar(2));

        //Submit to a Hedera network
        ContractFunctionResult getMessage = contractQuery.execute(client);

        //Get the message
        List<String> message = getMessage.getStringArray(0);

        //Log the message
        System.out.println("The contract message: " + message);
    }

    private static void getMyOpenTodos(Client client, ContractId contractId) throws TimeoutException, PrecheckStatusException {
        // Calls a function of the smart contract
        ContractCallQuery contractQuery = new ContractCallQuery()
                //Set the gas for the query
                .setGas(500000)
                //Set the contract ID to return the request for
                .setContractId(contractId)
                //Set the function of the contract to call
                .setFunction("getMyOpenTodos")
                //Set the query payment for the node returning the request
                //This value must cover the cost of the request otherwise will fail
                .setQueryPayment(new Hbar(2));

        //Submit to a Hedera network
        ContractFunctionResult getMessage = contractQuery.execute(client);

        //Get the message
        List<String> message = getMessage.getStringArray(0);

        //Log the message
        System.out.println("The contract message: " + message);
    }

    private static void addMyNewTodo(Client client, ContractId contractId, String message) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {


        //Create a new contract execute transaction
        ContractExecuteTransaction contractExecuteTransaction = new ContractExecuteTransaction()
                //Set the contract ID
                .setContractId(contractId)
                //Set the gas for the query
                .setGas(100000)
                //Set the parameters for the function call
                .setFunction("addMyTodo", new ContractFunctionParameters().addString(message))
                //Set the payment amount for the function call
                .setMaxTransactionFee(new Hbar(2));

        //Sign with the client operator private key and submit the transaction to a Hedera network
        TransactionResponse contractExecuteTransactionResponse = contractExecuteTransaction.execute(client);

        //Request the receipt of the transaction
        contractExecuteTransactionResponse.getReceipt(client);
    }

    private static void getOwner(Client client, ContractId contractId) throws TimeoutException, PrecheckStatusException {
        // Calls a function of the smart contract
        ContractCallQuery contractQuery = new ContractCallQuery()
                //Set the gas for the query
                .setGas(100000)
                //Set the contract ID to return the request for

                .setContractId(contractId)
                //Set the function of the contract to call
                .setFunction("getOwner")
                //Set the query payment for the node returning the request
                //This value must cover the cost of the request otherwise will fail
                .setQueryPayment(new Hbar(2));

        //Submit to a Hedera network
        ContractFunctionResult getMessage = contractQuery.execute(client);

        //Get the message
        String message = getMessage.getAddress(0);

        //Log the message
        System.out.println("The contract message: " + message);
    }

}
