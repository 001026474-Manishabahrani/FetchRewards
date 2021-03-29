# Fetch-Rewards
Fetch Rewards Coding Exercise for Points

Application to store points for payers and calculate spending chronologically.

* Dependencies
	* Java >= 8
	* Maven 
	* Spring

* To run execute the following command in base folder
```
mvn spring-boot:run
```

* Postman Tests included in main folder

* API Routes for this application 


**[POST]** /addTransaction
```
Adds given transaction details to local memory
Input JSON: { "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }
Output: Success/Invalid 
```

**[POST]** /spendPoints
```
Reduces the payers by given points based on first come first spend basis
Input JSON: { "points": 5000 }
Output: [
    		{ "payer": "DANNON", "points": -100 },
    		{ "payer": "UNILEVER", "points": -200 },
    		{ "payer": "MILLER COORS", "points": -4,700 }
		]
```

**[GET]** /pointBalance
```
Calculates balance points per payer
Output: [
			{"payer":"UNILEVER","points":0},
			{"payer":"MILLER COORS","points":5300},
			{"payer":"DANNON","points":1000}
		]
```

#### Assumption that if any data is not found then the record is invalid 
