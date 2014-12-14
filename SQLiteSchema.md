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
ID       - Unique ID for each entry
DATE     - Date of purchase 
	Formatted: YYYY:MM:DD:HH:SS
Category - Name of Category Expense Item belongs to
Amount   - Expense amount
User     - Person who bought ttem
 
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
