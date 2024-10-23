# eBanking Application - 2024

## Project Overview
The eBanking Application is a mobile banking solution that provides users with essential functionalities such as account management, money transfers, currency exchange, stock purchases, and financial recommendations. The app aims to offer a seamless digital banking experience with added premium features for enhanced services.

## Features
The application supports the following functionalities:

1. **User Account Management**
   - Create user accounts with unique email identification.
   - Manage personal details such as first name, last name, and address.

2. **Portfolio Management**
   - Support for multiple currency accounts per user.
   - Ability to own stocks and cryptocurrencies.
   - Maintain a list of friends for easy money transfers.

3. **Banking Operations**
   - **Account Creation:** Open accounts in supported currencies.
   - **Fund Deposit:** Add money to accounts.
   - **Currency Exchange:** Exchange money between different currency accounts.
   - **Money Transfer:** Transfer funds to friends.
   - **Stock Purchases:** Buy stocks using USD.

4. **Stock Recommendations**
   - Uses a simplified version of the Simple Moving Averages (SMA) Crossover Strategy for recommendations:
     - Calculates short-term SMA (last 5 days) and long-term SMA (last 10 days).
     - Recommends stocks when the short-term SMA is higher than the long-term SMA.

5. **Premium Account Features (Bonus)**
   - Users can upgrade to a premium account for additional benefits:
     - No fees for currency exchanges.
     - Discounted rates (5% off) on recommended stock purchases.

## Design Patterns
The project employs at least four design patterns for better code organization and maintainability:

1. **Singleton Pattern**: Manages a single instance of configuration settings or database connections.
2. **Factory Pattern**: Creates various account types or currencies.
3. **Observer Pattern**: Notifies users about stock recommendations or account changes.
4. **Strategy Pattern**: Implements different algorithms for currency exchange and stock purchase strategies.

## Input and Output
The application processes data from the following input files:
- **`currencies.txt`**: Lists supported currencies.
- **`exchangeRates.csv`**: Contains exchange rates for different currencies.
- **`stocks.txt`**: Lists stocks available for trading.
- **`stockValues.csv`**: Provides stock prices for the last 10 days.
- **`commands.txt`**: Contains commands for user and account management, transactions, and more.

Output will be displayed in the console, showing command results or error messages.

## Testing and Evaluation
- **Automated Tests**: The project includes tests to validate functionality.
- **Design Patterns Justification**: The project demonstrates the proper usage and implementation of design patterns.

## Bonus Implementation
- Implements premium account features with specific test cases for validation.
