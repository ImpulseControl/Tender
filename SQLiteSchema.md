#Tender SQL Schema

##Tables

###Expenses
####Overview
List Individialul Expense Items
####Type Table
|Name              |Type            |
|:----------------:|:--------------:|
|ID                |KEY             |
|Date              |DATE            |
|Descripton        |TEXT            |
|Category          |TEXT            |
|Amount            |DOUBLE          |
|User              |TEXT            |
####Data
ID   - Unique Id for each Entry
DATE - Date of Purchase 
	Formatted: MM:DD:YY:HH:SS
Category - Name of Ctegory Expense Item belongs to
Amount - Expense Amount
User - Person Who Bought Item
 
###Categories
####Overview
####Type Table
|Name          |Type         |
|:------------:|:-----------:|
|ID            |KEY          |
|Name          |TEXT         |
|Interval      |TEXT         |
|Budget        |DOUBLE       |
|Spent         |DOUBLE       |
|Start Date    |DATE         |
|End Date      |DATE         |
|Is Current    |BOOLEAN      |
####Data


###Users
####Overview
####Type Table
|Name          |Type         |
|:------------:|:-----------:|
|ID            |KEY          |
|First Name    |TEXT         |
|Last Name     |TEXT         |
|Email         |TEXT         |
####Data
