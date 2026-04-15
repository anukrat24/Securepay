package com.example.securepay.Router;

import com.example.securepay.Bank.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PaymentRouter {

    private final Random random = new Random();

    // traffic distribution config
    private final Map<String, List<RouteConfig>> routingTable = Map.of(

            "UPI", List.of(

                    new RouteConfig(new SBIBank(), 70),

                    new RouteConfig(new HDFCBank(), 30)

            ),

            "CARD", List.of(

                    new RouteConfig(new HDFCBank(), 60),

                    new RouteConfig(new SBIBank(), 40)

            ),

            "NETBANKING", List.of(

                    new RouteConfig(new SBIBank(), 100)

            )
    );

    public Bank route(String payMode) {

        if(payMode == null)
            throw new RuntimeException("PayMode required");

        List<RouteConfig> routes =
                routingTable.get(payMode.toUpperCase());

        if(routes == null)
            throw new RuntimeException("Invalid pay mode");

        int randomValue = random.nextInt(100);

        int cumulative = 0;

        for(RouteConfig rc : routes){

            cumulative += rc.trafficPercentage;

            if(randomValue < cumulative){

                return rc.bank;

            }

        }

        return routes.get(0).bank;
    }

    // helper class
    private static class RouteConfig {

        Bank bank;

        int trafficPercentage;

        RouteConfig(Bank bank, int trafficPercentage){

            this.bank = bank;

            this.trafficPercentage = trafficPercentage;

        }

    }
}