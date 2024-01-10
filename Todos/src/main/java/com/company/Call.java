package com.company;

import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.concurrent.TimeoutException;

public class Call {

    public static void main(String[] args) throws PrecheckStatusException, TimeoutException {
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
//0.0.7496997
        ContractId contractId = ContractId.fromString("0.0.7496997");
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
