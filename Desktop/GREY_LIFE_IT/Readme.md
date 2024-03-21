# GreyLife - Loan Risk Assessment 

## Introduction

We propose to develop an AI-powered blockchain-based mobile application that will help TVS Credit to assess the risk of borrowers more accurately. This app collects essential borrower data, such as credit scores, income, and debt-to-income ratios, alongside non-traditional data in the form of a personality assessment test to anticipate financial behavior and risk tolerance levels.

An advanced ML model is then employed to determine whether an applicant qualifies as a low-risk candidate based on both traditional and non-traditional data. Notably, all borrower information will be securely stored in a decentralized blockchain network, ensuring the utmost security and privacy.

The app features user-friendly customer service assisted by automated systems and live human interaction to help loan borrowers clear doubts regarding the process, etc. Furthermore, this application goes beyond risk assessment by offering personalized risk mitigation guidance through customer service. This approach encompasses financial education and tailored recommendations aimed at improving the borrower's financial well-being. By adopting a proactive stance, the application empowers borrowers to progressively diminish their risk profile over time.

## Project Components

This Project is divided into 3 parts:

1. **Android**
2. **Machine Learning**
3. **Blockchain**

## Android App

The Android app has two sides:

- **Borrower**
- **Lender**


### Borrower's Side App Screenshots

#### Login and Signup Screens:
<img width="507" alt="Screenshot 2023-11-17 at 5 05 08 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/0df491c2-cc84-4390-a3f4-730303233b92">

#### Home and Status Screens:
<img width="758" alt="Screenshot 2023-11-17 at 5 06 01 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/99b0aa20-4bd9-4321-a720-ced2555678da">

#### SRIT Test and Risk Mitigation Screens:
<img width="507" alt="Screenshot 2023-11-17 at 5 06 35 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/720d4d6e-5286-403e-b3b7-f9624b2c2f7d">


### Lender's Side App Screenshots


#### Customer Details and Risk Mitigation Screens:
<img src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/b2eb40fb-fa84-48f8-ba40-2c06bd96e0d6" width="250" height="500" /> 
<img src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/46e9b714-e2a1-496d-a4cd-cf82ff523987" width="250" height="500" /> 

#### Status:
<img src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/232b7aa6-7ebe-43e3-b9e8-4ba10ca41bdd" width="250" height="500" /> 
<img src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/5b2f92e8-5fe1-4ff3-88d0-cff595446547" width="250" height="500" /> 

## Machine Learning
### Overview
This project showcases the deployment of machine learning models using PCA (Principal Component Analysis) and XGBoost in Python. The models are trained and saved using pickle, then integrated into a Flask API. The Flask API is deployed on Railway, enabling users to send data from their mobile devices and receive predictions in real-time.
#### PCA (Principal Component Analysis)
Reduces the dimensionality of data, which can lead to faster training and improved model efficiency.
Helps in identifying important patterns and reducing noise in the data
It Reduces the input size from 11 to 3 and then process the result 
#### XGBoost
Provides high predictive accuracy and often outperforms other machine learning algorithms.
Allows for feature selection and can handle both numerical and categorical data
#### Ensemble Learning
Can lead to improved model performance, especially when the individual models have complementary strengths and weaknesses. Provides more robust predictions and is less sensitive to outliers or noise in the data.

### Workflow

<img width="641" alt="Screenshot 2023-11-17 at 5 07 19 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/a8a84e10-4456-4e1d-b329-17bead07c46b">

### Sample Input and Output
<img width="1010" alt="Screenshot 2023-11-17 at 5 07 47 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/603f9ea4-2b5e-4659-86fb-dab63ad46e4d">

## BlockChain
### Overview
This project focuses on the deployment of a Polygon Edge Chain on Amazon Web Services (AWS) using the Kaleido platform. The Polygon Edge Chain provides high-performance, low-latency infrastructure for blockchain applications. By successfully running a Polygon Edge Chain, this project aims to facilitate the development of efficient and scalable decentralized applications
### Consortium blockchain architecture
#### Node
Each node stores a copy of the blockchain and participates in the consensus process to validate transactions and add new blocks to the chain. There are two nodes running on polygon blockchain
#### Ledger
The ledger is the decentralized database that stores all of the transactions that occur on the blockchain
#### Smart Contracts
Smart contracts are self-executing contracts.These are used blockchain consortium architecture to automate the process of executing transactions on the blockchain. Kaleido's Smart Contract Management component simplifies Ethereum transaction submission and application development by providing clean RESTful interfaces for interaction with your smart contract methods.
##### Smart Contract WorkFlow
<img width="1030" alt="Screenshot 2023-11-17 at 5 08 15 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/50640901-9417-4fec-b6fa-b85b505d0400">

#### Governance
Consortium blockchain governance is member-defined and adaptable, comprising rules and decision-making mechanisms tailored to specific use cases and goals.
Access to a private blockchain network is restricted to authorized parties only, and the network is not open to the public.
Private blockchains are preferred in enterprise use cases, such as supply chain management, to maintain greater network control.
#### Transaction Details
<img width="1003" alt="Screenshot 2023-11-17 at 5 08 59 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/87e49173-f167-412a-b616-986433f3a8f1">
<img width="492" alt="Screenshot 2023-11-17 at 5 09 23 PM" src="https://github.com/Smarshal21/GREY_LIFE_IT/assets/99678760/021ae77c-8b02-4726-aa79-72232f7a1adc">

## Features
- Authentication - Email-Password
- Dashboard
- Status Page (Application Status)
- Page for Uploading Documents and Data
- ML model Processed Output
- Secure Storage of data in Private Polygon BlockChain Deployed on Kaleido

## TECH STACK
- Kotlin
- XML
- Firebase
- Python
- Sklearn
- Flask
- Railway
- Solidity
- Kaleido
- Polygon-Edge
- JavaScript

## Meet The Team
- KSN Samanwith (Team Leader)| [Github](https://github.com/Smarshal21)
- Badri Akkala | [Github](https://github.com/LCB2021029-Badri)


